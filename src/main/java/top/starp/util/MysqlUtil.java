package top.starp.util;

//import cn.hutool.json.JSONObject;

import com.alibaba.fastjson.JSONObject;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlUtil {

    public static List<Map<String, Object>> select(String query, Connection connection) throws SQLException {
        try (       // 创建Statement对象
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);) {
            List<Map<String, Object>> resultList = MysqlUtil.toResultList(resultSet);
            return resultList;
        } catch (Exception e) {
//            log.error(e);
            StringUtils.printException(e);
            return new ArrayList<>();
        }
//        finally {
//
//        }


        // 关闭结果集和Statement对象
//        resultSet.close();
//        statement.close();
//        return resultList;
    }

    public static String yesterdayNight(String your_date_column
            , String fromHour
            , String toHour) {
        if (fromHour == null) {
            fromHour = "11";
        }
        if (toHour == null) {
            toHour = "6";
        }

//        TableNames.xueSheng学生基础信息

        String tpl =
                " {your_date_column}  >= DATE_SUB(CURDATE(), INTERVAL 1 DAY) + INTERVAL {fromHour} HOUR\n" +
                        "  AND {your_date_column}  <= CURDATE() + INTERVAL {toHour} HOUR \n ";
        String sql = tpl
                .replace("{your_date_column}", your_date_column)
                .replace("{fromHour}", fromHour)
                .replace("{toHour}", toHour);
        return sql;
    }

    public static String fromHourToNextDayHour(String your_date_column, Integer from, Integer toHour) {
        if (from == null) {
            from = 11;
        }
        if (toHour == null) {
            toHour = 6;
        }
        String tpl = "  {your_date_column} >= DATE_ADD(CURDATE(), INTERVAL {from} HOUR)\n" +
                "  AND {your_date_column} <= DATE_ADD(DATE_ADD(CURDATE(), INTERVAL 1 DAY), INTERVAL {toHour} HOUR) ";
        String sql = tpl
                .replace("{your_date_column}", your_date_column)
                .replace("{from}", "" + from)
                .replace("{toHour}", "" + toHour);
        return sql;

    }

    public static String limit(Integer limit) {
        return "  limit   " + limit;
    }

    public static String page(Integer pageNumber, Integer pageSize) {
        if (pageNumber == null) {
            pageNumber = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Integer offset = (pageNumber - 1) * pageSize;
        String tpl = "   LIMIT {pageSize} OFFSET {offset}  "
                .replace("{pageSize}", "" + pageSize)
                .replace("{offset}", "" + offset);
        return tpl;
    }

    public static void main2(String[] args) throws FileNotFoundException, SQLException {

        String confPath = Common.mysql_conf_path_baian;
        Connection connection = MysqlUtil.getConnection(confPath);


        String tableName = TableNames.chuRuQinShi7天出入寝室记录;
        String 出入方向 = "CRFX_1_5";
        String whereCol = 出入方向;
//        whre

        String 出入时间 = "CRSJ_1_4";
//        String fromHourToNextDayHour = fromHourToNextDayHour(出入时间, 11, 6);
//        String fromHourToNextDayHour = fromHourToNextDayHour(出入时间, 10, 6);
//        String fromHourToNextDayHour = fromHourToNextDayHour(出入时间, 10+12, 9);
//        String fromHourToNextDayHour = yesterdayNight(出入时间,"11","6");
        String fromHourToNextDayHour = yesterdayNight(出入时间, "9", "6");
//        and {fromHourToNextDayHour}
        // 执行查询语句

//        {limit}
        String query = ("SELECT * FROM {tableName} where  `{whereCol}` = 'out'  " +
                "  and {fromHourToNextDayHour}  {page} ")
                .replace("{tableName}", tableName)
                .replace("{whereCol}", whereCol)
                .replace("{fromHourToNextDayHour}", fromHourToNextDayHour)
//                .replace("{limit}",limit(999))
                .replace("{page}", page(1, 11));
        String queryStu晚出 = "\n" +
                "SELECT\n" +
                "学生信息.XM_1_5 as 姓名\n" +
                ",  FROM_UNIXTIME(宿舍出入.CRSJ_1_4) as 出入时间格式化\n" +
                "\n" +
                ",学生信息.XH_1_4 as 学号\n" +
                ",学生信息.YDDH_1_16 as 电话\n" +
                "\n" +
                ", 学生信息.ZYMC_1_20 as 专业\n" +
                ", 学生信息.YXMC_1_18 as 学院\n" +
                "\n" +
                ", 宿舍出入.CRM_1_3 as 地点\n" +
                ", 宿舍出入.CRSJ_1_4 as 出入时间\n" +
                "\n" +
                ",学生信息.MZ_1_1\n" +
                ",学生信息.BJBM_1_9\n" +
                ",学生信息.*\n" +
                "FROM chaxun_jieguoji1_1_8CB115 as 宿舍出入\n" +
                "JOIN chaxun_jieguoji1_1_9CD67C as 学生信息\n" +
                "ON 学生信息.XH_1_4 = 宿舍出入.YHID_1_2\n" +
                "WHERE 宿舍出入.CRSJ_1_4\n" +
                "BETWEEN UNIX_TIMESTAMP(DATE_SUB(CURDATE(), INTERVAL 1 DAY) + INTERVAL 23 HOUR + INTERVAL 30 MINUTE)\n" +
                "AND UNIX_TIMESTAMP(CURDATE() + INTERVAL 6 HOUR + INTERVAL 0 MINUTE)";

        query = queryStu晚出;
        System.out.println("query");
        System.out.println(query);

        List<Map<String, Object>> resultList = MysqlUtil.select(query, connection);
        System.out.println("resultList");
        StringUtils.printList(resultList);

//
        MysqlUtil.closeConnection(connection);

//        MysqlUtil.

    }

    public static void closeConnection(Connection connection) {
        // 关闭数据库连接
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException, SQLException {

        String sql轨迹3000="SELECT  * from chaxun_jieguoji1_1_A02A2C order by FSSJ_1_11 DESC  limit 3000\n";
        String 不在学校的Sql ="" +
                "\n" +
                "select XH_1_1 as stu_num\n" +
                ",RELATED_TABLE_NAME_1_18 as event_type\n" +
                ",ACT_1_10 as act\n" +
                ",PLACE_1_9 as place\n" +
                ",FSSJ_1_11 as occurrence_time\n" +
                ",FROM_UNIXTIME(FSSJ_1_11) AS occ_time_str\n" +
                "from\n" +
                "chaxun_jieguoji1_1_A02A2C\n" +
                "where\n" +
                "from_unixtime(FSSJ_1_11) >= (curdate() - interval 1 day - interval 12 hour)\n" +
                "or\n" +
                "date_format(curdate(),'%w') in (6, 7)\n" +
                "and\n" +
                "from_unixtime(FSSJ_1_11) >= subdate(subdate(curdate(),date_format(curdate(),'%w') - 1),3)\n" +
                "\n";

        try(
                Connection connection = MysqlUtil.getConnection(Common.mysql_conf_path_baian);

        ){
//            List<Map<String, Object>> select = MysqlUtil.select(不在学校的Sql, connection);
            List<Map<String, Object>> select = MysqlUtil.select(sql轨迹3000, connection);
            Map<String, Object> oneObj = select.get(0);
            String nowTimeStr = TimeUtil.nowTimeStr();
            String  tableName=k.gui_ji+"_"+nowTimeStr;
            String createTableSql = SqlUtil.getCreateTableSql(tableName, oneObj,true);
            log.info("createTableSql");
            log.info(createTableSql);


            String insertIntoManySqlStrObj = SqlUtil.to_insert_into_many_sql_str_obj(tableName, select);
//            MysqlUtil.update(insertIntoManySqlStrObj,connection);

            createTableDo(createTableSql,insertIntoManySqlStrObj);

        }
    }

   public  static void   createTableDo(String  sql
   , String insertIntoManySqlStrObj) throws FileNotFoundException {

//        MysqlUtil.in
//        SqlUtil.to_insert_into_many_sql(k.guiji)
    String  connConf=   Common.mysql_conf_path_db_brain_teng_xun;
        try(
                Connection connection = MysqlUtil.getConnection(Common.mysql_conf_path_db_brain_teng_xun);
        ){
            DatabaseMetaData metaData = connection.getMetaData();
//            metaData.getTables()





            int update = MysqlUtil.update(sql, connection);
            log.info("inse "+update);

//            MapUtil.getInteger()





//            List<Map<String, Object>> select = MysqlUtil.select(不在学校的Sql, connection);
//            SqlUtil.getCreateTableSql(k.gui_ji_not_in_school,);

            int update1 = MysqlUtil.update(insertIntoManySqlStrObj, connection);
            log.info("update1 insertIntoManySqlStrObj  "+update1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public static int update(String sql, Connection connectionTenXun) {
        try (
                Statement stmt = connectionTenXun.createStatement();
        ) {
            // 执行SQL语句
            int insertedCnt = stmt.executeUpdate(sql);
            return insertedCnt;
        } catch (SQLException e) {
//            log.error(e);
//            e.getNextException()
            log.error("sql  "+sql);
//            log.error(sql);
            StringUtils.printException(e);
            throw new RuntimeException(e);
        }

    }

    public static Connection getConnection(String confPath) throws FileNotFoundException {
//        JSONObject conf = JsonUtil.filePathToJsonObj(confPath);
        Map conf = JsonUtil.filePathToMap(confPath);
//        JSONObject jsonObject = JsonUtil.filePathToJsonObj(confPath);
        String host = MapUtil.getString(conf, k.host);
        Integer port = MapUtil.getInteger(conf, k.port,3306);
//        String portStr = MapUtil.getString(conf, k.port);
////        Integer port= portStr.
//        int port = Integer.parseInt(portStr);
        String databaseName = MapUtil.getString(conf, k.db_name);
//        databaseName=k.brain;
        String username = MapUtil.getString(conf, k.username);
        String password = MapUtil.getString(conf, k.password);
//        String host = MapUtil.getString(conf, k.host);
//        String host = MapUtil.getString(conf, k.host);


//        String host = conf.getStr(k.host);
//        Integer port = conf.getInt(k.port);
//        String databaseName = conf.getStr(k.db_name);
////        String port = conf.getStr(k.port);
//        String username = conf.getStr(k.username);
//        String password = conf.getStr(k.password);
//        String host = conf.getStr(k.host);
        String connectionString = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
//        String url = conf.getStr(k.url,connectionString);
        String url = MapUtil.getString(conf, k.url, connectionString);


//        String host = conf.getStr(k.host);
//        Integer port = conf.getInt(k.port);
//        String databaseName = conf.getStr(k.db_name);
////        String port = conf.getStr(k.port);
//        String username = conf.getStr(k.username);
//        String password = conf.getStr(k.password);
////        String host = conf.getStr(k.host);
//        String connectionString = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
//        String url = conf.getStr(k.url,connectionString);


//        TableNames.chaxun_jieguoji1_1_8FC90B
        // JDBC连接字符串

//        String connectionString = connectionString(confPath);
        // 声明数据库连接对象
//        Connection connection = null;

        try {
            // 加载JDBC驱动程序
            // 加载JDBC驱动程序
            Class.forName("com.mysql.jdbc.Driver");
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Class.forName("com.mysql.cj.jdbc.Driver");

            // 建立数据库连接
            Connection connection = DriverManager.getConnection(connectionString, username, password);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
//        public static String connectionString(String confPath) throws FileNotFoundException {
////        JSONObject conf = JsonUtil.filePathToJsonObj(Common.mysql_conf_path_baian);
//        JSONObject conf = JsonUtil.filePathToJsonObj(confPath);
//        String host = conf.getStr(k.host);
//        Integer port = conf.getInt(k.port);
//        String databaseName = conf.getStr(k.db_name);
////        String port = conf.getStr(k.port);
//        String username = conf.getStr(k.username);
//        String password = conf.getStr(k.password);
////        String host = conf.getStr(k.host);
//        String url = conf.getStr(k.url);
////        k.accessKey
//
//        // JDBC连接字符串
//        String connectionString = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
//        return connectionString;
//    }


    public static List<Map<String, Object>> toResultList(ResultSet resultSet) throws SQLException {
        List<Map<String, Object>> resultList = new ArrayList<>();


        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (resultSet.next()) {
            Map<String, Object> rowMap = new HashMap<>();

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object columnValue = resultSet.getObject(i);

                rowMap.put(columnName, columnValue);
            }

            resultList.add(rowMap);
        }
        return resultList;
    }

}
