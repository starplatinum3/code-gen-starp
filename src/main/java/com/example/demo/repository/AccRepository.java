package com.example.demo.repository;
import com.example.demo.entity.Acc;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
/**
 * @description acc
 * @author mqp
 * @date 2021-12-20
 */
@Repository
public interface AccRepository extends JpaRepository<Acc,Integer> {

 List<Acc>findFirst5ByOrderByCreateTimeDesc();

}