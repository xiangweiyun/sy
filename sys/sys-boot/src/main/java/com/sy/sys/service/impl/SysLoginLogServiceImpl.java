package com.sy.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.center.common.utils.BlankUtils;
import com.sy.center.common.utils.BrowserUtils;
import com.sy.center.common.utils.DeviceUtils;
import com.sy.center.common.utils.StringUtils;
import com.sy.sys.entity.SysLoginLog;
import com.sy.sys.mapper.SysLoginLogMapper;
import com.sy.sys.service.SysLoginLogService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.SocketException;
import java.time.LocalDateTime;

/**
 * <p>
 * 登录日志 服务实现类
 * </p>
 *
 * @author xiangwy
 * @since 2021-09-22
 */
@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {

    @Override
    public void saveLoginLog(HttpServletRequest request, String loginAccount, String operType, String resultType, String msg) {
        if (BlankUtils.isNotBlank(loginAccount)) {
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
            sysLoginLog.setLoginAccount(loginAccount);
            super.save(sysLoginLog);
        }
    }
}
