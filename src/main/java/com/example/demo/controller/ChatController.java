//package com.example.demo.controller;
//
////import com.gungnir.campus.entity.AskTrainVo;
//
////import com.gungnir.integration.dto.AskTrainVo;
////import com.gungnir.integration.util.JsonUtil;
////import okhttp3.*;
//import com.example.demo.dto.AskTrainVo;
//import com.lark.oapi.okhttp.*;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//import top.starp.util.JsonUtil;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//public class ChatController {
//
//    String bodyGet(AskTrainVo askTrain) throws IOException {
//        String askText = askTrain.getAskText();
//        Map<String, Object> params = new HashMap<>();
////        params.put()
////        params.put(k.model,"chatglm2-6b");
//        params.put("model", "chatglm2-6b");
//        Boolean streamOrDefault = askTrain.getStreamOrDefault();
//
////        params.put(k.stream,streamOrDefault);
//        params.put("stream", true);
////        params.put(k.stream,true);
////        top.starp.util.MapUtil.
//        Map<String, String> message = new HashMap<>();
////        message.put(k.role,"user");
////        message.put(k.content,askText);
//
//        message.put("role", "user");
//        message.put("content", askText);
//
////       stream
//        List<Map<String, String>> list = new ArrayList<>();
//        list.add(message);
////       List<Map<String, String>> list = ListUtil.createList(message);
//
////        params.put(k.messages,list);
//        params.put("messages", list);
////          messages=[
////            {"role": "user", "content":content}
////        ],
//
////        {
////            "model": "gpt-3.5-turbo",
////                "messages": [{"role": "user", "content": "Hello!"}]
////        }
////        HttpUtil.post(chatUrl,params)
//
////       toJsonString(params);
//
////       JsonUtil
////       Json
//        String jsonString = JsonUtil.toJsonString(params);
//        return jsonString;
//    }
//
////    public static String toJsonString(Map<String, Object> map) {
////        Gson gson = new Gson();
////        return gson.toJson(map);
////
////    }
//
//    @Value("${chatHost}")
//    String chatHost;
//
//    @Value("${chat-uri}")
//    String chatUri;
//
//    /**
//     * @api {post} /chat/stream streamData
//     * @apiName streamData
//     * @apiGroup chatService
//     * @apiDescription 无
//     * @apiParam {String} askText
//     * @apiParamExample {json} Request
//     * {
//     *       "askText":"Java 怎么学"
//     *  }
//     * @apiSuccess {Map} data
//     * @apiSuccessExample {json} Response
//     * {
//     *   "code": 200,
//     *   "msg": "success",
//     *   "data": {}
//     * }
//     */
//    @PostMapping("/chat/stream")
//    public SseEmitter streamData(@org.springframework.web.bind.annotation.RequestBody AskTrainVo askTrainVo) throws IOException {
//        //    @GetMapping("/chat/stream")
//
//
//        // 创建SseEmitter对象
//        SseEmitter emitter = new SseEmitter();
//        OkHttpClient client = new OkHttpClient();
//
////        String chatBaseUrl=  "http://10.66.10.235:8000/v1";
////        String chatBaseUrl=  "http://{chatHost}:8000/v1".replace("{chatHost}",chatHost);
//        String chatBaseUrl = "http://{chatUri}/v1".replace("{chatUri}", chatUri);
//        String chatPath = "/chat/completions";
//        String chatUrl = chatBaseUrl + chatPath;
//
//        String bodyStr = bodyGet(askTrainVo);
//// 创建POST请求体，可以根据需要设置请求参数
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
//                bodyStr);
//
//
//        Request request = new Request.Builder()
//                .url(chatUrl)  // 替换为你的API URL
//                .post(requestBody)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            ResponseBody responseBody = response.body();
////            System.out.println("Request was not successful. Status code: " + response.code());
////            if (responseBody != null) {
//////                System.out.println("Response body: " + responseBody.string());
////            }
//
//            int code = response.code();
//            System.out.println("Request was not successful. Status code: " + code);
//
//            if (responseBody != null) {
//                if (code != 200) {
//                    String string = responseBody.string();
//                    System.out.println("Response body err : " + string);
//                    Map<String, Object> logMap = new HashMap<>();
//                    logMap.put("msg", "Response body err : " + string);
//                    logMap.put("code", 500);
//                    logMap.put("responseBody", responseBody);
//                    // 处理请求失败的情况
//                    String jsonString = JsonUtil.toJsonString(logMap);
////                    emitter.completeWithError(new Exception(logMap.toString()));
//                    emitter.completeWithError(new Exception(jsonString));
//                }
////                System.out.println("Response body: " + responseBody.string());
//            }
//
//            if (response.isSuccessful() && responseBody != null) {
//                // 获取响应的输入流
//                InputStream inputStream = responseBody.byteStream();
//
//                // 读取输入流的数据并通过SseEmitter发送给客户端
////                byte[] buffer = new byte[8192];
//                int bytesRead;
////                while ((bytesRead = inputStream.read(buffer)) != -1) {
////                    emitter.send(buffer, 0, bytesRead);
////                }
//
//                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        // 将每行数据发送给客户端
//                        String clearLine = line.replace("data:", "").trim();
//                        emitter.send(clearLine);
//                    }
//                }
//
//                // 发送完成信号给客户端
//                emitter.complete();
//            } else {
//                // 处理请求失败的情况
//                emitter.completeWithError(new Exception("Request failed"));
//            }
//        } catch (IOException e) {
//            // 处理IO异常
//            emitter.completeWithError(e);
//        }
//
//        // 在这里执行OkHttp请求并返回SseEmitter对象
//        return emitter;
//    }
//}
