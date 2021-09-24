package com.sy.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.sys.entity.SysRoleMenu;
import com.sy.sys.mapper.SysRoleMenuMapper;
import com.sy.sys.service.SysRoleMenuService;

/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>
 *
 * @author zxwen
 * @since 2021-09-22
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

	/**
	 * 方法重写，新增数据前先删除角色的菜单数据
	 * 增回回滚事务
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveBatch(List<SysRoleMenu> listSysRoleMenu) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("role_id", listSysRoleMenu.get(0).getRoleId());
		super.removeByMap(map);
		
		return super.saveBatch(listSysRoleMenu);
	}

	/* 
	 * 根据角色ID获取菜单
	 */
	@Override
	public List<SysRoleMenu> listByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		QueryWrapper<SysRoleMenu> wrapper = Wrappers.query();
		wrapper.eq(SysRoleMenu.ROLE_ID, roleId);
		return super.list(wrapper);
	}
}
