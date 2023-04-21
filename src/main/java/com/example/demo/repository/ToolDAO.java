package com.example.demo.repository;

//import com.gm.wj.dto.TableName;
//import com.gm.wj.entity.User;
import ch.qos.logback.classic.db.names.TableName;
import com.example.demo.entity.Acc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @author Evan
 * @date 2019/4
 */
public interface ToolDAO extends JpaRepository<Acc, Integer> {
//    User findByUsername(String username);

//    @PersistenceContext
//     EntityManager entityManager;

//    User getByUsernameAndPassword(String username, String password);
public static void main(String[] args) {
   String string= "SELECT \n" +
            "\n" +
            "table_name \n" +
            "\n" +
            "FROM\n" +
            "\n" +
            "information_schema.tables \n" +
            "\n" +
            "WHERE table_schema = ? \n" +
            "\n" +
            "AND table_type = ? ";
    System.out.println(string);
    String columns="select * from information_schema.columns\n" +
            "   where table_schema= ? and table_name = ? ";
    System.out.println(columns);
}
    @Query(value = "SELECT \n" +
            "\n" +
            "table_name \n" +
            "\n" +
            "FROM\n" +
            "\n" +
            "information_schema.tables \n" +
            "\n" +
            "WHERE table_schema = ? \n" +
            "\n" +
            "AND table_type = ? ",nativeQuery = true)
    List<Map<String, Object>> selectTableNames(String table_schema, String table_type);
//mybatis 返回 类型
    @Query(value = "SELECT \n" +
            "\n" +
            "table_name \n" +
            "\n" +
            "FROM\n" +
            "\n" +
            "information_schema.tables \n" +
            "\n" +
            "WHERE table_schema = ? \n" +
            " ",nativeQuery = true)
    List<Map<String, Object>> selectTableNames(String table_schema);


    @Query(value = "select * from information_schema.columns\n" +
            "   where table_schema= ? and table_name = ? ",nativeQuery = true)
    List<Map<String, Object>> selectTableCols(String table_schema, String table_name);


    /**
     * ramework.core.convert.ConverterNotFoundException: No converter found capable of converting from type [
     * @param table_schema
     * @param table_type
     * @return
     */
    @Query(value = "SELECT \n" +
            "\n" +
            "table_name \n" +
            "\n" +
            "FROM\n" +
            "\n" +
            "information_schema.tables \n" +
            "\n" +
            "WHERE table_schema = ? \n" +
            "\n" +
            "AND table_type = ? ",nativeQuery = true)
    List<TableName> selectTableNamesToObj(String table_schema, String table_type);



}
