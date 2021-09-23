package com.sy.sys.service.impl;

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
}
