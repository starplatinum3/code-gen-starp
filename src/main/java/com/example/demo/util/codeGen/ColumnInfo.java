package com.example.demo.util.codeGen;

//import com.gm.wj.util.StringUtils;

import com.example.demo.common.JavaDataType;
import com.example.demo.common.MysqlDataType;
import com.example.demo.util.DataTypeMap;
import com.example.demo.util.DateUtilSt;
import com.example.demo.util.NameCreator;
import com.example.demo.util.StringUtils;
import lombok.*;
import top.starp.util.ListUtil;
import top.starp.util.RandomUtil;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Entity
//@Table
//PlanTimeSlotRepository
//ColumnInfoRepository
public class ColumnInfo {
    public String getTABLE_CATALOG() {
        return TABLE_CATALOG;
    }

    public void setTABLE_CATALOG(String TABLE_CATALOG) {
        this.TABLE_CATALOG = TABLE_CATALOG;
    }

    public String getIS_NULLABLE() {
        return IS_NULLABLE;
    }

    public void setIS_NULLABLE(String IS_NULLABLE) {
        this.IS_NULLABLE = IS_NULLABLE;
    }

    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public void setTABLE_NAME(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
    }

    public String getTABLE_SCHEMA() {
        return TABLE_SCHEMA;
    }

    public void setTABLE_SCHEMA(String TABLE_SCHEMA) {
        this.TABLE_SCHEMA = TABLE_SCHEMA;
    }

    public String getEXTRA() {
        return EXTRA;
    }

    public void setEXTRA(String EXTRA) {
        this.EXTRA = EXTRA;
    }

    public String getCOLUMN_NAME() {
        return COLUMN_NAME;
    }

    public void setCOLUMN_NAME(String COLUMN_NAME) {
        this.COLUMN_NAME = COLUMN_NAME;
    }

    public String getCOLUMN_KEY() {
        return COLUMN_KEY;
    }

    public void setCOLUMN_KEY(String COLUMN_KEY) {
        this.COLUMN_KEY = COLUMN_KEY;
    }

    public String getCOLUMN_COMMENT() {
        return COLUMN_COMMENT;
    }

    public void setCOLUMN_COMMENT(String COLUMN_COMMENT) {
        this.COLUMN_COMMENT = COLUMN_COMMENT;
    }

    public String getPRIVILEGES() {
        return PRIVILEGES;
    }

    public void setPRIVILEGES(String PRIVILEGES) {
        this.PRIVILEGES = PRIVILEGES;
    }

    public String getCOLLATION_NAME() {
        return COLLATION_NAME;
    }

    public void setCOLLATION_NAME(String COLLATION_NAME) {
        this.COLLATION_NAME = COLLATION_NAME;
    }

    public String getCOLUMN_TYPE() {
        return COLUMN_TYPE;
    }

    public void setCOLUMN_TYPE(String COLUMN_TYPE) {
        this.COLUMN_TYPE = COLUMN_TYPE;
    }

    public String getDATA_TYPE() {
        return DATA_TYPE;
    }

    public void setDATA_TYPE(String DATA_TYPE) {
        this.DATA_TYPE = DATA_TYPE;
    }

    public String getCHARACTER_SET_NAME() {
        return CHARACTER_SET_NAME;
    }

    public void setCHARACTER_SET_NAME(String CHARACTER_SET_NAME) {
        this.CHARACTER_SET_NAME = CHARACTER_SET_NAME;
    }

    public String getJdlType() {
        return jdlType;
    }

    public void setJdlType(String jdlType) {
        this.jdlType = jdlType;
    }

    public String getJava字段名() {
        return java字段名;
    }

    public void setJava字段名(String java字段名) {
        this.java字段名 = java字段名;
    }

    public String getJava字段名开头大写() {
        return java字段名开头大写;
    }

    public void setJava字段名开头大写(String java字段名开头大写) {
        this.java字段名开头大写 = java字段名开头大写;
    }

    public String getJavaFieldName() {
        return javaFieldName;
    }

    public void setJavaFieldName(String javaFieldName) {
        this.javaFieldName = javaFieldName;
    }

    public String getTsDataType() {
        return tsDataType;
    }

    public void setTsDataType(String tsDataType) {
        this.tsDataType = tsDataType;
    }

    public String getJavaFieldNameStartsWithUppercase() {
        return javaFieldNameStartsWithUppercase;
    }

    public void setJavaFieldNameStartsWithUppercase(String javaFieldNameStartsWithUppercase) {
        this.javaFieldNameStartsWithUppercase = javaFieldNameStartsWithUppercase;
    }

    public static Map<String, String> getDATA_TYPE_Map() {
        return DATA_TYPE_Map;
    }

    public static void setDATA_TYPE_Map(Map<String, String> DATA_TYPE_Map) {
        ColumnInfo.DATA_TYPE_Map = DATA_TYPE_Map;
    }

    public List<String> getNameTypeList() {
        return nameTypeList;
    }

    public void setNameTypeList(List<String> nameTypeList) {
        this.nameTypeList = nameTypeList;
    }

    public List<String> getAddressTypeList() {
        return addressTypeList;
    }

    public void setAddressTypeList(List<String> addressTypeList) {
        this.addressTypeList = addressTypeList;
    }

    String TABLE_CATALOG = "def";
    String IS_NULLABLE = "YES";
    String TABLE_NAME = "notice";
    String TABLE_SCHEMA = "gcsm";
    String EXTRA = "";
    String COLUMN_NAME = "state";
    String COLUMN_KEY = "";
    String COLUMN_COMMENT = "公告状态";
    String PRIVILEGES = "select,insert,update,references";
    String COLLATION_NAME = "utf8_general_ci";
    String COLUMN_TYPE = "varchar(255)";
    String DATA_TYPE = "varchar";
    String CHARACTER_SET_NAME = "utf8";
    String jdlType = "";
    String java字段名;

    String java字段名开头大写;

    String javaFieldName;
    String tsDataType;
    String javaFieldNameStartsWithUppercase;
//    public  String getJavaFieldName(){
//        return getJava字段名().trim();
//
//    }

    public String getJdlRow() {
//        postId: number;
//        getCOLUMN_TYPE()
//        String tsDataType = DataTypeMap.mysqlToTs(getDATA_TYPE());
//        String row=   "   #javaFieldName# #javaFieldType#\n";
//        getJavaFieldType()
        String row = "   #javaFieldName# #javaFieldType#\n";
        return row
                .replace("#javaFieldName#", javaFieldName)
                .replace("#javaFieldType#", jdlType)
//                    .replace("#formName#",formName)
                ;

//      "   " +   javaFieldName+"   "+javaFieldType
//        return  "    "+ getJavaFieldName()+" : "+tsDataType+"  ; ";
    }

    public String getTsDataDType() {
//        postId: number;
//        getCOLUMN_TYPE()
//        String tsDataType = DataTypeMap.mysqlToTs(getDATA_TYPE());
        return "    " + getJavaFieldName() + " : " + tsDataType + "  ; ";
    }

    public String getTsDataDListParams() {
//        postId: number;
//        getCOLUMN_TYPE()
//        String tsDataType = DataTypeMap.mysqlToTs(getDATA_TYPE());
//        postId?: string;
        return "    " + getJavaFieldName() + "?: " + tsDataType + "  ; ";
    }


    public String getColumnCommentShow() {
        if (StringUtils.isNone(COLUMN_COMMENT)) {
//             return getCOLUMN_NAME();
//             return getJava字段名().trim();
            return getJavaFieldName();
        }
        return getCOLUMN_COMMENT().trim();
    }

    public String getJavaFieldType() {
//          String data_type = columnInfo.getDATA_TYPE();
//            "varchar";
        String java字段类型 = ColumnInfo.DATA_TYPE_Map.get(DATA_TYPE);
        if (java字段类型 == null) {
            return "String";
        }
        return java字段类型;
    }

    //    public String getAndEqualTo(){
//
//    }
    public String getAndEqualTo() {
//        String res= "and#name#EqualTo";

//        String res= "criteria = criteria.and#name#EqualTo(\n" +
//                "         example.get#name#()\n" +
//                "      );\n";
        String res = "if(!StrUtil.isNone(example.get#name#())){\n" +
                "     criteria = criteria.and#name#EqualTo(\n" +
                "         example.get#name#()\n" +
                "      );\n" +
                "}";

//        name
        res = res.replace("#name#", javaFieldNameStartsWithUppercase);
        return res;
    }

    public static void main(String[] args) {
        String res = "criteria = criteria.and#name#EqualTo(\n" +
                "         example.get#name#()\n" +
                "      );\n";
        System.out.println(res);
    }

    public String getMysqlToJavaFiledRow() {
//          String data_type = columnInfo.getDATA_TYPE();
//            "varchar";
//        if(getJavaFieldType())
        String column_name = getCOLUMN_NAME();

        if (MysqlDataType.varchar.equals(DATA_TYPE) ||
                MysqlDataType.charType.equals(DATA_TYPE) ||
                MysqlDataType.longtext.equals(DATA_TYPE) ||
                MysqlDataType.longblob.equals(DATA_TYPE) ||
                MysqlDataType.text.equals(DATA_TYPE) ||
                MysqlDataType.enumType.equals(DATA_TYPE)
        ) {
            String row = "          #javaFieldName# =  Convert.toStr(map.get(\"#columnName#\")); \n";
            return row
//                    .replace("#commentShow#",commentShow)
//                    .replace("#java字段名#",java字段名)
                    .replace("#javaFieldName#", javaFieldName)
//                    .replace("#javaFieldType#",javaFieldType)
                    .replace("#columnName#", column_name)
                    ;
        }

        if (MysqlDataType.intType.equals(DATA_TYPE) ||
                MysqlDataType.bigint.equals(DATA_TYPE) ||
                MysqlDataType.tinyint.equals(DATA_TYPE)) {
            String row = "          #javaFieldName# =  Convert.toInt(map.get(\"#columnName#\")); \n";
            return row
//                    .replace("#commentShow#",commentShow)
//                    .replace("#java字段名#",java字段名)
                    .replace("#javaFieldName#", javaFieldName)
//                    .replace("#javaFieldType#",javaFieldType)
                    .replace("#columnName#", column_name)
                    ;
        }

        if (MysqlDataType.datetime.equals(DATA_TYPE)
                || MysqlDataType.date.equals(DATA_TYPE)) {
            String row = "          #javaFieldName# =  Convert.toDate(map.get(\"#columnName#\")); \n";
            return row
//                    .replace("#commentShow#",commentShow)
//                    .replace("#java字段名#",java字段名)
                    .replace("#javaFieldName#", javaFieldName)
//                    .replace("#javaFieldType#",javaFieldType)
                    .replace("#columnName#", column_name)
                    ;
        }
        if (MysqlDataType.doubleType.equals(DATA_TYPE)) {
            String row = "          #javaFieldName# =  Convert.toDouble(map.get(\"#columnName#\")); \n";
            return row
//                    .replace("#commentShow#",commentShow)
//                    .replace("#java字段名#",java字段名)
                    .replace("#javaFieldName#", javaFieldName)
//                    .replace("#javaFieldType#",javaFieldType)
                    .replace("#columnName#", column_name)
                    ;
        }
        if (MysqlDataType.floatType.equals(DATA_TYPE)) {
            String row = "          #javaFieldName# =  Convert.toFloat(map.get(\"#columnName#\")); \n";
            return row
                    .replace("#javaFieldName#", javaFieldName)
                    .replace("#columnName#", column_name)
                    ;
        }

        if (MysqlDataType.decimal.equals(DATA_TYPE)) {
            String row = "          #javaFieldName# =  Convert.toBigDecimal(map.get(\"#columnName#\")); \n";
            return row
                    .replace("#javaFieldName#", javaFieldName)
                    .replace("#columnName#", column_name)
                    ;
        }

//        decimal
//        if (MysqlDataType.enumType.equals(DATA_TYPE)) {
//            String row=  "          #javaFieldName# =  Convert.toDouble(map.get(\"#columnName#\")); \n";
//            return   row
////                    .replace("#commentShow#",commentShow)
////                    .replace("#java字段名#",java字段名)
//                    .replace("#javaFieldName#",javaFieldName)
////                    .replace("#javaFieldType#",javaFieldType)
//                    .replace("#columnName#",column_name)
//                    ;
//        }
        String row = "          #javaFieldName# =  Convert.to#javaFieldType#(map.get(\"#columnName#\")); \n";
        String javaFieldType = getJavaFieldType();
        return row
//                    .replace("#commentShow#",commentShow)
//                    .replace("#java字段名#",java字段名)
                .replace("#javaFieldName#", javaFieldName)
//                    .replace("#javaFieldType#",javaFieldType)
                .replace("#columnName#", column_name)
                .replace("#javaFieldType#", javaFieldType)

                ;
//        if(DATA_TYPE.equals(DataTypeMap.))
//        String row=  "          #java字段名# =  Convert.to#javaFieldType#(map.get(\"#columnName#\")); \n";
//        row=  row
//                .replace("#commentShow#",commentShow)
//                .replace("#java字段名#",java字段名)
//                .replace("#javaFieldType#",javaFieldType)
//                .replace("#columnName#",columnName)
//        ;
//        String java字段类型 = ColumnInfo.DATA_TYPE_Map.get(DATA_TYPE);
//        if(java字段类型==null){
//            return "String";
//        }
//        return java字段类型;
    }

    public String 获取java字段类型() {
//          String data_type = columnInfo.getDATA_TYPE();
//            "varchar";
        String java字段类型 = ColumnInfo.DATA_TYPE_Map.get(DATA_TYPE);
        if (java字段类型 == null) {
            return "String";
        }
        return java字段类型;
    }

    public static Map<String, String> DATA_TYPE_Map = new HashMap<>();

    //[date, datetime, double, varchar, char, longblob, tinyint, text, float, bigint, int]
//
    public String mockData() {
//        coty
        final String column_name = getCOLUMN_NAME();
        if ("id".equals(column_name)) {
            return "null";
        }
        if (column_name.contains("name")) {
            final String name = NameCreator.createName();
            return name;
        }
//        final String data_type = getDATA_TYPE();
//        final Map<String, String> data_type_map = getDATA_TYPE_Map();
//        data_type_map.
//        随机数字 int java

//        MysqlDataType.datetime.equals(data_type)
//        MysqlDataType.datetime.equals()
        if (isDateType()) {
            final String now = DateUtilSt.getNow();
            return now;
        }
        if (MysqlDataType.isNumberType(DATA_TYPE)) {
            final int randomInt = RandomUtil.randomInt(0, 91111111);
            return "" + randomInt + "";
        }
        return "mockData";
    }

    public boolean isDateType() {
        return MysqlDataType.datetime.equals(DATA_TYPE)

                || MysqlDataType.date.equals(DATA_TYPE);

    }

    public boolean isNumberType() {
        for (String numberType : MysqlDataType.numberTypeArr) {
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

    static {
        DATA_TYPE_Map.put("varchar", "String");
        DATA_TYPE_Map.put("int", "Integer");
        DATA_TYPE_Map.put("tinyint", "Integer");
        DATA_TYPE_Map.put("longtext", "String");
        DATA_TYPE_Map.put("text", "String");
        DATA_TYPE_Map.put("datetime", "Date");
        DATA_TYPE_Map.put("double", "Double");
        DATA_TYPE_Map.put("char", "String");
        DATA_TYPE_Map.put("bigint", "Integer");
        DATA_TYPE_Map.put("enum", "String");
        DATA_TYPE_Map.put("float", "Float");
        DATA_TYPE_Map.put("longblob", "String");

//          DATA_TYPE_Map.put(MysqlDataType.date,"Date");
        DATA_TYPE_Map.put(MysqlDataType.date, JavaDataType.Date);
        DATA_TYPE_Map.put(MysqlDataType.decimal, "BigDecimal");
//          Integer
    }
//     public static void main(String[] args) {
////          ColumnInfo columnInfo=new ColumnInfo();
////          columnInfo.getCHARACTER_SET_NAME()
//     }
//
////     public  void fromMap(Map<String ,Object>map){
////          TABLE_CATALOG= (String) map.get("");
////     }

    List<String> nameTypeList = Arrays.asList("name", "username");
    List<String> addressTypeList = Arrays.asList("地址", "住址");

    //    genBatchAddPartsSetRows
    public String genBatchAddPartsSetRow(String entityName) {
        String columnCommentShow = getColumnCommentShow();
//        getJavaFieldType()
//    entity

        String tpl = "  {entityName}.set{javaFieldNameStartsWithUppercase}(Converter.to{converterFuncName}({entityName}Parts.get(partIdx++).toString()));\n";
        final String converterFuncName = MysqlDataType.toConverterFuncName(DATA_TYPE);
        tpl = tpl
                .replace("#columnCommentShow#", columnCommentShow)
                .replace("#javaFieldName#", javaFieldName)
                .replace("#COLUMN_NAME#", COLUMN_NAME)
//                .replace("#className#", className)
                .replace("{entityName}", entityName)
                .replace("{converterFuncName}", converterFuncName)
                .replace("{javaFieldNameStartsWithUppercase}", javaFieldNameStartsWithUppercase)
        ;
        return tpl;
    }

    public String makeReactRuoyiDrawerFormRow(String className) {
        String columnCommentShow = getColumnCommentShow();
//        getJavaFieldType()
        if (MysqlDataType.intType.equals(DATA_TYPE)) {
            String tpl = "<Row gutter={[16, 16]}>\n" +
                    "        <Col span={24} order={1}>\n" +
                    "          <ProFormDigit\n" +
                    "            name=\"#javaFieldName#\"\n" +
                    "            label={intl.formatMessage({\n" +
                    "              id: 'system.#className#.#COLUMN_NAME#',\n" +
                    "              defaultMessage: '#columnCommentShow#',\n" +
                    "            })}\n" +
                    "            width=\"xl\"\n" +
                    "            placeholder=\"请输入#columnCommentShow#\"\n" +
                    "            disabled\n" +
                    "        //     hidden={!props.values.#javaFieldName#}\n" +
                    "            rules={[\n" +
                    "              {\n" +
                    "                required: false,\n" +
                    "                message: <FormattedMessage id=\"请输入#columnCommentShow#！\" defaultMessage=\"请输入#columnCommentShow#！\" />,\n" +
                    "              },\n" +
                    "            ]}\n" +
                    "          />\n" +
                    "        </Col>\n" +
                    "      </Row>";

            tpl = tpl
                    .replace("#columnCommentShow#", columnCommentShow)
                    .replace("#javaFieldName#", javaFieldName)
                    .replace("#COLUMN_NAME#", COLUMN_NAME)
                    .replace("#className#", className)
            ;
            return tpl;
        }
        if (MysqlDataType.varchar.equals(DATA_TYPE)) {
            String tpl = " <Row gutter={[16, 16]}>\n" +
                    "        <Col span={24} order={1}>\n" +
                    "          <ProFormText\n" +
                    "            name=\"#javaFieldName#\"\n" +
                    "            label={intl.formatMessage({\n" +
                    "              id: 'system.#className#.#COLUMN_NAME#',\n" +
                    "              defaultMessage: '#columnCommentShow#',\n" +
                    "            })}\n" +
                    "            width=\"xl\"\n" +
                    "            placeholder=\"请输入#columnCommentShow#\"\n" +
                    "            rules={[\n" +
                    "              {\n" +
                    "                required: false,\n" +
                    "                message: (\n" +
                    "                  <FormattedMessage id=\"请输入#columnCommentShow#！\" defaultMessage=\"请输入#columnCommentShow#！\" />\n" +
                    "                ),\n" +
                    "              },\n" +
                    "            ]}\n" +
                    "          />\n" +
                    "        </Col>\n" +
                    "      </Row>";

            tpl = tpl
                    .replace("#columnCommentShow#", columnCommentShow)
                    .replace("#javaFieldName#", javaFieldName)
                    .replace("#COLUMN_NAME#", COLUMN_NAME)
                    .replace("#className#", className)
            ;
            return tpl;
        }

        String tpl = " <Row gutter={[16, 16]}>\n" +
                "        <Col span={24} order={1}>\n" +
                "          <ProFormText\n" +
                "            name=\"#javaFieldName#\"\n" +
                "            label={intl.formatMessage({\n" +
                "              id: 'system.#className#.#COLUMN_NAME#',\n" +
                "              defaultMessage: '#columnCommentShow#',\n" +
                "            })}\n" +
                "            width=\"xl\"\n" +
                "            placeholder=\"请输入#columnCommentShow#\"\n" +
                "            rules={[\n" +
                "              {\n" +
                "                required: false,\n" +
                "                message: (\n" +
                "                  <FormattedMessage id=\"请输入#columnCommentShow#！\" defaultMessage=\"请输入#columnCommentShow#！\" />\n" +
                "                ),\n" +
                "              },\n" +
                "            ]}\n" +
                "          />\n" +
                "        </Col>\n" +
                "      </Row>";

        tpl = tpl
                .replace("#columnCommentShow#", columnCommentShow)
                .replace("#javaFieldName#", javaFieldName)
                .replace("#COLUMN_NAME#", COLUMN_NAME)
                .replace("#className#", className)
        ;
        return tpl;


    }

    public String makeReactFormItem() {
        String columnCommentShow = getColumnCommentShow();
        if ("sex".equals(javaFieldName)) {
            return "<Form.Item label='性别'>\n" +
                    "                                    {getFieldDecorator('sex', {\n" +
                    "                                        rules: [{ required: false, message: '请选择性别' }]\n" +
                    "                                    })(\n" +
                    "                                        <Radio.Group>\n" +
                    "                                            <Radio value='man'>男</Radio>\n" +
                    "                                            <Radio value='women'>女</Radio>\n" +
                    "                                            <Radio value='unknow'>不详</Radio>\n" +
                    "                                        </Radio.Group>\n" +
                    "                                    )}\n" +
                    "                                </Form.Item>";
        }
        if (nameTypeList.contains(javaFieldName)) {
            String tpl = "<Form.Item\n" +
                    "                                    label={\n" +
                    "                                        <span>\n" +
                    "                                            #commentShow#&nbsp;\n" +
                    "                                            <Tooltip title='可以尽量好听点，真的!'>\n" +
                    "                                                <Icon type='question-circle-o' />\n" +
                    "                                            </Tooltip>\n" +
                    "                                        </span>\n" +
                    "                                    }>\n" +
                    "                                    {getFieldDecorator('#javaFieldName#', {\n" +
                    "                                        rules: [{ required: false, message: '请输入#commentShow#' }]\n" +
                    "                                    })(<Input placeholder='请输入#commentShow#' />)}\n" +
                    "                                </Form.Item>";

            tpl = tpl
                    .replace("#commentShow#", getColumnCommentShow())
//                     .replace("#java字段名#",java字段名)
                    .replace("#javaFieldName#", javaFieldName)
            ;
            return tpl;
        }
        if ("age".equals(javaFieldName)) {
            String tpl = "<Form.Item label='年龄'>\n" +
                    "                                    {getFieldDecorator('age', {\n" +
                    "                                        rules: [{ required: false, message: '请输入年龄' }]\n" +
                    "                                    })(<InputNumber placeholder='请输入年龄' style={{ width: '100%' }} />)}\n" +
                    "                                </Form.Item>";


            return tpl;
        }
        if (javaFieldName.contains("date") || javaFieldName.contains("time")) {
            String tpl = "<Form.Item label='#commentShow#'>\n" +
                    "                                    {getFieldDecorator('#javaFieldName#', {\n" +
                    "                                        rules: [{ type: 'object', required: false, message: '请选择#commentShow#' }]\n" +
                    "                                    })(<DatePicker style={{ width: '100%' }} placeholder='请选择#commentShow#' />)}\n" +
                    "                                </Form.Item>";

            tpl = tpl
                    .replace("#commentShow#", getColumnCommentShow())
                    .replace("#javaFieldName#", javaFieldName)
            ;
            return tpl;
        }
        if (javaFieldName.contains("mail") || columnCommentShow.contains("邮箱")) {
            String tpl = "<Form.Item label='#commentShow#'>\n" +
                    "                                    {getFieldDecorator('#javaFieldName#', {\n" +
                    "                                        rules: [\n" +
                    "                                            {\n" +
                    "                                                type: 'email',\n" +
                    "                                                message: '请输入正确的#commentShow#!'\n" +
                    "                                            },\n" +
                    "                                            {\n" +
                    "                                                required: false,\n" +
                    "                                                message: '请输入#commentShow#'\n" +
                    "                                            }\n" +
                    "                                        ]\n" +
                    "                                    })(\n" +
                    "                                        <AutoComplete\n" +
                    "                                            dataSource={websiteOptions}\n" +
                    "                                            onChange={this.handleWebsiteChange}\n" +
                    "                                            placeholder='请输入#commentShow#'>\n" +
                    "                                            <Input />\n" +
                    "                                        </AutoComplete>\n" +
                    "                                    )}\n" +
                    "                                </Form.Item>";

            tpl = tpl
                    .replace("#commentShow#", columnCommentShow)
                    .replace("#javaFieldName#", javaFieldName)
            ;
            return tpl;
        }

        if (javaFieldName.contains("password") || columnCommentShow.contains("密码")) {
            String tpl = " <Form.Item label='#commentShow#' hasFeedback>\n" +
                    "                                    {getFieldDecorator('#javaFieldName#', {\n" +
                    "                                        rules: [\n" +
                    "                                            {\n" +
                    "                                                required: false,\n" +
                    "                                                message: '请输入#commentShow#!'\n" +
                    "                                            },\n" +
                    "                                            {\n" +
                    "                                                validator: this.validateToNextPassword\n" +
                    "                                            }\n" +
                    "                                        ]\n" +
                    "                                    })(<Input.Password placeholder='请输入#commentShow#' />)}\n" +
                    "                                </Form.Item>\n" +
                    "" +
                    "<Form.Item label='确认#commentShow#' hasFeedback>\n" +
                    "                                    {getFieldDecorator('confirm', {\n" +
                    "                                        rules: [\n" +
                    "                                            {\n" +
                    "                                                required: false,\n" +
                    "                                                message: '请确认#commentShow#!'\n" +
                    "                                            },\n" +
                    "                                            {\n" +
                    "                                                validator: this.compareToFirstPassword\n" +
                    "                                            }\n" +
                    "                                        ]\n" +
                    "                                    })(<Input.Password onBlur={this.handleConfirmBlur} placeholder='请确认#commentShow#' />)}\n" +
                    "                                </Form.Item>";

            tpl = tpl
                    .replace("#commentShow#", columnCommentShow)
                    .replace("#javaFieldName#", javaFieldName)
            ;
            return tpl;
        }

        boolean addressTypeField = javaFieldName.contains("address") ||
                ListUtil.haveLikeWordMuch(addressTypeList, columnCommentShow);
        if (addressTypeField) {
            String tpl = "  <Form.Item label='#commentShow#'>\n" +
                    "                                    {getFieldDecorator('#javaFieldName#', {\n" +
                    "                                        initialValue: ['sichuan', 'chengdu', 'gaoxin'],\n" +
                    "                                        rules: [{ type: 'array', required: false, message: '请选择#commentShow#!' }]\n" +
                    "                                    })(<Cascader options={residences} placeholder='请选择#commentShow#' />)}\n" +
                    "                                </Form.Item>";

            tpl = tpl
                    .replace("#commentShow#", columnCommentShow)
                    .replace("#javaFieldName#", javaFieldName)
            ;
            return tpl;
        }

        if (javaFieldName.contains("phone") || columnCommentShow.contains("电话")) {
            String tpl = " <Form.Item label='#commentShow#' extra='你最好写真实的#commentShow#.'>\n" +
                    "                                    {getFieldDecorator('#javaFieldName#', {\n" +
                    "                                        rules: [{ required: false, message: '请输入#commentShow#!' }]\n" +
                    "                                    })(<Input addonBefore={prefixSelector} />)}\n" +
                    "                                </Form.Item>";

            tpl = tpl
                    .replace("#commentShow#", columnCommentShow)
                    .replace("#javaFieldName#", javaFieldName)
            ;
            return tpl;
        }
        String tpl = " <Form.Item\n" +
                "                                    label={\n" +
                "                                        <span>\n" +
                "                                            #commentShow#&nbsp;\n" +
                "                                            <Tooltip title='可以尽量好听点，真的!'>\n" +
                "                                                <Icon type='question-circle-o' />\n" +
                "                                            </Tooltip>\n" +
                "                                        </span>\n" +
                "                                    }>\n" +
                "                                        {/* 表单用的字符串的话 感觉不是很优雅啊  不过应该没事  */}\n" +
                "                                    {getFieldDecorator('#javaFieldName#', {\n" +
                "                                        rules: [{ required: false, message: '请输入#commentShow#' }]\n" +
                "                                    })(<Input placeholder='请输入#commentShow#' />)}\n" +
                "                                </Form.Item>";

        tpl = tpl
                .replace("#commentShow#", columnCommentShow)
                .replace("#javaFieldName#", javaFieldName)
        ;
        return tpl;

    }

    public String getReactFormItem() {
//         get
//        javaFieldName.equals()
        if ("sex".equals(javaFieldName)) {
            return "<Form.Item label='性别'>\n" +
                    "                                    {getFieldDecorator('sex', {\n" +
                    "                                        rules: [{ required: false, message: '请选择性别' }]\n" +
                    "                                    })(\n" +
                    "                                        <Radio.Group>\n" +
                    "                                            <Radio value='man'>男</Radio>\n" +
                    "                                            <Radio value='women'>女</Radio>\n" +
                    "                                            <Radio value='unknow'>不详</Radio>\n" +
                    "                                        </Radio.Group>\n" +
                    "                                    )}\n" +
                    "                                </Form.Item>";
        }
        if (nameTypeList.contains(javaFieldName)) {
            return "<Form.Item\n" +
                    "                                    label={\n" +
                    "                                        <span>\n" +
                    "                                            用户名&nbsp;\n" +
                    "                                            <Tooltip title='可以尽量好听点，真的!'>\n" +
                    "                                                <Icon type='question-circle-o' />\n" +
                    "                                            </Tooltip>\n" +
                    "                                        </span>\n" +
                    "                                    }>\n" +
                    "                                    {getFieldDecorator('username', {\n" +
                    "                                        rules: [{ required: false, message: '请输入用户名' }]\n" +
                    "                                    })(<Input placeholder='请输入用户名' />)}\n" +
                    "                                </Form.Item>";
        }
        return "<Form.Item\n" +
                "                                    label={\n" +
                "                                        <span>\n" +
                "                                            #commentShow#&nbsp;\n" +
                "                                            <Tooltip title='可以尽量好听点，真的!'>\n" +
                "                                                <Icon type='question-circle-o' />\n" +
                "                                            </Tooltip>\n" +
                "                                        </span>\n" +
                "                                    }>\n" +
                "                                    {getFieldDecorator('#java字段名#', {\n" +
                "                                        rules: [{ required: false, message: '请输入#commentShow#' }]\n" +
                "                                    })(<Input placeholder='请输入#commentShow#' />)}\n" +
                "                                </Form.Item>\n";
    }
//    public    String  getAndEqualTo(){
////        String res= "and#name#EqualTo";
//        String res= ".and#name#EqualTo(\n" +
//                "         example.get#name#()\n" +
//                "      )";
////        name
//        res=res.replace("#name#", javaFieldNameStartsWithUppercase);
//        return res;
//    }

    public void fromMap(Map<String, Object> map) {
        TABLE_CATALOG = (String) map.get("TABLE_CATALOG");
        IS_NULLABLE = (String) map.get("IS_NULLABLE");
        TABLE_NAME = (String) map.get("TABLE_NAME");
        TABLE_SCHEMA = (String) map.get("TABLE_SCHEMA");
        EXTRA = (String) map.get("EXTRA");
        COLUMN_NAME = (String) map.get("COLUMN_NAME");
        COLUMN_KEY = (String) map.get("COLUMN_KEY");
        COLUMN_COMMENT = (String) map.get("COLUMN_COMMENT");
        PRIVILEGES = (String) map.get("PRIVILEGES");
        COLLATION_NAME = (String) map.get("COLLATION_NAME");
        COLUMN_TYPE = (String) map.get("COLUMN_TYPE");
        DATA_TYPE = (String) map.get("DATA_TYPE");
        CHARACTER_SET_NAME = (String) map.get("CHARACTER_SET_NAME");
        java字段名 = StringUtils.underlineToCamelCase(COLUMN_NAME);


        java字段名开头大写 = StringUtils.upperCaseFirst(java字段名);
        javaFieldName = StringUtils.underlineToCamelCase(COLUMN_NAME);
//         javaFieldName= StringUtils.upperCaseFirst(COLUMN_NAME).trim();
//         javaFieldNameStartsWithUppercase= StringUtils.upperCaseFirst(javaFieldName).trim();
        javaFieldNameStartsWithUppercase = StringUtils.upperCaseFirst(javaFieldName);
        tsDataType = DataTypeMap.mysqlToTs(getDATA_TYPE());
        jdlType = DataTypeMap.mysqlToJdl(getDATA_TYPE());

    }


}
