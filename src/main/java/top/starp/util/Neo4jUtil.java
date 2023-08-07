package top.starp.util;

import org.neo4j.driver.*;

import java.util.ArrayList;
import java.util.List;

public class Neo4jUtil {

    private static final String URL = "bolt://localhost:7687";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

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
        Driver driver = createDriver();

        // 示例：插入节点
        insertNode("Person", "John Doe");

        // 示例：查询所有节点
        List<Record> nodes = listNodes(driver);
        for (Record node : nodes) {
            System.out.println("Node ID: " + node.get(0).asNode().id() + ", Labels: " + node.get(0).asNode().labels() + ", Properties: " + node.get(0).asNode().asMap());
        }

        // 示例：更新节点属性
        updateNodeProperty(1, "name", "Jane Smith");

        // 示例：删除节点
        deleteNode(2);

        driver.close();
    }

    public static Driver createDriver() {
        return GraphDatabase.driver(URL, AuthTokens.basic(USERNAME, PASSWORD));
    }

    public static void insertNode(String label, String name) {
        try (Session session = createDriver().session()) {
            String query = "CREATE (:" + label + " {name: $name})";

            session.run(query, Values.parameters("name", name));
            System.out.println("节点插入成功");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Record> listNodes(Driver driver) {
        List<Record> nodes = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (n) RETURN n";

            Result result = session.run(query);

            while (result.hasNext()) {
                Record record = result.next();
                nodes.add(record);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodes;
    }

    public static void updateNodeProperty(long nodeId, String propertyKey, String propertyValue) {
        try (Session session = createDriver().session()) {
            String query = "MATCH (n) WHERE ID(n) = $nodeId SET n." + propertyKey + " = $propertyValue";

            session.run(query, Values.parameters("nodeId", nodeId, "propertyValue", propertyValue));
            System.out.println("节点属性更新成功");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteNode(long nodeId) {
        try (Session session = createDriver().session()) {
            String query = "MATCH (n) WHERE ID(n) = $nodeId DELETE n";

            session.run(query, Values.parameters("nodeId", nodeId));
            System.out.println("节点删除成功");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
