package com.example.demo.repository;

//import com.starp.exam.entity.Env;

import java.util.List;
import java.util.Date;

import com.example.demo.entity.Env;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
/**
 * @description  env
 * @author starp
 * @date 2023-06-18 21:54:52
 */
@Repository
public interface EnvRepository extends JpaRepository<Env,Integer> ,
        JpaSpecificationExecutor<Env> {

Env findFirstByEnvName(String envName);
 List<Env>findAllByEnvName(String envName);
//findAllBy
Env findFirstById(Integer id);

}
