package com.sy.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.ecis.service.SysLoginLogService;
import com.sy.sys.mapper.SysLoginLogMapper;
import com.sy.center.common.utils.BlankUtils;
import com.sy.center.common.utils.BrowserUtils;
import com.sy.center.common.utils.DeviceUtils;
import com.sy.center.common.utils.StringUtils;
import com.sy.sys.entity.SysLoginLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.net.SocketException;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户登录日志 服务实现类
 * </p>
 *
 * @author xiangwy
 * @since 2020-12-03
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {

	@Override
	public void saveLoginLog(HttpServletRequest request, String logonId, String operType, String resultType,
			String msg) {
		if (BlankUtils.isNotBlank(logonId)) {
			SysLoginLog sysLoginLog = new SysLoginLog();
			sysLoginLog.setOperType(operType);
			sysLoginLog.setResultType(resultType);
			sysLoginLog.setRemarks(msg);
			// 获取浏览器类型
			sysLoginLog.setBrowserType(BrowserUtils.checkBrowse(request));
			sysLoginLog.setOperTime(LocalDateTime.now());
			// 获取设备类型以及设备
			if (DeviceUtils.isMobileDevice(request)) {
				sysLoginLog.setLoginType("手机登录");
				sysLoginLog.setEquipment("手机");
			} else {
				sysLoginLog.setLoginType("电脑登录");
				sysLoginLog.setEquipment("电脑");
			}
			// 获取ip地址
			String ip = StringUtils.getRemoteAddr(request);
			if ("0:0:0:0:0:0:0:1".equals(ip)) {
				try {
					ip = StringUtils.getRealIp();
				} catch (SocketException e) {
					e.printStackTrace();
				}
			}
			sysLoginLog.setRemoteAddr(ip);
			// 获取用户代理
			sysLoginLog.setUserAgent(BrowserUtils.checkBrowse(request));
			sysLoginLog.setLogonId(logonId);
			super.save(sysLoginLog);
		}
	}

}
