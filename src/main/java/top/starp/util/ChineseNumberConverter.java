package top.starp.util;

import java.util.HashMap;
import java.util.Map;

public class ChineseNumberConverter {
    private static final Map<Character, Integer> NUMBER_MAP = createNumberMap();

    private static Map<Character, Integer> createNumberMap() {
        Map<Character, Integer> map = new HashMap<>();
        map.put('零', 0);
        map.put('一', 1);
        map.put('二', 2);
        map.put('三', 3);
        map.put('四', 4);
        map.put('五', 5);
        map.put('六', 6);
        map.put('七', 7);
        map.put('八', 8);
        map.put('九', 9);
        map.put('十', 10);
        return map;
    }

    public static int convertChineseNumber(String chineseNumber) {
        int result = 0;
//        int temp = 0;
        int temp = 1;
        chineseNumber=chineseNumber.replace("号","");
//        十五可以 二十五不可以
        for (int i = 0; i < chineseNumber.length(); i++) {
            char c = chineseNumber.charAt(i);
//            int number = NUMBER_MAP.get(c);
//            int number =     MapUtil.getInteger(NUMBER_MAP, String.valueOf(c));
            int number =     MapUtil.getInteger(NUMBER_MAP,c);

            if (number >= 10) {
                result += temp * number;
                temp = 0;
            } else if (number >= 0) {
                temp = temp * 10 + number;
            }
        }
        return result + temp;
    }

    /**
     * 十二 转为阿拉伯数字为: 2
     * 十二 转为阿拉伯数字为: 12
     *
     * 二十三 转为阿拉伯数字为: 123
     * @param args
     */
    public static void main(String[] args) {
//        String chineseNumber = "十二";

        String chineseNumber = "二十三";
//        十二 转为阿拉伯数字为: 2
        int arabicNumber = convertChineseNumber(chineseNumber);
        System.out.println(chineseNumber + " 转为阿拉伯数字为: " + arabicNumber);
    }
}
