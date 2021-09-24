package com.sy.sys.controller;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sy.center.framework.utils.DataformResult;
import com.sy.sys.entity.SysOrg;
import com.sy.sys.service.SysMenuService;
import com.sy.sys.service.SysUserOrgDeptService;
import com.sy.sys.service.SysUserService;
import com.sy.sys.vo.SysDeptVo;
import com.sy.sys.vo.SysMenuVo;
import com.sy.sys.vo.SysUserOrgDeptVo;
import com.sy.sys.vo.SysUserVo;
import com.sy.sys.vo.user.Dept;
import com.sy.sys.vo.user.Org;
import com.sy.sys.vo.user.User;

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
@RequestMapping("/user")
@Api(tags = "用户相关权限信息")
public class UserController {
	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
    private SysUserService sysUserService;
	
	@Autowired
    private SysUserOrgDeptService sysUserOrgDeptService;
	
	@Autowired
    private SysMenuService sysMenuService;
   
    @ApiOperation(value = "用户表-根据角色ID分页查询", notes = "用户表-根据角色ID分页查询")
    @ApiImplicitParams({
        @ApiImplicitParam(required = false, name = "appId", value = "应用ID", dataType = "Long"),
        @ApiImplicitParam(required = false, name = "userId", value = "用户ID", dataType = "Long")})
    @GetMapping("/information")
    public DataformResult<User> pageListVoByRoleId(Long appId, Long userId) {
    	SysUserVo sysUserVo =  sysUserService.getVoById(userId);
    	if(sysUserVo==null) {
    		DataformResult.failure("未找到该用户相关信息");
    	}
    	
    	User user = new User();
    	user.setId(sysUserVo.getId());
    	user.setOrgId(sysUserVo.getOrgId());
    	user.setOrgName(sysUserVo.getOrgName());
    	user.setDeptId(sysUserVo.getDeptId());
    	user.setDeptName(sysUserVo.getDeptName());
    	user.setJobCode(sysUserVo.getJobTitleCode());
    	user.setJobName(sysUserVo.getJobName());
    	user.setPostCode(sysUserVo.getPostCode());
    	user.setPostName(sysUserVo.getPostName());
    	user.setName(sysUserVo.getName());
    	user.setUserName(sysUserVo.getUsername());
    	
    	List<SysUserOrgDeptVo> list = sysUserOrgDeptService.listByUserId(userId);
    	setOrgDeptInfo(user, list);
    	
    	List<SysMenuVo> menu = sysMenuService.listTreeDataByAppIdAndUserId(appId, userId);
    	user.setListMenu(menu);
    	
    	return DataformResult.success(user);
    }
    
    
    private void setOrgDeptInfo(User user, List<SysUserOrgDeptVo> list) {
    	if(list == null) {
    		return;
    	}
    	List<Org> listOrg = new ArrayList<Org>();
    	List<Dept> listDept = new ArrayList<Dept>();
    	
    	for(SysUserOrgDeptVo sysUserOrgDeptVo : list) {
    		Dept  dept = new Dept();
    		dept.setDeptId(sysUserOrgDeptVo.getDeptId());
    		dept.setDeptName(sysUserOrgDeptVo.getDeptName());
    		dept.setOrgId(sysUserOrgDeptVo.getOrgId());
    		dept.setOrgName(sysUserOrgDeptVo.getOrgName());
    		listDept.add(dept);
    		
    		boolean isExists = false;
    		for(Org org : listOrg) {
    			if(org.getOrgId().equals(sysUserOrgDeptVo.getOrgId())) {
    				isExists = true;
    				break;
    			}
    		}
    		
    		if(!isExists) {
    			Org org = new Org();
    			org.setOrgId(sysUserOrgDeptVo.getOrgId());
    			org.setOrgName(sysUserOrgDeptVo.getOrgName());
    		}
    	}
    	
    	user.setListDept(listDept);
    	user.setListOrg(listOrg);
    }
}
