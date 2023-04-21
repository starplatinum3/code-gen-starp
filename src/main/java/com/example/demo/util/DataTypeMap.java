package com.example.demo.util;

public class DataTypeMap {
    public  static String mysqlToTs(String dataType){
        if("varchar".equals(dataType)){
            return "string";
        }
        if("int".equals(dataType)){
            return "number";
        }
        if("datetime".equals(dataType)){
            return "Date";
        }

        if("tinyint".equals(dataType)){
            return "number";
        }
        if("longtext".equals(dataType)){
            return "string";
        }

        if("double".equals(dataType)){
            return "number";
        }
        if("char".equals(dataType)){
            return "string";
        }
        if("bigint".equals(dataType)){
            return "number";
        }
        if("enum".equals(dataType)){
            return "string";
        }
        return  "string";

    }

    /**
     * String: Java字符串。它的默认大小取决于基础后端（如果使用JPA，默认情况下为255），但是您可以使用校验规则进行更改（例如，修改 max大小为1024）。
     * Integer: Java整数。
     * Long: Java长整数。
     * Float: Java浮点数.
     * Double: Java双精度浮点数.
     * BigDecimal: java.math.BigDecimal对象, 当您需要精确的数学计算时使用（通常用于财务操作）。
     * LocalDate: java.time.LocalDate对象, 用于正确管理Java中的日期。
     * Instant: java.time.Instant对象, 用于表示时间戳，即时间线上的瞬时点。
     * ZonedDateTime: java.time.ZonedDateTime对象, 用于表示给定时区（通常是日历中会议、约定）中的本地日期时间。请注意，REST和持久层都不支持时区，因此您很可能应该使用Instant。
     * Duration: java.time.Duration对象, 用于表示时间量。
     * UUID: java.util.UUID对象.
     * Boolean: Java布尔型.
     * Enumeration:Java枚举对象。选择此类型后，子生成器将询问您要在枚举中使用哪些值，并将创建一个特定的enum类来存储它们。
     * Blob: Blob对象，用于存储一些二进制数据。选择此类型时，子生成器将询问您是否要存储通用二进制数据，图像对象或CLOB（长文本）。图像将专门在Angular侧进行优化处理，因此可以将其正常显示给最终用户。
     * @param dataType
     * @return
     */
    public  static String mysqlToJdl(String dataType){
        if("varchar".equals(dataType)){
            return "String";
        }
        if("int".equals(dataType)){
            return "Integer";
        }
        if("datetime".equals(dataType)){
            return "LocalDate";
        }

        if("tinyint".equals(dataType)){
            return "Integer";
        }
        if("longtext".equals(dataType)){
            return "String";
        }

        if("double".equals(dataType)){
            return "Double";
        }
        if("char".equals(dataType)){
            return "String";
        }
        if("bigint".equals(dataType)){
            return "Long";
        }
        if("enum".equals(dataType)){
            return "String";
        }
        return  "String";

    }
}
