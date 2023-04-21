package com.example.demo.controller;

import com.example.demo.entity.Acc;
import com.example.demo.entity.ReturnT;
import com.example.demo.repository.AccRepository;
import com.example.demo.repository.ToolDAO;
import com.example.demo.util.FileUtil;
import com.example.demo.util.StrUtil;
import com.example.demo.util.StringUtils;
import com.example.demo.util.codeGen.CodeGen;
import com.example.demo.util.codeGen.ColumnInfo;
import com.example.demo.util.codeGen.TableInfo;
import com.sun.xml.txw2.output.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import javax.annotation.Resource;
import javax.xml.transform.Result;
import java.lang.annotation.Annotation;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author mqp
 * @description acc
 * @date 2021-12-20
 */
@RestController
@RequestMapping("/all")
@Slf4j
@Api(tags = "all")
@Transactional
@CrossOrigin
public class AllController {

    @Value("${spring.datasource.url}")
    String springDatasourceUrl;
    @Resource
    ToolDAO toolDAO;
    void genByMap(   Map<String, String> map){
        String className= map.get("className");
        String classURL= map.get("classURL");
        String classDesc= map.get("classDesc");
        String methodName= map.get("methodName");
        String methodDesc= map.get("methodDesc");
        String methodURL= map.get("methodURL");
        String requestType= map.get("requestType");
//        .#methodType#(
//        String jsonDefaultNull = genJsonDefaultNull();
//        String str="";
//        str.replace("#methodType#",requestType.toLowerCase())
//                .replace("#实体名#",requestType.toLowerCase())
//                .replace("#commentShow#",commentShow)
//                .replace("#java字段名#",java字段名)
//                .replace("#实体名#",实体名)
//                .replace("#类名#",类名)
//                .replace("#java字段名开头大写#",java字段名开头大写)
    }
    @Autowired
    WebApplicationContext applicationContext;
    @ApiOperation(value = "getAllURL", notes = "getAllURL")
    @RequestMapping(value = "/getAllURL", method = RequestMethod.POST)
    public Object getAllURL() {
        List<Map<String, String>> resultList = new ArrayList<>();

        RequestMappingHandlerMapping requestMappingHandlerMapping =
                applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> mappingInfoHandlerMethodEntry : map.entrySet()) {
            Map<String, String> resultMap = new LinkedHashMap<>();

            RequestMappingInfo requestMappingInfo = mappingInfoHandlerMethodEntry.getKey();
            HandlerMethod handlerMethod = mappingInfoHandlerMethodEntry.getValue();

            resultMap.put("className",handlerMethod.getMethod().getDeclaringClass().getName()); // 类名
            Annotation[] parentAnnotations = handlerMethod.getBeanType().getAnnotations();
            for (Annotation annotation : parentAnnotations) {
                if (annotation instanceof Api) {
                    Api api = (Api) annotation;
                    resultMap.put("classDesc",api.value());
                } else if (annotation instanceof RequestMapping) {
                    RequestMapping requestMapping = (RequestMapping) annotation;
                    if (null != requestMapping.value() && requestMapping.value().length > 0) {
                        resultMap.put("classURL",requestMapping.value()[0]);//类URL
                    }
                }
            }
            resultMap.put("methodName", handlerMethod.getMethod().getName()); // 方法名
            Annotation[] annotations = handlerMethod.getMethod().getDeclaredAnnotations();
            if (annotations != null) {
                // 处理具体的方法信息
                for (Annotation annotation : annotations) {
                    if (annotation instanceof ApiOperation) {
                        ApiOperation methodDesc = (ApiOperation) annotation;
                        String desc = methodDesc.value();
                        resultMap.put("methodDesc",desc);//接口描述
                    }
                }
            }
            PatternsRequestCondition p = requestMappingInfo.getPatternsCondition();
            for (String url : p.getPatterns()) {
                resultMap.put("methodURL",url);//请求URL
//                "postData({quotation}{url}{quotation},dataPost).then(res=>{})"
//                        .replace("{url}",url)
//                        .replace("{quotation}",'"'+"")
//                ;
            }
//            url    "methodURL": "/rgb/delete",

            RequestMethodsRequestCondition methodsCondition = requestMappingInfo.getMethodsCondition();
            for (RequestMethod requestMethod : methodsCondition.getMethods()) {
                resultMap.put("requestType",requestMethod.toString());//请求方式：POST/PUT/GET/DELETE
            }
            resultList.add(resultMap);
        }
        return  resultList;
//        return JSONArray.fromObject(resultList);
    }
    @ApiOperation(value = "table_names_out_str_get", notes = "table_names_out_str_get")
    @PostMapping("/table_names_out_str_get")
    public Object table_names_out_str_get(@RequestBody TableInfo tableInfo) throws Exception {
//        userService.editUser(requestUser);
//        String controllerPackageName = LibraryController.class.getPackage().getName();
//        log.info("controllerPackageName {}",controllerPackageName);


//        EntityManager entityManager;
//        entityManager.find()
//        <expression> or DISTINCT expected, got '*'
//        String hql="Select * From User";
//        List<User> userList=entityManager.createQuery(hql);
//        Query nativeQuery = entityManager.createNativeQuery(hql);
//        List resultList = nativeQuery.getResultList();
//        nativeQuery.setParameter()
        //        List<Map<String, Object>> maps = userDAO.selectTableNames("wj", "base table");
//        List<Map<String, Object>> tableNames =
//                userDAO.selectTableNames("gcsm", "base table");
//        for (Map<String, Object> tableName : tableNames) {
//            tableName.get("ta")
//        }

        System.out.println("springDatasourceUrl");
        System.out.println(springDatasourceUrl);
        String table_schema = tableInfo.getTable_schema();
//        String table_schema= "gcsm";
        if (StringUtils.isNone(table_schema)) {
//            table_schema="gcsm";
//            table_schema="mqtt_control";
//            table_schema = "exam";
            table_schema = "iot_db";
        }
        List<Map<String, Object>> maps =
//                toolDAO.selectTableNames(table_schema, "base table");
                toolDAO.selectTableNames(table_schema);
//        List<TableName> tableNames = userDAO.selectTableNamesToObj("gcsm", "base table");
        System.out.println(maps);
        log.info("selectTableNames");
        log.info("maps {}", maps);

//        userDAO.saveAll()

//        CodeGen
//        File file = new File("repo");
        String code = FileUtil.readResourceFileData(
                "genCodeTemplate/elementUi/Tables.js");

        StringBuilder tablesCodeRows = new StringBuilder();
        StringBuilder colProperties= new StringBuilder();
        Set<String> set = new HashSet<>();
        List<String> table_names = maps.stream().map(o -> {
            String table_name = (String) o.get("table_name");
            return table_name;
        }).collect(Collectors.toList());
        log.info("table_names {}",table_names);
        Map<String, List<ColumnInfo>> mapTableCols = new HashMap<>();
        String  ALTER_add_tenant_id_out_str="";
        String  ALTER_MODIFY_tenant_id_str="";
        String table_names_out_str="";
        for (String table_name : table_names) {
            String  table_nameOne= "\"{table_name}\",".replace("{table_name}",table_name);
            table_names_out_str+=table_nameOne+"\n";

            List<Map<String, Object>> tableCols =
                    toolDAO.selectTableCols(table_schema, table_name);
            List<ColumnInfo> columnInfos = tableCols.stream().map(o -> {
                ColumnInfo columnInfo = new ColumnInfo();
                columnInfo.fromMap(o);
                return columnInfo;
            }).collect(Collectors.toList());
            mapTableCols.put(table_name, columnInfos);
//            ALTER TABLE `t_user`
//            ADD COLUMN `tenant_id`  int NULL
            String ALTER_add_tenant_id="ALTER TABLE `{table_name}`\n" +
                    "ADD COLUMN `tenant_id`  int NULL ;";
            String ALTER_MODIFY_tenant_id="ALTER TABLE `{table_name}`\n" +
                    "MODIFY COLUMN `tenant_id`  varchar(20) NULL DEFAULT NULL;";

            ALTER_add_tenant_id= ALTER_add_tenant_id
//                    .replace()
                    .replace("{table_name}",table_name);
            ALTER_add_tenant_id_out_str+=ALTER_add_tenant_id+"\n";
            ALTER_MODIFY_tenant_id= ALTER_MODIFY_tenant_id
                    .replace("{table_name}",table_name);
            ALTER_add_tenant_id_out_str+=ALTER_add_tenant_id+"\n";
            ALTER_MODIFY_tenant_id_str+=ALTER_MODIFY_tenant_id+"\n";
            log.info("ALTER_add_tenant_id {}",ALTER_add_tenant_id);
            String camelCaseTable = StringUtils.underlineToCamelCase(table_name);
            String tablesRow = "\"#camelCaseTable#\":\"#camelCaseTable#\",\n";
            tablesRow = tablesRow.replace("#camelCaseTable#", camelCaseTable);
            tablesCodeRows.append(tablesRow);

            String colPropertiesRow="else if (type === '#camelCaseTable#') {\n" +
                    "              this.properties = getDict(#camelCaseTable#Columns);\n" +
                    "          }\n";
            colPropertiesRow=colPropertiesRow.replace("#camelCaseTable#", camelCaseTable);
            colProperties.append(colPropertiesRow);
            List<String> dataTypes = columnInfos.stream()
                    .map(o -> o.getDATA_TYPE()).collect(Collectors.toList());
            set.addAll(dataTypes);
        }
//        System.out.println("mapTableCols");
//        System.out.println(mapTableCols);

//        try {
//            CodeGen.gen(mapTableCols, table_schema);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        code = code
                .replace("#TablesRows#", tablesCodeRows.toString())
        ;
//        java.nio.file.Path dictDataPath = Paths.get(CodeGen.pathFileString,
//                "elementUi", "utils", "Tables" + ".js");
//
//        Path colIfPath = Paths.get(CodeGen.pathFileString,
//                "elementUi", "utils", "colIf" + ".js");

//        System.out.println("dictDataPath");
//        System.out.println(dictDataPath);
//        FileUtil.writeCode(dictDataPath, code);
//        FileUtil.writeCode(colIfPath, colProperties.toString());
//        Path ALTER_add_tenant_id_out_str_file_path = Paths.get(CodeGen.pathFileString,
//                "sql", "ALTER_add_tenant_id.sql");
//
//        FileUtil.writeCode(ALTER_add_tenant_id_out_str_file_path, ALTER_add_tenant_id_out_str);

//        columnInfos.

        System.out.println("set 字段类型有哪些");
        System.out.println(set);
//        log.info(ALTER_MODIFY_tenant_id_str);
//        set 字段类型有哪些
//[longtext, datetime, double, varchar, char, tinyint, int]
//        CodeGen

//        maps.forEach(o->{
//            TableName tableName = new TableName();
//            tableName.fromMap(o);
//            return
//        });
//        List<TableName> tableNames = maps.stream().map(o -> {
//            TableName tableName = new TableName();
//            tableName.fromMap(o);
//            return tableName;
//        }).collect(Collectors.toList());
//        log.info("tableNames {}",tableNames);
//        log.info(tableNames);

//        Co
log.info("table_names_out_str {}",table_names_out_str);
        return ReturnT.success(table_names_out_str);
//        Result.
//        return ResultFactory.buildSuccessResult(mapTableCols);
    }

    @ApiOperation(value = "genCode", notes = "genCode")
    @PostMapping("/genCode")
    public Object genCode(@RequestBody TableInfo tableInfo) throws Exception {
//        userService.editUser(requestUser);
//        String controllerPackageName = LibraryController.class.getPackage().getName();
//        log.info("controllerPackageName {}",controllerPackageName);


//        EntityManager entityManager;
//        entityManager.find()
//        <expression> or DISTINCT expected, got '*'
//        String hql="Select * From User";
//        List<User> userList=entityManager.createQuery(hql);
//        Query nativeQuery = entityManager.createNativeQuery(hql);
//        List resultList = nativeQuery.getResultList();
//        nativeQuery.setParameter()
        //        List<Map<String, Object>> maps = userDAO.selectTableNames("wj", "base table");
//        List<Map<String, Object>> tableNames =
//                userDAO.selectTableNames("gcsm", "base table");
//        for (Map<String, Object> tableName : tableNames) {
//            tableName.get("ta")
//        }

        System.out.println("springDatasourceUrl");
        System.out.println(springDatasourceUrl);
        String table_schema = tableInfo.getTable_schema();
//        String table_schema= "gcsm";
        if (StringUtils.isNone(table_schema)) {
//            table_schema="gcsm";
//            table_schema="mqtt_control";
//            table_schema = "exam";
            table_schema = "iot_db";
        }
        List<Map<String, Object>> maps =
//                toolDAO.selectTableNames(table_schema, "base table");
                toolDAO.selectTableNames(table_schema);
//        List<TableName> tableNames = userDAO.selectTableNamesToObj("gcsm", "base table");
//        System.out.println(maps);
//        log.info("selectTableNames");
//        log.info("maps {}", maps);

//        userDAO.saveAll()

//        CodeGen
//        File file = new File("repo");
        String code = FileUtil.readResourceFileData(
                "genCodeTemplate/elementUi/Tables.js");

        StringBuilder tablesCodeRows = new StringBuilder();
        StringBuilder colProperties= new StringBuilder();
        Set<String> set = new HashSet<>();
        List<String> table_names = maps.stream().map(o -> {
            String table_name = (String) o.get("table_name");
            return table_name;
        }).collect(Collectors.toList());
//        log.info("table_names {}",table_names);
        Map<String, List<ColumnInfo>> mapTableCols = new HashMap<>();
      String  ALTER_add_tenant_id_out_str="";
      String  ALTER_MODIFY_tenant_id_str="";
        for (String table_name : table_names) {
            List<Map<String, Object>> tableCols =
                    toolDAO.selectTableCols(table_schema, table_name);
            List<ColumnInfo> columnInfos = tableCols.stream().map(o -> {
                ColumnInfo columnInfo = new ColumnInfo();
                columnInfo.fromMap(o);
                return columnInfo;
            }).collect(Collectors.toList());
            mapTableCols.put(table_name, columnInfos);
//            ALTER TABLE `t_user`
//            ADD COLUMN `tenant_id`  int NULL
            String ALTER_add_tenant_id="ALTER TABLE `{table_name}`\n" +
                    "ADD COLUMN `tenant_id`  int NULL ;";
            String ALTER_MODIFY_tenant_id="ALTER TABLE `{table_name}`\n" +
                    "MODIFY COLUMN `tenant_id`  varchar(20) NULL DEFAULT NULL;";

            ALTER_add_tenant_id= ALTER_add_tenant_id
//                    .replace()
                    .replace("{table_name}",table_name);
            ALTER_add_tenant_id_out_str+=ALTER_add_tenant_id+"\n";
            ALTER_MODIFY_tenant_id= ALTER_MODIFY_tenant_id
                    .replace("{table_name}",table_name);
            ALTER_add_tenant_id_out_str+=ALTER_add_tenant_id+"\n";
            ALTER_MODIFY_tenant_id_str+=ALTER_MODIFY_tenant_id+"\n";
//            log.info("ALTER_add_tenant_id {}",ALTER_add_tenant_id);
//            StringUtils.rm 删掉前面的 t_
//            pre
//            CodeGen.
           String table_name_removePrefix = StrUtil.removePrefix(
                   table_name, new String[]{  TableInfo.tablePreffix});
//            String camelCaseTable = StringUtils.underlineToCamelCase(table_name);

            String camelCaseTable = StringUtils.underlineToCamelCase(table_name);
            String tablesRow = "\"#camelCaseTable#\":\"#camelCaseTable#\",\n";
            tablesRow = tablesRow.replace("#camelCaseTable#", camelCaseTable);

            tablesCodeRows.append(tablesRow);
            tablesCodeRows.append(   "\"#table_name_removePrefix#\":\"#table_name_removePrefix#\",\n"
                    .replace("#table_name_removePrefix#", table_name_removePrefix));
            tablesCodeRows.append(   "\"#table_name#\":\"#table_name#\",\n"
                    .replace("#table_name#", table_name));

            String colPropertiesRow="else if (type === '#camelCaseTable#') {\n" +
                    "              this.properties = getDict(#camelCaseTable#Columns);\n" +
                    "          }\n";
            colPropertiesRow=colPropertiesRow.replace("#camelCaseTable#", camelCaseTable);
            colProperties.append(colPropertiesRow);
            List<String> dataTypes = columnInfos.stream()
                    .map(o -> o.getDATA_TYPE()).collect(Collectors.toList());
            set.addAll(dataTypes);
        }
//        System.out.println("mapTableCols");
//        System.out.println(mapTableCols);

        try {
            CodeGen.gen(mapTableCols, table_schema);
        } catch (Exception e) {
            e.printStackTrace();
        }

        code = code
                .replace("#TablesRows#", tablesCodeRows.toString())
        ;
        java.nio.file.Path TablesJsPath = Paths.get(CodeGen.pathFileString,
                "elementUi", "utils", "Tables" + ".js");

        Path colIfPath = Paths.get(CodeGen.pathFileString,
                "elementUi", "utils", "colIf" + ".js");

//        System.out.println("TablesJsPath");
//        System.out.println(TablesJsPath);
        FileUtil.writeCode(TablesJsPath, code);
        FileUtil.writeCode(colIfPath, colProperties.toString());
        Path ALTER_add_tenant_id_out_str_file_path = Paths.get(CodeGen.pathFileString,
                "sql", "ALTER_add_tenant_id.sql");

        FileUtil.writeCode(ALTER_add_tenant_id_out_str_file_path, ALTER_add_tenant_id_out_str);

//        columnInfos.

        System.out.println("set 字段类型有哪些");
        System.out.println(set);
        log.info(ALTER_MODIFY_tenant_id_str);
//        set 字段类型有哪些
//[longtext, datetime, double, varchar, char, tinyint, int]
//        CodeGen

//        maps.forEach(o->{
//            TableName tableName = new TableName();
//            tableName.fromMap(o);
//            return
//        });
//        List<TableName> tableNames = maps.stream().map(o -> {
//            TableName tableName = new TableName();
//            tableName.fromMap(o);
//            return tableName;
//        }).collect(Collectors.toList());
//        log.info("tableNames {}",tableNames);
//        log.info(tableNames);

//        Co

        return ReturnT.success(mapTableCols);
//        Result.
//        return ResultFactory.buildSuccessResult(mapTableCols);
    }

}
