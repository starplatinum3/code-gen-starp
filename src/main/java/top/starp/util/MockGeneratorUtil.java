 package top.starp.util;

 import com.example.demo.util.DataTypeMap;
 import com.example.demo.util.codeGen.ColumnInfo;
 import com.google.gson.JsonObject;
// import org.apache.commons.lang3.StringUtils;
// import uk.co.jemos.podam.api.PodamFactory;
// import uk.co.jemos.podam.api.PodamFactoryImpl;
// import uk.co.jemos.podam.exceptions.PodamMockeryException;
 import java.lang.reflect.Field;
 import java.math.BigDecimal;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.Date;
 import java.util.List;

 public class MockGeneratorUtil {

     // private static final PodamFactory PODAM_FACTORY = new PodamFactoryImpl();

     public static String genJsonMock(List<ColumnInfo> columnInfos) {
         if (columnInfos == null) {
             return "{}";
         }

         JsonObject jsonObject = new JsonObject();

         for (ColumnInfo columnInfo : columnInfos) {
             String javaFieldName = columnInfo.getJavaFieldName();
             String type = columnInfo.getDATA_TYPE();

//             System.out.println("type");
//             System.out.println(type);
             Object mockValue = null;
             // try {
             //     Class<?> fieldType = Class.forName(columnInfo.getJavaType());
             //     mockValue = PODAM_FACTORY.manufacturePojo(fieldType);
             // } catch (ClassNotFoundException e) {
             //     mockValue = null;
             // } catch (PodamMockeryException e) {
             //     mockValue = null;
             // }

             if (type.equalsIgnoreCase("VARCHAR")) {
                 if ("name".equalsIgnoreCase(javaFieldName)) {
                     String[] names = {"Alice", "Bob", "Charlie", "David", "Ethan"};
                     mockValue = names[(int) (Math.random() * names.length)];
                 } else {
                     // mockValue = StringUtils.EMPTY;
                     mockValue =RandomStringUtils.generateRandomString();
                 }
             } else if ( columnInfo.isNumberType()) {
//                 type.equalsIgnoreCase("INT")
//                 DataTypeMap.
//                 columnInfo.isNumberType()
//                 DataType
                 if ("age".equalsIgnoreCase(javaFieldName)) {
                     int ageGroup = (int) (Math.random() * 3);
                     switch (ageGroup) {
                         case 0:
                             mockValue = (int) (Math.random() * 18) + 1;
                             break;
                         case 1:
                             mockValue = (int) (Math.random() * 20) + 18;
                             break;
                         case 2:
                             mockValue = (int) (Math.random() * 80) + 38;
                             break;
                     }
                 } else {
                     mockValue = (int) (Math.random() * 100);
                 }
//                 System.out.println("mockValue int");
//                 System.out.println(mockValue);

//                 type.equalsIgnoreCase("DATE")
             } else if ( columnInfo.isDateType()) {
                 mockValue = new Date();
//                 type.is
             } else if (type.equalsIgnoreCase("BOOLEAN")) {
                 mockValue = true;
             } else if (type.equalsIgnoreCase("ENUM")) {
                 try {
                   String colName=  columnInfo.getJavaFieldName();
                 //   columnInfo.getJavaType()
                     Field field = Class.forName(colName).getDeclaredField("$VALUES");
                     field.setAccessible(true);
                     Object[] values = (Object[]) field.get(null);
                     mockValue = values[(int) (Math.random() * values.length)];
                 } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
                     mockValue = null;
                 }
             } else if (type.equalsIgnoreCase("DECIMAL")) {
                 mockValue = BigDecimal.valueOf(Math.random() * 100).setScale(2, BigDecimal.ROUND_HALF_UP);
             } else if (type.equalsIgnoreCase("DOUBLE")) {
                 mockValue = Math.random() * 100;
             } else if (type.equalsIgnoreCase("FLOAT")) {
                 mockValue = (float) (Math.random() * 100);
             } else {
//                 mockValue = null;
                 String randomString = RandomStringUtils.generateRandomString();
                 mockValue =randomString;
//                 MockGeneratorUtil.g
//                 RandomUtil.randomInt()
             }

             if (mockValue != null) {
                 jsonObject.addProperty(javaFieldName, mockValue.toString());
             } else {
                 jsonObject.addProperty(javaFieldName, "");
             }
         }

         return jsonObject.toString();
     }

     public static void main(String[] args) {
         // 创建一个列信息列表，包含三个列：id、name、age
         List<ColumnInfo> columnInfos = new ArrayList<>();
         columnInfos.add(new ColumnInfo("id", "INT", "id"));
         columnInfos.add(new ColumnInfo("name", "VARCHAR", "name"));
         columnInfos.add(new ColumnInfo("age", "INT", "age"));

         // 生成 10 个 JSON 对象的模拟数据，并打印输出
         for (int i = 1; i <= 10; i++) {
             String json = genJsonMock(columnInfos);
             System.out.println("Json " + i + ": " + json);
         }
     }

     // private static final List<String> STATES = Arrays.asList("New York", "
     private static final List<String> STATES = Arrays.asList("New York", "California", "Texas", "Florida", "Ohio");

     private static final List<String> NAMES = Arrays.asList("Alice", "Bob", "Charlie", "David", "Ethan");

     private static final String[] LEVELS = {"1st grade", "2nd grade", "3rd grade", "4th grade", "5th grade",
             "6th grade", "7th grade", "8th grade", "9th grade", "10th grade", "11th grade", "12th grade",
             "Undergraduate", "Master's degree", "Doctoral degree"};

     private static final String[] SUBJECTS = {"Mathematics", "Physics", "Chemistry", "Biology", "History", "Geography",
             "Literature", "Music", "Art", "Physical education"};

     static {
         Arrays.sort(LEVELS);
         Arrays.sort(SUBJECTS);
     }

 }
