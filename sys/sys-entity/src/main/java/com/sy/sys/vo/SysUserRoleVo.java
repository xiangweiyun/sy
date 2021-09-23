package com.sy.sys.vo;

import com.sy.sys.entity.SysUserRole;

import io.swagger.annotations.ApiModelProperty;

/**
 * 用户角色VO
 * @author zxwen
 * @date 2021年9月22日
 *  
 */
public class SysUserRoleVo extends SysUserRole{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "角色描述")
	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
