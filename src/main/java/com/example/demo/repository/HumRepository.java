package com.example.demo.repository;
import com.example.demo.entity.Hum;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
/**
 * @description hum
 * @author mqp
 * @date 2021-12-19
 */
@Repository
public interface HumRepository extends JpaRepository<Hum,Integer> {



}