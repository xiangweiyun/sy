package com.sy.sys.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 复制用户角色
 * @author zxwen
 * @date 2021年9月26日
 *  
 */

@Getter
@Setter
@ApiModel(value="复制用户角色", description="复制用户角色")
public class UserRoleCopy {
	
	@ApiModelProperty(value = "用户ID(从)")
	private Long userIdFr;
	
	@ApiModelProperty(value = "用户ID(到)")
	private Long userIdTo;
}
