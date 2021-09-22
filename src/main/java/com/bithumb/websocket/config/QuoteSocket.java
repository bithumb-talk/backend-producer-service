package com.bithumb.websocket.config;

import com.bithumb.coin.service.CoinServiceImpl;
import com.bithumb.websocket.controller.dto.QuoteRequestDto;
import com.bithumb.websocket.controller.dto.QuoteResponseDto;
import com.bithumb.websocket.domain.Quote;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


@WebSocket(maxTextMessageSize = 64 * 1024)
@RequiredArgsConstructor
public class QuoteSocket {
    private final KafkaTemplate<String, Quote> kafkaTemplate;
    private final CoinServiceImpl coinService;
    private final RedisTemplate redisTemplate;

    private static final String TOPIC = "kafka-spring-producer-coin-test3";

    private final CountDownLatch closeLatch = new CountDownLatch(1);;

    @SuppressWarnings("unused")
    private Session session;

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException{
        return this.closeLatch.await(duration,unit);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason){
        System.out.printf("Connection closed: %d - %s%n",statusCode,reason);
        this.session = null;
        this.closeLatch.countDown(); // trigger latch
    }

    @OnWebSocketConnect
    public void onConnect(Session session){
        System.out.printf("Got connect: %s%n",session);
        this.session = session;
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";

        try{
            Future<Void> fut;
            QuoteRequestDto quoteRequest = new QuoteRequestDto();
            quoteRequest.setType("ticker");

            quoteRequest.setSymbols(coinService.getQuote());
            quoteRequest.setTickTypes(new String[]{
                    "MID"
            });
            jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(quoteRequest);
            fut = session.getRemote().sendStringByFuture(jsonInString);
            fut.get(2,TimeUnit.SECONDS); // wait for send to complete.
        }catch (Throwable t){
            t.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String msg) throws JsonProcessingException, ParseException, UnsupportedEncodingException {
        ObjectMapper mapper = new ObjectMapper();
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject)parser.parse(msg);

        Set key = obj.keySet();
        if (!key.contains("status")){
            QuoteResponseDto quote = mapper.readValue(msg, QuoteResponseDto.class);
            HashOperations operations = redisTemplate.opsForHash();
            String str = new String((byte[]) operations.get(quote.getContent().getSymbol(),quote.getContent().getSymbol()),"UTF-8");
            quote.getContent().setKorean(str);
            System.out.println("getContent"+quote.getContent());
            kafkaTemplate.send(TOPIC,quote.getContent());
        }
    }
}
