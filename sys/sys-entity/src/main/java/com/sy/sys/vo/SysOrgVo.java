package com.sy.sys.vo;

import java.util.List;

import com.sy.sys.entity.SysOrg;

/**
 * SysOrg 机构树
 * @author zxwen
 * @date 2021年9月18日
 *  
 */
public class SysOrgVo extends SysOrg{
	private static final long serialVersionUID = 1L;

	/** 子机构记录. */
	private List<SysOrgVo> children;
	
	/** 是否存在子机构记录. */
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
