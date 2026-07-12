
package com.main.ajarul.service;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

import com.main.ajarul.config.MQTTConfig;

@Service
public class MQTTService {

    private final MqttClient client;

    public MQTTService() throws Exception {

        client = new MqttClient(
                MQTTConfig.BROKER,
                MqttClient.generateClientId());

        MqttConnectOptions options = new MqttConnectOptions();

        options.setUserName(MQTTConfig.USERNAME);
        options.setPassword(MQTTConfig.PASSWORD.toCharArray());

        client.connect(options);

        System.out.println("MQTT Connected");
    }

    public void publish(String topic, String message) {

        try {

            client.publish(
                    topic,
                    new MqttMessage(message.getBytes()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}