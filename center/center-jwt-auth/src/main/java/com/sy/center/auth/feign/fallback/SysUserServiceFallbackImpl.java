package com.sy.center.auth.feign.fallback;

import com.sy.center.auth.feign.SysUserService;
import com.sy.center.framework.utils.DataformResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 系统用户信息服务的fallback
 * 
 * @author xiangwy
 * @date: 2020-12-03 10:38:14
 * @Copyright: Copyright (c) 2020
 * @Company: XWY有限公司
 * @Version: V1.0
 */
@Service
public class SysUserServiceFallbackImpl implements SysUserService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public DataformResult<Map<String, Object>> getByLoginInfo(@RequestParam(value = "userName") String userName,
			@RequestParam(value = "passWord") String passWord) {
		logger.error("系统用户jwt登录调用{}异常:{}", "getByLoginInfo", userName);
		return DataformResult.failure("系统用户jwt登录调用getByLoginInfo异常-" + userName);
	}

}
