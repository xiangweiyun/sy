package com.sy.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sy.sys.entity.SysRoleMenu;

/**
 * <p>
 * 角色和菜单关联表 服务类
 * </p>
 *
 * @author zxwen
 * @since 2021-09-22
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {
	boolean saveBatch(List<SysRoleMenu> listSysRoleMenu);
}
