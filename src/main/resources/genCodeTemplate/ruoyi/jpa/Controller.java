package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.service.ISysDeptService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
//import com.ruoyi.system.domain.#className#;
import com.ruoyi.system.entity.#className#;
//import com.ruoyi.system.service.I#className#Service;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * #entityName#Controller
 *
 * @author ruoyi
 * @date 2022-08-17
 */
@RestController
@RequestMapping("/system/#entityName#")
public class #className#Controller extends BaseController
{
//    @Autowired
//    private I#className#Service #entityName#Service;

    @Autowired
    private #className#Repository #entityName#Repository;

    @Autowired
    ISysDeptService deptService;

    @PreAuthorize("@ss.hasPermi('system:#entityName#:list')")
    @GetMapping("/list")
    public Object list(#className# #entityName#,
        @RequestParam(required = false, defaultValue = "0") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize)
    {
//        logger.info("hasPermi list");
//        logger.info("#entityName# {}",#entityName#);
//        startPage();
//        List<#className#> list = #entityName#Service.select#className#List(#entityName#);
//        List<#className#> list = #entityName#Repository.findAll(#entityName#);

        try {
            //创建匹配器，需要查询条件请修改此处代码
            ExampleMatcher matcher = ExampleMatcher.matchingAll();

            //创建实例
            Example<#className#> example = Example.of(#entityName#, matcher);
            //分页构造
            Pageable pageable = PageRequest.of(pageNumber, pageSize);

            return AjaxResult.success(#entityName#Repository.findAll(example, pageable));

        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }

//        return getDataTable(list);
    }
    /**
     * 导出#entityName#列表
     */
    @PreAuthorize("@ss.hasPermi('system:#entityName#:export')")
    @Log(title = "#entityName#", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, #className# #entityName#)
    {
        //创建匹配器，需要查询条件请修改此处代码
//        ExampleMatcher matcher = ExampleMatcher.matchingAll();
//
//        //创建实例
//        Example<#className#> example = Example.of(#entityName#, matcher);
//        //分页构造
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        return ReturnT.success(#entityName#Repository.findAll(example, pageable));
//        List<#className#> list =#entityName#Repository.findAll(example, pageable);
        List<#className#> list =#entityName#Repository.findAll();
//        List<#className#> list = #entityName#Service.select#className#List(#entityName#);
        ExcelUtil<#className#> util = new ExcelUtil<#className#>(#className#.class);
        util.exportExcel(response, list, "#entityName#数据");
    }

    /**
     * 获取#entityName#详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:#entityName#:query')")
//    @GetMapping(value = "/{humanid}")
//    public AjaxResult getInfo(@PathVariable("humanid") Long humanid)
//    {
//        return AjaxResult.success(#entityName#Service.select#className#ByHumanid(humanid));
//    }

        /**
         * 获取#entityName#详细信息
         */
//        @PreAuthorize("@ss.hasPermi('system:#entityName#:query')")
//        @GetMapping(value = "/{id}")
//        public AjaxResult getInfo(@PathVariable("id") Long id)
//        {
//                Optional<#className#> #entityName# = #entityName#Repository.findById(id);
//                if (#entityName#.isPresent()) {
//                return AjaxResult.success(#entityName#.get());
//                } else {
//                return AjaxResult.error("没有找到该对象");
//                }
////            return AjaxResult.success(#entityName#Service.select#className#ById(id));
//        }

@PreAuthorize("@ss.hasPermi('system:#entityName#:query')")
@GetMapping(value = "/{id}")
public AjaxResult getInfo(@PathVariable("id") Integer id) {
        Optional<#className#> byId = #entityName#Repository.findById(id);
        if (byId.isPresent()) {
        return AjaxResult.success(byId.get());
        } else {
        return AjaxResult.error("没有找到该对象");
        }
        }

    /**
     * 新增#entityName#
     */
//    @PreAuthorize("@ss.hasPermi('system:#entityName#:add')")
//    @Log(title = "#entityName#", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody #className# #entityName#)
//    {
//        return toAjax(#entityName#Service.insert#className#(#entityName#));
//    }

/**
 * 新增#entityName#
 */
@PreAuthorize("@ss.hasPermi('system:#entityName#:add')")
@Log(title = "#entityName#", businessType = BusinessType.INSERT)
@PostMapping
public AjaxResult add(@RequestBody #className# #entityName#)
        {
        try {
        return AjaxResult.success( #entityName#Repository.save(#entityName#));
//        return toAjax(#entityName#Repository.save(#entityName#));
//        return ReturnT.success(#entityName#Repository.save(#entityName#));
        } catch (Exception e) {
        e.printStackTrace();
        return AjaxResult.error("保存失败");
        }

//        return toAjax(#entityName#Service.insert#className#(#entityName#));
        }

    /**
     * 修改#entityName#
     */
//    @PreAuthorize("@ss.hasPermi('system:#entityName#:edit')")
//    @Log(title = "#entityName#", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody #className# #entityName#)
//    {
//        return toAjax(#entityName#Service.update#className#(#entityName#));
//    }

//    @PreAuthorize("@ss.hasPermi('system:#entityName#:edit')")
//    @Log(title = "#entityName#", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody #className# #entityName#)
//        {
//
//        try {
//           return toAjax(#entityName#Repository.save(#entityName#));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return AjaxResult.error("保存失败");
//        }
//
////        return toAjax(#entityName#Service.update#className#(#entityName#));
//        }

    @PreAuthorize("@ss.hasPermi('system:#entityName#:edit')")
    @Log(title = "#entityName#", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody #className# #entityName#) {
           try {
        #className# save = #entityName#Repository.save(#entityName#);
           return AjaxResult.success(save);
           } catch (Exception e) {
           e.printStackTrace();
           return AjaxResult.error(e.getMessage());
           }
           }

    /**
     * 删除#entityName#
     */
//    @PreAuthorize("@ss.hasPermi('system:#entityName#:remove')")
//    @Log(title = "#entityName#", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{humanids}")
//    public AjaxResult remove(@PathVariable Long[] humanids)
//    {
//        return toAjax(#entityName#Service.delete#className#ByHumanids(humanids));
//    }

@PreAuthorize("@ss.hasPermi('system:#entityName#:remove')")
@Log(title = "#entityName#", businessType = BusinessType.DELETE)
@DeleteMapping("/{ids}")
public AjaxResult remove(@PathVariable  List<Integer> ids)
        {
        try{
        #entityName#Repository.deleteAllById(ids);
        return AjaxResult.success();
        }catch (Exception e){
        e.printStackTrace();
        return AjaxResult.error();
        }

//        return toAjax(humanArchiveService.deleteHumanArchiveByHumanids(humanids));
        }
}
