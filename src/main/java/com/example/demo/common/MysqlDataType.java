package com.example.demo.common;


import top.starp.util.JavaDataType;

/**
 * DATA_TYPE_Map.put("varchar","String");
 * DATA_TYPE_Map.put("int","Integer");
 * DATA_TYPE_Map.put("tinyint","Integer");
 * DATA_TYPE_Map.put("longtext","String");
 * DATA_TYPE_Map.put("text","String");
 * DATA_TYPE_Map.put("datetime","Date");
 * DATA_TYPE_Map.put("double","Double");
 * DATA_TYPE_Map.put("char","String");
 * DATA_TYPE_Map.put("bigint","Integer");
 * DATA_TYPE_Map.put("enum","String");
 * DATA_TYPE_Map.put("float","Float");
 * DATA_TYPE_Map.put("longblob","String");
 * //          DATA_TYPE_Map.put(MysqlDataType.date,"Date");
 * DATA_TYPE_Map.put(MysqlDataType.date, JavaDataType.Date);
 */
public class MysqlDataType {
public  static String toConverterFuncName(String mysqlDataType){
    if (intType.equals(mysqlDataType)) {
        return "Int";
    }
    if (doubleType.equals(mysqlDataType)) {
        return "Double";
    }
    if (floatType.equals(mysqlDataType)) {
        return "Float";
    }
//    isNumberType()
    if(isTimeType(mysqlDataType)){
        return "Date";
    }
//    if (date.equals(mysqlDataType)) {
//        return "Date";
//    }
//    if (.equals(mysqlDataType)) {
//        return "Bool";
//    }
    if (bigint.equals(mysqlDataType)) {
        return "Long";
    }

    if ( isTextType(mysqlDataType)) {
        return "Str";
    }
    return "Str";
//    if (intType.equals(mysqlDataType)) {
//        return "Int";
//    }
//    JavaDataType.b
//    JavaDataType.BigDecimal
}

    public static final String intType = "int";
    public static final String tinyint = "tinyint";
    public static final String doubleType = "double";
    public static final String bigint = "bigint";
    public static final String floatType = "float";

    void d() {

    }

    public static final String charType = "char";
    public static final String varchar = "varchar";
    public static final String text = "text";
    public static final String longtext = "longtext";
    public static final String decimal = "decimal";
    public static final String longblob = "longblob";

    public static final String datetime = "datetime";
    public static final String date = "date";

    public static final String enumType = "enum";


    public static String[] numberTypeArr = new String[]{
            intType
            , tinyint
            , doubleType
            , bigint
            , floatType
    };
    public static String[]timeTypeArr = new String[]{
            date
            , datetime
    };
    public static String[] textTypeArr = new String[]{
            charType
            , varchar
            , text
            , longtext
            , decimal
            , longblob
    };
    public static boolean isTimeType(String DATA_TYPE) {
        for (String numberType : MysqlDataType.timeTypeArr) {
//            if (numberType.equals(DATA_TYPE)) {
//                return true;
//            }
            if (numberType.equalsIgnoreCase(DATA_TYPE)) {
                return true;
            }
        }
        return false;

    }

    public static void main(String[] args) {
        String s = "int".toUpperCase();
        System.out.println(s);
//        INT
    }
    public static boolean isNumberType(String DATA_TYPE) {
        for (String numberType : MysqlDataType.numberTypeArr) {
//            String numberTypeUpper = numberType.toUpperCase();
            if (numberType
                    .equalsIgnoreCase(DATA_TYPE)) {
                return true;
            }

//            if (numberType.toUpperCase()
//                    .equals(DATA_TYPE.toUpperCase())) {
//                return true;
//            }
        }
        return false;
//        return
//                MysqlDataType.intType.equals(DATA_TYPE)
//                ||          MysqlDataType.decimal.equals(DATA_TYPE)
//                ||          MysqlDataType.date.equals(DATA_TYPE)
//                ;

    }

    public static boolean isTextType(String DATA_TYPE) {
        for (String numberType : MysqlDataType.textTypeArr) {
            if (numberType.equals(DATA_TYPE)) {
                return true;
            }
        }
        return false;
//        return
//                MysqlDataType.intType.equals(DATA_TYPE)
//                ||          MysqlDataType.decimal.equals(DATA_TYPE)
//                ||          MysqlDataType.date.equals(DATA_TYPE)
//                ;

    }
}
