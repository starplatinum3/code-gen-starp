package com.example.demo.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import top.starp.util.log;

@Controller
@RequestMapping("/all")
@Slf4j
public class MySSEController_Working {

    private Set<SseEmitter> sseEmitters = new HashSet<SseEmitter>();
    private int messageCount = 0;


//
//    @RequestMapping("/ssestream")
//    public Callable<ResponseEntity<ResponseBodyEmitter>> example() {
//        return () -> {
//            ResponseBodyEmitter emitter = new ResponseBodyEmitter();
//            // 处理业务逻辑，根据需要使用emitter写入数据
//            // 完成emitter
//            for (int i = 0; i <77 ; i++) {
//                emitter.send("MessageCounter : " + i);
//            }
//            emitter.complete();
//            return new ResponseEntity<>(emitter, HttpStatus.OK);
//        };
//    }

    @GetMapping("/ssestream")
    public DeferredResult<ResponseEntity<ResponseBodyEmitter>> example() throws IOException {
        DeferredResult<ResponseEntity<ResponseBodyEmitter>> deferredResult = new DeferredResult<>();
        // 处理业务逻辑，根据需要使用ResponseBodyEmitter写入数据
        // 完成ResponseBodyEmitter并设置到DeferredResult中
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
//        for (int i = 0; i <77 ; i++) {
//                emitter.send("MessageCounter : " + i);
//            }
        emitterSend(emitter);
//        emitter.complete();
        deferredResult.setResult(new ResponseEntity<>(emitter, HttpStatus.OK));
        return deferredResult;
    }




//    @GetMapping("/ssestream")
//    public ResponseBodyEmitter example() throws IOException {
////        DeferredResult<ResponseEntity<ResponseBodyEmitter>> deferredResult = new DeferredResult<>();
//        // 处理业务逻辑，根据需要使用ResponseBodyEmitter写入数据
//        // 完成ResponseBodyEmitter并设置到DeferredResult中
//        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
////        for (int i = 0; i <77 ; i++) {
////                emitter.send("MessageCounter : " + i);
////            }
//        emitterSend(emitter);
////        emitter.complete();
////        deferredResult.setResult(new ResponseEntity<>(emitter, HttpStatus.OK));
//        return emitter;
//    }




//    @RequestMapping("/ssestream")
//    public SseEmitter getRealTimeMessageAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        final SseEmitter sseEmitter = new SseEmitter();
//
//        sseEmitter.onCompletion(() -> {
//            synchronized (this.sseEmitters) {
//                this.sseEmitters.remove(sseEmitter);
//            }
//        });
//
//        sseEmitter.onTimeout(()-> {
//            sseEmitter.complete();
//        });
//
//        // Put context in a map
//        sseEmitters.add(sseEmitter);
//
//        return sseEmitter;
//    }

    void emitterSend(ResponseBodyEmitter emitter){

        int maxCnt=999999;
        Thread thread = new Thread(() -> {
            try {
                int i=0;
                while (i++<maxCnt) {
                    // 生成随机数据
                    Random random = new Random();
                    int randomNumber = random.nextInt(100);

//                    log.info("randomNumber:{}",randomNumber);
                    // 发送数据到客户端
                    emitter.send("Random number: " + randomNumber);

                    // 暂停一秒
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                // 处理发送异常
                emitter.completeWithError(e);
            }finally {
                if(emitter!=null){
                    emitter.complete();
                }
            }
        });

        thread.start();
    }




//    @Scheduled(fixedDelay = 2*1000)
//    public void scheduledMsgEmitter() throws IOException
//    {
//        if(!sseEmitters.isEmpty())
//            ++messageCount;
//        else
//            System.out.println("No active Emitters ");
//
//        System.out.println("Sent Messages : " + messageCount);
//
//        sseEmitters.forEach(emitter -> {
//            if (null != emitter)
//                try {
//                    System.out.println("Timeout : "+ emitter.getTimeout());
//                    emitter.send("MessageCounter : " + messageCount);
//                    emitter.complete();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//        });
//    }
//

}