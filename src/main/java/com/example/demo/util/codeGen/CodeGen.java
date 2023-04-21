package com.example.demo.util.codeGen;

//import cn.hutool.core.date.DateUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.*;
//import com.gm.wj.WjApplication;
//import com.gm.wj.entity.ColumnInfo;
import lombok.Data;
import org.assertj.core.util.DateUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Data
public class CodeGen {


//    public static void main(String[] args) {
////        List<String> tableNames=new ArrayList<>();
////        List<String> tableNames= Arrays.asList("plan","time_slot","program");
////        int frontIdx=0;
////        int linkIdx=1;
////        int endIdx=2;
////        String whereCode=" where ";
////        for (int i = 0; i < tableNames.size(); i++) {
////            String frontTable = tableNames.get(frontIdx);
////            String linkTable = tableNames.get(linkIdx);
////            String backTable = tableNames.get(endIdx);
////            String  linkTableName=frontTable+"_"+backTable;
////            whereCode+="#frontTable#.id = #linkTableName#.#frontTable#_id ";
////            whereCode+="#linkTableName#. #endTable#_id = #endTable#.id ";
////        }
//        List<String> tableNames= Arrays.asList("plan","time_slot","program");
//        String whereCode = makeWhereCode(tableNames);
//        String selectCode = makeSelectCode(tableNames);
//        String fromCode = makeFromCode(tableNames);
//        System.out.println(selectCode+fromCode+whereCode);
//    }

    java.nio.file.Path pathFile;
    //    java.nio.file.Path pathFileString;
//   public  static   String pathFileString="D:\\codeGen";
    public static String pathFileString = "C:\\codeGen";
    //  static   String 包名 = "com.example.demo";
//  static   String 包名 = "com.exam";
//    static String 包名 = "com.example.demo";
//    static String packageName = "com.example.demo";
//    static String packageName = "com.ruoyi.system";
//    static String packageName = "com.example.demo";
    static String packageName = "com.starp.exam";
    static String 包名 = packageName;
//    this.ca
//    this.

    String author = "starp";

    //   public static final boolean doWriteFile=false;
    public static final boolean doWriteFile = true;

//    String findFirstRows(String tableName, String 类名 ,
//                         List<ColumnInfo> columnInfos){
//        String  res="";
//        for (ColumnInfo columnInfo : columnInfos) {
//            String column_name = columnInfo.getCOLUMN_NAME();
////           ColumnInfo findFirstByCHARACTER_SET_NAME(String CHARACTER_SET_NAME);
//        }
//    }

    static String returnType = "ReturnT";

    public String genController(String tableName,
                                List<ColumnInfo> columnInfos) throws Exception {
//    包名字
//    tableName.tit
//    java title
//
//       StrUtil.u

        String CamelCaseName = StringUtils.underlineToCamelCase(tableName);
//       String 类名 = StrUtil.captureName(tableName);
        String 类名 = StringUtils.captureName(CamelCaseName);

//        TableInfo tableInfo=new TableInfo() ;
        TableInfo tableInfo = new TableInfo(tableName, columnInfos);
        String controllerCode = tableInfo.genController(包名);
//        String jsonDefaultNull = tableInfo.genJsonDefaultNull();
//        String iViewColumns = tableInfo.genIViewColumns();
//        String genIViewEdit = tableInfo.genIViewEdit();
//        String genIViewManage = tableInfo.genIViewManage();
//        String columnsDicMap = tableInfo.genColumnsDicMap();
////        System.out.println("jsonDefaultNull");
////        System.out.println(jsonDefaultNull);
////        System.out.println("iViewColumns");
////        System.out.println(iViewColumns);
////        System.out.println("genIViewEdit");
////        System.out.println(genIViewEdit);
//        System.out.println("genIViewManage");
//        System.out.println(genIViewManage);
//        System.out.println("columnsDicMap");
//        System.out.println(columnsDicMap);


//        Date now = DateUtil.now();
//        String ymdhms = DateUtilSt.dateToString(now, DateUtilSt.ymdhms);

//
//        code = code.replace("#类名#", 类名)
//                .replace("#包名#", 包名)
//                .replace("#description#", CamelCaseName)
//                .replace("#author#", author)
////                .replace("#date#", DateUtilSt.dateToString(DateUtil.now(),DateUtilSt.ymdhms));
//                .replace("#date#", ymdhms)
//                .replace("#FindFirstRows#", genFindFirstRows)
//        ;

//     String RepositoryFileName=  类名+"Repository.java";


        String javaFileName = 类名 + Suffix.Controller + ".java";
//       String javaFileNameAbs=  包路径+"/"+javaFileName;
//        System.out.println("javaFileName");
//        System.out.println(javaFileName);
        String rootDir = pathFile.toString();
        java.nio.file.Path javaFileNameAbs = Paths.get(rootDir, "jpa", "controller", javaFileName);
//        java.nio.file.Path javaFileNameAbs = Paths.get(包路径,"controller", javaFileName);
//        System.out.println("javaFileNameAbs");
//        System.out.println(javaFileNameAbs);
//        String javaFileNameAbsStr = javaFileNameAbs.toString();
//        System.out.println("javaFileNameAbsStr");
//        System.out.println(javaFileNameAbsStr);

        if (doWriteFile) {
            FileUtil.writeCode(javaFileNameAbs, controllerCode);
//            FileUtil.renameIf存在(javaFileNameAbs.toString());
//            System.out.println("写文件 "+javaFileNameAbsStr);
//            try(FileWriter fileWriter=new FileWriter(javaFileNameAbsStr)){
//                fileWriter.write(controllerCode);
//            }
        }

        return controllerCode;
    }

    /**
     * 可以运行 但是是不是放到 back 更加合适呢
     *
     * @param tableName
     * @return
     * @throws IOException
     */
    public String genJpaRepository(String tableName,
                                   List<ColumnInfo> columnInfos) throws IOException {
//    包名字
//    tableName.tit
//    java title
//
//       StrUtil.u

        String CamelCaseName = StringUtils.underlineToCamelCase(tableName);
//       String 类名 = StrUtil.captureName(tableName);
        String 类名 = StringUtils.captureName(CamelCaseName);

        TableInfo tableInfo = new TableInfo();
        tableInfo.setColumnInfos(columnInfos);
        tableInfo.setTableName(tableName);
        tableInfo.set类名(类名);
        String genFindFirstRows = tableInfo.genFindFirstRows();

        String code = "package #包名#.repository;\n" +
                "\n" +
                "import #包名#.entity.#类名#;\n" +
                "\n" +
                "import java.util.List;\n" +
                "import java.util.Date;\n" +
                "import org.springframework.data.jpa.repository.JpaRepository;\n" +
                "import org.springframework.data.jpa.repository.JpaSpecificationExecutor;\n" +
                "import org.springframework.data.jpa.repository.Query;\n" +
                "import org.springframework.stereotype.Repository;\n" +
                "/**\n" +
                " * @description  #description#\n" +
                " * @author #author#\n" +
                " * @date #date#\n" +
                " */\n" +
                "@Repository\n" +
                "public interface #类名#Repository extends JpaRepository<#类名#,Integer> ,\n" +
                "        JpaSpecificationExecutor<#类名#> {\n" +
                "\n" +
                "#FindFirstRows#" +
                "\n" +
                "}\n";
        Date now = DateUtil.now();
        String ymdhms = DateUtilSt.dateToString(now, DateUtilSt.ymdhms);


        code = code.replace("#类名#", 类名)
                .replace("#包名#", 包名)
                .replace("#description#", CamelCaseName)
                .replace("#author#", author)
//                .replace("#date#", DateUtilSt.dateToString(DateUtil.now(),DateUtilSt.ymdhms));
                .replace("#date#", ymdhms)
                .replace("#FindFirstRows#", genFindFirstRows)
        ;

//     String RepositoryFileName=  类名+"Repository.java";


        String javaFileName = 类名 + Suffix.Repository + ".java";
//       String javaFileNameAbs=  包路径+"/"+javaFileName;
//       System.out.println("javaFileName");
//       System.out.println(javaFileName);
        String rootDir = pathFile.toString();
        java.nio.file.Path javaFileNameAbs = Paths.get(rootDir, "jpa", "repository", javaFileName);
//       java.nio.file.Path javaFileNameAbs = Paths.get(包路径,"repository", javaFileName);
//       System.out.println("javaFileNameAbs");
//       System.out.println(javaFileNameAbs);
//       String javaFileNameAbsStr = javaFileNameAbs.toString();
//       System.out.println("javaFileNameAbsStr");
//       System.out.println(javaFileNameAbsStr);

        if (doWriteFile) {

            FileUtil.writeCode(javaFileNameAbs, code);
//           FileUtil.renameIf存在(javaFileNameAbs.toString());
//           System.out.println("写文件 "+javaFileNameAbsStr);
//           try(FileWriter fileWriter=new FileWriter(javaFileNameAbsStr)){
//               fileWriter.write(code);
//           }
        }

//        String backUpFilenameAbs=backUpDir+javaFileName;
//       java.nio.file.Path backUpFilenameAbs = Paths.get(backUpDir, javaFileName);
//       boolean exists = backUpFilenameAbs.toFile().exists();
//     String backUpPathStr=  backUpFilenameAbs.toString();
//       if(exists){
////           String notExistFileName = FileUtil.generateNotExistFileName(backUpFilenameAbs.toString());
//           backUpPathStr= FileUtil.generateNotExistFileName(backUpPathStr);
////           FileUtil.renameIf存在(backUpFilenameAbs.toString());
////           System.out.println("notExistFileName");
////           System.out.println(notExistFileName);
//           System.out.println("backUpPathStr");
//           System.out.println(backUpPathStr);
//       }
//       FileUtil

//     re

//        jpa
//        System.out.println(s);
        return code;
    }

    public static String genBatchInsert() {
        return null;
    }

    public void jsonLoopMakeJavaClassCode(Object object) {

        if (object instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) object;
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                Object o = entry.getValue();
                if (o instanceof String) {
//                    javaClassCode+="String "+entry.getKey()+" ;\n";
//                    javaClassCode+= String.format("     String %s = \"%s\";\n",entry.getKey(),entry.getValue() );
//                    javaClassCode.append(
//                            String.format("     String %s = \"%s\";\n",
//                                    entry.getKey(),entry.getValue()
//                            ));
                    javaClassCodeBody.append(
                            String.format("     String %s = \"%s\";\n",
                                    entry.getKey(), entry.getValue()
                            ));

                    String fieldName = entry.getKey();
                    Object fieldVal = entry.getValue();
                    fromMapCodeBody.append(
                            String.format("    %s = (String)map.get(\"%s\");\n",
                                    fieldName, fieldName
                            ));
//                    System.out.println("String "+entry.getKey()+" ;");
//                    System.out.println("key:" + entry.getKey() + "，value:" + entry.getValue());
                } else {
                    jsonLoopMakeJavaClassCode(o);
                }
            }
        }
        if (object instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) object;
            for (Object o : jsonArray) {
                jsonLoopMakeJavaClassCode(o);
            }
        }
    }


    //    String javaClassCode="public class  ClassName { \n";
    StringBuilder javaClassCodeBody = new StringBuilder();
//    StringBuilder javaClassCode=new StringBuilder("" +
//        "import lombok.*;\n" +
//        "import javax.persistence.Entity;\n" +
//        "import javax.persistence.Table;\n" +
//        "\n" +
//        "@Data\n" +
//        "@ToString\n" +
//        "@AllArgsConstructor\n" +
//        "@NoArgsConstructor\n" +
//        "@Builder\n" +
//        "@Entity\n" +
//        "@Table\n" +
//        "public class  ClassName { \n");

    String mapListCode = "List<#className#> list = maps.stream().map(map -> {\n" +
            "            #className# obj = new #className#();\n" +
            "            obj.fromMap(map);\n" +
            "            return obj;\n" +
            "        }).collect(Collectors.toList());";

//    String insertBatchCode="<insert id=\"insertBatch\">\n" +
//            "    INSERT INTO #tableName# (#cols# node_id,`type`,perf_time,pm25_h,pm10_h,temp_h,humi_h,co2_h,tvoc_h)\n" +
//            "    VALUES\n" +
//            "    <foreach collection=\"list\" close=\"\" index=\"index\" item=\"item\" open=\"\" separator=\",\">\n" +
//            "      (#values# #{item.nodeId},#{item.type},#{item.perfTime},#{item.pm25H},#{item.pm10H},#{item.tempH},#{item.humiH},#{item.co2H},#{item.tvocH})\n" +
//            "    </foreach>\n" +
//            "  </insert>";

    public static String genInsertBatchCodeMybatis(List<String> cols, String tableName) {
        String insertBatchCode = "<insert id=\"insertBatch\">\n" +
                "    INSERT INTO #tableName# (#cols# )\n" +
                "    VALUES\n" +
                "    <foreach collection=\"list\" close=\"\" index=\"index\" item=\"item\" open=\"\" separator=\",\">\n" +
                "      (#values#)\n" +
                "    </foreach>\n" +
                "  </insert>";
//        String colsList=
//        for (String col : cols) {
//            col
//        }
//        String colsList = String.join(",", cols);
//        String colsList = String.join(",", cols);
        String colsList = cols.stream().map(o -> "`" + o + "`")
                .collect(Collectors.joining(","));
        String values = cols.stream().map(o -> {
            return String.format("#{item.%s}", o);
        }).collect(Collectors.joining(","));
        return insertBatchCode.replace("#cols#", colsList)
                .replace("#values#", values)
                .replace("#tableName#", tableName)
                ;
    }

    String javaClassCode = "" +
            "import lombok.*;\n" +
            "import javax.persistence.Entity;\n" +
            "import javax.persistence.Table;\n" +
            "\n" +
            "@Data\n" +
            "@ToString\n" +
            "@AllArgsConstructor\n" +
            "@NoArgsConstructor\n" +
            "@Builder\n" +
            "@Entity\n" +
            "@Table\n" +
            "public class  ClassName { " +
            "\n" +
            "%s\n" +
            "%s" +
            "\n" +
            "}\n";

    //    StringBuilder fromMapCode=new StringBuilder(" public  void fromMap(Map<String ,Object>map){\n" +
//            "         %s\n" +
//            "     }");
    StringBuilder fromMapCodeBody = new StringBuilder();
    String fromMapCode = " public  void fromMap(Map<String ,Object>map){\n" +
            "         %s\n" +
            "     }";

    public String jsonToJavaCode(String json) {
        JSONObject jsonObject = JsonUtil.stringToJson(json);
//        jsonObject.forEach(o->o.ge);
        jsonLoopMakeJavaClassCode(jsonObject);
//        javaClassCode.append("\n } ");
        fromMapCode = String.format(fromMapCode, fromMapCodeBody);

        javaClassCode = String.format(javaClassCode, javaClassCodeBody, fromMapCode);
//        javaClassCode+="\n } ";

        return "";
    }

    static String makeFromCode(List<String> tableNames) {
//        List<String> tableNames= Arrays.asList("plan","time_slot","program");
        int frontIdx = 0;
//        int linkIdx=1;
        int backIdx = 1;
//        int endIdx=2;
//        String whereCode=" where ";
//        0 1 2
        StringBuilder whereCode = new StringBuilder(" from ");
        String firstTable = tableNames.get(0);
        whereCode.append(firstTable).append(", ");
        for (int i = 0; i < tableNames.size(); i++) {
            String frontTable = tableNames.get(frontIdx);
//            String linkTable = tableNames.get(backIdx);
            String backTable = tableNames.get(backIdx);
//            String backTable = tableNames.get(endIdx);
            String linkTableName = frontTable + "_" + backTable;

//            whereCode.append(" #frontTable#,\n#linkTableName#,\n#backTable#,"
            whereCode.append(" #linkTableName#,\n#backTable#,"
                    .replace("#frontTable#", frontTable)
                    .replace("#backTable#", backTable)
                    .replace("#linkTableName#", linkTableName))
            ;
            frontIdx++;
            backIdx++;
            if (backIdx >= tableNames.size()) {
                break;
            }

        }

//        whereCode.rela
        whereCode = new StringBuilder(whereCode.substring(0, whereCode.length() - 1));
        whereCode.append("\n");
//        whereCode=   whereCode.replaceFirst("and","where");

//        System.out.println(whereCode);
        return whereCode.toString();
    }

    static String makeSelectCode(List<String> tableNames) {
//        List<String> tableNames= Arrays.asList("plan","time_slot","program");
        int frontIdx = 0;
//        int linkIdx=1;
        int backIdx = 1;
//        int endIdx=2;
//        String whereCode=" where ";
        StringBuilder whereCode = new StringBuilder(" select \n");
        String firstTable = tableNames.get(0);
        whereCode.append(firstTable + ".*").append(",\n ");
        List<String> codes = new ArrayList<>();
        for (int i = 0; i < tableNames.size(); i++) {
            String frontTable = tableNames.get(frontIdx);
//            String linkTable = tableNames.get(backIdx);
            String backTable = tableNames.get(backIdx);
//            String backTable = tableNames.get(endIdx);
            String linkTableName = frontTable + "_" + backTable;
//            whereCode.append("  #frontTable#.*, \n #backTable#.*,"
            codes.add(backTable + ".*");
//            whereCode.append("   #backTable#.*,"
//                    .replace("#frontTable#", frontTable)
//                    .replace("#backTable#", backTable)
//                    .replace("#linkTableName#", linkTableName))
//            ;
            frontIdx++;
            backIdx++;
            if (backIdx >= tableNames.size()) {
                break;
            }
        }

//        pre
//        System.out.println(codes);
        String tables = String.join(",\n", codes);
        whereCode.append(tables);
//        whereCode = new StringBuilder(whereCode.substring(0, whereCode.length() - 1));
        whereCode.append("\n");
//        System.out.println(whereCode);
        return whereCode.toString();
    }

    static String makeWhereCode(List<String> tableNames) {
//        List<String> tableNames= Arrays.asList("plan","time_slot","program");
        int frontIdx = 0;
//        int linkIdx=1;
        int backIdx = 1;
//        int endIdx=2;
//        String whereCode=" where ";
        StringBuilder whereCode = new StringBuilder(" ");
        for (int i = 0; i < tableNames.size(); i++) {
            String frontTable = tableNames.get(frontIdx);
//            String linkTable = tableNames.get(backIdx);
            String backTable = tableNames.get(backIdx);
//            String backTable = tableNames.get(endIdx);
            String linkTableName = frontTable + "_" + backTable;
            whereCode.append(" \n and #frontTable#.id = #linkTableName#.#frontTable#_id \n and  #linkTableName#. #backTable#_id = #backTable#.id "
                    .replace("#frontTable#", frontTable)
                    .replace("#backTable#", backTable)
                    .replace("#linkTableName#", linkTableName))
            ;
            frontIdx++;
            backIdx++;
            if (backIdx >= tableNames.size()) {
                break;
            }

        }

        whereCode = new StringBuilder(whereCode.toString().replaceFirst("and", "where"));

//        System.out.println(whereCode);
        return whereCode.toString();
    }

    static void genLinkedTableQueryTest() {
//    List<String> tableNames= Arrays.asList("plan","time_slot","program");
        List<String> tableNames = Arrays.asList("draw", "question");

        String linkedTableQuery = genLinkedTableQuery(tableNames);
        System.out.println(linkedTableQuery);
    }

    static void test() {
        //        Notice notice = new Notice();

//        String gen = gen(notice);
//        System.out.println(gen);

//        CodeGen codeGen = new CodeGen();
//        codeGen.genAxios(notice);
////        codeGen.axiosGetCode.so
//        System.out.println(codeGen.axiosPostCode);

//        List<String> tableNames= Arrays.asList("plan","time_slot","program");
////        String whereCode = makeWhereCode(tableNames);
////        String selectCode = makeSelectCode(tableNames);
////        String fromCode = makeFromCode(tableNames);
////        System.out.println(selectCode+fromCode+whereCode);
//        String linkedTableQuery = genLinkedTableQuery(tableNames);
//        System.out.println(linkedTableQuery);
//

        CodeGen codeGen = new CodeGen();
        codeGen.jsonToJavaCode(" {\n" +
                "      \"CHARACTER_MAXIMUM_LENGTH\": 255,\n" +
                "      \"TABLE_CATALOG\": \"def\",\n" +
                "      \"TABLE_SCHEMA\": \"gcsm\",\n" +
                "      \"EXTRA\": \"\",\n" +
                "      \"IS_NULLABLE\": \"YES\",\n" +
                "      \"COLUMN_KEY\": \"\",\n" +
                "      \"COLUMN_TYPE\": \"varchar(255)\",\n" +
                "      \"COLUMN_COMMENT\": \"公告状态\",\n" +
                "      \"COLLATION_NAME\": \"utf8_general_ci\",\n" +
                "      \"CHARACTER_OCTET_LENGTH\": 765,\n" +
                "      \"CHARACTER_SET_NAME\": \"utf8\",\n" +
                "      \"DATA_TYPE\": \"varchar\",\n" +
                "      \"TABLE_NAME\": \"notice\",\n" +
                "      \"COLUMN_NAME\": \"state\",\n" +
                "      \"ORDINAL_POSITION\": 4,\n" +
                "      \"PRIVILEGES\": \"select,insert,update,references\"\n" +
                "    },");
        System.out.println(codeGen.javaClassCode);
//        System.out.println(codeGen.fromMapCode);
    }

    static String 包路径 = "D:\\proj\\springShort\\GCSM-server\\src\\main\\java\\com\\gm\\wj";
    //    static String filePathRoot="D:\\codeGen";
    static String filePathRoot = "C:\\codeGen";

    static class Path {
        static String controller = "controller";
        static String repository = "repository";
    }

    static class Suffix {
        //        EquipmentRepository
        static String Repository = "Repository";
        static String Controller = "Controller";
    }

    static String backUpDir = "D:\\";

    static void testFile() {
        String className = "java";
//        Paths.get(className+Suffix.Repository,
//        String javaFileName=className+Suffix.Repository+".java";
        String javaFileName = className + Suffix.Repository + ".java";
//       String javaFileNameAbs=  包路径+"/"+javaFileName;
        java.nio.file.Path javaFileNameAbs = Paths.get(包路径, javaFileName);
//        String backUpFilenameAbs=backUpDir+javaFileName;
        java.nio.file.Path backUpFilenameAbs = Paths.get(backUpDir, javaFileName);
        boolean exists = backUpFilenameAbs.toFile().exists();
//        有的话 要重命名
        System.out.println("exists backUpFilenameAbs");
        System.out.println(exists);
        System.out.println("javaFileNameAbs");
        System.out.println(javaFileNameAbs);
        System.out.println("backUpFilenameAbs");
        System.out.println(backUpFilenameAbs);
//        java 重命名文件
//        FileUtil
    }

    public static void main(String[] args) throws Exception {

//        File directory = new File("");//参数为空
//        String courseFile = directory.getCanonicalPath() ;
//        System.out.println(courseFile);
//        D:\proj\springShort\GCSM-server
//        src/
//        D:\proj\springShort\GCSM-server\src\main\java\com\gm\wj

//        URL resource = CodeGen.class.getResource("");
//        System.out.println(resource);
//        file:/D:/proj/springShort/GCSM-server/target/classes/com/gm/wj/util/
//        new File("").getAbsolutePath()

//        testFile();

//        CodeGen codeGen = new CodeGen();
//        codeGen.set包名(WjApplication.class.getPackage().getName());
////        ColumnInfoRepository.java
////        String java = codeGen.genJpaRepository("java");
//        String java = codeGen.genJpaRepository("column_info");
//        System.out.println(java);

//        genSaveBatchMapTable("paper","fill_question",null);
//        genSaveBatchMapTable("paper", "fill_question", "paper_manage");
        genLinkedTableQueryTest();

    }

    static String genLinkedTableQuery(List<String> tableNames) {
//       List<String> tableNames= Arrays.asList("plan","time_slot","program");
        String whereCode = makeWhereCode(tableNames);
        String selectCode = makeSelectCode(tableNames);
        String fromCode = makeFromCode(tableNames);
//       System.out.println(selectCode+fromCode+whereCode);
//       Path.

        return selectCode + fromCode + whereCode + ";";
    }

    String axiosGetCode;
    String axiosPostCode;

    public void genAxios(Object object) {
        String tableName = getTableName(object);
        String jsonAllFiled = ObjectToJson.Object2JsonAllFiled(object);

        String axiosGetCodeTpl = "axios.get(   common.baseURL + \"/#tableName#\").then((resp) => {\n" +
                "        if (resp && resp.code === 200) {\n" +
                "             let data=resp.data.data\n" +
                "             this.#tableName#=data\n" +
                "        }\n" +
                "      });";

        String axiosPostCodeTpl = "axios.post(   common.baseURL + \"/#tableName#\",#dataForm#).then((resp) => {\n" +
                "        if (resp && resp.code === 200) {\n" +
                "             let data=resp.data.data\n" +
                "             this.#tableName#=data\n" +
                "        }\n" +
                "      });";
        axiosGetCode = axiosGetCodeTpl.replace("#tableName#", tableName);
        axiosPostCode = axiosPostCodeTpl.replace("#tableName#", tableName)
                .replace("#dataForm#", jsonAllFiled);

    }

    static String apiPrefix = "/api";

    //  static   String iView项目目录="D:\\proj\\private\\party-school-vue";
    static String iView项目目录 = "D:\\iviewBody\\party-school-vue";
    static String iView项目src目录 = iView项目目录 + "\\src";
    static String iView项目utils目录 = iView项目src目录 + "/utils";
static  boolean doWriteRuoyi=false;
    public static String gen(Map<String, List<ColumnInfo>> mapTableCols, String table_schema) throws Exception {
        AtomicReference<String> columnsDicMapCode = new AtomicReference<>("// xlsx中文字段和后端数据库的映射\n");
//        java.nio.file.Path pathFile = Paths.get(filePathRoot, DateUtilSt.getNowFileFormat());
        java.nio.file.Path pathFile = Paths.get(filePathRoot, table_schema, DateUtilSt.getNowFileFormat());
//        String pathFileString = pathFile.toString();
        pathFileString = pathFile.toString();
        String springBoot = "springBoot";

        CodeGen codeGen = new CodeGen();
//        codeGen.set包名(DemoApplication.class.getPackage().getName());
//        CodeGen.包名=DemoApplication.class.getPackage().getName();
//            filePathRoot


        AtomicReference<String> jdlData = new AtomicReference<>("");
        codeGen.setPathFile(pathFile);
        AtomicReference<String> routeRows = new AtomicReference<>("");
        AtomicReference<String> insertIntoAllSql = new AtomicReference<>("");
//       String insertIntoAllSql="";
        List<String>ColumnsDicMapUnderScoreList=new ArrayList<>();

        List<String >tableNameExcelColsMapRowList=new ArrayList<>();
        List<String >tableNameExcelColsMapRowUnderscoreList=new ArrayList<>();
        List<String >tableNameOriginExcelColsMapRowUnderScoreList=new ArrayList<>();
//        AtomicReference<AtomicReference<AtomicReference<AtomicReference<String>>>> routeRows= new AtomicReference<AtomicReference<AtomicReference<AtomicReference<String>>>>("");
        mapTableCols.forEach((tableName, columnInfos) -> {
//            columnInfos.ge
            String CamelCaseName = StringUtils.underlineToCamelCase(tableName);
//            javaClass
//       String 类名 = StrUtil.captureName(tableName);
            String className = StringUtils.captureName(CamelCaseName);
//            JavaClass
            try {
//                String tpl = " {\n" +
//                        "        title: 'menu.entity.#CamelCaseName#',\n" +
//                        "        name: '#CamelCaseName#',\n" +
//                        "        path: '#CamelCaseName#',\n" +
//                        "        component: '#CamelCaseName#',\n" +
//                        "        wrappers: ['@/components/KeepAlive'],\n" +
//                        "        keppAlive: true,\n" +
//                        "      },\n";
                String tpl = " {\n" +
                        "        title: 'menu.system.#CamelCaseName#',\n" +
                        "        name: '#CamelCaseName#',\n" +
                        "        path: '#CamelCaseName#',\n" +
                        "        component: 'system/#CamelCaseName#',\n" +
                        "        wrappers: ['@/components/KeepAlive'],\n" +
                        "        keppAlive: true,\n" +
                        "      },\n";
                String routeRow = tpl.replace("#CamelCaseName#", CamelCaseName);
//                codeGen.enti
//                routeRows+=routeRow;
                routeRows.set(routeRows.get() + routeRow);
                codeGen.genJpaRepository(tableName, columnInfos);
                codeGen.genController(tableName, columnInfos);
//                String javaController = codeGen.gen(tableName,columnInfos);
//                System.out.println("===========");
//                System.out.println("genJpaRepository");
//                System.out.println(java);
//                System.out.println("javaController");
//                System.out.println(javaController);

                TableInfo tableInfo = new TableInfo(tableName, columnInfos);
                tableInfo.setPathFileString(pathFileString);
//                tableInfo.
                String export=" ";
//                boolean doExport=false;
                boolean doExport=true;
                if(doExport){
                    export="export  ";
                }
                String columnsDicMap = tableInfo.genColumnsDicMap(export);
                String columnsDicMapUnderScore = tableInfo.genColumnsDicMapUnderScore(export);
                ColumnsDicMapUnderScoreList.add(columnsDicMapUnderScore);
//                columnsDicMap
//                columnsDicMapCode+= columnsDicMap.get();
//                columnsDicMapCode.set();
//                tableInfo.gen
                columnsDicMapCode.set(columnsDicMapCode.get() + columnsDicMap);

//                包路径
                tableInfo.genIViewManage();

                tableInfo.genIViewEdit();
                tableInfo.genIViewForm();
                tableInfo.genIViewApi();
                tableInfo.genElementUiTable();
                tableInfo.genElementTableMybatisPlus();
                tableInfo.genElementUiForm();
                tableInfo.genEntity(packageName);
                tableInfo.genElmPlusTable(packageName);
                tableInfo.genReactForm(packageName);
                tableInfo.genReactTable(packageName);
                tableInfo.genXueZhiSiApiJs(packageName);

                String jdl = tableInfo.genJdl();
//                jdlData+=jdl+"\n";
                jdlData.set(jdlData.get() + jdl + "\n");
                if(doWriteRuoyi){
                    tableInfo.genTsDataD(packageName);
                    tableInfo.genReactRuoyiService(packageName);
                    tableInfo.genReactRuoyiEdit(packageName);
                    tableInfo.genReactRuoyiTable(packageName);
                    tableInfo.genRuoYiJpaEntity(packageName);
                    tableInfo.genRuoYiJpaController(packageName);
                }

                tableInfo.genXueZhiSiController(packageName);
                tableInfo.genMybatisXmlSqls();
                final String insertInto = tableInfo.genInsertInto(packageName);
//                insertIntoAllSql+=insertInto+"\n";
                insertIntoAllSql.set(insertIntoAllSql.get() + insertInto + "\n");

                tableInfo.genMybatisPlusController(packageName);
//                tableInfo.genJpaControllerIView(packageName);
                tableInfo.genServiceMybatisPlus(packageName);
                tableInfo.genServiceImplMybatisPlus(packageName);

                tableInfo.genMapperJavaMybatisPlus(packageName);
                tableInfo.genCsv();
//                tableInfo.getTable_schema()
//                String tableName1 = tableInfo.getTableName();
                String tableNameExcelColsMapRow = tableInfo.genTableNameExcelColsMapRow();
                String tableNameExcelColsMapRowUnderScore = tableInfo
                        .genTableNameExcelColsMapRowUnderScore();
                String tableNameOriginExcelColsMapRowUnderScore = tableInfo
                        .genTableNameOriginExcelColsMapRowUnderScore();

//                String tableNameExcelColsMapRowTpl="'{tableName}':{tableName}Columns,";
//           String tableNameExcelColsMapRow=
//                   tableNameExcelColsMapRowTpl.replace("{tableName}",tableName);
                tableNameExcelColsMapRowList.add(tableNameExcelColsMapRow);
                tableNameExcelColsMapRowUnderscoreList.add(tableNameExcelColsMapRowUnderScore);
                tableNameOriginExcelColsMapRowUnderScoreList
                        .add(tableNameOriginExcelColsMapRowUnderScore);
//                String columnsDicMap = tableInfo.genColumnsDicMap();
//                tableInfo.get
//                java.nio.file.Path dictDataPath = Paths.get(pathFileString,
//                        "iview","manage", "dictData.js");
//                FileUtil.writeCode(dictDataPath,columnsDicMapCode.get());
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        tableNameExcelColsMapRowList.addAll(tableNameExcelColsMapRowUnderscoreList);
        tableNameExcelColsMapRowList.addAll(tableNameOriginExcelColsMapRowUnderScoreList);
        String tableNameExcelColsMapRowsStr =
                String.join("\n", tableNameExcelColsMapRowList);
        String ColumnsDicMapUnderScoreListStr =
                String.join("\n", ColumnsDicMapUnderScoreList);

        String code = FileUtil.readResourceFileData("genCodeTemplate/ruoyi/react/routes.ts");
//        String code = FileUtil.readResourceFileData("genCodeTemplate/elmPlus/ElementTableMybatisPlus.vue");

        code = code
                .replace("#routeRows#", routeRows.get())

        ;
//        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
//                "elmPlus","tableMybatisPlus", 类名+"Table.vue");
        java.nio.file.Path ruoYiRoutePath = Paths.get(pathFileString,
                "react", "ruoyi", "routes.ts");
        FileUtil.writeCode(ruoYiRoutePath, code);
//        #routeRows#
        String columnsDicMapJsLastCode = "// 通过上述columns转换成{label:value}形式\n" +
                "export function getDict(columns) {\n" +
                "  const res = {};\n" +
                "  for (let column of columns) {\n" +
                "    res[column['label']] = column['key'];\n" +
                "  }\n" +
                "  return res;\n" +
                "}\n";
        String tableNameExcelColsMapTpl="\n let tableNameExcelColsMap={\n" +
                "  {tableNameExcelColsMapRowsStr}" +
                "}\n";
        String    tableNameExcelColsMap=  tableNameExcelColsMapTpl.
                replace("{tableNameExcelColsMapRowsStr}",tableNameExcelColsMapRowsStr);
        columnsDicMapCode.set(columnsDicMapCode.get()

                + ColumnsDicMapUnderScoreListStr
//                tableNameExcelColsMap 要写在下面 不然 undefined
                + tableNameExcelColsMap
                + columnsDicMapJsLastCode);

//        System.out.println("columnsDicMapCode");
//        System.out.println(columnsDicMapCode);
//        dow
//        filePathRoot
        System.out.println("写入文件 在 " + pathFile);
//        java.nio.file.Path dictDataPath = Paths.get(iView项目utils目录, "dictData.js");
        String filePath = pathFile.toString();
        java.nio.file.Path dictDataPath = Paths.get(pathFile.toString(),
                "iview", "utils", "dictData.js");
        FileUtil.writeCode(dictDataPath, columnsDicMapCode.get());
        java.nio.file.Path jdlPath = Paths.get(filePath,
                "jdl", "jdl.jdl");
        FileUtil.writeCode(jdlPath, jdlData.get());
        java.nio.file.Path insertSqlPath = Paths.get(filePath,
                "sql", "insert.sql");
        FileUtil.writeCode(insertSqlPath, insertIntoAllSql.get());
//        if (CodeGen.doWriteFile) {
//
//            java.nio.file.Path dictDataPath = Paths.get(iView项目utils目录, "dictData.js");
//            FileUtil.writeCode(dictDataPath,columnsDicMapCode.get());
////            FileUtil.renameIf存在(dictDataPath.toFile().getAbsolutePath());
////            try(FileWriter )
//
//        }
        return "";

    }

//    String genSaveBatchMapTable(String fatherTable,String childTable){
//        String mapTable=fatherTable+"_"+childTable;
////        StringUtils.upperCaseFirst()
//        String mapTableCamelCase=   StringUtils.underlineToCamelCase(mapTable);
//        String mapTablePascal = StringUtils.upperCaseFirst(mapTableCamelCase);
////        String mapTablePascal = StringUtils.underlineToPascal(mapTable);
//        String fatherTableCamelCase=   StringUtils.underlineToCamelCase(mapTable);
////        String fatherTablePascal = StringUtils.underlineToPascal(fatherTable);
//        String fatherTablePascal = StringUtils.upperCaseFirst(fatherTableCamelCase);
//
//        String childTableCamelCase=   StringUtils.underlineToCamelCase(childTable);
//        String childTablePascal = StringUtils.upperCaseFirst(childTableCamelCase);
//    }

    public static String genSaveBatchMapTable(String fatherTable, String childTable, String mapTable) throws Exception {

        String code = FileUtil.readResourceFileData("genCodeTemplate/mybatisPlus/saveMapBatch.java");
//        String iViewFormInputs = genIViewFormInputs();
//        String mapTable=fatherTable+"_"+childTable;
        if (mapTable == null) {
            mapTable = fatherTable + "_" + childTable;
        }
//        StringUtils.upperCaseFirst()
        String mapTableCamelCase = StringUtils.underlineToCamelCase(mapTable);
        String mapTablePascal = StringUtils.upperCaseFirst(mapTableCamelCase);
//        String mapTablePascal = StringUtils.underlineToPascal(mapTable);
        String fatherTableCamelCase = StringUtils.underlineToCamelCase(fatherTable);
//        String fatherTablePascal = StringUtils.underlineToPascal(fatherTable);
        String fatherTablePascal = StringUtils.upperCaseFirst(fatherTableCamelCase);

        String childTableCamelCase = StringUtils.underlineToCamelCase(childTable);
        String childTablePascal = StringUtils.upperCaseFirst(childTableCamelCase);


        code = code
//                .replace("#formInputs#", iViewFormInputs)
                .replace("#mapTableCamelCase#", mapTableCamelCase)
                .replace("#mapTablePascal#", mapTablePascal)
//                .replace("#实体名#", 实体名)
                .replace("#fatherTableCamelCase#", fatherTableCamelCase)
                .replace("#fatherTablePascal#", fatherTablePascal)
                .replace("#childTableCamelCase#", childTableCamelCase)
                .replace("#childTablePascal#", childTablePascal)
//                .replace("#jsonDefaultNull#", jsonDefaultNull)
//                .replace("#iViewColumnsRows#", iViewColumnsRows)
        ;
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "mybatisPlus", "saveMapBatch", "saveMapBatch_" + mapTable + ".java");
        System.out.println("dictDataPath");
        System.out.println(dictDataPath);
        FileUtil.writeCode(dictDataPath, code);

        return code;
    }


    public static String gen(Object object) {
        String tableName = getTableName(object);
        String jsonAllFiled = ObjectToJson.Object2JsonAllFiled(object);

        String axiosCode = "axios.get(   common.baseURL + \"/#tableName#\").then((resp) => {\n" +
                "        if (resp && resp.code === 200) {\n" +
                "             let data=resp.data.data" +
                "        }\n" +
                "      });";

        String axiosPostCode = "axios.post(   common.baseURL + \"/#tableName#\",#dataForm#).then((resp) => {\n" +
                "        if (resp && resp.code === 200) {\n" +
                "             let data=resp.data.data" +
                "        }\n" +
                "      });";
        String replace = axiosCode.replace("#tableName#", tableName);
        String replace1 = axiosPostCode.replace("#tableName#", tableName)
                .replace("#dataForm#", jsonAllFiled);
        return replace;
    }

//    public static <T> String getTableName(T obj) {
//        String tableName = obj.getClass().getSimpleName();
//        tableName = StringUtils.underscoreNameLower(tableName);
//        return tableName;
//    }

    public static String getTableName(Object obj) {
        String tableName = obj.getClass().getSimpleName();
        tableName = StringUtils.underscoreNameLower(tableName);
        return tableName;
    }
}
