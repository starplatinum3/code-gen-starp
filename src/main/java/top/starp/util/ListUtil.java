package top.starp.util;

import com.google.common.collect.Lists;

import java.util.*;

public class ListUtil {

    public static Long sum(List<Integer> numbers) {
        long sum=0;
        for (Integer number : numbers) {
            sum+=number;
        }
        return  sum;
    }

    public static List<Integer> generateRandomList(int size, int minValue, int maxValue) {
        List<Integer> list = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < size; i++) {
            int randomNumber = random.nextInt(maxValue - minValue + 1) + minValue;
            list.add(randomNumber);
        }

        return list;
    }

    public static Map<String, List<HashMap<String, Object>>>
    groupByHashMap(List<HashMap<String, Object>> records, String groupByName) {
        // 创建一个用于存储分组后记录的Map
        Map<String, List<HashMap<String, Object>>> groupedRecords = new HashMap<>();

        // 根据用户ID进行分组
        for (HashMap<String, Object> record : records) {
            String userId = (String) record.get(groupByName);

            // 检查该用户ID是否已经存在于分组中
            if (groupedRecords.containsKey(userId)) {
                // 如果存在，将记录添加到现有分组中
                groupedRecords.get(userId).add(record);
            } else {
                // 如果不存在，创建一个新的分组并将记录添加到其中
                List<HashMap<String, Object>> newGroup = new ArrayList<>();
                newGroup.add(record);
                groupedRecords.put(userId, newGroup);
            }
        }
        return groupedRecords;
    }


//    public static Map<String, List<Map<String, Object>>>
//    groupBy(List<Map<String, Object>> records, String groupByName) {
//        // 创建一个用于存储分组后记录的Map
//        Map<String, List<Map<String, Object>>> groupedRecords = new HashMap<>();
//
//// 根据用户ID进行分组
//        for (Map<String, Object> record : records) {
//            String userId = (String) record.get(groupByName);
//
//            // 检查该用户ID是否已经存在于分组中
//            if (groupedRecords.containsKey(userId)) {
//                // 如果存在，将记录添加到现有分组中
//                groupedRecords.get(userId).add(record);
//            } else {
//                // 如果不存在，创建一个新的分组并将记录添加到其中
//                List<Map<String, Object>> newGroup = new ArrayList<>();
//                newGroup.add(record);
//                groupedRecords.put(userId, newGroup);
//            }
//        }
//        return groupedRecords;
//    }


//    public static Map<String, List<Map<?, ?>>>
//    groupBy(List<Map<?, ?>> records, String groupByName) {
//        // 创建一个用于存储分组后记录的Map
//        Map<String, List<Map<?, ?>>> groupedRecords = new HashMap<>();
//
//// 根据用户ID进行分组
//        for (Map<?, ?> record : records) {
//            String userId = (String) record.get(groupByName);
//
//            // 检查该用户ID是否已经存在于分组中
//            if (groupedRecords.containsKey(userId)) {
//                // 如果存在，将记录添加到现有分组中
//                groupedRecords.get(userId).add(record);
//            } else {
//                // 如果不存在，创建一个新的分组并将记录添加到其中
//                List<Map<?, ?>> newGroup = new ArrayList<>();
//                newGroup.add(record);
//                groupedRecords.put(userId, newGroup);
//            }
//        }
//        return groupedRecords;
//    }



    public static Map<String, List<Map<?, ?>>>
    groupBy(List<Map> records, String groupByName) {
        // 创建一个用于存储分组后记录的Map
        Map<String, List<Map<?, ?>>> groupedRecords = new HashMap<>();

// 根据用户ID进行分组
        for (Map<?, ?> record : records) {
            String userId = (String) record.get(groupByName);

            // 检查该用户ID是否已经存在于分组中
            if (groupedRecords.containsKey(userId)) {
                // 如果存在，将记录添加到现有分组中
                groupedRecords.get(userId).add(record);
            } else {
                // 如果不存在，创建一个新的分组并将记录添加到其中
                List<Map<?, ?>> newGroup = new ArrayList<>();
                newGroup.add(record);
                groupedRecords.put(userId, newGroup);
            }
        }
        return groupedRecords;
    }

    public static  <T> T getLastElement(List<T>list){
        int lastIndex = list.size() - 1;
//        String lastElement = list.get(lastIndex);
      return   list.get(lastIndex);
    }

//    public static List<String> createAndInitializeList(String... elements) {
//      return   createAndInitializeList(elements);
//
//    }

    public  static <T> List<T> createAndInitializeList(T... elements) {

        List<T> list = new ArrayList<>(Arrays.asList(elements));
        return list;
    }

    @SafeVarargs
    public  static <T> List<T> createList(T... elements) {
        return Lists.newArrayList(elements);
//        return createAndInitializeList(elements);
    }
    public static void mainList(String[] args) {
        List<String> myList = createAndInitializeList("Apple", "Banana", "Orange");
        System.out.println(myList);
    }





public static void main(String[] args) {
//        ElmGenKt.v
//        ElmGenKt.
//         String text=f" this is F-$String.class.getSimpleName() ";
//         System.out.println(text);
// //        this is F-String
// //        f"this F-$short.class.getMethod()"
//    boolean  eq=   Objects.equals($"Zircon: [ ${text.trim()} ]","Zircon: [ "+text.trim()+" ]");
// //        assert Objects.equals($"Zircon: [ ${text.trim()} ]","Zircon: [ "+text.trim()+" ]");
// //        Objects
//         System.out.println("eq");
//         System.out.println(eq);
//        eq
//        true

    }
    /**
     *
     * @param list
     * @param smallToken
     * @return
     */
    public  static  boolean haveTag(List<String> list,String smallToken){
//        $"{da}";
//        smallToken
//        hav
        for (String bigWord : list) {
            if (bigWord.contains(smallToken)) {
                return true;
            }
        }
        return false;
    }

    public  static  boolean haveLike(List<String> list,String big){
//        $"{da}";
//        smallToken
//        hav
        for (String tag : list) {
            if (big.contains(tag)) {
                return true;
            }
//            if (bigWord.contains(smallToken)) {
//                return true;
//            }
        }
        return false;
    }

    public  static  String getTag(Collection<String> list,String big){
        for (String tag : list) {
            if (big.contains(tag)) {
                return tag;
            }
        }
        return null;
    }
    public  static  boolean haveLike(Collection<String> list,String big){
        String tag1 = getTag(list, big);
        return tag1!=null;
//        $"{da}";
//        smallToken
//        hav
//        for (String tag : list) {
//            if (big.contains(tag)) {
//                return true;
//            }
////            if (bigWord.contains(smallToken)) {
////                return true;
////            }
//        }
//        return false;
    }

    /**
     * 对比的字符串是多的  if (likeWhat.contains(s)) {
     * 比如 likeWhat 家庭住址 ， s 是 住址
     * 因为数据库可能是字段是家庭住址，他是多样化的，而我这里已经设置好的 是比较少的几个枚举
     * @param list
     * @param likeWhat
     * @return
     */
    public  static  boolean haveLikeWordMuch(List<String> list,String likeWhat){
        for (String s : list) {
//            if (s.contains(likeWhat)) {
                if (likeWhat.contains(s)) {
                    return true;
                }
                return true;
        }
        return false;
    }

//    public  static  boolean getLikeItem(List<String> list,String likeWhat){
//        for (String s : list) {
//            if (s.contains(likeWhat)) {
////                return true;
//                return s;
//            }
//        }
//        return false;
//    }
}
