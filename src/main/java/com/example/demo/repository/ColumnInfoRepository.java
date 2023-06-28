package com.example.demo.repository;

//import com.starp.exam.entity.ColumnInfo;

import java.util.List;
import java.util.Date;

import com.example.demo.entity.ColumnInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
/**
 * @description  columnInfo
 * @author starp
 * @date 2023-06-18 21:43:44
 */
@Repository
public interface ColumnInfoRepository extends JpaRepository<ColumnInfo,Integer> ,
        JpaSpecificationExecutor<ColumnInfo> {

ColumnInfo findFirstByColumnName(String columnName);
ColumnInfo findFirstByComment(String comment);
ColumnInfo findFirstByColumnType(String columnType);
ColumnInfo findFirstById(Integer id);

List<ColumnInfo>findAllByIdIn(List<Integer>ids);

}
