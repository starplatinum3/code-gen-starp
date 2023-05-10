package com.example.demo.util;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Map;

@Data
public class MongoReq {
    JSONObject data;
    String collectionName;
    Map<String,Object> likeMap;
    Map<String,Object>  equalMap;
}
