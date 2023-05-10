package com.example.demo.util;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Collections;
import java.util.List;

public class MongoUtil {
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
}
