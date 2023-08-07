package top.starp.util;

import org.neo4j.driver.*;
import org.neo4j.driver.types.Node;

import java.util.ArrayList;
import java.util.List;

public class Neo4jConnectionExample {
    public  static  List<Record> list(String query, Driver driver) {
        List<Record> records = new ArrayList<>();
        try (Session session = driver.session()) {

            // 执行一个简单的查询
//            String query = "MATCH (n) RETURN n LIMIT 5";
            Result result = session.run(query);

            // 处理查询结果
            while (result.hasNext()) {
                Record record = result.next();
                records.add(record);
//                Node node = record.get("n").asNode();
//                System.out.println("Node ID: " + node.id() + ", Labels: " + node.labels() + ", Properties: " + node.asMap());
            }
            return records;
        } finally {
            // 关闭数据库连接
            driver.close();
        }


    }

    public static void main(String[] args) {

        // 连接到Neo4j数据库
        String uri = "bolt://localhost:7687";
        String username = "neo4j";
        String password = "your_password";

        Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));

        try (Session session = driver.session()) {

            // 执行一个简单的查询
            String query = "MATCH (n) RETURN n LIMIT 5";
            Result result = session.run(query);

            // 处理查询结果
            while (result.hasNext()) {
                Record record = result.next();
                Node node = record.get("n").asNode();
                System.out.println("Node ID: " + node.id() + ", Labels: " + node.labels() + ", Properties: " + node.asMap());
            }
        }

        // 关闭数据库连接
        driver.close();
    }
}
