package com.main.ajarul.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig
        implements WebSocketMessageBrokerConfigurer {
          

    @Override
    public void configureMessageBroker(
            MessageBrokerRegistry config) {

        config.enableSimpleBroker("/topic");

        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(
            StompEndpointRegistry registry) {

        registry
                .addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Bean
public ApplicationRunner testBroker(
        SimpMessagingTemplate template) {

    return args -> {

        new Thread(() -> {

            try {

                Thread.sleep(15000);

                System.out.println(
                        "TEST MESSAGE");

                template.convertAndSend(
                        "/topic/device/ESP32_001",
                        "HELLO");
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    };
}
  
}