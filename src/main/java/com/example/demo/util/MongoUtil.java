package com.example.demo.util;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collections;
import java.util.List;

public class MongoUtil {
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

    public static List<Document> mongoJoin(String localDoc,
                                           String otherDoc, String localField
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

        return mongoJoin(mongoReq.getLocalDoc()
                , mongoReq.otherDoc
                , mongoReq.otherDoc
                , mongoReq.localField
                , mongoTemplate
                , projectionOperation);

    }
    public static List<Document> mongoJoin(MongoReq mongoReq
            , MongoTemplate mongoTemplate) {

        return mongoJoin(mongoReq.getLocalDoc()
                , mongoReq.otherDoc
                , mongoReq.otherDoc
                , mongoReq.localField
                , mongoTemplate
               );

    }
}
