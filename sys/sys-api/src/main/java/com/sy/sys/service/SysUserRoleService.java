package com.sy.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sy.sys.entity.SysUserRole;
import com.sy.sys.vo.SysUserRoleVo;

/**
 * <p>
 * 用户和角色关联表 服务类
 * </p>
 *
 * @author zxwen
 * @since 2021-09-22
 */
public interface SysUserRoleService extends IService<SysUserRole> {
	boolean saveBatch(List<SysUserRole> listSysUserRole);
	List<SysUserRoleVo> listByUserId(Long userId);
	boolean copyUserRole(Long userIdFr , Long userIdTo, Long userId);
}
