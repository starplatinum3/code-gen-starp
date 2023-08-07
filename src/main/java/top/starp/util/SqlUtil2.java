package top.starp.util;


import com.google.common.collect.Lists;

import java.text.ParseException;
import java.util.*;

public class SqlUtil2 {
//
    public static String getDropTableSql(String tableName) {
        String dropTpl = "DROP TABLE IF EXISTS `{tableName}`;\n";

        return dropTpl
                .replace("{tableName}", tableName);
    }


    @SuppressWarnings(k.unused)
    String minusDays3() {
        return TimeUtil.toBetweenAndSql(
                TimeUtil.minusDays(3)
        );
    }

    @SuppressWarnings(k.unused)
    public static String getCreateTableSql(String tableName, Map<String, Object> oneObj) {

        return getCreateTableSql(tableName, oneObj, false);
    }

    public static String getCreateTableSql(String tableName, Map<String, Object> oneObj, Boolean doDrop) {
        if (doDrop == null) {
            doDrop = false;
        }
        Set<String> keys = oneObj.keySet();
        List<String> ddlRowList = new ArrayList<>();
        for (String key : keys) {
            String ddlRow = " `{key}` varchar(255) DEFAULT NULL ".replace("{key}", key);
            ddlRowList.add(ddlRow);
        }

        String ddlRows = String.join(",\n", ddlRowList);


        String dropSql = getDropTableSql(tableName);
        if (!doDrop) {
            dropSql = "";
        }
        String ddlTpl = " {dropSql} \n " +
                "CREATE TABLE `{tableName}` (\n" +
                "  {ddlRows}" +
                " \n " +
                " ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
        return ddlTpl
                .replace("{ddlRows}", ddlRows)
                .replace("{tableName}", tableName)
                .replace("{dropSql}", dropSql);
    }

    public static String to_where_condition(Map<String, String> data_map) {
        List<String> conditions = new ArrayList<>();
        data_map.forEach((k, v) -> {
            String tpl = " {k}='{v}' \n ";
            if (v != null) {
                String one_condition = tpl
                        .replace("{k}", k)
                        .replace("{v}", v);
                conditions.add(one_condition);
            }

        });
        String where_condition = String.join("  and  ", conditions);
        return "where    " + where_condition;
    }

   static void colNamesColValuesSetUp(Map<String, String> data_map){
        List<String> col_vals = new ArrayList<>();
        List<String> col_names = new ArrayList<>();
        data_map.forEach((k, v) -> {
            String tpl = " {k}='{v}' \n ";
            col_names.add("`" + k + "`");
            col_vals.add("'" + v + "'");
        });
    }

    public static col_names_and_col_vals to_col_names_and_col_vals(
            Map<String, String> data_map) {
        List<String> col_vals = new ArrayList<>();
        List<String> col_names = new ArrayList<>();
        data_map.forEach((k, v) -> {
            String tpl = " {k}='{v}' \n ";
            col_names.add("`" + k + "`");
            col_vals.add("'" + v + "'");

        });
//        colNamesColValuesSetUp
        final col_names_and_col_vals col_names_and_col_vals = new col_names_and_col_vals();
        col_names_and_col_vals.setCol_names(col_names);
        col_names_and_col_vals.setCol_vals(col_vals);
        return col_names_and_col_vals;
    }

    /**
     * list ['val1' ,'val2'   ]
     *
     * @param data_map Map<String,String>data_map
     * @return List<String>  to_col_vals
     */
    public static List<String> to_col_vals(
            Map<String, String> data_map) {
        List<String> col_vals = new ArrayList<>();
        data_map.forEach((colName, val) -> {
            String tpl = " {k}='{v}' \n ";
            if (val == null) {
                col_vals.add(null);
            } else {
                col_vals.add("'" + val + "'");
            }

        });

        return col_vals;

    }

    public static String to_insert_into_many_sql_str_obj(String table_name,
                                                         List<Map<String, Object>> objs
    ) {

        List<Map<String, String>> maps = MapUtil.convertToStringMapList(objs);
        return to_insert_into_many_sql(table_name, maps);
    }


    public static String to_insert_into_many_sql(String table_name,
                                                 List<Map<String, String>> objs
    ) {
        Map<String, String> data_map = objs.get(0);
        final col_names_and_col_vals col_names_and_col_vals
                = to_col_names_and_col_vals(data_map);
        List<String> col_names = col_names_and_col_vals.getCol_names();
        List<String> col_vals_first = col_names_and_col_vals.getCol_vals();
        List<String> col_vals_str_list = new ArrayList<>();
        for (Map<String, String> obj : objs) {
            List<String> col_vals = to_col_vals(obj);
            String col_vals_str = String.join(",", col_vals);
            col_vals_str = "({col_vals_str})".replace("{col_vals_str}", col_vals_str);
            col_vals_str_list.add(col_vals_str);
        }
        String col_names_str = String.join(",   ", col_names);
        String col_vals_str_list_str = String.join(", \n ", col_vals_str_list);
        return " INSERT INTO  {table_name} \n ({col_names_str})  \n VALUES {col_vals_str_list_str} ;"
                .replace("{table_name}", table_name)
                .replace("{col_names_str}", col_names_str)
                .replace("{col_vals_str_list_str}", col_vals_str_list_str)
                ;

    }

    @SuppressWarnings(k.unused)
    public static String to_update_sql(Map<String, String> data_map) {
        String id_col_name = data_map.get(id_col_name_key);
        String update_set = SqlUtil2.to_update_set(data_map
                , id_col_name);

        String table_name = data_map.get(table_name_key);
        return "update `{table_name}` \n "
                .replace("{table_name}", table_name) + update_set;
    }




    /**
     * update_sql
     * update `table_name`
     * set    a='1'
     * ,  b='2'
     * ,  sex='2'
     * ,  name='2'
     * ,  id_col_name='id_col_name_key'
     * ,  id='2'
     * ,  class_name='2'
     * ,  table_name='table_name'
     *<p>
     *
     *     update_sql
     * update `table_name`
     *  set    a='1'
     *   ,  b='2'
     *   ,  sex='2'
     *   ,  name='2'
     *   ,  id_col_name='id_col_name_key'
     *   ,  id='2'
     *   ,  class_name='2'
     *   ,  table_name='table_name'
     * <p>
     *
     * @param data_map    Map<String ,String >data_map
     * @param id_col_name ,String id_col_name
     * @return String to_update_set(
     */

    public static String to_update_set(
            Map<String, String> data_map
            , String id_col_name
    ) {


        String where_condition = whereConditionsGet(data_map, id_col_name);
        return "set   " + where_condition;


    }

    void d(String timeCol) throws ParseException {

        String whereSql = betweenTime(timeCol, "2020-01-01", "2020-01-02", "yyyy-MM-dd");
    }

    public static void main(String[] args) {


        HashMap<String, List<Integer>> hashMap = new HashMap<String, List<Integer>>() {{
            put("1", Lists.newArrayList(1, 2, 3));
            put("2", Lists.newArrayList(1, 2, 3));
            put("3", Lists.newArrayList(1, 2, 3));
        }};
        Map<String, List<Integer>> stringListMap = u.mapOf(
                u.p("1", u.list(1, 23))
        );

        Map<String, Map<String, List<Integer>>> stringMapMap = u.mapOf(
                u.p("da", u.mapOf(
                        u.p("da", u.list(1, 3, 13, 1))
                ))
                , u.p("da", u.mapOf(
                        u.p(
                                "da", u.list(1, 3, 13, 1)
                        )

                ))

        );
    }

    /**
     * set    a='1'
     * ,  b='2'
     * ,  sex='2'
     * ,  name='2'
     * ,  class_name='2'
     * <p>
     * where id  =  '2'
     *
     * @param data_map    Map<String ,String >data_map
     * @param id_col_name String id_col_name
     * @return String to_update_set_where_id(
     */
    public static String to_update_set_where_id(
            Map<String, String> data_map
            , String id_col_name
    ) {


        String where_condition = whereConditionsGet(data_map, id_col_name);
        String id_col_val = data_map.get(id_col_name);
        String whereIdCondition = "\n where {id_col_name}  =  '{id_col_val}' "
                .replace("{id_col_name}", id_col_name)
                .replace("{id_col_val}", id_col_val);
        return "set   " + where_condition + whereIdCondition;

    }

    private static String  whereConditionsGet(Map<String, String> data_map, String id_col_name) {
        List<String> conditions = new ArrayList<>();

        for (Map.Entry<String, String> stringStringEntry : data_map.entrySet()) {
            String key = stringStringEntry.getKey();
            String value = stringStringEntry.getValue();
            if (key.equals(id_col_name)) {
                continue;
            }
            if (value == null) {
                continue;
            }

            String tpl = " {k}='{v}' \n ";
            String one_condition = tpl
                    .replace("{k}", key)
                    .replace("{v}", value);
            conditions.add(one_condition);
        }


        return String.join(" , ", conditions);
    }

    /**
     * // 示例用法
     * String key = "CRSJ_1_10_1_4";
     * String timeStrStart = "2023-01-01";
     * String timeStrEnd = "2023-01-05";
     * String format = "yyyy-MM-dd";
     * String sql = betweenTime(key, timeStrStart, timeStrEnd, format);
     * System.out.println(sql);
     * <p>
     * result CRSJ_1_10_1_4 between  1672502400 and 1672848000
     *
     * @param key          String key,
     * @param timeStrStart String timeStrStart,
     * @param timeStrEnd   String timeStrEnd,
     * @param format       String format
     * @return result CRSJ_1_10_1_4 between  1672502400 and 1672848000
     * @throws ParseException ParseException
     */
    public static String betweenTime(String key, String timeStrStart, String timeStrEnd, String format) throws ParseException {
        long timeStampStart = TimeUtil.strToTimestamp(timeStrStart, format);
        long timeStampEnd = TimeUtil.strToTimestamp(timeStrEnd, format);
        return String.format(" %s between  %d and %d ", key, timeStampStart, timeStampEnd);
    }

    public static void main_betweenTime(String[] args) throws ParseException {
        String key = "CRSJ_1_10_1_4";
        String timeStrStart = "2023-01-01";
        String timeStrEnd = "2023-01-05";
        String format = "yyyy-MM-dd";
        String sql = betweenTime(key, timeStrStart, timeStrEnd, format);
        System.out.println(sql);
        // NOTE:  2023/7/24 CRSJ_1_10_1_4 between  1672502400 and 1672848000

    }

    public static String to_insert_into_sql(String table_name,
                                            Map<String, String> data_map) {
        List<String> col_vals = new ArrayList<>();
        List<String> col_names = new ArrayList<>();
        data_map.forEach((k, v) -> {
            String tpl = " {k}='{v}' \n ";
            col_names.add("`" + k + "`");
            col_vals.add("'" + v + "'");

        });
        String col_names_str = String.join(",  ", col_names);
        String col_vals_str = String.join(",  ", col_vals);
        return " INSERT INTO  {table_name} \n ({col_names_str})  \n VALUES ({col_vals_str}) ;"
                .replace("{table_name}", table_name)
                .replace("{col_names_str}", col_names_str)
                .replace("{col_vals_str}", col_vals_str)
                ;
    }



    // TODO: 2023/7/25















    static String table_name = "table_name";
    static String table_name_key = "table_name";
    static String id_col_name_key = "id_col_name";

//    public static String getDropTableSql(String tableName) {
//        String dropTpl = "DROP TABLE IF EXISTS `{tableName}`;\n";
//        return dropTpl.replace("{tableName}", tableName);
//    }
//
//
//    @SuppressWarnings(k.unused)
//    String minusDays3() {
//        return TimeUtil.toBetweenAndSql(TimeUtil.minusDays(3));
//    }
//
//
//    @SuppressWarnings(k.unused)
//    public static String getCreateTableSql(String tableName, Map<String, Object> oneObj) {
//        return getCreateTableSql(tableName, oneObj, false);
//    }
//
//
//    public static String getCreateTableSql(String tableName, Map<String, Object> oneObj, Boolean doDrop) {
//        if (doDrop == null) {
//            doDrop = false;
//        }
//        Set<String> keys = oneObj.keySet();
//        List<String> ddlRowList = new ArrayList<>();
//        for (String key : keys) {
//            String ddlRow = " `{key}` varchar(255) DEFAULT NULL ".replace("{key}", key);
//            ddlRowList.add(ddlRow);
//        }
//        String ddlRows = String.join(",\n", ddlRowList);
//        String dropSql = getDropTableSql(tableName);
//        if (!doDrop) {
//            dropSql = "";
//        }
//        String ddlTpl = " {dropSql} \n " + "CREATE TABLE `{tableName}` (\n" + "  {ddlRows}" + " \n " + " ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
//        return ddlTpl.replace("{ddlRows}", ddlRows).replace("{tableName}", tableName).replace("{dropSql}", dropSql);
//    }
//
//
//    public static String toWhereCondition(Map<String, String> dataMap) {
//        List<String> conditions = new ArrayList<>();
//        dataMap.forEach((k, v) -> {
//            String tpl = " {k}='{v}' \n ";
//            if (v != null) {
//                String oneCondition = tpl.replace("{k}", k).replace("{v}", v);
//                conditions.add(oneCondition);
//            }
//        });
//        String whereCondition = String.join("  and  ", conditions);
//        return "where    " + whereCondition;
//    }
//
//
//    static void colNamesColValuesSetUp(Map<String, String> dataMap) {
//        List<String> colVals = new ArrayList<>();
//        List<String> colNames = new ArrayList<>();
//        dataMap.forEach((k, v) -> {
//            String tpl = " {k}='{v}' \n ";
//            colNames.add("`" + k + "`");
//            colVals.add("'" + v + "'");
//        });
//    }
//
//
//    public static col_names_and_col_vals toColNamesAndColVals(Map<String, String> dataMap) {
//        List<String> colVals = new ArrayList<>();
//        List<String> colNames = new ArrayList<>();
//        dataMap.forEach((k, v) -> {
//            String tpl = " {k}='{v}' \n ";
//            colNames.add("`" + k + "`");
//            colVals.add("'" + v + "'");
//        });
//        //        colNamesColValuesSetUp
//        final col_names_and_col_vals colNamesAndColVals = new col_names_and_col_vals();
//        colNamesAndColVals.setCol_names(colNames);
//        colNamesAndColVals.setCol_vals(colVals);
//        return colNamesAndColVals;
//    }
//
//
//    /**
//     * list ['val1' ,'val2'   ]
//     *
//     * @param data_map Map<String,String>data_map
//     * @return List<String>  to_col_vals
//     */
//    public static List<String> toColVals(Map<String, String> dataMap) {
//        List<String> colVals = new ArrayList<>();
//        dataMap.forEach((colName, val) -> {
//            String tpl = " {k}='{v}' \n ";
//            if (val == null) {
//                colVals.add(null);
//            } else {
//                colVals.add("'" + val + "'");
//            }
//        });
//        return colVals;
//    }
//
//
//    public static String toInsertIntoManySqlStrObj(String tableName, List<Map<String, Object>> objs) {
//        List<Map<String, String>> maps = MapUtil.convertToStringMapList(objs);
//        return to_insert_into_many_sql(tableName, maps);
//    }
//
//
//    public static String toInsertIntoManySql(String tableName, List<Map<String, String>> objs) {
//        Map<String, String> dataMap = objs.get(0);
//        final col_names_and_col_vals colNamesAndColVals = to_col_names_and_col_vals(dataMap);
//        List<String> colNames = colNamesAndColVals.getCol_names();
//        List<String> colValsFirst = colNamesAndColVals.getCol_vals();
//        List<String> colValsStrList = new ArrayList<>();
//        for (Map<String, String> obj : objs) {
//            List<String> colVals = to_col_vals(obj);
//            String colValsStr = String.join(",", colVals);
//            colValsStr = "({col_vals_str})".replace("{col_vals_str}", colValsStr);
//            colValsStrList.add(colValsStr);
//        }
//        String colNamesStr = String.join(",   ", colNames);
//        String colValsStrListStr = String.join(", \n ", colValsStrList);
//        return " INSERT INTO  {table_name} \n ({col_names_str})  \n VALUES {col_vals_str_list_str} ;".replace("{table_name}", tableName).replace("{col_names_str}", colNamesStr).replace("{col_vals_str_list_str}", colValsStrListStr);
//    }
//
//
//    @SuppressWarnings(k.unused)
//    public static String toUpdateSql(Map<String, String> dataMap) {
//        String idColName = dataMap.get(idColNameKey);
//        String updateSet = SqlUtil.to_update_set(dataMap, idColName);
//        String tableName = dataMap.get(tableNameKey);
//        return "update `{table_name}` \n ".replace("{table_name}", tableName) + updateSet;
//    }
//
//
//    /**
//     *  update_sql
//     *  update `table_name`
//     *  set    a='1'
//     *  ,  b='2'
//     *  ,  sex='2'
//     *  ,  name='2'
//     *  ,  id_col_name='id_col_name_key'
//     *  ,  id='2'
//     *  ,  class_name='2'
//     *  ,  table_name='table_name'
//     * <p>
//     *
//     *      update_sql
//     *  update `table_name`
//     *   set    a='1'
//     *    ,  b='2'
//     *    ,  sex='2'
//     *    ,  name='2'
//     *    ,  id_col_name='id_col_name_key'
//     *    ,  id='2'
//     *    ,  class_name='2'
//     *    ,  table_name='table_name'
//     *  <p>
//     *
//     *  @param data_map    Map<String ,String >data_map
//     *  @param id_col_name ,String id_col_name
//     *  @return String to_update_set(
//     */
//    public static String toUpdateSet(Map<String, String> dataMap, String idColName) {
//        String whereCondition = whereConditionsGet(dataMap, idColName);
//        return "set   " + whereCondition;
//    }
//
//
//    void d(String timeCol) throws ParseException {
//        String whereSql = betweenTime(timeCol, "2020-01-01", "2020-01-02", "yyyy-MM-dd");
//    }
//
//
//    public static void main(String[] args) {
//        HashMap<String, List<Integer>> hashMap = new HashMap<String, List<Integer>>() {
//
//            {
//                put("1", Lists.newArrayList(1, 2, 3));
//                put("2", Lists.newArrayList(1, 2, 3));
//                put("3", Lists.newArrayList(1, 2, 3));
//            }
//        };
//        Map<String, List<Integer>> stringListMap = u.mapOf(u.p("1", u.list(1, 23)));
//        Map<String, Map<String, List<Integer>>> stringMapMap = u.mapOf(u.p("da", u.mapOf(u.p("da", u.list(1, 3, 13, 1)))), u.p("da", u.mapOf(u.p("da", u.list(1, 3, 13, 1)))));
//    }
//
//
//    /**
//     * set    a='1'
//     * ,  b='2'
//     * ,  sex='2'
//     * ,  name='2'
//     * ,  class_name='2'
//     * <p>
//     * where id  =  '2'
//     *
//     * @param data_map    Map<String ,String >data_map
//     * @param id_col_name String id_col_name
//     * @return String to_update_set_where_id(
//     */
//    public static String toUpdateSetWhereId(Map<String, String> dataMap, String idColName) {
//        String whereCondition = whereConditionsGet(dataMap, idColName);
//        String idColVal = dataMap.get(idColName);
//        String whereIdCondition = "\n where {id_col_name}  =  '{id_col_val}' ".replace("{id_col_name}", idColName).replace("{id_col_val}", idColVal);
//        return "set   " + whereCondition + whereIdCondition;
//    }
//
//
//    private static String whereConditionsGet(Map<String, String> dataMap, String idColName) {
//        List<String> conditions = new ArrayList<>();
//        for (Map.Entry<String, String> stringStringEntry : dataMap.entrySet()) {
//            String key = stringStringEntry.getKey();
//            String value = stringStringEntry.getValue();
//            if (key.equals(idColName)) {
//                continue;
//            }
//            if (value == null) {
//                continue;
//            }
//            String tpl = " {k}='{v}' \n ";
//            String oneCondition = tpl.replace("{k}", key).replace("{v}", value);
//            conditions.add(oneCondition);
//        }
//        return String.join(" , ", conditions);
//    }
//
//
//    /**
//     * // 示例用法
//     * String key = "CRSJ_1_10_1_4";
//     * String timeStrStart = "2023-01-01";
//     * String timeStrEnd = "2023-01-05";
//     * String format = "yyyy-MM-dd";
//     * String sql = betweenTime(key, timeStrStart, timeStrEnd, format);
//     * System.out.println(sql);
//     * <p>
//     * result CRSJ_1_10_1_4 between  1672502400 and 1672848000
//     *
//     * @param key          String key,
//     * @param timeStrStart String timeStrStart,
//     * @param timeStrEnd   String timeStrEnd,
//     * @param format       String format
//     * @return result CRSJ_1_10_1_4 between  1672502400 and 1672848000
//     * @throws ParseException ParseException
//     */
//    public static String betweenTime(String key, String timeStrStart, String timeStrEnd, String format) throws ParseException {
//        long timeStampStart = TimeUtil.strToTimestamp(timeStrStart, format);
//        long timeStampEnd = TimeUtil.strToTimestamp(timeStrEnd, format);
//        return String.format(" %s between  %d and %d ", key, timeStampStart, timeStampEnd);
//    }
//
//
//    public static void mainBetweenTime(String[] args) throws ParseException {
//        String key = "CRSJ_1_10_1_4";
//        String timeStrStart = "2023-01-01";
//        String timeStrEnd = "2023-01-05";
//        String format = "yyyy-MM-dd";
//        String sql = betweenTime(key, timeStrStart, timeStrEnd, format);
//        System.out.println(sql);
//        // NOTE:  2023/7/24 CRSJ_1_10_1_4 between  1672502400 and 1672848000
//    }
//
//
//    public static String toInsertIntoSql(String tableName, Map<String, String> dataMap) {
//        List<String> colVals = new ArrayList<>();
//        List<String> colNames = new ArrayList<>();
//        dataMap.forEach((k, v) -> {
//            String tpl = " {k}='{v}' \n ";
//            colNames.add("`" + k + "`");
//            colVals.add("'" + v + "'");
//        });
//        String colNamesStr = String.join(",  ", colNames);
//        String colValsStr = String.join(",  ", colVals);
//        return " INSERT INTO  {table_name} \n ({col_names_str})  \n VALUES ({col_vals_str}) ;".replace("{table_name}", tableName).replace("{col_names_str}", colNamesStr).replace("{col_vals_str}", colValsStr);
//    }
//


    // TODO: 2023/7/25
//    public static String getDropTableSql(String tableName) {
//        String dropTpl = "DROP TABLE IF EXISTS `{tableName}`;\n";
//        return dropTpl.replace("{tableName}", tableName);
//    }
//
//
//    @SuppressWarnings(k.unused)
//    String minusDays3() {
//        return TimeUtil.toBetweenAndSql(TimeUtil.minusDays(3));
//    }
//
//
//    @SuppressWarnings(k.unused)
//    public static String getCreateTableSql(String tableName, Map<String, Object> oneObj) {
//        return getCreateTableSql(tableName, oneObj, false);
//    }
//
//
//    public static String getCreateTableSql(String tableName, Map<String, Object> oneObj, Boolean doDrop) {
//        if (doDrop == null) {
//            doDrop = false;
//        }
//        Set<String> keys = oneObj.keySet();
//        List<String> ddlRowList = new ArrayList<>();
//        for (String key : keys) {
//            String ddlRow = " `{key}` varchar(255) DEFAULT NULL ".replace("{key}", key);
//            ddlRowList.add(ddlRow);
//        }
//        String ddlRows = String.join(",\n", ddlRowList);
//        String dropSql = getDropTableSql(tableName);
//        if (!doDrop) {
//            dropSql = "";
//        }
//        String ddlTpl = " {dropSql} \n " + "CREATE TABLE `{tableName}` (\n" + "  {ddlRows}" + " \n " + " ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
//        return ddlTpl.replace("{ddlRows}", ddlRows).replace("{tableName}", tableName).replace("{dropSql}", dropSql);
//    }
//
//
//    public static String toWhereCondition(Map<String, String> dataMap) {
//        List<String> conditions = new ArrayList<>();
//        dataMap.forEach((k, v) -> {
//            String tpl = " {k}='{v}' \n ";
//            if (v != null) {
//                String oneCondition = tpl.replace("{k}", k).replace("{v}", v);
//                conditions.add(oneCondition);
//            }
//        });
//        String whereCondition = String.join("  and  ", conditions);
//        return "where    " + whereCondition;
//    }
//
//
//    static void colNamesColValuesSetUp(Map<String, String> dataMap) {
//        List<String> colVals = new ArrayList<>();
//        List<String> colNames = new ArrayList<>();
//        dataMap.forEach((k, v) -> {
//            String tpl = " {k}='{v}' \n ";
//            colNames.add("`" + k + "`");
//            colVals.add("'" + v + "'");
//        });
//    }
//
//
//    public static col_names_and_col_vals toColNamesAndColVals(Map<String, String> dataMap) {
//        List<String> colVals = new ArrayList<>();
//        List<String> colNames = new ArrayList<>();
//        dataMap.forEach((k, v) -> {
//            String tpl = " {k}='{v}' \n ";
//            colNames.add("`" + k + "`");
//            colVals.add("'" + v + "'");
//        });
//        //        colNamesColValuesSetUp
//        final col_names_and_col_vals colNamesAndColVals = new col_names_and_col_vals();
//        colNamesAndColVals.setColNames(colNames);
//        colNamesAndColVals.setColVals(colVals);
//        return colNamesAndColVals;
//    }
//
//
//    /**
//     * list ['val1' ,'val2'   ]
//     *
//     * @param data_map Map<String,String>data_map
//     * @return List<String>  to_col_vals
//     */
//    public static List<String> toColVals(Map<String, String> dataMap) {
//        List<String> colVals = new ArrayList<>();
//        dataMap.forEach((colName, val) -> {
//            String tpl = " {k}='{v}' \n ";
//            if (val == null) {
//                colVals.add(null);
//            } else {
//                colVals.add("'" + val + "'");
//            }
//        });
//        return colVals;
//    }
//
//
//    public static String toInsertIntoManySqlStrObj(String tableName, List<Map<String, Object>> objs) {
//        List<Map<String, String>> maps = MapUtil.convertToStringMapList(objs);
//        return toInsertIntoManySql(tableName, maps);
//    }
//
//
//    public static String toInsertIntoManySql(String tableName, List<Map<String, String>> objs) {
//        Map<String, String> dataMap = objs.get(0);
//        final col_names_and_col_vals colNamesAndColVals = toColNamesAndColVals(dataMap);
//        List<String> colNames = colNamesAndColVals.getColNames();
//        List<String> colValsFirst = colNamesAndColVals.getColVals();
//        List<String> colValsStrList = new ArrayList<>();
//        for (Map<String, String> obj : objs) {
//            List<String> colVals = toColVals(obj);
//            String colValsStr = String.join(",", colVals);
//            colValsStr = "({col_vals_str})".replace("{col_vals_str}", colValsStr);
//            colValsStrList.add(colValsStr);
//        }
//        String colNamesStr = String.join(",   ", colNames);
//        String colValsStrListStr = String.join(", \n ", colValsStrList);
//        return " INSERT INTO  {table_name} \n ({col_names_str})  \n VALUES {col_vals_str_list_str} ;".replace("{table_name}", tableName).replace("{col_names_str}", colNamesStr).replace("{col_vals_str_list_str}", colValsStrListStr);
//    }
//
//
//    @SuppressWarnings(k.unused)
//    public static String toUpdateSql(Map<String, String> dataMap) {
//        String idColName = dataMap.get(idColNameKey);
//        String updateSet = SqlUtil.toUpdateSet(dataMap, idColName);
//        String tableName = dataMap.get(tableNameKey);
//        return "update `{table_name}` \n ".replace("{table_name}", tableName) + updateSet;
//    }
//
//
//    /**
//     *  update_sql
//     *  update `table_name`
//     *  set    a='1'
//     *  ,  b='2'
//     *  ,  sex='2'
//     *  ,  name='2'
//     *  ,  id_col_name='id_col_name_key'
//     *  ,  id='2'
//     *  ,  class_name='2'
//     *  ,  table_name='table_name'
//     * <p>
//     *
//     *      update_sql
//     *  update `table_name`
//     *   set    a='1'
//     *    ,  b='2'
//     *    ,  sex='2'
//     *    ,  name='2'
//     *    ,  id_col_name='id_col_name_key'
//     *    ,  id='2'
//     *    ,  class_name='2'
//     *    ,  table_name='table_name'
//     *  <p>
//     *
//     *  @param dataMap    Map<String ,String >data_map
//     *  @param idColName ,String id_col_name
//     *  @return String to_update_set(
//     */
//    public static String toUpdateSet(Map<String, String> dataMap, String idColName) {
//        String whereCondition = whereConditionsGet(dataMap, idColName);
//        return "set   " + whereCondition;
//    }
//
//
//    void d(String timeCol) throws ParseException {
//        String whereSql = betweenTime(timeCol, "2020-01-01", "2020-01-02", "yyyy-MM-dd");
//    }
//
//
//    public static void main(String[] args) {
//        HashMap<String, List<Integer>> hashMap = new HashMap<String, List<Integer>>() {
//
//            {
//                put("1", Lists.newArrayList(1, 2, 3));
//                put("2", Lists.newArrayList(1, 2, 3));
//                put("3", Lists.newArrayList(1, 2, 3));
//            }
//        };
//        Map<String, List<Integer>> stringListMap = u.mapOf(u.p("1", u.list(1, 23)));
//        Map<String, Map<String, List<Integer>>> stringMapMap = u.mapOf(u.p("da", u.mapOf(u.p("da", u.list(1, 3, 13, 1)))), u.p("da", u.mapOf(u.p("da", u.list(1, 3, 13, 1)))));
//    }
//
//
//    /**
//     * set    a='1'
//     * ,  b='2'
//     * ,  sex='2'
//     * ,  name='2'
//     * ,  class_name='2'
//     * <p>
//     * where id  =  '2'
//     *
//     * @param dataMap    Map<String ,String >data_map
//     * @param idColName String id_col_name
//     * @return String to_update_set_where_id(
//     */
//    public static String toUpdateSetWhereId(Map<String, String> dataMap, String idColName) {
//        String whereCondition = whereConditionsGet(dataMap, idColName);
//        String idColVal = dataMap.get(idColName);
//        String whereIdCondition = "\n where {id_col_name}  =  '{id_col_val}' ".replace("{id_col_name}", idColName).replace("{id_col_val}", idColVal);
//        return "set   " + whereCondition + whereIdCondition;
//    }
//
//
//    private static String whereConditionsGet(Map<String, String> dataMap, String idColName) {
//        List<String> conditions = new ArrayList<>();
//        for (Map.Entry<String, String> stringStringEntry : dataMap.entrySet()) {
//            String key = stringStringEntry.getKey();
//            String value = stringStringEntry.getValue();
//            if (key.equals(idColName)) {
//                continue;
//            }
//            if (value == null) {
//                continue;
//            }
//            String tpl = " {k}='{v}' \n ";
//            String oneCondition = tpl.replace("{k}", key).replace("{v}", value);
//            conditions.add(oneCondition);
//        }
//        return String.join(" , ", conditions);
//    }
//
//
//    /**
//     * // 示例用法
//     * String key = "CRSJ_1_10_1_4";
//     * String timeStrStart = "2023-01-01";
//     * String timeStrEnd = "2023-01-05";
//     * String format = "yyyy-MM-dd";
//     * String sql = betweenTime(key, timeStrStart, timeStrEnd, format);
//     * System.out.println(sql);
//     * <p>
//     * result CRSJ_1_10_1_4 between  1672502400 and 1672848000
//     *
//     * @param key          String key,
//     * @param timeStrStart String timeStrStart,
//     * @param timeStrEnd   String timeStrEnd,
//     * @param format       String format
//     * @return result CRSJ_1_10_1_4 between  1672502400 and 1672848000
//     * @throws ParseException ParseException
//     */
//    public static String betweenTime(String key, String timeStrStart, String timeStrEnd, String format) throws ParseException {
//        long timeStampStart = TimeUtil.strToTimestamp(timeStrStart, format);
//        long timeStampEnd = TimeUtil.strToTimestamp(timeStrEnd, format);
//        return String.format(" %s between  %d and %d ", key, timeStampStart, timeStampEnd);
//    }
//
//
//    public static void mainBetweenTime(String[] args) throws ParseException {
//        String key = "CRSJ_1_10_1_4";
//        String timeStrStart = "2023-01-01";
//        String timeStrEnd = "2023-01-05";
//        String format = "yyyy-MM-dd";
//        String sql = betweenTime(key, timeStrStart, timeStrEnd, format);
//        System.out.println(sql);
//        // NOTE:  2023/7/24 CRSJ_1_10_1_4 between  1672502400 and 1672848000
//    }
//
//
//    public static String toInsertIntoSql(String tableName, Map<String, String> dataMap) {
//        List<String> colVals = new ArrayList<>();
//        List<String> colNames = new ArrayList<>();
//        dataMap.forEach((k, v) -> {
//            String tpl = " {k}='{v}' \n ";
//            colNames.add("`" + k + "`");
//            colVals.add("'" + v + "'");
//        });
//        String colNamesStr = String.join(",  ", colNames);
//        String colValsStr = String.join(",  ", colVals);
//        return " INSERT INTO  {table_name} \n ({col_names_str})  \n VALUES ({col_vals_str}) ;".replace("{table_name}", tableName).replace("{col_names_str}", colNamesStr).replace("{col_vals_str}", colValsStr);
//    }
//
//
//


}
