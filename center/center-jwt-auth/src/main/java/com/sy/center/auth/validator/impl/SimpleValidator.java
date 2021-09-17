package com.sy.center.auth.validator.impl;

import com.sy.center.auth.validator.IReqValidator;
import com.sy.center.auth.vo.AuthRequestVo;
import com.sy.center.framework.constant.CustomRspCon;
import com.sy.center.framework.utils.DataformResult;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 直接验证账号密码是不是admin
 * 
 * @author xiangwy
 * @date: 2020-12-03 11:17:27
 * @Copyright: Copyright (c) 2020
 * @Company: XWY有限公司
 * @Version: V1.0
 */
@Service
public class SimpleValidator implements IReqValidator {

	private static String USER_NAME = "admin";

	private static String PASSWORD = "admin";

	@Override
	public DataformResult<Map<String, Object>> validate(AuthRequestVo authRequestVo) {
		String userName = authRequestVo.getUserName();
		String password = authRequestVo.getPassword();
		if (USER_NAME.equals(userName) && PASSWORD.equals(password)) {
			return DataformResult.success();
		} else {
			return DataformResult.failure(CustomRspCon.FLAG_LOGIN_ERROR);
		}
	}
}
