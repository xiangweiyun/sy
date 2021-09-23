package com.sy.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sy.sys.entity.SysLoginLog;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 登录日志 服务类
 * </p>
 *
 * @author xiangwy
 * @since 2021-09-22
 */
public interface SysLoginLogService extends IService<SysLoginLog> {
    /**
     * 登录日志保存
     *
     * @author: xiangwy
     * @date: 2020-12-03 10:29:36
     * @param request
     * @param loginAccount    登录账号
     * @param operType   操作类型（1,登录;2,登出）
     * @param resultType 登录结果类型(1,成功；2失败)
     * @param msg        备注
     */
    void saveLoginLog(HttpServletRequest request, String loginAccount, String operType, String resultType,
                             String msg);

}
