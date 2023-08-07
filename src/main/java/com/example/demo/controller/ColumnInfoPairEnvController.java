package com.example.demo.controller;
//package com.starp.exam.iView.controller;

//import com.starp.exam.entity.ColumnInfoPairEnv;
//import com.starp.exam.repository.ColumnInfoPairEnvRepository;
//import com.starp.exam.util.ReturnT;
import com.example.demo.entity.ColumnInfo;
import com.example.demo.entity.ColumnInfoPairEnv;
import com.example.demo.entity.Env;
import com.example.demo.repository.ColumnInfoPairEnvRepository;
import com.example.demo.repository.ColumnInfoRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import top.starp.util.ListUtil;
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
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

/**
 * @author mqp
 * @description ColumnInfoPairEnv
 * @date 2022-06-27
 */

@Slf4j
@Api(tags = "columnInfoPairEnv")
//@CrossOrigin
//@CrossOrigin(allowCredentials = "true")
@RestController
//@RequestMapping("/columnInfoPairEnv")
@RequestMapping("/api/columnInfoPairEnv")
public class ColumnInfoPairEnvController {

@Autowired
private ColumnInfoPairEnvRepository columnInfoPairEnvRepository;

        /*
         新增或编辑

         let  data= {  
         "columnInfoId":null, "columnInfoIdMax":null, "columnInfoIdMin":null ,
         "envId":null, "envIdMax":null, "envIdMin":null ,
         "id":null, "idMax":null, "idMin":null 
                  }
         axios.post(Common.baseUrl + "/columnInfoPairEnv/save",data).then((res) => {
         console.log("res");
         console.log(res);
         let  data= res.data.data
         });

	entitySave(){

         let  data= {  
         "columnInfoId":null, "columnInfoIdMax":null, "columnInfoIdMin":null ,
         "envId":null, "envIdMax":null, "envIdMin":null ,
         "id":null, "idMax":null, "idMin":null 
                  }

         this.$axios({
				method: "post",
				url: "/columnInfoPairEnv/save",
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
@ApiOperation(value = "save columnInfoPairEnv", notes = "save columnInfoPairEnv")
public Object save(@RequestBody ColumnInfoPairEnv columnInfoPairEnv) {
        try {

                return ReturnT.success(columnInfoPairEnvRepository.save(columnInfoPairEnv));
        } catch (Exception e) {
        e.printStackTrace();
        return ReturnT.error("保存失败");
        }

        }



        @PostMapping("/findByEnvList")
        @ApiOperation(value = "findByEnvList", notes = "findByEnvList")
        public Object findByEnvList(@RequestBody List<Env> list){
                //                List<ColumnInfoPairEnv> allByColumnInfoIdIn = columnInfoPairEnvRepository.findAllByColumnInfoIdIn();
//                allByColumnInfoIdIn.
//                env col
//                这个 env 的所有
                List<Integer> envIds = list.stream().map(Env::getId).collect(Collectors.toList());
//                List<Integer> envIds = ListUtil.createList(1);
                List<ColumnInfoPairEnv> allByEnvIdIn = columnInfoPairEnvRepository.findAllByEnvIdIn(    envIds);
//                for (ColumnInfoPairEnv infoPairEnv : allByEnvIdIn) {
////                        修改
//                        infoPairEnv.setColumnInfoId(2);
//                }
                List<Integer> columnInfoIds = allByEnvIdIn.stream().map(ColumnInfoPairEnv::getColumnInfoId).collect(Collectors.toList());
                List<ColumnInfo> allByIdIn = columnInfoRepository.findAllByIdIn(columnInfoIds);
//                根据env 知道 colinfo
//                这个env 的 col 修改
//                修改成别的 id的
                return ReturnT.success(allByIdIn);
        }

        @PostMapping("/change")
        @ApiOperation(value = "change", notes = "change")
        public Object change(@RequestBody List<ColumnInfoPairEnv> list){
                List<Integer> envIds = list.stream().map(ColumnInfoPairEnv::getEnvId).collect(Collectors.toList());
                columnInfoPairEnvRepository.deleteAllByEnvIdIn(envIds);
                List<ColumnInfoPairEnv> columnInfoPairEnvs = columnInfoPairEnvRepository.saveAll(list);
                return ReturnT.success(columnInfoPairEnvs);
        }



        @Resource
        ColumnInfoRepository columnInfoRepository;

//        MongoTemplate

/*

 let  data=  [
    {  
         "columnInfoId":null, "columnInfoIdMax":null, "columnInfoIdMin":null ,
         "envId":null, "envIdMax":null, "envIdMin":null ,
         "id":null, "idMax":null, "idMin":null 
                  }
 ]
         this.$axios({
				method: "post",
				url: "/columnInfoPairEnv/saveAll",
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
         "columnInfoId":null, "columnInfoIdMax":null, "columnInfoIdMin":null ,
         "envId":null, "envIdMax":null, "envIdMin":null ,
         "id":null, "idMax":null, "idMin":null 
                  }
 ]
 axios.post(Common.baseUrl + "/columnInfoPairEnv/saveAll",data).then(res => {
 console.log("res");
 console.log(res);
 Toast('saveAll成功');
 let  data= res.data.data
 });
 */
@PostMapping("/saveAll")
@ApiOperation(value = "saveAll", notes = "saveAll")
public Object saveAll(@RequestBody List<ColumnInfoPairEnv> list){
        try {
        return ReturnT.success(columnInfoPairEnvRepository.saveAll(list));
        } catch (Exception e) {
        e.printStackTrace();
        return ReturnT.error("保存失败");
        }

        }


        /*
       删除
         let  data= {}
         axios.post(Common.baseUrl + "/columnInfoPairEnv/delete?id="+id,data).then((res) => {
         console.log("res");
         console.log(res);
         Toast('删除成功');
         let  data= res.data.data
         });

         this.$axios({
				method: "post",
				url: "/columnInfoPairEnv/delete?id="+id,
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
@ApiOperation(value = "delete columnInfoPairEnv", notes = "delete columnInfoPairEnv")
public Object delete(int id) {
        Optional<ColumnInfoPairEnv> columnInfoPairEnv = columnInfoPairEnvRepository.findById(id);
//        columnInfoPairEnvRepository.findby
        if (columnInfoPairEnv.isPresent()) {
        columnInfoPairEnvRepository.deleteById(id);
        return ReturnT.success("删除成功");
        } else {
        return ReturnT.error("没有找到该对象");
        }
        }

    /*
     let  data= {  
         "columnInfoId":null, "columnInfoIdMax":null, "columnInfoIdMin":null ,
         "envId":null, "envIdMax":null, "envIdMin":null ,
         "id":null, "idMax":null, "idMin":null 
                  }
     axios.post(Common.baseUrl + "/columnInfoPairEnv/deleteBy",data).then((res) => {
     console.log("res");
     console.log(res);
     Toast('删除成功');
      let  data= res.data.data

     });

      this.$axios({
				method: "post",
				url: "/columnInfoPairEnv/deleteBy",
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
public Object deleteBy(@RequestBody  ColumnInfoPairEnv columnInfoPairEnv) {
        try {
        Integer id = columnInfoPairEnv.getId();
        columnInfoPairEnvRepository.deleteById(id);
        return ReturnT.success("删除成功");
        } catch (Exception e) {
        e.printStackTrace();
        return ReturnT.error(e.getMessage());
        }
        }



   /*


     axios.post(Common.baseUrl + "/columnInfoPairEnv/find?id="+id,{}).then(res => {
     console.log("res");
     console.log(res);
    Toast('查找成功');
    let  data= res.data.data

     });

    this.$axios({
				method: "post",
				url: "/columnInfoPairEnv/find?id="+id,
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
@ApiOperation(value = "find columnInfoPairEnv by id", notes = "find columnInfoPairEnv by id")
public Object find(int id) {
        Optional<ColumnInfoPairEnv> columnInfoPairEnv = columnInfoPairEnvRepository.findById(id);
        if (columnInfoPairEnv.isPresent()) {
        return ReturnT.success(columnInfoPairEnv.get());
        } else {
        return ReturnT.error("没有找到该对象");
        }
        }

/**
 * 分页查询
 let  data= {  
         "columnInfoId":null, "columnInfoIdMax":null, "columnInfoIdMin":null ,
         "envId":null, "envIdMax":null, "envIdMin":null ,
         "id":null, "idMax":null, "idMin":null 
                  }
 axios.post(Common.baseUrl + "/columnInfoPairEnv/list",data).then((res) => {
 console.log("res");
 console.log(res);
 let   columnInfoPairEnvList=  res.data.data.content
 Toast('查找成功');
 });

 this.$axios({
 method: "post",
 url: "/columnInfoPairEnv/list",
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
@ApiOperation(value = "list columnInfoPairEnv", notes = "list columnInfoPairEnv")
public Object list(@RequestBody ColumnInfoPairEnv columnInfoPairEnv,
@RequestParam(required = false, defaultValue = "0") int pageNumber,
@RequestParam(required = false, defaultValue = "10") int pageSize) {

        try {
        //创建匹配器，需要查询条件请修改此处代码
        ExampleMatcher matcher = ExampleMatcher.matchingAll();

        //创建实例
        Example<ColumnInfoPairEnv> example = Example.of(columnInfoPairEnv, matcher);
        //分页构造
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return ReturnT.success(columnInfoPairEnvRepository.findAll(example, pageable));

        } catch (Exception e) {
        e.printStackTrace();
        return ReturnT.error(e.getMessage());
        }

        }




/**
 *
 let  data= [ {  
         "columnInfoId":null, "columnInfoIdMax":null, "columnInfoIdMin":null ,
         "envId":null, "envIdMax":null, "envIdMin":null ,
         "id":null, "idMax":null, "idMin":null 
                  }  ]
 axios.post(Common.baseUrl + "/columnInfoPairEnv/findAllById",data).then((res) => {
 console.log("res");
 console.log(res);
 let   columnInfoPairEnvList=  res.data.data.content
 });


 this.$axios({
 method: "post",
 url: "/columnInfoPairEnv/findAllById",
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
        List<ColumnInfoPairEnv> allById =columnInfoPairEnvRepository.findAllById(ids);
        StarpPage<ColumnInfoPairEnv> starpPage = new StarpPage<>(allById, pageNumber, pageSize);
        return ReturnT.success(starpPage);
        } catch (Exception e) {
        e.printStackTrace();
        return ReturnT.error("保存失败");
        }
        }


/**
 *
 let  data= [ {  
         "columnInfoId":null, "columnInfoIdMax":null, "columnInfoIdMin":null ,
         "envId":null, "envIdMax":null, "envIdMin":null ,
         "id":null, "idMax":null, "idMin":null 
                  }  ]
 axios.post(Common.baseUrl + "/columnInfoPairEnv/deleteInBatch",data).then((res) => {
 console.log("res");
 console.log(res);
 let   columnInfoPairEnvList=  res.data.data.content
 });

 this.$axios({
 method: "post",
 url: "/columnInfoPairEnv/deleteInBatch",
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
public Object deleteInBatch(@RequestBody List<ColumnInfoPairEnv> entities) {
        try {
        columnInfoPairEnvRepository.deleteInBatch(entities);
        return ReturnT.success("批量删除成功");
        } catch (Exception e) {
        e.printStackTrace();
        return ReturnT.error("批量删除失败");
        }
        }

/**
 * listLike
 let columnInfoPairEnvList: any = ref([]);
 let  data= {  
         "columnInfoId":null, "columnInfoIdMax":null, "columnInfoIdMin":null ,
         "envId":null, "envIdMax":null, "envIdMin":null ,
         "id":null, "idMax":null, "idMin":null 
                  }
 axios.post(Common.baseUrl + "/columnInfoPairEnv/listLike",data).then((res) => {
 console.log("res");
 console.log(res);
 columnInfoPairEnvList.value = res.data.data.content     });


 this.$axios({
 method: "post",
 url: "/columnInfoPairEnv/listLike",
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
public Object listLike(@RequestBody ColumnInfoPairEnv columnInfoPairEnv,
@RequestParam(required = false, defaultValue = "0") int pageNumber,
@RequestParam(required = false, defaultValue = "10") int pageSize) {

        try {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ColumnInfoPairEnv> page = columnInfoPairEnvRepository.findAll((Specification<ColumnInfoPairEnv>)
        (root, criteriaQuery, criteriaBuilder) -> {
        List<Predicate> list = new ArrayList<Predicate>();
        if (!StringUtils.isNone(columnInfoPairEnv.getId())) {
            list.add(criteriaBuilder.equal(root.get("columnInfoId").as(String.class), columnInfoPairEnv.getColumnInfoId()));
        }
if (!StringUtils.isNone(columnInfoPairEnv.getId())) {
            list.add(criteriaBuilder.equal(root.get("envId").as(String.class), columnInfoPairEnv.getEnvId()));
        }
if (!StringUtils.isNone(columnInfoPairEnv.getId())) {
            list.add(criteriaBuilder.equal(root.get("id").as(String.class), columnInfoPairEnv.getId()));
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
//    ColumnInfoPairEnvService columnInfoPairEnvService;

    /*

     create

     let  data= {  
         "columnInfoId":null, "columnInfoIdMax":null, "columnInfoIdMin":null ,
         "envId":null, "envIdMax":null, "envIdMin":null ,
         "id":null, "idMax":null, "idMin":null 
                  }
     axios.post(Common.baseUrl + "/columnInfoPairEnv/create",data).then((res) => {
     console.log("res");
     console.log(res);
     });

     this.$axios({
     method: "post",
     url:  "/columnInfoPairEnv/create",
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
//    public Object create(@RequestBody ColumnInfoPairEnv columnInfoPairEnv) {
//        boolean save = columnInfoPairEnvService.save(columnInfoPairEnv);
//        return ReturnT.success(save);
//    }


        }
