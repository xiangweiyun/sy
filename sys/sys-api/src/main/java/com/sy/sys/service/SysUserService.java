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
	/**
	 * 保存用户信息
	 * @param sysUser
	 * @return
	 */
	boolean saveUser(SysUser sysUser);
	
	/**
	 * 获取用户信息VO
	 * @param id
	 * @return
	 */
	SysUserVo getVoById(Long id);
	
	/**
	 * 用户列表分页查询
	 * @param page
	 * @param orgId
	 * @param deptId
	 * @param name
	 * @param userName
	 * @param noNum
	 * @return
	 */
	IPage<SysUserVo> pageListVo(Page page, Long orgId, Long deptId, String name, String userName, String noNum);
	
	/**
	 * 查询角色分配给了那个用户
	 * @param page
	 * @param roleId
	 * @return
	 */
	IPage<SysUserVo> pageListVoByRoleId(Page page, Long roleId);
	
	/**
	 * 帐号重覆性检查
	 * @param sysUser
	 * @param organAccountAllowRepeat
	 * @return
	 */
	boolean accountRepeatCheck(SysUser sysUser, Boolean organAccountAllowRepeat);
}
