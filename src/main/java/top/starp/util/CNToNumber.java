package top.starp.util;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Andy
 * @since 2023-04-23 18:45
 */
public class CNToNumber {

    /**
     * 中文数字
     */
    private static char[] cnArr_a = new char [] {'零','一','二','三','四','五','六','七','八','九'};
    private static char[] cnArr_A = new char [] {'零','壹','贰','叁','肆','伍','陆','柒','捌','玖'};
    private static final String allChineseNum = "零一二三四五六七八九壹贰叁肆伍陆柒捌玖十拾百佰千仟万萬亿";

    /**
     * 中文单位
     */
    private static char[] unit_a = new char [] {'亿','万','千','百','十'};
    private static char[] unit_A = new char [] {'亿','萬','仟','佰','拾'};
    private static final String allChineseUnit = "十拾百佰千仟万萬亿";

    /**
     * 将汉字中的数字转换为阿拉伯数字
     * (例如：三万叁仟零肆拾伍亿零贰佰萬柒仟陆佰零伍)
     *
     * @param chineseNum;
     * @return long
     */
    public static BigDecimal chineseNumToArabicNum(String chineseNum) {
//        chineseNum
        if(chineseNum==null){
            return null;
        }
        String chineseNumTrimed = chineseNum.trim();
//        ListUtil
        if (u.list("万","一万").contains(chineseNumTrimed)) {
            return BigDecimal.valueOf(10000);
//            return 10000;
        }
        if (chineseNumTrimed.startsWith("十")) {
            int i = ChineseNumberConverter.convertChineseNumber(chineseNumTrimed);
            return  BigDecimal.valueOf(i);
        }
//        if("万".equals(chineseNumTrimed)){
//
//        }
        try {
            // 最终返回的结果
            BigDecimal result = new BigDecimal(0);

            if (chineseNum == null || chineseNum.trim().length() == 0) {
                return result;
            }

            char lastUnit = chineseNum.charAt(chineseNum.length() - 1);

            Boolean appendUnit = true;
            long lastUnitNum = 1;
            if (isCnUnit(lastUnit)) {
                chineseNum = chineseNum.substring(0, chineseNum.length() - 1);
                lastUnitNum = chnNameValue[chnUnitToValue(String.valueOf(lastUnit))].value;
                appendUnit = chnNameValue[chnUnitToValue(String.valueOf(lastUnit))].secUnit;
            } else if (chineseNum.length() == 1) {
                // 如果长度为1时
                int num = strToNum(chineseNum);
                if (num != -1) {
                    return BigDecimal.valueOf(num);
                } else {
                    return null;
                }
            }

            // 将小写中文数字转为大写中文数字
            for (int i = 0; i < cnArr_a.length; i++) {
                chineseNum = chineseNum.replaceAll(String.valueOf(cnArr_a[i]), String.valueOf(cnArr_A[i]));
            }
            // 将小写单位转为大写单位
            for (int i = 0; i < unit_a.length; i++) {
                chineseNum = chineseNum.replaceAll(String.valueOf(unit_a[i]), String.valueOf(unit_A[i]));
            }

            for (int i = 0; i < unit_A.length; i++) {
                if (chineseNum.trim().length() == 0) {
                    break;
                }
                String unitUpperCase = String.valueOf(unit_A[i]);
                String str = null;
                if (chineseNum.contains(unitUpperCase)) {
                    str = chineseNum.substring(0, chineseNum.lastIndexOf(unitUpperCase) + 1);
                }
                if (str != null && str.trim().length() > 0) {
                    // 下次循环截取的基础字符串
                    chineseNum = chineseNum.replaceAll(str, "");
                    // 单位基础值
                    long unitNum = chnNameValue[chnUnitToValue(unitUpperCase)].value;
                    String temp = str.substring(0, str.length() - 1);
                    long number = chnStringToNumber(temp);
                    result = result.add(BigDecimal.valueOf(number).multiply(BigDecimal.valueOf(unitNum)));
                }
                // 最后一次循环，被传入的数字没有处理完并且没有单位的个位数处理
                if (i + 1 == unit_a.length && !"".equals(chineseNum)) {
                    long number = chnStringToNumber(chineseNum);
                    if (!appendUnit) {
                        number = BigDecimal.valueOf(number).multiply(BigDecimal.valueOf(lastUnitNum)).longValue();
                    }
                    result = result.add(BigDecimal.valueOf(number));
                }
            }
            // 加上单位
            if (appendUnit && lastUnitNum > 1) {
                result = result.multiply(BigDecimal.valueOf(lastUnitNum));
            } else if (lastUnitNum > 0) {
                if (result.compareTo(BigDecimal.ZERO) == BigDecimal.ZERO.intValue()) {
                    result = BigDecimal.ONE;
                    result = result.multiply(BigDecimal.valueOf(lastUnitNum));
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回中文数字汉字所对应的阿拉伯数字，若str不为中文数字，则返回-1
     *
     * @param string;
     * @return int
     */
    private static int strToNum(String string){
        for(int i = 0;i<cnArr_a.length;i++){
            if (Objects.equals(string,String.valueOf(cnArr_a[i])) || Objects.equals(string,String.valueOf(cnArr_A[i]))){
                return i;
            }
        }
        return -1;
    }

    /**
     * 判断传入的字符串是否全是汉字数字和单位
     *
     * @param chineseStr;
     * @return boolean
     */
    public static boolean isCnNumAll(String chineseStr){
        if (isBlank(chineseStr)){
            return true;
        }
        char [] charArray = chineseStr.toCharArray();
        for(char c : charArray){
            if (!allChineseNum.contains(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是空的
     *
     * @param cs
     * @return
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    /**
     * 判断传入的字符是否是汉字数字和单位
     *
     * @param chineseChar;
     * @return boolean
     */
    public static boolean isCnNum(char chineseChar) {
        if (!allChineseNum.contains(String.valueOf(chineseChar))) {
            return false;
        }else{
            return true;
        }
    }

    /**
     * 判断是否是中文单位
     *
     * @param unitStr;
     * @return boolean
     */
    public static boolean isCnUnit(char unitStr) {
        if (!allChineseUnit.contains(String.valueOf(unitStr))) {
            return false;
        }else{
            return true;
        }
    }

    /**
     * 中文转换成阿拉伯数字，中文字符串除了包括0-9的中文汉字，还包括十，百，千，万等权位。
     * 此处是完成对这些权位的类型定义。
     * name是指这些权位的汉字字符串。
     * value是指权位多对应的数值的大小。诸如：十对应的值的大小为10，百对应为100等
     * secUnit若为true，代表该权位为节权位，即万，亿，万亿等
     */
    public static class ChnNameValue{
        String name;
        long value;
        Boolean secUnit;
        ChnNameValue(String name,long value,Boolean secUnit){
            this.name=name;
            this.value=value;
            this.secUnit=secUnit;
        }
    }

    static ChnNameValue chnNameValue[] = {
            new ChnNameValue("十",10,false),
            new ChnNameValue("拾",10,false),
            new ChnNameValue("百",100,false),
            new ChnNameValue("佰",100,false),
            new ChnNameValue("千",1000,false),
            new ChnNameValue("仟",1000,false),
            new ChnNameValue("万",10000,true),
            new ChnNameValue("萬",10000,true),
            new ChnNameValue("亿",100000000,true)
    };

    /**
     * 返回中文汉字权位在chnNameValue数组中所对应的索引号，若不为中文汉字权位，则返回-1
     *
     * @param str;
     * @return int
     */
    private static int chnUnitToValue(String str){
        for(int i=0;i<chnNameValue.length;i++){
            if(str.equals(chnNameValue[i].name)){
                return i;
            }
        }
        return -1;
    }

    /**
     * 返回中文数字字符串所对应的int类型的阿拉伯数字
     * (千亿/12位数)
     *
     * @param str;
     * @return long
     */
    private static long chnStringToNumber(String str){
        long returnNumber=0;
        long section=0;
        int index=0;
        long number=0;
        while (index<str.length()){
            // 从左向右依次获取对应中文数字，取不到返回-1
            int num = strToNum(str.substring(index,index+1));
            //若num>=0，代表该位置（pos），所对应的是数字不是权位。若小于0，则表示为权位
            if(num >= 0){
                number = num;
                index++;
                //pos是最后一位，直接将number加入到section中。
                if(index>=str.length()){
                    section += number;
                    returnNumber += section;
                    break;
                }
            }else{
                int chnNameValueIndex=chnUnitToValue(str.substring(index,index+1));

                if (chnNameValueIndex == -1) {
                    // 字符串存在除 数字和单位 以外的中文

                    Map<String, Object> map = u.map(k.error, "字符串存在除 <数字和单位> 以外的中文"
                            , k.str, str);

                    throw new NumberFormatException(StringUtils.getMapStr(map));

//                    throw new NumberFormatException("字符串存在除 <数字和单位> 以外的中文 {str}".replace("{str}",str));
                }

                //chnNameValue[chnNameValueIndex].secUnit==true，表示该位置所对应的权位是节权位，
                if(chnNameValue[chnNameValueIndex].secUnit){
                    section = (section + number) * chnNameValue[chnNameValueIndex].value;
                    returnNumber += section;
                    section=0;
                }else{
                    section += number*chnNameValue[chnNameValueIndex].value;
                }
                index++;
                number=0;
                if(index>=str.length()){
                    returnNumber += section;
                    break;
                }
            }
        }
        return returnNumber;
    }

    public static void mainManyStr(String[] args) {
        String str = "三万叁仟零肆拾伍亿零贰佰萬柒仟陆佰零伍万";
        String str1 = "三万叁仟零肆拾伍亿零贰佰萬柒仟陆佰零伍";
        String str2 = "三万叁仟零肆拾伍亿零柒仟陆佰零伍";
        String str3 = "三仟零肆十万";
        String str4 = "三万三千";
        String str5 = "三万零四十";
        String str6 = "三万叁仟零肆拾伍亿";
        String str7 = "十";
        String str8 = "一百";
        String str9 = "一百零一";
        String str10 = "一千";
        String str11 = "一千零一";
        String str12 = "一千零一十";
        String str13 = "零";
        String str14 = "一";
        String str15 = "贰";
        String str16 = "万";
        String str17 = "空";
        String str18 = "空万";

        System.out.println();
        System.out.println(">>> " + str + " : " + chineseNumToArabicNum(str));
        System.out.println(">>> " + str1 + " : " + chineseNumToArabicNum(str1));
        System.out.println(">>> " + str2 + " : " + chineseNumToArabicNum(str2));
        System.out.println(">>> " + str3 + " : " + chineseNumToArabicNum(str3));
        System.out.println(">>> " + str4 + " : " + chineseNumToArabicNum(str4));
        System.out.println(">>> " + str5 + " : " + chineseNumToArabicNum(str5));
        System.out.println(">>> " + str6 + " : " + chineseNumToArabicNum(str6));
        System.out.println(">>> " + str7 + " : " + chineseNumToArabicNum(str7));
        System.out.println(">>> " + str8 + " : " + chineseNumToArabicNum(str8));
        System.out.println(">>> " + str9 + " : " + chineseNumToArabicNum(str9));
        System.out.println(">>> " + str10 + " : " + chineseNumToArabicNum(str10));
        System.out.println(">>> " + str11 + " : " + chineseNumToArabicNum(str11));
        System.out.println(">>> " + str12 + " : " + chineseNumToArabicNum(str12));
        System.out.println(">>> " + str13 + " : " + chineseNumToArabicNum(str13));
        System.out.println(">>> " + str14 + " : " + chineseNumToArabicNum(str14));
        System.out.println(">>> " + str15 + " : " + chineseNumToArabicNum(str15));
        System.out.println(">>> " + str16 + " : " + chineseNumToArabicNum(str16));
        System.out.println(">>> " + str17 + " : " + chineseNumToArabicNum(str17));
        System.out.println(">>> " + str18 + " : " + chineseNumToArabicNum(str18));
        System.out.println();
//        十五
//        test("十五");
//        test("三千零一十");
//        test("三百零一十");


//        test("十五");
        test("三千零一十",3010.0);
        test("三百零一十",310);

//        三千零一十 三百零一十

        test("十五",15.0);

        /**
         *
         *
         >>> 三万叁仟零肆拾伍亿零贰佰萬柒仟陆佰零伍万 : 33045020076050000
         >>> 三万叁仟零肆拾伍亿零贰佰萬柒仟陆佰零伍 : 3304502007605
         >>> 三万叁仟零肆拾伍亿零柒仟陆佰零伍 : 3304500007605
         >>> 三仟零肆十万 : 30400000
         >>> 三万三千 : 33000
         >>> 三万零四十 : 30040
         >>> 三万叁仟零肆拾伍亿 : 3304500000000
         >>> 十 : 10
         >>> 一百 : 100
         >>> 一百零一 : 101
         >>> 一千 : 1000
         >>> 一千零一 : 1001
         >>> 一千零一十 : 1010
         >>> 零 : 0
         >>> 一 : 1
         >>> 贰 : 2
         >>> 万 : 0 错误
         >>> 空 : null
         >>> 空万 : null

         * Time: 2023_07_13_09_31_31    十五  -> 5
         * Time: 2023_07_13_09_31_31    三千零一十  -> 3010
         * Time: 2023_07_13_09_31_31    三百零一十  -> 310
         */


    }

    private static final String[] numbers = {
            "一", "二", "三", "四", "五", "六", "七", "八", "九", "十",
            "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十",
            "二十一", "二十二", "二十三", "二十四", "二十五", "二十六", "二十七", "二十八", "二十九", "三十", "三十一"
    };

    /**
     *
     中文数字: 二十八
     期望结果: 28
     解析结果: 28
     测试结果: 通过

     中文数字: 二十九
     期望结果: 29
     解析结果: 29
     测试结果: 通过

     中文数字: 三十
     期望结果: 30
     解析结果: 30
     测试结果: 通过

     中文数字: 三十一
     期望结果: 31
     解析结果: 31
     测试结果: 通过

     * @param args
     */
    public static void main(String[] args) {
        for (int i = 1; i <= 31; i++) {
            testNumber(i);
        }
    }

    private static void testNumber(int number) {
        String chineseNumber = numbers[number - 1];
//        double expectedValue = (double) number;
        BigDecimal expectedValue =   BigDecimal.valueOf(number);
        BigDecimal actualValue =  CNToNumber.chineseNumToArabicNum(chineseNumber);
//        double actualValue = parseChineseNumber(chineseNumber);
        System.out.println("中文数字: " + chineseNumber);
        System.out.println("期望结果: " + expectedValue);
        System.out.println("解析结果: " + actualValue);
//        System.out.println("测试结果: " + (actualValue == expectedValue ? "通过" : "失败"));
//        int i = actualValue.compareTo(expectedValue);
//        System.out.println("测试结果: " + (actualValue .compareTo(expectedValue)==0 ? "通过" : "失败"));
//        ObjectU
       String isPassStr= ObjectUtil.equals(expectedValue,actualValue) ? "通过" : "失败";
        System.out.println("测试结果: " +isPassStr);
        System.out.println();

    }

    static void test(String testNumberZh){
//        十五
        log.info(testNumberZh+"  -> "+ chineseNumToArabicNum(testNumberZh));

    }

    static void test(String testNumberZh,Integer shouldVal){
//        十五
        BigDecimal genVal = chineseNumToArabicNum(testNumberZh);
        String  cmpRes="fail";
        if (genVal.compareTo( BigDecimal.valueOf(shouldVal))==0) {
            cmpRes="ok";
        }
        log.info(testNumberZh+"  -> "+ chineseNumToArabicNum(testNumberZh)+"  cmpRes "+cmpRes);

    }

    static void test(String testNumberZh,Double shouldVal){
//        十五
        BigDecimal genVal = chineseNumToArabicNum(testNumberZh);
//        if (genVal.equals(shouldVal)) {
//
//        }
        String  cmpRes="fail";
        if (genVal.compareTo( BigDecimal.valueOf(shouldVal))==0) {
            cmpRes="ok";
        }


//        if (genVal.toString().equals(shouldVal)) {
//            cmpRes="ok";
//        }
        log.info(testNumberZh+"  -> "+ chineseNumToArabicNum(testNumberZh)+"  cmpRes "+cmpRes);

    }

    static void test(String testNumberZh,String shouldVal){
//        十五
        BigDecimal genVal = chineseNumToArabicNum(testNumberZh);
//        if (genVal.equals(shouldVal)) {
//
//        }
//        genVal.compareTo( BigDecimal.valueOf(shouldVal))
        String  cmpRes="fail";
        if (genVal.toString().equals(shouldVal)) {
            cmpRes="ok";
        }
        log.info(testNumberZh+"  -> "+ chineseNumToArabicNum(testNumberZh)+"  cmpRes "+cmpRes);

    }

}

