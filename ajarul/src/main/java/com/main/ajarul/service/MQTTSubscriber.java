package com.main.ajarul.service;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import com.main.ajarul.config.MQTTConfig;

import jakarta.annotation.PostConstruct;

@Service
public class MQTTSubscriber {
    private MqttClient client;

    @PostConstruct
    public void init() {
        try {
            client = new MqttClient(MQTTConfig.BROKER,
                    MqttClient.generateClientId());
            MqttConnectOptions options = new MqttConnectOptions();

            options.setUserName(MQTTConfig.USERNAME);
            options.setPassword(
                    MQTTConfig.PASSWORD.toCharArray());

            options.setAutomaticReconnect(true);
            options.setCleanSession(true);

            client.connect(options);
            client.subscribe("home/+/status");

System.out.println("Subscribed : home/+/status");
client.setCallback(new MqttCallback() {

    @Override
    public void connectionLost(Throwable cause) {

        System.out.println("MQTT Lost");

    }

    @Override
    public void messageArrived(
            String topic,
            MqttMessage message) {

        System.out.println("================================");
        System.out.println("TOPIC : " + topic);
        System.out.println("MESSAGE : "
                + new String(message.getPayload()));
        System.out.println("================================");

    }

    @Override
    public void deliveryComplete(
            IMqttDeliveryToken token) {

    }

});

            System.out.println("MQTT Subscriber Connected");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
