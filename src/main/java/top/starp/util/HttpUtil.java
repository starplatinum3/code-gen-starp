package top.starp.util;

//import cn.hutool.json.JSONObject;

import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import com.qingxun.javasdkapi.DefaultOpenApiClient;
import com.qingxun.javasdkapi.OpenApiClient;
import com.qingxun.javasdkapi.request.TextTransRequest;
import com.qingxun.javasdkapi.request.UploadTransRequest;
import com.qingxun.javasdkapi.response.TextTransResponse;
import com.qingxun.javasdkapi.response.UploadTransResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.http.HttpRequest;

//@Slf4j
public class HttpUtil {

    private static String appId = "您的应用id";

    private static String privateKey = "您的密钥";

    private static OpenApiClient defaultOpenApiClient = new DefaultOpenApiClient(appId, privateKey);

    private static String filePath = "您需要上传的文件路径";

    private static String fromLanguage = "文件的源语言";

    private static String toLanguage = "文件的目标语言";


//    d(){
//
//        TextTransRequest textTransRequest = new TextTransRequest.Builder().setFrom("en").setTo("zh").setText("hello").builder();
//        TextTransResponse textTransResponse = defaultOpenApiClient.excute(textTransRequest);
//        String msg = textTransResponse.getMsg();
//
//    }


    public static void main(String[] args) {

        //包装了上传文件的参数
        UploadTransRequest uploadTransRequest = new UploadTransRequest.Builder().setFrom(fromLanguage)
                .setTo(toLanguage).setFile(new File(filePath))
                .builder();



        //包装了上传文件的响应参数 {"code": 100,"data": {"tid": 14587},"msg": "请求成功"}
        UploadTransResponse uploadTransResponse = defaultOpenApiClient.excute(uploadTransRequest);

        //对应响应数据的code
        log.info("code:{}",uploadTransResponse.getApiCode());

        //对应响应数据的msg
        log.info("msg:{}",uploadTransResponse.getApiMsg());

        //其他字段，tid对应data中的tid
        log.info("tid:{}", uploadTransResponse.getTid());

    }

//    d(){
//     String url=   "https://www.fanyigou.com/TranslateApi/api/trans";
//     HttpUtil.postReturnMap(url, map);
//    }

    /**
     *  Map<String, String> params null 可以的
     * @param baseUrl
     * @param params
     * @return
     */
    public static String buildURL(String baseUrl, Map<String, String> params) {
        return  URLBuilder. buildURL(baseUrl,params);
    }

//    public static String buildURL(String baseUrl, Map<?, ?> params) {
//        return  URLBuilder. buildURL(baseUrl,params);
//    }

//    Map<String, Object>    getTryToken( Req req) throws TyrfingMongoException, TyrfingServiceException {
////        Req req = new Req();
////        req.setToken(loginToken);
////        req.setUrl(apiWithToken);
//        String token = req.getToken();
//        if(token==null){
//            try {
//                String tokenByApiSaveDb = getTokenByApiSaveDb();
//                req.setToken(tokenByApiSaveDb);
//                return  HttpUtil.get(req);
//            }catch (Exception e){
//                StringUtils.printException(e);
//                return null;
//            }
//
//        }
//        Map<String, Object> jsonObject = HttpUtil.get(req);
//
//
////        return result;
//
////        Map<String, Object> jsonObject = JsonUtil.toMap(asStr);
////        Integer code = (Integer) jsonObject.get(k.code);
//        Double code = (Double) jsonObject.get(k.code);
//        if (code==200) {
//            return jsonObject;
//        }
//        LogUtil.error("没有授权");
//        LogUtil.error("jsonObject");
//        LogUtil.error(jsonObject+"");
////            throw new
//
//        String tokenByApiSaveDb = getTokenByApiSaveDb();
////            String asStr = HttpRequest.get(apiWithToken)
////                    .header(k.Authorization,tokenByApiSaveDb)
////                    .timeout(20000)//超时，毫秒
////                    .execute().body();
////            Map<String, Object> jsonObject = JsonUtil.toMap(asStr);
////            Req req = new Req();
//        req.setToken(tokenByApiSaveDb);
////            req.setUrl(apiWithToken);
//        return  HttpUtil.get(req);
//
////        if (code!=200) {
////
////        }
//    }

    public static final int timeout = 20000;

    public static Map<String, Object> get(Req req) {
//    cn.hutool.http.HttpRequest
//        Req
        String token = req.getToken();
        String url = req.getUrl();
//        HttpRe

//        String apiWithToken,String  tokenByApiSaveDb
        String asStr = HttpRequest.get(url)
                .header(k.Authorization, token)
                .timeout(timeout)//超时，毫秒
                .execute().body();
        Map<String, Object> jsonObject = JsonUtil.toMap(asStr);
        return jsonObject;
    }

    public static JSONObject get(String url) {
        String result2 = HttpRequest.get(url)
                .timeout(20000)//超时，毫秒
                .execute().body();

        JSONObject entries = JsonUtil.stringToJson(result2);
//        JsonUtil.pa
        return entries;
    }

    public static String getAsStr(String url) {
        String result = HttpRequest.get(url)
                .timeout(20000)//超时，毫秒
                .execute().body();

        return result;

//        JSONObject entries = JsonUtil.stringToJson(result2);
//        JsonUtil.pa
//        return  entries;
    }


    private static final Logger log = LoggerFactory.getLogger("HttpUtil");

    static void checkRes(Map<String, Object> jsonObject) {
        if (!jsonObject.containsKey(k.data)) {
            log.info("api no data");
            return;
        }
        try {
            List<Map<String, Object>> data = (List<Map<String, Object>>) jsonObject.get(k.data);
            int size = data.size();
            log.info(" api res size == {}", size);
            if (size == 0) {
                log.info(" api res size 0 == {}", size);
                return;
            }
            Map<String, Object> map = data.get(0);
            log.info("字段映射");
            StringUtils.printMap(map);

            if (size <= 1) {
                log.info("第一个实体值没有");
                return;
            }
//            Map<String, Object> map = data.get(0);
            log.info("字段值 第一个obj");
            StringUtils.printMap(data.get(1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Map<String, Object>> getData(String url) {
        Map<String, Object> asMap = HttpUtil.getAsMap(url);
        if (!asMap.containsKey(k.data)) {
            return null;
        }
        try {
            List<Map<String, Object>> data = (List<Map<String, Object>>) asMap.get(k.data);
            return data;
        } catch (Exception e) {
            return null;
        }

    }

    public static Map<String, Object> getAsMap(String url) {
        String asStr = HttpUtil.getAsStr(url);
        Map<String, Object> jsonObject = JsonUtil.toMap(asStr);
//        Map<?, ?> map = JsonUtil.toMap(asStr);
        checkRes(jsonObject);
        return jsonObject;
    }


//    public static    com.alibaba.fastjson.JSONObject getAsJson(String url){
////        String result = HttpRequest.get(url)
////                .timeout(20000)//超时，毫秒
////                .execute().body();
////
////        return result;
//        com.alibaba.fastjson.JSONObject jsonObject = JsonUtil.stringToFastJson(getAsStr(url));
//        return   jsonObject;
//
////        JSONObject entries = JsonUtil.stringToJson(result2);
////        JsonUtil.pa
////        return  entries;
//    }
//

    public static String postAsStr(String url, Map<?, ?> params) throws IOException {
//        String urlWithParams = URLBuilder.buildURL(url, params);
        String jsonString = JsonUtil.toJsonString(params);
        String result2 = HttpRequest.post(url)
                .body(jsonString)
                .timeout(20000)//超时，毫秒
                .execute().body();
        return result2;
//        JSONObject entries = JsonUtil.stringToJson(result2);
//        return  entries;
    }

//    public static String postAsStr(String url, Map<String, String> params) throws IOException {
////        String urlWithParams = URLBuilder.buildURL(url, params);
//        String jsonString = JsonUtil.toJsonString(params);
//        String result2 = HttpRequest.post(url)
//                .body(jsonString)
//                .timeout(20000)//超时，毫秒
//                .execute().body();
//        return result2;
////        JSONObject entries = JsonUtil.stringToJson(result2);
////        return  entries;
//    }

    public static String postForm(String url,Map<String ,Object>form
            ,Map<String,String >query,Map<String ,String >header){
//      return   post(url,form,query,header,
//                u.mapOf(
////                     u.p(   k.Content_Type, k.application_json)
//                     u.p(  "Content-Type", "multipart/form-data")
//                ));


//        post("http://localhost:8080/api/1.0/ai/robot/answer",null,null,null,null);


        String urlAll = HttpUtil.buildURL(url,query);
        HttpRequest post = HttpRequest.post(urlAll);
        try(
                HttpResponse execute = post.headerMap(header, true)
                        .header("Content-Type", "multipart/form-data")
//                        .headerMap(addHeader, true)
                        .timeout(20000)//超时，毫秒

                        .form(form)
                        .execute();
        ){
            String body = execute.body();
            return body;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String postJson(String url,Map<String ,Object>form
            ,Map<String,String >query,Map<String ,String >header){
//        return   post(url,form,query,header,
//                u.mapOf(
////                     u.p(   k.Content_Type, k.application_json)
////                        u.p(  "Content-Type", "multipart/form-data")
//                        u.p(  "Content-Type", "application/json")
//                ));

        String urlAll = HttpUtil.buildURL(url,query);
        HttpRequest post = HttpRequest.post(urlAll);
        try(
                HttpResponse execute = post.headerMap(header, true)
//                        .headerMap(addHeader, true)
                        .timeout(20000)//超时，毫秒
//                        .header("Content-Type", "multipart/form-data")
//                        .form(form)
//                        .body()
                        .body(JsonUtil.toJsonString(form))
                        .execute();
        ){
            String body = execute.body();
            return body;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
//        post("http://localhost:8080/api/1.0/ai/robot/answer",null,null,null,null);
    }

   public static String  postForm(String url,Map<String ,Object>form
           ,Map<String,String >query,Map<String ,String >header
   ,Map<String ,String >addHeader){
//        postJson(url,form,query,header);

        String urlAll = HttpUtil.buildURL(url,query);
        HttpRequest post = HttpRequest.post(urlAll);
        try(
                HttpResponse execute = post.headerMap(header, true)
                        .headerMap(addHeader, true)
                        .timeout(20000)//超时，毫秒
//                        .header("Content-Type", "multipart/form-data")
                        .form(form)
                        .execute();
                ){
            String body = execute.body();
            return body;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

//        HttpRequest postRequest = HttpRequest.post(url)
//                .header("Content-Type", "multipart/form-data")
//                .query("knowledge_base_id", knowledgeBaseId)
//                .query("old_doc", oldDocName)
//                .form("file", FileUtil.file(filePath));
    }


    public static void main2(String[] args) {
        String url = "http://your-api-url/local_doc_qa/update_file";
        String knowledgeBaseId = "kb1";
        String oldDocName = "doc_name_1.pdf";
        String filePath = "/path/to/upload/file.pdf";

        String urlAll = HttpUtil.buildURL(url, u.mapOf(
                u.p("knowledge_base_id", knowledgeBaseId)
                , u.p("old_doc", oldDocName)
        ));
//        HttpRequest.post(urlAll)
//                .header("Content-Type", "multipart/form-data")
//                .




//        HttpRequest postRequest = HttpRequest.post(url)
//                .header("Content-Type", "multipart/form-data")
//                .query("knowledge_base_id", knowledgeBaseId)
//                .query("old_doc", oldDocName)
//                .form("file", FileUtil.file(filePath));
//
//        postRequest.headerMap();
//
//        String response = postRequest.execute().body();
//        System.out.println(response);




        HttpUtil.postJson(url
                , u.mapOf(
                u.p("knowledge_base_id", knowledgeBaseId)
                , u.p("old_doc", oldDocName)
                 )
                , u.mapOf(
                        u.p("knowledge_base_id", knowledgeBaseId)
                        , u.p("old_doc", oldDocName)
                )
                ,null);

        String s = HttpReq.HttpReqBuilder.aHttpReq()
                .withUrl(url)
                .withForm(
                u.mapOf(
                        u.p("knowledge_base_id", knowledgeBaseId)
                        , u.p("old_doc", oldDocName)
                )
        ).build().postJson();

        Map<?, ?> post = HttpReq.HttpReqBuilder.aHttpReq()
                .withUrl(url)
                .withForm(
                        u.mapOf(
                                u.p("knowledge_base_id", knowledgeBaseId)
                                , u.p("old_doc", oldDocName)
                        )
                ).build().post();
        HttpReq httpReq = HttpReq.HttpReqBuilder.aHttpReq()
                .withUrl(url)
                .withForm(
                        u.mapOf(
                                u.p("knowledge_base_id", knowledgeBaseId)
                                , u.p("old_doc", oldDocName)
                        )
                ).build();
//        postForm()

//        post.get(k.data);
//
//        Map<?, ?> map = MapUtil.getMap(post, k.data);
////        map.
//        Map<?, ?> map1 = MapUtil.getMap(map, k.data);
    }

    public static Map<String, Object> postReturnMap(String url, Map<?, ?> params) throws IOException {
        String result2 = postAsStr(url, params);
        Map<String, Object> map = JsonUtil.toMap(result2);


//        Map<?, ?> mapNoType = JsonUtil.toMapNoType(result2);
//        Object o = mapNoType.get(k.data);
//        Map<?,?> dabble= (Map<?,?>)  mapNoType.get(k.data);
//        Map<?, ?> map1 = MapUtil.getMap(mapNoType, k.data);



//        dabble.put()
//        String urlWithParams = URLBuilder.buildURL(url, params);
//        String jsonString = JsonUtil.toJsonString(params);
//        String result2 = HttpRequest.post(url)
//                .body(jsonString)
//                .timeout(20000)//超时，毫秒
//                .execute().body();
//        JSONObject entries = JsonUtil.stringToJson(result2);
        return map;
    }

//    public static Map<String, Object> postReturnMap(String url, Map<String, String> params) throws IOException {
//        String result2 = postAsStr(url, params);
//        Map<String, Object> map = JsonUtil.toMap(result2);
////        String urlWithParams = URLBuilder.buildURL(url, params);
////        String jsonString = JsonUtil.toJsonString(params);
////        String result2 = HttpRequest.post(url)
////                .body(jsonString)
////                .timeout(20000)//超时，毫秒
////                .execute().body();
////        JSONObject entries = JsonUtil.stringToJson(result2);
//        return map;
//    }

    public static JSONObject post(String url, Map<String, String> params) throws IOException {
        String result2 = postAsStr(url, params);
//        Map<String, Object> map = JsonUtil.toMap(result2);
//        String urlWithParams = URLBuilder.buildURL(url, params);
//        String jsonString = JsonUtil.toJsonString(params);
//        String result2 = HttpRequest.post(url)
//                .body(jsonString)
//                .timeout(20000)//超时，毫秒
//                .execute().body();
        JSONObject entries = JsonUtil.stringToJson(result2);
        return entries;
    }

    public static JSONObject columnList(Integer modelId) {
        String url = "http://10.61.186.231:8220/maple-cloud-qbsz-data-model/model/column/list";
        Map<String, String> paramMap = new HashMap<>();
//        paramMap.put(k.id,""+342);
        paramMap.put(k.id, "" + modelId);
        JSONObject jsonObject = HttpUtil.get(url, paramMap);
        return jsonObject;
    }


    public static JSONObject columnList(Req req) {
        String url = "http://10.61.186.231:8220/maple-cloud-qbsz-data-model/model/column/list";
        Map<String, String> paramMap = new HashMap<>();
        Integer modelId = req.getModelId();
//        paramMap.put(k.id,""+342);
        paramMap.put(k.id, "" + modelId);
//        JSONObject jsonObject = HttpUtil.get(url, paramMap);
//        Map<String, String> headerMap = new HashMap<>();
//        headerMap.put("Authorization",req.getToken());
        String token = req.getToken();
        String urlWithParams = URLBuilder.buildURL(url, paramMap);
        String result2 = HttpRequest.get(urlWithParams)
                .header(k.Authorization, token)
//                .header("Authorization",token)
                .timeout(20000)//超时，毫秒
                .execute().body();
        JSONObject jsonObject = JsonUtil.stringToJson(result2);
        return jsonObject;

    }

    public static   Map<String, Object>  getMap(String url, Map<String, String> params) {
        String urlWithParams = URLBuilder.buildURL(url, params);
        String result2 = HttpRequest.get(urlWithParams)
//                .header()
                .timeout(20000)//超时，毫秒
                .execute().body();
        Map<String, Object> map = JsonUtil.toMap(result2);
        return map;
//        JSONObject entries = JsonUtil.stringToJson(result2);
//        return entries;
//        implementation 'cn.hutool:hutool-all:5.8.11'
    }

    public static JSONObject get(String url, Map<String, String> params) {
        String urlWithParams = URLBuilder.buildURL(url, params);
        String result2 = HttpRequest.get(urlWithParams)
//                .header()
                .timeout(20000)//超时，毫秒
                .execute().body();
        JSONObject entries = JsonUtil.stringToJson(result2);
        return entries;
//        implementation 'cn.hutool:hutool-all:5.8.11'
    }
}
