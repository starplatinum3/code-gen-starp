package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utils for handle strings.
 * @author Evan
 * @date 2020/3/2 21:04
 */
public class StringUtils {

public  static boolean isNullOrEmpty(Object object){
//    isNullOrEmpty
    if(object==null){
        return true;
    }
    if(object instanceof  String){
        String string = (String) object;
        if(string.equals("")){
            return true;
        }
        return false;

    }
   return false;
}

    public static  String  reverse(String string){
        String reverse = new StringBuffer(string).reverse().toString();
        return reverse;
    }
    public static boolean isNone(Object object ){
        if(object==null){
            return true;
        }
        try {
            String string=   (String)object;
            if("".equals(string)){
                return true;
            }
        }catch (Exception e){
            return false;
        }
        return false;

    }
    // Empty checks
    //-----------------------------------------------------------------------
    /**
     * <p>Checks if a String is empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the String.
     * That functionality is available in isBlank().</p>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }


    public static String quotation(Object object) {
        return " '" + object + "' ";
    }

    public static String like(Object object) {
        return " '%" + object + "%' ";
    }

    //首字母大写
    public static String captureName(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return  name;

    }
    //————————————————
//    版权声明：本文为CSDN博主「zhurhyme」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//    原文链接：https://blog.csdn.net/zhurhyme/article/details/27951099
//
    public static String makeSqlCreate(String table){

        String[] split = table.split("\n");
        String[] cmts = split[0].split("\t");
        String[] fields = split[1].split("\t");
        System.out.println(table);
//        `equipment_name`  varchar(255) NULL ,
        int idx=0;
//        System.out.println();
//        CREATE TABLE `NewTable` (
        String res="CREATE TABLE `NewTable` (";
        List<String > fieldList=new ArrayList<>();
        for (String field : fields) {
            String cmt=  cmts[idx];
            idx++;
            fieldList.add(String.format("`%s`  varchar(255) NULL COMMENT '%s' ",field,cmt ));
//            res+=String.format("`%s`  varchar(255) NULL COMMENT '%s' ,",field,cmt );
//            System.out.println(String.format("`%s`  varchar(255) NULL COMMENT '%s' ,",field,cmt ));
        }
        String collect = String.join(",\n", fieldList);
//        String res
        res+=collect+")\n" +
                ";\n";
        return res;

//        res+=")\n" +
//                ";\n";
    }
    public  static   String toPascalCase(String camelCase) {
        if (camelCase == null || camelCase.length() == 0) {
            return "";
        }

        String out = "" + Character.toUpperCase(camelCase.charAt(0));
        if (camelCase.length() == 1) {
            return out;
//           长度为1 就直接返回 变大的这个
        }
        out += camelCase.substring(1);
        return out;
    }

    /**
     * StrUtil 类，声明省略
     */
    enum Place {
        BELOW, UP
    }


    /**
     * byte	Byte
     * short	Short
     * int	Integer
     * long	Long
     * float	Float
     * double	Double
     * boolean	Boolean
     * char	Character
     *
     * @param canonicalName
     * @return
     */
    public static String primaryToPackaging(String canonicalName) {
        switch (canonicalName) {
            case "byte":
                return "java.lang.Byte";
            case "short":
                return "java.lang.Short";
            case "int":
                return "java.lang.Integer";
            case "long":
                return "java.lang.Long";
            case "float":
                return "java.lang.Float";
            case "double":
                return "java.lang.Double";
            case "boolean":
                return "java.lang.Boolean";
            case "char":
                return "java.lang.Character";
            default:
                return canonicalName;
        }
    }

    //
//    public static String getRequestContain(HttpServletRequest request) {
//
//        Enumeration enu = request.getParameterNames();
//        StringBuilder stringBuilder = new StringBuilder();
//        while (enu.hasMoreElements()) {
//            String paraName = (String) enu.nextElement();
//            stringBuilder.append(paraName).append(": ").
//                    append(request.getParameter(paraName)).append(", ");
////        System.out.println(paraName+": "+request.getParameter(paraName));
//        }
//        return stringBuilder.toString();
//    }
    public static String addBackslash(String origin) {
//        return origin.replaceAll("[\"']", "\\\\$0");
        return origin.replace("'", "\\\"");
    }

    /**
     * '  -> "
     *
     * @param origin
     * @return
     */
    public static String singleQuotationMarkToDouble(String origin) {
//        return origin.replaceAll("[\"']", "\\\\$0");
        return origin.replace("'", "\"");
    }

    //format方法
    public static String formatToFour0(String s) {

//写上你要怎么处理字符串
//这里假定是一个1到4位的整数，不足四位时前面补0
        int len = s.length();
//不足四位
//        https://zhidao.baidu.com/question/175025220.html
        if (len < 4) {
            int prefixNum = 4 - len;//计算要补几个0
//前面补0
            StringBuilder sBuilder = new StringBuilder(s);
            for (int i = 0; i < prefixNum; i++) {
                sBuilder.insert(0, "0");
            }
            s = sBuilder.toString();
        }
        return s;
    }

    //正则表达式   :   完美
    public static boolean isNumber(String str) {
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }

    //    void leftSlashToRightSlash(String string){
//
//    }

    /**
     * 去掉前面的0的parseInt
     *
     * @param str
     * @return
     */
    public static int parseInt(String str) {

        int start = 0;

        while (str.charAt(start) == '0') start++;
        str = str.substring(start);
        return Integer.parseInt(str);
    }
    static  void test(){
//        oneLineJapaneseOneLineChineseShow();
//        and();
//        getLines();
//        getBelowLinesInPara();
//        String str = "1234";
//        String stripChars = "124";
//        3
//        也就是说 stripchars 里面带有的东西 都会被消掉
//        System.out.println(StringUtils.strip(str, stripChars));
//        System.out.println(StringUtils.trim(str));  //消除空格
//        System.out.println(StringStartTrim(str, stripChars));
//        System.out.println(rDelStr(str, stripChars));
//        String string = "1：1";
//        System.out.println(Arrays.toString(string.split("[:：]")));

//        String res = addBackslash("{'id':'1','name':'jojo','gender':'男'}");
//        java 有没有类似 r"" python

//        System.out.println(res);

//       String jsonStr=  StrUtil.addBackslash("'{'data':'java send'}'");

//        String table= "id\t设备名称\t所属机构\t所属分组\tMAC地址\t分辨率\t设备状态\t系统升级 \t当前计划\t设备型号\t设备IP\t运行内存\t存储空间\t系统版本\t信发版本\t无线MAC地址\tSN\t激活时间\t分组id\t安装位置\t注册时间\t屏显方式\t设备运行时长\t最后心跳时间\n" +
//                "id\tequipment_name\torganization\tgroup\tmac\tresolution_ratio\tstatus\tsystem_upgrade\tcurrent_plan\tequipment_model\tip\tram\tstorage\tversion\tsending_version\twireless_mac\tsn\tactivation_time\tgroup_id\tinstallation_position\tregistration_time\tscreen_display_mode\tequipment_run_duration\tlast_heartbeat";
//        String table= "id\t缩略图\t计划名称\t计划状态\t播放模式\t播放日期\t作者\t审核人\t更新时间\t多屏同步\t开始日期\t结束日期\t开始时间\t结束时间\t创建时间\t播放策略\t原因\t节目id\n" +
//                "id\tthumbnail\tplan_name\tstatus\tplay_mode\tplay_date\tauthor\treviewed_by\tupdate_time\tmulti_screen_sync\tstart_date\tend_date\tstart_time\tend_time\tcreate_time\tplay_strategy\treason\tprogram_id";
//        String table= "id\t缩略图\t节目名称\t分辨率\t节目时长\t节目大小\t节目状态\t作者\t更新时间\n" +
//                "id\tthumbnail\tprogram_name\tresolution_ratio\tduration\tprogram_size\tstatus\tauthor\tupdate_time";
//
        String table= "id\t什么表\t什么变化\t时间\n" +
                "id\ttable_name\tchange\ttime";


//

        String s = makeSqlCreate(table);
        System.out.println(s);

//        boolean none = isNone("");
//        System.out.println(none);

    }

//    public static void main(String[] args) {
////
//        String java = captureName("java");
//        System.out.println(java);
////        Java
//    }

    void jsonTest() {
//        ok
        String jsonStr = StrUtil.singleQuotationMarkToDouble("{'data':'java send'}");
        System.out.println("jsonStr");
        System.out.println(jsonStr);

        String result = "\n" +
                "{\n" +
                " \n" +
                "    \"success\":\"true\",\n" +
                " \n" +
                "    \"returnAddress\":\"123\"\n" +
                " \n" +
                "}";
        System.out.println("result right");
        System.out.println(result);


        JSONObject jsonObject = JSON.parseObject(jsonStr);
//        JSONObject jsonObject= JSON.parseObject(StrUtil.addBackslash("{data:java send}"));
        System.out.println(jsonObject);
//        JSON.toJSONString({"1":"2"})

        JSONObject jsonObject2 = JSON.parseObject(result);
        System.out.println(jsonObject2);
    }

    /**
     * 去掉指定字符串的开头的指定字符
     *
     * @param stream 原始字符串
     * @param trim   要删除的字符串
     * @return 这个是trim的前面部分，如果stream里面有的话，会被删掉，比如说
     * trim是124，stream是1234，那么前面一样的12 会被删掉，变成34
     */
    public static String StringStartTrim(String stream, String trim) {
        // null或者空字符串的时候不处理
        if (stream == null || stream.length() == 0 || trim == null || trim.length() == 0) {
            return stream;
        }
        // 要删除的字符串结束位置
        int end;
        // 正规表达式
        String regPattern = "[" + trim + "]*+";
        Pattern pattern = Pattern.compile(regPattern, Pattern.CASE_INSENSITIVE);
        // 去掉原始字符串开头位置的指定字符
        Matcher matcher = pattern.matcher(stream);
        if (matcher.lookingAt()) {
            end = matcher.end();
            stream = stream.substring(end);
        }
        // 返回处理后的字符串
        return stream;
    }

    public static String frontDelStr(String oldStr, String dontWant) {

        if (dontWant.equals("")) {
            return oldStr;
        }
        int oldStrLen = (oldStr).length();
        int dontWantLen = (dontWant).length();
        int minLen = Math.min(oldStrLen, dontWantLen);

        int iOld = 0;
        int iDont = 0;

        int iRes = 0;
        int iNow = 0;
        while (true) {


            if (!(oldStr.charAt(iOld) == dontWant.charAt(iDont))) {
                return oldStr.substring(iRes, oldStrLen - iRes);
            }

            iNow++;
            if (iNow - iRes == dontWantLen) {
                iRes += dontWantLen;

            }
            if (iDont == minLen - 1 || iOld == minLen - 1)
                return oldStr.substring(iRes, oldStrLen - iRes);

            iOld++;
            iDont++;

        }
    }


//  public static String getBasePath(HttpServletRequest request){
//        String path = request.getContextPath();
//      return request.getScheme()+"://"+request.getServerName()
//              +":"+request.getServerPort()+path+"/";
//    }

    /**
     * 从背后删除字符串，比如1234，我不要34，就会得到12.比如1234，我不要2，如果是
     * strip的话，就会把那个2也删掉的，但是我这个是直接忽略，因为最后没有2，返回的
     * 还是1234
     *
     * @param oldStr   原来的字符串
     * @param dontWant 不想要的字符串
     * @return
     */
    public static String rDelStr(String oldStr, String dontWant) {
        //todo ,completed
        if (dontWant.equals("")) {
            return oldStr;
        }
        int oldStrLen = (oldStr).length();
        int dontWantLen = (dontWant).length();
        int iOld = oldStrLen - 1;
        int iDont = dontWantLen - 1;

        int iRes = oldStrLen;
        int iNow = oldStrLen;
        while (true) {


            if (!(oldStr.charAt(iOld) == dontWant.charAt(iDont))) {
                return oldStr.substring(0, iRes);
            }

            iNow -= 1;
            if (iRes - iNow == dontWantLen) {
                iRes -= dontWantLen;

            }
            if (iDont == 0 || iOld == 0)
                return oldStr.substring(0, iRes);

            iOld--;
            iDont--;

        }

    }

    static void getLinesInPara(Place place) {

        System.out.println("get " + (place.equals(Place.BELOW) ? "below" : "up")
                + " lines ,input eof to stop input");
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        List<String> strings = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            if (s.equals("eof")) {
                break;
            }
            if ((i & 1) == (place.equals(Place.BELOW) ? 1 : 0)) {
                strings.add(s);
            }
            i++;
        }
        for (String s : strings) {
            System.out.println(s);
        }
    }

    static void getBelowLinesInPara() {
        getLinesInPara(Place.BELOW);

    }

    //    https://blog.csdn.net/sinaihalo/article/details/80908399
//    @WriteDataSource
    public static void updateEquipmentAssets(String tableName, List<String> keyList, List<String> valueList) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(tableName).append(" SET ");//equipmentassetstable
        for (int i = 0; i < keyList.size(); i++) {
            if (!"ID".equals(keyList.get(i))) {
                sb.append(keyList.get(i));
                sb.append("=");
                sb.append("'").append(valueList.get(i)).append("'");
                if (i != keyList.size() - 1) {
                    sb.append(",");
                }
            }

        }
        for (int i = 0; i < keyList.size(); i++) {
            if ("ID".equals(keyList.get(i))) {
                sb.append(" WHERE ID = '").append(valueList.get(i)).append("'");
            }
        }
        //jt.execute(sb.toString());
        System.out.println(sb);
    }

    public static void splitJointUpdateSql(String tableName, Map<String, String> setMap,
                                           Map<String, String> conditionMap) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(tableName).append(" SET ");//equipmentassetstable

//        https://www.cnblogs.com/damoblog/p/9124937.html
        int size = setMap.size();
        int i = 0;
        for (Map.Entry<String, String> entry : setMap.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            sb.append(mapKey).append("='").append(mapValue).append("'");
//            System.out.println(mapKey+":"+mapValue);
            if (i != size - 1) {
                sb.append(",");
            }
            i++;
        }
        i = 0;
        size = conditionMap.size();
        sb.append(" where ");
        for (Map.Entry<String, String> entry : conditionMap.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            sb.append(mapKey).append("=").append(mapValue);
            if (i != size - 1) {
                sb.append(" and ");
            }
            i++;
        }
//
//        for(int i=0;i<keyList.size();i++){
//            if(!"ID".equals(keyList.get(i))){
//                sb.append(keyList.get(i));
//                sb.append("=");
//                sb.append("'").append(valueList.get(i)).append("'");
//                if(i!=keyList.size()-1){
//                    sb.append(",");
//                }
//            }
//
//        }
//        for(int i=0;i<keyList.size();i++){
//            if("ID".equals(keyList.get(i))){
//                sb.append(" WHERE ID = '").append(valueList.get(i)).append("'");
//            }
//        }
        //jt.execute(sb.toString());
        System.out.println(sb);
    }


    public static StringBuilder listToStringBuilder(List<String> list, String suffix) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : list) {
            stringBuilder.append(string).append(suffix);
        }
        return stringBuilder;
    }

    public static StringBuilder listToStringBuilder(List<String> list, String suffix, Boolean endNo) {
        StringBuilder stringBuilder = new StringBuilder();
        if (endNo) {
            int size = list.size();
            int i = 0;

            for (String string : list) {
                stringBuilder.append(string);
                if (i != size - 1) stringBuilder.append(suffix);
                i++;
            }

        } else {

            for (String string : list) {
                stringBuilder.append(string).append(suffix);

            }
        }
        return stringBuilder;

    }

    public static StringBuilder oneLineJapaneseOneLineChinese(String japanese, String chinese) {
        String[] splitJapanese = japanese.split("\n");
        String[] splitChinese = chinese.split("\n");
        StringBuilder sb = new StringBuilder();
        int len = splitJapanese.length;
        for (int i = 0; i < len; i++) {
            sb.append(splitJapanese[i]).append("\n").append(splitChinese[i]);
        }
        return sb;
    }

    public static StringBuilder oneLineJapaneseOneLineChinese(List<String> japaneseList,
                                                              List<String> chineseList) {
//        String[] splitJapanese = japanese.split("\n");
//        String[] splitChinese = chinese.split("\n");
        StringBuilder sb = new StringBuilder();
        int len = japaneseList.size();
        for (int i = 0; i < len; i++) {
            sb.append(japaneseList.get(i)).append("\n").append(chineseList.get(i)).append("\n");
        }
        return sb;
    }

    //    https://www.runoob.com/java/java-scanner-class.html
    public static void oneLineJapaneseOneLineChineseShow() {
        Scanner scanner = new Scanner(System.in);
        List<String> japaneseList = new ArrayList<>();
        List<String> chineseList = new ArrayList<>();
        // 判断是否还有输入
        System.out.println("把一串日语和一串中文交错放置");
        System.out.println("输入一串日语，以eof结尾，并且回车");
        while (scanner.hasNextLine()) {
            String str1 = scanner.nextLine();
            if (str1.equals("eof")) break;
//            System.out.println("输入的数据为：" + str1);
            japaneseList.add(str1);
        }
        System.out.println("输入一串中文，以eof结尾，并且回车");
        while (scanner.hasNextLine()) {
            String str1 = scanner.nextLine();
            if (str1.equals("eof")) break;
//            System.out.println("输入的数据为：" + str1);
            chineseList.add(str1);
        }
        scanner.close();

        System.out.println(oneLineJapaneseOneLineChinese(japaneseList, chineseList));

    }

    static List<String> getBelowLines(String lines) {

        return getLines(lines, "below");
    }

    static List<String> getLines(String lines, String where) {
        String[] strings = lines.split("\n");
        List<String> belowLines = new ArrayList<>();

        int len = strings.length;
        for (int i = 0; i < len; i++) {
            if ((i & 1) == (where.equals("below") ? 1 : 0)) {
                belowLines.add(strings[i]);
            }
        }
        return belowLines;
    }

    static void and() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i & 1);
        }
    }

    /**
     * 数据库 是可以大写的
     * https://blog.csdn.net/xianrenmodel/article/details/110792291
     * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。
     * 例如：HelloWorld->HELLO_WORLD
     *
     * @param name 转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String underscoreName(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            // 将第一个字符处理成大写
            result.append(name.substring(0, 1).toUpperCase());
            // 循环处理其余字符
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // 其他字符直接转成大写
                result.append(s.toUpperCase());
            }
        }
        return result.toString();
    }


    /**
     * 数据库 是可以大写的
     * https://blog.csdn.net/xianrenmodel/article/details/110792291
     * 将驼峰式命名的字符串转换为下划线小写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。
     * 例如：HelloWorld->hello_world
     *
     * @param name 转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String underscoreNameLower(String name) {
        String underscoreName = underscoreName(name);
        return underscoreName.toLowerCase();

    }


//    public static boolean checkTimeStr(String timeStr) {
//        Date date = TimeUtil.stringtoDate(timeStr, TimeUtil.fmtYmd);
//        if (date == null) {
//            UiUtil.showError("日期格式异常");
//            return false;
//        }
//        return true;
//    }


    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。  * 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母小写
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String camels[] = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }


    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String upperCaseFirst(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param str
     * @return
     */
    public static String lowerCaseFirst(String str) {
        //2019-2-10 解决StringUtils.lowerCaseFirst潜在的NPE异常@liutf
        return (str != null && str.length() > 1) ? str.substring(0, 1).toLowerCase() + str.substring(1) : "";
    }

//    驼峰 转化为 下划线

    /**
     * 下划线，转换为驼峰式
     *
     * @param underscoreName
     * @return
     */
    public static String underlineToCamelCase(String underscoreName) {
        StringBuilder result = new StringBuilder();
        if (underscoreName != null && underscoreName.trim().length() > 0) {
            boolean flag = false;
            for (int i = 0; i < underscoreName.length(); i++) {
                char ch = underscoreName.charAt(i);
                if ("_".charAt(0) == ch) {
                    flag = true;
                } else {
                    if (flag) {
                        result.append(Character.toUpperCase(ch));
                        flag = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();
    }
    public static String underlineToPascal(String underscoreName) {
        String camelCase = underlineToCamelCase(underscoreName);
        return  upperCaseFirst(camelCase);

    }


//    public static boolean isNotNull(String str){
////        return org.apache.commons.lang3.StringUtils.isNotEmpty(str);
//        return false;
//    }

    static void test6() {
        String name_up_lo = camelName("_name_up_lo");
//       String name_up_lo = camelName("_name_up_lo");
        String name_up_lo2 = camelName("_name_up_lo_");
        String name_up_lo3 = camelName("_name__up_lo_");
        String name_up_lo4 = camelName("_na_me_up_lo_");
        String name_up_lo5 = camelName("na_me_up_lo");

        System.out.println("name_up_lo");
        System.out.println(name_up_lo);


        System.out.println("name_up_lo2");
        System.out.println(name_up_lo2);

        System.out.println("name_up_lo3");
        System.out.println(name_up_lo3);

        System.out.println("name_up_lo4");
        System.out.println(name_up_lo4);

        System.out.println("name_up_lo5");
        System.out.println(name_up_lo5);

//        name_up_lo
//                nameUpLo
//        name_up_lo2
//                nameUpLo
//        name_up_lo3
//                nameUpLo
//        name_up_lo4
//                naMeUpLo
//        name_up_lo5
//                naMeUpLo
    }

    public  static boolean isNone(String string){
        return  string==null || string.equals("");
    }
    public static void main(String[] args) {
        test6();
    }

    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
