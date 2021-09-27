package com.sy.sys.vo;

import com.sy.sys.entity.SysUserOrg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户关联机构VO
 * @author zxwen
 * @date 2021年9月27日
 *  
 */
@Getter
@Setter
@ApiModel(value="SysUserOrgVo对象", description="用户关联机构VO")
public class SysUserOrgVo extends SysUserOrg{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "机构描述")
    private String orgName;
}
