package com.sy.center.gateway.fegin.fallback;

import com.sy.center.framework.utils.DataformResult;
import com.sy.center.gateway.fegin.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * jwt 认证服务的fallback
 * 
 * @author xiangwy
 * @date: 2020-12-01 17:35:26
 * @Copyright: Copyright (c) 2020
 * @Company: XWY有限公司
 * @Version: V1.0
 */
@Service
public class AuthServiceFallbackImpl implements AuthService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public DataformResult<String> checkToken(@RequestParam(value = "authToken") String authToken) {
		logger.error("调用{}异常:{}", "checkToken", authToken);
		return DataformResult.failure();
	}

}
