package com.sy.sys.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sy.center.framework.utils.DataformResult;
import com.sy.sys.entity.SysUserOrgDept;
import com.sy.sys.service.SysUserOrgDeptService;
import com.sy.sys.vo.SysUserRoleVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * 用户部门关联表控制器.
 * 
 * @author zxwen
 * @date: 2021-09-22
 * @Copyright: Copyright (c) 2021
 * @Company: 云南升玥信息技术有限公司
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sysUserOrgDept")
@Api(tags = "用户部门关联表")
public class SysUserOrgDeptController {
	private final Logger logger = LoggerFactory.getLogger(SysUserOrgDeptController.class);
	@Autowired
    private SysUserOrgDeptService sysUserOrgDeptService;
    
    @ApiOperation(value = "用户部门关联表-保存", notes = "用户部门关联表-保存")
    @PostMapping("/save")
    public DataformResult<String> save(@RequestBody SysUserOrgDept sysUserOrgDept) {
        if (null == sysUserOrgDept.getId()) {
            sysUserOrgDeptService.save(sysUserOrgDept);
        } else {
            sysUserOrgDeptService.updateById(sysUserOrgDept);
        }
        return DataformResult.success();
    }

    @ApiOperation(value = "用户部门关联表-删除", notes = "用户部门关联表-刪除")
    @PostMapping("/remove/{id}")
    public DataformResult<String> removeById(@PathVariable("id") Long id) {
        sysUserOrgDeptService.removeById(id);
        return DataformResult.success();
    }

    @ApiOperation(value = "用户部门关联表-根据ID获取", notes = "用户部门关联表-根据ID获取")
    @GetMapping("/{id}")
    public DataformResult<SysUserOrgDept> getById(@PathVariable("id") Long id) {
        SysUserOrgDept sysUserOrgDept = sysUserOrgDeptService.getById(id);
        return DataformResult.success(sysUserOrgDept);
    }
    
    @ApiOperation(value = "用户部门关联表-批量保存", notes = "用户部门关联表-批量保存")
    @PostMapping("/saveBatch")
    public DataformResult<String> saveBatch(@RequestBody List<SysUserOrgDept> listSysUserOrgDept) {
    	if(listSysUserOrgDept==null || listSysUserOrgDept.size() == 0) {
    		return DataformResult.failure("没有可以操作的内容");
    	}
    	
    	int mainCount = 0;
    	for(SysUserOrgDept sysUserOrgDept:listSysUserOrgDept) {
    		if(sysUserOrgDept.getHasMain() != null && sysUserOrgDept.getHasMain()) {
    			mainCount++;
    		}
    	}
    	if(mainCount==0) {
    		return DataformResult.failure("没有设置主部门");
    	}
    	if(mainCount>1) {
    		return DataformResult.failure("主部门只能唯一");
    	}
    	sysUserOrgDeptService.saveBatch(listSysUserOrgDept);
        return DataformResult.success();
    }
    
    @ApiOperation(value = "用户部门关联表-根据用户ID获取", notes = "根据用户ID获取要关联的部门信息")
    @GetMapping("/listByUserId")
    public DataformResult<List<SysUserRoleVo>> listByUserId(Long userId) {
		// TODO Auto-generated method stub
		return DataformResult.success(sysUserOrgDeptService.listByUserId(userId));
	}
}
