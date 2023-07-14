package top.starp.util;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

//extends Op
public class Op {

    static Object   getVal( Op op ){
        String key = op.getField();
        Object value = op.getValue();
        if ("_id".equals(key)) {
            return  new ObjectId((String)  value);
        }
        return  value;
//        key.equals("_id")
//        new ObjectId((String)  value)
    }

    /**
     *  if ("_id".equals(key)) {
     *             return  new ObjectId((String)  value);
     *         }
     * @return
     */
    public Object   getMongoVal(){
        String key = this.getField();
         Object value = this.getValue();
//        String key = op.getField();
//        Object value = op.getValue();
        if ("_id".equals(key)) {
            return  new ObjectId((String)  value);
        }
        return  value;
//        key.equals("_id")
//        new ObjectId((String)  value)
    }

//    d(){
//        String key = this.getField();
//        Object mongoVal = getMongoVal();
//        Criteria where = Criteria.where(key);
//
//        if (k._id.equals(key)) {
//            Criteria criteria = where.is( new ObjectId((String) mongoVal) );
//        }
//        Criteria criteria = where.is(mongoVal);
//    }
    /**
     * mongo ops
     * @return
     */
//    Op op, Criteria where
   public   Criteria getCriteria( ){
       String key = this.getField();
       Object value = this.getValue();
       String opStr = this.getOp();
//       String opStr= op.getOp();
       Criteria where = Criteria.where(key);

       /**
        *  _id  obj
        */
       Object mongoVal = getMongoVal();
//       Object mongoVal = op.getMongoVal();
//       if (k.equals.equals(opStr)) {
//           return where.equals(mongoVal);
//       }
//       ListUtil.c
//       ListUtil.createList("1").contains(opStr)
//       ListUtil.createList(k.is,k.equals).contains(opStr)
//        if (k.is.equals(opStr)
//        ||k.equals.equals(opStr)) {
       if (  ListUtil.createList(k.is,k.equals,Op.eq).contains(opStr)) {
//           key.
//           if (k._id.equals(key)) {
//               Criteria criteria = where.is( new ObjectId((String) mongoVal) );
//           }
            Criteria criteria = where.is(mongoVal);
            return criteria;
        }

        if (k.like.equals(opStr)) {
            return  where        .regex(".*?\\" +mongoVal+ ".*");
//            Criteria criteria = where.is(mongoVal);
//            return criteria;
        }

//       if (k.notEquals.equals(opStr)) {
//           return  where.ne(mongoVal);
//       }
       if (ListUtil.createList( k.ne,k.notEquals).contains(opStr)) {
           return  where.ne(mongoVal);
       }
//       {name  sort desc}
//       if (ListUtil.createList( k.sortBy).contains(opStr)) {
//           return  where.ne(mongoVal);
//       }
       if (k.in.equals(opStr)) {
//           mongoVal
          List<Object>list= (List<Object>)mongoVal;
//           return  where.in(mongoVal);
           return  where.in(list);
       }
//       if (k.distinct.equals(opStr)) {
//           return null;
//       }
//       if (k.content.equals(opStr)) {
//           return null;
//       }
       if (ListUtil.createList( k.count,k.distinct).contains(opStr)) {
           return  null;
       }
//       where.in()
        return  where.is(mongoVal);
    }

//    extends Operation
    String field;
    String op;

    Object value;

    public Op() {
    }

    public  static  final String  like= "like";
    public  static  final String  notEquals= "notEquals";
    public  static  final String  eq= "eq";
    public  static  final String  orderBy= "orderBy";
    public  static  final String  asc= "asc";
   public  static  final String  in= "in";
   public  static  final String  is= "is";
   public  static  final String  isAfter= "isAfter";
   public  static  final String  lessThanOrEqual= "lessThanOrEqual";
   public  static  final String  lessThan= "lessThan";
   public  static  final String  greaterThanOrEqual= "greaterThanOrEqual";
   public  static  final String  desc= "desc";
   public  static  final String  greaterThan= "greaterThan";
   public  static  final String  equals= "equals";
   public  static  final String  ne= "ne";

    public Op(String field, Object value, String op) {
        this.field = field;
        this.op = op;
        this.value = value;
    }

//     public  static Op like(String field, Object value){
////        Op.
//       return  new Op(field,value, Op.like);
//    }
//     public  static Op equals(String field, Object value){
//       return  new Op(field,value, Op.equals);
//    }
//    public  static Op in(String field, Object value){
//        return  new Op(field,value, Op.in);
//    }

//    public  static Op like(String field, Object value){
//        return  new Op(field,value, Op.like);
//    }
//
//    public  static Op notEquals(String field, Object value){
//        return  new Op(field,value, Op.notEquals);
//    }
//
//    public  static Op in(String field, Object value){
//        return  new Op(field,value, Op.in);
//    }
//
//    public  static Op isAfter(String field, Object value){
//        return  new Op(field,value, Op.isAfter);
//    }
//
//    public  static Op lessThanOrEqual(String field, Object value){
//        return  new Op(field,value, Op.lessThanOrEqual);
//    }
//
//    public  static Op lessThan(String field, Object value){
//        return  new Op(field,value, Op.lessThan);
//    }
//
//    public  static Op greaterThanOrEqual(String field, Object value){
//        return  new Op(field,value, Op.greaterThanOrEqual);
//    }
//
//    public  static Op greaterThan(String field, Object value){
//        return  new Op(field,value, Op.greaterThan);
//    }
//
//    public  static Op equals(String field, Object value){
//        return  new Op(field,value, Op.equals);
//    }


    public  static Op of(String field, Object value, String op){
        return  new Op(field,value,op);
    }
    public  static    List<Op> list(Op...ops){
        List<Op> list = ListUtil.createList(ops);
        return  list;
    }



    public  static Op like(String field, Object value){
        return  new Op(field,value, Op.like);
    }

    public  static Op notEquals(String field, Object value){
        return  new Op(field,value, Op.notEquals);
    }
    public  static Op orderBy(String field, Object value){
        return  Op.of(field,value,Op.orderBy);
    }

    public  static Op desc(String field){
        return  orderBy(field, Op.desc);
    }

    public  static Op asc(String field){
        return  orderBy(field, Op.asc);
    }
   public     String   mysqlOp(){
//        String opMark="=";
//        if (Op.equals.equals(op)) {
//            return "=";
//        }
//        mysql op
        if(ListUtil.createList(Op.eq,Op.equals,Op.is).contains(op)){
            return "=";
        }
        if(ListUtil.createList(Op.notEquals,Op.ne).contains(op)){
            return "<>";
        }
       if(ListUtil.createList(Op.greaterThan).contains(op)){
           return ">";
       }
       if(ListUtil.createList(Op.lessThan).contains(op)){
           return "<";
       }
       if(ListUtil.createList(Op.greaterThanOrEqual).contains(op)){
           return ">=";
       }
       if(ListUtil.createList(Op.lessThanOrEqual).contains(op)){
           return "<=";
       }
       if(ListUtil.createList(Op.like).contains(op)){
           return Op.like;
       }

        return "=";
    }

//    ListUtil.createList(Op.eq,Op.equals);
   String  mysqlOpCondition(){
//        String opMark="=";
//        if (Op.equals.equals(op)) {
//
//        }
        String mysqlOp = mysqlOp();
       String  mysqlOpRow= "  `{field}` {mysqlOp}  {value} "
                .replace("{field}",field)
                .replace("{value}",""+value)
                .replace("{mysqlOp}",mysqlOp)
//                .replace("{}",field)
//                .replace("{}",field)
                ;
       return mysqlOpRow;
    }

//    public  static Op orderBy(String field){
//        return  Op.of(field,null,Op.orderBy);
//    }
//     Op.of(k.orderBy,Op.orderBy,Op.desc)

    public  static Op in(String field, Object value){
        return  new Op(field,value, Op.in);
    }

    public  static Op isAfter(String field, Object value){
        return  new Op(field,value, Op.isAfter);
    }

    public  static Op lessThanOrEqual(String field, Object value){
        return  new Op(field,value, Op.lessThanOrEqual);
    }

    public  static Op lessThan(String field, Object value){
        return  new Op(field,value, Op.lessThan);
    }

    public  static Op greaterThanOrEqual(String field, Object value){
        return  new Op(field,value, Op.greaterThanOrEqual);
    }

    public  static Op greaterThan(String field, Object value){
        return  new Op(field,value, Op.greaterThan);
    }

    public  static Op equals(String field, Object value){
        return  new Op(field,value, Op.equals);
    }

    public  static Op eq(String field, Object value){
        return  new Op(field,value, Op.equals);
    }

    public  static Op le(String field, Object value){
        return  new Op(field,value, Op.lessThanOrEqual);
    }

    public  static Op lt(String field, Object value){
        return  new Op(field,value, Op.lessThan);
    }

    public  static Op ge(String field, Object value){
        return  new Op(field,value, Op.greaterThanOrEqual);
    }

    public  static Op gt(String field, Object value){
        return  new Op(field,value, Op.greaterThan);
    }

    public  static Op ne(String field, Object value){
        return  new Op(field,value, Op.notEquals);
    }

//    public  static Op in(String field, Object value){
//        return  new Op(field,value, Op.in);
//    }

    public  static Op nin(String field, Object value){
        return  new Op(field,value, Op.notEquals);
    }

    public  static Op is(String field, Object value){
        return  new Op(field,value, Op.equals);
    }

    public  static Op isNot(String field, Object value){
        return  new Op(field,value, Op.notEquals);
    }

    public  static Op regex(String field, Object value){
        return  new Op(field,value, Op.like);
    }

    public  static Op exists(String field, Object value){
        return  new Op(field,value, Op.in);
    }



    public Op(String field, Object value) {
        this.field = field;
        this.op =k.equals;
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    @Override
    public String toString() {
        return "Op{" +
                "field='" + field + '\'' +
                ", op='" + op + '\'' +
                ", value=" + value +
                '}';
    }
}
