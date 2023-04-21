package com.example.demo.controller;

import com.example.demo.entity.Oled;
import com.example.demo.entity.ReturnT;
import com.example.demo.repository.OledRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @description oled
 * @author zhengkai.blog.csdn.net
 * @date 2021-12-26
 */
@RestController
@RequestMapping("/oled")
@Slf4j
@Api(tags = "oled")
@Transactional
@CrossOrigin
public class OledController {

    @Autowired
    private OledRepository oledRepository;

    /**
    * 新增或编辑
    */
    @PostMapping("/save")
    @ApiOperation(value = "update", notes = "update")
    public Object save(@RequestBody Oled oled){
        return oledRepository.save(oled);
    }

    /**
    * 删除
    */
    @PostMapping("/delete")
    @ApiOperation(value = "update", notes = "update")
    public Object delete(int id){
        Optional<Oled> oled=oledRepository.findById(id);
        if(oled.isPresent()){
            oledRepository.deleteById(id);
            return ReturnT.success("删除成功");
        }else{
            return ReturnT.error("没有找到该对象");
        }
    }

    /**
    * 查询
    */
    @PostMapping("/find")
    @ApiOperation(value = "update", notes = "update")
    public Object find(int id){
        Optional<Oled> oled=oledRepository.findById(id);
        if(oled.isPresent()){
            return ReturnT.success(oled.get());
        }else{
            return ReturnT.error("没有找到该对象");
        }
    }

    /**
    * 分页查询
    */
    @PostMapping("/list")
    @ApiOperation(value = "update", notes = "update")
    public Object list(@RequestBody Oled oled,
                        @RequestParam(required = false, defaultValue = "0") int pageNumber,
                        @RequestParam(required = false, defaultValue = "10") int pageSize) {

            //创建匹配器，需要查询条件请修改此处代码
            ExampleMatcher matcher = ExampleMatcher.matchingAll();

            //创建实例
            Example<Oled> example = Example.of(oled, matcher);
            //分页构造
            Pageable pageable = PageRequest.of(pageNumber,pageSize);

            return oledRepository.findAll(example, pageable);
    }

}