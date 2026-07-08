package com.main.ajarul.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
public class WebSocketEvents {


    @EventListener
    public void handleConnect(
            SessionConnectEvent event) {

        System.out.println(
                "CONNECTED: "
                        + event.getMessage());
    }

    @EventListener
    public void handleSubscribe(
            SessionSubscribeEvent event) {

        System.out.println(
                "SUBSCRIBED: "
                        + event.getMessage());
    }
}