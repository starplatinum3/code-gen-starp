package com.example.demo.mqtt;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Acc;
import com.example.demo.entity.Hum;
import com.example.demo.entity.Oled;
import com.example.demo.entity.Rgb;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;

/**
 * MQTT 推送回调
 */

//@Slf4j
public class PushCallback implements MqttCallback {


    private MqttClient client;
    private MqttConnectOptions options;


    private static final Logger log = LoggerFactory.getLogger(PushCallback.class);


    public PushCallback(MqttClient client, MqttConnectOptions options) {
        this.client = client;
        this.options = options;
    }


    @SneakyThrows
    @Override
    public void connectionLost(Throwable cause) {
//        log.info("断开连接，建议重连" + this);
        log.warn("断开连接，建议重连");
        log.warn("原因: " + cause);
        //断开连接，重连
        int tryTimes = 1;
        while (!client.isConnected()) {
            Thread.sleep(1000);

            log.info("重试第{}次", tryTimes);
//                https://blog.csdn.net/zhan107876/article/details/100935779
            //即使连接上也要先断开再重新连接
//            client.disconnect();  //不这样就重连会报错
//            这里不能断开连接啊，断了就有问题
//            log.info("重新连接");
            client.connect(options);
            log.info("连接完成");
            tryTimes++;


        }
        log.info("重连成功，开始订阅消息");
        //发布相关的订阅
//            订阅


//        client.subscribe(topic, qos);
        client.subscribe(MqttConfig.topic, MqttConfig.qos);


    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        //log.info(token.isComplete() + "");
    }


    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
//        log.info("Topic: " + topic);
//        log.info("Message: " + new String(message.getPayload()));
        String payload = new String(message.getPayload());
//        JSONObject jsonObject = JSONObject.parseObject(new String(message.getPayload()));
        JSONObject jsonObject=null;
        try{
             jsonObject = JSONObject.parseObject(payload);

        }catch (Exception e){
            log.info("不是json 直接写入payload");
            e.printStackTrace();
        }

//        log.info("");
//        PushClient client = PushClient.getInstance();
//        RedPackToMiNiDto redPackToMiNiDto
//        但是用这个不是 还是要set 和 put有什么区别吗. 确实
        log.info("jsonObject {}",jsonObject);
        if (topic.equals(MqttConfig.adxlTopic)) {

            Acc acc = Acc.builder().x((Integer) jsonObject.get("x")).y((Integer) jsonObject.get("y"))
                    .z((Integer) jsonObject.get("z")).createTime(new Date()).build();
            MqttService.mqttService.accRepository.save(acc);

        } else if (topic.equals(MqttConfig.HumTempTopic)) {


            Double hum = getDoubleFromJSONObject(jsonObject, "hum");
            Double temp = getDoubleFromJSONObject(jsonObject, "temp");
            Hum humTemp = Hum.builder().hum(hum).temp(temp).createTime(new Date()).build();
            MqttService.mqttService.humRepository.save(humTemp);

        }  else if (topic.equals(MqttConfig.oledTopic)) {

            MqttService.mqttService.oledRepository.save( Oled.builder().payload(payload).createTime(new Date()).build());
        }else if (topic.equals(MqttConfig.rgbTopic)) {
//            Rgb build = Rgb.builder().r((Integer) jsonObject.get("r")).
//                    g((Integer) jsonObject.get("g")).b((Integer) jsonObject.get("b")).createTime(new Date()).build();
            Rgb build = Rgb.builder().r(getIntFromJSONObject(jsonObject,"r")).
                    g(getIntFromJSONObject(jsonObject,"g")).
                    b(getIntFromJSONObject(jsonObject,"b")).createTime(new Date()).build();
            MqttService.mqttService.rgbRepository.save(build);

        }

    }

    Double getDoubleFromJSONObject(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getBigDecimal(key).doubleValue();
        } catch (Exception e) {
            try{
                return Double.parseDouble(jsonObject.getString(key));
            }catch (Exception e1){
                return  null;
            }


        }

    }


    Integer getIntFromJSONObject(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getInteger(key);
        } catch (Exception e) {
            try{
                String string = jsonObject.getString(key);
                return Integer.parseInt(string);
            }catch (Exception e1){
                return  null;
            }


        }

    }
}