package top.starp.util;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Resp {
  public   GeoReq geoReq;
    public  JSONObject resp;
}
