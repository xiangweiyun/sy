package com.sy.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.sys.entity.SysUser;
import com.sy.sys.entity.SysUserOrgDept;
import com.sy.sys.mapper.SysUserOrgDeptMapper;
import com.sy.sys.service.SysUserOrgDeptService;
import com.sy.sys.service.SysUserService;
import com.sy.sys.vo.SysUserRoleVo;

/**
 * <p>
 * 用户部门关联表 服务实现类
 * </p>
 *
 * @author zxwen
 * @since 2021-09-22
 */
@Service
public class SysUserOrgDeptServiceImpl extends ServiceImpl<SysUserOrgDeptMapper, SysUserOrgDept> implements SysUserOrgDeptService {
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysUserOrgDeptMapper sysUserOrgDeptMapper;
	
	
	/**
	 * 方法重写，新增数据前先删除用户关联部门数据
	 * 增回回滚事务
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveBatch(List<SysUserOrgDept> listSysUserOrgDept) {
		// TODO Auto-generated method stub
		//删除部门信息
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", listSysUserOrgDept.get(0).getUserId());
		super.removeByMap(map);
		
		/** 如果存在主部门，更新到员工表 */
		for(SysUserOrgDept sysUserOrgDept:listSysUserOrgDept) {
    		if(sysUserOrgDept.getHasMain() != null && sysUserOrgDept.getHasMain()) {
    			SysUser sysUser = sysUserService.getById(sysUserOrgDept.getUserId());
    			if(sysUser!=null) {
    				if(!(sysUser.getOrgId() == sysUserOrgDept.getOrgId() && 
    				   sysUser.getDeptId() == sysUserOrgDept.getDeptId())) {
    					SysUser sysUserUpdate = new SysUser();
    					sysUserUpdate.setId(sysUserOrgDept.getUserId());
    					sysUserUpdate.setOrgId(sysUserOrgDept.getOrgId());
    					sysUserUpdate.setDeptId(sysUserOrgDept.getDeptId());
    					sysUserService.updateById(sysUserUpdate);
    				}
    			}
    		}
    	}
		
		/** 保存员工部门信息 */
		return super.saveBatch(listSysUserOrgDept);
	}
	
	/* 
	 * 用户部门信息列表
	 */
	@Override
	public List<SysUserRoleVo> listByUserId(Long userId) {
		// TODO Auto-generated method stub
		return  sysUserOrgDeptMapper.listByUserId(userId);
	}

}
