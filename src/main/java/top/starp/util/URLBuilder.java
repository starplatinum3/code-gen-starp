package top.starp.util;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class URLBuilder {

    public static String buildURL(String baseUrl, Map<String, String> params) {
//      return   buildURL(baseUrl,"/",params);
      return   buildURL(baseUrl,"",params);

    }
    public static String buildURL(String baseUrl, String path, Map<String, String> params) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        urlBuilder.append(path);

        if (params != null && !params.isEmpty()) {
            urlBuilder.append("?");

            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if (key != null && value != null) {
                    try {
                        urlBuilder.append(URLEncoder.encode(key, "UTF-8"));
                        urlBuilder.append("=");
                        urlBuilder.append(URLEncoder.encode(value, "UTF-8"));
                        urlBuilder.append("&");
                    } catch (Exception e) {
                        // 处理异常
                    }
                }
            }

            // 删除末尾的"&"
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }

        return urlBuilder.toString();
    }

    public static void main(String[] args) {
        String baseUrl = "https://example.com/api";
//        String path = "/users";
        String path = "users";
//        String path = "";
//        Map.
//        HashMap
//        Map<String, String> params = Map.of(
//                "name", "John Doe",
//                "age", "30",
//                "city", "New York"
//        );

        Map<String, String> params = new HashMap<>();
//        params.put("name", "John Doe");
//        params.put("age", "30");
//        params.put("city", "New York");
//        https://example.com/api/users?city=New+York&name=John+Doe&age=30

        String url = buildURL(baseUrl, path, params);
        System.out.println(url);
    }
}
