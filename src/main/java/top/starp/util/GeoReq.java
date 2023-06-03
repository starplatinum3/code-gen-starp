package top.starp.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeoReq {
    String origin="104.07,30.67";//出发点经纬度
    String destination="104.46,29.23";//目的地经纬度
    String address;
    String city;
    String force;
}
