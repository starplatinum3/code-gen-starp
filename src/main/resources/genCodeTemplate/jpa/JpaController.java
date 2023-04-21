package #包名#.controller;
//package #包名#.iView.controller;

import #包名#.entity.#类名#;
import #包名#.repository.#类名#Repository;
import #包名#.util.ReturnT;
import top.starp.util.StringUtils;
import top.starp.util.page.StarpPage;

import javax.annotation.Resource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.github.pagehelper.Page;
import java.util.*;

import javax.persistence.criteria.Predicate;

/**
 * @author mqp
 * @description #类名#
 * @date 2022-06-27
 */

@Slf4j
@Api(tags = "#实体名#")
//@CrossOrigin
@CrossOrigin(allowCredentials = "true")
@RestController
//@RequestMapping("/#实体名#")
@RequestMapping("/api/#实体名#")
public class #类名#Controller {

@Autowired
private #类名#Repository #实体名#Repository;

        /*
         新增或编辑

         let  data= #jsonDefaultNull#
         axios.post(Common.baseUrl + "/#实体名#/save",data).then((res) => {
         console.log("res");
         console.log(res);
         let  data= res.data.data
         });

	entitySave(){

         let  data= #jsonDefaultNull#

         this.$axios({
				method: "post",
				url: "/#实体名#/save",
				data: data,
			}).then(res => {
				this.datalist = res.data;
				console.log("res save");
					console.log(res);
			}).catch(err => {
				console.log("err");
				console.log(err);
			})
			}


         */
@PostMapping("/save")
@ApiOperation(value = "save #实体名#", notes = "save #实体名#")
public Object save(@RequestBody #类名# #实体名#) {
        try {
        return ReturnT.success(#实体名#Repository.save(#实体名#));
        } catch (Exception e) {
        e.printStackTrace();
        return ReturnT.error("保存失败");
        }

        }


/*

 let  data=  [
    #jsonDefaultNull#
 ]
         this.$axios({
				method: "post",
				url: "/#实体名#/saveAll",
				data: data,
			}).then(res => {
				this.datalist = res.data;
				console.log("res save");
					console.log(res);
			}).catch(err => {
				console.log("err");
				console.log(err);
			})

 let  data=  [
    #jsonDefaultNull#
 ]
 axios.post(Common.baseUrl + "/#实体名#/saveAll",data).then(res => {
 console.log("res");
 console.log(res);
 Toast('saveAll成功');
 let  data= res.data.data
 });
 */
@PostMapping("/saveAll")
@ApiOperation(value = "saveAll", notes = "saveAll")
public Object saveAll(@RequestBody List<#类名#> list){
        try {
        return ReturnT.success(#实体名#Repository.saveAll(list));
        } catch (Exception e) {
        e.printStackTrace();
        return ReturnT.error("保存失败");
        }

        }


        /*
       删除
         let  data= {}
         axios.post(Common.baseUrl + "/#实体名#/delete?id="+id,data).then((res) => {
         console.log("res");
         console.log(res);
         Toast('删除成功');
         let  data= res.data.data
         });

         this.$axios({
				method: "post",
				url: "/#实体名#/delete?id="+id,
				data: data,
			}).then(res => {
				this.datalist = res.data;
				console.log("res save");
					console.log(res);
			}).catch(err => {
				console.log("err");
				console.log(err);
			})

         */
@PostMapping("/delete")
@ApiOperation(value = "delete #实体名#", notes = "delete #实体名#")
public Object delete(int id) {
        Optional<#类名#> #实体名# = #实体名#Repository.findById(id);
        if (#实体名#.isPresent()) {
        #实体名#Repository.deleteById(id);
        return ReturnT.success("删除成功");
        } else {
        return ReturnT.error("没有找到该对象");
        }
        }

    /*
     let  data= #jsonDefaultNull#
     axios.post(Common.baseUrl + "/#实体名#/deleteBy",data).then((res) => {
     console.log("res");
     console.log(res);
     Toast('删除成功');
      let  data= res.data.data

     });

      this.$axios({
				method: "post",
				url: "/#实体名#/deleteBy",
				data: data,
			}).then(res => {
				this.datalist = res.data;
				console.log("res save");
					console.log(res);
			}).catch(err => {
				console.log("err");
				console.log(err);
			})

     */
@PostMapping("/deleteBy")
@ApiOperation(value = "deleteBy", notes = "deleteBy")
public Object deleteBy(@RequestBody  #类名# #实体名#) {
        try {
        Integer id = #实体名#.getId();
        #实体名#Repository.deleteById(id);
        return ReturnT.success("删除成功");
        } catch (Exception e) {
        e.printStackTrace();
        return ReturnT.error(e.getMessage());
        }
        }



   /*


     axios.post(Common.baseUrl + "/#实体名#/find?id="+id,{}).then(res => {
     console.log("res");
     console.log(res);
    Toast('查找成功');
    let  data= res.data.data

     });

    this.$axios({
				method: "post",
				url: "/#实体名#/find?id="+id,
				data: data,
			}).then(res => {
				this.datalist = res.data;
				console.log("res save");
					console.log(res);
			}).catch(err => {
				console.log("err");
				console.log(err);
			})
     */
@PostMapping("/find")
@ApiOperation(value = "find #实体名# by id", notes = "find #实体名# by id")
public Object find(int id) {
        Optional<#类名#> #实体名# = #实体名#Repository.findById(id);
        if (#实体名#.isPresent()) {
        return ReturnT.success(#实体名#.get());
        } else {
        return ReturnT.error("没有找到该对象");
        }
        }

/**
 * 分页查询
 let  data= #jsonDefaultNull#
 axios.post(Common.baseUrl + "/#实体名#/list",data).then((res) => {
 console.log("res");
 console.log(res);
 let   #实体名#List=  res.data.data.content
 Toast('查找成功');
 });

 this.$axios({
 method: "post",
 url: "/#实体名#/list",
 data: data,
 }).then(res => {
 this.datalist = res.data;
 console.log("res save");
 console.log(res);
 }).catch(err => {
 console.log("err");
 console.log(err);
 })

 */
@PostMapping("/list")
@ApiOperation(value = "list #实体名#", notes = "list #实体名#")
public Object list(@RequestBody #类名# #实体名#,
@RequestParam(required = false, defaultValue = "0") int pageNumber,
@RequestParam(required = false, defaultValue = "10") int pageSize) {

        try {
        //创建匹配器，需要查询条件请修改此处代码
        ExampleMatcher matcher = ExampleMatcher.matchingAll();

        //创建实例
        Example<#类名#> example = Example.of(#实体名#, matcher);
        //分页构造
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return ReturnT.success(#实体名#Repository.findAll(example, pageable));

        } catch (Exception e) {
        e.printStackTrace();
        return ReturnT.error(e.getMessage());
        }

        }




/**
 *
 let  data= [ #jsonDefaultNull#  ]
 axios.post(Common.baseUrl + "/#实体名#/findAllById",data).then((res) => {
 console.log("res");
 console.log(res);
 let   #实体名#List=  res.data.data.content
 });


 this.$axios({
 method: "post",
 url: "/#实体名#/findAllById",
 data: data,
 }).then(res => {
 this.datalist = res.data;
 console.log("res save");
 console.log(res);
 }).catch(err => {
 console.log("err");
 console.log(err);
 })


 */
@PostMapping("/findAllById")
@ApiOperation(value = "findAllById", notes = "findAllById")
public Object findAllById(@RequestBody List<Integer> ids,
@RequestParam(required = false, defaultValue = "0") int pageNumber,
@RequestParam(required = false, defaultValue = "10") int pageSize) {
        try {
        List<#类名#> allById =#实体名#Repository.findAllById(ids);
        StarpPage<#类名#> starpPage = new StarpPage<>(allById, pageNumber, pageSize);
        return ReturnT.success(starpPage);
        } catch (Exception e) {
        e.printStackTrace();
        return ReturnT.error("保存失败");
        }
        }


/**
 *
 let  data= [ #jsonDefaultNull#  ]
 axios.post(Common.baseUrl + "/#实体名#/deleteInBatch",data).then((res) => {
 console.log("res");
 console.log(res);
 let   #实体名#List=  res.data.data.content
 });

 this.$axios({
 method: "post",
 url: "/#实体名#/deleteInBatch",
 data: data,
 }).then(res => {
 this.datalist = res.data;
 console.log("res save");
 console.log(res);
 }).catch(err => {
 console.log("err");
 console.log(err);
 })

 */
@PostMapping("/deleteInBatch")
@ApiOperation(value = "deleteInBatch", notes = "deleteInBatch")
public Object deleteInBatch(@RequestBody List<#类名#> entities) {
        try {
        #实体名#Repository.deleteInBatch(entities);
        return ReturnT.success("批量删除成功");
        } catch (Exception e) {
        e.printStackTrace();
        return ReturnT.error("批量删除失败");
        }
        }

/**
 * listLike
 let #实体名#List: any = ref([]);
 let  data= #jsonDefaultNull#
 axios.post(Common.baseUrl + "/#实体名#/listLike",data).then((res) => {
 console.log("res");
 console.log(res);
 #实体名#List.value = res.data.data.content     });


 this.$axios({
 method: "post",
 url: "/#实体名#/listLike",
 data: data,
 }).then(res => {
 this.datalist = res.data;
 console.log("res save");
 console.log(res);
 }).catch(err => {
 console.log("err");
 console.log(err);
 })

 */
@PostMapping("/listLike")
@ApiOperation(value = "listLike", notes = "listLike")
public Object listLike(@RequestBody #类名# #实体名#,
@RequestParam(required = false, defaultValue = "0") int pageNumber,
@RequestParam(required = false, defaultValue = "10") int pageSize) {

        try {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<#类名#> page = #实体名#Repository.findAll((Specification<#类名#>)
        (root, criteriaQuery, criteriaBuilder) -> {
        List<Predicate> list = new ArrayList<Predicate>();
        #jpaLikeRows#
        Predicate[] p = new Predicate[list.size()];
        return criteriaBuilder.and(list.toArray(p));
        }, pageable);
        return ReturnT.success(page);

        } catch (Exception e) {
        e.printStackTrace();
        return ReturnT.error(e.getMessage());
        }

        }

//        @Resource
//    #类名#Service #实体名#Service;

    /*

     create

     let  data= #jsonDefaultNull#
     axios.post(Common.baseUrl + "/#实体名#/create",data).then((res) => {
     console.log("res");
     console.log(res);
     });

     this.$axios({
     method: "post",
     url:  "/#实体名#/create",
     data: data,
     }).then(res => {
     this.datalist = res.data;
     console.log("res save");
     console.log(res);
     }).catch(err => {
     console.log("err");
     console.log(err);
     })

     */
//    @ApiOperation(value = "create", notes = "create")
//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public Object create(@RequestBody #类名# #实体名#) {
//        boolean save = #实体名#Service.save(#实体名#);
//        return ReturnT.success(save);
//    }


        }
