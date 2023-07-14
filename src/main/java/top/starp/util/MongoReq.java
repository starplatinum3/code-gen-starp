package top.starp.util;

//import lombok.Builder;
//import lombok.Data;

//import org.apache.zookeeper.Op;

//import org.apache.zookeeper.Op;

import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class MongoReq {

    public Integer getPageNumberOrDefaultPageReq(){
        Integer pageNumberOrDefault = getPageNumberOrDefault();
        if(pageNumberOrDefault==0){
            return 0;
        }
        return  pageNumberOrDefault-1;
    }

 public static String   getCountField(MongoReq mongoReq){
//        List<Op> ops = mongoReq.getOps();
//        for (Op op : ops) {
//            String field = op.getField();
//            String op1 = op.getOp();
//            if (k.count.equals(op1)) {
//                return field;
//            }
//        }
//        return null;
    return getOpField(mongoReq,k.count);
    }

    public static String  getOpField(MongoReq mongoReq,String  opName){
        List<Op> ops = mongoReq.getOps();
        if(ops==null){
            RuntimeException runtimeException = new RuntimeException("f(ops==null){");
            StringUtils.printException(runtimeException);
            throw runtimeException;
//            k.distinct
//            return null;
        }
        for (Op op : ops) {
            String op1 = op.getOp();
            if (opName.equals(op1)) {
                String field = op.getField();
                return field;
            }
        }
        return null;
    }
    public static String  getDistinctField(MongoReq mongoReq){
       return getOpField(mongoReq,k.distinct);

//
//        List<Op> ops = mongoReq.getOps();
//        if(ops==null){
//            RuntimeException runtimeException = new RuntimeException("f(ops==null){");
//            StringUtils.printException(runtimeException);
//            throw runtimeException;
////            k.distinct
////            return null;
//        }
//        for (Op op : ops) {
//            String op1 = op.getOp();
//            if (k.distinct.equals(op1)) {
//                String field = op.getField();
//                return field;
//            }
//        }
//        return null;
    }

    public  String  getCountField(){
        return   getCountField(this);
    }
    public  String  getDistinctField(){
      return   getDistinctField(this);
    }

   public static final Integer FIRST=0;
public     MongoReq(){}
       public String localDoc;
    public String otherDoc;
    public  String localField;
    public    String foreignField;


    List<Op> ops;

    public List<Op> getOps() {
        return ops;
    }
    List<Op> sortBy;

//    d(){
//        List<  Sort.Order >sortOrders=new ArrayList<>();
//        for (Op op : sortBy) {
//            Sort.Order sortingField1 = Sort.Order.desc("sortingField1");
//            sortOrders.add()
//        }
//    }
    public List<Op> getSortBy() {
        return sortBy;
    }

    public void setSortBy(List<Op> sortBy) {
        this.sortBy = sortBy;
    }

    public void sortBys(Op ... sortBy) {
        this.sortBy = Arrays.asList(sortBy);
    }

    public void setOps(List<Op> ops) {
        this.ops = ops;
    }


    public static final Integer deleted=1;
//    public void setOps(Op ... ops) {
//        this.ops = Arrays.asList(ops);
//    }
    public void ops(Op ... ops) {
        this.ops = Arrays.asList(ops);
    }
    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public Map<String, Object> getLikeMap() {
        return likeMap;
    }

    public void setLikeMap(Map<String, Object> likeMap) {
        this.likeMap = likeMap;
    }

    public Map<String, Object> getEqualMap() {
        return equalMap;
    }

    public void setEqualMap(Map<String, Object> equalMap) {
        this.equalMap = equalMap;
    }

    public Map<String, Object> getUpdateMap() {
        return updateMap;
    }

    public void setUpdateMap(Map<String, Object> updateMap) {
        this.updateMap = updateMap;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageNumberIfAbsent(Integer pageNumber) {
        if(this.pageNumber ==null)
        this.pageNumber = pageNumber;
    }
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    public void setPageSizeIfAbsent(Integer pageSize) {
        if(this.pageSize ==null)
        this.pageSize = pageSize;
    }

    Map<String,Object>  data;
    String collectionName;
    Map<String,Object> likeMap;
    Map<String,Object>  equalMap;
    Map<String,Object>  notEqualMap;

    public Map<String, Object> getNotEqualMap() {
        return notEqualMap;
    }

    public void setNotEqualMap(Map<String, Object> notEqualMap) {
        this.notEqualMap = notEqualMap;
    }

    Map<String,Object>  updateMap;


//    Integer pageNumber = 1;     // The page number you want to retrieve
//    Integer pageSize = 10;      // The number of documents per page
    Integer pageNumber ;    // The page number you want to retrieve
    Integer pageSize ;
    public Integer getPageSizeOrDefault(){
        Integer pageSize = getPageSize();
        if(pageSize==null){
//            return  10;
            return  5;
//            pageSize=10;
        }
        return pageSize;
    }

    public Integer getPageNumberOrDefault(){
        Integer pageNumber = getPageNumber();
        if(pageNumber==null){
            return  1;
        }
        return pageNumber;
    }

    public static final class MongoReqBuilder {
        private Map<String, Object> data;
        private String collectionName;
        private Map<String, Object> likeMap;
        private Map<String, Object> equalMap;
        private Map<String, Object> updateMap;
        private Integer pageNumber;
        private Integer pageSize;

        private MongoReqBuilder() {
        }

        public static MongoReqBuilder aMongoReq() {
            return new MongoReqBuilder();
        }

        public MongoReqBuilder withData(Map<String, Object> data) {
            this.data = data;
            return this;
        }

        public MongoReqBuilder withCollectionName(String collectionName) {
            this.collectionName = collectionName;
            return this;
        }

        public MongoReqBuilder withLikeMap(Map<String, Object> likeMap) {
            this.likeMap = likeMap;
            return this;
        }

        public MongoReqBuilder withEqualMap(Map<String, Object> equalMap) {
            this.equalMap = equalMap;
            return this;
        }

        public MongoReqBuilder withUpdateMap(Map<String, Object> updateMap) {
            this.updateMap = updateMap;
            return this;
        }

        public MongoReqBuilder withPageNumber(Integer pageNumber) {
            this.pageNumber = pageNumber;
            return this;
        }

        public MongoReqBuilder withPageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public MongoReq build() {
            MongoReq mongoReq = new MongoReq();
            mongoReq.setData(data);
            mongoReq.setCollectionName(collectionName);
            mongoReq.setLikeMap(likeMap);
            mongoReq.setEqualMap(equalMap);
            mongoReq.setUpdateMap(updateMap);
            mongoReq.setPageNumber(pageNumber);
            mongoReq.setPageSize(pageSize);
            return mongoReq;
        }
    }

}
