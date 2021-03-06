package com.sy.sys.controller;


import com.sy.center.framework.utils.DataformResult;
import com.sy.sys.entity.SysOrg;
import com.sy.sys.service.SysOrgService;
import com.sy.sys.vo.SysOrgVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

 /**
 * 系统机构表控制器.
 * 
 * @author zxwen
 * @date: 2021-09-17
 * @Copyright: Copyright (c) 2021
 * @Company: 云南升玥信息技术有限公司
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sysOrg")
@Api(tags = "系统机构表")
public class SysOrgController {
	private final Logger logger = LoggerFactory.getLogger(SysOrgController.class);
	@Autowired
    private SysOrgService sysOrgService;
    
    @ApiOperation(value = "系统机构表-保存", notes = "系统机构表-保存")
    @PostMapping("/save")
    public DataformResult<String> save(@RequestBody SysOrg sysOrg) {
        if (null == sysOrg.getId()) {
            sysOrgService.save(sysOrg);
        } else {
            sysOrgService.updateById(sysOrg);
        }
        return DataformResult.success();
    }

    @ApiOperation(value = "系统机构表-删除", notes = "系统机构表-刪除")
    @PostMapping("/remove/{id}")
    public DataformResult<String> removeById(@PathVariable("id") Long id) {
        sysOrgService.removeById(id);
        return DataformResult.success();
    }

    @ApiOperation(value = "系统机构表-根据ID获取", notes = "系统机构表-根据ID获取")
    @GetMapping("/{id}")
    public DataformResult<SysOrg> getById(@PathVariable("id") Long id) {
        SysOrg sysOrg = sysOrgService.getById(id);
        return DataformResult.success(sysOrg);
    }
    
    @ApiOperation(value = "系统机构表-树型结构", notes = "系统机构表-传值orgId 会获取本机构及下属机构数据 ，不传值获取所有机构")
    @GetMapping("/getTreeData")
    public DataformResult<List<SysOrgVo>> getTreeData(Long orgId) {
        return DataformResult.success(sysOrgService.getTreeData(orgId));
    }
    
    @ApiOperation(value = "系统机构表-LIST结构", notes = "系统机构表-传值orgId 根据机构ID,查询机构子记录，包含机构本身")
    @GetMapping("/listChildById")
    public DataformResult<List<SysOrgVo>> listChildById(Long orgId) {
        return DataformResult.success(sysOrgService.listChildById(orgId));
    }
}
