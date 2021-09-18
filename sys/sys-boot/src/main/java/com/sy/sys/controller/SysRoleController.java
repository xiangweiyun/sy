package com.sy.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sy.center.framework.utils.DataformResult;
import com.sy.sys.entity.SysRole;
import com.sy.sys.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统角色表控制器.
 *
 * @author xiangwy
 * @date: 2021-09-18
 * @Copyright: Copyright (c) 2021
 * @Company:
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sysRole")
@Api(tags = "系统角色表")
public class SysRoleController {
    private final Logger logger = LoggerFactory.getLogger(SysRoleController.class);
    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation(value = "系统角色表-新增", notes = "系统角色表-新增")
    @PostMapping("/save")
    public DataformResult<String> save(@RequestBody SysRole sysRole) {
        if (null == sysRole.getId()) {
            sysRoleService.save(sysRole);
        } else {
            sysRoleService.updateById(sysRole);
        }
        return DataformResult.success();
    }

    @ApiOperation(value = "系统角色表-删除", notes = "系统角色表-刪除")
    @PostMapping("/remove/{id}")
    public DataformResult<String> removeById(@PathVariable("id") Long id) {
        sysRoleService.removeById(id);
        return DataformResult.success();
    }

    @ApiOperation(value = "系统角色表-根据ID获取", notes = "系统角色表-根据ID获取")
    @GetMapping("/{id}")
    public DataformResult<SysRole> getById(@PathVariable("id") Long id) {
        SysRole sysRole = sysRoleService.getById(id);
        return DataformResult.success(sysRole);
    }

    @ApiOperation(value = "系统角色表-获取角色列表", notes = "系统角色表-获取角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(required = false, name = "orgId", value = "机构ID", dataType = "Long"),
            @ApiImplicitParam(required = false, name = "name", value = "角色名称", dataType = "String"),
            @ApiImplicitParam(required = false, name = "code", value = "角色编码", dataType = "String")})
    @GetMapping("/listSysRole")
    public DataformResult<List<SysRole>> listSysRole(Long orgId, String name, String code) {
        return DataformResult.success(sysRoleService.listSysRole(orgId, name, code));
    }

    @ApiOperation(value = "系统角色表-分页获取角色列表", notes = "系统角色表-分页获取角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(required = false, name = "orgId", value = "机构ID", dataType = "Long"),
            @ApiImplicitParam(required = false, name = "name", value = "角色名称", dataType = "String"),
            @ApiImplicitParam(required = false, name = "code", value = "角色编码", dataType = "String")})
    @GetMapping("/pageSysRole")
    public DataformResult<IPage<SysRole>> pageSysRole(Page page, Long orgId, String name, String code) {
        return DataformResult.success(sysRoleService.pageSysRole(page, orgId, name, code));
    }
}
