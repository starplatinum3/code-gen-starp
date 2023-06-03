package top.starp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalkTwo {

    String formatted_address;

    Object geocode_geo;
    JSONObject walking智慧;
    JSONObject walkingZUCC;
    String location;
    Double minZucc ;
    Double min智慧 ;
   public static Double getMin(  JSONObject walkingZUCC){
        JSONObject route = walkingZUCC.getJSONObject("route");
//                paths
        JSONArray paths = route.getJSONArray("paths");
        JSONObject path0 = paths.getJSONObject(0);
        Integer duration = path0.getInteger("duration");
//                秒数 抓话
//                duration/60 分钟
       return duration*1.0/60;
    }

   public void minuteSet(){
         minZucc = getMin(walkingZUCC);
         min智慧 = getMin(walking智慧);
//        minZucc
    }
}
