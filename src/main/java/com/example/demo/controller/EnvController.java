package com.example.demo.controller;
//package com.starp.exam.iView.controller;

//import com.starp.exam.entity.Env;
//import com.starp.exam.repository.EnvRepository;
//import com.starp.exam.util.ReturnT;
import com.example.demo.entity.Env;
import com.example.demo.repository.EnvRepository;
import top.starp.util.ReturnT;
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
 * @description Env
 * @date 2022-06-27
 */

@Slf4j
@Api(tags = "env")
//@CrossOrigin
@CrossOrigin(allowCredentials = "true")
@RestController
//@RequestMapping("/env")
@RequestMapping("/api/env")
public class EnvController {

@Autowired
private EnvRepository envRepository;

        /*
         新增或编辑

         let  data= {  
         "envName":null ,
         "id":null, "idMax":null, "idMin":null 
                  }
         axios.post(Common.baseUrl + "/env/save",data).then((res) => {
         console.log("res");
         console.log(res);
         let  data= res.data.data
         });

	entitySave(){

         let  data= {  
         "envName":null ,
         "id":null, "idMax":null, "idMin":null 
                  }

         this.$axios({
				method: "post",
				url: "/env/save",
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
@ApiOperation(value = "save env", notes = "save env")
public Object save(@RequestBody Env env) {
        try {
//                envRepository.findAllById()
        return ReturnT.success(envRepository.save(env));
        } catch (Exception e) {
        e.printStackTrace();
        return ReturnT.error("保存失败");
        }

        }


/*

 let  data=  [
    {  
         "envName":null ,
         "id":null, "idMax":null, "idMin":null 
                  }
 ]
         this.$axios({
				method: "post",
				url: "/env/saveAll",
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
    {  
         "envName":null ,
         "id":null, "idMax":null, "idMin":null 
                  }
 ]
 axios.post(Common.baseUrl + "/env/saveAll",data).then(res => {
 console.log("res");
 console.log(res);
 Toast('saveAll成功');
 let  data= res.data.data
 });
 */
@PostMapping("/saveAll")
@ApiOperation(value = "saveAll", notes = "saveAll")
public Object saveAll(@RequestBody List<Env> list){
        try {
        return ReturnT.success(envRepository.saveAll(list));
        } catch (Exception e) {
        e.printStackTrace();
        return ReturnT.error("保存失败");
        }

        }


        /*
       删除
         let  data= {}
         axios.post(Common.baseUrl + "/env/delete?id="+id,data).then((res) => {
         console.log("res");
         console.log(res);
         Toast('删除成功');
         let  data= res.data.data
         });

         this.$axios({
				method: "post",
				url: "/env/delete?id="+id,
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
@ApiOperation(value = "delete env", notes = "delete env")
public Object delete(int id) {
        Optional<Env> env = envRepository.findById(id);
        if (env.isPresent()) {
        envRepository.deleteById(id);
        return ReturnT.success("删除成功");
        } else {
        return ReturnT.error("没有找到该对象");
        }
        }

    /*
     let  data= {  
         "envName":null ,
         "id":null, "idMax":null, "idMin":null 
                  }
     axios.post(Common.baseUrl + "/env/deleteBy",data).then((res) => {
     console.log("res");
     console.log(res);
     Toast('删除成功');
      let  data= res.data.data

     });

      this.$axios({
				method: "post",
				url: "/env/deleteBy",
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
public Object deleteBy(@RequestBody  Env env) {
        try {
        Integer id = env.getId();
        envRepository.deleteById(id);
        return ReturnT.success("删除成功");
        } catch (Exception e) {
        e.printStackTrace();
        return ReturnT.error(e.getMessage());
        }
        }



   /*


     axios.post(Common.baseUrl + "/env/find?id="+id,{}).then(res => {
     console.log("res");
     console.log(res);
    Toast('查找成功');
    let  data= res.data.data

     });

    this.$axios({
				method: "post",
				url: "/env/find?id="+id,
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
@ApiOperation(value = "find env by id", notes = "find env by id")
public Object find(int id) {
        Optional<Env> env = envRepository.findById(id);
        if (env.isPresent()) {
        return ReturnT.success(env.get());
        } else {
        return ReturnT.error("没有找到该对象");
        }
        }

/**
 * 分页查询
 let  data= {  
         "envName":null ,
         "id":null, "idMax":null, "idMin":null 
                  }
 axios.post(Common.baseUrl + "/env/list",data).then((res) => {
 console.log("res");
 console.log(res);
 let   envList=  res.data.data.content
 Toast('查找成功');
 });

 this.$axios({
 method: "post",
 url: "/env/list",
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
@ApiOperation(value = "list env", notes = "list env")
public Object list(@RequestBody Env env,
@RequestParam(required = false, defaultValue = "0") int pageNumber,
@RequestParam(required = false, defaultValue = "10") int pageSize) {

        try {
        //创建匹配器，需要查询条件请修改此处代码
        ExampleMatcher matcher = ExampleMatcher.matchingAll();

        //创建实例
        Example<Env> example = Example.of(env, matcher);
        //分页构造
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return ReturnT.success(envRepository.findAll(example, pageable));

        } catch (Exception e) {
        e.printStackTrace();
        return ReturnT.error(e.getMessage());
        }

        }




/**
 *
 let  data= [ {  
         "envName":null ,
         "id":null, "idMax":null, "idMin":null 
                  }  ]
 axios.post(Common.baseUrl + "/env/findAllById",data).then((res) => {
 console.log("res");
 console.log(res);
 let   envList=  res.data.data.content
 });


 this.$axios({
 method: "post",
 url: "/env/findAllById",
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
        List<Env> allById =envRepository.findAllById(ids);
        StarpPage<Env> starpPage = new StarpPage<>(allById, pageNumber, pageSize);
        return ReturnT.success(starpPage);
        } catch (Exception e) {
        e.printStackTrace();
        return ReturnT.error("保存失败");
        }
        }


/**
 *
 let  data= [ {  
         "envName":null ,
         "id":null, "idMax":null, "idMin":null 
                  }  ]
 axios.post(Common.baseUrl + "/env/deleteInBatch",data).then((res) => {
 console.log("res");
 console.log(res);
 let   envList=  res.data.data.content
 });

 this.$axios({
 method: "post",
 url: "/env/deleteInBatch",
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
public Object deleteInBatch(@RequestBody List<Env> entities) {
        try {
        envRepository.deleteInBatch(entities);
        return ReturnT.success("批量删除成功");
        } catch (Exception e) {
        e.printStackTrace();
        return ReturnT.error("批量删除失败");
        }
        }

/**
 * listLike
 let envList: any = ref([]);
 let  data= {  
         "envName":null ,
         "id":null, "idMax":null, "idMin":null 
                  }
 axios.post(Common.baseUrl + "/env/listLike",data).then((res) => {
 console.log("res");
 console.log(res);
 envList.value = res.data.data.content     });


 this.$axios({
 method: "post",
 url: "/env/listLike",
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
public Object listLike(@RequestBody Env env,
@RequestParam(required = false, defaultValue = "0") int pageNumber,
@RequestParam(required = false, defaultValue = "10") int pageSize) {

        try {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Env> page = envRepository.findAll((Specification<Env>)
        (root, criteriaQuery, criteriaBuilder) -> {
        List<Predicate> list = new ArrayList<Predicate>();
        if (!StringUtils.isNone(env.getId())) {
            list.add(criteriaBuilder.equal(root.get("envName").as(String.class), env.getEnvName()));
        }
if (!StringUtils.isNone(env.getId())) {
            list.add(criteriaBuilder.equal(root.get("id").as(String.class), env.getId()));
        }

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
//    EnvService envService;

    /*

     create

     let  data= {  
         "envName":null ,
         "id":null, "idMax":null, "idMin":null 
                  }
     axios.post(Common.baseUrl + "/env/create",data).then((res) => {
     console.log("res");
     console.log(res);
     });

     this.$axios({
     method: "post",
     url:  "/env/create",
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
//    public Object create(@RequestBody Env env) {
//        boolean save = envService.save(env);
//        return ReturnT.success(save);
//    }


        }
