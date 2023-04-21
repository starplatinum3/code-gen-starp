package com.example.demo.mqtt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Configuration
@PropertySource("classpath:application.yml")    	//指明配置文件位置  会自动去resouces下找到此文件
//@ConfigurationProperties(prefix = "mqtt")

//添加一个 配置，万一要改 只要在这里改就好了，集中在一起，比较方便
@Data
public class MqttConfig {




    public static String    HumTempTopic ="ZUCC-IOTMQP/esp";
    public static String    adxlTopic ="ZUCC-IOTMQP/adxl";
    public static String   oledTopic ="ZUCC-IOTMQP/oled";
    public static String    rgbTopic ="ZUCC-IOTMQP/rgb";
//    温湿度信息 ZUCC-IOTMQP/esp
//    Adxl 加速度信息  ZUCC-IOTMQP/adxl
//    Oled显示屏  ZUCC-IOTMQP/oled
//    控制rgb灯 ZUCC-IOTMQP/rgb

    public  static  String[] topic = {  MqttConfig.HumTempTopic,  MqttConfig.adxlTopic,
            MqttConfig.oledTopic,MqttConfig.rgbTopic};
    public  static int[] qos = {1, 1, 1,1
    };

    @Value("${mqtt.host}")
    public static   String MQTT_HOST ;
    @Value("${mqtt.client_id}")
    public static  String MQTT_CLIENT_ID ;
    @Value("${mqtt.username}")
    public static  String MQTT_USERNAME ;
    @Value("${mqtt.password}")
    public static  String MQTT_PASSWORD ;

//要写在 set
    @Value("${mqtt.host}")
    public void setMQTT_HOST(String mqttHost) {
        MQTT_HOST = mqttHost;
    }
    @Value("${mqtt.client_id}")
    public void setMQTT_CLIENT_ID(String mqtt_client_id) {
       MQTT_CLIENT_ID = mqtt_client_id;
    }
    @Value("${mqtt.username}")
    public void setMQTT_USERNAME(String mqtt_username) {
       MQTT_USERNAME = mqtt_username;
    }
    @Value("${mqtt.password}")
    public void setMQTT_PASSWORD(String mqtt_password) {
        MQTT_PASSWORD = mqtt_password;
    }

    //@Bean
    public MqttConfig() {

    }

    public static void main(String[] args) {
    }
}
