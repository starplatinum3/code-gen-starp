package com.example.demo.controller;

import com.example.demo.dto.AskTrainVo;
import com.google.common.collect.ImmutableList;
import com.lark.oapi.okhttp.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import top.starp.util.JsonUtil;
import top.starp.util.ReturnT;
import top.starp.util.u;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/all")
public class MyController {
    private  SseEmitter emitter = new SseEmitter();

    @GetMapping("/stream")
    public SseEmitter stream() {
        return emitter;
    }

//    d(){
////        HttpU
//    }

    @PostMapping("/chat/streamMe")
    public Object streamData(@org.springframework.web.bind.annotation.RequestBody AskTrainVo askTrainVo) throws IOException {
        //    @GetMapping("/chat/stream")


//        "/api/endpoint")
//        WebCLient

        // 创建SseEmitter对象
//        SseEmitter emitter = new SseEmitter();
        OkHttpClient client = new OkHttpClient();

//        String chatBaseUrl=  "http://10.66.10.235:8000/v1";
//        String chatBaseUrl=  "http://{chatHost}:8000/v1".replace("{chatHost}",chatHost);


//        String chatBaseUrl = "http://{chatUri}/v1".replace("{chatUri}", chatUri);
//        String chatPath = "/chat/completions";
//

//        String chatUrl = chatBaseUrl + chatPath;

//        String chatUrl =   "http://localhost:8889/all/api/endpoint";
        String chatUrl =      "http://10.160.195.50:50029/chat";
//        {
//            "question": "java怎么学",
//                "streaming": true,
//                "history": []
//        }

//        u.mapOf()
//        String bodyStr = JsonUtil.toJsonString(u.mapOf());
        String bodyStr = JsonUtil.toJsonString(   u.mapOf(
                u.p("question","java怎么学")
                ,    u.p("streaming",true)
                ,    u.p("history",u.list())
        ));

//        String bodyStr = bodyGet(askTrainVo);

//       RequestBody
// 创建POST请求体，可以根据需要设置请求参数
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                bodyStr);


        Request request = new Request.Builder()
                .url(chatUrl)  // 替换为你的API URL
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
//            System.out.println("Request was not successful. Status code: " + response.code());
//            if (responseBody != null) {
//                System.out.println("Response body: " + responseBody.string());
//            }

            int code = response.code();
//            System.out.println("Request was not successful. Status code: " + code);

            if (responseBody != null) {
                if (code != 200) {
                    String string = responseBody.string();
                    System.out.println("Response body err : " + string);
                    Map<String, Object> logMap = new HashMap<>();
                    logMap.put("msg", "Response body err : " + string);
                    logMap.put("code", 500);
                    logMap.put("responseBody", responseBody);
                    // 处理请求失败的情况
                    String jsonString = JsonUtil.toJsonString(logMap);
//                    emitter.completeWithError(new Exception(logMap.toString()));
                    emitter.completeWithError(new Exception(jsonString));
                }
//                System.out.println("Response body: " + responseBody.string());
            }

            if (response.isSuccessful() && responseBody != null) {
                // 获取响应的输入流
                InputStream inputStream = responseBody.byteStream();

                // 读取输入流的数据并通过SseEmitter发送给客户端
//                byte[] buffer = new byte[8192];
                int bytesRead;
//                while ((bytesRead = inputStream.read(buffer)) != -1) {
//                    emitter.send(buffer, 0, bytesRead);
//                }

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // 将每行数据发送给客户端
                        String clearLine = line.replace("data:", "").trim();
                        emitter.send(clearLine);
//                        try{
//                            emitter.send(clearLine);
//                        }catch (Exception e){
//                            emitter=new SseEmitter();
//                        }

                    }
                }

                // 发送完成信号给客户端
                emitter.complete();
            } else {
                // 处理请求失败的情况
                emitter.completeWithError(new Exception("Request failed"));
            }
        } catch (IOException e) {
            // 处理IO异常
            emitter.completeWithError(e);
        }

        // 在这里执行OkHttp请求并返回SseEmitter对象
//        return emitter;
        return ReturnT.success(
                u.mapOf(
                        u.p("msg","ok")
                        ,u.p("chatUrl",chatUrl)
                )
        );
    }


    @PostMapping("/api/endpoint")
    public void handlePostRequest() {
        // 处理 POST 请求

        int maxCnt=999999;
        // 启动新的线程发送数据
        Thread thread = new Thread(() -> {
            try {
                // 模拟传递数据
                for (int i = 0; i < maxCnt; i++) {
                    // 生成要传递的数据
                    String data = "Data " + i;

                    // 发送数据到客户端
                    emitter.send(data);

                    // 暂停一秒
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 处理发送异常
                emitter.completeWithError(e);
            } finally {
                // 发送完毕后，关闭连接
                emitter.complete();
            }
        });

        thread.start();
    }


//    private LoadingCache<Long, SseEmitter> sseEmitterMap =
//            Caffeine.newBuilder().initialCapacity(1024)
//                    // 手动设置2分钟缓存过期，一次流式请求不可能超过2分钟
//                    .expireAfterAccess(2L, TimeUnit.MINUTES)
//                    //缓存填充策略
//                    .build(sseId -> new SseEmitter(0L));

    /**
     * 获取SSE连接，返回sseID给前端
     * @return
     */
//    @GetMapping("/chat/getSseEmitter")
//    public SseEmitter getSseEmitter() {
//
//        // 默认30秒超时,设置为0L则永不超时
//        SseEmitter sseEmitter = new SseEmitter(0L);
//
//        // 生成sseID 并且通过SseEmitter传递给前端，后续前端通过sseID来发送消息（实现双向通讯）
//        long sseEmitterId = SnowflakeIdGenerator.nextId();
//
//        try {
//            sseEmitter.send(SseEmitter.event()
//                    .id(String.valueOf(sseEmitterId))
//                    .data(sseEmitterId)
//                    .reconnectTime(3000));
//
//        } catch (IOException e) {
//            log.error("获取SSE连接失败！");
//            return null;
//        }
//
//        sseEmitterMap.put(sseEmitterId, sseEmitter);
//        log.info("获取SSE连接成功！");
//        return sseEmitter;
//    }

    /**
     * 多轮流式对话
     * @param req
     * @return
     */
//    @PostMapping(path = "/chat/streamSessionChat")
//    public ReturnResult streamSessionChat(@RequestBody @Valid StreamSessionChatRequest req){
//        // 若用户上传了apikey则使用用户的，否则采用本系统的
//        UserApiKeyEntity userApiKeyEntity = userApiKeyService.getByUserIdAndType(req.getUserId(), ApiType.OPENAI);
//        String apiKey = userApiKeyEntity != null && !StringUtils.isEmpty(userApiKeyEntity.getApikey())
//                ? userApiKeyEntity.getApikey()
//                : adminApiKeyService.roundRobinGetByType(ApiType.OPENAI);
//        if(apiKey == null){
//            return ReturnResult.error().codeAndMessage(ResultCode.ADMIN_APIKEY_NULL);
//        }
//
//        SessionType sessionType = SessionType.get(req.getSessionType());
//        ChatGPTReq gptReq  = ChatGPTReq.builder()
//                .model(OpenAIConst.MODEL_NAME_CHATGPT_3_5)
//                .max_tokens(OpenAIConst.MAX_TOKENS - sessionType.maxContextToken)
//                .stream(true)
//                .build();
//
//        // 获取指定的sseEmitter, 将响应信息通过sseEmitter发送出去
//        SseEmitter sseEmitter = sseEmitterMap.get(req.getSseEmitterId());
//        if(sseEmitter == null){
//            return ReturnResult.error();
//        }
//        chatService.streamSessionChat(
//                req.getUserId(), req.getSessionId(), gptReq, req.getMessage()
//                , apiKey, sseEmitter, SessionType.get(req.getSessionType()));
//
//        // 清除缓存
//        sseEmitterMap.invalidate(req.getSseEmitterId());
//        return ReturnResult.ok();
//    }

    /**
     * 单轮流式对话
     * @param req
     * @return
     */
//    @PostMapping(path = "/chat/streamOneShotChat")
//    public ReturnResult streamOneShotChat(@RequestBody @Valid StreamOneShotChatRequest req){
//        // 若用户上传了apikey则使用用户的，否则采用本系统的
//        UserApiKeyEntity userApiKeyEntity = userApiKeyService.getByUserIdAndType(req.getUserId(), ApiType.OPENAI);
//        String apiKey = userApiKeyEntity != null && !StringUtils.isEmpty(userApiKeyEntity.getApikey())
//                ? userApiKeyEntity.getApikey()
//                : adminApiKeyService.roundRobinGetByType(ApiType.OPENAI);
//        if(apiKey == null){
//            return ReturnResult.error().codeAndMessage(ResultCode.ADMIN_APIKEY_NULL);
//        }
//
//        SessionType sessionType = SessionType.get(req.getSessionType());
//        ChatGPTReq gptReq  = ChatGPTReq.builder()
//                .model(OpenAIConst.MODEL_NAME_CHATGPT_3_5)
//                .messages(ImmutableList.of(new ContextMessage(Role.USER.name, req.getMessage())))
//                .max_tokens(OpenAIConst.MAX_TOKENS - sessionType.maxContextToken)
//                .stream(true)
//                .build();
//
//        // 获取指定的sseEmitter, 将响应信息通过sseEmitter发送出去
//        SseEmitter sseEmitter = sseEmitterMap.get(req.getSseEmitterId());
//        if(sseEmitter == null){
//            return ReturnResult.error();
//        }
//        chatService.streamOneShotChat(req.getUserId(), gptReq, apiKey, sseEmitter);
//
//        // 清除缓存
//        sseEmitterMap.invalidate(req.getSseEmitterId());
//        return ReturnResult.ok();
//    }

    /**
     * 开始游戏
     * @param req
     * @Arthor: oujiajun
     * @return
     */
//    @PostMapping("/chat/game/startGameSession")
//    public ReturnResult startGameSession(@RequestBody @Valid StartGameStreamSessionRequest req) {
//
//        // 若用户上传了apikey则使用用户的，否则采用本系统的
//        UserApiKeyEntity userApiKeyEntity = userApiKeyService.getByUserIdAndType(req.getUserId(), ApiType.OPENAI);
//        String apiKey = userApiKeyEntity != null && !StringUtils.isEmpty(userApiKeyEntity.getApikey())
//                ? userApiKeyEntity.getApikey()
//                : adminApiKeyService.getBestByType(ApiType.OPENAI);
//        if(apiKey == null){
//            return ReturnResult.error().codeAndMessage(ResultCode.ADMIN_APIKEY_NULL);
//        }
//
//        String storyType = req.getStoryType() == null ? "冒险": req.getStoryType();
//        String gameStartPrompt = String.format(promptService.getByTopic(Prompt.GAME_START.topic), storyType);
//
//        SessionType sessionType = SessionType.get(req.getSessionType());
//        ChatGPTReq gptReq  = ChatGPTReq.builder()
//                .model(OpenAIConst.MODEL_NAME_CHATGPT_3_5)
//                .max_tokens(OpenAIConst.MAX_TOKENS - sessionType.maxContextToken)
//                .stream(true)
//                .build();
//
//        // 获取指定的sseEmitter, 将响应信息通过sseEmitter发送出去
//        SseEmitter sseEmitter = sseEmitterMap.get(req.getSseEmitterId());
//        if(sseEmitter == null){
//            return ReturnResult.error();
//        }
//        chatService.streamSessionChat(req.getUserId(), req.getSessionId(), gptReq,
//                gameStartPrompt, apiKey, sseEmitter, sessionType);
//
//        // 清除缓存
//        sseEmitterMap.invalidate(req.getSseEmitterId());
//        return ReturnResult.ok();
//    }
//
//

//    // 定时任务或触发条件下的数据发送
//    @Scheduled(fixedDelay = 1000) // 每秒发送一次数据
//    public void sendDataToClient() {
//        try {
//            // 生成随机数据
//            Random random = new Random();
//            int randomNumber = random.nextInt(100);
//
//            log.info("randomNumber:{}",randomNumber);
//
//            // 发送数据到客户端
//            emitter.send("Random number: " + randomNumber);
//        } catch (IOException e) {
//            e.printStackTrace();
//            // 处理发送异常
//            emitter.completeWithError(e);
//        }
//    }


//    @PostConstruct
//    public void startSendingData() {
////        emitter.
//        Thread thread = new Thread(() -> {
//            try {
//                while (true) {
//                    // 生成随机数据
//                    Random random = new Random();
//                    int randomNumber = random.nextInt(100);
//
//                    log.info("randomNumber:{}",randomNumber);
//                    // 发送数据到客户端
//                    emitter.send("Random number: " + randomNumber);
//
//                    // 暂停一秒
//                    Thread.sleep(1000);
//                }
//            } catch (Exception e) {
//                // 处理发送异常
//                emitter.completeWithError(e);
//            }
//        });
//
//        thread.start();
//    }
}
