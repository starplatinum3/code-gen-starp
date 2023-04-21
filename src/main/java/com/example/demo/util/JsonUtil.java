package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_ENUMS_USING_TO_STRING;

public class JsonUtil {
//    private JSONReader getJsonReader(String srcPath) {
//        JSONReader jsonReader = null;
//        try {
//            FileReader fileReader=new FileReader(
//                    appContext.getCacheDir().toString()+ File.separator + srcPath);
//            jsonReader = new JSONReader(fileReader);
//        } catch (FileNotFoundException e) {
////            Log.d(TAG, "loadTestData: 读取失败");
//            e.printStackTrace();
//        }
//        return jsonReader;
//    }

    static  <T> T jsonObjToJavaClass(JSONObject jsonObject,Class<T>tClass){
        return jsonObject.toJavaObject(tClass);
    }

    public static String getJSONStringFromJavaBean(Class<? extends Object> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuffer stb = new StringBuffer("{");
        for (int i = 0; i < fields.length; i++) {
            try {
                Field field = fields[i];
                field.setAccessible(true);
                String name = field.getName();
                stb.append("\"").append(name).
                        append("\"").
                        append(":\"\"").append(",");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int i = stb.lastIndexOf(",");
        return stb.substring(0,i)+"}";
    }

    public  static  String objectToString(Object object){
        String json = JSONObject.toJSONString(object);
        return json;
    }

    public  static  <T> T stringToObject(String json, Class<T> clazz) {
        if(json == null) {
            return null;
        }
        if(clazz == null) {
            throw new RuntimeException("clazz不能为空");
        }
        try {
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(
                    String.format("JSON字符串转换成对象失败, JSON:[%s], class:[%s]",
                            json, clazz ));
        }
    }

//    public static String getJsonSchema(Class clazz) throws IOException {
////        org.codehaus.jackson.map.ObjectMapper mapper = new ObjectMapper();
//        ObjectMapper mapper = new ObjectMapper();
//        //There are other configuration options you can set.  This is the one I needed.
////        mapper.configure(SerializationConfig.Feature.WRITE_ENUMS_USING_TO_STRING, true);
//        mapper.configure(WRITE_ENUMS_USING_TO_STRING, true);
//
//        JsonSchema schema = mapper.generateJsonSchema(clazz);
//
//        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema);
//    }

    public static void main(String[] args) throws IOException {
//        String jsonSchema = getJsonSchema(PatientInfo.class);
//        System.out.println("jsonSchema");
//        System.out.println(jsonSchema);
//        String jsonStringFromJavaBean = getJSONStringFromJavaBean(NoticeDto.class);
//        System.out.println(jsonStringFromJavaBean);
//        File file = new File();
//        try(Scanner scanner=new Scanner(file)){
//
//        }
//        FileUtil.readResourceFileData()
    }

    public static List<JSONObject> listToPascalCase(List javaObjects){
        List<JSONObject> ret=new ArrayList<>();
        for (Object javaObject : javaObjects) {
            ret.add(toPascalCase(javaObject));
        }
        return ret;
    }

    public static JSONObject toPascalCase(Object javaObject) {
        JSONObject jsonObject = (JSONObject) JSON.toJSON(javaObject);
//        Class<?> aClass = jsonObject.getClass();
//        System.out.println("aClass");
//        System.out.println(aClass);
//        System.out.println("jsonObject");
//        System.out.println(jsonObject);

        JSONObject pascalCase = new JSONObject();
        jsonObject.forEach((key, val) -> {
//            java 第一个字符大写
//            toTitle java
//            to PascalCase
//            StringUtil.ToPascalCase(col.Name)
//            StringUtil
//            StringUtils.Pas
//            pascalCase.put(com.zucc.whatRubbish.util.StrUtil.toPascalCase(key), val);
//            StrUtil.toP
//            com.gm.wj.util.StrUtil
            pascalCase.put(StrUtil.toPascalCase(key), val);
        });

//        System.out.println("pascalCase");
//        System.out.println(pascalCase);
        return pascalCase;
    }


    public static <T> T strToObj(String text, Class<T> clazz) {
//        JSONObject.pa
        return JSONObject.parseObject(text, clazz);
    }

    //    https://blog.csdn.net/u011008029/article/details/51315339
    public static  JSONObject stringToJson(String string){
        string = string.trim();
//        string = string.trim(",");
//        string = string.strip(",");
//        String.st
//        string.stri
//        删掉最后的,
        if(string.endsWith(",")){
            string=string.substring(0,string.length()-1);
        }
        JSONObject json;
        json = JSONObject.parseObject(string);
        return json;
    }


    public static String getJsonSchema(Class clazz) throws IOException {
//        org.codehaus.jackson.map.ObjectMapper mapper = new ObjectMapper();
        ObjectMapper mapper = new ObjectMapper();
        //There are other configuration options you can set.  This is the one I needed.
//        mapper.configure(SerializationConfig.Feature.WRITE_ENUMS_USING_TO_STRING, true);
        mapper.configure(WRITE_ENUMS_USING_TO_STRING, true);

        JsonSchema schema = mapper.generateJsonSchema(clazz);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema);
    }
//
//    public static void main(String[] args) throws IOException {
////        String jsonSchema = getJsonSchema(PatientInfo.class);
////        System.out.println("jsonSchema");
////        System.out.println(jsonSchema);
//    }

//    public static List<JSONObject> listToPascalCase(List javaObjects){
//        List<JSONObject> ret=new ArrayList<>();
//        for (Object javaObject : javaObjects) {
//            ret.add(toPascalCase(javaObject));
//        }
//        return ret;
//    }

//    public static JSONObject toPascalCase(Object javaObject) {
//        JSONObject jsonObject = (JSONObject) JSON.toJSON(javaObject);
////        Class<?> aClass = jsonObject.getClass();
////        System.out.println("aClass");
////        System.out.println(aClass);
////        System.out.println("jsonObject");
////        System.out.println(jsonObject);
//
//        JSONObject pascalCase = new JSONObject();
//        jsonObject.forEach((key, val) -> {
////            java 第一个字符大写
////            toTitle java
////            to PascalCase
////            StringUtil.ToPascalCase(col.Name)
////            StringUtil
////            StringUtils.Pas
////            pascalCase.put(com.zucc.whatRubbish.util.StrUtil.toPascalCase(key), val);
//            pascalCase.put(StrUtil.toPascalCase(key), val);
//        });
//
////        System.out.println("pascalCase");
////        System.out.println(pascalCase);
//        return pascalCase;
//    }

}
