package com.sy.center.auth.feign;

import com.sy.center.auth.feign.fallback.SysUserServiceFallbackImpl;
import com.sy.center.framework.utils.DataformResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 系统用户信息
 * 
 * @author xiangwy
 * @date: 2020-12-03 10:41:49
 * @Copyright: Copyright (c) 2020
 * @Company: XWY有限公司
 * @Version: V1.0
 */
@FeignClient(name = "sys-boot", fallback = SysUserServiceFallbackImpl.class)
public interface SysUserService {
	/**
	 * 系统用户登录
	 * 
	 * @param username 登录账号或者手机号或者邮箱
	 * @param password 登录密码
	 * @return 系统用户信息
	 */
	@GetMapping(value = "/sysUser/getByLoginInfo")
	DataformResult<Map<String, Object>> getByLoginInfo(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password);

}
