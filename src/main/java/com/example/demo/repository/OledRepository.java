package com.example.demo.repository;

import com.example.demo.entity.Oled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @description oled
 * @author zhengkai.blog.csdn.net
 * @date 2021-12-26
 */
@Repository
public interface OledRepository extends JpaRepository<Oled,Integer> {



}