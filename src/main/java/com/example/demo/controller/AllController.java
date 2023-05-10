package com.example.demo.controller;

import com.example.demo.entity.Acc;
import com.example.demo.entity.ReturnT;
import com.example.demo.repository.AccRepository;
import com.example.demo.repository.ToolDAO;
import com.example.demo.util.*;
import com.example.demo.util.codeGen.CodeGen;
import com.example.demo.util.codeGen.ColumnInfo;
import com.example.demo.util.codeGen.TableInfo;
import com.lark.oapi.Client;
import com.lark.oapi.core.cache.LocalCache;
import com.lark.oapi.core.enums.BaseUrlEnum;
import com.lark.oapi.core.httpclient.OkHttpTransport;
import com.lark.oapi.core.request.RequestOptions;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.okhttp.OkHttpClient;
import com.lark.oapi.service.docx.v1.model.CreateDocumentReq;
import com.lark.oapi.service.docx.v1.model.CreateDocumentReqBody;
import com.lark.oapi.service.docx.v1.model.CreateDocumentResp;
import com.lark.oapi.service.drive.v1.model.*;
import com.sun.xml.txw2.output.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ExposedFields;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import javax.annotation.Resource;
import javax.xml.transform.Result;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.net.Proxy;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.lark.oapi.Client;
import com.lark.oapi.core.cache.LocalCache;
import com.lark.oapi.core.enums.BaseUrlEnum;
import com.lark.oapi.core.httpclient.OkHttpTransport;
import com.lark.oapi.core.response.RawResponse;
import com.lark.oapi.core.token.AccessTokenType;
import com.lark.oapi.okhttp.OkHttpClient;

import java.net.Proxy;
import java.util.concurrent.TimeUnit;

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
    @Value("${feishuAppSecret}")
    String feishuAppSecret;
    @Value("${feishuAppId}")
    String feishuAppId;

    public static void main(String[] args) {
//        Client client= Client.newBuilder(feishuAppId,feishuAppSecret)
//                .marketplaceApp() // 设置 app 类型为商店应用
//                .openBaseUrl(BaseUrlEnum.FeiShu) // 设置域名，默认为飞书
//                .helpDeskCredential("helpDeskId","helpDeskSecret") // 服务台应用才需要设置
//                .requestTimeout(3, TimeUnit.SECONDS) // 设置httpclient 超时时间，默认永不超时
//                .disableTokenCache() // 禁用token管理，禁用后需要开发者自己传递token
//                .logReqAtDebug(true) // 在 debug 模式下会打印 http 请求和响应的 headers,body 等信息。
//                .build();
    }

    @ApiOperation(value = "clientFeiShu", notes = "clientFeiShu")
    @RequestMapping(value = "/clientFeiShu", method = RequestMethod.POST)
    public Object clientFeiShu() {
        Client client = Client.newBuilder(feishuAppId, feishuAppSecret)
                .marketplaceApp() // 设置 app 类型为商店应用
                .openBaseUrl(BaseUrlEnum.FeiShu) // 设置域名，默认为飞书
                .helpDeskCredential("helpDeskId", "helpDeskSecret") // 服务台应用才需要设置
                .requestTimeout(3, TimeUnit.SECONDS) // 设置httpclient 超时时间，默认永不超时
                .disableTokenCache() // 禁用token管理，禁用后需要开发者自己传递token
                .logReqAtDebug(true) // 在 debug 模式下会打印 http 请求和响应的 headers,body 等信息。
                .build();
        log.info("client {}", client);
//        client.docx().document().
        return ReturnT.success(client);
    }

    @ApiOperation(value = "clientFeiShu", notes = "clientFeiShu")
    @RequestMapping(value = "/BatchQueryMetaReq", method = RequestMethod.POST)
    public Object BatchQueryMetaReq() throws Exception {
        // 构建client
        Client client = Client.newBuilder(feishuAppId, feishuAppSecret).build();

        // 创建请求对象
        BatchQueryMetaReq req = BatchQueryMetaReq.newBuilder()
                .userIdType("user_id")
                .metaRequest(MetaRequest.newBuilder()
                        .requestDocs(new RequestDoc[]{})
                        .withUrl(false)
                        .build())
                .build();

        // 发起请求
        BatchQueryMetaResp resp = client.drive().meta().batchQuery(req);

        // 处理服务端错误
        if (!resp.success()) {
            System.out.println(String.format("code:%s,msg:%s,reqId:%s"
                    , resp.getCode(), resp.getMsg(), resp.getRequestId()));
            return resp;
        }

        // 业务数据处理
        System.out.println(Jsons.DEFAULT.toJson(resp.getData()));
        return resp;
    }

    @ApiOperation(value = "clientFeiShu", notes = "clientFeiShu")
    @RequestMapping(value = "/ListFile", method = RequestMethod.POST)
    public Object ListFile() throws Exception {
        // 构建client
        Client client = Client.newBuilder(feishuAppId, feishuAppSecret)
//                .disableTokenCache()
                //如需SDK自动管理租户Token的获取与刷新,可删除该行
                .build();

//        "D:\download\hugDown_mrm8488_distill-bert-base-spanish-wwm-cased-finetuned-spa-squad2-es.txt"
//        ListFileResp.

        ListFileReq req = ListFileReq.newBuilder().build();
//                .folderToken()
        // 创建请求对象
        // 发起请求
        // 如开启了Sdk的token管理功能，就无需调用 RequestOptions.newBuilder().tenantAccessToken("t-xxx").build()来设置租户token了
        ListFileResp resp = client.drive().file().list(req);
//        client.drive().file().uploadAll()
        // 处理服务端错误
        if (!resp.success()) {
            System.out.println(String.format("code:%s,msg:%s,reqId:%s", resp.getCode(), resp.getMsg(), resp.getRequestId()));
            return resp;
        }

        // 业务数据处理
        System.out.println(Jsons.DEFAULT.toJson(resp.getData()));
        return resp;
    }

    @ApiOperation(value = "clientFeiShu", notes = "clientFeiShu")
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public void init() {
        Proxy proxy = Proxy.NO_PROXY;  // 自定义代理服务器
        Client client = Client.newBuilder("appId", "appSecret")
                .openBaseUrl(BaseUrlEnum.FeiShu) // 设置域名，默认为飞书，支持重载String，设置私有部署飞书服务器
                .requestTimeout(3, TimeUnit.MINUTES) // 设置httpclient 超时时间，默认永不超时
                .httpTransport(new OkHttpTransport(
                        new OkHttpClient().newBuilder()
                                .readTimeout(3, TimeUnit.MINUTES) // 另一种方式设置超时时间
                                .callTimeout(3, TimeUnit.MINUTES) // 另一种方式设置超时时间
                                .proxy(proxy)  // 设置使用代理服务器访问飞书服务器
                                .build()
                ))
                .tokenCache(LocalCache.getInstance()) // 使用SDK自带本地缓存, 无需提前获取tenant_access_token
                .logReqAtDebug(true) // 在 debug 模式下会打印 http 请求和响应的 headers,body 等信息。
                .build();
    }

    @ApiOperation(value = "clientFeiShu", notes = "clientFeiShu")
    @RequestMapping(value = "/CreateDocumentResp", method = RequestMethod.POST)
    public Object CreateDocumentResp() throws Exception {
        // 构建client
        Client client = Client.newBuilder(feishuAppId, feishuAppSecret).build();

        // 发起请求
        CreateDocumentResp resp = client.docx().document()
                .create(CreateDocumentReq.newBuilder()
                        .createDocumentReqBody(CreateDocumentReqBody.newBuilder()
                                .title("title")
                                .folderToken("fldcniHf40Vcv1DoEc8SXeuA0Zd")
                                .build())
                        .build()
                );


        return resp;
//        // 处理服务端错误
//        if (!resp.success()) {
//            log.info("resp {}",resp);
////            log.info(resp);
//            System.out.println(String.format("code:%s,msg:%s,reqId:%s"
//                    , resp.getCode(), resp.getMsg(), resp.getRequestId()));
//            return  ReturnT.error("jsonStr");
//        }
//
//        String jsonStr = Jsons.DEFAULT.toJson(resp.getData());
//        // 业务数据处理
//        System.out.println("jsonStr");
//        System.out.println(jsonStr);
//        return ReturnT.success(jsonStr);
    }

    // 构建client
    Client client = Client.newBuilder(feishuAppId, feishuAppSecret)
            .disableTokenCache() //如需SDK自动管理租户Token的获取与刷新,可删除该行
            .build();

    @ApiOperation(value = "clientFeiShu", notes = "clientFeiShu")
    @RequestMapping(value = "/listFile", method = RequestMethod.POST)
    public Object listFile() throws Exception {
        // 构建client
        Client client = Client.newBuilder(feishuAppId, feishuAppSecret)
//                .disableTokenCache()
                //如需SDK自动管理租户Token的获取与刷新,可删除该行
                .build();

        // 创建请求对象
        // 发起请求
        // 如开启了Sdk的token管理功能，就无需调用
        // RequestOptions.newBuilder().tenantAccessToken("t-xxx").build()来设置租户token了
//        Need pass an access token value
//        client.drive().file().list()
        ListFileResp resp = client.drive().file().list(ListFileReq.newBuilder().build());
//		ListFileResp resp = client.drive().file().list(RequestOptions.newBuilder()
//			.tenantAccessToken("")
//			.build());

        // 处理服务端错误
        if (!resp.success()) {
            System.out.println(String.format("code:%s,msg:%s,reqId:%s",
                    resp.getCode(), resp.getMsg(), resp.getRequestId()));
            return resp;
        }

        // 业务数据处理
        System.out.println(Jsons.DEFAULT.toJson(resp.getData()));
        return resp;
    }

    @Value("${spring.datasource.url}")
    String springDatasourceUrl;
    @Resource
    ToolDAO toolDAO;

    void genByMap(Map<String, String> map) {
        String className = map.get("className");
        String classURL = map.get("classURL");
        String classDesc = map.get("classDesc");
        String methodName = map.get("methodName");
        String methodDesc = map.get("methodDesc");
        String methodURL = map.get("methodURL");
        String requestType = map.get("requestType");
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

    @Resource
    MongoTemplate mongoTemplate;
//    @RequestBody LookupOperation lookupOperationDto

    public static List<Document> mongoJoin(String localDoc,
                                    String otherDoc, String localField
            , String foreignField
            , MongoTemplate mongoTemplate) {
        String joinDoc = "joinDoc";
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from(otherDoc)
                .localField(localField)
                .foreignField(foreignField)
                .as(joinDoc);
        Criteria criteria = Criteria.where(joinDoc).ne(Collections.emptyList());

        Aggregation aggregation = Aggregation.newAggregation(
                lookupOperation
                , Aggregation.match(criteria)
        );
        return mongoTemplate.aggregate(aggregation, localDoc, Document.class).getMappedResults();
    }

    @RequestMapping(value = "/count", method = RequestMethod.POST)
    public Object count( @RequestBody MongoReq mongoReq){
//        MongoReq
        String collectionName = mongoReq.getCollectionName();
        long count = mongoTemplate.count(new Query(), collectionName);
         return ReturnT.success(count);
    }
    @RequestMapping(value = "/joinJobCollections", method = RequestMethod.POST)
    public List<Document> joinJobCollections() {
//        String colAName = "col_a";
//        String colBName = "col_b";
//
//        Aggregation agg = Aggregation.newAggregation(
//                Aggregation.lookup(colBName, "recommendData.entityId", "entryId", "joinedData"),
//                Aggregation.unwind("joinedData"),
//                Aggregation.project(
//                        Aggregation.fields(
//                                Aggregation.exclude("_id"),
////                                Aggregation.com
//                                Aggregation.computed("entityId", "$recommendData.entityId"),
//                                Aggregation.include("otherFieldFromColA"),
//                                Aggregation.computed("joinedFieldFromColB", "$joinedData.fieldFromColB")
//                        )
//                )
//        );

        //设置lookup

//        ExposedFields fields = lookupOperationDto.getFields();
//        fields.getField()
//        LookupOperation lookupOperation = LookupOperation.newLookup()
//                .from("nowcoder_resp")
//                .localField("recommendData.entityId")
//                .foreignField("entryId").as("userDoc");

//        LookupOperation lookupOperation = LookupOperation.newLookup()
//                .from("nowcoder_resp")
//                .localField("recommendData.entityId")
//                .foreignField("entryId")
//                .as("userDoc")
//                .pipeline(
//                        Aggregation.pipeline(
//                                Aggregation.match(Criteria.where("userDoc").ne(null))
//                        )
//                );

        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("nowcoder_resp")
                .localField("recommendData.entityId")
                .foreignField("entryId")
                .as("userDoc");
        String userDoc = "userDoc";
//        nowcoder_resp

        List<Document> documents = MongoUtil.mongoJoin("nowcoder"
                , "nowcoder_resp"
                , "recommendData.entityId"
                , "entryId"
                , mongoTemplate);

//        Aggregation agg = Aggregation.newAggregation(
//                lookupOperation,
//                Aggregation.match(Criteria.where("userDoc").ne(null))
//        );

//        List<Document> results = mongoTemplate.aggregate(agg, "col_a", Document.class).getMappedResults();


        //这里分三个部分，先lookup；结果再用project筛选，如果不筛选user里面所有内容，包括密码都会输出；最后sort排序
//        Aggregation.newAggregation()
//        ProjectionOperation project = project("id", "demographic", "sizes", "userDoc.realname");
//        ScriptObject demographic = sort(Sort.Direction.ASC, "demographic");
//        ProjectionOperation as = project("recommendData.entityId")
//                .and("entityId").previousOperation().andExclude("_id")
//                .andInclude("otherFieldFromColA")
//                .and("joinedData.fieldFromColB").as("joinedFieldFromColB");
//        ExposedFields fields = as.getFields();
//        mongo

//        Criteria criteria = new Criteria().andOperator(
//                Criteria.where("fieldName").exists(true),
//                Criteria.where("fieldName").ne(Collections.emptyList())
//        );
        Criteria criteria = Criteria.where(userDoc).ne(Collections.emptyList());
//        Criteria criteria = Criteria.where(userDoc)
//                .exists(true).and(userDoc).ne(Collections.emptyList());
//        'userDoc' expression specified as 'userDoc : Document{{$ne=[]}}'.
//        Criteria already contains 'userDoc : Document{{$exists=true}}'.
        Aggregation aggregation = Aggregation.newAggregation(
                lookupOperation
//                ,Aggregation.match(Criteria.where(userDoc).ne(null))
//                project,
//                fields,
//                ,Aggregation.sort(Sort.Direction.ASC, "demographic")
//               criteria
                , Aggregation.match(criteria)
        );


//        Aggregation agg = Aggregation.newAggregation(
//                Aggregation.match(criteria),
//                // 其他聚合操作
//        );

//        List<SomeEntity> result = mongoTemplate.aggregate(agg, SomeEntity.class).getMappedResults();


//        Query query = new Query(criteria);
//————————————————
//        版权声明：本文为CSDN博主「野人204666」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/u012565113/article/details/82978095
//


//        Aggregation.addFields()
//                .addFieldWithValue("userDocNotEmpty")
//                .withValue(
//                        // 判断数组长度是否大于0
//                        new Document("$gt", Arrays.asList(new Document("$size", "$userDoc"), 0))
//                );

//        Aggregation agg = Aggregation.newAggregation(
//                lookupOperation, Aggregation.addFields().addField(
//                        "userDocNotEmpty",
//                        new Document("$gt", Arrays.asList(new Document("$size", "$userDoc"), 0))
//                ),
////                Aggregation.match(Criteria.where("userDocNotEmpty").is(true))
//                Aggregation.match(Criteria.where("userDocNotEmpty").is(true))
//        );

//        List<Document> results = mongoTemplate.aggregate(agg, "col_a", Document.class).getMappedResults();


        String colAName = "col_a";
        String colBName = "col_b";
//        ProjectionOperation as = project("recommendData.entityId").and("entityId")
//                .previousOperation().andExclude("_id")
//                .andInclude("otherFieldFromColA")
//                .and("joinedData.fieldFromColB").as("joinedFieldFromColB");
//        Aggregation agg = Aggregation.newAggregation(
//                Aggregation.lookup(colBName, "recommendData.entityId", "entryId", "joinedData"),
//                Aggregation.unwind("joinedData"),
//                project(
//                        as
//                )
//        );
//正式查询
//        List<OrderQsDemographicSize> list = MongoTemplate
//                .aggregate(aggregation, "orderQsDemographicSize", OrderQsDemographicSize.class).getMappedResults();
//————————————————
//        版权声明：本文为CSDN博主「野人204666」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/u012565113/article/details/82978095
        return mongoTemplate.aggregate(aggregation, "nowcoder", Document.class).getMappedResults();
//        return mongoTemplate.aggregate(agg, colAName, Document.class).getMappedResults();
    }

    @Autowired
    WebApplicationContext applicationContext;

    @ApiOperation(value = "getAllURL", notes = "getAllURL")
    @RequestMapping(value = "/getAllURL", method = RequestMethod.POST)
    public Object getAllURL() throws FileNotFoundException {
        String postMethodRows = "";
        List<Map<String, String>> resultList = new ArrayList<>();


//        Scanner scanner=new Scanner(new File(tplPath));
//        String out="";
//        while (scanner.hasNextLine()) {
//            scanner.nextLine();
//        }
        RequestMappingHandlerMapping requestMappingHandlerMapping =
                applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> mappingInfoHandlerMethodEntry : map.entrySet()) {
            Map<String, String> resultMap = new LinkedHashMap<>();

            RequestMappingInfo requestMappingInfo = mappingInfoHandlerMethodEntry.getKey();
            HandlerMethod handlerMethod = mappingInfoHandlerMethodEntry.getValue();

            resultMap.put("className", handlerMethod.getMethod().getDeclaringClass().getName()); // 类名
            Annotation[] parentAnnotations = handlerMethod.getBeanType().getAnnotations();
            for (Annotation annotation : parentAnnotations) {
                if (annotation instanceof Api) {
                    Api api = (Api) annotation;
                    resultMap.put("classDesc", api.value());
                } else if (annotation instanceof RequestMapping) {
                    RequestMapping requestMapping = (RequestMapping) annotation;
                    if (null != requestMapping.value() && requestMapping.value().length > 0) {
                        resultMap.put("classURL", requestMapping.value()[0]);//类URL
                    }
                }
            }
            String methodName = handlerMethod.getMethod().getName();
            resultMap.put("methodName", handlerMethod.getMethod().getName()); // 方法名
            Annotation[] annotations = handlerMethod.getMethod().getDeclaredAnnotations();
//            java 17 模板字符串
//            String postMethod=  """
//                    const ${methodName}= ()=>{
//                        postData(`http://localhost:8889/all/${methodName}`,{
//                        "table_schema": "hotel"
//                        })
//                          .then(res => {
//                            console.log(res); // JSON data parsed by `data.json()` call
//                          });
//
//                        }
//                    """;
            String postMethod = "const ${methodName}= ()=>{\n" +
                    "                        postData(`http://localhost:8889/all/${methodName}`,{\n" +
                    "                        \"table_schema\": \"hotel\"\n" +
                    "                        })\n" +
                    "                          .then(res => {\n" +
                    "                            console.log(res); // JSON data parsed by `data.json()` call\n" +
                    "                          });\n" +
                    "                                        \n" +
                    "                        }";

            postMethod = postMethod.replace("${methodName}", methodName);
            postMethodRows += postMethod + "\n";
//            System.out.println("postMethod");
            System.out.println(postMethod);
            if (annotations != null) {
                // 处理具体的方法信息
                for (Annotation annotation : annotations) {
                    if (annotation instanceof ApiOperation) {
                        ApiOperation methodDesc = (ApiOperation) annotation;
                        String desc = methodDesc.value();
                        resultMap.put("methodDesc", desc);//接口描述
                    }
                }
            }
            PatternsRequestCondition p = requestMappingInfo.getPatternsCondition();
            for (String url : p.getPatterns()) {
                resultMap.put("methodURL", url);//请求URL
//                "postData({quotation}{url}{quotation},dataPost).then(res=>{})"
//                        .replace("{url}",url)
//                        .replace("{quotation}",'"'+"")
//                ;
            }

//            url    "methodURL": "/rgb/delete",

            RequestMethodsRequestCondition methodsCondition = requestMappingInfo.getMethodsCondition();
            for (RequestMethod requestMethod : methodsCondition.getMethods()) {
                resultMap.put("requestType", requestMethod.toString());//请求方式：POST/PUT/GET/DELETE
            }
            resultList.add(resultMap);
        }
        String tplPath = "D:\\proj\\springBoot\\code-gen-starp\\src\\main\\resources\\genCodeTemplate\\api\\postman_test.html";
//        FileUtil.readResourceFileData()
        String tpl = FileUtil.readAll(tplPath);
        String code = tpl.replace("{postMethodRows}", postMethodRows);
//        Path fileName = new Path("1").getFileName();
//        java  用 字符串 创建一个 Path
//        String pathString = "C:/path/to/file.txt";
        Path path = Paths.get(tplPath);

//        FileUtil.writeCode();
//        {postMethodRows}
        return resultList;
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
        StringBuilder colProperties = new StringBuilder();
        Set<String> set = new HashSet<>();
        List<String> table_names = maps.stream().map(o -> {
            String table_name = (String) o.get("table_name");
            return table_name;
        }).collect(Collectors.toList());
        log.info("table_names {}", table_names);
        Map<String, List<ColumnInfo>> mapTableCols = new HashMap<>();
        String ALTER_add_tenant_id_out_str = "";
        String ALTER_MODIFY_tenant_id_str = "";
        String table_names_out_str = "";
        for (String table_name : table_names) {
            String table_nameOne = "\"{table_name}\",".replace("{table_name}", table_name);
            table_names_out_str += table_nameOne + "\n";

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
            String ALTER_add_tenant_id = "ALTER TABLE `{table_name}`\n" +
                    "ADD COLUMN `tenant_id`  int NULL ;";
            String ALTER_MODIFY_tenant_id = "ALTER TABLE `{table_name}`\n" +
                    "MODIFY COLUMN `tenant_id`  varchar(20) NULL DEFAULT NULL;";

            ALTER_add_tenant_id = ALTER_add_tenant_id
//                    .replace()
                    .replace("{table_name}", table_name);
            ALTER_add_tenant_id_out_str += ALTER_add_tenant_id + "\n";
            ALTER_MODIFY_tenant_id = ALTER_MODIFY_tenant_id
                    .replace("{table_name}", table_name);
            ALTER_add_tenant_id_out_str += ALTER_add_tenant_id + "\n";
            ALTER_MODIFY_tenant_id_str += ALTER_MODIFY_tenant_id + "\n";
            log.info("ALTER_add_tenant_id {}", ALTER_add_tenant_id);
            String camelCaseTable = StringUtils.underlineToCamelCase(table_name);
            String tablesRow = "\"#camelCaseTable#\":\"#camelCaseTable#\",\n";
            tablesRow = tablesRow.replace("#camelCaseTable#", camelCaseTable);
            tablesCodeRows.append(tablesRow);

            String colPropertiesRow = "else if (type === '#camelCaseTable#') {\n" +
                    "              this.properties = getDict(#camelCaseTable#Columns);\n" +
                    "          }\n";
            colPropertiesRow = colPropertiesRow.replace("#camelCaseTable#", camelCaseTable);
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
        log.info("table_names_out_str {}", table_names_out_str);
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
        StringBuilder colProperties = new StringBuilder();
        Set<String> set = new HashSet<>();
        List<String> table_names = maps.stream().map(o -> {
            String table_name = (String) o.get("table_name");
            return table_name;
        }).collect(Collectors.toList());
//        log.info("table_names {}",table_names);
        Map<String, List<ColumnInfo>> mapTableCols = new HashMap<>();
        String ALTER_add_tenant_id_out_str = "";
        String ALTER_MODIFY_tenant_id_str = "";
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
            String ALTER_add_tenant_id = "ALTER TABLE `{table_name}`\n" +
                    "ADD COLUMN `tenant_id`  int NULL ;";
            String ALTER_MODIFY_tenant_id = "ALTER TABLE `{table_name}`\n" +
                    "MODIFY COLUMN `tenant_id`  varchar(20) NULL DEFAULT NULL;";

            ALTER_add_tenant_id = ALTER_add_tenant_id
//                    .replace()
                    .replace("{table_name}", table_name);
            ALTER_add_tenant_id_out_str += ALTER_add_tenant_id + "\n";
            ALTER_MODIFY_tenant_id = ALTER_MODIFY_tenant_id
                    .replace("{table_name}", table_name);
            ALTER_add_tenant_id_out_str += ALTER_add_tenant_id + "\n";
            ALTER_MODIFY_tenant_id_str += ALTER_MODIFY_tenant_id + "\n";
//            log.info("ALTER_add_tenant_id {}",ALTER_add_tenant_id);
//            StringUtils.rm 删掉前面的 t_
//            pre
//            CodeGen.
            String table_name_removePrefix = StrUtil.removePrefix(
                    table_name, new String[]{TableInfo.tablePreffix});
//            String camelCaseTable = StringUtils.underlineToCamelCase(table_name);

            String camelCaseTable = StringUtils.underlineToCamelCase(table_name);
            String tablesRow = "\"#camelCaseTable#\":\"#camelCaseTable#\",\n";
            tablesRow = tablesRow.replace("#camelCaseTable#", camelCaseTable);

            tablesCodeRows.append(tablesRow);
            tablesCodeRows.append("\"#table_name_removePrefix#\":\"#table_name_removePrefix#\",\n"
                    .replace("#table_name_removePrefix#", table_name_removePrefix));
            tablesCodeRows.append("\"#table_name#\":\"#table_name#\",\n"
                    .replace("#table_name#", table_name));

            String colPropertiesRow = "else if (type === '#camelCaseTable#') {\n" +
                    "              this.properties = getDict(#camelCaseTable#Columns);\n" +
                    "          }\n";
            colPropertiesRow = colPropertiesRow.replace("#camelCaseTable#", camelCaseTable);
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
