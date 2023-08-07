package top.starp.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeoReq {
  public   String origin="104.07,30.67";//出发点经纬度
    public  String destination="104.46,29.23";//目的地经纬度
    public  String address="浙大城市学院";
    public    String city="杭州";
    public  String force="false";
    public  String applicationKey;
    public String keywords="浙大城市学院";
    public String types="公共厕所";
    public String extensions="all";
//    String offset;
//    String page;

    public Integer offset=20;
    public   Integer page=1;

    public static void main(String[] args) {
        GeoReq geoReq = new GeoReq();
        String place_text = geoReq.place_text();
        System.out.println("place_text");
        System.out.println(place_text);
//        https://restapi.amap.com/v3/place/text?extensions=all&keywords=%E6%B5%99%E5%A4%A7%E5%9F%8E%E5%B8%82%E5%AD%A6%E9%99%A2&offset=20&city=%E6%9D%AD%E5%B7%9E&page=1
    }
   public  String  place_text(){

       Map<String, String> params = new HashMap<>();
//        params.put("name", "John Doe");
//        params.put("age", "30");
//        params.put("city", "New York");
               params.put("keywords", keywords);
        params.put("city", city);
        params.put("offset", ""+offset);
        params.put("page", ""+page);
        params.put("key", applicationKey);
        params.put("extensions",extensions);
//        params.put("city", "New York");
//        https://example.com/api/users?city=New+York&name=John+Doe&age=30
       String url = URLBuilder.buildURL("https://restapi.amap.com/v3/place/text", params);

//       HttpUtil
//       java 拼接 url 参数 路径
//       String tpl=
//    "https://restapi.amap.com/v3/place/text?keywords={keywords}&city={city}&offset={offset}&page={page}&key={key}&extensions=all";
//
//       String url=tpl  .replace("{address}",address)
//                        .replace("{key}",applicationKey)
//                        .replace("{keywords}",keywords)
//                        .replace("{city}",city)
//                        .replace("{offset}",""+offset)
//                        .replace("{page}",""+page)
//                ;
        return url;
    }
   public String assistant_input_tips(){
        Map<String, String> params = new HashMap<>();
//        params.put("name", "John Doe");
//        params.put("age", "30");
//        params.put("city", "New York");
        params.put("keywords", keywords);
        params.put("city", city);
        params.put("key", applicationKey);



//        params.put("extensions",extensions);
//        params.put("offset", ""+offset);
//        params.put("page", ""+page);
//        params.put("city", "New York");
//        https://example.com/api/users?city=New+York&name=John+Doe&age=30
        String url = URLBuilder.buildURL("https://restapi.amap.com/v3/assistant/inputtips", params);
return url;
    }
}
