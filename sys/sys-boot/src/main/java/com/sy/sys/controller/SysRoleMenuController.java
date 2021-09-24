package com.sy.sys.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sy.center.common.utils.JSON;
import com.sy.center.framework.utils.DataformResult;
import com.sy.sys.entity.SysMenu;
import com.sy.sys.entity.SysRoleMenu;
import com.sy.sys.service.SysRoleMenuService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * 角色和菜单关联表控制器.
 * 
 * @author zxwen
 * @date: 2021-09-22
 * @Copyright: Copyright (c) 2021
 * @Company: 云南升玥信息技术有限公司
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sysRoleMenu")
@Api(tags = "角色和菜单关联表")
public class SysRoleMenuController {
	private final Logger logger = LoggerFactory.getLogger(SysRoleMenuController.class);
	@Autowired
    private SysRoleMenuService sysRoleMenuService;
    
    @ApiOperation(value = "角色和菜单关联表-保存", notes = "角色和菜单关联表-保存")
    @PostMapping("/save")
    public DataformResult<String> save(@RequestBody SysRoleMenu sysRoleMenu) {
        if (null == sysRoleMenu.getId()) {
            sysRoleMenuService.save(sysRoleMenu);
        } else {
            sysRoleMenuService.updateById(sysRoleMenu);
        }
        return DataformResult.success();
    }

    @ApiOperation(value = "角色和菜单关联表-删除", notes = "角色和菜单关联表-刪除")
    @PostMapping("/remove/{id}")
    public DataformResult<String> removeById(@PathVariable("id") Long id) {
        sysRoleMenuService.removeById(id);
        return DataformResult.success();
    }

    @ApiOperation(value = "角色和菜单关联表-根据ID获取", notes = "角色和菜单关联表-根据ID获取")
    @GetMapping("/{id}")
    public DataformResult<SysRoleMenu> getById(@PathVariable("id") Long id) {
        SysRoleMenu sysRoleMenu = sysRoleMenuService.getById(id);
        return DataformResult.success(sysRoleMenu);
    }
    
    @ApiOperation(value = "角色和菜单关联表-根据角色ID获取菜单", notes = "角色和菜单关联表-根据角色ID获取菜单")
    @GetMapping("/listByRoleId")
    public DataformResult<List<SysRoleMenu>> listByRoleId(Long roleId) {
        return DataformResult.success(sysRoleMenuService.listByRoleId(roleId));
    }
    
    @ApiOperation(value = "角色和菜单关联表-批量保存", notes = "角色和菜单关联表-批量保存")
    @PostMapping("/saveBatch")
    public DataformResult<String> saveBatch(@RequestBody List<SysRoleMenu> listSysRoleMenu) {
    	if(listSysRoleMenu==null || listSysRoleMenu.size() == 0) {
    		return DataformResult.failure("没有可以操作的内容");
    	}
    	sysRoleMenuService.saveBatch(listSysRoleMenu);
        return DataformResult.success();
    }
}
