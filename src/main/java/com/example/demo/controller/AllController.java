package com.example.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Acc;
import com.example.demo.entity.AskTypeReq;
//import com.example.demo.entity.ReturnT;
import com.example.demo.repository.AccRepository;
import com.example.demo.repository.ToolDAO;
import com.example.demo.util.*;
//import com.example.demo.util.MongoReq;
import com.example.demo.util.FileUtil;
import com.example.demo.util.StringUtils;
import com.example.demo.util.codeGen.CodeGen;
import com.example.demo.util.codeGen.ColumnInfo;
import com.example.demo.util.codeGen.Table;
import com.example.demo.util.codeGen.TableInfo;
import com.google.gson.JsonObject;
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
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import com.sangupta.har.HarUtils;
import com.sangupta.har.model.Har;
import com.sangupta.har.model.HarEntry;
import com.sun.xml.txw2.output.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.bson.Document;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.web.JsonPath;
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
import java.io.IOException;
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
import top.starp.util.*;
import top.starp.util.HttpRequest;
import top.starp.util.JsonUtil;
import top.starp.util.MongoUtil;

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

//    @Value("")
    @Value("${gaoDeMapKey}")
    String gaoDeMapKey;
    @ApiOperation(value = "gaoDeMapWakling", notes = "gaoDeMapWakling")
    @RequestMapping(value = "/gaoDeMapWakling", method = RequestMethod.POST)
    public Object gaoDeMapWakling() {
        String origin="104.07,30.67";//出发点经纬度
        String destination="104.46,29.23";//目的地经纬度
//        120.149343,30.335056
//        String key="2ace948ef129************b0774750";//高德用户key
//        String key="2ace948ef129************b0774750";//高德用户key
        MapNavUtil mapResult=new MapNavUtil(origin, destination, gaoDeMapKey);
//        System.out.println(mapResult.getResults().toString());
//        System.out.println(mapResult.getResults().toString());
//        System.out.println("mapResult.walking()");
//        System.out.println(mapResult.walking());
//        MapNavResults walkingRes = mapResult.walking();
        JSONObject walkingRes = mapResult.walkingReq();
//        walkingReq

        log.info("walkingRes {}",walkingRes);
        Document document = new Document();
        document.put("origin",origin);
        document.put("destination",destination);
        document.put("walkingRes",walkingRes);
//        gao_de_map
//        mongoTemplate.
//        MongoUtil.in
        Document insert = mongoTemplate.insert(document, k.gao_de_map);
        return ReturnT.success(insert);
    }

    @ApiOperation(value = "walking", notes = "walking")
    @RequestMapping(value = "/walking", method = RequestMethod.POST)
    public Object walking( @RequestBody  GeoReq geoReq) {
//        WalkTwoGet()
//        String origin="104.07,30.67";//出发点经纬度
//        String destination="104.46,29.23";//目的地经纬度
//        120.149343,30.335056
//        String key="2ace948ef129************b0774750";//高德用户key
//        String key="2ace948ef129************b0774750";//高德用户key
//        MapNavUtil mapResult=new MapNavUtil(origin, destination, gaoDeMapKey);
//        System.out.println(mapResult.getResults().toString());
//        System.out.println(mapResult.getResults().toString());
//        System.out.println("mapResult.walking()");
//        System.out.println(mapResult.walking());
//        MapNavResults walkingRes = mapResult.walking();
//        JSONObject walkingRes = mapResult.walkingReq();

        JSONObject walkingRes = MapNavUtil.walking(geoReq, gaoDeMapKey);
//        walkingReq

        log.info("walkingRes {}",walkingRes);
        Document document = new Document();
//        document.put("origin",origin);
//        document.put("destination",destination);
        document.put("walkingRes",walkingRes);
        document.put("geoReq",geoReq);
        document.put("date",new Date());
//        gao_de_map
//        mongoTemplate.
//        MongoUtil.in
        Document insert = mongoTemplate.insert(document, k.gao_de_map);
        return ReturnT.success(insert);
    }

    /**
     *  * 搜索服务-关键字查询
     *      * 56 已用 56%
     *      * 100
     *      *
     * @param geoReq
     * @return
     */
    @ApiOperation(value = "geocode_geo", notes = "geocode_geo")
    @RequestMapping(value = "/place_text", method = RequestMethod.POST)
    public Object  place_text(@RequestBody GeoReq geoReq){
//        geoReq.place_text()
        geoReq.setApplicationKey(gaoDeMapKey);
        JSONObject jsonObject = HttpRequest.get(geoReq.place_text());

        geoReq.setApplicationKey("");
        Resp build = Resp.builder().resp(jsonObject).geoReq(geoReq).build();

//        geoReq.
//        WalkTwo
//        JSONObject insert = MongoUtil.insert(jsonObject, k.place_text, mongoTemplate);
//        JSONObject insert = MongoUtil.insert(jsonObject, k.place_text_search, mongoTemplate);
        Resp insert = MongoUtil.insert(build, k.place_text_search, mongoTemplate);

        return ReturnT.success(insert);
//        mongoTemplate.insert(jsonObject, k.WalkTwo);
    }

    @ApiOperation(value = "ColumnInfoInsert", notes = "ColumnInfoInsert")
    @RequestMapping(value = "/ColumnInfoInsert", method = RequestMethod.POST)
    public Object  ColumnInfoInsert(@RequestBody GeoReq geoReq){
//        WalkTwoGet()
//        mongoTemplate
        ColumnInfo build1 = ColumnInfo.builder()
                .COLUMN_NAME(k.apiName)
                .COLUMN_TYPE(k.string).build();

        ColumnInfo insert = MongoUtil.insert(build1, k.column_info, mongoTemplate);
        return insert;
    }


    /**

     * 输入提示
     * 50 已用 1%
     * 5000
     * @param geoReq
     * @return
     */
    @ApiOperation(value = "geocode_geo", notes = "geocode_geo")
    @RequestMapping(value = "/assistant_input_tips", method = RequestMethod.POST)
    public Object  assistant_input_tips(@RequestBody GeoReq geoReq){
        geoReq.setApplicationKey(gaoDeMapKey);
        JSONObject resp = HttpRequest.get(geoReq.assistant_input_tips());
        geoReq.setApplicationKey("");
        Resp build = Resp.builder().resp(resp).geoReq(geoReq).build();
        Resp insert = MongoUtil.insert(build, k.assistant_input_tips, mongoTemplate);
        return ReturnT.success(insert);
    }
//    assistant_input_tips

    @ApiOperation(value = "WalkTwoGet", notes = "geocode_geo")
    @RequestMapping(value = "/WalkTwoGet", method = RequestMethod.POST)
    public Object  WalkTwoGet(@RequestBody GeoReq geoReq){
        MongoReq mongoReq = new MongoReq();
        mongoReq.setCollectionName(k.WalkTwo);
//        mongoReq.set
        List<Map> maps = MongoUtil.find(mongoReq, mongoTemplate);
        return  maps;
    }

    @ApiOperation(value = "approve_list", notes = "geocode_geo")
    @RequestMapping(value = "/approve_list", method = RequestMethod.POST)
    public Object  approve_list(@RequestBody MongoReq mongoReq){
//        MongoReq mongoReq = new MongoReq();

//        mongoReq.setCollectionName(k.approve_list);
        mongoReq.setCollectionName(k.large_file_log);
//        mongoReq.setOps(Op.desc(k.id));


//        mongoReq.ops(Op.desc(k.id));
//        mongoReq.ops(Op.desc(k.file_size));
        mongoReq.sortBys(Op.desc(k.file_size));


//        mongoReq.set
        return  MongoUtil.findDocuments(mongoReq,mongoTemplate);
//        return MongoUtil.pageDocuments(mongoReq,mongoTemplate);
//        List<Map> maps = MongoUtil.find(mongoReq, mongoTemplate);
//        return  maps;
    }

    @ApiOperation(value = "large_file_log", notes = "geocode_geo")
    @RequestMapping(value = "/large_file_log", method = RequestMethod.POST)
    public Object  large_file_log(@RequestBody MongoReq mongoReq){
//        MongoReq mongoReq = new MongoReq();

//        mongoReq.setCollectionName(k.approve_list);
        mongoReq.setCollectionName(k.large_file_log);
//        mongoReq.setOps(Op.desc(k.id));

//        mongoReq.ops(Op.desc(k.id));
//        mongoReq.ops(Op.desc(k.file_size));
//        mongoReq.sortBys(Op.desc(k.file_size));
        mongoReq.sortBys(
                Op.desc(k.orderBy)
                ,Op.asc(k.file_size)
            );

//        mongoReq.set
        return  MongoUtil.findDocuments(mongoReq,mongoTemplate);
//        return MongoUtil.pageDocuments(mongoReq,mongoTemplate);
//        List<Map> maps = MongoUtil.find(mongoReq, mongoTemplate);
//        return  maps;
    }

    @ApiOperation(value = "AskTypeReq", notes = "geocode_geo")
    @RequestMapping(value = "/AskTypeReq", method = RequestMethod.POST)
    public Object  AskTypeReq(@RequestBody AskTypeReq askTypeReq){

        String askType = askTypeReq.getAskType();

//        MongoReq mongoReq = new MongoReq();
//
////        mongoReq.setCollectionName(k.approve_list);
//        mongoReq.setCollectionName(k.askTrain);
////        mongoReq.setOps(Op.desc(k.id));
//
////        mongoReq.ops(Op.desc(k.id));
////        mongoReq.ops(Op.desc(k.file_size));
////        mongoReq.sortBys(Op.desc(k.file_size));
//        mongoReq.sortBys(
//                Op.desc(k.orderBy)
//                ,Op.asc(k.file_size)
//        );
//        AskTypeReq
        return  ReturnT.success(
                typeShowGet(askType)
        );
//        return
//        mongoReq.set
//        return  MongoUtil.findDocuments(mongoReq,mongoTemplate);
//        return MongoUtil.pageDocuments(mongoReq,mongoTemplate);
//        List<Map> maps = MongoUtil.find(mongoReq, mongoTemplate);
//        return  maps;
    }

    String  typeShowGet(String  askType) {
//        askTrain.getAskText();
        MongoReq mongoReq1 = new MongoReq();
        mongoReq1.ops(Op.equals(k.category,askType));
        mongoReq1.setCollectionName(k.askTrain);
//        mongoReq1.setPageSize(1);
        mongoReq1.setPageSize(20);
//        mongoReq1.setPageNumber(1);
//        mongoReq1.setPageNumber(0);
        mongoReq1.setPageNumber(MongoReq.FIRST);
        mongoReq1.sortBys(Op.desc(k.orderBy)
                ,Op.desc(k._id));
        List<Document> documents = MongoUtil.findDocuments(mongoReq1, mongoTemplate);
        if(documents==null||documents.size()==0){
//            问题的 显示名字
            return null;
        }
        Document document = documents.get(0);
        String typeShow = document.getString(k.typeShow);
        return typeShow;

    }


    @ApiOperation(value = "insert_data", notes = "geocode_geo")
    @RequestMapping(value = "/insert_data", method = RequestMethod.POST)
    public Object  insert_data(@RequestBody MongoReq mongoReq){

        MongoReq insert1 = MongoUtil.insert(mongoReq, mongoReq.getCollectionName(), mongoTemplate);
        return ReturnT.success(insert1);
    }

    @ApiOperation(value = "pageDocuments", notes = "geocode_geo")
    @RequestMapping(value = "/pageDocuments", method = RequestMethod.POST)
    public Object  pageDocuments(@RequestBody MongoReq mongoReq){
//        MongoUtil.update
//        MongoTemplate update java
//        mongoTemplate.updateMulti()

//        MongoUtil.pageDocuments()

        Page<Document> page = MongoUtil.page(mongoReq, mongoTemplate, Document.class);
        return ReturnT.success(page);
    }

    @ApiOperation(value = "findDistinct", notes = "geocode_geo")
    @RequestMapping(value = "/findDistinct", method = RequestMethod.POST)
    public Object  findDistinct(@RequestBody MongoReq mongoReq){
//        List<Document> distinct = MongoUtil.findDistinct(mongoReq, mongoTemplate, Document.class);
        List<String> distinct = MongoUtil.findDistinct(mongoReq, mongoTemplate, String.class);
        return ReturnT.success(distinct);
    }

    @ApiOperation(value = "countField", notes = "geocode_geo")
    @RequestMapping(value = "/countField", method = RequestMethod.POST)
    public Object  countField(@RequestBody MongoReq mongoReq){
//        List<Op> ops = mongoReq.getOps();
//        for (Op op : ops) {
//            String field = op.getField();
//            String op1 = op.getOp();
//            if (k.count.equals(op1)) {
//                return field;
//            }
//        }

//        String countField = mongoReq.getCountField();
//        Long aLong = countField(countField);
//        mongoReq.set
        mongoReq.setPageSizeIfAbsent(9999999);
//        String countField = mongoReq.getCountField();

        List<Map> countList = MongoUtil.countField(mongoReq, mongoTemplate);








//        List<Map> maps = MongoUtil.find(mongoReq, mongoTemplate);
////        Map<String, List<Map<?, ?>>> stringListMap = ListUtil.groupBy(maps, k.apiName);
//        Map<String, List<Map<?, ?>>> stringListMap = ListUtil.groupBy(maps,countField);
//
//        List<Map>countList=new ArrayList<>();
//        Map<String ,Integer>coutMap=new HashMap<>();
//        for (Map.Entry<String, List<Map<?, ?>>> stringListEntry : stringListMap.entrySet()) {
//            List<Map<?, ?>> value = stringListEntry.getValue();
//            String key = stringListEntry.getKey();
//            coutMap.put(key,value.size());
//            int size = value.size();
//
//            Map map=new HashMap();
//            map.put(k.field,key);
//            map.put(k.count,size);
//            countList.add(map);
//        }
//
//




//





//
////        List<Document> distinct = MongoUtil.findDistinct(mongoReq, mongoTemplate, Document.class);
//        List<String> distinct = MongoUtil.findDistinct(mongoReq, mongoTemplate, String.class);
//        return ReturnT.success(  countFieldDo(mongoReq));
//        return ReturnT.success( stringListMap);
//        return ReturnT.success( coutMap);
        return ReturnT.success( countList);
    }


    @ApiOperation(value = "countFieldCntGreater2", notes = "geocode_geo")
    @RequestMapping(value = "/countFieldCntGreater2", method = RequestMethod.POST)
    public Object  countFieldCntGreater2(@RequestBody MongoReq mongoReq){
        mongoReq.setPageSizeIfAbsent(9999999);

        List<Map> countList = MongoUtil.countField(mongoReq, mongoTemplate);

        for (Map map : countList) {
//            Integer integer = (Integer) map.get(k.count);
            Integer count = MapUtil.getInteger(map, k.count);
            String field = MapUtil.getString(map, k.field);
//            MapU
            if(count>=2){
                log.info("count>=2) field  "+field);
            }
        }
        return ReturnT.success( countList);
    }


    private Class<?> getEntityType() {
        // 返回您要查询的实体类类型
        return Map.class;
    }

    public Map countFieldDo( MongoReq mongoReq) {
//        MongoReq mongoReq=new MongoReq();
        String countField = mongoReq.getCountField();
        String collectionName = mongoReq.getCollectionName();
        GroupOperation groupOperation = Aggregation.group(countField).count().as("count");
        TypedAggregation<?> aggregation = Aggregation.newAggregation(getEntityType(), groupOperation);
        AggregationResults<Map> aggregationResults = mongoTemplate.aggregate(aggregation, collectionName, Map.class);
//        aggregationResults = mongoTemplate.aggregate(aggregation, collectionName, Map.class);
//        CountResult result = aggregationResults.getUniqueMappedResult();
        Map result = aggregationResults.getUniqueMappedResult();
//        return result != null ?(Long)    result.get(k.count): 0;
        return result;
    }
    public Long countField(String fieldName) {
        MongoReq mongoReq=new MongoReq();
        String collectionName = mongoReq.getCollectionName();
        GroupOperation groupOperation = Aggregation.group(fieldName).count().as("count");
        TypedAggregation<?> aggregation = Aggregation.newAggregation(getEntityType(), groupOperation);
        AggregationResults<Map> aggregationResults = mongoTemplate.aggregate(aggregation, collectionName, Map.class);
//        aggregationResults = mongoTemplate.aggregate(aggregation, collectionName, Map.class);
//        CountResult result = aggregationResults.getUniqueMappedResult();
        Map result = aggregationResults.getUniqueMappedResult();
        return result != null ?(Long)    result.get(k.count): 0;
    }

    //    D:\proj\brain\admin-antd-react\src\pages\ApiUrlMapTable
//    findDistinct
    @ApiOperation(value = "update", notes = "geocode_geo")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object  update(@RequestBody MongoReq mongoReq){
        UpdateResult update = MongoUtil.update(mongoReq, mongoTemplate, Document.class);
        return ReturnT.success(update);
    }


//    @ApiOperation(value = "insert_data", notes = "geocode_geo")
//    @RequestMapping(value = "/insert_data", method = RequestMethod.POST)
//    public Object  insert_data(@RequestBody MongoReq mongoReq){
//        MongoReq insert1 = MongoUtil.insert(mongoReq, mongoReq.getCollectionName(), mongoTemplate);
//        return ReturnT.success(insert1);
//    }

    @ApiOperation(value = "geocode_geo", notes = "geocode_geo")
    @RequestMapping(value = "/geocode_geo", method = RequestMethod.POST)
    public Object  geocode_geo(@RequestBody GeoReq geoReq){

        String address = geoReq.getAddress();
        String city = geoReq.getCity();
        String force = geoReq.getForce();
//        force.
        if (k.trueKey.equals(force)) {
            Document insert = reqSave(geoReq);
            return ReturnT.success(insert);
        }
//        mongoTemplate.query()
//        MongoUtil.mongoJoin()

//        gao_de_map
//        mongoTemplate.
//        MongoUtil.in
//  MongoUtil.find()
        String geocode_geo_col_name = k.geocode_geo;
        Map<String,Object>eq=new HashMap<>();
        eq.put(k.address,address);
//        eq.put(k.city,city);
        eq.put(k.geoReq+"."+k.city,city);
//        new HashMap<>();
        MongoReq build = MongoReq.builder().equalMap(eq)
                .collectionName(geocode_geo_col_name)
//                .pageNumber()
                .build();
//        Page<Document> documents = MongoUtil.pageDocuments(build, mongoTemplate);
//        MongoUtil.findList()
//        List<Map> maps = MongoUtil.find(build, mongoTemplate);
        List<Document> eqAddress = MongoUtil.findDocuments(build, mongoTemplate);
//        log.info("eqAddress {}",eqAddress);
        if(eqAddress.size()>0){
//            eqAddress.get
//            ListUtil.haveLike()
//            最新的
            Document document1 = ListUtil.getLastElement(eqAddress);
//            Document document1 = eqAddress.get(0);
//            有了 不要用api
//            Data.geocode_geo
//            Data.geocode_geo.geocodes[0].location
//            document1.gepa
//               bson Document get by path 像是这样的      Data.geocode_geo.geocodes[0].location

            // 通过路径获取值
//            Object value = document1.get("Data.geocode_geo.geocodes.0.location");
//            Object value = document1.get("geocode_geo.geocodes.0.location");

//            Cannot cast org.bson.Document to com.alibaba.fastjson.JSONObject
//            Object value = document1.get("geocode_geo", Document.class)
//                    .getList("geocodes", Document.class)
//                    .get(0).get("location");

//            Object geocode_geo = document1.get("geocode_geo");
//            geocodes
            Document geocode_geo = document1.get("geocode_geo", Document.class);
            List<Document> geocodes = geocode_geo.getList("geocodes", Document.class);
            Document geocode0 = geocodes.get(0);
            Object value = geocode0.get("location");
            String formatted_address = geocode0.getString("formatted_address");
//            Object value = document1.get("geocode_geo", JSONObject.class)
//                    .getJSONArray("geocodes")
//                    .getJSONObject(0).get("location");
//            List categories = JsonPath.from(json).get("store.book.category");
//
//            JsonPath.

// 如果您知道路径下的值是特定类型的，您可以进行适当的类型转换
            if (value instanceof String) {
                String location = (String) value;
                log.info("location {}",location);
//                城院
//                String   loc城院 ="120.155627,30.328467";
                String   loc城院 ="120.155368,30.322805"; //南校区
              String loc杭州浙大网新智慧立方=  "120.120432,30.337285";
//                GeoReq.builder().origin(location).destination()
//                GeoReq geoReq1 = GeoReq.builder().origin(location).destination(loc城院).build();
//                geoReq1.address=""
                JSONObject walkingZUCC = MapNavUtil.walking(
                        GeoReq.builder().origin(location).destination(loc城院).build(),
                        gaoDeMapKey
                );

//                JSONObject jsonObject = HttpRequest.get(geoReq1.place_text());

//                route
//paths
//                JSONObject route = walkingZUCC.getJSONObject("route");
////                paths
//                JSONArray paths = route.getJSONArray("paths");
//                JSONObject path0 = paths.getJSONObject(0);
//                Integer duration = path0.getInteger("duration");


//                秒数 抓话
//                duration/60 分钟
                JSONObject walking智慧 = MapNavUtil.walking(
                        GeoReq.builder().origin(location).destination(loc杭州浙大网新智慧立方).build(),
                        gaoDeMapKey
                );
                WalkTwo build1 = WalkTwo.builder()
                        .walkingZUCC(walkingZUCC).walking智慧(walking智慧)
                        .geocode_geo(geocode_geo)
                        .formatted_address(formatted_address).location(location).build();


                build1.minuteSet();
//                new Doc

                WalkTwo insert = mongoTemplate.insert(build1, k.WalkTwo);
                log.info("insert WalkTwo zucc {}",insert);
                // 进一步处理字符串类型的location值
                // ...
            } else if (value instanceof Double) {
                Double location = (Double) value;
                // 进一步处理Double类型的location值
                // ...
            }

            return ReturnT.success(document1);
        }
        Document insert = reqSave(geoReq);
//        JSONObject geocode_geo = MapNavUtil.geocode_geo(geoReq, gaoDeMapKey);
//        log.info("geocode_geo {}",geocode_geo);
//        Document document = new Document();
//        document.put("geoReq",geoReq);
//        document.put("geocode_geo",geocode_geo);
//        document.put("address",address);
//        Date date = new Date();
//        document.put("date",date);

//        maps.
//        Document insert = mongoTemplate.insert(document, k.geocode_geo);

        return ReturnT.success(insert);
    }

    Document reqSave(GeoReq geoReq){
        String address = geoReq.getAddress();
        JSONObject geocode_geo = MapNavUtil.geocode_geo(geoReq, gaoDeMapKey);
        log.info("geocode_geo {}",geocode_geo);
        Document document = new Document();
        document.put("geoReq",geoReq);
        document.put("geocode_geo",geocode_geo);
        document.put("address",address);
        Date date = new Date();
        document.put("date",date);
//        maps.
        Document insert = mongoTemplate.insert(document, k.geocode_geo);
        return insert;
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

//        AskTypeReq()
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


//        equalMap=null, localDoc=nowcoder, otherDoc=nowcoder_resp,
//        localField=momentData.entityId, foreignField=entryId)]
//        nowcoder  nowcoder_resp nowcoder_resp
//        recommendData.entityId
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("nowcoder_resp")
                .localField("recommendData.entityId")
                .foreignField("entryId")
                .as("userDoc");
        String userDoc = "userDoc";
//        nowcoder_resp



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

    @PostMapping("/nowcoder_resp_join")
    public  Object nowcoder_resp_join(@RequestBody MongoReq mongoReq
//            ,  @RequestBody WriteFileDto writeFileDto
   ){

//        List<Document> documents = MongoUtil.mongoJoin(mongoReq, mongoTemplate);
//        for (Document document : documents) {
////            document.get("")
//        }

//        2023-05-10 10:55:19.080  INFO 40344 --- [nio-8889-exec-3] org.mongodb.driver.connection            : Opened connection [connectionId{localValue:6, serverValue:1330}] to 43.142.150.223:27017
//        ProjectionOperation projectionOperation = Aggregation.project()
//                .and("momentData.content").as("momentDataContent")
//                .and(  "joinDoc.content").as("joinDocContent");


//        return MongoUtil.mongoJoin(
//                "nowcoder"
//                , "nowcoder_resp"
//                , "recommendData.entityId"
//                , "entryId"
//                , mongoTemplate
//                ,projectionOperation);
        List<Document> documents = MongoUtil.mongoJoin(mongoReq, mongoTemplate  );
//        for
//        List<JSONObject>listAsk=new ArrayList<>();
//        List<JSONObject>listAsk=new ArrayList<>();
        JSONArray listAsk=new JSONArray();
        for (Document document : documents) {
//            String momentData = document.getString("momentData");
//            JSONObject momentData =(JSONObject) document.get("momentData");
            Document momentData =(Document) document.get("momentData");
            String content = momentData.getString("content");
            System.out.println("content");
            System.out.println(content);
            String ask=content;
//            content": "类型#裤*版型#宽松*风格#性感*图案#线条*裤型#阔腿裤", "summary":
//            Document joinDoc =(Document)   document.get("joinDoc");
//            ArrayList<Document> joinDoc =(ArrayList<Document>)   document.get("joinDoc");
////            java.util.ArrayList cannot be cast to org.bson.Document
////            joinDoc:
//            for (Document commentData : joinDoc) {
//                String contentCommentData = commentData.getString("content");
//                System.out.println("contentCommentData");
//                System.out.println(contentCommentData);
//            }
            String allCommentData = getAllCommentData(document);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("content",ask);
            jsonObject.put("summary",allCommentData);
            listAsk.add(jsonObject);
//            System.out.println("joinDoc");
//            System.out.println(joinDoc);
////            document.f
////            joinDoc.forEach(o->o.ge);
////            joinDoc.getas
//            String content1 = joinDoc.getString("content");

        }
//        String filePath = writeFileDto.getFilePath();
//        JSONObject faskjson 写出一个文件
        String nowFileFormat = DateUtilSt.getNowFileFormat();
        String outPath = "D:\\i-brain\\nowcoder_join/nowcoder_join_{nowFileFormat}.json"
                .replace("{nowFileFormat}", nowFileFormat);
        System.out.println("outPath");
        System.out.println(outPath);
        JsonUtil.writeJsonToFile(listAsk,outPath);
        int size = listAsk.size();
//        System.out.println("size listAsk");
//        System.out.println(size);
        log.info("size listAsk {}" ,size);
        return documents;
//        ],
//        momentData
//        return   MongoUtil.mongoJoin(mongoReq,mongoTemplate
//        ,projectionOperation);
    }
//    size listAsk 7

    @PostMapping("/nowcoderHarList_insert")
    public  Object nowcoderHarList_insert() throws IOException {

//        getNowcoderHarList
//        List<List<Document>> nowcoderHarList = HarUtilKt.getNowcoderHarList("D:\\download\\www.nowcoder.com工作4.har");

        String filePath="D:\\download\\www.nowcoder.com工作4.har";
        String collectionName = "nowcoder";
        List<Document> nowcoderHarList = getNowcoderHarListPat(filePath);
        Collection<Document> documents1 = mongoTemplate.insert(nowcoderHarList,collectionName);
//        for (List<Document> documents : nowcoderHarList) {
////            k.elementUi
////            Collection<Document> documents1 = mongoTemplate.insertAll(documents,"1");
//            Collection<Document> documents1 = mongoTemplate.insert(documents,"1");
//        }
//        MongoReq mongoReq = new MongoReq();
//        nowcoder_resp_join()
        return ReturnT.success(documents1);

    }

    public static List<List<Document>> getNowcoderHarList(String filepath) throws IOException {
        File file = new File(filepath);
        Har read = HarUtils.read(file);
        int index = 0;
        List<List<Document>> list = new ArrayList<>();
        for (HarEntry entry : read.log.entries) {
            if (!entry.request.url.contains("recommend")) {
                continue;
            }
            if (index == 0) {
                String jsonStr = entry.response.content.text;
                JSONArray parseJsonGetRecords = HarUtilKt. parseJsonGetRecords(jsonStr);
                if (parseJsonGetRecords != null) {
                    List<Document> jsonArrayToDocuments = JsonUtilKt. jsonArrayToDocuments(parseJsonGetRecords);
                    list.add(jsonArrayToDocuments);
                }
            }
            index++;
        }
        return list;
    }

    public static List<Document> getNowcoderHarListPat(String filepath) throws IOException {
        File file = new File(filepath);
        Har read = HarUtils.read(file);
        int index = 0;
//        List<List<Document>> list = new ArrayList<>();
        List<Document> list = new ArrayList<>();
        for (HarEntry entry : read.log.entries) {
            if (!entry.request.url.contains("recommend")) {
                continue;
            }
            if (index == 0) {
                String jsonStr = entry.response.content.text;
                JSONArray parseJsonGetRecords = HarUtilKt. parseJsonGetRecords(jsonStr);
                if (parseJsonGetRecords != null) {
                    List<Document> jsonArrayToDocuments = JsonUtilKt. jsonArrayToDocuments(parseJsonGetRecords);
//                    list.add(jsonArrayToDocuments);
//                    ListUtil.
//                    list
                    list.addAll(jsonArrayToDocuments);
                }
            }
            index++;
        }
        return list;
    }
    //    , MongoCollection<Document> collection
    public static void traverseFolder(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
//                file.name.startsWith("")
                    boolean isHar = file.getName().endsWith(".har")
                            && file.getName().contains("nowcoder.com");
//                nowcoder.com
//                file.name.contains("nowcoder.com")
                    if (!isHar) {
                        continue;
                    }
//                nowcoder.com工作回暖了
                    System.out.println(file.getAbsolutePath());
//                    insertFile(file, collection);
                    List<List<Document>> nowcoderHarList = HarUtilKt.getNowcoderHarList(file.getAbsolutePath());
//                if (file.isDirectory()) {
//                    System.out.println(file.getAbsolutePath());
////                    insertFile(file,collection)
//                }
                }
            }
        }
    }


    public static String getAllCommentData(Document document){
        ArrayList<Document> joinDoc =(ArrayList<Document>)   document.get("joinDoc");
//            java.util.ArrayList cannot be cast to org.bson.Document
//            joinDoc:
        StringBuilder res= new StringBuilder();
        for (Document commentData : joinDoc) {
            String contentCommentData = commentData.getString("content");
//            System.out.println("contentCommentData");
//            System.out.println(contentCommentData);
            res.append(contentCommentData).append("\n");
        }
        return res.toString();
    }
    @Autowired
    WebApplicationContext applicationContext;

    @ApiOperation(value = "getAllURL", notes = "getAllURL")
    @RequestMapping(value = "/getAllURL", method = RequestMethod.POST)
    public Object getAllURL() throws FileNotFoundException {
        StringBuilder postMethodRows = new StringBuilder();
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
            postMethodRows.append(postMethod).append("\n");
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

//        String tpl = FileUtil.readAll(tplPath);
        String tpl =        top.starp.util.FileUtil.readAll(tplPath);
        String code = tpl.replace("{postMethodRows}", postMethodRows.toString());
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
//        TableInfo
//        List<Table> tables = Table.mapsToObjs(maps);
//        Table(maps )
//        List<TableName> tableNames = userDAO.selectTableNamesToObj("gcsm", "base table");
        Map<String, Object> stringObjectMap = maps.get(0);
//        Map
        Set<String> tableInfoKeys = stringObjectMap.keySet();
        System.out.println("tableInfoKeys");
        System.out.println(tableInfoKeys);
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
        List<Table> tables = Table.mapsToObjs(maps);
        Map<String, Object> stringObjectMap = maps.get(0);
//        Map
        Set<String> tableInfoKeys = stringObjectMap.keySet();
        System.out.println("tableInfoKeys");
        System.out.println(tableInfoKeys);
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
            CodeGen.gen(mapTableCols, table_schema,tables);
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
