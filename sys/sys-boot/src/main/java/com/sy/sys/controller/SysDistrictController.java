package com.sy.sys.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.sy.center.framework.utils.DataformResult;
import com.sy.sys.entity.SysDistrict;
import com.sy.sys.service.SysDistrictService;
import com.sy.sys.vo.SysDistrictVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 /**
 * 区域表控制器.
 * 
 * @author zxwen
 * @date: 2021-09-18
 * @Copyright: Copyright (c) 2021
 * @Company:
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sysDistrict")
@Api(tags = "区域表")
public class SysDistrictController {
	private final Logger logger = LoggerFactory.getLogger(SysDistrictController.class);
	@Autowired
    private SysDistrictService sysDistrictService;
    
    @ApiOperation(value = "区域表-新增", notes = "区域表-新增")
    @PostMapping("/save")
    public DataformResult<String> save(@RequestBody SysDistrict sysDistrict) {
        if (null == sysDistrict.getId()) {
            sysDistrictService.save(sysDistrict);
        } else {
            sysDistrictService.updateById(sysDistrict);
        }
        return DataformResult.success();
    }

    @ApiOperation(value = "区域表-删除", notes = "区域表-刪除")
    @PostMapping("/remove/{id}")
    public DataformResult<String> removeById(@PathVariable("id") Long id) {
        sysDistrictService.removeById(id);
        return DataformResult.success();
    }

    @ApiOperation(value = "区域表-根据ID获取", notes = "区域表-根据ID获取")
    @GetMapping("/{id}")
    public DataformResult<SysDistrict> getById(@PathVariable("id") Long id) {
        SysDistrict sysDistrict = sysDistrictService.getById(id);
        return DataformResult.success(sysDistrict);
    }
    
    @ApiOperation(value = "区域表-根区域", notes = "获取区域根数据")
    @GetMapping("/listMain")
	public DataformResult<List<SysDistrictVo>> listMain() {
		// TODO Auto-generated method stub
		   List<SysDistrictVo> list = sysDistrictService.listMain();
	       return DataformResult.success(list);
	}
	
    @ApiOperation(value = "区域表-根据ID获取一级子记录", notes = "根据ID获取一级子记录 用于异步加载")
    @GetMapping("/listChildren")
	public DataformResult<List<SysDistrictVo>> listChildren(Long id) {
		// TODO Auto-generated method stub
    	List<SysDistrictVo> list = sysDistrictService.listChildren(id);
	    return DataformResult.success(list);
	}
}
