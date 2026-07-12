package com.main.ajarul.config;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQTTConfig {

    public static final String BROKER =
            "ssl://g1893b13.ala.asia-southeast1.emqxsl.com:8883";

    public static final String USERNAME = "esp32";

    // Put the password you created in EMQX here
    public static final String PASSWORD = "12345678";

    @Bean
    public MqttConnectOptions mqttConnectOptions() {

        MqttConnectOptions options = new MqttConnectOptions();

        options.setServerURIs(new String[]{BROKER});
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());

        options.setAutomaticReconnect(true);
        options.setCleanSession(true);

        return options;
    }
}