package top.starp.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

import java.util.Map;

/**
 *      Map<?, ?> post = HttpReq.HttpReqBuilder.aHttpReq()
 *                 .withUrl(url)
 *                 .withForm(
 *                         u.mapOf(
 *                                 u.p("knowledge_base_id", knowledgeBaseId)
 *                                 , u.p("old_doc", oldDocName)
 *                         )
 *                 ).build().post();
 */
public class HttpReq {
    String url;
    Map<String, Object> form;
    Map<String, String> query;
    Map<String, String> header;
    public static final int timeout = 20000;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getForm() {
        return form;
    }

    public void setForm(Map<String, Object> form) {
        this.form = form;
    }

    public Map<String, String> getQuery() {
        return query;
    }

    public void setQuery(Map<String, String> query) {
        this.query = query;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    /**
     *      Map<?, ?> post = HttpReq.HttpReqBuilder.aHttpReq()
     *                 .withUrl(url)
     *                 .withForm(
     *                         u.mapOf(
     *                                 u.p("knowledge_base_id", knowledgeBaseId)
     *                                 , u.p("old_doc", oldDocName)
     *                         )
     *                 ).build().post();
     * @return
     */
    public  Map<?, ?>  post() {
        String jsonStr = postJson();
        Map<?, ?> mapNoType = JsonUtil.toMapNoType(jsonStr);
        return mapNoType;
//        String urlAll = HttpUtil.buildURL(url, query);
//        cn.hutool.http.HttpRequest post = HttpRequest.post(urlAll);
//        post.timeout(timeout);
//        if (header != null) {
//            post.headerMap(header, true);
//        }
////        if(query!=null){
////            post.headerMap(query, true);
////        }
//        if (null == form) {
//            form = u.mapOf();
//        }
////        post  .body(JsonUtil.toJsonString(form))
//        try (
//
//                HttpResponse execute = post
//                        .body(JsonUtil.toJsonString(form))
//                        .execute();
//        ) {
//            String body = execute.body();
//            return body;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
    }
    public  Map<?, ?>   postForm(){
        String resStr = postFormReturnStr();
        Map<?, ?> mapNoType = JsonUtil.toMapNoType(resStr);
        return mapNoType;
    }

    HttpRequest initPost(){
        if (urlBase!=null) {
            url=urlBase+url;
        }
        String urlAll = HttpUtil.buildURL(url,query);
        HttpRequest post = HttpRequest.post(urlAll);
        post.timeout(timeout);
        if (header != null) {
            post.headerMap(header, true);
        }
        if (null == form) {
            form = u.mapOf();
        }
        return post;
    }
    public  String  postFormReturnStr(){
//        postJson(url,form,query,header);

//        if (urlBase!=null) {
//            url=urlBase+url;
//        }
//        String urlAll = HttpUtil.buildURL(url,query);
//        HttpRequest post = HttpRequest.post(urlAll);
//        post.timeout(timeout);
//        if (header != null) {
//            post.headerMap(header, true);
//        }
//        if (null == form) {
//            form = u.mapOf();
//        }


        HttpRequest post = initPost();
        try(
                HttpResponse execute = post
                        .header("Content-Type", "multipart/form-data")
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

    public static void main(String[] args) {
        Map<?, ?> post = HttpReqBuilder.aHttpReq().withForm(u.mapOf()).build().post();
        String s = HttpReqBuilder.aHttpReq().withForm(u.mapOf()).build().postJson();
    }

//    public static String  postForm(String url,Map<String ,Object>form
//            ,Map<String,String >query,Map<String ,String >header
//            ,Map<String ,String >addHeader){
////        postJson(url,form,query,header);
//
//        String urlAll = HttpUtil.buildURL(url,query);
//        HttpRequest post = HttpRequest.post(urlAll);
//        try(
//                HttpResponse execute = post.headerMap(header, true)
//                        .headerMap(addHeader, true)
//                        .timeout(20000)//超时，毫秒
////                        .header("Content-Type", "multipart/form-data")
//                        .form(form)
//                        .execute();
//        ){
//            String body = execute.body();
//            return body;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//
////        HttpRequest postRequest = HttpRequest.post(url)
////                .header("Content-Type", "multipart/form-data")
////                .query("knowledge_base_id", knowledgeBaseId)
////                .query("old_doc", oldDocName)
////                .form("file", FileUtil.file(filePath));
//    }
    public String postJson() {


//        if (urlBase!=null) {
//            url=urlBase+url;
//        }
//        String urlAll = HttpUtil.buildURL(url, query);
//        cn.hutool.http.HttpRequest post = HttpRequest.post(urlAll);
//        post.timeout(timeout);
//        if (header != null) {
//            post.headerMap(header, true);
//        }
////        if(query!=null){
////            post.headerMap(query, true);
////        }
//        if (null == form) {
//            form = u.mapOf();
//        }



        HttpRequest post = initPost();
//        post  .body(JsonUtil.toJsonString(form))
//        HttpRequest body1 = post.body(JsonUtil.toJsonString(form));
        try (

                HttpResponse execute = post
                        .body(JsonUtil.toJsonString(form))
                        .execute();
        ) {
            String body = execute.body();
            return body;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static String postJson(String url, Map<String, Object> form
//            , Map<String, String> query, Map<String, String> header) {
////        return   post(url,form,query,header,
////                u.mapOf(
//////                     u.p(   k.Content_Type, k.application_json)
//////                        u.p(  "Content-Type", "multipart/form-data")
////                        u.p(  "Content-Type", "application/json")
////                ));
//
//
//        String urlAll = HttpUtil.buildURL(url, query);
//        cn.hutool.http.HttpRequest post = HttpRequest.post(urlAll);
//        try (
//                HttpResponse execute = post.headerMap(header, true)
////                        .headerMap(addHeader, true)
//                        .timeout(20000)//超时，毫秒
////                        .header("Content-Type", "multipart/form-data")
////                        .form(form)
////                        .body()
//                        .body(JsonUtil.toJsonString(form))
//                        .execute();
//        ) {
//            String body = execute.body();
//            return body;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
////        post("http://localhost:8080/api/1.0/ai/robot/answer",null,null,null,null);
//    }

    String urlBase;

    public String getUrlBase() {
        return urlBase;
    }

    public void setUrlBase(String urlBase) {
        this.urlBase = urlBase;
    }

    public static final class HttpReqBuilder {
        private String url;
        private Map<String, Object> form;
        private Map<String, String> query;
        private Map<String, String> header;
        private int timeout;
        private String urlBase;

        private HttpReqBuilder() {
        }

        public static HttpReqBuilder aHttpReq() {
            return new HttpReqBuilder();
        }

        public HttpReqBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

        public HttpReqBuilder withForm(Map<String, Object> form) {
            this.form = form;
            return this;
        }

        public HttpReqBuilder withQuery(Map<String, String> query) {
            this.query = query;
            return this;
        }

        public HttpReqBuilder withHeader(Map<String, String> header) {
            this.header = header;
            return this;
        }

        public HttpReqBuilder withTimeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public HttpReqBuilder withUrlBase(String urlBase) {
            this.urlBase = urlBase;
            return this;
        }

        public HttpReq build() {
            HttpReq httpReq = new HttpReq();
            httpReq.setUrl(url);
            httpReq.setForm(form);
            httpReq.setQuery(query);
            httpReq.setHeader(header);
            httpReq.setUrlBase(urlBase);
//            httpReq.timeout = this.timeout;
            return httpReq;
        }
    }
}
