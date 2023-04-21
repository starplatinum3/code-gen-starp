package top.starp.util;

import java.util.List;
import java.util.Objects;

public class ListUtil {
    public static void main(String[] args) {
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
     * @param likeWhat
     * @return
     */
    public  static  boolean haveLike(List<String> list,String likeWhat){
//        $"{da}";
//        hav
        for (String s : list) {
            if (s.contains(likeWhat)) {
                return true;
            }
        }
        return false;
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
