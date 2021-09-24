package com.sy.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sy.sys.entity.SysUser;
import com.sy.sys.vo.SysUserVo;

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
	SysUserVo getVoById(Long id);
	IPage<SysUserVo> pageListVo(Page page, Long orgId, Long deptId, String name, String userName, String noNum);
}
