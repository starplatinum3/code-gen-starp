package top.starp.util;

import java.lang.reflect.Field;

public class DateTimeParams {
    Integer year;
    Integer month;
    Integer day;
    Integer hour;
    Integer minute;
    Integer second;

    public static void main(String[] args) {
        Class<?> clazz =  DateTimeParams.class;
        // 获取Class对象
//        Class<?> clazz = DateTimeParams.getClass();

        // 获取所有声明的字段
        Field[] fields = clazz.getDeclaredFields();

        // 遍历字段并输出字段名和类型
        for (Field field : fields) {
            String fieldName = field.getName();
            Class<?> fieldType = field.getType();

//            System.out.println("Field name: " + fieldName);
//            System.out.println("Field type: " + fieldType.getSimpleName());
            String fieldTypeSimpleName = fieldType.getSimpleName();
//            System.out.println("------------------------");
            String pascalCase = StringUtils.toPascalCase(fieldName);

//         String  tpl=   "public {fieldTypeSimpleName} get{pascalCase}() {\n" +
//                    "        return {fieldName};\n" +
//                    "    }";

            String  tpl=   "public {fieldTypeSimpleName} get{pascalCase}OrDefault() {\n" +
                    "        if({fieldName}==null){\n" +
                    "            return 0;\n" +
                    "        }\n" +
                    "        return {fieldName};\n" +
                    "    }";
            String code = tpl.replace("{pascalCase}", pascalCase)
                    .replace("{fieldName}", fieldName)
                    .replace("{fieldTypeSimpleName}", fieldTypeSimpleName)
                    ;
            System.out.println(code);
//                            .replace("{pascalCase}",pascalCase)
        }
    }

    public Integer getYearOrDefault() {
        if(year==null){
            return 0;
        }
        return year;
    }
    public Integer getMonthOrDefault() {
        if(month==null){
            return 0;
        }
        return month;
    }
    public Integer getDayOrDefault() {
        if(day==null){
            return 0;
        }
        return day;
    }
    public Integer getHourOrDefault() {
        if(hour==null){
            return 0;
        }
        return hour;
    }
    public Integer getMinuteOrDefault() {
        if(minute==null){
            return 0;
        }
        return minute;
    }
    public Integer getSecondOrDefault() {
        if(second==null){
            return 0;
        }
        return second;
    }

    public DateTimeParams() {
    }



    public DateTimeParams(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public Integer getYear() {
        return year;
    }

//    public Integer getYearOrDefault() {
//        if(year==null){
//            return 0;
//        }
//        return year;
//    }
    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public static final class DateTimeParamsBuilder {
        private Integer year;
        private Integer month;
        private Integer day;
        private Integer hour;
        private Integer minute;
        private Integer second;

        private DateTimeParamsBuilder() {
        }

        public static DateTimeParamsBuilder aDateTimeParams() {
            return new DateTimeParamsBuilder();
        }

        public DateTimeParamsBuilder withYear(Integer year) {
            this.year = year;
            return this;
        }

        public DateTimeParamsBuilder withMonth(Integer month) {
            this.month = month;
            return this;
        }

        public DateTimeParamsBuilder withDay(Integer day) {
            this.day = day;
            return this;
        }

        public DateTimeParamsBuilder withHour(Integer hour) {
            this.hour = hour;
            return this;
        }

        public DateTimeParamsBuilder withMinute(Integer minute) {
            this.minute = minute;
            return this;
        }

        public DateTimeParamsBuilder withSecond(Integer second) {
            this.second = second;
            return this;
        }

        public DateTimeParams build() {
            DateTimeParams dateTimeParams = new DateTimeParams();
            dateTimeParams.setYear(year);
            dateTimeParams.setMonth(month);
            dateTimeParams.setDay(day);
            dateTimeParams.setHour(hour);
            dateTimeParams.setMinute(minute);
            dateTimeParams.setSecond(second);
            return dateTimeParams;
        }
    }
}
