package #包名#.controller;



//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import javax.persistence.criteria.Predicate;

//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.github.pagehelper.Page;


//import org.springframework.data.domain.*;
//import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

//import #包名#.util.JsonUtil;
//import #包名#.util.ReturnT;
import  #包名#.utility.#returnType#;

//import #包名#.util.#returnType#;
//import #包名#.util.StrUtil;
//import #包名#.util.page.StarpPage;
//import #包名#.repository.#类名#Repository;
import #包名#.service.#类名#Service;
import #包名#.domain.#类名#;

//import #包名#.entity.#类名#;
//import #包名#.util.StringUtils;
import #包名#.utility.StringUtils;


import javax.annotation.Resource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mqp
 * @description #类名#
 * @date 2022-06-27
 */

@Slf4j
//@Api(tags = "#实体名#")
//@CrossOrigin
@CrossOrigin(allowCredentials = "true")
@RestController
@RequestMapping("/#实体名#")
//@RequestMapping("/api/#实体名#")
public class #类名#ControllerMbp {

//    @Autowired
//    private #类名#Repository #实体名#Repository;

        /*新增或编辑
         let  data= #jsonDefaultNull#
//         axios.post(Common.baseUrl + "/#实体名#/save",data)
         this.$axios.post( "/#实体名#/save",data)
         .then((res) => {

         console.log("res");
         console.log(res);
         let  data= res.data.data
         });
         */
        @PostMapping("/save")
//        @ApiOperation(value = "save #实体名#", notes = "save #实体名#")
        public Object save(@RequestBody #类名# #实体名#) {
            try {
            return #returnType#.success(#实体名#Service.saveOrUpdate(#实体名#));
            } catch (Exception e) {
            e.printStackTrace();
//             return #returnType#.error("保存失败");
           return #returnType#.error("保存失败 "+e.getMessage());
        }

        }


/*
 let  data=  [
    #jsonDefaultNull#
 ]
// axios.post(Common.baseUrl + "/#实体名#/saveAll",data)
 this.$axios.post( "/#实体名#/saveAll",data)
 .then(res => {

 console.log("res");
 console.log(res);
 Toast('saveAll成功');
 let  data= res.data.data
 });
 */
    @PostMapping("/saveAll")
//    @ApiOperation(value = "saveAll", notes = "saveAll")
    public Object saveAll(@RequestBody List<#类名#> list){
        try {
        return #returnType#.success(#实体名#Service.saveOrUpdateBatch(list));
        } catch (Exception e) {
        e.printStackTrace();
        return #returnType#.error("保存失败");
        }

        }




    /*
     let  data= #jsonDefaultNull#
//     axios.post(Common.baseUrl + "/#实体名#/deleteBy",data)
     this.$axios.post( "/#实体名#/deleteBy",data)
     .then((res) => {

     console.log("res");
     console.log(res);
     Toast('删除成功');
      let  data= res.data.data

     });
     */
    @PostMapping("/deleteBy")
//    @ApiOperation(value = "deleteBy", notes = "deleteBy")
    public Object deleteBy(@RequestBody  #类名# #实体名#) {
        try {
            Integer id = #实体名#.getId();
//            Integer id = #实体名#.get#类名#Id();
            #实体名#Service.removeById(id);
              return #returnType#.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return #returnType#.error(e.getMessage());
        }
        }






        @Resource
    #类名#Service #实体名#Service;

    /*
     let  data= #jsonDefaultNull#
    // axios.post(Common.baseUrl + `/#实体名#/selectPage/equal?pageNumber=${this.pageNumber}&pageSize=${this.pageSize}`,data)
     this.$axios.post(`/#实体名#/selectPage/equal?pageNumber=${this.pageNumber}&pageSize=${this.pageSize}`,data)
     .then(res => {

     console.log("res");
     console.log(res);
     this.tableData = res.data.data.records;
     this.pageTotal= res.data.data.total
          let  records=res.data.response.records
     });
     */
//    @RequestMapping(value = "/selectPage/equal", method = RequestMethod.POST)
    @RequestMapping(value = "/selectPageEqual", method = RequestMethod.POST)
    public Object selectPageEqual(@RequestBody #类名# #实体名#,
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize) {
        try {
//            com.baomidou.mybatisplus.extension.plugins.pagination.Page<#类名#> pageTool =
//            new   com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber,pageSize);
        Page<#类名#> pageTool = new  Page<>(pageNumber,pageSize);

            LambdaQueryWrapper<#类名#> equals = Wrappers.lambdaQuery();
            equals
            #MybatisPlusSelectPageEqualRows#
         ;

            IPage<#类名#> page = #实体名#Service.page(pageTool,equals);
            return #returnType#.success(page);
        }catch (Exception e){
            log.info("error {}",e.getMessage());
            return  #returnType#.error(e.getMessage());
            }
//            finally {
//            PageHelper.clearPage();
//             }
        }


    /* create
     let  data= #jsonDefaultNull#
//     axios.post(Common.baseUrl + "/#实体名#/create",data)
      this.$axios.post( "/#实体名#/create",data)
     .then((res) => {
     console.log("res");
     console.log(res);
     });
     */
//    @ApiOperation(value = "create", notes = "create")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object create(@RequestBody #类名# #实体名#) {
        boolean save = #实体名#Service.save(#实体名#);
        return #returnType#.success(save);
    }

        /*
         let  data= #jsonDefaultNull#
//         axios.post(Common.baseUrl + `/#实体名#/selectPage?pageNumber=${this.pageNumber}&pageSize=${this.pageSize}`,data).then(res => {
          this.$axios.post( `/#实体名#/selectPage?pageNumber=${this.pageNumber}&pageSize=${this.pageSize}`,data).then(res => {
         console.log("res");
         console.log(res);
              let  records=res.data.response.records
              this.tableData =  res.response.records
this.total =res.response.total
this.queryParam.pageIndex=res.response.current
         });
         */
        @RequestMapping(value = "/selectPage", method = RequestMethod.POST)
        public Object selectPage(@RequestBody #类名# #实体名#,
            @RequestParam(required = false, defaultValue = "1") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        try {
//            com.baomidou.mybatisplus.extension.plugins.pagination.Page<#类名#> pageTool =
//            new   com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber,pageSize);
            Page<#类名#> pageTool = new  Page<>(pageNumber,pageSize);
              LambdaQueryWrapper<#类名#> like = Wrappers.lambdaQuery();
             like
            #MybatisPlusSelectPageLikeRows#
            ;

        IPage<#类名#> page = #实体名#Service.page(pageTool,like);
            return #returnType#.success(page);
           }catch (Exception e){
                log.info("error {}",e.getMessage());
                return  #returnType#.error(e.getMessage());
          }
        }

      /*
     let  data= []
//     axios.post(Common.baseUrl + "/#实体名#/removeByIds",data)
      this.$axios.post( "/#实体名#/removeByIds",data)
     .then(res=> {
     console.log("res");
     console.log(res);
     });
     */
@PostMapping("/removeByIds")
public Object removeByIds(@RequestBody  List<Integer> ids) {
        try {
        boolean b = #实体名#Service.removeByIds(ids);
        return #returnType#.success("删除成功");
        } catch (Exception e) {
        e.printStackTrace();
        return #returnType#.error(e.getMessage());
        }
        }

    /*
     let  data= #jsonDefaultNull#
    // axios.post(Common.baseUrl + `/#实体名#/selectPlusPage?pageNumber=${this.pageNumber}&pageSize=${this.pageSize}`,data)
     this.$axios.post(`/#实体名#/selectPlusPage?pageNumber=${this.pageNumber}&pageSize=${this.pageSize}`,data)
     .then(res => {

     console.log("res");
     console.log(res);
     this.tableData = res.data.data.records;
     console.log(" this.tableData")
     console.log( this.tableData)
     this.pageTotal= res.data.data.total
     });
     */
    @RequestMapping(value = "/selectPlusPage", method = RequestMethod.POST)
    public Object selectPlusPage(@RequestBody #类名# #实体名#,
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize) {
        try {
//                com.baomidou.mybatisplus.extension.plugins.pagination.Page<#类名#> pageTool =
//                new   com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber,pageSize);
            Page<#类名#> pageTool = new  Page<>(pageNumber,pageSize);
                LambdaQueryWrapper<#类名#> like = Wrappers.lambdaQuery();
                like
                #MybatisPlusSelectPageLikeRows#
                ;
                IPage<#类名#> page = #实体名#Service.page(pageTool,like);
                return #returnType#.success(page);
            }catch (Exception e){
                log.info("error {}",e.getMessage());
                return  #returnType#.error(e.getMessage());
            }

        }

}
