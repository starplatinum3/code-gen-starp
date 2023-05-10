package com.example.demo.util;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@ToString
public class MongoReq {
    JSONObject data;
    String collectionName;
    Map<String,Object> likeMap;
    Map<String,Object>  equalMap;

    String localDoc;
    String otherDoc;
    String localField;
      String foreignField;
}
