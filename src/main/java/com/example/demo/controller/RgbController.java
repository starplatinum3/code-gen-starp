package com.example.demo.controller;

import com.example.demo.entity.ReturnT;
import com.example.demo.entity.Rgb;
import com.example.demo.repository.RgbRepository;
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
 * @description rgb
 * @author zhengkai.blog.csdn.net
 * @date 2021-12-26
 */
@RestController
@RequestMapping("/rgb")

@Slf4j
@Api(tags = "rgb")
@Transactional
@CrossOrigin
public class RgbController {

    @Autowired
    private RgbRepository rgbRepository;

    /**
    * 新增或编辑
    */
    @PostMapping("/save")
    @ApiOperation(value = "update", notes = "update")
    public Object save(@RequestBody  Rgb rgb){
        return rgbRepository.save(rgb);
    }

    /**
    * 删除
    */
    @PostMapping("/delete")
    @ApiOperation(value = "update", notes = "update")
    public Object delete(int id){
        Optional<Rgb> rgb=rgbRepository.findById(id);
        if(rgb.isPresent()){
            rgbRepository.deleteById(id);
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
        Optional<Rgb> rgb=rgbRepository.findById(id);
        if(rgb.isPresent()){
            return ReturnT.success(rgb.get());
        }else{
            return ReturnT.error("没有找到该对象");
        }
    }

    /**
    * 分页查询
    */
    @PostMapping("/list")
    @ApiOperation(value = "update", notes = "update")
    public Object list(@RequestBody Rgb rgb,
                        @RequestParam(required = false, defaultValue = "0") int pageNumber,
                        @RequestParam(required = false, defaultValue = "10") int pageSize) {

            //创建匹配器，需要查询条件请修改此处代码
            ExampleMatcher matcher = ExampleMatcher.matchingAll();

            //创建实例
            Example<Rgb> example = Example.of(rgb, matcher);
            //分页构造
            Pageable pageable = PageRequest.of(pageNumber,pageSize);

            return rgbRepository.findAll(example, pageable);
    }

}