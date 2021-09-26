package com.sy.sys.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 修改密码
 * @author zxwen
 * @date 2021年9月26日
 *  
 */

@Getter
@Setter
@ApiModel(value="修改密码", description="修改密码")
public class UserPwdModify {
	
	@ApiModelProperty(value = "用户ID")
	private Long userId;
	
	@ApiModelProperty(value = "用户帐号")
	private String userName;
	
	@ApiModelProperty(value = "旧密码")
	private String oldPwd;
	
	@ApiModelProperty(value = "新密码")
	private String newPwd;
}
