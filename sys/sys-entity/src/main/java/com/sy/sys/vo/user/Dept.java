package com.sy.sys.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 部门
 * @author zxwen
 * @date 2021年9月24日
 *  
 */

@Getter
@Setter
@ApiModel(value="部门", description="部门")
public class Dept {
	@ApiModelProperty(value = "机构ID")
	private Long orgId;
	
	@ApiModelProperty(value = "机构描述")
	private String orgName;
	
	@ApiModelProperty(value = "部门ID")
	private Long deptId;
	
	@ApiModelProperty(value = "部门描述")
	private String deptName;
}
