package com.bithumb;


import com.bithumb.websocket.service.WebSocketServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = { "com.bithumb.config","com.bithumb.websocket", "com.bithumb.utils"})
public class ProducerApplication {
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(ProducerApplication.class, args);
		WebSocketServiceImpl client = ctx.getBean(WebSocketServiceImpl.class);
		client.start();
	}
}
