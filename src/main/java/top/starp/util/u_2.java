package top.starp.util;


import java.util.*;

public class u_2 {

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
     * <p>
     *          if (u.list("万","一万").contains(chineseNumTrimed)) {
     *             return BigDecimal.valueOf(10000);
     *         }
     *
     * @param elms elms
     * @return <T> List<T> list
     * @param <T> <T>
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

//    d(){
//        List<Map<String, String>> list5 = u.list(
//                u.mapOf(u.p("1", "1"))
//                , u.mapOf(u.p("1", "1"))
//        );
//        Map<String, String> stringStringMap1 = list5.get(0);
//        List<String> collect = new ArrayList<>(stringStringMap1.keySet());
//        ExcelTool.convert();
//    }


    public static void main(String[] args) {
        Map<String, String> stringStringMap = u_2.mapOf(
                p("1", "2")
                , p("1", "2")
        );
        List<Map<String, Object>> list = u_2.list(
                u_2.map("2", 312L)
                , u_2.map("2", 312L)
        );

        List<Map<String, Long>> list1 = u_2.list(
                u_2.mapType("2", 312L)
                , u_2.mapType("2", 312L)
        );

        List<Map<String, Integer>> list2 = list(
                p("da", 1)
                , p("da", 1)
                , p("da", 1)
                , p("da", 1)
                , p("da", 1)
        );

        List<Map<Integer, Integer>> list3 = u_2.list(
                u_2.p(3131, 414),
                u_2.p(3114, 114514)
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

//        List<Map<String, String>> list5 = u.list(
//                u.mapOf(u.p("1", "1"))
//                , u.mapOf(u.p("1", "1"))
//        );
//        Map<String, String> stringStringMap1 = list5.get(0);
//        List<String> collect = new ArrayList<>(stringStringMap1.keySet());
//        ExcelTool.convert();


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
     * <p>
     *         List<Map<Integer, Integer>> list3 = u.list(
     *                 u.p(3131, 414),
     *                 u.p(3114, 114514)
     *         );
     * @param pairs pairs
     * @return  <K,V>   List<  Map<K, V> > list
     * @param <K>  <K>
     * @param <V>  <V>
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
     * @param keyVals keyVals
     * @return Map<String ,Object> map
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
