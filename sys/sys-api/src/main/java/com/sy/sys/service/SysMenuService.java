package com.sy.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sy.sys.entity.SysMenu;
import com.sy.sys.vo.SysMenuVo;

/**
 * <p>
 * 系统菜单表 服务类
 * </p>
 *
 * @author zxwen
 * @since 2021-09-18
 */
public interface SysMenuService extends IService<SysMenu> {
	List<SysMenuVo> listTreeData();
	List<SysMenuVo> listTreeDataByAppIdAndUserId(Long appId, Long userId);
	List<SysMenuVo> listTreeDataByRoleId(Long roleId);
}
