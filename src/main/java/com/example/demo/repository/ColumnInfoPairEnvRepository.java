package com.example.demo.repository;

//import com.starp.exam.entity.ColumnInfoPairEnv;

import java.util.List;
import java.util.Date;

import com.example.demo.entity.ColumnInfoPairEnv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
/**
 * @description  columnInfoPairEnv
 * @author starp
 * @date 2023-06-18 21:54:52
 */
@Repository
public interface ColumnInfoPairEnvRepository extends JpaRepository<ColumnInfoPairEnv,Integer> ,
        JpaSpecificationExecutor<ColumnInfoPairEnv> {

ColumnInfoPairEnv findFirstByColumnInfoId(Integer columnInfoId);
ColumnInfoPairEnv findFirstByEnvId(Integer envId);
ColumnInfoPairEnv findFirstById(Integer id);
//findAllBy
    List<ColumnInfoPairEnv>findAllByColumnInfoId(Integer columnInfoId);
    List<ColumnInfoPairEnv>findAllByEnvIdIn(List<Integer> EnvIds);
    List<ColumnInfoPairEnv>findAllByColumnInfoIdIn(List<Integer> ColumnInfoIds);
    List<ColumnInfoPairEnv>findAllByIdIn(List<Integer> ids);

    List<ColumnInfoPairEnv>deleteAllByColumnInfoIdIn(List<Integer> ColumnInfoIds);
    List<ColumnInfoPairEnv>deleteAllByEnvIdIn(List<Integer> EnvIds);


}
