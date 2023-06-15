package top.starp.util;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//import com.jplus.common.http.HttpRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * 高德地图导航工具类
 * @开发者 tiddler
 * @编写时间 2018年8月14日 上午10:01:05
 * @文件名 MapNavUtil.java
 * @类名 MapNavUtil
 */
@Slf4j
public class MapNavUtil {
    private String startCoordinate;
    private String endCoordinate;
    private String applicationKey;
    private String param;
    /**
     * 必须要构造参数
     * @param startCoordinate 起点经纬度 经度在前，纬度在后
     * @param endCoordinate 终点经纬度 经度在前，纬度在后
     * @param applicationKey 高德地图应用key，需要Web服务类型的key
     */
    public MapNavUtil(String startCoordinate, String endCoordinate,
            String applicationKey) {
        this.startCoordinate = startCoordinate;
        this.endCoordinate = endCoordinate;
        this.applicationKey = applicationKey;
        this.param="origin="+this.startCoordinate+"&destination="+this.endCoordinate+"&key="+this.applicationKey;
    }
    /**
     * 获取地图导航返回值
     * @return
     */
    public MapNavResults getResults(){
        String sendGet = HttpRequest.sendGet("https://restapi.amap.com/v3/direction/driving", param);
//        JSONObject jsonObject=JSONObject.fromObject(sendGet);
        JSONObject jsonObject = JsonUtil.stringToJson(sendGet);
        String routeJsonString = jsonObject.get("route").toString();
//        JSONObject routeObject=JSONObject.fromObject(routeJsonString);
        JSONObject routeObject = JsonUtil.stringToJson(routeJsonString);
        JSONArray jsonArray = routeObject.getJSONArray("paths");
        JSONObject zuiJson = jsonArray.getJSONObject(0);
        MapNavResults mapResult=new MapNavResults();
        mapResult.setDistance(zuiJson.get("distance").toString());
        mapResult.setDuration(zuiJson.get("duration").toString());
        mapResult.setTolls(zuiJson.get("tolls").toString());
        return mapResult;
    }


    public static JSONObject geocode_geo(String address,String applicationKey){
       String url= "https://restapi.amap.com/v3/geocode/geo?address={address}&key={key}"
                .replace("{address}",address).replace("{key}",applicationKey);

        JSONObject jsonObject = HttpRequest.get(url);
        return jsonObject;
    }

//    public static JSONObject place_text(GeoReq geoReq,String applicationKey){
//        String address = geoReq.getAddress();
//        String url=
//                "https://restapi.amap.com/v3/place/text?keywords={keywords}&city={city}&offset={offset}&page={page}&key={key}&extensions=all"
//                        .replace("{address}",address)
//                        .replace("{key}",applicationKey)
//                        .replace("{keywords}",keywords)
//                        .replace("{city}",city)
//                ;
//
//
//        JSONObject jsonObject = HttpRequest.get(url);
//        return jsonObject;
//    }

    public static JSONObject walking(GeoReq geoReq,String applicationKey){
//        String address,
//        "origin="+this.startCoordinate+"&destination="+this.endCoordinate+"&key="+this.applicationKey;
        String walking = "https://restapi.amap.com/v3/direction/{method}?origin={origin}&destination={destination}&key={key}"
                .replace("{method}", "walking")
                .replace("{origin}", geoReq.getOrigin())
                .replace("{destination}", geoReq.getDestination())
                .replace("{key}",applicationKey)
                ;
        log.info("walking url {}",walking);
//        String address = geoReq.getAddress();
//        String url= "https://restapi.amap.com/v3/geocode/geo?address={address}&key={key}"
//                .replace("{address}",address).replace("{key}",applicationKey);

        JSONObject jsonObject = HttpRequest.get(walking);
        return jsonObject;
    }

    public static JSONObject geocode_geo(GeoReq geoReq,String applicationKey){
//        String address,
        String address = geoReq.getAddress();
        String city = geoReq.getCity();
        String url= "https://restapi.amap.com/v3/geocode/geo?address={address}&key={key}&city={city}"
                .replace("{address}",address)
                .replace("{key}",applicationKey)
                .replace("{city}",city)
                ;

        log.info("geocode_geo url {}",url);
        JSONObject jsonObject = HttpRequest.get(url);
        return jsonObject;
    }
    public MapNavResults driving(){
//        https://restapi.amap.com/v3/geocode/geo?address=北京市朝阳区阜通东大街6号&output=XML&key=<用户的key>

//
        return   getResults("https://restapi.amap.com/v3/direction/{method}"
                .replace("{method}","driving"));
    }

    public MapNavResults walking(){
//        String walkingUrl = "https://restapi.amap.com/v3/direction/{method}"
//                .replace("{method}", "walking");
//        JSONObject jsonObject = HttpRequest.get(walkingUrl, param);
//        return  jsonObject;
//        HttpRequest.get()
        return   getResults("https://restapi.amap.com/v3/direction/{method}"
                .replace("{method}","walking"));
    }

    public JSONObject walkingReq(){
//        duration
//
//                步行时间预计

//        单位：秒
        String walkingUrl = "https://restapi.amap.com/v3/direction/{method}"
                .replace("{method}", "walking");
        JSONObject jsonObject = HttpRequest.get(walkingUrl, param);
        return  jsonObject;
//        HttpRequest.get()
//        return   getResults("https://restapi.amap.com/v3/direction/{method}"
//                .replace("{method}","walking"));
    }

    public MapNavResults getResults(String url){
//        String sendGet = HttpRequest.sendGet("https://restapi.amap.com/v3/direction/driving", param);
        String sendGet = HttpRequest.sendGet(url, param);
//        JSONObject jsonObject=JSONObject.fromObject(sendGet);
        JSONObject jsonObject = JsonUtil.stringToJson(sendGet);
        String routeJsonString = jsonObject.get("route").toString();
//        JSONObject routeObject=JSONObject.fromObject(routeJsonString);
        JSONObject routeObject = JsonUtil.stringToJson(routeJsonString);
        JSONArray jsonArray = routeObject.getJSONArray("paths");
        JSONObject zuiJson = jsonArray.getJSONObject(0);
        MapNavResults mapResult=new MapNavResults();
        mapResult.setDistance(zuiJson.get("distance").toString());
        mapResult.setDuration(zuiJson.get("duration").toString());
        mapResult.setTolls(zuiJson.get("tolls").toString());
        return mapResult;
    }
}