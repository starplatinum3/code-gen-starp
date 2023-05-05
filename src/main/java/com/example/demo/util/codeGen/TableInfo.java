package com.example.demo.util.codeGen;

import com.example.demo.common.MysqlDataType;
import com.example.demo.util.DateUtilSt;
import com.example.demo.util.FileUtil;
import com.example.demo.util.StrUtil;
import com.example.demo.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

//import com.gm.wj.entity.ColumnInfo;
//import com.gm.wj.util.StrUtil;
import lombok.Data;
//import top.starp.util.MockGenerator;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Data
public class TableInfo {
  public    static final String tablePreffix = "t_";
    /**
     * 下划线
     */
    String tableName;
    String tableNameOrigin;
    @Deprecated
    String 类名;
    String className;
    List<ColumnInfo> columnInfos;
    @Deprecated
    String 实体名;
    String entityName;
    String table_schema;
    String table_type;

    public TableInfo() {
//        tablePreffix
    }

    public TableInfo(String tableName, List<ColumnInfo> columnInfos) {
//        tableName.
//        tableName= StrUtil.frontDelStr(tableName, tablePreffix);
//        tableName= StrUtil.StringStartTrim(tableName, tablePreffix);
//        new String[]{tablePreffix}
//        StringUtils.isEmpty()
        this.tableNameOrigin = tableName;
        tableName = StrUtil.removePrefix(tableName, new String[]{tablePreffix});
//        tableName= StrUtil.removePrefix(tableName, tablePreffix);
        this.tableName = tableName;

        this.columnInfos = columnInfos;
        实体名 = StringUtils.underlineToCamelCase(tableName);
        类名 = StringUtils.upperCaseFirst(实体名);

        entityName = StringUtils.underlineToCamelCase(tableName);

        className = StringUtils.upperCaseFirst(entityName);
//        实体名 = StringUtils.lowerCaseFirst(类名);
    }

    
    String genJsonDefaultNull() {
        if (columnInfos == null) {
            return "{ }";
        }
        List<String> rowNames = columnInfos.stream()
                .map(columnInfo -> {
                    String java字段名 = columnInfo.getJava字段名();
                    String row = "         \"#java字段名#\":null "
                            .replace("#java字段名#", java字段名);
                    return row;
                }).collect(Collectors.toList());
        return "{  " +
                "\n" +
                String.join(",\n", rowNames) +
                "\n" +
                "                  }";


//        return rows;

    }

    private static final List<String> NAMES = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eva");
    private static final List<String> STATES = Arrays.asList("waiting", "running", "stopped");

//     需要一个genJsonMock 函数 ，根据不同的字段 生成不同的默认值，比如是名字 就随机名字，
//     是时间就随机时间，是数字就随机数字。数字如果是年龄 要考虑年龄段，状态要给几个状态，比如等待中、运行中之类的
    public static String genJsonMock(List<ColumnInfo> columnInfos) {
        // MockGenerator.genMock(tablePreffix)
        if (columnInfos == null) {
            return "{ }";
        }

        Random rand = new Random();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();

        for (ColumnInfo columnInfo : columnInfos) {
            String javaFieldName = columnInfo.getJavaFieldName();
        //     String type = columnInfo.getType();
            String type =   columnInfo.getDATA_TYPE();

        //     "VARCHAR"
        //      if (type.equalsIgnoreCase("string")) 
            if (type.equalsIgnoreCase("VARCHAR")) {
                if ("name".equalsIgnoreCase(javaFieldName)) {
                    rootNode.put(javaFieldName, NAMES.get(rand.nextInt(NAMES.size())));
                } else {
                    rootNode.put(javaFieldName, "");
                }
            } else if (type.equalsIgnoreCase("int")) {
                if ("age".equalsIgnoreCase(javaFieldName)) {
                    int ageGroup = rand.nextInt(3);
                    switch (ageGroup) {
                        case 0:
                            rootNode.put(javaFieldName, rand.nextInt(18) + 1);
                            break;
                        case 1:
                            rootNode.put(javaFieldName, rand.nextInt(20) + 18);
                            break;
                        case 2:
                            rootNode.put(javaFieldName, rand.nextInt(80) + 38);
                            break;
                    }
                } else {
                    rootNode.put(javaFieldName, rand.nextInt(100));
                }
            } else if (type.equalsIgnoreCase("date")) {
                rootNode.put(javaFieldName, "2023-04-27 03:12:09");
            } else if (type.equalsIgnoreCase("boolean")) {
                rootNode.put(javaFieldName, true);
            } else if (type.equalsIgnoreCase("enum")) {
                if ("state".equalsIgnoreCase(javaFieldName)) {
                    rootNode.put(javaFieldName, STATES.get(rand.nextInt(STATES.size())));
                } else {
                    rootNode.put(javaFieldName, "");
                }
            } else {
                rootNode.putNull(javaFieldName);
            }
        }

        return rootNode.toString();
    }

    String genIViewColumns() {
        List<String> rowNames = columnInfos.stream().map(columnInfo -> {
            String java字段名 = columnInfo.getJava字段名();
//            String column_comment = columnInfo.getCOLUMN_COMMENT();
            String column_comment = columnInfo.getColumnCommentShow();
            String row = "                {\n" +
                    "                        title: '#column_comment#',\n" +
                    "                        key: '#java字段名#',\n" +
                    "                        //  slot:\"#java字段名#\",\n" +
                    "                        // 这里要配置 slot 才能被抓到\n" +
                    "                        width: 100\n" +
                    "                    }";
            row = row.
                    replace("#java字段名#", java字段名)
                    .replace("#column_comment#", column_comment)
            ;
            return row;
        }).collect(Collectors.toList());
        return " columns: [  \n" + String.join(",\n", rowNames) + " \n ]";
    }

    String genIViewColumnsRows() {
//        AtomicReference<String> rows= new AtomicReference<>("");
        if (columnInfos == null) {
            return null;
        }
        List<String> rowNames = columnInfos.stream().map(columnInfo -> {
            String java字段名 = columnInfo.getJava字段名();
//            String column_comment = columnInfo.getCOLUMN_COMMENT();
            String column_comment = columnInfo.getColumnCommentShow();
            String row = "            " +
                    "\n" +
                    "                   {" +
                    "\n" +
                    "                        title: '#column_comment#'," +
                    "\n" +
                    "                        key: '#java字段名#',\n" +
                    "                        //  slot:\"#java字段名#\",\n" +
                    "                        // 这里要配置 slot 才能被抓到\n" +
                    "                        width: 200\n" +
                    "                    } ,";
            row = row.
                    replace("#java字段名#", java字段名)
                    .replace("#column_comment#", column_comment)
            ;
            return row;
        }).collect(Collectors.toList());

        StringBuilder rows = new StringBuilder();
//        rowNames.stream().map(columnInfo -> {
////            rows.ad
////            rows+= columnInfo.get();
//        })
        for (String rowName : rowNames) {
            rows.append(rowName);
        }
//        return  String.join(",\n", rowNames);
        return rows.toString();
//        return   " columns: [  \n"+ String.join(",\n", rowNames) +" \n ]";
    }

    String genIViewEdit() throws Exception {
//        ClassPathResource classPathResource = new ClassPathResource("excleTemplate/test.xlsx");
//        InputStream inputStream =classPathResource.getInputStream();
//

        String iViewColumnsRows = genIViewColumnsRows();
        String jsonDefaultNull = genJsonDefaultNull();
//        InputStream resourceInputStream = FileUtil.getResourceInputStream("genCodeTemplate/IviewEdit.vue");
//        int read = resourceInputStream.read();


//        String code = "<template>\n" +
//                "    <div>\n" +
//                "        <layout-edit-content title=\"#类名#Edit\">\n" +
//                "            <div slot=\"main\">\n" +
//                "                <#类名#Form :entityId=\"entityId\" :type=\"type\"></#类名#Form>\n" +
//                "            </div>\n" +
//                "        </layout-edit-content>\n" +
//                "\n" +
//                "        <Table :columns=\"columns\" :data=\"allList\" ref=\"selection\" :loading=\"searchLoading\">\n" +
//                "\n" +
//                "        </Table>\n" +
//                "\n" +
//                "    </div>\n" +
//                "</template>\n" +
//                "\n" +
//                "<script>\n" +
//                "    import {getUserInfo} from \"../../api/user\";\n" +
//                "    import LayoutEditContent from \"../../components/layout/layout-edit-content\";\n" +
//                "    // import UserForm from \"../../components/form/user-form\";\n" +
//                "    import #类名#Form from \"../../components/form/#实体名#-form\";\n" +
//                "\n" +
//                "    // console.log(1)\n" +
//                "    // import {batchDelete, deleteOne, exportToWord,selectAll,selectPage} from \"../../api/permission\";\n" +
//                "    import {batchDelete, deleteOne, exportToWord,selectAll} from \"../../api/permission\";\n" +
//                "    import {update,create,selectPage} from \"../../utils/request\";\n" +
//                "    export default {\n" +
//                "        name: \"#类名#Edit\",\n" +
//                "        components: {\n" +
//                "            LayoutEditContent,\n" +
//                "            #类名#Form },\n" +
//                "        created() {\n" +
//                "            // this.userId = this.$route.query.userId;\n" +
//                "            // this.entityId =    this.$route.query.id;\n" +
//                "            console.log( \"this.entityId\")\n" +
//                "            console.log( this.entityId)\n" +
//                "            if (!!this.entityId) {\n" +
//                "                this.type = 'update';\n" +
//                "            }\n" +
//                "\n" +
//                "\n" +
//                "\n" +
//                "            this. selectPage();\n" +
//                "        },\n" +
//                "        methods:{\n" +
//                "            selectPage() {\n" +
//                "                this.searchLoading = true;\n" +
//                "                selectPage(this.select,this.select.pageNum,\n" +
//                "                    this.select.pageSize,this.tableName).then(res => {\n" +
//                "                    // this.writerList = res.data.data.list;\n" +
//                "\n" +
//                "                    console.log(\"res\")\n" +
//                "                    console.log(res)\n" +
//                "                    // this.allList = res.data.data.list;\n" +
//                "                    this.allList = res.data.data;\n" +
//                "\n" +
//                "                    console.log( \"this.allList\")\n" +
//                "                    console.log( this.allList)\n" +
//                "\n" +
//                "                    this.total = res.data.data.total;\n" +
//                "                    this.searchLoading = false;\n" +
//                "                })\n" +
//                "            },\n" +
//                "\n" +
//                "        data() {\n" +
//                "            return {\n" +
//                "                // entityId:'',\n" +
//                "                // entityId:this.$route.query.userId,\n" +
//                "                entityId:this.$route.query.id,\n" +
//                "                tableName:\"#实体名#\",\n" +
//                "                total: 0,\n" +
//                "                searchLoading: false,\n" +
//                "                userId: '',\n" +
//                "                type: 'create',\n" +
//                "                select: {\n" +
//                "                    deviceId: '',\n" +
//                "                    phone: '',\n" +
//                "                    nickname: '',\n" +
//                "                    alias: '',\n" +
//                "                    gender: '',\n" +
//                "                    age: '',\n" +
//                "                    testTime: '',\n" +
//                "                    edition: '',\n" +
//                "                    height: '',\n" +
//                "                    weight: '',\n" +
//                "                    weightDegree: '',\n" +
//                "                    prvWeight: '',\n" +
//                "                    bmi: '',\n" +
//                "                    bmiDegree: '',\n" +
//                "                    prvBmi: '',\n" +
//                "                    ffm: '',\n" +
//                "                    ffmDegree: '',\n" +
//                "                    prvFfm: '',\n" +
//                "                    tbw: '',\n" +
//                "                    tbwDegree: '',\n" +
//                "                    prvTbw: '',\n" +
//                "                    muscle: '',\n" +
//                "                    muscleDegree: '',\n" +
//                "                    prvMuscle: '',\n" +
//                "                    protein: '',\n" +
//                "                    proteinDegree: '',\n" +
//                "                    prvProtein: '',\n" +
//                "                    minerals: '',\n" +
//                "                    mineralsDegree: '',\n" +
//                "                    prvMinerals: '',\n" +
//                "                    calcium: '',\n" +
//                "                    calciumDegree: '',\n" +
//                "                    prvCalcium: '',\n" +
//                "                    bfm: '',\n" +
//                "                    bfmDegree: '',\n" +
//                "                    prvBfm: '',\n" +
//                "                    pbf: '',\n" +
//                "                    pbfDegree: '',\n" +
//                "                    prvPbf: '',\n" +
//                "                    smm: '',\n" +
//                "                    smmDegree: '',\n" +
//                "                    prvSmm: '',\n" +
//                "                    vfi: '',\n" +
//                "                    vfiDegree: '',\n" +
//                "                    prvVfi: '',\n" +
//                "                    ffmi: '',\n" +
//                "                    ffmiDegree: '',\n" +
//                "                    prvFfmi: '',\n" +
//                "                    physicalAge: '',\n" +
//                "                    waistHipRatio: '',\n" +
//                "                    futureYearHeight: '',\n" +
//                "                    futureYearWeight: '',\n" +
//                "                    muscleControl: '',\n" +
//                "                    bfmControl: '',\n" +
//                "                    segmentalBfm: '',\n" +
//                "                    segmentalFat: '',\n" +
//                "                    basalMetabolicRate: '',\n" +
//                "                    basalMetabolicRateDegree: '',\n" +
//                "                    kcal: '',\n" +
//                "                    caloriesBurnedControl: '',\n" +
//                "                    shape: '',\n" +
//                "                    id: '',\n" +
//                "                    personId: '',\n" +
//                "\n" +
//                "\n" +
//                "                    pageNum: 1,\n" +
//                "                    pageSize: 10\n" +
//                "\n" +
//                "                    // 会 显示9个 是因为他吗 不是因为后台\n" +
//                "                    // 确实有问题 ，这里要写10个 ，才是正常的诶\n" +
//                "                    // 那个控件要改掉他 每页的个数吧\n" +
//                "                    // 只有 90页  这样显示不全的吧\n" +
//                "                    // 只要给 total 就可以工作了吧\n" +
//                "                },\n" +
//                "\n" +
//                "                columns: [\n" +
//                "                    {\n" +
//                "                        type: 'selection',\n" +
//                "                        width: 60,\n" +
//                "                        align: 'center'\n" +
//                "                    },\n" +
//                "                    {\n" +
//                "                        type: 'index',\n" +
//                "                        width: 60,\n" +
//                "                        align: 'center'\n" +
//                "                    },\n" +
//                "\n" +
//                "             #iViewColumnsRows#      " +
//                "\n" +
//                "\n" +
//                "\n" +
//                "\n" +
//                "\n" +
//                "                    {\n" +
//                "                        title: '操作',\n" +
//                "                        // slot: 'action',\n" +
//                "                        fixed: 'right',\n" +
//                "                        width: 240,\n" +
//                "                        align: 'center'\n" +
//                "                    }\n" +
//                "                    ,\n" +
//                "       " +
//                "                ],\n" +
//                "                allList:[],\n" +
//                "            }\n" +
//                "        },\n" +
//                "    }\n" +
//                "</script>\n" +
//                "\n" +
//                "<style scoped>\n" +
//                "\n" +
//                "</style>\n";
//
//        System.out.println("code iview template");
//        System.out.println(code);

        String code = FileUtil.readResourceFileData("genCodeTemplate/IviewEdit.vue");

        code = code
                .replace("#iViewColumnsRows#", iViewColumnsRows)
                .replace("#类名#", 类名)
                .replace("#实体名#", 实体名)
                .replace("#jsonDefaultNull#", jsonDefaultNull)
        ;
//        iViewColumnsRows
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "iview", "edit", 类名 + "Edit.vue");
        FileUtil.writeCode(dictDataPath, code);
        return code;
    }


    String jpaLikeRows() {
        StringBuilder rows = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
            String java字段名 = columnInfo.getJava字段名();
            String java字段名开头大写 = columnInfo.getJava字段名开头大写();
            String row = "if (!StringUtils.isNone(#实体名#.getId())) {\n" +
                    "            list.add(criteriaBuilder.equal(root.get(\"#java字段名#\").as(String.class), #实体名#.get#java字段名开头大写#()));\n" +
                    "        }\n";
            row = row.replace("#java字段名#", java字段名)
                    .replace("#java字段名开头大写#", java字段名开头大写)
                    .replace("#实体名#", 实体名);
            rows.append(row);
//            rows.replace("java字段名",java字段名).
        }
        return rows.toString();


//        return rows;

    }

    String json1() {

        StringBuilder rows = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
            String java字段名 = columnInfo.getJava字段名();
            String java字段名开头大写 = columnInfo.getJava字段名开头大写();
            String row = "if (!StringUtils.isNone(#实体名#.getId())) {\n" +
                    "            list.add(criteriaBuilder.equal(root.get(\"#java字段名#\").as(String.class), #实体名#.get#java字段名开头大写#()));\n" +
                    "        }\n";
            row = row.replace("#java字段名#", java字段名)
                    .replace("#java字段名开头大写#", java字段名开头大写)
                    .replace("#实体名#", 实体名);
            rows.append(row);
//            rows.replace("java字段名",java字段名).
        }
        return rows.toString();


//        return rows;

    }

    public String genIViewFormInputs() {
        StringBuilder res = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
            String java字段名 = columnInfo.getJava字段名();
            String commentShow = columnInfo.getColumnCommentShow();
            String row = "<Col span=\"8\">\n" +
                    "                            <FormItem label=\"#commentShow#\">\n" +
                    "                                <Input v-model=\"select.#java字段名#\"/>\n" +
                    "                            </FormItem>\n" +
                    "                        </Col>";
            row = row
                    .replace("#commentShow#", commentShow)
                    .replace("#java字段名#", java字段名);
            res.append(row);

        }
        return res.toString();
    }


    public String genFromMapRows() {

        StringBuilder res = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
//            String java字段名 = columnInfo.getJava字段名();
//            String commentShow = columnInfo.getColumnCommentShow();
//            String javaFieldType = columnInfo.getJavaFieldType();
//            String columnName = columnInfo.getCOLUMN_NAME();

            String mysqlToJavaFiledRow = columnInfo.getMysqlToJavaFiledRow();
//            String row=  "            #java字段名# = (#javaFieldType#)map.get(\"#columnName#\");\n";
//            String row=  "          #java字段名# =  Convert.to#javaFieldType#(map.get(\"#columnName#\")); \n";
//            row=  row
//                    .replace("#commentShow#",commentShow)
//                    .replace("#java字段名#",java字段名)
//                    .replace("#javaFieldType#",javaFieldType)
//                    .replace("#columnName#",columnName)
//            ;
//            res.append(row);
            res.append(mysqlToJavaFiledRow);

        }
        return res.toString();
    }

    //    final List<String> columnNames = columnInfos.stream().map(columnInfo -> {
//        String columnName = columnInfo.getCOLUMN_NAME();
//        final String mockData = columnInfo.mockData();
//        return "`" + columnName+ "`";
////            return columnName;
//    }).collect(Collectors.toList());
    public String genMockDataStr() {

//        StringBuilder res= new StringBuilder();
        final List<String> columnNames = columnInfos.stream().map(columnInfo -> {
//        String columnName = columnInfo.getCOLUMN_NAME();
            final String mockData = columnInfo.mockData();
//        return "`" + mockData+ "`";
//        St
            if ("null".equals(mockData)) {
                return "null";
            }
//        if(mockData)
            return "'" + mockData + "'";
//            return columnName;
        }).collect(Collectors.toList());
        final String columnNamesStr = String.join(",", columnNames);
//        for (ColumnInfo columnInfo : columnInfos) {
////            String java字段名 = columnInfo.getJava字段名();
////            String commentShow = columnInfo.getColumnCommentShow();
////            String javaFieldType = columnInfo.getJavaFieldType();
//            String columnName = columnInfo.getCOLUMN_NAME();
////            columnInfo.get
//            String mysqlToJavaFiledRow = columnInfo.getMysqlToJavaFiledRow();
////            String row=  "            #java字段名# = (#javaFieldType#)map.get(\"#columnName#\");\n";
//            String row=  "          `#columnName#`";
//            row=  row
//                    .replace("#commentShow#",commentShow)
//                    .replace("#java字段名#",java字段名)
//                    .replace("#javaFieldType#",javaFieldType)
//                    .replace("#columnName#",columnName)
//            ;
////            res.append(row);
//            res.append(mysqlToJavaFiledRow);
//
//        }
        return columnNamesStr;
    }
    public String genInsertDataStr() {

//        StringBuilder res= new StringBuilder();
        final List<String> columnNames = columnInfos.stream().map(columnInfo -> {
//        String columnName = columnInfo.getCOLUMN_NAME();
            final String column_name = columnInfo.getCOLUMN_NAME();
//            final String mockData = columnInfo.mockData();
////        return "`" + mockData+ "`";
////        St
//            if ("null".equals(mockData)) {
//                return "null";
//            }
//        if(mockData)
//            return "'" + mockData + "'";
            return "#{"+ column_name+"}";
//            return columnName;
        }).collect(Collectors.toList());
        final String columnNamesStr = String.join(",", columnNames);
//        for (ColumnInfo columnInfo : columnInfos) {
////            String java字段名 = columnInfo.getJava字段名();
////            String commentShow = columnInfo.getColumnCommentShow();
////            String javaFieldType = columnInfo.getJavaFieldType();
//            String columnName = columnInfo.getCOLUMN_NAME();
////            columnInfo.get
//            String mysqlToJavaFiledRow = columnInfo.getMysqlToJavaFiledRow();
////            String row=  "            #java字段名# = (#javaFieldType#)map.get(\"#columnName#\");\n";
//            String row=  "          `#columnName#`";
//            row=  row
//                    .replace("#commentShow#",commentShow)
//                    .replace("#java字段名#",java字段名)
//                    .replace("#javaFieldType#",javaFieldType)
//                    .replace("#columnName#",columnName)
//            ;
////            res.append(row);
//            res.append(mysqlToJavaFiledRow);
//
//        }
        return columnNamesStr;
    }
    public String genInsertIntoFields() {

//        StringBuilder res= new StringBuilder();
        final List<String> columnNames = columnInfos.stream().map(columnInfo -> {
            String columnName = columnInfo.getCOLUMN_NAME();
            return "`" + columnName + "`";
//            return columnName;
        }).collect(Collectors.toList());
        final String columnNamesStr = String.join(",", columnNames);
//        for (ColumnInfo columnInfo : columnInfos) {
////            String java字段名 = columnInfo.getJava字段名();
////            String commentShow = columnInfo.getColumnCommentShow();
////            String javaFieldType = columnInfo.getJavaFieldType();
//            String columnName = columnInfo.getCOLUMN_NAME();
////            columnInfo.get
//            String mysqlToJavaFiledRow = columnInfo.getMysqlToJavaFiledRow();
////            String row=  "            #java字段名# = (#javaFieldType#)map.get(\"#columnName#\");\n";
//            String row=  "          `#columnName#`";
//            row=  row
//                    .replace("#commentShow#",commentShow)
//                    .replace("#java字段名#",java字段名)
//                    .replace("#javaFieldType#",javaFieldType)
//                    .replace("#columnName#",columnName)
//            ;
////            res.append(row);
//            res.append(mysqlToJavaFiledRow);
//
//        }
        return columnNamesStr;
    }

    public String genToEsEntityRows() {
//        toEsEntityRows
        StringBuilder res = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
//            String java字段名 = columnInfo.getJava字段名();
            String commentShow = columnInfo.getColumnCommentShow();
//            String javaFieldType = columnInfo.getJavaFieldType();
//            String columnName = columnInfo.getCOLUMN_NAME();
//            columnInfo.getJavaFieldType();
            final String javaFieldName = columnInfo.getJavaFieldName();
            final String javaFieldNameStartsWithUppercase =
                    columnInfo.getJavaFieldNameStartsWithUppercase();
//            String mysqlToJavaFiledRow = columnInfo.getMysqlToJavaFiledRow();
            String row = "   #entityName#Es.set#javaFieldNameStartsWithUppercase#(#javaFieldName#);\n";
//            String row=  "          #java字段名# =  Convert.to#javaFieldType#(map.get(\"#columnName#\")); \n";
            row = row
                    .replace("#commentShow#", commentShow)
//                    .replace("#java字段名#",java字段名)
//                    .replace("#javaFieldType#",javaFieldType)
//                    .replace("#columnName#",columnName)
                    .replace("#entityName#", entityName)
                    .replace("#javaFieldNameStartsWithUppercase#", javaFieldNameStartsWithUppercase)
                    .replace("#javaFieldName#", javaFieldName)
            ;
            res.append(row);
//            res.append(mysqlToJavaFiledRow);

        }
        return res.toString();
    }

    public String genNamesRows() {

        StringBuilder res = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
//            String java字段名 = columnInfo.getJava字段名();
//            String commentShow = columnInfo.getColumnCommentShow();
//            String javaFieldType = columnInfo.getJavaFieldType();
            String columnName = columnInfo.getCOLUMN_NAME();
//            columnInfo.ge
//            final String javaFieldName = columnInfo.getMysqlToJavaFiledRow();
//            String mysqlToJavaFiledRow = columnInfo.getMysqlToJavaFiledRow();
//            String row=  "            #java字段名# = (#javaFieldType#)map.get(\"#columnName#\");\n";
            String row = "     public  static String  {columnName}=\"{columnName}\"; \n";
            row = row
//                    .replace("#commentShow#",commentShow)
//                    .replace("#java字段名#",java字段名)
//                    .replace("#javaFieldType#",javaFieldType)
//                    .replace("#columnName#",columnName)
                    .replace("{columnName}", columnName)
            ;
//            res.append(row);
            res.append(row);

        }
        return res.toString();
    }

    public String genAndEqualToList() {

        StringBuilder res = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
//            String java字段名 = columnInfo.getJava字段名();
//            String commentShow = columnInfo.getColumnCommentShow();
//            String javaFieldType = columnInfo.getJavaFieldType();
//            String columnName = columnInfo.getCOLUMN_NAME();

//            String mysqlToJavaFiledRow = columnInfo.getMysqlToJavaFiledRow();
            String andEqualTo = columnInfo.getAndEqualTo();
            res.append(andEqualTo).append("\n");

        }
        return res.toString();
    }

    public String genElmCols() {
        StringBuilder res = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
            String java字段名 = columnInfo.getJava字段名();
            String javaFieldNameStartsWithUppercase = columnInfo.getJavaFieldNameStartsWithUppercase();
            String commentShow = columnInfo.getColumnCommentShow();
            int width = Math.max(commentShow.length() * 20, 100); // 根据列名长度计算列宽度，最小为100px
            String row = "<el-table-column " +
                    ":formatter=\"format{javaFieldNameStartsWithUppercase}\" " +
                    " prop=\"#java字段名#\" label=\"#commentShow#\" " +
                    "width=\"#width#\" align=\"center\" sortable show-overflow-tooltip>\n" +
                    "  <template slot-scope=\"scope\">\n" +
                    "    <div v-html=\"scope.row.#java字段名#\"></div>\n" +
                    "  </template>\n" +
                    "  :formatter=\"format#java字段名#\"\n" + // 添加自定义格式化函数
                    "</el-table-column>\n";
            row = row
                    .replace("#commentShow#", commentShow)
                    .replace("#java字段名#", java字段名)
                    .replace("{javaFieldNameStartsWithUppercase}", javaFieldNameStartsWithUppercase)
                    .replace("#width#", Integer.toString(width))
            ;

            // 添加自定义格式化函数
//            row = row.replace(":formatter=\"format#java字段名#\"", "");
            String column_type = columnInfo.getCOLUMN_TYPE();
//            columnInfo.getColumnType()
//            if (column_type.toLowerCase().contains("date")) {
//                row = row.replace("</el-table-column>",
//                        ":formatter=\"format#java字段名#\"\n</el-table-column>");
//                row = row.replace("#java字段名#", java字段名);
//            }
            res.append(row);
        }
        return res.toString();
    }

    // 自定义格式化函数
    public String genFormatFunctions() {
        StringBuilder res = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
            String java字段名 = columnInfo.getJava字段名();
            String column_type = columnInfo.getCOLUMN_TYPE();
            if (column_type.toLowerCase().contains("date")) {
                res.append("format")
                        .append(java字段名.substring(0, 1).toUpperCase())
                        .append(java字段名.substring(1))
                        .append("(row) {\n")
                        .append("  const date = new Date(row.")
                        .append(java字段名)
                        .append(")\n")
                        .append("  return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()\n")
                        .append("}\n\n");
            }
        }
        return res.toString();
    }


//    public String genElmCols() {
//        StringBuilder res = new StringBuilder();
//        for (ColumnInfo columnInfo : columnInfos) {
//            String java字段名 = columnInfo.getJava字段名();
//            String commentShow = columnInfo.getColumnCommentShow();
////            String row = " <el-table-column prop=\"#java字段名#\" label=\"#commentShow#\" width=\"200\" align=\"center\"></el-table-column>\n";
//            String row = " <el-table-column prop=\"#java字段名#\" label=\"#commentShow#\" width=\"200\" align=\"center\">\n" +
//                    "  <template slot-scope=\"scope\">\n" +
//                    "          <div v-html=\"scope.row.#java字段名#\"></div>\n" +
//                    "        </template>\n" +
//                    "</el-table-column>\n";
//            row = row
//                    .replace("#commentShow#", commentShow)
//                    .replace("#java字段名#", java字段名);
//            res.append(row);
//
//        }
//        return res.toString();
//    }

    public static void main2ElTableCols(String[] args) {
        String row = " <el-table-column prop=\"#java字段名#\" label=\"#commentShow#\" width=\"200\" align=\"center\"></el-table-column>\n";
        System.out.println(row);
    }

    public String genTsDataDListParamsFields() {
        StringBuilder res = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
            String tsDataDType = columnInfo.getTsDataDListParams();
            res.append(tsDataDType + "\n");

        }
        return res.toString();
    }


    public String genTsDataDFields() {
        StringBuilder res = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
            String tsDataDType = columnInfo.getTsDataDType();
//            String java字段名 = columnInfo.getJava字段名();
//            String commentShow = columnInfo.getColumnCommentShow();
//            String column_name = columnInfo.getCOLUMN_NAME();
//            String java字段类型 = columnInfo.获取java字段类型();
//            String row=   " /**\n" +
//                    "     * #column_name#\n" +
//                    "     */\n" +
//                    "    @ApiModelProperty(\"#column_name#\")\n" +
//                    "    @Column(name=\"#column_name#\")\n" +
//                    "    private #java字段类型# #java字段名#;\n";
//            row=  row
//                    .replace("#commentShow#",commentShow)
//                    .replace("#java字段名#",java字段名)
//                    .replace("#column_name#",column_name)
//                    .replace("#java字段类型#",java字段类型)
//            ;
            res.append(tsDataDType + "\n");


        }
        return res.toString();
    }

    public String genJpaEntityFields(boolean withSwagger) {
        StringBuilder res = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
            String java字段名 = columnInfo.getJava字段名();
            String commentShow = columnInfo.getColumnCommentShow();
            String column_name = columnInfo.getCOLUMN_NAME();
            String java字段类型 = columnInfo.获取java字段类型();
            if (!"id".equals(column_name)) {
                String row = " /**\n" +
                        "     * #column_name#\n" +
                        "     */\n" +
                        (withSwagger ? "    @ApiModelProperty(\"#column_name#\")\n" : "") +
                        "    @Column(name=\"#column_name#\")\n" +
                        "    private #java字段类型# #java字段名#;\n";
                row = row
                        .replace("#commentShow#", commentShow)
                        .replace("#java字段名#", java字段名)
                        .replace("#column_name#", column_name)
                        .replace("#java字段类型#", java字段类型)
                ;
                res.append(row);
            }


        }
        return res.toString();
    }

    public String genJpaEntityFields() {
        StringBuilder res = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
            String java字段名 = columnInfo.getJava字段名();
            String commentShow = columnInfo.getColumnCommentShow();
            String column_name = columnInfo.getCOLUMN_NAME();
            String java字段类型 = columnInfo.获取java字段类型();
            if (!"id".equals(column_name)) {
                String row = " /**\n" +
                        "     * #column_name#\n" +
                        "     */\n" +
                        "    @ApiModelProperty(\"#column_name#\")\n" +
                        "    @Column(name=\"#column_name#\")\n" +
                        "    private #java字段类型# #java字段名#;\n";
                row = row
                        .replace("#commentShow#", commentShow)
                        .replace("#java字段名#", java字段名)
                        .replace("#column_name#", column_name)
                        .replace("#java字段类型#", java字段类型)
                ;
                res.append(row);
            }


        }
        return res.toString();
    }

    public String genElSearchItemRows() {
        StringBuilder res = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
            String java字段名 = columnInfo.getJava字段名();
            String commentShow = columnInfo.getColumnCommentShow();
            String column_name = columnInfo.getCOLUMN_NAME();
            String java字段类型 = columnInfo.获取java字段类型();
            String javaFieldName = columnInfo.getJavaFieldName();
            if (!"id".equals(column_name)) {
                String row = " {commentShow}  <el-input\n" +
                        "          placeholder=\"请输入{commentShow}\"\n" +
                        "          :maxlength=\"10\"\n" +
                        "          size=\"small\"\n" +
                        "          clearable\n" +
                        "          style=\"width: 200px\"\n" +
                        "          v-model=\"query.{javaFieldName}\"\n" +
                        "        ></el-input>\n";
                row = row
                        .replace("{commentShow}", commentShow)
                        .replace("{javaFieldName}", javaFieldName)
                ;
                res.append(row);
            }


        }
        return res.toString();
    }
    public String genElFormItemRows() {
        StringBuilder res = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
            String java字段名 = columnInfo.getJava字段名();
            String commentShow = columnInfo.getColumnCommentShow();
            String column_name = columnInfo.getCOLUMN_NAME();
            String java字段类型 = columnInfo.获取java字段类型();
            String javaFieldName = columnInfo.getJavaFieldName();
            if (!"id".equals(column_name)) {
                String row = "<el-form-item label=\"{commentShow}\" prop=\"{javaFieldName}\">\n" +
                        "            <el-input\n" +
                        "    v-model=\"queryParams.{javaFieldName}\"\n" +
                        "    placeholder=\"请输入{commentShow}\"\n" +
                        "    clearable\n" +
                        "            style=\"width: 200px\"  \n" +
                        "    @keyup.enter=\"handleQuery\"\n" +
                        "            />\n" +
                        "         </el-form-item>";
                row = row
                        .replace("{commentShow}", commentShow)
                        .replace("{javaFieldName}", javaFieldName)
                ;
                res.append(row);
            }


        }
        return res.toString();
    }

    public String genElTableColumnRows() {
        StringBuilder res = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
            String java字段名 = columnInfo.getJava字段名();
            String commentShow = columnInfo.getColumnCommentShow();
            String column_name = columnInfo.getCOLUMN_NAME();
            String java字段类型 = columnInfo.获取java字段类型();
            String javaFieldName = columnInfo.getJavaFieldName();
            if (!"id".equals(column_name)) {
                String row = "<el-table-column label=\"{commentShow}\" align=\"center\" prop=\"{javaFieldName}\" />";
                row = row
                        .replace("{commentShow}", commentShow)
                        .replace("{javaFieldName}", javaFieldName)
                ;
                res.append(row);
            }


        }
        return res.toString();
    }


    public String genElmFormItems() {
        StringBuilder res = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
            String java字段名 = columnInfo.getJava字段名();
            String commentShow = columnInfo.getColumnCommentShow();
            String row = " <el-form-item label=\"#commentShow#\">\n" +
                    "                    <el-input " +
                    " placeholder=\"请输入内容\" \n" +
                    ":maxlength=\"10\" size=\"small\" clearable\n" +
                    "  style=\"width: 200px;\"  " +
                    "" +
                    "v-model=\"form.#java字段名#\"></el-input>\n" +
                    "                </el-form-item>\n";
            row = row
                    .replace("#commentShow#", commentShow)
                    .replace("#java字段名#", java字段名);
            res.append(row);

        }
        return res.toString();
    }

    public String genElmQueryInputs() {
        return genElmQueryInputs("query");

    }

    /**
     * label 显示
     *
     * @param formName
     * @return
     */
    public String genElmQueryInputs(String formName) {
        StringBuilder res = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
            String java字段名 = columnInfo.getJava字段名();
            String commentShow = columnInfo.getColumnCommentShow();
            String row = " <el-form-item label=\"#commentShow#\">\n" +
                    "          <el-input  placeholder=\"请输入内容\" \n" +
                    ":maxlength=\"10\" size=\"small\" clearable\n" +
                    "  style=\"width: 200px;\"   v-model=\"#formName#.#java字段名#\"></el-input>\n" +
                    "        </el-form-item>\n";
            row = row
                    .replace("#commentShow#", commentShow)
                    .replace("#java字段名#", java字段名)
                    .replace("#formName#", formName);
            res.append(row);

        }
        return res.toString();
    }

    public String genJdl() {

        String jdl = "entity #className# {\n" +
                "       #jdlRows#\n" +
                "}";

        String jdlRows = genJdlRows();
        return jdl
                .replace("#className#", className)
                .replace("#jdlRows#", jdlRows)
                ;

    }

    public String genJdlRows() {
        StringBuilder res = new StringBuilder();

        for (ColumnInfo columnInfo : columnInfos) {

            String jdlRow = columnInfo.getJdlRow();
            res.append(jdlRow);

        }
        return res.toString();
    }

    public String genColumnsDicMap(String export) throws Exception {

        String prefix =export+ " const #实体名#Columns = [ \n ".replace("#实体名#", 实体名);
        StringBuilder res = new StringBuilder(prefix);
        for (ColumnInfo columnInfo : columnInfos) {
            String java字段名 = columnInfo.getJava字段名();
            String commentShow = columnInfo.getColumnCommentShow();

            String row = "{\n" +
                    "    label: \"#commentShow#\", key: \"#java字段名#\"\n" +
                    "  },\n";
            row = row
                    .replace("#commentShow#", commentShow)
                    .replace("#java字段名#", java字段名);
            res.append(row);
        }
        res.append(" ]\n");
        return res.toString();

    }

    public String genColumnsDicMapUnderScore(String export) throws Exception {

        String prefix =export+ " const #实体名#ColumnsUnderScore = [ \n "
                .replace("#实体名#", 实体名);
        StringBuilder res = new StringBuilder(prefix);
        for (ColumnInfo columnInfo : columnInfos) {
            String java字段名 = columnInfo.getJava字段名();
            String commentShow = columnInfo.getColumnCommentShow();
            String column_name = columnInfo.getCOLUMN_NAME();

            String row = "{\n" +
                    "    label: \"#commentShow#\", key: \"#column_name#\"\n" +
                    "  },\n";
            row = row
                    .replace("#commentShow#", commentShow)
                    .replace("#java字段名#", java字段名)
                    .replace("#column_name#", column_name)
            ;
            res.append(row);
        }
        res.append(" ]\n");
        return res.toString();

    }
    public String genTableNameExcelColsMapRow() throws Exception {

        String tableNameExcelColsMapRowTpl="'{entityName}':{entityName}Columns,";
        String tableNameExcelColsMapRow=
                tableNameExcelColsMapRowTpl
                        .replace("{tableName}",tableName)
                        .replace("{entityName}",entityName)
                ;
        return  tableNameExcelColsMapRow;
    }

    public String genTableNameExcelColsMapRowUnderScore() throws Exception {
        String tableNameExcelColsMapRowTpl="'{entityName}':{entityName}ColumnsUnderScore,";
        String tableNameExcelColsMapRow=
                tableNameExcelColsMapRowTpl
                        .replace("{tableName}",tableName)
//                        .replace("{tableName}",tableName)
                        .replace("{entityName}",entityName)
                ;
        return  tableNameExcelColsMapRow;

    }
    public String genTableNameOriginExcelColsMapRowUnderScore() throws Exception {
//        tableNameOrigin
        String tableNameExcelColsMapRowTpl="'{tableNameOrigin}':{entityName}ColumnsUnderScore,";
        String tableNameExcelColsMapRow=
                tableNameExcelColsMapRowTpl
                        .replace("{tableNameOrigin}",tableNameOrigin)
//                        .replace("{tableName}",tableName)
                        .replace("{entityName}",entityName)
                ;
        return  tableNameExcelColsMapRow;

    }

    public String genMybatisPlusSelectPageEqualRows() {

        StringBuilder MybatisPlusSelectPageLikeRows = new StringBuilder();
//        类名
        for (ColumnInfo columnInfo : columnInfos) {
            String java字段名 = columnInfo.getJava字段名();
//            StringUtils.isnu

            String java字段名开头大写 = columnInfo.getJava字段名开头大写();
            String commentShow = columnInfo.getColumnCommentShow();

            String row = ".eq(\n" +
                    "                            !StringUtils.isNullOrEmpty(#实体名#.get#java字段名开头大写#()),\n" +
                    "                            #类名#::get#java字段名开头大写#,\n" +
                    "                            #实体名#.get#java字段名开头大写#()\n" +
                    "                    )\n";
            row = row
                    .replace("#commentShow#", commentShow)
                    .replace("#java字段名#", java字段名)
                    .replace("#实体名#", 实体名)
                    .replace("#类名#", 类名)
                    .replace("#java字段名开头大写#", java字段名开头大写)
            ;
            MybatisPlusSelectPageLikeRows.append(row);
        }
        return MybatisPlusSelectPageLikeRows.toString();
    }

    String genMybatisPlusSelectPageLikeRows() {
        StringBuilder MybatisPlusSelectPageLikeRows = new StringBuilder();
//        类名
        for (ColumnInfo columnInfo : columnInfos) {
            String java字段名 = columnInfo.getJava字段名();
            String java字段名开头大写 = columnInfo.getJava字段名开头大写();
            String commentShow = columnInfo.getColumnCommentShow();

            String column_type = columnInfo.getCOLUMN_TYPE();
            String compareType="like";
            if (MysqlDataType.isNumberType(column_type)) {
                 compareType="eq";
            }
            String row = ".{compareType}(\n" +
                    "                            !StringUtils.isNullOrEmpty(#实体名#.get#java字段名开头大写#()),\n" +
                    "                            #类名#::get#java字段名开头大写#,\n" +
                    "                            #实体名#.get#java字段名开头大写#()\n" +
                    "                    )\n";
            row = row
                    .replace("#commentShow#", commentShow)
                    .replace("#java字段名#", java字段名)
                    .replace("#实体名#", 实体名)
                    .replace("#类名#", 类名)
                    .replace("#java字段名开头大写#", java字段名开头大写)
                    .replace("{compareType}", compareType)
            ;
            MybatisPlusSelectPageLikeRows.append(row);
        }
        return MybatisPlusSelectPageLikeRows.toString();
    }

    public String genMybatisPlusSelectPage() {

//        String prefix="export const #实体名#Columns = [ \n ".replace("#实体名#",实体名);
//        StringBuilder res= new StringBuilder(prefix);
        StringBuilder MybatisPlusSelectPageLikeRows = new StringBuilder();
//        类名
        for (ColumnInfo columnInfo : columnInfos) {
            String java字段名 = columnInfo.getJava字段名();
            String java字段名开头大写 = columnInfo.getJava字段名开头大写();
            String commentShow = columnInfo.getColumnCommentShow();

            String row = ".like(\n" +
                    "                            !StringUtils.isNullOrEmpty(#实体名#.get#java字段名开头大写#()),\n" +
                    "                            #类名#::get#java字段名开头大写#,\n" +
                    "                            #实体名#.get#java字段名开头大写#()\n" +
                    "                    )\n";
            row = row
                    .replace("#commentShow#", commentShow)
                    .replace("#java字段名#", java字段名)
                    .replace("#实体名#", 实体名)
                    .replace("#类名#", 类名)
                    .replace("#java字段名开头大写#", java字段名开头大写)
            ;
            MybatisPlusSelectPageLikeRows.append(row);
        }
        String res = "@RequestMapping(value = \"/selectPage\", method = RequestMethod.POST)\n" +
                "    public Result selectPage(@RequestBody #类名# #实体名#,\n" +
                "                             @RequestParam(required = false, defaultValue = \"0\") int pageNumber,\n" +
                "                             @RequestParam(required = false, defaultValue = \"10\") int pageSize) {\n" +
                "        try {\n" +
                "            log.info(\"selectPage\");\n" +
                "            PageHelper.startPage(pageNumber,pageSize);\n" +
                "" +
                "            LambdaQueryWrapper<#类名#> like = Wrappers.lambdaQuery();\n" +
                "            like" +
                "                    #MybatisPlusSelectPageLikeRows#     " +
                "                    ;\n" +
                "            List<#类名#> list = #实体名#Service.list(like);\n" +
                "            Page writersPage = (Page) list;\n" +
                "            return Result.success(writersPage);\n" +
                "        }\n" +
                "        catch (Exception e){\n" +
                "            log.info(\"error {}\",e.getMessage());\n" +
                "            return  Result.error(e.getMessage());\n" +
                "        }\n" +
                "        finally {\n" +
                "            PageHelper.clearPage();\n" +
                "        }\n" +
                "    }";
        res = res
                .replace("#实体名#", 实体名)
                .replace("#类名#", 类名)
                .replace("#MybatisPlusSelectPageLikeRows#", MybatisPlusSelectPageLikeRows)
        ;
        return res.toString();

    }

    String pathFileString;

    public String genIViewForm() throws Exception {

        String code = FileUtil.readResourceFileData("genCodeTemplate/IViewForm.vue");
        String iViewFormInputs = genIViewFormInputs();
        String jsonDefaultNull = genJsonDefaultNull();
        String iViewColumnsRows = genIViewColumnsRows();
        code = code
                .replace("#formInputs#", iViewFormInputs)
                .replace("#类名#", 类名)
                .replace("#实体名#", 实体名)
                .replace("#jsonDefaultNull#", jsonDefaultNull)
                .replace("#iViewColumnsRows#", iViewColumnsRows)
        ;
//        iViewColumnsRows
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "iview", "form", 类名 + "Form.vue");
        FileUtil.writeCode(dictDataPath, code);

        return code;
    }


    public String genIViewManage() throws Exception {

        String code = FileUtil.readResourceFileData("genCodeTemplate/IViewManage.vue");
        String iViewFormInputs = genIViewFormInputs();
        String jsonDefaultNull = genJsonDefaultNull();
        String iViewColumnsRows = genIViewColumnsRows();
        code = code
                .replace("#formInputs#", iViewFormInputs)
                .replace("#类名#", 类名)
                .replace("#实体名#", 实体名)
                .replace("#jsonDefaultNull#", jsonDefaultNull)
                .replace("#iViewColumnsRows#", iViewColumnsRows)
        ;
//        iViewColumnsRows
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "iview", "manage", 类名 + "Manage.vue");
        FileUtil.writeCode(dictDataPath, code);

        return code;
    }

    String genBatchAddPartsSetRows() {
//        AtomicReference<String> rows= new AtomicReference<>("");
        if (columnInfos == null) {
            return null;
        }
        List<String> rowNames = columnInfos.stream().map(columnInfo -> {
            String java字段名 = columnInfo.getJava字段名();
//            entityName;
//            String column_comment = columnInfo.getCOLUMN_COMMENT();
            String column_comment = columnInfo.getColumnCommentShow();
            final String batchAddPartsSetRow = columnInfo.genBatchAddPartsSetRow(entityName);
//            String row = "     bill.setBill_dorm(Integer.parseInt(billParts.get(partIdx++).toString()));\n";
//            row = row.
//                    replace("#java字段名#", java字段名)
//                    .replace("#column_comment#", column_comment)
//            ;
            return batchAddPartsSetRow;
        }).collect(Collectors.toList());

        StringBuilder rows = new StringBuilder();
//        rowNames.stream().map(columnInfo -> {
////            rows.ad
////            rows+= columnInfo.get();
//        })
        for (String rowName : rowNames) {
            rows.append(rowName);
        }
//        return  String.join(",\n", rowNames);
        return rows.toString();
//        return   " columns: [  \n"+ String.join(",\n", rowNames) +" \n ]";
    }

    public String genServiceImplMybatisPlus(String 包名) throws Exception {

        String code = FileUtil.readResourceFileData(
                "genCodeTemplate/mybatisPlus/serviceImpl.java");
        final String batchAddPartsSetRows = genBatchAddPartsSetRows();
        code = code
                .replace("#类名#", 类名)
                .replace("#实体名#", 实体名)
                .replace("#包名#", 包名)
                .replace("{batchAddPartsSetRows}", batchAddPartsSetRows)
        ;
//        iViewColumnsRows
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "mybatisPlus", "service", "impl", 类名 + "ServiceImpl.java");
        FileUtil.writeCode(dictDataPath, code);

        return code;
    }

    public String genCsv() throws Exception {

        String collect = columnInfos.stream().map(o -> o.getColumnCommentShow()).collect(Collectors.joining(","));
//        iViewColumnsRows
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "csv", 类名 + ".csv");
        FileUtil.writeCodeBom(dictDataPath, collect);
//        ANSI– 保存
        return collect;
    }

    public String genMapperJavaMybatisPlus(String 包名) throws Exception {

        String code = FileUtil.readResourceFileData("genCodeTemplate/mybatisPlus/Mapper.java");
        code = code
                .replace("#类名#", 类名)
                .replace("#实体名#", 实体名)
                .replace("#包名#", 包名)
        ;
//        iViewColumnsRows
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "mybatisPlus", "mapper", 类名 + "Mapper.java");
        FileUtil.writeCode(dictDataPath, code);

        return code;
    }

    public String genServiceMybatisPlus(String 包名) throws Exception {

        String code = FileUtil.readResourceFileData("genCodeTemplate/mybatisPlus/Service.java");
        code = code
                .replace("#类名#", 类名)
                .replace("#实体名#", 实体名)
                .replace("#包名#", 包名)
        ;
//        iViewColumnsRows
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "mybatisPlus", "service", 类名 + "Service.java");
        FileUtil.writeCode(dictDataPath, code);

        return code;
    }


    public String genIViewApi() throws Exception {

        String code = FileUtil.readResourceFileData(
                "genCodeTemplate/api/api.js");
//        String iViewFormInputs = genIViewFormInputs();
//        String jsonDefaultNull = genJsonDefaultNull();
//        String iViewColumnsRows = genIViewColumnsRows();
        // .replace("#className#", className)
//     .replace("#entityName#", entityName)
        String jsonDefaultNull = genJsonDefaultNull();
        code = code
//                .replace("#formInputs#", iViewFormInputs)
//                .replace("#类名#", 类名)
                .replace("#实体名#", 实体名)
                .replace("#entityName#", entityName)
                .replace("#jsonDefaultNull#", jsonDefaultNull)
//                .replace("#iViewColumnsRows#", iViewColumnsRows)
        ;
//        iViewColumnsRows
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "iview", "api", entityName + ".js");
//                "iview","api", 实体名+".js");

        FileUtil.writeCode(dictDataPath, code);

        return code;
    }

    public String genElementUiTable() throws Exception {

        String code = FileUtil.readResourceFileData("genCodeTemplate/elementUi/ElementTable.vue");
//        String iViewFormInputs = genIViewFormInputs();
        String jsonDefaultNull = genJsonDefaultNull();
//        String iViewColumnsRows = genIViewColumnsRows();
        String elmCols = genElmCols();
        String elmFormItems = genElmFormItems();
        String elmQueryInputs = genElmQueryInputs();
        String elmQueryInputsSelectedRow = genElmQueryInputs("selectedRow");
        code = code
//                .replace("#formInputs#", iViewFormInputs)
                .replace("#类名#", 类名)
                .replace("#实体名#", 实体名)
//                .replace("#实体名#", 实体名)
                .replace("#elmFormItems#", elmFormItems)
                .replace("#elmCols#", elmCols)
                .replace("#jsonDefaultNull#", jsonDefaultNull)
                .replace("#elmQueryInputs#", elmQueryInputs)
                .replace("#elmFormItemsSelectedRow#", elmQueryInputsSelectedRow)
//                .replace("#jsonDefaultNull#", jsonDefaultNull)
//                .replace("#iViewColumnsRows#", iViewColumnsRows)
        ;
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "elementUi", "table", 类名 + "Table.vue");
        FileUtil.writeCode(dictDataPath, code);

        return code;
    }

    public String genElementPlusTableMybatisPlus() throws Exception {

        String code = FileUtil.readResourceFileData("genCodeTemplate/elmPlus/ElementTableMybatisPlus.vue");
//        String iViewFormInputs = genIViewFormInputs();
        String jsonDefaultNull = genJsonDefaultNull();
//        String iViewColumnsRows = genIViewColumnsRows();
        String elmCols = genElmCols();
        String elmFormItems = genElmFormItems();
        String elmQueryInputs = genElmQueryInputs();
        String elmQueryInputsSelectedRow = genElmQueryInputs("selectedRow");
        code = code
//                .replace("#formInputs#", iViewFormInputs)
                .replace("#类名#", 类名)
                .replace("#实体名#", 实体名)
//                .replace("#实体名#", 实体名)
                .replace("#elmFormItems#", elmFormItems)
                .replace("#elmCols#", elmCols)
                .replace("#jsonDefaultNull#", jsonDefaultNull)
                .replace("#elmQueryInputs#", elmQueryInputs)
                .replace("#elmFormItemsSelectedRow#", elmQueryInputsSelectedRow)
//                .replace("#jsonDefaultNull#", jsonDefaultNull)
//                .replace("#iViewColumnsRows#", iViewColumnsRows)
        ;
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "elmPlus", "tableMybatisPlus", 类名 + "Table.vue");
        FileUtil.writeCode(dictDataPath, code);

        return code;
    }


    public String genElementTableMybatisPlus() throws Exception {

        String code = FileUtil.readResourceFileData("genCodeTemplate/elementUi/ElementTableMybatisPlus.vue");
//        String iViewFormInputs = genIViewFormInputs();
        String jsonDefaultNull = genJsonDefaultNull();
//        String iViewColumnsRows = genIViewColumnsRows();
        String elmCols = genElmCols();
        String elmFormItems = genElmFormItems();
        String elmQueryInputs = genElmQueryInputs();
        String elmQueryInputsSelectedRow = genElmQueryInputs("selectedRow");
        code = code
//                .replace("#formInputs#", iViewFormInputs)
                .replace("#类名#", 类名)
                .replace("#实体名#", 实体名)
//                .replace("#实体名#", 实体名)
                .replace("#elmFormItems#", elmFormItems)
                .replace("#elmCols#", elmCols)
                .replace("#jsonDefaultNull#", jsonDefaultNull)
                .replace("#elmQueryInputs#", elmQueryInputs)
                .replace("#elmFormItemsSelectedRow#", elmQueryInputsSelectedRow)
//                .replace("#jsonDefaultNull#", jsonDefaultNull)
//                .replace("#iViewColumnsRows#", iViewColumnsRows)
        ;
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "elementUi", "tableMybatisPlus", 类名 + "Table.vue");
        FileUtil.writeCode(dictDataPath, code);

        return code;
    }


    public String genReactRuoyiDrawerFormRows() {
        StringBuilder res = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
//            String java字段名 = columnInfo.getJava字段名();
//            String commentShow = columnInfo.getColumnCommentShow();
////            javafi
//            String javaFieldName = columnInfo.getJavaFieldName();
            String reactRuoyiDrawerFormRow = columnInfo.makeReactRuoyiDrawerFormRow(className);

//            String row=   "#javaFieldName#: props.values.#javaFieldName#,\n";
//
//            row=  row
//                    .replace("#commentShow#",commentShow)
////                    .replace("#java字段名#",java字段名)
//                    .replace("#javaFieldName#",javaFieldName)
//                    .replace("#className#",className)
//                    .replace("#reactRuoyiDrawerFormRow#",reactRuoyiDrawerFormRow)
//            ;
            res.append(reactRuoyiDrawerFormRow).append("\n");

        }
//        String  cols="const columns = [ #columnsRows# ]"
//                .replace("#columnsRows#" ,res.toString());
        return res.toString();
//        return cols;
    }

//    public String genReactRuoyiRouteRows(){
//        StringBuilder res= new StringBuilder();
//        for (ColumnInfo columnInfo : columnInfos) {
////            String java字段名 = columnInfo.getJava字段名();
//            String commentShow = columnInfo.getColumnCommentShow();
////            javafi
//            String javaFieldName = columnInfo.getJavaFieldName();
//            String row=   "#javaFieldName#: props.values.#javaFieldName#,\n";
//
//            row=  row
//                    .replace("#commentShow#",commentShow)
////                    .replace("#java字段名#",java字段名)
//                    .replace("#javaFieldName#",javaFieldName)
//                    .replace("#className#",className)
//            ;
//            res.append(row);
//
//        }
////        String  cols="const columns = [ #columnsRows# ]"
////                .replace("#columnsRows#" ,res.toString());
//        return res.toString();
////        return cols;
//    }

    public String genReactRuoyiSetFieldsValueRows() {
        StringBuilder res = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
//            String java字段名 = columnInfo.getJava字段名();
            String commentShow = columnInfo.getColumnCommentShow();
//            javafi
            String javaFieldName = columnInfo.getJavaFieldName();
            String row = "#javaFieldName#: props.values.#javaFieldName#,\n";

            row = row
                    .replace("#commentShow#", commentShow)
//                    .replace("#java字段名#",java字段名)
                    .replace("#javaFieldName#", javaFieldName)
                    .replace("#className#", className)
            ;
            res.append(row);

        }
//        String  cols="const columns = [ #columnsRows# ]"
//                .replace("#columnsRows#" ,res.toString());
        return res.toString();
//        return cols;
    }


    public String genReactRuoyiCols() {
        StringBuilder res = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
            String commentShow = columnInfo.getColumnCommentShow();
            String javaFieldName = columnInfo.getJavaFieldName();
            String row = "{\n" +
                    "      title: <FormattedMessage id=\"system.#className#.#javaFieldName#\" defaultMessage=\"#commentShow#\" />,\n" +
                    "      dataIndex: '#javaFieldName#',\n" +
                    "      hideInSearch: true,\n" +
                    "      sorter: true,\n" +
                    "      defaultSortOrder: 'ascend',\n" +
                    "    },\n";

            row = row
                    .replace("#commentShow#", commentShow)
//                    .replace("#java字段名#",java字段名)
                    .replace("#javaFieldName#", javaFieldName)
                    .replace("#className#", className)
            ;
            res.append(row);

        }
//        String  cols="const columns = [ #columnsRows# ]"
//                .replace("#columnsRows#" ,res.toString());
        return res.toString();
//        return cols;
    }

    public String genReactTableCols() {
        StringBuilder res = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
            String java字段名 = columnInfo.getJava字段名();
            String commentShow = columnInfo.getColumnCommentShow();
            String row = "{\n" +
                    "        title: '#commentShow#',\n" +
                    "        dataIndex: '#java字段名#',\n" +
                    "        key: '#java字段名#',\n" +
                    "        render: text => <Button type='link'>{text}</Button>\n" +
                    "    },\n";
            row = row
                    .replace("#commentShow#", commentShow)
                    .replace("#java字段名#", java字段名)
            ;
            res.append(row);

        }
//        String  cols="const columns = [ #columnsRows# ]"
//                .replace("#columnsRows#" ,res.toString());
        return res.toString();
//        return cols;
    }


    public String genReactFormCols() {
        StringBuilder res = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
            String reactFormItem = columnInfo.makeReactFormItem();

            res.append(reactFormItem);

//            String java字段名 = columnInfo.getJava字段名();
//            String commentShow = columnInfo.getColumnCommentShow();
//            String row=   "<Form.Item\n" +
//                    "                                    label={\n" +
//                    "                                        <span>\n" +
//                    "                                            #commentShow#&nbsp;\n" +
//                    "                                            <Tooltip title='可以尽量好听点，真的!'>\n" +
//                    "                                                <Icon type='question-circle-o' />\n" +
//                    "                                            </Tooltip>\n" +
//                    "                                        </span>\n" +
//                    "                                    }>\n" +
//                    "                                    {getFieldDecorator('#java字段名#', {\n" +
//                    "                                        rules: [{ required: true, message: '请输入#commentShow#' }]\n" +
//                    "                                    })(<Input placeholder='请输入#commentShow#' />)}\n" +
//                    "                                </Form.Item>\n";
//            row=  row
//                    .replace("#commentShow#",commentShow)
//                    .replace("#java字段名#",java字段名)
//            ;
//            res.append(row);

        }
//        String  cols="const columns = [ #columnsRows# ]"
//                .replace("#columnsRows#" ,res.toString());
        return res.toString();
//        return cols;
    }


    /**
     * 会写入磁盘
     *
     * @param type
     * @param fileName
     * @return
     * @throws Exception
     */
    public String genIndexJs(String type, String fileName) throws Exception {
//        String code = FileUtil.readResourceFileData("genCodeTemplate/react/antDesign/Table.jsx");
        String codeIndexJs = FileUtil.readResourceFileData("genCodeTemplate/react/antDesign/index.js");
//        String jpaEntityFields = genJpaEntityFields();
//        String fromMapRows = genFromMapRows();
//        String reactTableCols = genReactTableCols();
        String now = DateUtilSt.getNow();
        codeIndexJs = codeIndexJs
//                .replace("#formInputs#", iViewFormInputs)
//                .replace("#类名#", 类名)
                .replace("#fileName#", fileName)


        ;
//        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
//                "react","antDesign", 类名+"Table",类名+"Table.jsx");
//        FileUtil.writeCode(dictDataPath,code);

        java.nio.file.Path indexJsPath = Paths.get(pathFileString,
                "react", "antDesign", type, fileName, "index.js");
        FileUtil.writeCode(indexJsPath, codeIndexJs);
        return codeIndexJs;
    }


    public String genReactTable(String 包名) throws Exception {
        String code = FileUtil.readResourceFileData("genCodeTemplate/react/antDesign/Table.jsx");
//        String codeIndexJs = FileUtil.readResourceFileData("genCodeTemplate/react/antDesign/index.js");
//        String jpaEntityFields = genJpaEntityFields();
//        String fromMapRows = genFromMapRows();
        String reactTableCols = genReactTableCols();
        String now = DateUtilSt.getNow();
        code = code
//                .replace("#formInputs#", iViewFormInputs)
//                .replace("#类名#", 类名)
                .replace("#实体名#", 实体名)
                .replace("#包名#", 包名)
                .replace("#类名#", 类名)
//                .replace("#jpaEntityFields#", jpaEntityFields)
//                .replace("#fromMapRows#", fromMapRows)
//                .replace("#tableName#", tableName)
                .replace("#date#", now)
                .replace("#reactTableCols#", reactTableCols)

        ;
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "react", "antDesign", "Table", 类名 + "Table", 类名 + "Table.jsx");
        FileUtil.writeCode(dictDataPath, code);
        String table = genIndexJs("Table", 类名 + "Table");

//        java.nio.file.Path indexJsPath = Paths.get(pathFileString,
//                "react","antDesign", 类名+"Table","index.js");
//        String
//        FileUtil.writeCode(indexJsPath,code);

        return code;
    }

    public String genXueZhiSiApiJs(String 包名) throws Exception {
        String code = FileUtil.readResourceFileData(
                "genCodeTemplate/xueZhiSi/api.js");

        String now = DateUtilSt.getNow();
        code = code
//                .replace("#formInputs#", iViewFormInputs)
//                .replace("#类名#", 类名)
                .replace("#实体名#", 实体名)
                .replace("#包名#", 包名)
                .replace("#类名#", 类名)
//                .replace("#jpaEntityFields#", jpaEntityFields)
//                .replace("#fromMapRows#", fromMapRows)
//                .replace("#tableName#", tableName)
                .replace("#date#", now)

        ;
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "xueZhiSi", "api", 实体名 + ".js");
        FileUtil.writeCode(dictDataPath, code);


        return code;
    }

    public String genReactRuoyiService(String 包名) throws Exception {
//        genCodeTemplate.ruoyi.react
        String code = FileUtil.readResourceFileData("genCodeTemplate/ruoyi/react/service.ts");
//        String codeIndexJs = FileUtil.readResourceFileData("genCodeTemplate/react/antDesign/index.js");
//        String jpaEntityFields = genJpaEntityFields();
//        String fromMapRows = genFromMapRows();
//        ReactRuoyiCols
//        String reactTableCols = genReactTableCols();
//        String reactRuoyiCols = genReactRuoyiCols();
//        genReactRuoyiCols
        String now = DateUtilSt.getNow();
        code = code
//                .replace("#formInputs#", iViewFormInputs)
//                .replace("#类名#", 类名)
//                .replace("#实体名#", 实体名)
                .replace("#包名#", 包名)
//                .replace("#类名#", 类名)
                .replace("#className#", className)
                .replace("#entityName#", entityName)
//                .replace("#jpaEntityFields#", jpaEntityFields)
//                .replace("#fromMapRows#", fromMapRows)
//                .replace("#tableName#", tableName)
                .replace("#date#", now)
//                .replace("#reactTableCols#", reactTableCols)
//                .replace("#reactRuoyiCols#", reactRuoyiCols)

        ;
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "react", "ruoyi", entityName, "service.ts");
        FileUtil.writeCode(dictDataPath, code);


        return code;
    }

//    public String genReactRuoyiRoutes(String 包名) throws Exception {
////        genCodeTemplate.ruoyi.react
//        String code = FileUtil.readResourceFileData("genCodeTemplate/ruoyi/react/routes.ts");
////        String codeIndexJs = FileUtil.readResourceFileData("genCodeTemplate/react/antDesign/index.js");
////        String jpaEntityFields = genJpaEntityFields();
////        String fromMapRows = genFromMapRows();
////        ReactRuoyiCols
////        String reactTableCols = genReactTableCols();
////        String reactRuoyiCols = genReactRuoyiCols();
////        genReactRuoyiCols
//        String reactRuoyiSetFieldsValueRows = genReactRuoyiSetFieldsValueRows();
//        String reactRuoyiDrawerFormRows = genReactRuoyiDrawerFormRows();
//        String now = DateUtilSt.getNow();
//        code = code
////                .replace("#formInputs#", iViewFormInputs)
////                .replace("#类名#", 类名)
////                .replace("#实体名#", 实体名)
//                .replace("#包名#", 包名)
////                .replace("#类名#", 类名)
//                .replace("#className#", className)
//                .replace("#entityName#", entityName)
//                .replace("#reactRuoyiSetFieldsValueRows#", reactRuoyiSetFieldsValueRows)
//                .replace("#reactRuoyiDrawerFormRows#", reactRuoyiDrawerFormRows)
////                .replace("#jpaEntityFields#", jpaEntityFields)
////                .replace("#fromMapRows#", fromMapRows)
////                .replace("#tableName#", tableName)
//                .replace("#date#", now)
////                .replace("#reactTableCols#", reactTableCols)
////                .replace("#reactRuoyiCols#", reactRuoyiCols)
//
//        ;
//        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
//                "react","ruoyi",entityName,"components","edit.tsx");
//        FileUtil.writeCode(dictDataPath,code);
//
//
//        return code;
//    }
//

    public String genReactRuoyiEdit(String 包名) throws Exception {
//        genCodeTemplate.ruoyi.react
        String code = FileUtil.readResourceFileData("genCodeTemplate/ruoyi/react/components/edit.tsx");
//        String codeIndexJs = FileUtil.readResourceFileData("genCodeTemplate/react/antDesign/index.js");
//        String jpaEntityFields = genJpaEntityFields();
//        String fromMapRows = genFromMapRows();
//        ReactRuoyiCols
//        String reactTableCols = genReactTableCols();
//        String reactRuoyiCols = genReactRuoyiCols();
//        genReactRuoyiCols
        String reactRuoyiSetFieldsValueRows = genReactRuoyiSetFieldsValueRows();
        String reactRuoyiDrawerFormRows = genReactRuoyiDrawerFormRows();
        String now = DateUtilSt.getNow();
        code = code
//                .replace("#formInputs#", iViewFormInputs)
//                .replace("#类名#", 类名)
//                .replace("#实体名#", 实体名)
                .replace("#包名#", 包名)
//                .replace("#类名#", 类名)
                .replace("#className#", className)
                .replace("#entityName#", entityName)
                .replace("#reactRuoyiSetFieldsValueRows#", reactRuoyiSetFieldsValueRows)
                .replace("#reactRuoyiDrawerFormRows#", reactRuoyiDrawerFormRows)
//                .replace("#jpaEntityFields#", jpaEntityFields)
//                .replace("#fromMapRows#", fromMapRows)
//                .replace("#tableName#", tableName)
                .replace("#date#", now)
//                .replace("#reactTableCols#", reactTableCols)
//                .replace("#reactRuoyiCols#", reactRuoyiCols)

        ;
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "react", "ruoyi", entityName, "components", "edit.tsx");
        FileUtil.writeCode(dictDataPath, code);


        return code;
    }


    public String genReactRuoyiTable(String 包名) throws Exception {
//        genCodeTemplate.ruoyi.react
        String code = FileUtil.readResourceFileData("genCodeTemplate/ruoyi/react/index.tsx");
//        String codeIndexJs = FileUtil.readResourceFileData("genCodeTemplate/react/antDesign/index.js");
//        String jpaEntityFields = genJpaEntityFields();
//        String fromMapRows = genFromMapRows();
//        ReactRuoyiCols
//        String reactTableCols = genReactTableCols();
        String reactRuoyiCols = genReactRuoyiCols();
//        genReactRuoyiCols
        String now = DateUtilSt.getNow();
        code = code
//                .replace("#formInputs#", iViewFormInputs)
//                .replace("#类名#", 类名)
//                .replace("#实体名#", 实体名)
                .replace("#包名#", 包名)
//                .replace("#类名#", 类名)
                .replace("#className#", className)
                .replace("#entityName#", entityName)
//                .replace("#jpaEntityFields#", jpaEntityFields)
//                .replace("#fromMapRows#", fromMapRows)
//                .replace("#tableName#", tableName)
                .replace("#date#", now)
//                .replace("#reactTableCols#", reactTableCols)
                .replace("#reactRuoyiCols#", reactRuoyiCols)

        ;
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "react", "ruoyi", entityName, "index.tsx");
        FileUtil.writeCode(dictDataPath, code);


        return code;
    }


    public String genReactForm(String 包名) throws Exception {

        String type = "Form";
        String code = FileUtil.readResourceFileData("genCodeTemplate/react/antDesign/Form.jsx");
//        String code = FileUtil.readResourceFileData("genCodeTemplate/react/antDesign/Form.jsx");
//        String codeIndexJs = FileUtil.readResourceFileData("genCodeTemplate/react/antDesign/index.js");
//        String jpaEntityFields = genJpaEntityFields();
//        String fromMapRows = genFromMapRows();
        String reactFormCols = genReactFormCols();
        String now = DateUtilSt.getNow();
        code = code
//                .replace("#formInputs#", iViewFormInputs)
//                .replace("#类名#", 类名)
//                .replace("#实体名#", 实体名)
//                .replace("#实体名#", entityName)
//                .replace("#包名#", 包名)
//                .replace("#类名#", 类名)
//                .replace("#jpaEntityFields#", jpaEntityFields)
//                .replace("#fromMapRows#", fromMapRows)
//                .replace("#tableName#", tableName)
                .replace("#date#", now)
                .replace("#reactFormCols#", reactFormCols)

        ;


        String formName = className + type;
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
//                "react","antDesign","Form", 类名+"Form",类名+"Form.jsx");
                "react", "antDesign", "Form", className + "Form", className + "Form.jsx");
        FileUtil.writeCode(dictDataPath, code);
//        String table = genIndexJs("Table", 类名 + "Table");
        genIndexJs(type, formName);

        return code;
    }

    public String genTsDataD(String packageName) throws Exception {
        String code = FileUtil.readResourceFileData("genCodeTemplate/ruoyi/react/data.d.ts");
//        String jpaEntityFields = genJpaEntityFields();
//        String fromMapRows = genFromMapRows();
        String tsDataDFields = genTsDataDFields();
        String tsDataDListParamsFields = genTsDataDListParamsFields();

        String now = DateUtilSt.getNow();
//        entityName
        code = code
//                .replace("#formInputs#", iViewFormInputs)
//                .replace("#类名#", 类名)
//                .replace("#实体名#", 实体名)
                .replace("#entityName#", entityName)
                .replace("#packageName#", packageName)
                .replace("#tsDataDFields#", tsDataDFields)
                .replace("#tsDataDListParamsFields#", tsDataDListParamsFields)
//                .replace("#类名#", 类名)
                .replace("#className#", className)
//                .replace("#jpaEntityFields#", jpaEntityFields)
//                .replace("#fromMapRows#", fromMapRows)
                .replace("#tableName#", tableName)
                .replace("#date#", now)

        ;
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "react", "ruoyi", entityName, "data" + ".d.ts");
        FileUtil.writeCode(dictDataPath, code);

        return code;
    }

    public String genRuoYiJpaController(String packageName) throws Exception {
        String code = FileUtil.readResourceFileData("genCodeTemplate/ruoyi/jpa/Controller.java");
//        String jpaEntityFields = genJpaEntityFields(false);
//        String fromMapRows = genFromMapRows();

        String now = DateUtilSt.getNow();
//        entityName
        code = code
//                .replace("#formInputs#", iViewFormInputs)
//                .replace("#类名#", 类名)
//                .replace("#实体名#", 实体名)
                .replace("#entityName#", entityName)
                .replace("#className#", className)
//                .replace("#包名#", packageName)
                .replace("#packageName#", packageName)
//                .replace("#类名#", 类名)
//                .replace("#jpaEntityFields#", jpaEntityFields)
//                .replace("#fromMapRows#", fromMapRows)
                .replace("#tableName#", tableName)
                .replace("#date#", now)

        ;
//        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
//                "jpa","entity", 类名+".java");
        // TODOok: 2022/8/18 ruoyi
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "react", "ruoyiJPA", "controller", className + "Controller.java");
        FileUtil.writeCode(dictDataPath, code);

        return code;
    }

    public String genXueZhiSiController(String packageName)
            throws Exception {
//        genCodeTemplate/xueZhiSi/MybatisController.java
        String code = FileUtil.readResourceFileData(
                "genCodeTemplate/xueZhiSi/MybatisController.java");
        String andEqualToList = genAndEqualToList();
        String now = DateUtilSt.getNow();
        code = code
//                .replace("#formInputs#", iViewFormInputs)
                .replace("#类名#", 类名)
                .replace("#实体名#", 实体名)
                .replace("#entityName#", entityName)
                .replace("#className#", className)
                .replace("#包名#", packageName)
                .replace("#packageName#", packageName)
//                .replace("#类名#", 类名)
//                .replace("#jpaEntityFields#", jpaEntityFields)
//                .replace("#fromMapRows#", fromMapRows)
                .replace("#tableName#", tableName)
                .replace("#date#", now)
                .replace("#returnType#", CodeGen.returnType)
                .replace("#andEqualToList#", andEqualToList)
//        andDeletedEqualTo

        ;

        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "xueZhiSi", "controller", className + "Controller.java");
        FileUtil.writeCode(dictDataPath, code);

        return code;
    }
    public String genMybatisXmlSqls() throws Exception{
//        String code = "insert into `#tableName#` (#InsertIntoFields#) values (#mockDataStr#);";
        String code = "insert into `#tableName#` (#InsertIntoFields#) values (#insertDataStr#);";

        final String InsertIntoFields = genInsertIntoFields();
//        final String mockDataStr = genMockDataStr();
        final String insertDataStr = genInsertDataStr();
//    mockData
//    final List<String> columnNames = columnInfos.stream().map(columnInfo -> {
//        String columnName = columnInfo.getCOLUMN_NAME();
//        final String mockData = columnInfo.mockData();
//        return "`" + columnName+ "`";
////            return columnName;
//    }).collect(Collectors.toList());
        String now = DateUtilSt.getNow();
//        entityName
        code = code
//                .replace("#formInputs#", iViewFormInputs)
//                .replace("#类名#", 类名)
                .replace("#实体名#", 实体名)
//                .replace("#包名#", packageName)
//                .replace("#packageName#", packageName)
                .replace("#类名#", 类名)
//                .replace("#jpaEntityFields#", jpaEntityFields)
//                .replace("#fromMapRows#", fromMapRows)
                .replace("#tableName#", tableName)
                .replace("#date#", now)
                .replace("#InsertIntoFields#", InsertIntoFields)
//                .replace("#mockDataStr#", mockDataStr)
                .replace("#insertDataStr#", insertDataStr)

        ;
//        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
//                "jpa","entity", 类名+".java");
        // TODOok: 2022/8/18 ruoyi
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "sqlXml",className+".sql");
        FileUtil.writeCode(dictDataPath,code);

        return code;
    }
    public String genOneInsertInto() {
        String code = "insert into `#tableName#` (#InsertIntoFields#) values (#mockDataStr#);";

        final String InsertIntoFields = genInsertIntoFields();
        final String mockDataStr = genMockDataStr();
//    mockData
//    final List<String> columnNames = columnInfos.stream().map(columnInfo -> {
//        String columnName = columnInfo.getCOLUMN_NAME();
//        final String mockData = columnInfo.mockData();
//        return "`" + columnName+ "`";
////            return columnName;
//    }).collect(Collectors.toList());
        String now = DateUtilSt.getNow();
//        entityName
        code = code
//                .replace("#formInputs#", iViewFormInputs)
//                .replace("#类名#", 类名)
                .replace("#实体名#", 实体名)
//                .replace("#包名#", packageName)
//                .replace("#packageName#", packageName)
                .replace("#类名#", 类名)
//                .replace("#jpaEntityFields#", jpaEntityFields)
//                .replace("#fromMapRows#", fromMapRows)
                .replace("#tableName#", tableName)
                .replace("#date#", now)
                .replace("#InsertIntoFields#", InsertIntoFields)
                .replace("#mockDataStr#", mockDataStr)

        ;
//        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
//                "jpa","entity", 类名+".java");
        // TODOok: 2022/8/18 ruoyi
//        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
//                "sql",className+".sql");
//        FileUtil.writeCode(dictDataPath,code);

        return code;
    }

    //    insert into `tbl_admins` () values ();
    public String genInsertInto(String packageName) throws Exception {
//    String code = "insert into `#tableName#` (#InsertIntoFields#) values (#mockDataStr#);";
//    String jpaEntityFields = genJpaEntityFields(false);
//    String fromMapRows = genFromMapRows();
//    final String InsertIntoFields = genInsertIntoFields();
//    final String mockDataStr = genMockDataStr();
        int genInsertIntoCnt = 10;
        StringBuilder codeOut = new StringBuilder();
        for (int i = 0; i < genInsertIntoCnt; i++) {
            final String insertInto = genOneInsertInto();
            codeOut.append(insertInto).append("\n");
        }

//    mockData
//    final List<String> columnNames = columnInfos.stream().map(columnInfo -> {
//        String columnName = columnInfo.getCOLUMN_NAME();
//        final String mockData = columnInfo.mockData();
//        return "`" + columnName+ "`";
////            return columnName;
//    }).collect(Collectors.toList());
//    String now = DateUtilSt.getNow();
////        entityName
//    code = code
////                .replace("#formInputs#", iViewFormInputs)
////                .replace("#类名#", 类名)
//            .replace("#实体名#", 实体名)
////                .replace("#包名#", packageName)
//            .replace("#packageName#", packageName)
//            .replace("#类名#", 类名)
//            .replace("#jpaEntityFields#", jpaEntityFields)
//            .replace("#fromMapRows#", fromMapRows)
//            .replace("#tableName#", tableName)
//            .replace("#date#", now)
//            .replace("#InsertIntoFields#", InsertIntoFields)
//            .replace("#mockDataStr#", mockDataStr)

//    ;
//        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
//                "jpa","entity", 类名+".java");
        // TODOok: 2022/8/18 ruoyi
        final String stringCodeOut = codeOut.toString();
//    java.nio.file.Path dictDataPath = Paths.get(pathFileString,
//            "sql",tableName+".sql");
//    FileUtil.writeCode(dictDataPath,codeOut.toString());

        return stringCodeOut;
    }

    public String genRuoYiJpaEntity(String packageName) throws Exception {
        String code = FileUtil.readResourceFileData(
                "genCodeTemplate/ruoyi/jpa/JpaEntity.java");
        String jpaEntityFields = genJpaEntityFields(false);
        String fromMapRows = genFromMapRows();

        String now = DateUtilSt.getNow();
//        entityName
        code = code
//                .replace("#formInputs#", iViewFormInputs)
//                .replace("#类名#", 类名)
                .replace("#实体名#", 实体名)
//                .replace("#包名#", packageName)
                .replace("#packageName#", packageName)
                .replace("#类名#", 类名)
                .replace("#jpaEntityFields#", jpaEntityFields)
                .replace("#fromMapRows#", fromMapRows)
                .replace("#tableName#", tableName)
                .replace("#date#", now)

        ;
//        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
//                "jpa","entity", 类名+".java");
        // TODOok: 2022/8/18 ruoyi
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "react", "ruoyiJPA", "entity", className + ".java");
        FileUtil.writeCode(dictDataPath, code);

        return code;
    }

    public String genEntity(String packageName) throws Exception {
        String code = FileUtil.readResourceFileData(
                "genCodeTemplate/jpa/JpaEntity.java");
        String jpaEntityFields = genJpaEntityFields();
        String fromMapRows = genFromMapRows();
        final String namesRows = genNamesRows();
        String now = DateUtilSt.getNow();
//        entityName
//        tablePreffix
        final String toEsEntityRows = genToEsEntityRows();
        code = code
//                .replace("#formInputs#", iViewFormInputs)
//                .replace("#类名#", 类名)
                .replace("#实体名#", 实体名)
                .replace("#包名#", packageName)
                .replace("#类名#", 类名)
                .replace("#jpaEntityFields#", jpaEntityFields)
                .replace("#fromMapRows#", fromMapRows)
                .replace("#tableName#", tableName)
                .replace("#date#", now)
//                .replace("#date#", now)
                .replace("#tablePreffix#", tablePreffix)
                .replace("{NamesRows}", namesRows)
                .replace("{toEsEntityRows}", toEsEntityRows)


        ;
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "jpa", "entity", 类名 + ".java");
        FileUtil.writeCode(dictDataPath, code);

        return code;
    }

    public String genElmPlusTable(String packageName) throws Exception {
        String code = FileUtil.readResourceFileData(
                "genCodeTemplate/elmPlus/ElmPlusTable.vue");
        String jpaEntityFields = genJpaEntityFields();
        String fromMapRows = genFromMapRows();
        final String namesRows = genNamesRows();
        String now = DateUtilSt.getNow();
//        entityName
//        tablePreffix
        String ElFormItemRows = genElFormItemRows();
        String ElSearchItemRows = genElSearchItemRows();
//        ElSearchItemRows
        String elTableColumnRows = genElTableColumnRows();
        final String toEsEntityRows = genToEsEntityRows();
        code = code
//                .replace("#formInputs#", iViewFormInputs)
//                .replace("#类名#", 类名)
                .replace("#实体名#", 实体名)
                .replace("#包名#", packageName)
                .replace("#类名#", 类名)
                .replace("#jpaEntityFields#", jpaEntityFields)
                .replace("#fromMapRows#", fromMapRows)
                .replace("#tableName#", tableName)
                .replace("#date#", now)
//                .replace("#date#", now)
                .replace("#tablePreffix#", tablePreffix)
                .replace("{NamesRows}", namesRows)
                .replace("{toEsEntityRows}", toEsEntityRows)
                .replace("{ElFormItemRows}", ElFormItemRows)
                .replace("{ElSearchItemRows}", ElSearchItemRows)
                .replace("{elTableColumnRows}", elTableColumnRows)

        ;
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "elementPlus", "table", 类名 + "Table.vue");
        FileUtil.writeCode(dictDataPath, code);

        return code;
    }

    public String genMybatisPlusController(String 包名) throws Exception {
        String code = FileUtil.readResourceFileData("genCodeTemplate/mybatisPlus/MybatisPlusController.java");
        String jpaEntityFields = genJpaEntityFields();
        String fromMapRows = genFromMapRows();
        String jsonDefaultNull = genJsonDefaultNull();
        String MybatisPlusSelectPageEqualRows = genMybatisPlusSelectPageEqualRows();
        String now = DateUtilSt.getNow();
//        String MybatisPlusSelectPage = genMybatisPlusSelectPage();
        String MybatisPlusSelectPageLikeRows = genMybatisPlusSelectPageLikeRows();
        code = code
//                .replace("#formInputs#", iViewFormInputs)
//                .replace("#类名#", 类名)
                .replace("#实体名#", 实体名)
                .replace("#包名#", 包名)
                .replace("#类名#", 类名)
                .replace("#jpaEntityFields#", jpaEntityFields)
                .replace("#fromMapRows#", fromMapRows)
                .replace("#tableName#", tableName)
                .replace("#date#", now)
                .replace("#MybatisPlusSelectPageEqualRows#", MybatisPlusSelectPageEqualRows)
                .replace("#jsonDefaultNull#", jsonDefaultNull)
//                .replace("#MybatisPlusSelectPage#", MybatisPlusSelectPage)
                .replace("#MybatisPlusSelectPageLikeRows#", MybatisPlusSelectPageLikeRows)
                .replace("#returnType#", CodeGen.returnType)

        ;
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "MybatisPlus", "controller", 类名 + "ControllerMbp.java");
        FileUtil.writeCode(dictDataPath, code);

        return code;
    }

    public String genJpaControllerIView(String 包名) throws Exception {
//        D:\proj\springBoot\codeGen\src\main\resources\genCodeTemplate\jpa\JpaControllerIView.java
        String code = FileUtil.readResourceFileData("genCodeTemplate/jpa/JpaControllerIView.java");
        String jpaEntityFields = genJpaEntityFields();
        String fromMapRows = genFromMapRows();
        String jsonDefaultNull = genJsonDefaultNull();
        String MybatisPlusSelectPageEqualRows = genMybatisPlusSelectPageEqualRows();
        String now = DateUtilSt.getNow();
        String MybatisPlusSelectPage = genMybatisPlusSelectPage();
        String MybatisPlusSelectPageLikeRows = genMybatisPlusSelectPageLikeRows();

        String jpaLikeRows = jpaLikeRows();
        code = code
//                .replace("#formInputs#", iViewFormInputs)
//                .replace("#类名#", 类名)
                .replace("#jpaLikeRows#", jpaLikeRows)
                .replace("#实体名#", 实体名)
                .replace("#包名#", 包名)
                .replace("#类名#", 类名)
                .replace("#jpaEntityFields#", jpaEntityFields)
                .replace("#fromMapRows#", fromMapRows)
                .replace("#tableName#", tableName)
                .replace("#date#", now)
                .replace("#MybatisPlusSelectPageEqualRows#", MybatisPlusSelectPageEqualRows)
                .replace("#jsonDefaultNull#", jsonDefaultNull)
//                .replace("#MybatisPlusSelectPage#", MybatisPlusSelectPage)
                .replace("#MybatisPlusSelectPageLikeRows#", MybatisPlusSelectPageLikeRows)
                .replace("#returnType#", CodeGen.returnType)

        ;
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "jpa", "controller", "iView", 类名 + "ControllerIView.java");
        FileUtil.writeCode(dictDataPath, code);

        return code;
    }


    public String genElementUiForm() throws Exception {

        String code = FileUtil.readResourceFileData("genCodeTemplate/elementUi/ElemForm.vue");
//        String iViewFormInputs = genIViewFormInputs();
        String jsonDefaultNull = genJsonDefaultNull();
//        String iViewColumnsRows = genIViewColumnsRows();
        String elmCols = genElmCols();
        String elmFormItems = genElmFormItems();
        String elmQueryInputs = genElmQueryInputs();
        String elmQueryInputsSelectedRow = genElmQueryInputs("selectedRow");
        code = code
//                .replace("#formInputs#", iViewFormInputs)
//                .replace("#类名#", 类名)
                .replace("#实体名#", 实体名)
                .replace("#类名#", 类名)
                .replace("#elmFormItems#", elmFormItems)
                .replace("#elmCols#", elmCols)
                .replace("#jsonDefaultNull#", jsonDefaultNull)
                .replace("#elmQueryInputs#", elmQueryInputs)
                .replace("#elmFormItemsSelectedRow#", elmQueryInputsSelectedRow)
//                .replace("#jsonDefaultNull#", jsonDefaultNull)
//                .replace("#iViewColumnsRows#", iViewColumnsRows)
        ;
        java.nio.file.Path dictDataPath = Paths.get(pathFileString,
                "elementUi", "form", 类名 + "Form.vue");
        FileUtil.writeCode(dictDataPath, code);

        return code;
    }

//    ElementTable.vue

    boolean doGenMybatisPlus = false;


    public String genController(String 包名) throws Exception {
        String code = FileUtil.readResourceFileData("genCodeTemplate/jpa/JpaController.java");

//        String code = "package #包名#.controller;\n" +
//                "\n" +
//                "import #包名#.entity.#类名#;\n" +
//                "import #包名#.entity.Rotation;\n" +
//                "import #包名#.repository.#类名#Repository;\n" +
//                "import com.gm.wj.util.AddOrderSocket;\n" +
//                "import com.gm.wj.util.JsonUtil;\n" +
//                "import com.gm.wj.util.ReturnT;\n" +
//                "import com.gm.wj.util.StrUtil;\n" +
//                "import com.gm.wj.util.page.StarpPage;\n" +
//                "import io.swagger.annotations.Api;\n" +
//                "import io.swagger.annotations.ApiOperation;\n" +
//                "import lombok.extern.slf4j.Slf4j;\n" +
//                "import org.springframework.data.domain.*;\n" +
//                "import org.springframework.data.jpa.domain.Specification;\n" +
//                "import org.springframework.web.bind.annotation.*;\n" +
//                "import org.springframework.beans.factory.annotation.Autowired;\n" +
//                "\n" +
//                "import java.util.*;\n" +
//                "\n" +
//                "import javax.persistence.criteria.Predicate;\n" +
//                "\n" +
//                "/**\n" +
//                " * @author mqp\n" +
//                " * @description #类名#\n" +
//                " * @date 2022-06-27\n" +
//                " */\n" +
//                "\n" +
//                "@Slf4j\n" +
//                "@Api(tags = \"#实体名#\")\n" +
//                "//@CrossOrigin\n" +
//                "@CrossOrigin(allowCredentials = \"true\")\n" +
//                "@RestController\n" +
//                "//@RequestMapping(\"/#实体名#\")\n" +
//                "@RequestMapping(\"/api/#实体名#\")\n" +
//                "public class #类名#Controller {\n" +
//                "\n" +
//                "    @Autowired\n" +
//                "    private #类名#Repository #实体名#Repository;\n" +
//                "\n" +
//                "    /**\n" +
//                "     * 新增或编辑\n" +
//                "   let  data= #jsonDefaultNull# \n" +
//                "   axios.post(Common.baseUrl + \"/#实体名#/save\",data).then((res) => {\n" +
//                "    console.log(\"res\");\n" +
//                "    console.log(res);\n" +
//                "   "+
//                "  });\n" +
//                "     */\n" +
//                "    @PostMapping(\"/save\")\n" +
//                "    @ApiOperation(value = \"save #实体名#\", notes = \"save #实体名#\")\n" +
//                "    public Object save(@RequestBody #类名# #实体名#) {\n" +
//                "        try {\n" +
//                "            return ReturnT.success(#实体名#Repository.save(#实体名#));\n" +
//                "        } catch (Exception e) {\n" +
//                "            e.printStackTrace();\n" +
//                "            return ReturnT.error(\"保存失败\");\n" +
//                "        }\n" +
//                "\n" +
//                "    }\n" +
//
//                "    /**\n" +
//                "     * 删除\n" +
//
//                "   let  data= {} \n" +
//                "   axios.post(Common.baseUrl + \"/#实体名#/delete?id=\"+id,data).then((res) => {\n" +
//                "    console.log(\"res\");\n" +
//                "    console.log(res);\n" +
//                "   "+
//                "  });\n" +
//                "     */\n" +
//                "    @PostMapping(\"/delete\")\n" +
//                "    @ApiOperation(value = \"delete #实体名#\", notes = \"delete #实体名#\")\n" +
//                "    public Object delete(int id) {\n" +
//                "        Optional<#类名#> #实体名# = #实体名#Repository.findById(id);\n" +
//                "        if (#实体名#.isPresent()) {\n" +
//                "            #实体名#Repository.deleteById(id);\n" +
//                "            return ReturnT.success(\"删除成功\");\n" +
//                "        } else {\n" +
//                "            return ReturnT.error(\"没有找到该对象\");\n" +
//                "        }\n" +
//                "    }\n" +
//                "\n" +
//                "    /**\n" +
//                "     * 查询\n" +
//                "     */\n" +
//                "    @PostMapping(\"/find\")\n" +
//                "    @ApiOperation(value = \"find #实体名# by id\", notes = \"find #实体名# by id\")\n" +
//                "    public Object find(int id) {\n" +
//                "        Optional<#类名#> #实体名# = #实体名#Repository.findById(id);\n" +
//                "        if (#实体名#.isPresent()) {\n" +
//                "            return ReturnT.success(#实体名#.get());\n" +
//                "        } else {\n" +
//                "            return ReturnT.error(\"没有找到该对象\");\n" +
//                "        }\n" +
//                "    }\n" +
//                "\n" +
//                "    /**\n" +
//                "     * 分页查询\n" +
//
//                "   let  data= #jsonDefaultNull# \n" +
//                "   axios.post(Common.baseUrl + \"/#实体名#/list\",data).then((res) => {\n" +
//                "    console.log(\"res\");\n" +
//                "    console.log(res);\n" +
//                "   let   #实体名#List=  res.data.data.content\n" +
//                "   "+
//                "  });\n" +
//                "     */\n" +
//                "    @PostMapping(\"/list\")\n" +
//                "    @ApiOperation(value = \"list #实体名#\", notes = \"list #实体名#\")\n" +
//                "    public Object list(@RequestBody #类名# #实体名#,\n" +
//                "                       @RequestParam(required = false, defaultValue = \"0\") int pageNumber,\n" +
//                "                       @RequestParam(required = false, defaultValue = \"10\") int pageSize) {\n" +
//                "\n" +
//                "        try {\n" +
//                "            //创建匹配器，需要查询条件请修改此处代码\n" +
//                "            ExampleMatcher matcher = ExampleMatcher.matchingAll();\n" +
//                "\n" +
//                "            //创建实例\n" +
//                "            Example<#类名#> example = Example.of(#实体名#, matcher);\n" +
//                "            //分页构造\n" +
//                "            Pageable pageable = PageRequest.of(pageNumber, pageSize);\n" +
//                "\n" +
//                "            return ReturnT.success(#实体名#Repository.findAll(example, pageable));\n" +
//                "\n" +
//                "        } catch (Exception e) {\n" +
//                "            e.printStackTrace();\n" +
//                "            return ReturnT.error(e.getMessage());\n" +
//                "        }\n" +
//                "\n" +
//                "    }\n" +
//                "\n" +
//                "\n" +
//
//                "    /**\n" +
//                "     * \n" +
//
//                "   let  data= [] \n" +
//                "   axios.post(Common.baseUrl + \"/#实体名#/findAllById\",data).then((res) => {\n" +
//                "    console.log(\"res\");\n" +
//                "    console.log(res);\n" +
//                "   let   #实体名#List=  res.data.data.content\n" +
//                "   "+
//                "  });\n" +
//                "     */\n" +
//
//                "    @PostMapping(\"/findAllById\")\n" +
//                "    @ApiOperation(value = \"findAllById\", notes = \"findAllById\")\n" +
//                "    public Object findAllById(@RequestBody List<Integer> ids,\n" +
//                "                              @RequestParam(required = false, defaultValue = \"0\") int pageNumber,\n" +
//                "                              @RequestParam(required = false, defaultValue = \"10\") int pageSize) {\n" +
//                "        try {\n" +
//                "            List<#类名#> allById =#实体名#Repository.findAllById(ids);\n" +
//                "            StarpPage<#类名#> starpPage = new StarpPage<>(allById, pageNumber, pageSize);\n" +
//                "            return ReturnT.success(starpPage);\n" +
//                "        } catch (Exception e) {\n" +
//                "            e.printStackTrace();\n" +
//                "            return ReturnT.error(\"保存失败\");\n" +
//                "        }\n" +
//                "    }\n" +
//                "\n" +
//                "\n" +
//                "    @PostMapping(\"/deleteInBatch\")\n" +
//                "    @ApiOperation(value = \"deleteInBatch\", notes = \"deleteInBatch\")\n" +
//                "    public Object deleteInBatch(@RequestBody List<#类名#> entities) {\n" +
//                "        try {\n" +
//                "            #实体名#Repository.deleteInBatch(entities);\n" +
//                "            return ReturnT.success(\"批量删除成功\");\n" +
//                "        } catch (Exception e) {\n" +
//                "            e.printStackTrace();\n" +
//                "            return ReturnT.error(\"批量删除失败\");\n" +
//                "        }\n" +
//                "    }\n" +
//                "\n" +
//
//                "    /**\n" +
//                "     * listLike\n" +
//
//                "" +
//                "   let #实体名#List: any = ref([]);\n" +
//                "   let  data= #jsonDefaultNull# \n" +
//                "           axios.post(Common.baseUrl + \"/#实体名#/listLike\",data).then((res) => {\n" +
//                "       console.log(\"res\");\n" +
//                "       console.log(res);\n" +
//                "    #实体名#List.value = res.data.data.content" +
//                "   "+
//                "  });\n" +
//                "     */\n" +
//
//                "    @PostMapping(\"/listLike\")\n" +
//                "    @ApiOperation(value = \"listLike\", notes = \"listLike\")\n" +
//                "    public Object listLike(@RequestBody #类名# #实体名#,\n" +
//                "                           @RequestParam(required = false, defaultValue = \"0\") int pageNumber,\n" +
//                "                           @RequestParam(required = false, defaultValue = \"10\") int pageSize) {\n" +
//                "\n" +
//                "        try {\n" +
//                "\n" +
//                "            Pageable pageable = PageRequest.of(pageNumber, pageSize);\n" +
//                "            Page<#类名#> page = #实体名#Repository.findAll((Specification<#类名#>)\n" +
//                "                    (root, criteriaQuery, criteriaBuilder) -> {\n" +
//                "                        List<Predicate> list = new ArrayList<Predicate>();\n" +
//                "  #jpaLikeRows# " +
//                "\n" +
//                "                        Predicate[] p = new Predicate[list.size()];\n" +
//                "                        return criteriaBuilder.and(list.toArray(p));\n" +
//                "                    }, pageable);\n" +
//                "            return ReturnT.success(page);\n" +
//                "\n" +
//                "        } catch (Exception e) {\n" +
//                "            e.printStackTrace();\n" +
//                "            return ReturnT.error(e.getMessage());\n" +
//                "        }\n" +
//                "\n" +
//                "    }\n" +
//                "\n" +
//                "@Resource\n" +
//                "    #类名#Service #实体名#Service;\n" +
//                "\n" +
//                "" +
//                "    /**\n" +
//                "     * create\n" +
//
//                "       let  data= #jsonDefaultNull# \n" +
//                "        axios.post(Common.baseUrl + \"/#实体名#/create\",data).then((res) => {\n" +
//                "           console.log(\"res\");\n" +
//                "           console.log(res);\n" +
//                "   "+
//                "  });\n" +
//                "     */\n" +
//
//                "@ApiOperation(value = \"create\", notes = \"create\")\n" +
//                "    @RequestMapping(value = \"\", method = RequestMethod.POST)\n" +
//                "    public Object create(@RequestBody #类名# #实体名#) {\n" +
//                "        boolean save = #实体名#Service.save(#实体名#);\n" +
//                "        return ReturnT.success(save);\n" +
//                "    }" +
//                "\n" +
//                (doGenMybatisPlus?genMybatisPlusSelectPage():"")+
//                "}\n";
//        System.out.println(code);
        String jsonDefaultNull = genJsonDefaultNull();
        String jpaLikeRows = jpaLikeRows();
//        if(doGenMybatisPlus){
//
//        }
        String genMybatisPlusSelectPage = genMybatisPlusSelectPage();

        code = code
                .replace("#jpaLikeRows#", jpaLikeRows)
                .replace("#实体名#", 实体名)
                .replace("#类名#", 类名)
                .replace("#包名#", 包名)
                .replace("#jsonDefaultNull#", jsonDefaultNull)
                .replace("#MybatisPlusSelectPage#", genMybatisPlusSelectPage)
        ;
        return code;

    }

    public String genFindFirstRows() {

        StringBuilder res = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfos) {
//            String column_name = columnInfo.getCOLUMN_NAME();
            String java字段名 = columnInfo.getJava字段名();
//           ColumnInfo findFirstByCHARACTER_SET_NAME(String CHARACTER_SET_NAME);
//            column_name.ge
//            String 字段名开头大写 = StrUtil.captureName(column_name);
            String 字段名开头大写 = StringUtils.captureName(java字段名);
//            res+=类名+
//            String column_type = columnInfo.getCOLUMN_TYPE();
//            column_type
//            String data_type = columnInfo.getDATA_TYPE();
////            "varchar";
//            String java字段类型 = ColumnInfo.DATA_TYPE_Map.get(data_type);
//            if()
            String java字段类型 = columnInfo.获取java字段类型();
            res.append("#类名# findFirstBy#字段名开头大写#(#字段类型名字# #字段名#);\n"
                            .replace("#类名#", 类名)
                            .replace("#字段名开头大写#", 字段名开头大写)
//                    .replace("#字段名#", column_name))
                            .replace("#字段名#", java字段名)
                            .replace("#字段类型名字#", java字段类型)
            )

            ;
        }
        return res.toString();
    }

    public static void main(String[] args) throws Exception {
        TableInfo tableInfo = new TableInfo();
//        tableInfo.se
//        String genFindFirstRows = tableInfo.genFindFirstRows();
//        System.out.println(genFindFirstRows);

//        String s = tableInfo.genIViewEdit();
//        System.out.println(s);


        String code = "package #包名#.controller;\n" +
                "\n" +
                "import #包名#.entity.#类名#;\n" +
                "import #包名#.entity.Rotation;\n" +
                "import #包名#.repository.#类名#Repository;\n" +
                "import com.gm.wj.util.AddOrderSocket;\n" +
                "import com.gm.wj.util.JsonUtil;\n" +
                "import com.gm.wj.util.ReturnT;\n" +
                "import com.gm.wj.util.StrUtil;\n" +
                "import com.gm.wj.util.page.StarpPage;\n" +
                "import io.swagger.annotations.Api;\n" +
                "import io.swagger.annotations.ApiOperation;\n" +
                "import lombok.extern.slf4j.Slf4j;\n" +
                "import org.springframework.data.domain.*;\n" +
                "import org.springframework.data.jpa.domain.Specification;\n" +
                "import org.springframework.web.bind.annotation.*;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "\n" +
                "import java.util.*;\n" +
                "\n" +
                "import javax.persistence.criteria.Predicate;\n" +
                "\n" +
                "/**\n" +
                " * @author mqp\n" +
                " * @description #类名#\n" +
                " * @date 2022-06-27\n" +
                " */\n" +
                "\n" +
                "@Slf4j\n" +
                "@Api(tags = \"#实体名#\")\n" +
                "//@CrossOrigin\n" +
                "@CrossOrigin(allowCredentials = \"true\")\n" +
                "@RestController\n" +
                "//@RequestMapping(\"/#实体名#\")\n" +
                "@RequestMapping(\"/api/#实体名#\")\n" +
                "public class #类名#Controller {\n" +
                "\n" +
                "    @Autowired\n" +
                "    private #类名#Repository #实体名#Repository;\n" +
                "\n" +
                "    /**\n" +
                "     * 新增或编辑\n" +
                "   let  data= #jsonDefaultNull# \n" +
                "   axios.post(Common.baseUrl + \"/#实体名#/save\",data).then((res) => {\n" +
                "    console.log(\"res\");\n" +
                "    console.log(res);\n" +
                "   " +
                "  });\n" +
                "     */\n" +
                "    @PostMapping(\"/save\")\n" +
                "    @ApiOperation(value = \"save #实体名#\", notes = \"save #实体名#\")\n" +
                "    public Object save(@RequestBody #类名# #实体名#) {\n" +
                "        try {\n" +
                "            return ReturnT.success(#实体名#Repository.save(#实体名#));\n" +
                "        } catch (Exception e) {\n" +
                "            e.printStackTrace();\n" +
                "            return ReturnT.error(\"保存失败\");\n" +
                "        }\n" +
                "\n" +
                "    }\n" +

                "    /**\n" +
                "     * 删除\n" +

                "   let  data= {} \n" +
                "   axios.post(Common.baseUrl + \"/#实体名#/delete?id=\"+id,data).then((res) => {\n" +
                "    console.log(\"res\");\n" +
                "    console.log(res);\n" +
                "   " +
                "  });\n" +
                "     */\n" +
                "    @PostMapping(\"/delete\")\n" +
                "    @ApiOperation(value = \"delete #实体名#\", notes = \"delete #实体名#\")\n" +
                "    public Object delete(int id) {\n" +
                "        Optional<#类名#> #实体名# = #实体名#Repository.findById(id);\n" +
                "        if (#实体名#.isPresent()) {\n" +
                "            #实体名#Repository.deleteById(id);\n" +
                "            return ReturnT.success(\"删除成功\");\n" +
                "        } else {\n" +
                "            return ReturnT.error(\"没有找到该对象\");\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 查询\n" +
                "     */\n" +
                "    @PostMapping(\"/find\")\n" +
                "    @ApiOperation(value = \"find #实体名# by id\", notes = \"find #实体名# by id\")\n" +
                "    public Object find(int id) {\n" +
                "        Optional<#类名#> #实体名# = #实体名#Repository.findById(id);\n" +
                "        if (#实体名#.isPresent()) {\n" +
                "            return ReturnT.success(#实体名#.get());\n" +
                "        } else {\n" +
                "            return ReturnT.error(\"没有找到该对象\");\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 分页查询\n" +

                "   let  data= #jsonDefaultNull# \n" +
                "   axios.post(Common.baseUrl + \"/#实体名#/list\",data).then((res) => {\n" +
                "    console.log(\"res\");\n" +
                "    console.log(res);\n" +
                "   let   #实体名#List=  res.data.data.content\n" +
                "   " +
                "  });\n" +
                "     */\n" +
                "    @PostMapping(\"/list\")\n" +
                "    @ApiOperation(value = \"list #实体名#\", notes = \"list #实体名#\")\n" +
                "    public Object list(@RequestBody #类名# #实体名#,\n" +
                "                       @RequestParam(required = false, defaultValue = \"0\") int pageNumber,\n" +
                "                       @RequestParam(required = false, defaultValue = \"10\") int pageSize) {\n" +
                "\n" +
                "        try {\n" +
                "            //创建匹配器，需要查询条件请修改此处代码\n" +
                "            ExampleMatcher matcher = ExampleMatcher.matchingAll();\n" +
                "\n" +
                "            //创建实例\n" +
                "            Example<#类名#> example = Example.of(#实体名#, matcher);\n" +
                "            //分页构造\n" +
                "            Pageable pageable = PageRequest.of(pageNumber, pageSize);\n" +
                "\n" +
                "            return ReturnT.success(#实体名#Repository.findAll(example, pageable));\n" +
                "\n" +
                "        } catch (Exception e) {\n" +
                "            e.printStackTrace();\n" +
                "            return ReturnT.error(e.getMessage());\n" +
                "        }\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "\n" +

                "    /**\n" +
                "     * \n" +

                "   let  data= [] \n" +
                "   axios.post(Common.baseUrl + \"/#实体名#/findAllById\",data).then((res) => {\n" +
                "    console.log(\"res\");\n" +
                "    console.log(res);\n" +
                "   let   #实体名#List=  res.data.data.content\n" +
                "   " +
                "  });\n" +
                "     */\n" +

                "    @PostMapping(\"/findAllById\")\n" +
                "    @ApiOperation(value = \"findAllById\", notes = \"findAllById\")\n" +
                "    public Object findAllById(@RequestBody List<Integer> ids,\n" +
                "                              @RequestParam(required = false, defaultValue = \"0\") int pageNumber,\n" +
                "                              @RequestParam(required = false, defaultValue = \"10\") int pageSize) {\n" +
                "        try {\n" +
                "            List<#类名#> allById =#实体名#Repository.findAllById(ids);\n" +
                "            StarpPage<#类名#> starpPage = new StarpPage<>(allById, pageNumber, pageSize);\n" +
                "            return ReturnT.success(starpPage);\n" +
                "        } catch (Exception e) {\n" +
                "            e.printStackTrace();\n" +
                "            return ReturnT.error(\"保存失败\");\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    @PostMapping(\"/deleteInBatch\")\n" +
                "    @ApiOperation(value = \"deleteInBatch\", notes = \"deleteInBatch\")\n" +
                "    public Object deleteInBatch(@RequestBody List<#类名#> entities) {\n" +
                "        try {\n" +
                "            #实体名#Repository.deleteInBatch(entities);\n" +
                "            return ReturnT.success(\"批量删除成功\");\n" +
                "        } catch (Exception e) {\n" +
                "            e.printStackTrace();\n" +
                "            return ReturnT.error(\"批量删除失败\");\n" +
                "        }\n" +
                "    }\n" +
                "\n" +

                "    /**\n" +
                "     * listLike\n" +

                "" +
                "   let #实体名#List: any = ref([]);\n" +
                "   let  data= #jsonDefaultNull# \n" +
                "           axios.post(Common.baseUrl + \"/#实体名#/listLike\",data).then((res) => {\n" +
                "       console.log(\"res\");\n" +
                "       console.log(res);\n" +
                "    #实体名#List.value = res.data.data.content" +
                "   " +
                "  });\n" +
                "     */\n" +

                "    @PostMapping(\"/listLike\")\n" +
                "    @ApiOperation(value = \"listLike\", notes = \"listLike\")\n" +
                "    public Object listLike(@RequestBody #类名# #实体名#,\n" +
                "                           @RequestParam(required = false, defaultValue = \"0\") int pageNumber,\n" +
                "                           @RequestParam(required = false, defaultValue = \"10\") int pageSize) {\n" +
                "\n" +
                "        try {\n" +
                "\n" +
                "            Pageable pageable = PageRequest.of(pageNumber, pageSize);\n" +
                "            Page<#类名#> page = #实体名#Repository.findAll((Specification<#类名#>)\n" +
                "                    (root, criteriaQuery, criteriaBuilder) -> {\n" +
                "                        List<Predicate> list = new ArrayList<Predicate>();\n" +
                "  #jpaLikeRows# " +
                "\n" +
                "                        Predicate[] p = new Predicate[list.size()];\n" +
                "                        return criteriaBuilder.and(list.toArray(p));\n" +
                "                    }, pageable);\n" +
                "            return ReturnT.success(page);\n" +
                "\n" +
                "        } catch (Exception e) {\n" +
                "            e.printStackTrace();\n" +
                "            return ReturnT.error(e.getMessage());\n" +
                "        }\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "@Resource\n" +
                "    #类名#Service #实体名#Service;\n" +
                "\n" +
                "" +
                "    /**\n" +
                "     * create\n" +

                "       let  data= #jsonDefaultNull# \n" +
                "        axios.post(Common.baseUrl + \"/#实体名#/create\",data).then((res) => {\n" +
                "           console.log(\"res\");\n" +
                "           console.log(res);\n" +
                "   " +
                "  });\n" +
                "     */\n" +

                "@ApiOperation(value = \"create\", notes = \"create\")\n" +
                "    @RequestMapping(value = \"\", method = RequestMethod.POST)\n" +
                "    public Object create(@RequestBody #类名# #实体名#) {\n" +
                "        boolean save = #实体名#Service.save(#实体名#);\n" +
                "        return ReturnT.success(save);\n" +
                "    }" +

                "}\n";
//        System.out.println(code);
        main2ElTableCols(args);
    }
}
