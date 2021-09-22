package com.sy.center.gateway.fegin;

import com.sy.center.gateway.fegin.fallback.AuthServiceFallbackImpl;
import com.sy.center.gateway.util.DataformResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * jwt 认证
 * 
 * @author xiangwy
 * @date: 2020-12-01 17:34:49
 * @Copyright: Copyright (c) 2020
 * @Company: XWY有限公司
 * @Version: V1.0
 */
@FeignClient(name = "${sy.appliation.name.jwt:jwt-auth}", fallback = AuthServiceFallbackImpl.class)
public interface AuthService {
	/**
	 * token 校验
	 * 
	 * @author: xiangwy
	 * @date: 2020-12-01 17:38:43
	 * @param authToken
	 * @return
	 */
	@PostMapping(value = "/auth/checkToken")
	DataformResult<String> checkToken(@RequestParam(value = "authToken") String authToken);

}
