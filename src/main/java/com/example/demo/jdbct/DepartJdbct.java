package com.example.demo.jdbct;

import com.example.demo.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class DepartJdbct {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    String l(Object object) {
        return StrUtil.like(object);
    }

    public void select() {
//        JdbcTemplate jdbcTemplate=new JdbcTemplate();
//        jdbcTemplate.queryForObject()
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select  * from tb_depart");
        System.out.println("maps");
        System.out.println(maps);
    }

    public void update() {
//        JdbcTemplate jdbcTemplate=new JdbcTemplate();
//        jdbcTemplate.queryForObject()
      jdbcTemplate.update("update tb_depart  set depart_name='11' where  id=2 ");
//        System.out.println("maps");
//        System.out.println(maps);
    }
}