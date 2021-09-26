package com.sy.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.sys.entity.SysUserRole;
import com.sy.sys.mapper.SysUserRoleMapper;
import com.sy.sys.service.SysUserRoleService;
import com.sy.sys.vo.SysUserRoleVo;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author zxwen
 * @since 2021-09-22
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
	
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	
	/**
	 * 方法重写，新增数据前先删除用户角色数据
	 * 增回回滚事务
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveBatch(List<SysUserRole> listSysUserRole) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", listSysUserRole.get(0).getUserId());
		super.removeByMap(map);
		
		return super.saveBatch(listSysUserRole);
	}

	/**
	 * 根据用户ID获取角色信息
	 */
	@Override
	public List<SysUserRoleVo> listByUserId(Long userId) {
		// TODO Auto-generated method stub
		return sysUserRoleMapper.listByUserId(userId);
	}

	/* 
	 * 复制用户角色
	 */
	@Override
	public boolean copyUserRole(Long userIdFr, Long userIdTo, Long userId) {
		// TODO Auto-generated method stub
		List<SysUserRoleVo> listUserRoleFr = sysUserRoleMapper.listByUserId(userIdFr);
		if(listUserRoleFr == null || listUserRoleFr.size() == 0) {
			return false;
		}
		List<SysUserRoleVo> listUserRoleTo = sysUserRoleMapper.listByUserId(userIdTo);
		List<SysUserRole>    listSysUserRole = new ArrayList<SysUserRole>();
		
		boolean isExistsRole = false;
		if(listUserRoleTo == null || listUserRoleTo.size() == 0) {
			isExistsRole = true;
		}
		for(SysUserRoleVo SysUserRoleVo : listUserRoleFr) {
			boolean isAddNew = true;
			if(isExistsRole) {
				for(int index=0; index<listUserRoleTo.size(); index++) {
					if(SysUserRoleVo.getRoleId().equals(listUserRoleTo.get(index).getRoleId())) {
						isAddNew = false;
						break;
					}
				}
			}
			if(isAddNew) {
				SysUserRole sysUserRole = new SysUserRole();
				sysUserRole.setUserId(userIdTo);
				sysUserRole.setRoleId(SysUserRoleVo.getRoleId());
				sysUserRole.setCreatedBy(userId);
				sysUserRole.setLastModifiedBy(userId);
				listSysUserRole.add(sysUserRole);
			}
		}
		
		if(listSysUserRole.size() > 0) {
			super.saveBatch(listSysUserRole);
		}
		
		return true;
	}
}
