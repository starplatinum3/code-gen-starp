package com.example.demo.repository;

import com.example.demo.entity.Rgb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @description rgb
 * @author zhengkai.blog.csdn.net
 * @date 2021-12-26
 */
@Repository
public interface RgbRepository extends JpaRepository<Rgb,Integer> {



}