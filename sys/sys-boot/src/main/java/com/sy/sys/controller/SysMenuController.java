package com.sy.sys.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.sy.center.framework.utils.DataformResult;
import com.sy.sys.entity.SysMenu;
import com.sy.sys.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 /**
 * 系统_资源控制器.
 * 
 * @author xiangwy
 * @date: 2021-09-17
 * @Copyright: Copyright (c) 2021
 * @Company:
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sysMenu")
@Api(tags = "系统_资源")
public class SysMenuController {
	private final Logger logger = LoggerFactory.getLogger(SysMenuController.class);
	@Autowired
    private SysMenuService sysMenuService;
    
    @ApiOperation(value = "系统_资源-新增", notes = "系统_资源-新增")
    @PostMapping("/save")
    public DataformResult<String> save(@RequestBody SysMenu sysMenu) {
        if (null == sysMenu.getId()) {
            sysMenuService.save(sysMenu);
        } else {
            sysMenuService.updateById(sysMenu);
        }
        return DataformResult.success();
    }

    @ApiOperation(value = "系统_资源-删除", notes = "系统_资源-刪除")
    @PostMapping("/remove/{id}")
    public DataformResult<String> removeById(@PathVariable("id") Long id) {
        sysMenuService.removeById(id);
        return DataformResult.success();
    }

    @ApiOperation(value = "系统_资源-根据ID获取", notes = "系统_资源-根据ID获取")
    @GetMapping("/{id}")
    public DataformResult<SysMenu> getById(@PathVariable("id") Long id) {
        SysMenu sysMenu = sysMenuService.getById(id);
        return DataformResult.success(sysMenu);
    }
}
