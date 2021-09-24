package com.sy.sys.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 机构
 * @author zxwen
 * @date 2021年9月24日
 *  
 */

@Getter
@Setter
@ApiModel(value="机构", description="机构")
public class Org {
	@ApiModelProperty(value = "机构ID")
	private Long orgId;
	
	@ApiModelProperty(value = "机构描述")
	private String orgName;
}
