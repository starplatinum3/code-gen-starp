package top.starp.util;

import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class MapUtil {

    public static <T> void modifyMapList(List<Map<String, Object>> mapList, MapFunction<T> func, T... args) {
        for (Map<String, Object> map : mapList) {
            func.apply(map, args);
        }
    }

    public interface MapFunction<T> {
        void apply(Map<String, Object> map, T... args);
    }

    // 示例函数：修改Map中的某个键值对
    public static void modifyKeyValue(Map<String, Object> map, String key, Object value) {
        map.put(key, value);
    }

//    public static void main(String[] args) {
//        List<Map<String, Object>> mapList = new ArrayList<>();
//
//        // 添加一些示例数据
//        Map<String, Object> map1 = new HashMap<>();
//        map1.put("name", "John");
//        map1.put("age", 25);
//        mapList.add(map1);
//
//        Map<String, Object> map2 = new HashMap<>();
//        map2.put("name", "Jane");
//        map2.put("age", 30);
//        mapList.add(map2);
//
//        // 调用modifyMapList并传递函数和参数
//        MapUtil.   modifyMapList(mapList, MapUtil::modifyKeyValue, "name", (Object)"Alice");
//
//       MapUtil. modifyMapList(mapList,MapUtil::modifyKeyValue,"");
////        MapUtil. modifyMapList(mapList, new MapFunction<Object>() {
////        })
//        // 打印修改后的结果
//        for (Map<String, Object> map : mapList) {
//            System.out.println(map);
//        }
//    }

    public static Integer getInteger(Map map,String  key,Integer defaultVal) {
        String string = getString(map, key,""+defaultVal);
        Integer integer = Integer.valueOf(string);
        return integer;
    }

    public static Integer getInteger(Map map,Character  key){
        Object val = map.get(key);
        if(val instanceof  Integer){
            return (Integer) val;
        }
        String string = getString(map, key);
        Integer integer = Integer.valueOf(string);
        return integer;
    }
    public static Integer getInteger(Map map,String  key){
        String string = getString(map, key);
//        int port = Integer.parseInt(string);
        Integer integer = Integer.valueOf(string);
        return integer;
//        Object val = map.get(key);
//        if(   val instanceof Integer){
//            map.get(key);
//            int port = Integer.parseInt(portStr);
//            Integer integer = (Integer) map.get(key);
//            return integer;
//        }
//        Map<String ,Object> logMap=new HashMap<>();
//        logMap.put(k.msg,"getInteger err");
//        logMap.put(k.map,map);
//        logMap.put(k.key,key);
//        logMap.put(k.type,"getInteger");
//        log.error(logMap);
//        String mapStr = StringUtils.getMapStr(logMap);
//        throw new RuntimeException(mapStr);
    }

//    public static Integer getInteger(Map map,String  key){
//        Object val = map.get(key);
//        if(   val instanceof Integer){
//            map.get(key);
//            int port = Integer.parseInt(portStr);
//            Integer integer = (Integer) map.get(key);
//            return integer;
//        }
//        Map<String ,Object> logMap=new HashMap<>();
//        logMap.put(k.msg,"getInteger err");
//        logMap.put(k.map,map);
//        logMap.put(k.key,key);
//        logMap.put(k.type,"getInteger");
//        log.error(logMap);
//        String mapStr = StringUtils.getMapStr(logMap);
//        throw new RuntimeException(mapStr);
//    }

//    public static String getString(Map map,String  key,String defaultVal){
////        getString()
//        Object val = map.get(key);
////        if(   val instanceof Integer){
////            Integer integer = (Integer) map.get(key);
////            return integer;
////        }
//        if(   val instanceof String){
//            return (String) map.get(key);
////            return integer;
//        }
//        return  defaultVal;
////        Map<String ,Object> logMap=new HashMap<>();
////        logMap.put(k.msg,"getInteger err");
////        logMap.put(k.map,map);
////        logMap.put(k.key,key);
////        logMap.put(k.type,"getInteger");
////        log.error(logMap);
////        String mapStr = StringUtils.getMapStr(logMap);
////        throw new RuntimeException(mapStr);
//    }

    public static String getString(Map map,Character  key){

        Map<String ,Object> logMap=new HashMap<>();
        logMap.put(k.msg,"String getString(Map map,Character key :   err");
        logMap.put(k.map,map);
        logMap.put(k.key,key);
        logMap.put(k.keyType,"Character");
        logMap.put(k.type,"no key");
        String mapStr = StringUtils.getMapStr(logMap);

//        key
        if (!map.containsKey(key)) {
            throw new RuntimeException(mapStr);
//            throw new RuntimeException( logMap.toString());
//            throw new RuntimeException(logMap);
        }
        Object val = map.get(key);
        if(   val instanceof String){
            return (String) map.get(key);
        }

//        log.error(logMap);
        logMap.put(k.type,"no key");
        throw new RuntimeException(StringUtils.getMapStr(logMap));
    }


    public static String getString(Map map,String  key){
//        key
        if (!map.containsKey(key)) {
            throw new RuntimeException("no key");
        }
        Object val = map.get(key);
//        if(   val instanceof Integer){
//            Integer integer = (Integer) map.get(key);
//            return integer;
//        }
        if(   val instanceof String){
            return (String) map.get(key);
//            return integer;
        }
        Map<String ,Object> logMap=new HashMap<>();
        logMap.put(k.msg,"getInteger err");
        logMap.put(k.map,map);
        logMap.put(k.key,key);
        logMap.put(k.type,"getInteger");
        log.error(logMap);
        String mapStr = StringUtils.getMapStr(logMap);
        throw new RuntimeException(mapStr);
    }


    public static String getString(Map map,String  key,String defaultVal){
        try {
            String string = getString(map, key);
            return string;
        }catch (Exception e){
            log.info("no key go defaultVal "+defaultVal);
//            StringUtils.printException(e);
            return defaultVal;
        }

//
////        key
//        if (!map.containsKey(key)) {
//            return defaultVal;
//        }
//        Object val = map.get(key);
////        if(   val instanceof Integer){
////            Integer integer = (Integer) map.get(key);
////            return integer;
////        }
//        if(   val instanceof String){
//            return (String) map.get(key);
////            return integer;
//        }
//        Map<String ,Object> logMap=new HashMap<>();
//        logMap.put(k.msg,"getInteger err");
//        logMap.put(k.map,map);
//        logMap.put(k.key,key);
//        logMap.put(k.type,"getInteger");
//        log.error(logMap);
//        String mapStr = StringUtils.getMapStr(logMap);
//        throw new RuntimeException(mapStr);
    }


    public static void main(String[] args) {
        List<Map<String, Integer>> listMap = new ArrayList<>();

//        log.info();
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("A", 1);
        map1.put("B", 2);
        listMap.add(map1);

        Map<String, Integer> map2 = new HashMap<>();
        map2.put("C", 3);
        map2.put("D", 4);
        listMap.add(map2);

        // 定义一个操作，输出Map中的键值对
        BiConsumer<Map<String, Integer>, Object[]> printKeyValuePairs = (map, params) -> {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        };

        // 定义一个操作，计算Map中的值的总和
        BiConsumer<Map<String, Integer>, Object[]> calculateSum = (map, params) -> {
            int sum = 0;
            for (int value : map.values()) {
                sum += value;
            }
            System.out.println("Sum: " + sum);
        };

        // 对列表中的每个Map执行输出键值对的操作
        MapUtil.operateOnListMap(listMap, printKeyValuePairs);

        // 对列表中的每个Map执行计算值总和的操作
        MapUtil.operateOnListMap(listMap, calculateSum);

        MapUtil.operateOnListMap(listMap,(map,params)->{
//            map.get()
            MapUtil.putNewKey(map,new HashMap<>());
        });
    }

    public static <K, V> void operateOnListMap(List<Map<K, V>> listMap
            , BiConsumer<Map<K, V>, Object[]> operation, Object... args) {
        for (Map<K, V> map : listMap) {
            operation.accept(map, args);
        }
    }

   public static String  getToVal(String originVal, List<Document>mappingConf ){
        List<Document> collect = mappingConf.stream().filter(o -> {
            String fromVal = o.getString(k.fromVal);
            return originVal.equals(fromVal);
        }).collect(Collectors.toList());
        if (collect.size()==0) {
            return  originVal;
        }
        Document document = collect.get(0);
        String toVal = document.getString(k.toVal);
        return  toVal;
    }

    public static void main_replaceValues(String[] args) {
        // 创建示例列表（List）和Map
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("key1", "general");
        map1.put("key2", "emphasis");
        list.add(map1);

        // 输出替换前的列表（List）
        System.out.println("替换前的列表：");
        System.out.println(list);

        // 替换列表（List）中的Map值
        replaceValues(list, "general", "一般");
        replaceValues(list, "emphasis", "强调");

//        List<Document>mappingConf;
//        for (Document document : mappingConf) {
//            String fromVal = document.getString(k.fromVal);
//            String toVal = document.getString(k.toVal);
//            replaceValues(list, fromVal, toVal);
//        }

        // 输出替换后的列表（List）
        System.out.println("替换后的列表：");
        System.out.println(list);
    }



//    public static void replaceValuesHashMap(List<HashMap<String, String>> list, List<Document>mappingConf) {
////        List<Document>mappingConf;
//        for (Document document : mappingConf) {
//            String fromVal = document.getString(k.fromVal);
//            String toVal = document.getString(k.toVal);
//            replaceValuesHashMap(list, fromVal, toVal);
//        }
//    }


    public static void replaceValues(List<Map<String, String>> list,  List<Document>mappingConf) {
//        List<Document>mappingConf;
        for (Document document : mappingConf) {
            String fromVal = document.getString(k.fromVal);
            String toVal = document.getString(k.toVal);
            replaceValues(list, fromVal, toVal);
        }
    }

    public static void replaceValuesHashMap(List<HashMap<String, Object>> list, List<Document>mappingConf) {
//        List<Document>mappingConf;
        for (Document document : mappingConf) {
            String fromVal = document.getString(k.fromVal);
            String toVal = document.getString(k.toVal);
            replaceValuesHashMap(list, fromVal, toVal);
        }
    }

//    public static <K, V> void replaceValuesHashMap(List<HashMap<K, V>> list, List<Document> mappingConf) {
//        for (Document document : mappingConf) {
//            K fromVal = (K) document.get(k.fromVal);
//            V toVal = (V) document.get(k.toVal);
//            replaceValuesHashMap(list, fromVal, toVal);
//        }
//    }
//

//    public static void replaceValues(List<Map<String, String>> list, String oldValue, String newValue) {
//        for (Map<String, String> map : list) {
//            for (Map.Entry<String, String> entry : map.entrySet()) {
//                if (entry.getValue().equals(oldValue)) {
//                    entry.setValue(newValue);
//                }
//            }
//        }
//    }

    /**
     * 您可以将这两个方法合并成一个方法，使用泛型类型参数 `<K, V>` 和 `Map` 接口作为参数类型。下面是合并后的方法：
     *
     * ```java
     * public static <K, V, T extends Map<K, V>> void replaceValues(List<T> list, V oldValue, V newValue) {
     *     for (Map<K, V> map : list) {
     *         for (Map.Entry<K, V> entry : map.entrySet()) {
     *             if (entry.getValue().equals(oldValue)) {
     *                 entry.setValue(newValue);
     *             }
     *         }
     *     }
     * }
     * ```
     *
     * 在这个方法中，使用了类型参数 `T extends Map<K, V>`，它表示传入的列表中的元素类型必须是实现了 `Map<K, V>` 接口的类型。这样，您可以将 `HashMap` 作为列表中的元素类型传递给这个方法。
     *
     * 通过将这两个方法合并成一个，您可以使用相同的逻辑来替换列表中所有类型为 `Map` 接口的元素中的旧值为新值，无论是 `Map` 还是 `HashMap`。
     * @param list
     * @param oldValue
     * @param newValue
     * @param <K>
     * @param <V>
     * @param <T>
     */
    public static <K, V, T extends Map<K, V>> void replaceValues(List<T> list, V oldValue, V newValue) {
        for (Map<K, V> map : list) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                if (entry.getValue().equals(oldValue)) {
                    entry.setValue(newValue);
                }
            }
        }
    }

//    public static <K, V> void replaceValues(List<Map<K, V>> list, V oldValue, V newValue) {
//        for (Map<K, V> map : list) {
//            for (Map.Entry<K, V> entry : map.entrySet()) {
//                if (entry.getValue().equals(oldValue)) {
//                    entry.setValue(newValue);
//                }
//            }
//        }
//    }

    /**
     * 引用 不用return
     * @param list
     * @param oldValue
     * @param newValue
     * @param <K>
     * @param <V>
     */
    public static <K, V> void replaceValuesHashMap(List<HashMap<K, V>> list, V oldValue, V newValue) {
        for (Map<K, V> map : list) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                if(entry==null){
                    log.info("oldValue  iif(entry==null){ ");
                    log.info(""+oldValue);
                    log.info("newValue");
                    log.info(""+newValue);
                    continue;
                }
                V value = entry.getValue();
                if(value==null){
                    log.info("oldValue  if(value==null){ ");
                    log.info(""+oldValue);
                    log.info("newValue");
                    log.info(""+newValue);
                    continue;
                }
                if (value.equals(oldValue)) {
                    entry.setValue(newValue);
                }
            }
        }
    }

    /**
     *  status : general -> status : 一般
     * @param map
     * @param valMappingConf
     */
   public static void valMapping(Map<String ,String>map,List<Document>valMappingConf){
//        List<Document>fid=new ArrayList<>();

//        fieldMapping()
//       从mongotemplate 读出两个字段  fromVal toVal,
        // 遍历Map，并根据值进行修改
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String fromVal = entry.getValue();
            String toVal = getToVal(fromVal, valMappingConf);
            if(fromVal.equals(toVal)){
                continue;
            }
            entry.setValue(toVal);
//            if (fromVal.equals("general")) {
//                entry.setValue("一般");
//            }
//            if (fromVal.equals("general")) {
//                entry.setValue("一般");
//            } else if (fromVal.equals("emphasis")) {
//                entry.setValue("强调");
//            }
        }
    }

    public static List<HashMap<String, Object>> mapListToHashMapList(List<Map<String, Object>> mapList) {
        List<HashMap<String, Object>> hashMapList = new ArrayList<>();

        for (Map<String, Object> map : mapList) {
            HashMap<String, Object> hashMap = new HashMap<>(map);
            hashMapList.add(hashMap);
        }

        return hashMapList;
    }

//    d(Map<String, Object> map,String key){
//        if (!map.containsKey(key)) {
//
//        }
//        Double id=   (Double) map.get(key);
//    }

//    public static void mergeMaps(Map<String, String> mapOrigin, Map<String, String> mapAdd) {
//        for (Map.Entry<String, String> entry : mapAdd.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//
//            if (mapOrigin.containsKey(key)) {
//                key = "b_" + key;
//            }
//
//            mapOrigin.put(key, value);
//        }
//    }

    public static <K, V> void mergeMaps(Map<K, V> mapSrc, Map<K, V> mapBeAdd) {
        for (Map.Entry<K, V> entry : mapBeAdd.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();

            if (mapSrc.containsKey(key)) {
                key = (K) ("b_" + key.toString());
            }

            mapSrc.put(key, value);
        }
    }

//    public static void mergeMaps(Map<String, ?> mapOrigin, Map<String, ?> mapAdd) {
//        for (Map.Entry<String, ?> entry : mapAdd.entrySet()) {
//            String key = entry.getKey();
//           Object value = entry.getValue();
//
//            if (mapOrigin.containsKey(key)) {
//                key = "b_" + key;
//            }
//
//            mapOrigin.put(key, value);
//        }
//    }

    public static List<Map<String, Object>> hashMapListToMapList(    List<HashMap<String, Object>> hashMapList ){
//        List<HashMap<String, Object>> hashMapList = new ArrayList<>();
        // 假设已经填充了 HashMap 列表

        List<Map<String, Object>> mapList = hashMapList.stream()
                .map(map -> new HashMap<>(map))
                .collect(Collectors.toList());
        return mapList;
    }

    /**
     *
     * @param mapListNoFirstConf
     * @param mapFieldToZh  name -> 名字
     * @return
     */
    public static List<Map<String, Object>> listFieldMapping(
            List<Map<String, Object>> mapListNoFirstConf
    ,   Map<String, String> mapFieldToZh ){

        List<Map<String, Object>> resList = new ArrayList<>();
        for (Map<String, Object> originEngObj : mapListNoFirstConf) {
            Map<String, Object> objZhField = MapUtil.fieldMapping(originEngObj, mapFieldToZh);
            resList.add(objZhField);
        }
        return resList;

    }

    public static List<Map<String, Object>> mapListToZhList( List<Map<String, Object>> allDataWithFirstConf){
//        Map<String, String> wrongWordMap=new HashMap<>();
      return   mapListToZhList(allDataWithFirstConf,null);
    }


    public static <K, V> void putNewKey(Map<K, V> hashMapOrigin, Map<K, V> newKeyMap) {
        // 创建临时的HashMap来存储替换后的键值对
        HashMap<K, V> tempMap = new HashMap<>();
        if(newKeyMap==null){
            log.error("hashMapOrigin  if(newKeyMap==null){");
            log.error(hashMapOrigin);
            return;
        }
        // 遍历原始的HashMap的条目
        for (Map.Entry<K, V> entry : hashMapOrigin.entrySet()) {
            if(entry==null){
                log.error("newKeyMap  if(entry==null){");
                log.error(newKeyMap);
                continue;
            }

            K key = entry.getKey();
            V value = entry.getValue();

            // 根据键映射表进行键的替换
            if (newKeyMap.containsKey(key)) {
                K newKey = (K) newKeyMap.get(key);
                tempMap.put(newKey, value);
            } else {
                tempMap.put(key, value);
            }
        }

        // 清空原始的HashMap
        hashMapOrigin.clear();

        // 将临时的HashMap中的键值对复制回原始的HashMap
        hashMapOrigin.putAll(tempMap);
    }

//    public static void putNewKey(Map<String, String> hashMap, Map<String, String> newKeyMap) {
//        // 创建临时的HashMap来存储替换后的键值对
//        HashMap<String, String> tempMap = new HashMap<>();
//
//        // 遍历原始的HashMap的条目
//        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//
//            // 根据键映射表进行键的替换
//            if (newKeyMap.containsKey(key)) {
//                String newKey = newKeyMap.get(key);
//                tempMap.put(newKey, value);
//            } else {
//                tempMap.put(key, value);
//            }
//        }
//
//        // 清空原始的HashMap
//        hashMap.clear();
//
//        // 将临时的HashMap中的键值对复制回原始的HashMap
//        hashMap.putAll(tempMap);
//    }











//    public static void putNewKey(HashMap<String, String> hashMap, Map<String, String> newKeyMap) {
//        // 创建临时的HashMap来存储替换后的键值对
//        HashMap<String, String> tempMap = new HashMap<>();
//
//        // 遍历原始的HashMap的条目
//        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//
//            // 根据键映射表进行键的替换
//            if (newKeyMap.containsKey(key)) {
//                String newKey = newKeyMap.get(key);
//                tempMap.put(newKey, value);
//            } else {
//                tempMap.put(key, value);
//            }
//        }
//
//        // 清空原始的HashMap
//        hashMap.clear();
//
//        // 将临时的HashMap中的键值对复制回原始的HashMap
//        hashMap.putAll(tempMap);
//    }


// public static void   putNewKey(Map<String ,String >hashMap
// ,Map<String ,String >newKeyMap){
//        // 遍历原始的HashMap的条目
//        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//
////            newKeyMap.get()
//            // 根据需要进行键的替换
//            if (key.equals("name")) {
//                String newKey = "名字";
//                hashMap.remove(key);  // 删除原始的键
//                hashMap.put(newKey, value);  // 添加新的键值对
//            }
//        }
//    }


    public static List<Map<String, Object>> mapListToZhList( List<Map<String, Object>> allDataWithFirstConf
                                                             , Map<String, String> wrongWordMap){
        Map<String, String> mapZhToField =  MapUtil.mapZhToFieldGet(allDataWithFirstConf);
        Map<String, String> mapFieldToZh = MapUtil.reverseMap(mapZhToField);
//      todo tip  name -> 名字

//        for (Map.Entry<String, String> stringStringEntry : mapFieldToZh.entrySet()) {
//            String value = stringStringEntry.getValue();
//            if("关注成都".equals(value)){
//                stringStringEntry.setValue(k.关注程度);
//            }
//        }

//        mapFieldToZh.put(k.)
//        log.info("mapZhToField {}", mapZhToField);
//        String filed体测等级 = mapZhToField.get("体测等级");
//        log.info("filed体测等级 {}", filed体测等级);
//        List<Map<String, Object>> resList = new ArrayList<>();
//        List<Map<String, String>> resList = new ArrayList<>();
        List<Map<String, Object>> resList = new ArrayList<>();
//        第一个 idx==0 的是 col 和 中文名字的 lebel的 配对
        for (int i = 1; i < allDataWithFirstConf.size(); i++) {
//            Object o = jsonArray.get(i);
//            JSONObject student体测成绩 = jsonArray.getJSONObject(i);
            Map<String, Object> originEngObj = allDataWithFirstConf.get(i);

//            String data体测等级 = (String) student体测成绩.get(filed体测等级);
//            Map<String, String> originEngObjStrMap = top.starp.util.MapUtil.convertToStringMap(originEngObj);
            Map<String, Object> objZhField = MapUtil.fieldMapping(originEngObj, mapFieldToZh);

            resList.add(objZhField);
//
//            if ("不及格".equals(data体测等级)) {
////                System.out.println("student体测成绩 不及格");
////                System.out.println(student体测成绩);
////                top.starp.util.MapUtil.fieldMapping(student体测成绩,mapFieldToZh);
//                Map<String, String> student体测成绩Zh = top.starp.util
//                        .MapUtil.fieldMapping(student体测成绩StrMap, mapFieldToZh);
//
//                resList.add(student体测成绩Zh);
////                resList.
//            }
//            Jackson
        }

        if(wrongWordMap==null){
            return  resList;
        }

        return  listFieldMapping(resList,wrongWordMap);

//        return resList;

    }


    public static  Map<String, String> mapZhToFieldGet(  Map<String, Object> keyMap) {
//        JSONObject keyMap = jsonArray.getJSONObject(0);
//        Map<String, Object> keyMap = jsonArray.get(0);

        Map<String, String> mapZhToField = new HashMap<>();
        for (Map.Entry<String, Object> stringObjectEntry : keyMap.entrySet()) {
            String key = stringObjectEntry.getKey();
            Object value = stringObjectEntry.getValue();
            if (value instanceof String) {
                String zh = (String) value;
                //            String zh = (String) value;
                mapZhToField.put(zh, key);
            }else{
//                map str 转化失败
                log.error("不是str");
                log.error("value");
                log.error(""+value);
                log.error("stringObjectEntry");
                log.error(""+stringObjectEntry);
            }

        }
        return mapZhToField;
    }
    public static  Map<String, String> mapZhToFieldGet(List<Map<String, Object>> jsonArray) {
//        JSONObject keyMap = jsonArray.getJSONObject(0);
        Map<String, Object> keyMap = jsonArray.get(0);
      return   mapZhToFieldGet(keyMap);

//        Map<String, String> mapZhToField = new HashMap<>();
//        for (Map.Entry<String, Object> stringObjectEntry : keyMap.entrySet()) {
//            String key = stringObjectEntry.getKey();
//            Object value = stringObjectEntry.getValue();
//            String zh = (String) value;
//            mapZhToField.put(zh, key);
//        }
//        return mapZhToField;
    }
    public static Map<String, Object> documentToMap(Document document) {
        Map<String, Object> map = new HashMap<>();

        for (String key : document.keySet()) {
            Object value = document.get(key);
            map.put(key, value);
        }

        return map;
    }

    public static void mainDoc(String[] args) {
        Document document = new Document();
        document.append("name", "John Doe");
        document.append("age", 30);
        document.append("city", "New York");

        Map<String, Object> map = documentToMap(document);
        System.out.println(map);
    }
    public static void main_fieldMapping1(String[] args) {
        // 原始的 map
        Map<String, String> originalMap = new HashMap<>();
        originalMap.put("field1", "value1");
        originalMap.put("field2", "value2");
        originalMap.put("field3", "value3");

        // 字段映射 map
        Map<String, String> fieldMapping = new HashMap<>();
        fieldMapping.put("field1", "newField1");
        fieldMapping.put("field3", "newField3");

//        Map<String, String> fieldMapping1 = MapUtil.fieldMapping(originalMap, fieldMapping);
//        System.out.println("fieldMapping1");
//        System.out.println(fieldMapping1);
    }

    public static <K, V> Map<V, K> reverseMap(Map<K, V> originalMap) {
        Map<V, K> reversedMap = new HashMap<>();

        for (Map.Entry<K, V> entry : originalMap.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();

            reversedMap.put(value, key);
        }

        return reversedMap;
    }

    public static void main_map(String[] args) {
        Map<String, Integer> originalMap = new HashMap<>();
        originalMap.put("key1", 1);
        originalMap.put("key2", 2);
        originalMap.put("key3", 3);

        Map<Integer, String> reversedMap = reverseMap(originalMap);
        System.out.println("reversedMap");
        System.out.println(reversedMap);
        //        reversedMap
//        {1=key1, 2=key2, 3=key3}
    }

    public static void main2(String[] args) {
        main_map(args);

    }

//    public static <K, V> Map<V, K> fieldMapping(Map<K, V> originalMap, Map<K, V> fieldMapping) {
//        Map<V, K> mappedMap = new HashMap<>();
//
//        for (Map.Entry<K, V> entry : originalMap.entrySet()) {
//            K key = entry.getKey();
//            V value = entry.getValue();
//
//            if (fieldMapping.containsKey(key)) {
//                K newKey = fieldMapping.get(key);
//                mappedMap.put(value, newKey);
//            } else {
//                mappedMap.put(value, key);
//            }
//        }
//
//        return mappedMap;
//    }

    public static   List<Map<String, String >  >    convertToStringMapList(List<Map<String, Object>>  originalMapList) {
        List<Map<String, String >  >   resList=new ArrayList<>();
        for (Map<String, Object> map : originalMapList) {
            Map<String, String> stringStringMap = convertToStringMap(map);
            resList.add(stringStringMap);
        }
        return resList;

    }
    public static Map<String, String> convertToStringMap(Map<String, Object> originalMap) {
        Map<String, String> stringMap = new HashMap<>();

        for (Map.Entry<String, Object> entry : originalMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            String stringValue = (value != null) ? value.toString() : null;
            stringMap.put(key, stringValue);
        }

        return stringMap;
    }

//    public static Map<String, String>  fieldMapping(  Map<String, Object> originalMap,
//                                                      Map<String, String> fieldMapping  ){
//        // 原始的 map
////        Map<String, String> originalMap = new HashMap<>();
////        originalMap.put("field1", "value1");
////        originalMap.put("field2", "value2");
////        originalMap.put("field3", "value3");
////
////        // 字段映射 map
////        Map<String, String> fieldMapping = new HashMap<>();
////        fieldMapping.put("field1", "newField1");
////        fieldMapping.put("field3", "newField3");
//
//        // 新的 map
//        Map<String, String> mappedMap = new HashMap<>();
//
//        // 遍历原始的 map
//        for (Map.Entry<String, String> entry : originalMap.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//
//            if (fieldMapping.containsKey(key)) {
//                // 如果存在字段映射，使用映射值作为新的键，原始字段的值作为新的值
//                String newKey = fieldMapping.get(key);
//                mappedMap.put(newKey, value);
//            } else {
//                // 如果不存在字段映射，直接将原始字段添加到新的 map 中
////                mappedMap.put(key, value);
//            }
//            mappedMap.put(key, value);
//        }
//
////        System.out.println(mappedMap);
//        return mappedMap;
//    }

    /**
     *   return fieldMappingOriginNo(originalMap,fieldMapping);
     *   { name : 张三 } -> {名字: 张三}
     * @param originalMap
     * @param fieldMapping
     * @return
     */
    public static Map<String, Object>  fieldMapping(  Map<String, Object> originalMap,
                                                      Map<String, String> fieldMapping  ){
       return fieldMappingOriginNo(originalMap,fieldMapping);
    }
    public static Map<String, Object>  fieldMappingOriginHave(  Map<String, Object> originalMap,
                                                      Map<String, String> fieldMapping  ){
        // 原始的 map
//        Map<String, String> originalMap = new HashMap<>();
//        originalMap.put("field1", "value1");
//        originalMap.put("field2", "value2");
//        originalMap.put("field3", "value3");
//
//        // 字段映射 map
//        Map<String, String> fieldMapping = new HashMap<>();
//        fieldMapping.put("field1", "newField1");
//        fieldMapping.put("field3", "newField3");

        // 新的 map
        Map<String, Object> mappedMap = new HashMap<>();

        // 遍历原始的 map
        for (Map.Entry<String, Object> entry : originalMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (fieldMapping.containsKey(key)) {
                // 如果存在字段映射，使用映射值作为新的键，原始字段的值作为新的值
                String newKey = fieldMapping.get(key);
                mappedMap.put(newKey, value);
            } else {
                // 如果不存在字段映射，直接将原始字段添加到新的 map 中
//                mappedMap.put(key, value);
            }
            mappedMap.put(key, value);
        }

//        System.out.println(mappedMap);
        return mappedMap;
    }

    /**
     *  { name : 张三 } -> {名字: 张三}
     * @param originalMap
     * @param fieldMapping
     * @return
     */
    public static Map<String, Object>  fieldMappingOriginNo(  Map<String, Object> originalMap,
                                                      Map<String, String> fieldMapping  ){
        // 原始的 map
//        Map<String, String> originalMap = new HashMap<>();
//        originalMap.put("field1", "value1");
//        originalMap.put("field2", "value2");
//        originalMap.put("field3", "value3");
//
//        // 字段映射 map
//        Map<String, String> fieldMapping = new HashMap<>();
//        fieldMapping.put("field1", "newField1");
//        fieldMapping.put("field3", "newField3");

        // 新的 map
        Map<String, Object> mappedMap = new HashMap<>();

        // 遍历原始的 map
        for (Map.Entry<String, Object> entry : originalMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (fieldMapping.containsKey(key)) {
                // 如果存在字段映射，使用映射值作为新的键，原始字段的值作为新的值
                String newKey = fieldMapping.get(key);
                mappedMap.put(newKey, value);
            } else {
                // 如果不存在字段映射，直接将原始字段添加到新的 map 中
//                mappedMap.put(key, value);
            }
//            mappedMap.put(key, value);
        }

//        System.out.println(mappedMap);
        return mappedMap;
    }

//    d(  Map<String, String> fieldMapping ){
//        if (fieldMapping.containsKey(key)) {
//            // 如果存在字段映射，使用映射值作为新的键，原始字段的值作为新的值
//            String newKey = fieldMapping.get(key);
//            mappedMap.put(newKey, value);
//        } else {
//            // 如果不存在字段映射，直接将原始字段添加到新的 map 中
////                mappedMap.put(key, value);
//        }
//        mappedMap.put(key, value);
//    }

//    public static Map<String, String>  fieldMapping(  Map<String, String> originalMap,
//                                                      Map<String, String> fieldMapping  ){
//        // 原始的 map
////        Map<String, String> originalMap = new HashMap<>();
////        originalMap.put("field1", "value1");
////        originalMap.put("field2", "value2");
////        originalMap.put("field3", "value3");
////
////        // 字段映射 map
////        Map<String, String> fieldMapping = new HashMap<>();
////        fieldMapping.put("field1", "newField1");
////        fieldMapping.put("field3", "newField3");
//
//        // 新的 map
//        Map<String, String> mappedMap = new HashMap<>();
//
//        // 遍历原始的 map
//        for (Map.Entry<String, String> entry : originalMap.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//
//            if (fieldMapping.containsKey(key)) {
//                // 如果存在字段映射，使用映射值作为新的键，原始字段的值作为新的值
//                String newKey = fieldMapping.get(key);
//                mappedMap.put(newKey, value);
//            } else {
//                // 如果不存在字段映射，直接将原始字段添加到新的 map 中
////                mappedMap.put(key, value);
//            }
//            mappedMap.put(key, value);
//        }
//
////        System.out.println(mappedMap);
//        return mappedMap;
//    }
}
