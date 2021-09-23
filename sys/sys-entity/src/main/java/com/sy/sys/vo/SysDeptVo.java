package com.sy.sys.vo;

import java.util.List;

import com.sy.sys.entity.SysDept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 系统部门扩展
 * </p>
 *
 * @author xiangwy
 * @since 2021-09-18
 */
@Getter
@Setter
@ApiModel(value="SysDeptVo对象", description="系统部门扩展")
public class SysDeptVo extends SysDept {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "子集部门")
    private List<SysDeptVo> children;
    
	@ApiModelProperty(value = "机构描述")
    private String orgName;
	
	@ApiModelProperty(value = "上级部门")
    private String parentName;
}
