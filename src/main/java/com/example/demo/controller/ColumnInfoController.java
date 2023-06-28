package com.example.demo.controller;
//package com.starp.exam.iView.controller;

//import com.starp.exam.entity.ColumnInfo;
//import com.starp.exam.repository.ColumnInfoRepository;
//import com.starp.exam.util.ReturnT;

import com.example.demo.entity.ColumnInfo;
import com.example.demo.entity.ReturnT;
import com.example.demo.repository.ColumnInfoRepository;
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
 * @description ColumnInfo
 * @date 2022-06-27
 */

@Slf4j
@Api(tags = "columnInfo")
//@CrossOrigin
@CrossOrigin(allowCredentials = "true")
@RestController
//@RequestMapping("/columnInfo")
@RequestMapping("/api/columnInfo")
public class ColumnInfoController {

    @Autowired
    private ColumnInfoRepository columnInfoRepository;

    /*
     新增或编辑

     let  data= {
     "columnName":null ,
     "comment":null ,
     "columnType":null ,
     "id":null, "idMax":null, "idMin":null
              }
     axios.post(Common.baseUrl + "/columnInfo/save",data).then((res) => {
     console.log("res");
     console.log(res);
     let  data= res.data.data
     });

entitySave(){

     let  data= {
     "columnName":null ,
     "comment":null ,
     "columnType":null ,
     "id":null, "idMax":null, "idMin":null
              }

     this.$axios({
            method: "post",
            url: "/columnInfo/save",
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
    @ApiOperation(value = "save columnInfo", notes = "save columnInfo")
    public Object save(@RequestBody ColumnInfo columnInfo) {
        try {

            return ReturnT.success(columnInfoRepository.save(columnInfo));
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnT.error("保存失败");
        }

    }


    /*

     let  data=  [
        {
             "columnName":null ,
             "comment":null ,
             "columnType":null ,
             "id":null, "idMax":null, "idMin":null
                      }
     ]
             this.$axios({
                    method: "post",
                    url: "/columnInfo/saveAll",
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
             "columnName":null ,
             "comment":null ,
             "columnType":null ,
             "id":null, "idMax":null, "idMin":null
                      }
     ]
     axios.post(Common.baseUrl + "/columnInfo/saveAll",data).then(res => {
     console.log("res");
     console.log(res);
     Toast('saveAll成功');
     let  data= res.data.data
     });
     */
    @PostMapping("/saveAll")
    @ApiOperation(value = "saveAll", notes = "saveAll")
    public Object saveAll(@RequestBody List<ColumnInfo> list) {
        try {
            return ReturnT.success(columnInfoRepository.saveAll(list));
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnT.error("保存失败");
        }

    }

    void d(){
        // TODO: 2023/6/18 怎么知道id 前端 列表 env col col 点击 下拉 一个env list,说这个env是的 这个col 是在这env里面的
//        那么就是 pair  api是有的 . 列出env 的 api有. col 想要批量env, env list 有.多选前端 save all .
//        是json list envid 和col id的 pair 的list,
//        col 要del  env , col envid 可以dele delete flag, update pair
//        col  env update,因为是 pair 所以不用update 就两个值
//        env pair save 有重复 多选 知道哪些选定了 但是不是diff的 save 还是saveall
//        所以 del all col id 的 然后 save all env col?  name col 的 zucc env 删除? 确实
//        如果是删除env id呢 zucc的 sex 删除? name 删除 然后 save . 保存了 zucc的 name.zucc 的sex会保存吗
//        不会 因为是 现在保存的是 name 字段 那么sex 字段的zucc pair 就丢失了 所以必须是删除col id的
//        所以有一对多关系
//        如果是env list 要选择他的字段呢 就是反过来
//        如果三层关系 学校 班级
//         公司 部门 员工
//        公司的 部门  删除公司的 部门.. 公司和员工  公司 的部门-
//        get comm by name
//        com dept id   ,.... dept ids
//        employ dep pair where depid in ids
//         com  dept and deot emp where com id =id and depid
//         columnInfoRepository.save()
//                colEnv save 是一个 pair
    }


    /*
   删除
     let  data= {}
     axios.post(Common.baseUrl + "/columnInfo/delete?id="+id,data).then((res) => {
     console.log("res");
     console.log(res);
     Toast('删除成功');
     let  data= res.data.data
     });

     this.$axios({
            method: "post",
            url: "/columnInfo/delete?id="+id,
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
    @ApiOperation(value = "delete columnInfo", notes = "delete columnInfo")
    public Object delete(int id) {
        Optional<ColumnInfo> columnInfo = columnInfoRepository.findById(id);
        if (columnInfo.isPresent()) {
            columnInfoRepository.deleteById(id);
            return ReturnT.success("删除成功");
        } else {
            return ReturnT.error("没有找到该对象");
        }
    }

    /*
     let  data= {  
         "columnName":null ,
         "comment":null ,
         "columnType":null ,
         "id":null, "idMax":null, "idMin":null 
                  }
     axios.post(Common.baseUrl + "/columnInfo/deleteBy",data).then((res) => {
     console.log("res");
     console.log(res);
     Toast('删除成功');
      let  data= res.data.data

     });

      this.$axios({
				method: "post",
				url: "/columnInfo/deleteBy",
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
    public Object deleteBy(@RequestBody ColumnInfo columnInfo) {
        try {
            Integer id = columnInfo.getId();
            columnInfoRepository.deleteById(id);
            return ReturnT.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnT.error(e.getMessage());
        }
    }


    /*


      axios.post(Common.baseUrl + "/columnInfo/find?id="+id,{}).then(res => {
      console.log("res");
      console.log(res);
     Toast('查找成功');
     let  data= res.data.data

      });

     this.$axios({
                 method: "post",
                 url: "/columnInfo/find?id="+id,
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
    @ApiOperation(value = "find columnInfo by id", notes = "find columnInfo by id")
    public Object find(int id) {
        Optional<ColumnInfo> columnInfo = columnInfoRepository.findById(id);
        if (columnInfo.isPresent()) {
            return ReturnT.success(columnInfo.get());
        } else {
            return ReturnT.error("没有找到该对象");
        }
    }

    /**
     * 分页查询
     * let  data= {
     * "columnName":null ,
     * "comment":null ,
     * "columnType":null ,
     * "id":null, "idMax":null, "idMin":null
     * }
     * axios.post(Common.baseUrl + "/columnInfo/list",data).then((res) => {
     * console.log("res");
     * console.log(res);
     * let   columnInfoList=  res.data.data.content
     * Toast('查找成功');
     * });
     * <p>
     * this.$axios({
     * method: "post",
     * url: "/columnInfo/list",
     * data: data,
     * }).then(res => {
     * this.datalist = res.data;
     * console.log("res save");
     * console.log(res);
     * }).catch(err => {
     * console.log("err");
     * console.log(err);
     * })
     */
    @PostMapping("/list")
    @ApiOperation(value = "list columnInfo", notes = "list columnInfo")
    public Object list(@RequestBody ColumnInfo columnInfo,
                       @RequestParam(required = false, defaultValue = "0") int pageNumber,
                       @RequestParam(required = false, defaultValue = "10") int pageSize) {

        try {
            //创建匹配器，需要查询条件请修改此处代码
            ExampleMatcher matcher = ExampleMatcher.matchingAll();

            //创建实例
            Example<ColumnInfo> example = Example.of(columnInfo, matcher);
            //分页构造
            Pageable pageable = PageRequest.of(pageNumber, pageSize);

            return ReturnT.success(columnInfoRepository.findAll(example, pageable));

        } catch (Exception e) {
            e.printStackTrace();
            return ReturnT.error(e.getMessage());
        }

    }


    /**
     * let  data= [ {
     * "columnName":null ,
     * "comment":null ,
     * "columnType":null ,
     * "id":null, "idMax":null, "idMin":null
     * }  ]
     * axios.post(Common.baseUrl + "/columnInfo/findAllById",data).then((res) => {
     * console.log("res");
     * console.log(res);
     * let   columnInfoList=  res.data.data.content
     * });
     * <p>
     * <p>
     * this.$axios({
     * method: "post",
     * url: "/columnInfo/findAllById",
     * data: data,
     * }).then(res => {
     * this.datalist = res.data;
     * console.log("res save");
     * console.log(res);
     * }).catch(err => {
     * console.log("err");
     * console.log(err);
     * })
     */
    @PostMapping("/findAllById")
    @ApiOperation(value = "findAllById", notes = "findAllById")
    public Object findAllById(@RequestBody List<Integer> ids,
                              @RequestParam(required = false, defaultValue = "0") int pageNumber,
                              @RequestParam(required = false, defaultValue = "10") int pageSize) {
        try {
            List<ColumnInfo> allById = columnInfoRepository.findAllById(ids);
            StarpPage<ColumnInfo> starpPage = new StarpPage<>(allById, pageNumber, pageSize);
            return ReturnT.success(starpPage);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnT.error("保存失败");
        }
    }


    /**
     * let  data= [ {
     * "columnName":null ,
     * "comment":null ,
     * "columnType":null ,
     * "id":null, "idMax":null, "idMin":null
     * }  ]
     * axios.post(Common.baseUrl + "/columnInfo/deleteInBatch",data).then((res) => {
     * console.log("res");
     * console.log(res);
     * let   columnInfoList=  res.data.data.content
     * });
     * <p>
     * this.$axios({
     * method: "post",
     * url: "/columnInfo/deleteInBatch",
     * data: data,
     * }).then(res => {
     * this.datalist = res.data;
     * console.log("res save");
     * console.log(res);
     * }).catch(err => {
     * console.log("err");
     * console.log(err);
     * })
     */
    @PostMapping("/deleteInBatch")
    @ApiOperation(value = "deleteInBatch", notes = "deleteInBatch")
    public Object deleteInBatch(@RequestBody List<ColumnInfo> entities) {
        try {
            columnInfoRepository.deleteInBatch(entities);
            return ReturnT.success("批量删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnT.error("批量删除失败");
        }
    }

    /**
     * listLike
     * let columnInfoList: any = ref([]);
     * let  data= {
     * "columnName":null ,
     * "comment":null ,
     * "columnType":null ,
     * "id":null, "idMax":null, "idMin":null
     * }
     * axios.post(Common.baseUrl + "/columnInfo/listLike",data).then((res) => {
     * console.log("res");
     * console.log(res);
     * columnInfoList.value = res.data.data.content     });
     * <p>
     * <p>
     * this.$axios({
     * method: "post",
     * url: "/columnInfo/listLike",
     * data: data,
     * }).then(res => {
     * this.datalist = res.data;
     * console.log("res save");
     * console.log(res);
     * }).catch(err => {
     * console.log("err");
     * console.log(err);
     * })
     */
    @PostMapping("/listLike")
    @ApiOperation(value = "listLike", notes = "listLike")
    public Object listLike(@RequestBody ColumnInfo columnInfo,
                           @RequestParam(required = false, defaultValue = "0") int pageNumber,
                           @RequestParam(required = false, defaultValue = "10") int pageSize) {

        try {

            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            Page<ColumnInfo> page = columnInfoRepository.findAll((Specification<ColumnInfo>)
                    (root, criteriaQuery, criteriaBuilder) -> {
                        List<Predicate> list = new ArrayList<Predicate>();
                        if (!StringUtils.isNone(columnInfo.getId())) {
                            list.add(criteriaBuilder.equal(root.get("columnName").as(String.class), columnInfo.getColumnName()));
                        }
                        if (!StringUtils.isNone(columnInfo.getId())) {
                            list.add(criteriaBuilder.equal(root.get("comment").as(String.class), columnInfo.getComment()));
                        }
                        if (!StringUtils.isNone(columnInfo.getId())) {
                            list.add(criteriaBuilder.equal(root.get("columnType").as(String.class), columnInfo.getColumnType()));
                        }
                        if (!StringUtils.isNone(columnInfo.getId())) {
                            list.add(criteriaBuilder.equal(root.get("id").as(String.class), columnInfo.getId()));
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
//    ColumnInfoService columnInfoService;

    /*

     create

     let  data= {  
         "columnName":null ,
         "comment":null ,
         "columnType":null ,
         "id":null, "idMax":null, "idMin":null 
                  }
     axios.post(Common.baseUrl + "/columnInfo/create",data).then((res) => {
     console.log("res");
     console.log(res);
     });

     this.$axios({
     method: "post",
     url:  "/columnInfo/create",
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
//    public Object create(@RequestBody ColumnInfo columnInfo) {
//        boolean save = columnInfoService.save(columnInfo);
//        return ReturnT.success(save);
//    }


}
