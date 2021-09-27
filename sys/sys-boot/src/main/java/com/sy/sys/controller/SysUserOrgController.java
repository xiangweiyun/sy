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
import com.sy.sys.entity.SysUserOrg;
import com.sy.sys.service.SysUserOrgService;
import com.sy.sys.vo.SysUserOrgVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * 用户关联机构控制器.
 * 
 * @author zxwen
 * @date: 2021-09-27
 * @Copyright: Copyright (c) 2021
 * @Company: 云南升玥信息技术有限公司
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sysUserOrg")
@Api(tags = "用户关联机构")
public class SysUserOrgController {
	private final Logger logger = LoggerFactory.getLogger(SysUserOrgController.class);
	@Autowired
    private SysUserOrgService sysUserOrgService;
    
    @ApiOperation(value = "用户关联机构-保存", notes = "用户关联机构-保存")
    @PostMapping("/save")
    public DataformResult<String> save(@RequestBody SysUserOrg sysUserOrg) {
        if (null == sysUserOrg.getId()) {
            sysUserOrgService.save(sysUserOrg);
        } else {
            sysUserOrgService.updateById(sysUserOrg);
        }
        return DataformResult.success();
    }

    @ApiOperation(value = "用户关联机构-删除", notes = "用户关联机构-刪除")
    @PostMapping("/remove/{id}")
    public DataformResult<String> removeById(@PathVariable("id") Long id) {
        sysUserOrgService.removeById(id);
        return DataformResult.success();
    }

    @ApiOperation(value = "用户关联机构-根据ID获取", notes = "用户关联机构-根据ID获取")
    @GetMapping("/{id}")
    public DataformResult<SysUserOrg> getById(@PathVariable("id") Long id) {
        SysUserOrg sysUserOrg = sysUserOrgService.getById(id);
        return DataformResult.success(sysUserOrg);
    }
    
    @ApiOperation(value = "用户关联机构-批量保存", notes = "用户关联机构-批量保存")
    @PostMapping("/saveBatch")
    public DataformResult<String> saveBatch(@RequestBody List<SysUserOrg> listSysUserOrg) {
    	if(listSysUserOrg==null || listSysUserOrg.size() == 0) {
    		return DataformResult.failure("没有可以操作的内容");
    	}
    	
    	sysUserOrgService.saveBatch(listSysUserOrg);
        return DataformResult.success();
    }
    
    @ApiOperation(value = "用户关联机构-根据用户ID获取", notes = "根据用户ID获取要关联关联机构信息")
    @GetMapping("/listByUserId")
    public DataformResult<List<SysUserOrgVo>> listByUserId(Long userId) {
		// TODO Auto-generated method stub
		return DataformResult.success(sysUserOrgService.listByUserId(userId));
	}
}
