package com.bithumb.websocket.config;

import com.bithumb.utils.domain.Coin;
import com.bithumb.utils.service.CoinServiceImpl;
import com.bithumb.websocket.controller.dto.QuoteRequest;
import com.bithumb.websocket.controller.dto.QuoteResponse;
import com.bithumb.websocket.domain.Quote;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;


@WebSocket(maxTextMessageSize = 64 * 1024)
@RequiredArgsConstructor
public class QuoteSocket {
    private final KafkaTemplate<String, Quote> kafkaTemplate;
    private final CoinServiceImpl coinService;

    private static final String TOPIC = "youngcha-coin-quote";

    private final CountDownLatch closeLatch = new CountDownLatch(1);

    @SuppressWarnings("unused")
    private Session session;

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
        return this.closeLatch.await(duration, unit);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.printf("Connection closed: %d - %s%n", statusCode, reason);
        this.session = null;
        this.closeLatch.countDown(); // trigger latch
    }

    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException, ExecutionException, InterruptedException, TimeoutException {
        System.out.printf("Got connect: %s%n", session);
        this.session = session;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";

        Future<Void> fut;
        HashMap<String, Coin> coins = coinService.getCoins();
        int i = 0;
        String[] str = new String[coins.size()];
        for (String strKey : coins.keySet()) {
            Coin strValue = coins.get(strKey);
            str[i++] = strValue.getMarket();
        }
        QuoteRequest quoteRequest = QuoteRequest.builder().type("ticker").symbols(str).tickTypes(new String[]{"24H"}).build();
        jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(quoteRequest);
        fut = session.getRemote().sendStringByFuture(jsonInString);
        fut.get(2, TimeUnit.SECONDS); // wait for send to complete.

    }

    @OnWebSocketMessage
    public void onMessage(String msg) throws IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(msg);

        Set key = obj.keySet();
        if (!key.contains("status")) {
            QuoteResponse quote = mapper.readValue(msg, QuoteResponse.class);
            String korean = coinService.getCoins().get(quote.getContent().getSymbol().split("_")[0]).getKorean();
            quote.getContent().setKorean(korean);
//            System.out.println(quote.getContent());
            kafkaTemplate.send(TOPIC, quote.getContent());
        }
    }
}
