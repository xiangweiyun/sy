package com.sy.sys.controller;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sy.center.common.utils.MD5Util;
import com.sy.center.framework.utils.DataformResult;
import com.sy.sys.entity.SysUser;
import com.sy.sys.service.SysUserService;
import com.sy.sys.vo.SysUserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

 /**
 * 用户表控制器.
 * 
 * @author zxwen
 * @date: 2021-09-22
 * @Copyright: Copyright (c) 2021
 * @Company: 云南升玥信息技术有限公司
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sysUser")
@Api(tags = "用户表")
public class SysUserController {
	private final Logger logger = LoggerFactory.getLogger(SysUserController.class);
	@Autowired
    private SysUserService sysUserService;
	
	@Value("${initialPassword}")
	private String initialPassword;
    
    @ApiOperation(value = "用户表-保存", notes = "用户表-保存")
    @PostMapping("/save")
    public DataformResult<String> save(@RequestBody SysUser sysUser) {
        if (null == sysUser.getId()) {
        	if(sysUser.getPassword()==null || sysUser.getPassword().isEmpty()) {
        		if(initialPassword==null) {
        			return DataformResult.failure("初始密码不能为空");
        		}
        		sysUser.setPassword(initialPassword);
        	}
        	sysUser.setPassword(MD5Util.encrypt(sysUser.getPassword()));
        } else {
        	sysUser.setPassword(null);
        }
        sysUserService.saveUser(sysUser);
        return DataformResult.success();
    }
    
    @ApiOperation(value = "用户表-删除", notes = "用户表-刪除")
    @PostMapping("/remove/{id}")
    public DataformResult<String> removeById(@PathVariable("id") Long id) {
        sysUserService.removeById(id);
        return DataformResult.success();
    }

    @ApiOperation(value = "用户表-根据ID获取", notes = "用户表-根据ID获取")
    @GetMapping("/{id}")
    public DataformResult<SysUserVo> getById(@PathVariable("id") Long id) {
        SysUserVo sysUserVo = sysUserService.getVoById(id);
        return DataformResult.success(sysUserVo);
    }
    
    @ApiOperation(value = "用户表-系统登陆", notes = "用户表-系统登陆")
    @GetMapping("/login")
    public DataformResult<SysUser> getById(String userName, String passWord) {
    	if(userName==null || userName.isEmpty() || passWord==null || passWord.isEmpty()) {
    		return DataformResult.failure("用户名或密码不允许为空");
    	}
    	QueryWrapper<SysUser> queryWrapper = Wrappers.query();
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("USERNAME", userName);
		queryWrapper.allEq(map);
		SysUser sysUser = sysUserService.getOne(queryWrapper);
    	
		if(sysUser==null) {
			return DataformResult.failure("该用户在系统中不存在");
		}
		
		String md5Str = MD5Util.encrypt(passWord);
		if(md5Str.equals(sysUser.getPassword())) {
			SysUser sysUserInfo = new SysUser();
			sysUserInfo.setId(sysUser.getId());
			sysUserInfo.setName(sysUser.getName());
			sysUserInfo.setUsername(sysUser.getUsername());
			return DataformResult.success(sysUserInfo);
		}else {
			return DataformResult.failure("输入的密码不正确");
		}
    }
    
    @ApiOperation(value = "用户表-分页查询", notes = "用户表-分页查询")
    @ApiImplicitParams({
        @ApiImplicitParam(required = false, name = "page", value = "分页配置", dataType = "Page"),
        @ApiImplicitParam(required = false, name = "orgId", value = "机构ID", dataType = "Long"),
        @ApiImplicitParam(required = false, name = "deptId", value = "部门ID", dataType = "Long"),
        @ApiImplicitParam(required = false, name = "name", value = "姓名", dataType = "String"),
        @ApiImplicitParam(required = false, name = "userName", value = "帐号", dataType = "String"),
        @ApiImplicitParam(required = false, name = "noNum", value = "工号", dataType = "String")})
    @GetMapping("/pageListVo")
    public DataformResult<IPage<SysUserVo>> pageListVo(Page page, Long orgId, Long deptId, String name, String userName, String noNum) {
    	if(orgId==null) {
    		return DataformResult.failure("机构编码不允许为空");
    	}    	
    	IPage<SysUserVo> result = sysUserService.pageListVo(page, orgId, deptId, name, userName, noNum);
    	return DataformResult.success(result);
    }
    
    @ApiOperation(value = "用户表-根据角色ID分页查询", notes = "用户表-根据角色ID分页查询")
    @GetMapping("/pageListVoByRoleId")
    public DataformResult<IPage<SysUserVo>> pageListVoByRoleId(Page page, Long roleId) {
    	if(roleId==null) {
    		return DataformResult.failure("角色编码不允许为空");
    	}    	
    	IPage<SysUserVo> result = sysUserService.pageListVoByRoleId(page, roleId);
    	return DataformResult.success(result);
    }
    
    @ApiOperation(value = "重置密码", notes = "重置密码")
    @PostMapping("resetPassword")
    public DataformResult<String> resetPassword(Long userId){
    	if(initialPassword==null) {
			return DataformResult.failure("初始密码为空， 无法重置密码");
		}
    	SysUser sysUser = sysUserService.getById(userId);
    	if(sysUser == null) {
    		return DataformResult.failure("系统未找到该用户信息");
    	}
    	SysUser sysUserUpdate = new SysUser();
    	sysUserUpdate.setId(userId);
    	sysUserUpdate.setPassword(MD5Util.encrypt(initialPassword));
    	sysUserService.updateById(sysUserUpdate);
    	
    	return DataformResult.success();
    }
    
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @ApiImplicitParams({
    	@ApiImplicitParam(required = false, name = "userId", value = "用户ID", dataType = "Long"),
        @ApiImplicitParam(required = false, name = "userName", value = "用户帐号", dataType = "String"),
        @ApiImplicitParam(required = false, name = "oldPwd", value = "旧密码", dataType = "String"),
        @ApiImplicitParam(required = false, name = "newPwd", value = "新密码", dataType = "String")})
    @PostMapping("modifyPassword")
    public DataformResult<String> modifyPassword(Long userId, String userName, String oldPwd, String newPwd){
    	if(userId == null || userName == null || oldPwd == null || newPwd == null) {
    		return DataformResult.failure("传值不完整");
    	}
    	
    	SysUser sysUser = sysUserService.getById(userId);
    	if(sysUser == null) {
    		return DataformResult.failure("系统未找到该用户信息");
    	}
    	
    	if(!sysUser.getUsername().equals(userName)) {
    		return DataformResult.failure("用户帐号不正确");
    	}
    	
    	if(!sysUser.getPassword().equals(MD5Util.encrypt(oldPwd))) {
    		return DataformResult.failure("用户原始密码不正确");
    	}
    	
    	SysUser sysUserUpdate = new SysUser();
    	sysUserUpdate.setId(userId);
    	sysUserUpdate.setPassword(MD5Util.encrypt(newPwd));
    	sysUserService.updateById(sysUserUpdate);
    	
    	return DataformResult.success();
    }
}
