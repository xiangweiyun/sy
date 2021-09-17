package com.sy.center.auth.validator.impl;

import com.sy.center.auth.feign.SysLoginLogService;
import com.sy.center.auth.feign.SysUserService;
import com.sy.center.auth.validator.IReqValidator;
import com.sy.center.auth.vo.AuthRequestVo;
import com.sy.center.common.constant.SysConstant;
import com.sy.center.common.redis.util.JedisUtils;
import com.sy.center.common.utils.BlankUtils;
import com.sy.center.framework.constant.CustomRspCon;
import com.sy.center.framework.utils.DataformResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 账号密码验证
 * 
 * @author xiangwy
 * @date: 2020-12-03 11:16:49
 * @Copyright: Copyright (c) 2020
 * @Company: XWY有限公司
 * @Version: V1.0
 */
@Service
public class DbValidator implements IReqValidator {

	private static Logger logger = LoggerFactory.getLogger(DbValidator.class);

	/**
	 * 系统用户信息接口
	 */
	@Qualifier("sysUserServiceFallbackImpl")
	@Autowired
	private SysUserService sysUserGssService;

	/**
	 * 系统用户登录日志接口
	 */
	@Qualifier("sysLoginLogServiceFallbackImpl")
	@Autowired
	private SysLoginLogService sysLoginLogService;

	@Override
	public DataformResult<Map<String, Object>> validate(AuthRequestVo authRequestVo) {
		String loginType = authRequestVo.getLogintype();
		// 登录类型 1 后台系统用户登录
		if (BlankUtils.isNotBlank(loginType)) {
			// 用户名
			String userName = authRequestVo.getUserName();
			// 密码
			String password = authRequestVo.getPassword();
			// 1 后台系统用户登录
			if ("1".equals(loginType)) {
				// 验证码
				String captcha = authRequestVo.getCaptcha();
				// 验证码id
				String captchaId = authRequestVo.getCaptchaId();
//				if (BlankUtils.isNotBlank(captcha) && BlankUtils.isNotBlank(captchaId)) {
					String key = SysConstant.CAPTCHA_KEY + captchaId;
					String captchaCache = (String) JedisUtils.getObject(key);
//					if (captcha.equalsIgnoreCase(captchaCache)) {
						JedisUtils.deleteKeys(key);
						DataformResult<Map<String, Object>> login = sysUserGssService.getByLoginInfo(userName,
								password);
						Map<String, Object> loginDatas = (Map<String, Object>) login.getObject();
						// 查看登录用户和密码是否正确
						// 用户名和密码正确,登录成功
						if (BlankUtils.isNotBlank(loginDatas)) {
							Map<String, Object> userInfo = (Map<String, Object>) loginDatas.get("userInfo");
							if (BlankUtils.isNotBlank(userInfo)) {
								// 密码注释掉
								if (BlankUtils.isNotBlank(userInfo.get("password"))) {
									userInfo.put("password", "***");
								}
							}
							loginDatas.put("userInfo", userInfo);
							sysLoginLogService.saveLoginLog(userName, "1", "1", "登录成功！！");
							logger.info(userName + "登录成功！");
							return DataformResult.success(loginDatas);
						} else {// 登录失败
							sysLoginLogService.saveLoginLog(userName, "1", "2", "登录失败，用户名或密码错误！");
							logger.info(CustomRspCon.FLAG_LOGIN_ERROR.getMsg());
							return DataformResult.failure(CustomRspCon.FLAG_LOGIN_ERROR);
						}
//					} else {
//						sysLoginLogService.saveLoginLog(userName, "1", "2", "验证码错误！");
//						return DataformResult.failure(XwyRspCon.FLAG_LOGIN_ERROR);
//					}
//				} else {
//					sysLoginLogService.saveLoginLog(userName, "1", "2", "验证码不能为空！");
//					return DataformResult.failure(XwyRspCon.FLAG_LOGIN_ERROR);
//				}
			} else {// 登录类型错误
				logger.info(CustomRspCon.FLAG_LOGIN_TYPE_ERROR.getMsg());
				return DataformResult.failure(CustomRspCon.FLAG_LOGIN_TYPE_ERROR);
			}
		} else {// 登录类型不能为空
			logger.info(CustomRspCon.FLAG_LOGIN_TYPE_NOTNULL.getMsg());
			return DataformResult.failure(CustomRspCon.FLAG_LOGIN_TYPE_NOTNULL);
		}
	}
}
