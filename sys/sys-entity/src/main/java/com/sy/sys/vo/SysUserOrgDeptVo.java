package com.sy.sys.vo;

import com.sy.sys.entity.SysUserOrgDept;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户部门关联表VO
 * @author zxwen
 * @date 2021年9月23日
 *  
 */

@ApiModel(value="SysUserOrgDeptVo对象", description="用户部门关联表VO")
public class SysUserOrgDeptVo extends SysUserOrgDept{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "机构描述")
	private String orgName;
	
	@ApiModelProperty(value = "部门描述")
	private String deptName;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}
