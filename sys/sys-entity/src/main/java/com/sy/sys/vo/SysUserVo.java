package com.sy.sys.vo;

import java.util.List;

import com.sy.sys.entity.SysUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户表VO
 * @author zxwen
 * @date 2021年9月24日
 *  
 */
@Getter
@Setter
@ApiModel(value="SysUser对象", description="系统用户扩展")
public class SysUserVo extends SysUser{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "机构描述")
	private String orgName;
	
	@ApiModelProperty(value = "部门描述")
	private String deptName;
	
	@ApiModelProperty(value = "职称描述")
	private String jobName;
	
	@ApiModelProperty(value = "职务描述")
	private String postName;
	
	@ApiModelProperty(value = "性别")
	private String genderName;
}
