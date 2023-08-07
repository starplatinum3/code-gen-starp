package top.starp.util;

//import com.gungnir.campus.entity.MongoReq;

import com.mongodb.client.DistinctIterable;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.*;
import java.util.stream.Collectors;

public class MongoUtil {
   public  static   List<Map> countField(MongoReq mongoReq,MongoTemplate mongoTemplate){
        String countField = mongoReq.getCountField();
        List<Map> maps = MongoUtil.find(mongoReq, mongoTemplate);
//        Map<String, List<Map<?, ?>>> stringListMap = ListUtil.groupBy(maps, k.apiName);
        Map<String, List<Map<?, ?>>> stringListMap = ListUtil.groupBy(maps,countField);

        List<Map>countList=new ArrayList<>();
//        Map<String ,Integer>coutMap=new HashMap<>();
        for (Map.Entry<String, List<Map<?, ?>>> stringListEntry : stringListMap.entrySet()) {
            List<Map<?, ?>> value = stringListEntry.getValue();
            String key = stringListEntry.getKey();
//            coutMap.put(key,value.size());
            int size = value.size();

            Map map=new HashMap();
            map.put(k.field,key);
            map.put(k.count,size);
            countList.add(map);
        }
        return countList;
    }

    public static Update getUpdate(MongoReq mongoReq) {
//        Update update = new Update();
//        Map<String, Object> updateMap = mongoReq.getUpdateMap();
        Map<String, Object> updateMap = mongoReq.getData();
//        for (Map.Entry<String, Object> stringObjectEntry : updateMap.entrySet()) {
//            Object value = stringObjectEntry.getValue();
////          Object value1 = stringObjectEntry.getValue();
//            String key = stringObjectEntry.getKey();
//            update.set(key, value);
//        }

        Update update = new Update();
        for (Map.Entry<String, Object> stringObjectEntry : updateMap.entrySet()) {
            String key = stringObjectEntry.getKey();
            if ("_id".equals(key)) {
                continue;
            }
            update.set(key, stringObjectEntry.getValue());
        }

        return update;
    }

    public static <T> UpdateResult update(MongoReq mongoReq, MongoTemplate mongoTemplate, Class<T> entityClass) {
        return updateMulti(mongoReq, mongoTemplate, entityClass);
    }

    public static <T> UpdateResult updateMulti(MongoReq mongoReq, MongoTemplate mongoTemplate, Class<T> entityClass) {

// Define the query to find the documents you want to update
//        Query query = new Query();
//        query.addCriteria(Criteria.where("field").is("value"));

        Query query = getQuery(mongoReq);
//      Update update = new Update();
//      Map<String, Object> updateMap = mongoReq.getUpdateMap();
//      for (Map.Entry<String, Object> stringObjectEntry : updateMap.entrySet()) {
//          Object value = stringObjectEntry.getValue();
////          Object value1 = stringObjectEntry.getValue();
//          String key = stringObjectEntry.getKey();
//          update.set(key, value);
//      }


        Update update = getUpdate(mongoReq);
// Define the update operation


//        page()
// Perform the update operation for multiple documents
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update
                , entityClass, mongoReq.getCollectionName());
        return updateResult;
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
        Aggregation aggregation;
        if (projectionOperation == null) {
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
        } else {
            aggregation = Aggregation.newAggregation(
//                projectionOperation,
                    lookupOperation
//                    ,  Aggregation.unwind(joinDoc)
                    , projectionOperation
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
        return mongoJoin(localDoc, otherDoc, localField, foreignField, mongoTemplate, null);

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

    public static Document findLast(String collectionName,
                                    MongoTemplate mongoTemplate) {
        return findLast(collectionName, mongoTemplate, k.data);

    }

    public static Document findLast(String collectionName,
                                    MongoTemplate mongoTemplate, String sortBy) {
//        k.xm
//        MongoTemplate mongoTemplate = getMongoTemplate();
        Query query = new Query();
//        query.with(Sort.by(Sort.Direction.DESC, "nowTimeStr")); // 根据时间戳字段降序排序
//        query.with(Sort.by(Sort.Direction.DESC, k.date)); // 根据时间戳字段降序排序
        query.with(Sort.by(Sort.Direction.DESC, sortBy)); // 根据时间戳字段降序排序
//        Document trainLog = mongoTemplate.findOne(query, Document.class, "train_log"); // 替换TrainLog为您的实体类名
        Document trainLog = mongoTemplate.findOne(query, Document.class, collectionName); // 替换TrainLog为您的实体类名

        return trainLog;

    }

    public static List<Map> find(MongoReq mongoReq, MongoTemplate mongoTemplate, Query query) {


        return find(mongoReq, mongoTemplate, query, Map.class);
    }

    public static Document insert(Document document, String collectionName, MongoTemplate mongoTemplate) {
        try {
            Document insert = mongoTemplate.insert(document, collectionName);
            return insert;
        } catch (Exception e) {
            StringUtils.printException(e);
            System.out.println("collectionName");
            System.out.println(collectionName);
            System.out.println("document");
            System.out.println(document);
            return null;
        }

    }

    public static <T> T insert(T objectToSave, String collectionName, MongoTemplate mongoTemplate) {
        return mongoTemplate.insert(objectToSave, collectionName);
    }

    public static <T> Collection<T> insert(Collection<? extends T> batchToSave, String collectionName, MongoTemplate mongoTemplate) {
        return mongoTemplate.insert(batchToSave, collectionName);
    }

    //    public static   Document insert( T document, String collectionName,MongoTemplate mongoTemplate){
//        Document insert = mongoTemplate.insert(document,collectionName);
//        return insert;
//    }
    public static UpdateResult updateFirst(MongoReq mongoReq, MongoTemplate mongoTemplate) {
        Map<String, Object> updateMap = mongoReq.getUpdateMap();
//        updateMap.get("")
        String id = (String) updateMap.get("id");
        Query query = Query.query(Criteria.where("_id").is(
                new ObjectId(id)
        ));

//        updateMulti()
//        Update update = new Update();
//        for (Map.Entry<String, Object> stringObjectEntry : updateMap.entrySet()) {
//            String key = stringObjectEntry.getKey();
//            if ("_id".equals(key)) {
//                continue;
//            }
//            update.set(key, stringObjectEntry.getValue());
//        }
        Update update = getUpdate(mongoReq);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Document.class, mongoReq.getCollectionName());
        return updateResult;
    }

    public static <T> List<T> find(MongoReq mongoReq, MongoTemplate mongoTemplate, Query query, Class<T> entityClass) {
        String collectionName = mongoReq.getCollectionName();
        return find(mongoReq, mongoTemplate, query, entityClass, collectionName);


//        if (collectionName==null) {
//            List<T> all = mongoTemplate.find(query, entityClass);
//
//            return all;
//        }
//
//        List<T> all = mongoTemplate.find(query, entityClass, collectionName);
//        return all;
    }

    public static <T> List<T> find(MongoReq mongoReq, MongoTemplate mongoTemplate, Query query, Class<T> entityClass
            , String collectionName) {
//        String collectionName = mongoReq.getCollectionName();

        if (collectionName == null) {
            List<T> all = mongoTemplate.find(query, entityClass);

            return all;
        }

//        query.get
        List<T> all = mongoTemplate.find(query, entityClass, collectionName);
        return all;
    }

    public static <T> List<T> find(MongoReq mongoReq, MongoTemplate mongoTemplate, Class<T> entityClass) {
        String collectionName = mongoReq.getCollectionName();
        Query query = getQuery(mongoReq);

        if (collectionName == null) {
            List<T> all = mongoTemplate.find(query, entityClass);

            return all;
        }

        List<T> all = mongoTemplate.find(query, entityClass, collectionName);
        return all;
    }


    public static <T> List<T> findList(String collectionName, MongoTemplate mongoTemplate,
                                       Query query, Class<T> entityClass) {
        if (collectionName == null) {
            return mongoTemplate.find(query, entityClass);
        }

//        getUpdate()
        return mongoTemplate.find(query, entityClass, collectionName);
    }

    public static <T> Page<T> page(MongoReq mongoReq, MongoTemplate mongoTemplate, Query query, Class<T> entityClass) {
        String collectionName = mongoReq.getCollectionName();

        Integer pageSize = mongoReq.getPageSize();
        long count = mongoTemplate.count(query, entityClass, collectionName);
        Pageable pageable = PageRequest.of(
                (mongoReq.getPageNumber() - 1) * pageSize, pageSize);
        List<T> pageList = findList(collectionName, mongoTemplate, query, entityClass);
        return (Page<T>) new PageImpl(pageList, pageable, count);

    }


    public static Page<Document> pageDocuments(MongoReq mongoReq, MongoTemplate mongoTemplate, Query query
    ) {
        String collectionName = mongoReq.getCollectionName();

        Integer pageSize = mongoReq.getPageSize();
        long count = mongoTemplate.count(query, Document.class, collectionName);
        Pageable pageable = PageRequest.of(
                (mongoReq.getPageNumber() - 1) * pageSize, pageSize);

        List<Document> documents = findDocuments(mongoReq, mongoTemplate);
        return (Page<Document>) new PageImpl(documents, pageable, count);

    }

    public static <T> Page<T> page(MongoReq mongoReq, MongoTemplate mongoTemplate, Class<T> entityClass) {
        Query query = getQuery(mongoReq);
        return page(mongoReq, mongoTemplate, query, entityClass);

    }

  public static String  getDistinctField(MongoReq mongoReq){
        List<Op> ops = mongoReq.getOps();
        for (Op op : ops) {
            String op1 = op.getOp();
            if (k.distinct.equals(op1)) {
                String field = op.getField();
                return field;
            }
        }
        return null;
    }

    public static List<Document>  findDistinct(MongoReq mongoReq,MongoTemplate mongoTemplate){
      return   findDistinct(mongoReq,mongoTemplate,Document.class);
    }
    public static  <T> List<T>  findDistinct(MongoReq mongoReq,MongoTemplate mongoTemplate,Class<T> resultClass){
        Query query = getQuery(mongoReq);
//        List<Op> ops = mongoReq.getOps();
//        for (Op op : ops) {
//            String op1 = op.getOp();
//            if (k.distinct.equals(op1)) {
//                String field = op.getField();
//            }
//        }
        String distinctField = mongoReq.getDistinctField();
        String collectionName = mongoReq.getCollectionName();
//        String distinctField = getDistinctField(mongoReq);
        try{
//            DistinctIterable<Document[]> result = productsCollection.distinct("subcategories", Document[].class);

            return mongoTemplate.findDistinct(query,distinctField, collectionName,Document.class,resultClass);
//            List<Document> distinct = mongoTemplate.findDistinct(query, distinctField, collectionName, Document.class, Document.class);
        }catch (InvalidDataAccessApiUsageException e){
            StringUtils.printException(e);
//            StringUtils.printMap(query);
//            log.info("query "+query);
//            log.info("distinctField "+distinctField);
//            Document document = new Document();
            Map<String ,Object>logMap=new HashMap<>();
            logMap.put(k.query,query);
            logMap.put(k.distinctField,distinctField);
            logMap.put(k.mongoReq,mongoReq);
//            log.info(query);
//            JsonUtil.to
            log.printMap(logMap);
        }
//        return list;
        return  ListUtil.createList();

    }

    public static Page<Document> pageDocuments(MongoReq mongoReq, MongoTemplate mongoTemplate) {
//
//        insert()
        Query query = getQueryNoPage(mongoReq);
        return pageDocuments(mongoReq, mongoTemplate, query);
//        return    page(mongoReq,mongoTemplate,query,entityClass);

    }

    public static Page<Document> page(MongoReq mongoReq, MongoTemplate mongoTemplate) {
        return page(mongoReq, mongoTemplate, Document.class);

    }

    public static List<Map> find(MongoReq mongoReq, MongoTemplate mongoTemplate) {
        Query query = MongoUtil.getQuery(mongoReq);
        return MongoUtil.find(mongoReq, mongoTemplate, query);
    }

    public static List<Map> find(MongoReq mongoReq, MongoTemplate mongoTemplate, String collectionName) {
        try {
            //        mongoTemplate.update()
            Query query = MongoUtil.getQuery(mongoReq);

//            List<Map<?,?>> maps = MongoUtil.find(mongoReq, mongoTemplate, query, Map.class, collectionName);
//            List<Map<?,?>> maps = MongoUtil.find(mongoReq, mongoTemplate, query, Map<>.class, collectionName);
//            List<Map> maps = MongoUtil.find(mongoReq, mongoTemplate, query, Map.class, collectionName);
//            List<Map<String, Object>> maps = mongoTemplate.find(query, Map.class, collectionName);


            List<Map> maps = mongoTemplate.find(query, Map.class, collectionName);
//            List<Map> maps1 = mongoTemplate.find(query, Map<String, Object>.class, collectionName);

//            List<Map<String, Object>> maps = mongoTemplate.query(query
//                    , new ParameterizedTypeReference<List<Map<String, Object>>>() {}, collectionName);


//            List<Map<String, Object>> maps = mongoTemplate.find(query
//                    , new ParameterizedTypeReference<List<Map<String, Object>>>() {}, collectionName);



            for (Map map : maps) {
                ObjectId id =  (ObjectId) map.get("_id");
                String idStr = id.toString();
                map.put("id", idStr);
//                map.put("id", map.get("_id"));
            }

//            List<Map<String, Object>> maps = mongoTemplate.find(query, Document.class, collectionName)
//                    .stream()
//                    .map(Document::toMap)
//                    .collect(Collectors.toList());


            return  maps;
//            List<Document> documents = MongoUtil.find(mongoReq, mongoTemplate, query, Document.class, collectionName);
//            for (Document document : documents) {
//                ObjectId id = (ObjectId) document.get("_id");
//                String idStr = id.toString();
//                document.put("id", idStr);
//            }

//            return documents;
        } catch (Exception e) {
//            String collectionName = mongoReq.getCollectionName();
            String message = e.getMessage();
//            "Couldn't find PersistentEntity".con
//            message null
            if (message != null && message.contains("Couldn't find PersistentEntity")) {
//                throw new
                StringUtils.printException(e);
                LogUtil.error("没有指定数据库名字  查看 collectionName " + collectionName);
            }
            return null;

        }

    }

    public static List<Document> findDocuments(MongoReq mongoReq, MongoTemplate mongoTemplate, String collectionName) {
        try {
            //        mongoTemplate.update()
            Query query = MongoUtil.getQuery(mongoReq);

//            List<Map<?,?>> maps = MongoUtil.find(mongoReq, mongoTemplate, query, Map.class, collectionName);
//            List<Map<?,?>> maps = MongoUtil.find(mongoReq, mongoTemplate, query, Map<>.class, collectionName);
//            List<Map> maps = MongoUtil.find(mongoReq, mongoTemplate, query, Map.class, collectionName);
//            for (Map map : maps) {
//                map.put("id", map.get("_id"));
//            }

            List<Document> documents = MongoUtil.find(mongoReq, mongoTemplate, query, Document.class, collectionName);
            for (Document document : documents) {
                ObjectId id = (ObjectId) document.get("_id");
                String idStr = id.toString();
                document.put("id", idStr);
            }

//            documents.stream().map(o->{
//                o.to/
//            })

            return documents;
        } catch (Exception e) {
//            String collectionName = mongoReq.getCollectionName();
            String message = e.getMessage();
//            "Couldn't find PersistentEntity".con
//            message null
            if (message != null && message.contains("Couldn't find PersistentEntity")) {
//                throw new
                StringUtils.printException(e);
                LogUtil.error("没有指定数据库名字  查看 collectionName " + collectionName);
            }
            return null;

        }

    }


    public static List<Document> findDocuments(MongoReq mongoReq, MongoTemplate mongoTemplate) {
        try {
            //        mongoTemplate.update()
            Query query = MongoUtil.getQuery(mongoReq);

            List<Document> documents = MongoUtil.find(mongoReq, mongoTemplate, query, Document.class);
            for (Document document : documents) {

                ObjectId id = (ObjectId) document.get("_id");
                String idStr = id.toString();

                document.put("id", idStr);
            }

            return documents;
        } catch (Exception e) {
            String collectionName = mongoReq.getCollectionName();
            String message = e.getMessage();
            if (message != null && message.contains("Couldn't find PersistentEntity")) {
//                throw new
                StringUtils.printException(e);
                LogUtil.error("没有指定数据库名字  查看 collectionName " + collectionName);
            }
            return null;

        }

    }

    public static List<Map> findPutId(MongoReq mongoReq, MongoTemplate mongoTemplate) {
        List<Map> maps = MongoUtil.find(mongoReq, mongoTemplate);
        for (Map map : maps) {
//            Object id = map.get("_id");
            String id = (String) map.get("_id");

            map.put("id", id);
        }
        return maps;
    }


    /**
     *  if (key.equals("_id")) {
     *                     query.addCriteria(
     *                             Criteria.where(key).ne(
     *                                     new ObjectId((String) value)
     *                             )
     * @param mongoReq
     * @return
     */
    public static Query getQuery(MongoReq mongoReq) {
        Query query = new Query();


        List<Op> ops = mongoReq.getOps();
//        List<Op>ops=new ArrayList<>();
        if (ops != null) {


            for (Op op : ops) {
//            String opStr= op.getOp();
//            String key = op.getField();
//            Object value = op.getValue();
//            k.equals
//            k.less
//            k.asc.
//            Object mongoVal = op.getMongoVal();
//            Criteria where = Criteria.where(key);
//            where.is()
                Criteria criteria = op.getCriteria();
                if(criteria==null){
                    continue;
                }
                query.addCriteria(criteria);

//            if (k.equals.equals(opStr)) {
//                query.addCriteria(
//                        Criteria.where(key ).is(
//                                op.getMongoVal()
////                               getVal(op)
//                        )
//                );
//
////                if(key.equals("_id")){
////                    query.addCriteria(
////                            Criteria.where(key ).is(
////                                    new ObjectId((String)  value)
////                            )
////                    );
////                }else{
////                    query.addCriteria(
////                            Criteria.where(key ).is(value)
////                    );
////                }
//            }
//
//
//


            }

        }


//        MongoReq
        Map<String, Object> likeMap = mongoReq.getLikeMap();


        if (likeMap != null) {
            for (Map.Entry<String, Object> stringStringEntry : likeMap.entrySet()) {
                String key = stringStringEntry.getKey();
                Object value = stringStringEntry.getValue();

                query.addCriteria(
                        Criteria.where(key)
                                .regex(".*?\\" + value + ".*")
                );
            }
        }

        Map<String, Object> equalMap = mongoReq.getEqualMap();
        if (equalMap != null) {
            for (Map.Entry<String, Object> stringStringEntry : equalMap.entrySet()) {
                String key = stringStringEntry.getKey();
                Object value = stringStringEntry.getValue();

                if (key.equals("_id")) {
                    query.addCriteria(
                            Criteria.where(key).is(
                                    new ObjectId((String) value)
                            )
                    );
                } else {
                    query.addCriteria(
                            Criteria.where(key).is(value)
                    );
                }
            }
        }

        Map<String, Object> notEqualMap = mongoReq.getNotEqualMap();

        if (notEqualMap != null) {
            for (Map.Entry<String, Object> stringStringEntry : notEqualMap.entrySet()) {
                String key = stringStringEntry.getKey();
                Object value = stringStringEntry.getValue();

                if (key.equals("_id")) {
                    query.addCriteria(
                            Criteria.where(key).ne(
                                    new ObjectId((String) value)
                            )
                    );
                } else {
                    query.addCriteria(
                            Criteria.where(key).ne(value)
                    );
                }
            }
        }

//        Integer pageNumberOrDefault = mongoReq.getPageNumberOrDefault();
//        int  pageNumber =    mongoReq.getPageNumber()==null?1:mongoReq.getPageNumber();
//        Integer pageNumber = mongoReq.getPageNumberOrDefault();
        Integer pageNumber = mongoReq.getPageNumberOrDefaultPageReq();
//        int pageNumber = mongoReq.getPageNumber() == null ? 0 : mongoReq.getPageNumber();
//        int  pageNumber =    mongoReq.getPageNumber()==null?1:mongoReq.getPageNumber();
        int pageSize = mongoReq.getPageSize() == null ? 10 : mongoReq.getPageSize();

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

// Calculate the number of documents to skip
//        int skip = (pageNumber - 1) * pageSize;
//        query.with(pageRequest).with(
//                Sort.by(Sort.Direction.DESC, "sortingField")
//        )
//        List<Op> ops = mongoReq.getOps();

        Query with = query
                .with(pageRequest);

//        with= addSortOrders(mongoReq,with);
        addSortOrders(mongoReq, with);


//        {
////          todo  导致bug 先注释
//            List<Op> sortBy = mongoReq.getSortBy();
//            List<Sort.Order> sortOrders = new ArrayList<>();
//
//            for (Op op : sortBy) {
////            Object value = op.getValue();
////            String field = op.getField();
////            String orderDirection= (String)value;
////            String opStr = op.getOp();
//                Sort.Order sortOrder = getSortOrder(op);
////            if (k.desc.equals(orderDirection)) {
////                Sort.Order sortingField1 = Sort.Order.desc(field);
////            }
////            Sort.Order sortingField1 = Sort.Order.desc(field);
//                sortOrders.add(sortOrder);
//            }
////        if()
//            if (!sortOrders.isEmpty()) {
////            with.with(sortOrders);
//                with.with(
//                        Sort.by(
//                                sortOrders
//                        )
//                );
//            }
//
//
//        }


        return with;
//        return  query
//                .with(pageRequest)


//                .with(Sort.by(
//                Sort.Order.desc("sortingField1"),
//                Sort.Order.asc("sortingField2")
//        ))
//                ;

//        return query.with(pageRequest);

    }

//   static Query addSortOrders(MongoReq mongoReq, Query with) {
////          todo  导致bug 先注释
//        List<Op> sortBy = mongoReq.getSortBy();
//        List<Sort.Order> sortOrders = new ArrayList<>();
//        if (sortBy == null) {
//            return with;
//        }
//        for (Op op : sortBy) {
////            Object value = op.getValue();
////            String field = op.getField();
////            String orderDirection= (String)value;
////            String opStr = op.getOp();
//            Sort.Order sortOrder = getSortOrder(op);
//            if (sortOrder == null) {
//                continue;
//            }
////            if (k.desc.equals(orderDirection)) {
////                Sort.Order sortingField1 = Sort.Order.desc(field);
////            }
////            Sort.Order sortingField1 = Sort.Order.desc(field);
//            sortOrders.add(sortOrder);
//        }
////        if()
//        if (!sortOrders.isEmpty()) {
////            with.with(sortOrders);
//            with=  with.with(
//                    Sort.by(
//                            sortOrders
//                    )
//            );
//        }
////       with.with()
//        return with;
//
//
//    }

    static void addSortOrders(MongoReq mongoReq, Query with) {
//          todo  导致bug 先注释
        List<Op> sortBy = mongoReq.getSortBy();
        List<Sort.Order> sortOrders = new ArrayList<>();
        if (sortBy == null) {
//            return with;
            return;
        }
        for (Op op : sortBy) {
//            Object value = op.getValue();
//            String field = op.getField();
//            String orderDirection= (String)value;
//            String opStr = op.getOp();
            Sort.Order sortOrder = getSortOrder(op);
            if (sortOrder == null) {
                continue;
            }
//            if (k.desc.equals(orderDirection)) {
//                Sort.Order sortingField1 = Sort.Order.desc(field);
//            }
//            Sort.Order sortingField1 = Sort.Order.desc(field);
            sortOrders.add(sortOrder);
        }
//        if()
        if (!sortOrders.isEmpty()) {
//            with.with(sortOrders);
//            with=
            with.with(
                    Sort.by(
                            sortOrders
                    )
            );
        }
//        MongoReq.
//       with.with()
//        return with;
//        return ;


    }

    static Sort.Order getSortOrder(Op op) {
        Object value = op.getValue();
        String field = op.getField();
        String orderDirection = (String) value;
        String opStr = op.getOp();
//        ListUtil.createList(k.desc).contains(orderDirection)
//        if (k.desc.equals(orderDirection)) {
        if (ListUtil.createList(k.desc).contains(orderDirection)) {
            Sort.Order desc = Sort.Order.desc(field);
            return desc;
        } else {
            return Sort.Order.asc(field);
        }
//        if (k.asc.equals(orderDirection)) {
//            return Sort.Order.asc(field);
//        }
    }

    static Object getVal(Op op) {
        String key = op.getField();
        Object value = op.getValue();
        if ("_id".equals(key)) {
            return new ObjectId((String) value);
        }
        return value;
//        key.equals("_id")
//        new ObjectId((String)  value)
    }

    //    Criteria d(Op op, Criteria where ){
//        String opStr= op.getOp();
//        Object mongoVal = op.getMongoVal();
//        if (k.equals.equals(opStr)) {
//            Criteria criteria = where.is(mongoVal);
//            return criteria;
//        }
//        if (k.like.equals(opStr)) {
//            return  where        .regex(".*?\\" +mongoVal+ ".*");
////            Criteria criteria = where.is(mongoVal);
////            return criteria;
//        }
//    }
    public static Query getQueryByOp(MongoReq mongoReq) {
        Query query = new Query();
        List<Op> ops = mongoReq.getOps();
//        List<Op>ops=new ArrayList<>();
        for (Op op : ops) {
            String opStr = op.getOp();
            String key = op.getField();
            Object value = op.getValue();
//            k.equals
//            k.less
//            k.asc.
            Object mongoVal = op.getMongoVal();
            Criteria where = Criteria.where(key);
//            where.is()
            Criteria criteria = op.getCriteria();
            query.addCriteria(criteria);

//            if (k.equals.equals(opStr)) {
//                query.addCriteria(
//                        Criteria.where(key ).is(
//                                op.getMongoVal()
////                               getVal(op)
//                        )
//                );
//
////                if(key.equals("_id")){
////                    query.addCriteria(
////                            Criteria.where(key ).is(
////                                    new ObjectId((String)  value)
////                            )
////                    );
////                }else{
////                    query.addCriteria(
////                            Criteria.where(key ).is(value)
////                    );
////                }
//            }
//
//
//


        }

//        MongoReq
        Map<String, Object> likeMap = mongoReq.getLikeMap();
        Map<String, Object> equalMap = mongoReq.getEqualMap();

        if (likeMap != null) {
            for (Map.Entry<String, Object> stringStringEntry : likeMap.entrySet()) {
                String key = stringStringEntry.getKey();
                Object value = stringStringEntry.getValue();

                query.addCriteria(
                        Criteria.where(key)
                                .regex(".*?\\" + value + ".*")
                );
            }
        }


        if (equalMap != null) {
            for (Map.Entry<String, Object> stringStringEntry : equalMap.entrySet()) {
                String key = stringStringEntry.getKey();
                Object value = stringStringEntry.getValue();

                if (key.equals("_id")) {
                    query.addCriteria(
                            Criteria.where(key).is(
                                    new ObjectId((String) value)
                            )
                    );
                } else {
                    query.addCriteria(
                            Criteria.where(key).is(value)
                    );
                }
            }
        }

        Map<String, Object> notEqualMap = mongoReq.getNotEqualMap();

        if (notEqualMap != null) {
            for (Map.Entry<String, Object> stringStringEntry : notEqualMap.entrySet()) {
                String key = stringStringEntry.getKey();
                Object value = stringStringEntry.getValue();

                if (key.equals("_id")) {
                    query.addCriteria(
                            Criteria.where(key).ne(
                                    new ObjectId((String) value)
                            )
                    );
                } else {
                    query.addCriteria(
                            Criteria.where(key).ne(value)
                    );
                }
            }
        }

//        Integer pageNumberOrDefault = mongoReq.getPageNumberOrDefault();
//        int  pageNumber =    mongoReq.getPageNumber()==null?1:mongoReq.getPageNumber();
        int pageNumber = mongoReq.getPageNumber() == null ? 0 : mongoReq.getPageNumber();
//        int  pageNumber =    mongoReq.getPageNumber()==null?1:mongoReq.getPageNumber();
        int pageSize = mongoReq.getPageSize() == null ? 10 : mongoReq.getPageSize();

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

// Calculate the number of documents to skip
//        int skip = (pageNumber - 1) * pageSize;

        return query.with(pageRequest);

    }

    public static Query getQueryNoPage(MongoReq mongoReq) {
        Query query = new Query();
//        MongoReq
        Map<String, Object> likeMap = mongoReq.getLikeMap();
        Map<String, Object> equalMap = mongoReq.getEqualMap();
        if (likeMap != null) {
            for (Map.Entry<String, Object> stringStringEntry : likeMap.entrySet()) {
                String key = stringStringEntry.getKey();
                Object value = stringStringEntry.getValue();

                query.addCriteria(
                        Criteria.where(key)
                                .regex(".*?\\" + value + ".*")
                );
            }
        }


        if (equalMap != null) {
            for (Map.Entry<String, Object> stringStringEntry : equalMap.entrySet()) {
                String key = stringStringEntry.getKey();
                Object value = stringStringEntry.getValue();

                if (key.equals("_id")) {
                    query.addCriteria(
                            Criteria.where(key).is(
                                    new ObjectId((String) value)
                            )
                    );
                } else {
                    query.addCriteria(
                            Criteria.where(key).is(value)
                    );
                }
            }
        }
        return query;

    }

}
