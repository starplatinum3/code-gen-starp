package top.starp.util;

import com.example.demo.common.MysqlDataType;

public class JavaDataType {


//       public  static  final    String varchar="varchar";
//       public  static  final    String String="String";
//       public  static  final    String Date="Date";
//    public  static  final     String intType="int";
//    public  static  final       String tinyint="tinyint";
//    public  static  final      String longtext="longtext";
//    public  static  final      String datetime="datetime";
//    public  static  final       String doubleType="double";
//    public  static  final      String charType="char";
//    public  static  final      String bigint="bigint";
//    public  static  final      String enumType="enum";
//    public  static  final      String date="date";
//    。。。

    //    JavaDataType .java
    public  static  final    String varchar="varchar";
    public  static  final    String String="String";
    //       public  static  final    String String="String";
    public  static  final    String Date="Date";
    public  static  final      String javaUtilDate="java.util.Date";
    public  static  final      String booleanType="boolean";
//boolean
//    Boolean
    public  static  final     String intType="int";
    public  static  final       String tinyint="tinyint";
    public  static  final      String longtext="longtext";
    public  static  final      String datetime="datetime";
    public  static  final       String doubleType="double";
    public  static  final      String charType="char";
    public  static  final      String bigint="bigint";
    public  static  final      String enumType="enum";
    public  static  final      String date="date";
    public  static  final      String Integer="java.lang.Integer";
    public  static  final      String Double="java.lang.Double";
    public  static  final      String Float="java.lang.Float";
    public  static  final      String Boolean="java.lang.Boolean";
    public  static  final      String javaLangString="java.lang.String";
    public  static  final      String BigDecimal= "java.math.BigDecimal";
//     "java.math.BigDecimal":
//    String
//    Date
//    Float
    public static  boolean isInt(String dateTypeName){
       return JavaDataType.intType.equals( dateTypeName)
                || JavaDataType.Integer.equals( dateTypeName);
    }
    public static  boolean isBool(String dateTypeName){
        return JavaDataType.booleanType.equals( dateTypeName)
                || JavaDataType.Boolean.equals( dateTypeName);
    }
    public  static  String toMysqlDataType(String dateTypeName){
        if (isInt(dateTypeName)) {
            return "int(8)";
        }
        if(isBool(dateTypeName)){
            return  "tinyint(1)";
        }
        if(Date.equals(dateTypeName)){
            return "datetime";
//            return   MysqlDataType.datetime;
        }
        if(BigDecimal.equals(dateTypeName)){
            return "decimal(18,2)";
        }

        return "VARCHAR(32)";
    }
}
