package top.starp.util;

//import com.starp.exam.base.ExamConstant;

import java.util.*;

//implements ExamConstant
public class SqlUtil {

  public static String   getDropTableSql(String  tableName){
        String dropTpl= "DROP TABLE IF EXISTS `{tableName}`;\n";

        String  dropSql=  dropTpl
//                .replace("{ddlRows}",ddlRows)
                .replace("{tableName}",tableName)
                ;
        return  dropSql;
//        if(!doDrop){
//            dropSql="";
//        }
    }

    public static String getCreateTableSql  ( String tableName,Map<String ,Object>oneObj){
       return getCreateTableSql(tableName,oneObj,false);
    }
    public static String getCreateTableSql  ( String tableName,Map<String ,Object>oneObj,Boolean doDrop){
      if(doDrop==null){
          doDrop=false;
      }
//        String tableName="";
//        Map<String ,Object>oneObj=new HashMap<>();
        Set<String> keys = oneObj.keySet();
//        String ddlRows="";
        List<String >ddlRowList=new ArrayList<>();
        for (String key : keys) {
          String  ddlRow= " `{key}` varchar(255) DEFAULT NULL ".replace("{key}",key);
            ddlRowList.add(ddlRow);
//            ddlRows+=ddlRow;
        }

//        ddlRowList.stream().jo
//        ",\n".j
        String ddlRows = String.join(",\n", ddlRowList);
//        String dropTpl= "DROP TABLE IF EXISTS `{tableName}`;\n";
//
//
//        String  dropSql=  dropTpl
//                .replace("{ddlRows}",ddlRows)
//                .replace("{tableName}",tableName)
//                ;


        String dropSql = getDropTableSql(tableName);
        if(!doDrop){
            dropSql="";
        }
       String  ddlTpl= " {dropSql} \n " +
                "CREATE TABLE `{tableName}` (\n" +
                "  {ddlRows}" +
                " \n " +
               " ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
      String  ddlSql=  ddlTpl
                .replace("{ddlRows}",ddlRows)
                .replace("{tableName}",tableName)
                .replace("{dropSql}",dropSql)
        ;
      return ddlSql;
    }
//    public static void main(String[] args) {
////      Map<String,String>  data_map={'a':'1',
////                'b':'2',
////                'name':'2',
////                'id':'2',
////                'sex':'2',
////                'class_name':'2',
////    }
//        Map<String,String>  data_map=new HashMap<>();
//
//        data_map.put("a","1");
//        data_map.put("b","2");
//        data_map.put("name","2");
//        data_map.put("id","2");
//        data_map.put("sex","2");
//        data_map.put("class_name","2");
//        data_map.put("table_name","table_name");
//        data_map.put("idNull",null);
////        data_map.put(id_col_name_key,"id_col_name_key");
//        final String where_condition = to_where_condition(data_map);
////        System.out.println(where_condition);
//
//        List<Map<String,String >>objs=new ArrayList<>();
//        objs.add(data_map);
//        String insert_into_many_sql = to_insert_into_many_sql("table_name", objs);
////        System.out.println("insert_into_many_sql");
////        System.out.println(insert_into_many_sql);
//        /*
//
//        insert_into_many_sql
// INSERT INTO  table_name
// (`a`,   `idNull`,   `b`,   `sex`,   `name`,   `id_col_name`,   `id`,   `class_name`,   `table_name`)
// VALUES ('1',null,'2','2','2','id_col_name_key','2','2','table_name') ;
//
//        insert_into_many_sql
// INSERT INTO  table_name
// (`a`,   `b`,   `sex`,   `name`,   `id_col_name`,   `id`,   `class_name`,   `table_name`)
// VALUES ('1','2','2','2','id_col_name_key','2','2','table_name') ;
//         */
//
//
//        /*
//        update_sql
//update `table_name`
// set    a='1'
//  ,  b='2'
//  ,  sex='2'
//  ,  name='2'
//  ,  id_col_name='id_col_name_key'
//  ,  id='2'
//  ,  class_name='2'
//  ,  table_name='table_name'
//         */
//        String update_sql = to_update_sql(data_map);
//
//        System.out.println("update_sql");
//        System.out.println(update_sql);
////        update_set=to_update_set(data_map)
////        print(update_set)
//
//
//      String  update_set_where_id=to_update_set_where_id(data_map,"id");
////        print(update_set_where_id)
//        System.out.println(update_set_where_id);
//           /*
//        set    a='1'
//  ,  b='2'
//  ,  sex='2'
//  ,  name='2'
//  ,  class_name='2'
//
// where id  =  '2'
//
//         */
//
//    }
    public  static String to_where_condition(Map<String,String>data_map){
        List<String> conditions=new ArrayList<>();
        data_map.forEach((k,v)->{
           String tpl= " {k}='{v}' \n ";
//           if(v!=null){
//               tpl=tpl.replace("{k}",k).replace("{v}",v);
//               conditions.add(tpl);
//           }
                    if(v!=null){
                        String   one_condition= tpl
                                .replace("{k}",k)
                                .replace("{v}",v)
                                ;
                        conditions.add(one_condition);
                    }

//           String one_condition=f" {k}='{v}' \n "
        });
       String where_condition= String.join("  and  ",conditions);
        return "where    "+where_condition;
    }
    public  static col_names_and_col_vals to_col_names_and_col_vals(
                                             Map<String,String>data_map){
        List<String> col_vals=new ArrayList<>();
        List<String> col_names=new ArrayList<>();
        data_map.forEach((k,v)->{
            String tpl= " {k}='{v}' \n ";
//            k
            col_names.add("`"+k+"`");
            col_vals.add("'"+v+"'");

//           String one_condition=f" {k}='{v}' \n "
        });
        final col_names_and_col_vals col_names_and_col_vals = new col_names_and_col_vals();
        col_names_and_col_vals.setCol_names(col_names);
        col_names_and_col_vals.setCol_vals(col_vals);
//
        return   col_names_and_col_vals;
//        return f" INSERT INTO  {table_name } \n ({col_names_str})  \n VALUES ({col_vals_str}) ;"
    }

    /**
     * list ['val1' ,'val2'   ]
     * @param data_map
     * @return
     */
    public  static  List<String>  to_col_vals(
            Map<String,String>data_map){
        List<String> col_vals=new ArrayList<>();
//        List<String> col_names=new ArrayList<>();
        data_map.forEach((colName,val)->{
            String tpl= " {k}='{v}' \n ";
//            k
            if(val==null){
                col_vals.add(null);
            }else{
                col_vals.add("'"+val+"'");
            }

        });

        return col_vals;

    }

    public  static String to_insert_into_many_sql_str_obj(String table_name,
                                                  List<   Map<String,Object>>objs
    ){

//        MapUtil.hashMapListToMapList(objs);
        List<Map<String, String>> maps = MapUtil.convertToStringMapList(objs);
        return  to_insert_into_many_sql(table_name,maps);
    }


    public  static String to_insert_into_many_sql(String table_name,
                                                  List<   Map<String,String>>objs
                                           ){
        Map<String,String> data_map=objs.get(0);
        final col_names_and_col_vals col_names_and_col_vals
                = to_col_names_and_col_vals(data_map);
        List<String> col_names = col_names_and_col_vals.getCol_names();
        List<String> col_vals_first = col_names_and_col_vals.getCol_vals();
        List<String>  col_vals_str_list=new ArrayList<>();
        for (Map<String, String> obj : objs) {
            List<String> col_vals = to_col_vals(obj);
            String col_vals_str = String.join(",", col_vals);
//            String
            col_vals_str="({col_vals_str})".replace("{col_vals_str}",col_vals_str);
            col_vals_str_list.add(col_vals_str);
        }
//        col_names_str=", ".join(col_names);
     String col_names_str=   String.join(",   ",col_names);
     String col_vals_str_list_str=   String.join(", \n ",col_vals_str_list);
        return " INSERT INTO  {table_name} \n ({col_names_str})  \n VALUES {col_vals_str_list_str} ;"
                .replace("{table_name}",table_name)
                .replace("{col_names_str}",col_names_str)
                .replace("{col_vals_str_list_str}",col_vals_str_list_str)
        ;

    }

    public  static String  to_update_sql(  Map<String ,String > data_map){
        String id_col_name = data_map.get(id_col_name_key);
        String update_set = SqlUtil.to_update_set(data_map
                , id_col_name);

        String table_name = data_map.get(table_name_key);
       return  "update `{table_name}` \n "
                .replace("{table_name}",table_name)+update_set;
    }

  static   String  table_name= "table_name";
    static  String  table_name_key= "table_name";
    static String  id_col_name_key= "id_col_name";
    /**
     * update_sql
     * update `table_name`
     *  set    a='1'
     *   ,  b='2'
     *   ,  sex='2'
     *   ,  name='2'
     *   ,  id_col_name='id_col_name_key'
     *   ,  id='2'
     *   ,  class_name='2'
     *   ,  table_name='table_name'
     *
     * @param data_map
     * @param id_col_name
     * @return
     */
    /*
    update_sql
update `table_name`
 set    a='1'
  ,  b='2'
  ,  sex='2'
  ,  name='2'
  ,  id_col_name='id_col_name_key'
  ,  id='2'
  ,  class_name='2'
  ,  table_name='table_name'

     */
    public  static String to_update_set(
                                        Map<String ,String >data_map
            ,String id_col_name
    ){



        List<String> conditions=new ArrayList<>();

        for (Map.Entry<String, String> stringStringEntry : data_map.entrySet()) {
            String key = stringStringEntry.getKey();
            String value = stringStringEntry.getValue();
            if (key.equals(id_col_name)){
                continue;
            }

            if(value==null){
                continue;
            }
            String tpl= " {k}='{v}' \n ";
            String   one_condition= tpl
                    .replace("{k}",key)
                    .replace("{v}",value)
                    ;
            conditions.add(one_condition);
        }



        String where_condition = String.join(" , ", conditions);
        return "set   "+where_condition;



    }

    /**
     * set    a='1'
     *   ,  b='2'
     *   ,  sex='2'
     *   ,  name='2'
     *   ,  class_name='2'
     *
     *  where id  =  '2'
     * @param data_map
     * @param id_col_name
     * @return
     */
    public  static String to_update_set_where_id(
            Map<String ,String >data_map
            ,String id_col_name
    ){



        List<String> conditions=new ArrayList<>();

        for (Map.Entry<String, String> stringStringEntry : data_map.entrySet()) {
            String key = stringStringEntry.getKey();
            String value = stringStringEntry.getValue();
            if (key.equals(id_col_name)){
                continue;
            }
            if(value==null){
                continue;
            }

            String tpl= " {k}='{v}' \n ";
            String   one_condition= tpl
                    .replace("{k}",key)
                    .replace("{v}",value)
                    ;
            conditions.add(one_condition);
        }

//        where_condition=" , ".join(conditions)
//                ;

        String where_condition = String.join(" , ", conditions);
//        return "set   "+where_condition;
//        data_map[id_col_name]
        String id_col_val = data_map.get(id_col_name);
       String whereIdCondition= "\n where {id_col_name}  =  '{id_col_val}' "
                .replace("{id_col_name}",id_col_name)
                .replace("{id_col_val}",id_col_val)
        ;
        return "set   "+where_condition+ whereIdCondition;

    }

    public  static String to_insert_into_sql(String table_name,
                 Map<String,String>data_map){
        List<String> col_vals=new ArrayList<>();
        List<String> col_names=new ArrayList<>();
        data_map.forEach((k,v)->{
            String tpl= " {k}='{v}' \n ";
//            k
            col_names.add("`"+k+"`");
            col_vals.add("'"+v+"'");

//           String one_condition=f" {k}='{v}' \n "
        });
     String   col_names_str  =String.join(",  ",col_names);
     String   col_vals_str = String.join(",  ",col_vals);
//        col_names_str=",  ".join(col_names)
//        String where_condition= String.join("  and  ",conditions);
//        return "where    "+where_condition;
//        # INSERT INTO 表名称 VALUES (值1, 值2,....)
     return    " INSERT INTO  {table_name} \n ({col_names_str})  \n VALUES ({col_vals_str}) ;"
                .replace("{table_name}",table_name)
                .replace("{col_names_str}",col_names_str)
                .replace("{col_vals_str}",col_vals_str)
                ;
//        return f" INSERT INTO  {table_name } \n ({col_names_str})  \n VALUES ({col_vals_str}) ;"
    }
}
