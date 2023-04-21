package com.example.demo.controller;

import com.example.demo.entity.Acc;
import com.example.demo.entity.ReturnT;
import com.example.demo.repository.AccRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mqp
 * @description acc
 * @date 2021-12-20
 */
@RestController
@RequestMapping("/acc")
@Slf4j
@Api(tags = "acc")
@Transactional
@CrossOrigin
public class AccController {

    @Autowired
    private AccRepository accRepository;

    /**
     * 新增或编辑
     */
    @PostMapping("/save")
    @ApiOperation(value = "update", notes = "update")
    public Object save(@RequestBody Acc acc) {
        return accRepository.save(acc);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ApiOperation(value = "update", notes = "update")
    public Object delete(int id) {
        Optional<Acc> acc = accRepository.findById(id);
        if (acc.isPresent()) {
            accRepository.deleteById(id);
            return ReturnT.success("删除成功");
        } else {
            return ReturnT.error("没有找到该对象");
        }
    }

    /**
     * 查询
     */
    @PostMapping("/find")
    @ApiOperation(value = "update", notes = "update")
    public Object find(int id) {
        Optional<Acc> acc = accRepository.findById(id);
        if (acc.isPresent()) {
            return ReturnT.success(acc.get());
        } else {
            return ReturnT.error("没有找到该对象");
        }
    }

    /**
     * 分页查询
     */
    @PostMapping("/list")
    @ApiOperation(value = "update", notes = "update")
    public Object list(@RequestBody Acc acc,
                       @RequestParam(required = false, defaultValue = "0") int pageNumber,
                       @RequestParam(required = false, defaultValue = "10") int pageSize) {

        //创建匹配器，需要查询条件请修改此处代码
        ExampleMatcher matcher = ExampleMatcher.matchingAll();

        //创建实例
        Example<Acc> example = Example.of(acc, matcher);
        //分页构造
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return accRepository.findAll(example, pageable);
    }

    /**
     * 分页查询
     */
    @PostMapping("/last")
    @ApiOperation(value = "update", notes = "update")
    public Object firstOrderByCreateTimeAtDesc(@RequestBody Acc acc,
                                               @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                               @RequestParam(required = false, defaultValue = "10") int pageSize) {

        List<Acc> first5ByOrderByCreateTimeDesc = accRepository.findFirst5ByOrderByCreateTimeDesc();
        Collections.reverse(first5ByOrderByCreateTimeDesc); //使用方法进行逆序

        return first5ByOrderByCreateTimeDesc;
//        return ReturnT.error("没有找到该对象");

    }

}