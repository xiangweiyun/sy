package com.sy.center.auth.feign;

import com.sy.center.auth.feign.fallback.SysLoginLogServiceFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户登录日志数据
 * 
 * @author xiangwy
 * @date: 2020-12-03 10:38:41
 * @Copyright: Copyright (c) 2020
 * @Company: XWY有限公司
 * @Version: V1.0
 */
@FeignClient(name = "sys-boot", fallback = SysLoginLogServiceFallbackImpl.class)
public interface SysLoginLogService {

	/**
	 * 登录日志保存
	 * 
	 * @author: xiangwy
	 * @date: 2020-12-03 10:38:35
	 * @param logonId
	 * @param operType
	 * @param resultType
	 * @param msg
	 */
	@PostMapping(value = "/sysLoginLog/saveLoginLog")
	void saveLoginLog(@RequestParam(value = "logonId") String logonId,
			@RequestParam(value = "operType") String operType, @RequestParam(value = "resultType") String resultType,
			@RequestParam(value = "msg") String msg);

}
