package com.example.demo.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 创建一个MQTT客户端
 */
public class PushClient {

    private static final Logger log = LoggerFactory.getLogger(PushClient.class);
    public static String MQTT_HOST = "";
    public static String MQTT_CLIENTID = "";
    public static String MQTT_USERNAME = "";
    public static String MQTT_PASSWORD = "";
    public static int MQTT_KEEPALIVE = 10;

    private MqttClient client;
    private static volatile PushClient mqttClient = null;

    public static PushClient getInstance() {
        initConfig();
//        2021年8月3日16:09:13 mqp host 在获取 instance的时候要获取
        if (mqttClient == null) {
            synchronized (PushClient.class) {
                if (mqttClient == null) {
                    mqttClient = new PushClient();
                }
            }
        }

        return mqttClient;
    }

    private static void initConfig() {
        PushClient.MQTT_HOST = MqttConfig.MQTT_HOST;
        PushClient.MQTT_CLIENTID = MqttConfig.MQTT_CLIENT_ID;
        PushClient.MQTT_USERNAME = MqttConfig.MQTT_USERNAME;
        PushClient.MQTT_PASSWORD = MqttConfig.MQTT_PASSWORD;
    }

    PushClient() {
        log.info("Connect MQTT: " + this);
        connect();
    }

    private void connect() {
        try {
            client = new MqttClient(MQTT_HOST, MQTT_CLIENTID, new MemoryPersistence());
            MqttConnectOptions option = new MqttConnectOptions();
            option.setCleanSession(true);
//            option.setCleanSession(false);
            option.setUserName(MQTT_USERNAME);
            option.setPassword(MQTT_PASSWORD.toCharArray());
            option.setConnectionTimeout(0);

            option.setKeepAliveInterval(MQTT_KEEPALIVE);
            option.setAutomaticReconnect(true);

            try {
                client.setCallback(new PushCallback(client, option));
                client.connect(option);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发布主题，用于通知<br>
     * 默认qos为1 非持久化
     *
     * @param topic
     * @param data
     */
    public void publish(String topic, String data) {
        publish(topic, data, 1, false);
    }

    /**
     * 发布
     *
     * @param topic
     * @param data
     * @param qos
     * @param retained
     */
    public void publish(String topic, String data, int qos, boolean retained) {
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(retained);
        message.setPayload(data.getBytes());
        MqttTopic mqttTopic = client.getTopic(topic);

        if (null == mqttTopic) {
            log.error("Topic Not Exist");
        }
        MqttDeliveryToken token;
        try {
            token = mqttTopic.publish(message);
            token.waitForCompletion();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅某个主题 qos默认为1
     *
     * @param topic
     */
    public void subscribe(String topic) {
        subscribe(topic, 0);
    }

    /**
     * 订阅某个主题
     *
     * @param topic
     * @param qos
     */
    public void subscribe(String topic, int qos) {
        try {
            client.subscribe(topic, qos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        PushClient.MQTT_HOST = MqttConfig.MQTT_HOST;
        PushClient.MQTT_CLIENTID = MqttConfig.MQTT_CLIENT_ID;
        PushClient.MQTT_USERNAME = MqttConfig.MQTT_USERNAME;
        PushClient.MQTT_PASSWORD = MqttConfig.MQTT_PASSWORD;

        PushClient client = PushClient.getInstance();

//        client id不能和 mqttx的一样，client_id 自己造一个就行
        System.out.println("订阅");

//        这里连了 mqttx 就断开了
        System.out.println("发送");
        client.publish("mqp", "java send");
    }
}