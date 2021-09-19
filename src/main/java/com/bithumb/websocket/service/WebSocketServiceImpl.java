package com.bithumb.websocket.service;

import com.bithumb.coin.service.CoinServiceImpl;
import com.bithumb.websocket.config.QuoteSocket;
import com.bithumb.websocket.domain.Quote;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class WebSocketServiceImpl implements WebSocketService{

    private String destUri = "wss://pubwss.bithumb.com/pub/ws";

    @Autowired
    private final KafkaTemplate<String, Quote> kafkaTemplate;
    @Autowired
    private final CoinServiceImpl coinService;
    public void start(){
        WebSocketClient client = new WebSocketClient();
        QuoteSocket socket = new QuoteSocket(kafkaTemplate, coinService);

        try{
            client.start();
            URI echoUri = new URI(destUri);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket,echoUri,request);
            System.out.printf("Connecting to : %s%n",echoUri);

            socket.awaitClose(5, TimeUnit.SECONDS);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}


