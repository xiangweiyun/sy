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
import com.sy.sys.entity.SysUserRole;
import com.sy.sys.service.SysUserRoleService;
import com.sy.sys.vo.SysUserRoleVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * 用户和角色关联表控制器.
 * 
 * @author zxwen
 * @date: 2021-09-22
 * @Copyright: Copyright (c) 2021
 * @Company: 云南升玥信息技术有限公司
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sysUserRole")
@Api(tags = "用户和角色关联表")
public class SysUserRoleController {
	private final Logger logger = LoggerFactory.getLogger(SysUserRoleController.class);
	@Autowired
    private SysUserRoleService sysUserRoleService;
    
    @ApiOperation(value = "用户和角色关联表-保存", notes = "用户和角色关联表-保存")
    @PostMapping("/save")
    public DataformResult<String> save(@RequestBody SysUserRole sysUserRole) {
        if (null == sysUserRole.getId()) {
            sysUserRoleService.save(sysUserRole);
        } else {
            sysUserRoleService.updateById(sysUserRole);
        }
        return DataformResult.success();
    }

    @ApiOperation(value = "用户和角色关联表-删除", notes = "用户和角色关联表-刪除")
    @PostMapping("/remove/{id}")
    public DataformResult<String> removeById(@PathVariable("id") Long id) {
        sysUserRoleService.removeById(id);
        return DataformResult.success();
    }

    @ApiOperation(value = "用户和角色关联表-根据ID获取", notes = "用户和角色关联表-根据ID获取")
    @GetMapping("/{id}")
    public DataformResult<SysUserRole> getById(@PathVariable("id") Long id) {
        SysUserRole sysUserRole = sysUserRoleService.getById(id);
        return DataformResult.success(sysUserRole);
    }
    
    @ApiOperation(value = "用户和角色关联表-批量保存", notes = "用户和角色关联表-批量保存")
    @PostMapping("/saveBatch")
    public DataformResult<String> saveBatch(@RequestBody List<SysUserRole> listSysUserRole) {
    	if(listSysUserRole==null || listSysUserRole.size() == 0) {
    		return DataformResult.failure("没有可以操作的内容");
    	}
    	sysUserRoleService.saveBatch(listSysUserRole);
        return DataformResult.success();
    }
    
    @ApiOperation(value = "用户和角色关联表-角色列表", notes = "根据用户ID获取角色信息")
    @GetMapping("/listByUserId")
    public DataformResult<List<SysUserRoleVo>> listByUserId(Long userId) {
		// TODO Auto-generated method stub
    	return DataformResult.success(sysUserRoleService.listByUserId(userId));
	}
}
