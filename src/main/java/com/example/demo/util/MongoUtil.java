package com.example.demo.util;

import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;
import top.starp.util.k;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MongoUtil {


    public static  <T> T insert(T objectToSave, String collectionName, MongoTemplate mongoTemplate) {
        return  mongoTemplate.insert(objectToSave,collectionName);
    }
    public  static <T> T save(T objectToSave, String collectionName, MongoTemplate mongoTemplate) {
        return  mongoTemplate.insert(objectToSave,collectionName);
    }
    public static List<Map> find(MongoReq mongoReq, MongoTemplate mongoTemplate, Query query){
//        String collectionName = mongoReq.getCollectionName();
//        if (collectionName==null) {
//            List<Map> all = mongoTemplate.find(query, Map.class);
////            List<Document> documents = mongoTemplate.find(query, Document.class);
////            for (Document document : documents) {
//////                document.getString()
////            }
//            return all;
//        }
//        List<Map> all = mongoTemplate.find(query, Map.class,collectionName);
//        return all;

        return find(mongoReq, mongoTemplate, query,Map.class);
    }

    public  static UpdateResult updateFirst(MongoReq mongoReq, MongoTemplate mongoTemplate){
        Map<String, Object> updateMap = mongoReq.getUpdateMap();
//        updateMap.get("")
        String id =(String) updateMap.get("id");
        Query query = Query.query(Criteria.where("_id").is(
                new ObjectId(id)
        ));

        Update update = new Update();
        for (Map.Entry<String, Object> stringObjectEntry : updateMap.entrySet()) {
            String key = stringObjectEntry.getKey();
            if("_id".equals(key)){
                continue;
            }
            update.set(key, stringObjectEntry.getValue());
        }
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Document.class,mongoReq.getCollectionName());
        return  updateResult;
    }

    public static  <T> List<T>find(MongoReq mongoReq, MongoTemplate mongoTemplate,Query query,Class<T> entityClass){
        String collectionName = mongoReq.getCollectionName();
        if (collectionName==null) {
            List<T> all = mongoTemplate.find(query, entityClass);
//            List<Document> documents = mongoTemplate.find(query, Document.class);
//            for (Document document : documents) {
////                document.getString()
//            }
            return all;
        }

        List<T> all = mongoTemplate.find(query, entityClass, collectionName);
        return all;
    }

//    /**
//     * 传入什么则返回什么类型分页对象
//     * @param current   当前页
//     * @param size      每页大小
//     * @param query     spring针对mongo封装的查询对象
//     * @param tClass    传入的class
//     * @param <T>       泛型
//     * @return
//     */
//    public <T> Page<T> getPage(int current,int size,Query query,Class<T> tClass){
//        long count = mongoTemplate.count(query, tClass);
//        Pageable pageable = PageRequest.of((current - 1) * size, size);
//        List<T> pageList = mongoTemplate.find(query.with(pageable), tClass);
//        return (Page<T>) new PageImpl(pageList, pageable, count);
//    }
//————————————————
//    版权声明：本文为CSDN博主「calmtho」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//    原文链接：https://blog.csdn.net/xtho62/article/details/124883948
//

    public static  <T> List<T>findList(String collectionName,MongoTemplate mongoTemplate,
                                       Query query,Class<T> entityClass){
        if (collectionName==null) {
            return  mongoTemplate.find(query, entityClass);
//            mongoTemplate.findOne()
        }

        return mongoTemplate.find(query, entityClass, collectionName);
    }
    public static  <T> Page<T> page(MongoReq mongoReq, MongoTemplate mongoTemplate, Query query, Class<T> entityClass){
        String collectionName = mongoReq.getCollectionName();
//        if (collectionName==null) {
//         return  mongoTemplate.find(query, entityClass);
////            mongoTemplate.findOne()
//        }
//
//        List<T> all = mongoTemplate.find(query, entityClass, collectionName);
//        return all;

//        Integer pageNumber = mongoReq.getPageNumber();

        Integer pageSize = mongoReq.getPageSize();
        long count = mongoTemplate.count(query, entityClass);
        Pageable pageable = PageRequest.of(
                (mongoReq.getPageNumber() - 1) * pageSize, pageSize);
        List<T> pageList = findList(collectionName, mongoTemplate, query, entityClass);
//        lis
//        find()
//        MongoUtil.find()
//        List<Document> documents = MongoUtil.findDocuments(mongoReq, mongoTemplate);
//        List<T> pageList = mongoTemplate.find(query.with(pageable), entityClass);
        return (Page<T>) new PageImpl(pageList, pageable, count);
//        PageImpl.o
//        Page.of
    }


    public static Page<Document>pageDocuments(MongoReq mongoReq, MongoTemplate mongoTemplate,Query query
    ){
        String collectionName = mongoReq.getCollectionName();

        Integer pageSize = mongoReq.getPageSize();
        long count = mongoTemplate.count(query, Document.class,collectionName);
        Pageable pageable = PageRequest.of(
                (mongoReq.getPageNumber() - 1) * pageSize, pageSize);
//        List<T> pageList = findList(collectionName, mongoTemplate, query, entityClass);

        List<Document> documents = findDocuments(mongoReq, mongoTemplate);
        return (Page<Document>) new PageImpl(documents, pageable, count);
//        PageImpl.o
//        Page.of
    }
    public static  <T> Page<T>page(MongoReq mongoReq, MongoTemplate mongoTemplate,Class<T> entityClass){
        Query query = getQuery(mongoReq);
        return    page(mongoReq,mongoTemplate,query,entityClass);

    }

    public static Page<Document>pageDocuments(MongoReq mongoReq, MongoTemplate mongoTemplate){
//        pageDocuments

        Query query = getQuery(mongoReq);
        return  pageDocuments(mongoReq,mongoTemplate,query);
//        return    page(mongoReq,mongoTemplate,query,entityClass);

    }

    public static Page<Document>page(MongoReq mongoReq, MongoTemplate mongoTemplate){
        return  page(mongoReq,mongoTemplate, Document.class);

    }

    public static List<Map> find(MongoReq mongoReq, MongoTemplate mongoTemplate){
        Query query = MongoUtil.getQuery(mongoReq);
        return   MongoUtil.find(mongoReq, mongoTemplate, query);
    }

//    public static Page<Document> findDocuments(MongoReq mongoReq, MongoTemplate mongoTemplate){
////        mongoTemplate.update()
//        Query query = MongoUtil.getQuery(mongoReq);
//        return   MongoUtil.find(mongoReq, mongoTemplate, query, Document.class);
//    }

    public static List<Document> findDocuments(MongoReq mongoReq, MongoTemplate mongoTemplate){
//        mongoTemplate.update()
        Query query = MongoUtil.getQuery(mongoReq);
        List<Document> documents = MongoUtil.find(mongoReq, mongoTemplate, query, Document.class);
        for (Document document : documents) {

//            Object id = document.get("_id");
//            String id = document.getString("_id");
//            ObjectId id = (ObjectId)document.get("id");
            ObjectId id = (ObjectId)document.get("_id");
//            id.getCounter()
            String idStr = id.toString();
//            System.out.println(id);

//            document.put("id",id);
            document.put("id",idStr);
        }

//        return   MongoUtil.find(mongoReq, mongoTemplate, query, Document.class);
        return  documents;
    }

    public static List<Map> findPutId(MongoReq mongoReq, MongoTemplate mongoTemplate){
        List<Map> maps = MongoUtil.find(mongoReq, mongoTemplate);
        for (Map map : maps) {
//            Object id = map.get("_id");
            String id =(String) map.get("_id");
//            System.out.println("id");
//            System.out.println(id);
            map.put("id",id);
        }
        return  maps;
    }




    public static Query getQuery(MongoReq mongoReq){
        Query query = new Query();
//        MongoReq
        Map<String, Object> likeMap = mongoReq.getLikeMap();
        Map<String, Object> equalMap = mongoReq.getEqualMap();
//        Map<String,String>likeMap=new HashMap<>();
        if(likeMap!=null){
            for (Map.Entry<String, Object> stringStringEntry : likeMap.entrySet()) {
                String key = stringStringEntry.getKey();
                Object value = stringStringEntry.getValue();

                query.addCriteria(
                        Criteria.where(key )
                                .regex(".*?\\" +value+ ".*")
                );
            }
        }


        if(equalMap!=null){
            for (Map.Entry<String, Object> stringStringEntry : equalMap.entrySet()) {
                String key = stringStringEntry.getKey();
                Object value = stringStringEntry.getValue();


//                new ObjectId("");
                if(key.equals("_id")){
                    query.addCriteria(
                            Criteria.where(key ).is(
                                    new ObjectId(   (String) value)
                            )
                    );
                }else{
                    query.addCriteria(
                            Criteria.where(key ).is(value)
                    );
                }
            }
        }
//        int pageNumber =1;
//        if(mongoReq!=null){
//            pageNumber =    mongoReq.getPageNumber();     // The page number you want to retrieve
//        }
//        int  pageNumber =    mongoReq.getPageNumber()==null?1:mongoReq.getPageNumber();
        int  pageNumber =    mongoReq.getPageNumber()==null?0:mongoReq.getPageNumber();
        int  pageSize =    mongoReq.getPageSize()==null?10:mongoReq.getPageSize();

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
//        int pageSize = mongoReq.getPageSize();      // The number of documents per page

// Calculate the number of documents to skip
        int skip = (pageNumber - 1) * pageSize;

// Create a query with pagination parameters
//        Query query = new Query().skip(skip).limit(pageSize);
        return query.with(pageRequest);
//        return  query.skip(skip).limit(pageSize);
//        return  query;
    }

    public static List<Document> mongoJoin(String localDoc,
                                           String otherDoc, String localField
            , String foreignField
            , MongoTemplate mongoTemplate
            , ProjectionOperation projectionOperation) {
        String joinDoc = "joinDoc";
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from(otherDoc)
                .localField(localField)
                .foreignField(foreignField)
                .as(joinDoc);
        Criteria criteria = Criteria.where(joinDoc).ne(Collections.emptyList());
        Aggregation aggregation ;
        if(projectionOperation==null)  {
             aggregation = Aggregation.newAggregation(
//                projectionOperation,
                    lookupOperation
//                    ,  Aggregation.unwind(joinDoc)
//                    ,projectionOperation
//                Aggregation.project()
//                        .and("momentDataContent").as("momentDataContent")
//                        .and("joinDocs.content").as("joinDocContent")
                    , Aggregation.match(criteria)
            );
        }else{
            aggregation = Aggregation.newAggregation(
//                projectionOperation,
                    lookupOperation
//                    ,  Aggregation.unwind(joinDoc)
                    ,projectionOperation
//                Aggregation.project()
//                        .and("momentDataContent").as("momentDataContent")
//                        .and("joinDocs.content").as("joinDocContent")
                    , Aggregation.match(criteria)
            );
        }

        return mongoTemplate.aggregate(aggregation, localDoc, Document.class).getMappedResults();
    }

    public static List<Document> mongoJoin(
            String localDoc,
              String otherDoc
            , String localField
            , String foreignField
            , MongoTemplate mongoTemplate
        ) {
      return   mongoJoin(localDoc,otherDoc,localField,foreignField,mongoTemplate,null);

    }

    public static List<Document> mongoJoin(MongoReq mongoReq
            , MongoTemplate mongoTemplate, ProjectionOperation projectionOperation) {
//        Aggregation aggregation = Aggregation.newAggregation(
//                Aggregation.project()
//                        .and("momentData.content").as("momentDataContent")
//                        .and(
//                                Aggregation.filter(
//                                        Criteria.where("content").ne(null),
//                                        Fields.field("$joinDoc")
//                                )
//                        ).as("joinDocContents")
//        );

//        ProjectionOperation as = Aggregation.project("joinDoc")
//                .and("joinDoc.content")
//                .as("content")
//                .and("joinDoc.otherField")
//                .as("otherField");
////        as.getFields().
//        Aggregation aggregation = Aggregation.newAggregation(
//                Aggregation.project()
//                        .and("momentData.content").as("momentDataContent")
//                        .and(
//
//                        ).as("joinDoc"),
//                Aggregation.match(
//                        Criteria.where("joinDoc.content").ne(null)
//                ),
//                Aggregation.project()
//                        .and("momentDataContent")
//                        .and("joinDoc.content").as("joinDocContents")
//        );
//

//        Aggregation aggregation = Aggregation.newAggregation(
//                Aggregation.project()
//                        .and("momentData.content").as("momentDataContent")
//                        .and(
//                                Aggregation.project("joinDoc")
//                                        .and("joinDoc.content")
//                                        .as("content")
//                                        .and("joinDoc.otherField")
//                                        .as("otherField")
//                        ).as("joinDoc"),
//                Aggregation.match(
//                        Criteria.where("joinDoc.content").ne(null)
//                ),
//                Aggregation.project()
//                        .and("momentDataContent")
//                        .and("joinDoc.content").as("joinDocContents")
//        );

//        Query query = new Query();

//        Aggregation.project("joinDoc")
//                .and("joinDoc.content").as("content")
//                .and("joinDoc.otherField").as("otherField").getFields()
//        ProjectionOperation as = Aggregation.project("joinDoc")
//                .and("joinDoc.content")
//                .as("content");
//        ProjectionOperation 怎么用到Aggregation 里面
//        new ProjectionOperation()
//        ProjectionOperation 转化成 ProjectionOperation.Projection
//        ProjectionOperation projectionOperation = Aggregation.project()
//                .and("momentData.content").as("momentDataContent")
//                .and(  "joinDoc.content").as("joinDocContent");
//        Aggregation aggregation = Aggregation.newAggregation(
//                projectionOperation,
//                Aggregation.match(
//                        Criteria.where("joinDoc.content").ne(null)
//                ),
//                Aggregation.project()
//                        .and("momentDataContent")
////                        .and("joinDoc.content").as("joinDocContents")
//        );

        return mongoJoin(
                mongoReq.getLocalDoc()
                , mongoReq.otherDoc
                , mongoReq.localField
                , mongoReq.foreignField
                , mongoTemplate
                , projectionOperation);

    }
    public static List<Document> mongoJoin(MongoReq mongoReq
            , MongoTemplate mongoTemplate) {

//        System.out.println( "mongoReq.otherDoc");
//        System.out.println( mongoReq.otherDoc);
//        System.out.println( "mongoReq.otherDoc");
//        System.out.println( mongoReq.otherDoc);
//        String localDoc,
//        String otherDoc
//            , String localField
//            , String foreignField
//            , MongoTemplate mongoTemplate
        return mongoJoin(
                mongoReq.getLocalDoc()
                , mongoReq.otherDoc
                , mongoReq.localField
                , mongoReq.foreignField
                , mongoTemplate
               );

    }
}
