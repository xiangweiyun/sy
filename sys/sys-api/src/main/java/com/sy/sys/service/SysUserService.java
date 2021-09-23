package com.sy.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sy.sys.entity.SysUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zxwen
 * @since 2021-09-22
 */
public interface SysUserService extends IService<SysUser> {
	boolean saveUser(SysUser sysUser);
}
