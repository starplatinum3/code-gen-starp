package top.starp.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseReader {

    public static List<Map<String, Object>>   toResultList(ResultSet resultSet) throws SQLException {
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
        return  resultList;
    }
    public static void main(String[] args) throws IOException, SQLException {
        // 数据库连接信息
//        String host = "localhost";
//        java.lang.ClassNotFoundException: com.mysql.cj.jdbc.Driver
//        int port = 3306;
//        String databaseName = "your_database_name";
//        String username = "your_username";
//        String password = "your_password";


//        JsonUtil.stringToJson()

//        String s = FileUtil.readAll(Common.mysql_conf_path_baian);
////        JsonUtil.jsonObjWriteToFile(s);
//        JSONObject jsonObject = JsonUtil.stringToJson(s);

       String  confPath= Common.mysql_conf_path_baian;
        Connection connection = MysqlUtil.getConnection(confPath);
//        MysqlUtil.

        // 创建Statement对象
//        Statement statement = connection.createStatement();

//        String tableName= TableNames.chuRuQinShi7天出入寝室记录;
        String tableName= TableNames.guiJiXueShengGuiji学生轨迹;
        // 执行查询语句
        String query = "SELECT * FROM {tableName} limit 10 ".replace("{tableName}",tableName);
//        ResultSet resultSet = statement.executeQuery(query);
////            resultSet.for
//        // 处理查询结果
//
//
////            while (resultSet.next()) {
//////                resultSet.get
////                // 读取每一行数据
////                int id = resultSet.getInt("id");
////                String name = resultSet.getString("name");
////                // 其他列...
////
////                // 对查询结果进行处理
////                System.out.println("ID: " + id + ", Name: " + name);
////            }
//
//        List<Map<String, Object>> resultList = MysqlUtil.toResultList(resultSet);

        List<Map<String, Object>> resultList = MysqlUtil.select(query, connection);
//        List<Map<String, Object>> resultList = new ArrayList<>();
//
//
//        ResultSetMetaData metaData = resultSet.getMetaData();
//        int columnCount = metaData.getColumnCount();
//
//        while (resultSet.next()) {
//            Map<String, Object> rowMap = new HashMap<>();
//
//            for (int i = 1; i <= columnCount; i++) {
//                String columnName = metaData.getColumnName(i);
//                Object columnValue = resultSet.getObject(i);
//
//                rowMap.put(columnName, columnValue);
//            }
//
//            resultList.add(rowMap);
//        }
        System.out.println("resultList");
        StringUtils.printList(resultList);

        String jsonString = JsonUtil.toJsonString(resultList);
        log.info("jsonString");
        log.info(jsonString);
//        jdbc 创建表

       String  outPath= "/home/data/{tableName}.json".replace("{tableName}",tableName);
//        FileUtil.writeToFile(outPath,jsonString);
        FileUtil.writeToFileMust(outPath,jsonString);
//        // 关闭结果集和Statement对象
//        resultSet.close();
//        statement.close();
        MysqlUtil.closeConnection(connection);


        try(
                Connection connectionTenXun = MysqlUtil.getConnection(Common.mysql_conf_path_db_brain_teng_xun);
                ){
//            MysqlUtil.
//            SqlUti
//            MapUtil.tost
            List<Map<String, String>> resultListStrStr = MapUtil.convertToStringMapList(resultList);
            String insertIntoManySql = SqlUtil.to_insert_into_many_sql(k.guiji, resultListStrStr);
            int update = MysqlUtil.update(insertIntoManySql, connectionTenXun);
            log.info("insertedCnt  "+update);

//            MysqlUtil.update()
//            Statement stmt = connectionTenXun.createStatement();
//            // 执行SQL语句
//            int res = stmt.executeUpdate(sql);
//            System.out.println("表创建成功！");
//            connectionTenXun.up
        }

//
//        JSONObject conf = JsonUtil.filePathToJsonObj(Common.mysql_conf_path_baian);
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

        // 声明数据库连接对象
//        Connection connection = null;

//        try {
//            // 加载JDBC驱动程序
//            // 加载JDBC驱动程序
//            Class.forName("com.mysql.jdbc.Driver");
////            Class.forName("com.mysql.cj.jdbc.Driver");
////            Class.forName("com.mysql.cj.jdbc.Driver");
//
//            // 建立数据库连接
////            connection = DriverManager.getConnection(connectionString, username, password);
//
//            // 创建Statement对象
//            Statement statement = connection.createStatement();
//
//            String tableName= TableNames.chuRuQinShi7天出入寝室记录;
//            // 执行查询语句
//            String query = "SELECT * FROM {tableName} limit 10 ".replace("{tableName}",tableName);
//            ResultSet resultSet = statement.executeQuery(query);
////            resultSet.for
//            // 处理查询结果
//
//
////            while (resultSet.next()) {
//////                resultSet.get
////                // 读取每一行数据
////                int id = resultSet.getInt("id");
////                String name = resultSet.getString("name");
////                // 其他列...
////
////                // 对查询结果进行处理
////                System.out.println("ID: " + id + ", Name: " + name);
////            }
//
//            List<Map<String, Object>> resultList = new ArrayList<>();
//
//
//            ResultSetMetaData metaData = resultSet.getMetaData();
//            int columnCount = metaData.getColumnCount();
//
//            while (resultSet.next()) {
//                Map<String, Object> rowMap = new HashMap<>();
//
//                for (int i = 1; i <= columnCount; i++) {
//                    String columnName = metaData.getColumnName(i);
//                    Object columnValue = resultSet.getObject(i);
//
//                    rowMap.put(columnName, columnValue);
//                }
//
//                resultList.add(rowMap);
//            }
//            System.out.println("resultList");
//            StringUtils.printList(resultList);
//
//            // 关闭结果集和Statement对象
//            resultSet.close();
//            statement.close();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            // 关闭数据库连接
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }
}
