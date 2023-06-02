package top.starp.util;
//import com.jplus.common.amap.MapNavUtil;
public class TestRequest {
    public static void main(String[] args) {
        String origin="104.07,30.67";//出发点经纬度
        String destination="104.46,29.23";//目的地经纬度
        String key="2ace948ef129************b0774750";//高德用户key
        MapNavUtil mapResult=new MapNavUtil(origin, destination, key);
        System.out.println(mapResult.getResults().toString());
        System.out.println(mapResult.getResults().toString());
        System.out.println("mapResult.walking()");
        System.out.println(mapResult.walking());
    }
}