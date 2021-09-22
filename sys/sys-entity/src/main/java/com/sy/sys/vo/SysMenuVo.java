package com.sy.sys.vo;

import java.util.List;

import com.sy.sys.entity.SysMenu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 菜单树结构
 * @author zxwen
 * @date 2021年9月18日
 *  
 */

@ApiModel(value="sysMenuVo对象", description="菜单树结构VO")
public class SysMenuVo extends SysMenu{
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "机构子记录")
	private List<SysMenuVo> children;
	
	@ApiModelProperty(value = "是否存在子记录")
	private boolean hasChildren;
	
	@ApiModelProperty(value = "是否选中")
	private boolean checked;

	public List<SysMenuVo> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenuVo> children) {
		this.children = children;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
