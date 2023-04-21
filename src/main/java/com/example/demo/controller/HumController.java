package com.example.demo.controller;
import com.example.demo.entity.Hum;
import com.example.demo.entity.ReturnT;
import com.example.demo.repository.HumRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @description hum
 * @author mqp
 * @date 2021-12-19
 */
@RestController
@RequestMapping("/hum")

@Slf4j
@Api(tags = "hum")
@Transactional
@CrossOrigin

public class HumController {

    @Autowired
    private HumRepository humRepository;

    /**
    * 新增或编辑
    */
    @ApiOperation(value = "update", notes = "update")
    @PostMapping("/save")
    public Object save(@RequestBody  Hum hum){
        return humRepository.save(hum);
    }

    /**
    * 删除
    */
    @ApiOperation(value = "update", notes = "update")
    @PostMapping("/delete")
//    public Object delete(@RequestBody  int id){
    public Object delete(  int id){
        Optional<Hum> hum=humRepository.findById(id);
        if(hum.isPresent()){
            humRepository.deleteById(id);
            return ReturnT.success("删除成功");
        }else{
            return ReturnT.error("没有找到该对象");
        }
    }

    @ApiOperation(value = "update", notes = "update")
    @PostMapping("/deleteBy")
    public Object deleteBy(@RequestBody  Hum humForm){
        Integer id = humForm.getId();
        Optional<Hum> hum=humRepository.findById(id);
        if(hum.isPresent()){
            humRepository.deleteById(id);
            return ReturnT.success("删除成功");
        }else{
            return ReturnT.error("没有找到该对象");
        }
    }

    /**
    * 查询
    */
    @ApiOperation(value = "update", notes = "update")
    @PostMapping("/find")
    public Object find( int id){
//    public Object find(@RequestBody  int id){
        Optional<Hum> hum=humRepository.findById(id);
        if(hum.isPresent()){
            return ReturnT.success(hum.get());
        }else{
            return ReturnT.error("没有找到该对象");
        }
    }

    /**
    * 分页查询
    */
    @ApiOperation(value = "update", notes = "update")
    @PostMapping("/list")
    public Object list(@RequestBody  Hum hum,
                        @RequestParam(required = false, defaultValue = "0") int pageNumber,
                        @RequestParam(required = false, defaultValue = "10") int pageSize) {

            //创建匹配器，需要查询条件请修改此处代码
            ExampleMatcher matcher = ExampleMatcher.matchingAll();

            //创建实例
            Example<Hum> example = Example.of(hum, matcher);
            //分页构造
            Pageable pageable = PageRequest.of(pageNumber,pageSize);

            return humRepository.findAll(example, pageable);
    }

}
