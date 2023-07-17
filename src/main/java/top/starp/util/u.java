package top.starp.util;

//import com.google.common.collect.Lists;

import java.util.*;

public class u {

    /**
     *   List<Map<String, Object>> confList = u.list(
     *                 u.map(
     *                         k.word, "今天"
     *                 ,k.nature,HanLPUtil.time
     *                 )
     *                 ,  u.map(
     *                         k.word, "昨天"
     *                         ,k.nature,HanLPUtil.time
     *                 )
     *         );
     *
     *          if (u.list("万","一万").contains(chineseNumTrimed)) {
     *             return BigDecimal.valueOf(10000);
     *         }
     *
     * @param elms
     * @return
     * @param <T>
     */
    @SafeVarargs
    public static <T> List<T> list(T... elms) {
//        return Lists.newArrayList(elms);
        return new ArrayList<>(Arrays.asList(elms));
    }

    public static <T> List<T> list() {
        return new ArrayList<>();
    }

    public static Map<String ,Object> map(String key, Object val){
        Map<String ,Object>map=new HashMap<>();
        map.put(key,val);
        return map;
    }

//    public static <K, V> Map<K, V> map(Object... keyVals) {
//        Map<K, V> map = new HashMap<>();
//        boolean isKey = true;
//        K key = null;
//        for (Object nowVal : keyVals) {
//            if (isKey) {
//                key = (K) nowVal;
//            } else {
//                map.put(key, (V) nowVal);
//            }
//            isKey = !isKey;
//        }
//        if (keyVals.length % 2 == 1) {
//            map.put(key, null);
//        }
//        return map;
//    }


    @SafeVarargs
    public static<K,V> Map<K ,V> mapOf(Pair<K,V> ... keyVals){
        Map<K ,V>map=new HashMap<>();
        for (Pair<K,V> keyVal : keyVals) {
//            keyVal.key
            map.put(keyVal.key,keyVal.val);
        }
        return  map;
    }

    public static void main(String[] args) {
        Map<String, String> stringStringMap = u.mapOf(
                p("1", "2")
                , p("1", "2")
        );
        List<Map<String, Object>> list = u.list(
                u.map("2", 312l)
                , u.map("2", 312l)
        );

        List<Map<String, Long>> list1 = u.list(
                u.mapType("2", 312l)
                , u.mapType("2", 312l)
        );

        List<Map<String, Integer>> list2 = list(
                p("da", 1)
                , p("da", 1)
                , p("da", 1)
                , p("da", 1)
                , p("da", 1)
        );

        List<Map<Integer, Integer>> list3 = u.list(
                u.p(3131, 414),
                u.p(3114, 114514)
        );
//        List<Map<String, ? extends Number>> list3 = list(
//                p("da", 1L)
//                , p("da", 1)
//                , p("da", 1)
//                , p("da", 1)
//                , p("da", 1)
//        );

        List<Map<String, Number>> list4 = list(
                p("da", 1L)
                , p("da", 1)
                , p("da", 1)
                , p("da", 1)
                , p("da", 1)
        );


//        Map<String, String> stringStringMap = u.mapOf(
//                u.maty
//                , p("1", "2")
//        );
    }

    public static  <K,V>  Pair<K ,V> p(K key,V val){
//         u.map(key,val);
       return new Pair<>(key,val);
//        Map<K ,V>map=new HashMap<>();
//        map.put(key,val);
//        return map;
    }

    public static  <K,V>  Map<K ,V> mapType(K key,V val){
//         u.map(key,val);
        Map<K ,V>map=new HashMap<>();
        map.put(key,val);
        return map;
    }

    /**
     *    List<Map<String, Integer>> list2 = list(
     *                 p("da", 1)
     *                 , p("da", 1)
     *                 , p("da", 1)
     *                 , p("da", 1)
     *                 , p("da", 1)
     *         );
     *
     *         List<Map<Integer, Integer>> list3 = u.list(
     *                 u.p(3131, 414),
     *                 u.p(3114, 114514)
     *         );
     * @param pairs
     * @return
     * @param <K>
     * @param <V>
     */
    @SafeVarargs
    public static  <K,V>   List<  Map<K, V> > list(Pair<K,V> ...pairs){
//        list();
//        List<Object> list = ListUtil.createList();
        List<  Map<K, V> >mapList=new ArrayList<>();
        for (Pair<K, V> pair : pairs) {
            Map<K, V> kvMap = mapType(pair.key, pair.val);
            mapList.add(kvMap);
        }
//         u.map(key,val);
//        Map<K ,V>map=new HashMap<>();
//        map.put(key,val);
        return mapList;
    }
    /**
     * Map<String, Object> map = u.map(k.error, "字符串存在除 <数字和单位> 以外的中文"
     *                             , k.str, str);
     * @param keyVals
     * @return
     */
    public static Map<String ,Object> map(Object ... keyVals){
//        if (keyVals.length%2==1) {
////            奇数
//        }
        Map<String ,Object>map=new HashMap<>();
//        int idx=0;
        boolean isKey=true;
        String  key=null;
        for (Object nowVal : keyVals) {
            if(isKey){
                key=(String) nowVal;
            }else{
                map.put(key,nowVal);
            }
            isKey=!isKey;
//            idx++;

        }
        if (keyVals.length%2==1) {
//            奇数
            map.put(key,null);
        }
//        map.put(key,val);
        return map;
    }
}
