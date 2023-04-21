package com.example.demo.mqtt;

import com.example.demo.entity.Oled;
import com.example.demo.entity.Rgb;
import com.example.demo.repository.AccRepository;
import com.example.demo.repository.HumRepository;
import com.example.demo.repository.OledRepository;
import com.example.demo.repository.RgbRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;


@Component
@Data
@Slf4j
public class MqttService {

    public static MqttService mqttService;


    @Resource
    AccRepository accRepository;

    @Resource
    HumRepository humRepository;
    @Resource
    OledRepository oledRepository;
    @Resource
    RgbRepository rgbRepository;

    @PostConstruct
    public void init() {
        mqttService = this;
    }

    public static void mqttStart() {
        PushClient client = PushClient.getInstance();


        for (int i = 0; i < MqttConfig.topic.length; i++) {

          String  topic=  MqttConfig.topic[i];
            log.info("MqttConfig.topic {}",topic);
            client.subscribe(MqttConfig.topic[i], MqttConfig.qos[i]);
        }

    }


}
