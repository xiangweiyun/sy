package com.sy.sys.vo;

import java.util.List;

import com.sy.sys.entity.SysOrg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * SysOrg 机构树
 * @author zxwen
 * @date 2021年9月18日
 *  
 */
@ApiModel(value="SysOrgVo对象", description="机构树结构VO")
public class SysOrgVo extends SysOrg{
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "机构子记录")
	private List<SysOrgVo> children;
	
	@ApiModelProperty(value = "是否存在子记录")
	private boolean hasChildren;

	public List<SysOrgVo> getChildren() {
		return children;
	}

	public void setChildren(List<SysOrgVo> children) {
		this.children = children;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
}
