package com.sy.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sy.center.framework.utils.DataformResult;
import com.sy.sys.entity.SysLoginLog;
import com.sy.sys.service.SysLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录日志控制器.
 *
 * @author xiangwy
 * @date: 2021-09-22
 * @Copyright: Copyright (c) 2021
 * @Company: 云南升玥信息技术有限公司
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sysLoginLog")
@Api(tags = "登录日志")
public class SysLoginLogController {
    private final Logger logger = LoggerFactory.getLogger(SysLoginLogController.class);
    @Autowired
    private SysLoginLogService sysLoginLogService;

    @ApiOperation(value = "登录日志-保存", notes = "登录日志-保存")
    @PostMapping("/save")
    public DataformResult<String> save(@RequestBody SysLoginLog sysLoginLog) {
        if (null == sysLoginLog.getId()) {
            sysLoginLogService.save(sysLoginLog);
        } else {
            sysLoginLogService.updateById(sysLoginLog);
        }
        return DataformResult.success();
    }

    @ApiOperation(value = "登录日志-删除", notes = "登录日志-刪除")
    @PostMapping("/remove/{id}")
    public DataformResult<String> removeById(@PathVariable("id") Long id) {
        sysLoginLogService.removeById(id);
        return DataformResult.success();
    }

    @ApiOperation(value = "登录日志-根据ID获取", notes = "登录日志-根据ID获取")
    @GetMapping("/{id}")
    public DataformResult<SysLoginLog> getById(@PathVariable("id") Long id) {
        SysLoginLog sysLoginLog = sysLoginLogService.getById(id);
        return DataformResult.success(sysLoginLog);
    }

    @ApiOperation(value = "用户登录日志-分页查询登录日志", notes = "用户登录日志-分页查询登录日志")
    @GetMapping("/pageSysLoginLog")
    public DataformResult<IPage<SysLoginLog>> pageSysLoginLog(Page page) {
        Page<SysLoginLog> pageParam = page;
        IPage<SysLoginLog> result = sysLoginLogService.page(pageParam);
        return DataformResult.success(result);
    }

    @ApiOperation(value = "用户登录日志-保存登录日志", notes = "用户登录日志-保存登录日志")
    @ApiImplicitParams({@ApiImplicitParam(required = false, name = "loginAccount", value = "登录账号", dataType = "String"),
            @ApiImplicitParam(required = false, name = "operType", value = "操作类型（1,登录;2,登出）", dataType = "String"),
            @ApiImplicitParam(required = false, name = "resultType", value = "登录结果类型(1,成功；2失败)", dataType = "String"),
            @ApiImplicitParam(required = false, name = "msg", value = "备注", dataType = "String")})
    @PostMapping("/saveLoginLog")
    public DataformResult<String> saveLoginLog(HttpServletRequest request, String loginAccount, String operType,
                                               String resultType, String msg) {
        sysLoginLogService.saveLoginLog(request, loginAccount, operType, resultType, msg);
        return DataformResult.success();
    }
}
