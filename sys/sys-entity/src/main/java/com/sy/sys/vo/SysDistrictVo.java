package com.sy.sys.vo;

import com.sy.sys.entity.SysDistrict;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 区域表VO
 * @author zxwen
 * @date 2021年9月18日
 *  
 */
@ApiModel(value="SysDistrictVo对象", description="区域表VO")
public class SysDistrictVo extends SysDistrict{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "是否存在子记录")
	private boolean hasChildren;

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
}
