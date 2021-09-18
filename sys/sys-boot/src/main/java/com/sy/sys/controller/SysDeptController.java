package com.sy.sys.controller;


import com.sy.center.framework.utils.DataformResult;
import com.sy.sys.entity.SysDept;
import com.sy.sys.service.SysDeptService;
import com.sy.sys.vo.SysDeptVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统部门控制器.
 * 
 * @author xiangwy
 * @date: 2021-09-18
 * @Copyright: Copyright (c) 2021
 * @Company: 云南升玥信息技术有限公司
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sysDept")
@Api(tags = "系统部门")
public class SysDeptController {
	private final Logger logger = LoggerFactory.getLogger(SysDeptController.class);
	@Autowired
    private SysDeptService sysDeptService;
    
    @ApiOperation(value = "系统部门-新增", notes = "系统部门-新增")
    @PostMapping("/save")
    public DataformResult<String> save(@RequestBody SysDept sysDept) {
        if (null == sysDept.getId()) {
            sysDeptService.save(sysDept);
        } else {
            sysDeptService.updateById(sysDept);
        }
        return DataformResult.success();
    }

    @ApiOperation(value = "系统部门-删除", notes = "系统部门-刪除")
    @PostMapping("/remove/{id}")
    public DataformResult<String> removeById(@PathVariable("id") Long id) {
        sysDeptService.removeById(id);
        return DataformResult.success();
    }

    @ApiOperation(value = "系统部门-根据ID获取", notes = "系统部门-根据ID获取")
    @GetMapping("/{id}")
    public DataformResult<SysDept> getById(@PathVariable("id") Long id) {
        SysDept sysDept = sysDeptService.getById(id);
        return DataformResult.success(sysDept);
    }

     @ApiOperation(value = "系统部门-根据机构获取部门列表(树型结构)", notes = "系统部门-根据机构获取部门列表(树型结构)")
     @GetMapping("/listTreeByOrgId/{orgId}")
     public DataformResult<List<SysDeptVo>> listTreeByOrgId(@PathVariable("orgId") Long orgId) {
         List<SysDeptVo> voList = sysDeptService.listTreeByOrgId(orgId);
         return DataformResult.success(voList);
     }
}
