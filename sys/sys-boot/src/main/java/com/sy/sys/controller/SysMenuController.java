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

import com.sy.center.common.utils.jwt.TokenUtil;
import com.sy.center.framework.utils.DataformResult;
import com.sy.sys.entity.SysMenu;
import com.sy.sys.service.SysMenuService;
import com.sy.sys.vo.SysMenuVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * 系统菜单表控制器.
 * 
 * @author zxwen
 * @date: 2021-09-18
 * @Copyright: Copyright (c) 2021
 * @Company: 云南升玥信息技术有限公司
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sysMenu")
@Api(tags = "系统菜单表")
public class SysMenuController {
	private final Logger logger = LoggerFactory.getLogger(SysMenuController.class);
	@Autowired
    private SysMenuService sysMenuService;
    
    @ApiOperation(value = "系统菜单表-保存", notes = "系统菜单表-保存")
    @PostMapping("/save")
    public DataformResult<String> save(@RequestBody SysMenu sysMenu) {
        if (null == sysMenu.getId()) {
            sysMenuService.save(sysMenu);
        } else {
            sysMenuService.updateById(sysMenu);
        }
        return DataformResult.success();
    }

    @ApiOperation(value = "系统菜单表-删除", notes = "系统菜单表-刪除")
    @PostMapping("/remove/{id}")
    public DataformResult<String> removeById(@PathVariable("id") Long id) {
        sysMenuService.removeById(id);
        return DataformResult.success();
    }
    
    @ApiOperation(value = "系统菜单表-根据ID获取", notes = "系统菜单表-根据ID获取")
    @GetMapping("/{id}")
    public DataformResult<SysMenu> getById(@PathVariable("id") Long id) {
        SysMenu sysMenu = sysMenuService.getById(id);
        return DataformResult.success(sysMenu);
    }
    
    @ApiOperation(value = "系统菜单表-树型结构【用于菜单管理】", notes = "系统菜单表-获取树型数据")
    @GetMapping("/listTreeData")
    public DataformResult<List<SysMenuVo>> listTreeData() {
        return DataformResult.success(sysMenuService.listTreeData());
    }
    
    @ApiOperation(value = "系统菜单表-树型结构【用于应用权限菜单】", notes = "系统菜单表-根据应用ID及用户ID获取树型数据")
    @GetMapping("/listTreeDataByAppIdAndUserId")
    public DataformResult<List<SysMenuVo>> listTreeDataByAppIdAndUserId(Long appId) {
    	if(appId == null) {
    		return DataformResult.failure("应用ID不允许为空");
    	}
    	
        return DataformResult.success(sysMenuService.listTreeDataByAppIdAndUserId(appId, Long.valueOf(TokenUtil.getUserId())));
    }
    
    @ApiOperation(value = "系统菜单表-树型结构【用于角色菜单授权】", notes = "系统菜单表-根据角色ID获取所有应用树型数据及选中的节点")
    @GetMapping("/listTreeDataByAppIdAndRoleId")
    public DataformResult<List<SysMenuVo>> listTreeDataByRoleId(Long roldId) {
    	if(roldId == null) {
    		return DataformResult.failure("角色ID不允许为空");
    	}
    	
    	 return DataformResult.success(sysMenuService.listTreeDataByRoleId(roldId));
    }    
}
