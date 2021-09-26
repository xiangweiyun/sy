package com.sy.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.sys.entity.SysUser;
import com.sy.sys.entity.SysUserOrgDept;
import com.sy.sys.mapper.SysUserMapper;
import com.sy.sys.service.SysUserOrgDeptService;
import com.sy.sys.service.SysUserService;
import com.sy.sys.vo.SysUserVo;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zxwen
 * @since 2021-09-22
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
	
	@Autowired
	private SysUserOrgDeptService sysUserOrgDeptService;
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	/* 
	 * 保存用户数据
	 * 更新部门信息
	 */
	@Override
	public boolean saveUser(SysUser sysUser) {
		// TODO Auto-generated method stub
		
		/**保存用户信息  */
		boolean isNew = false;
		if (null == sysUser.getId()) {
			isNew = true;
            super.save(sysUser);
        } else {
            super.updateById(sysUser);
        }
		
		/**当机构ID及部门ID不为空时，保存用户对应部门信息 */
		if(!(sysUser.getOrgId()==null || sysUser.getDeptId()==null)) {
			
			List<SysUserOrgDept> listNewUserOrgDept = new ArrayList<SysUserOrgDept>();
			if(isNew) {
				addListUserOrgDept(listNewUserOrgDept, sysUser);
			}else {
				QueryWrapper<SysUserOrgDept> wrapper = Wrappers.query();
				wrapper.eq(SysUserOrgDept.USER_ID, sysUser.getId());
				List<SysUserOrgDept> listSysUserOrgDept = sysUserOrgDeptService.list(wrapper);
				isNew = true;
				
				/**判断机构ID及部门ID在对应的机构部门信息是否存在 */
				if(listSysUserOrgDept != null && listSysUserOrgDept.size() > 0) {
					for(SysUserOrgDept row : listSysUserOrgDept) {
						if(row.getOrgId().equals(sysUser.getOrgId()) && row.getDeptId().equals(sysUser.getDeptId())) {
							isNew = false;
							if(!(row.getHasMain() != null && row.getHasMain())) {
								row.setHasMain(true);
								listNewUserOrgDept.add(row);
							}
						}else {
							if((row.getHasMain() != null && row.getHasMain())) {
								row.setHasMain(false);
								listNewUserOrgDept.add(row);
							}
						}
					}
					
					if(isNew) {
						addListUserOrgDept(listNewUserOrgDept, sysUser);
					}
				}else {
					addListUserOrgDept(listNewUserOrgDept, sysUser);
				}
			}
			
			if(listNewUserOrgDept.size() > 0) {
				sysUserOrgDeptService.saveOrUpdateBatch(listNewUserOrgDept);
			}
		}
		return true;
	}
	
	private void addListUserOrgDept(List<SysUserOrgDept> listNewUserOrgDept, SysUser sysUser) {
		SysUserOrgDept sysUserOrgDept = new SysUserOrgDept();
		sysUserOrgDept.setUserId(sysUser.getId());
		sysUserOrgDept.setOrgId(sysUser.getOrgId());
		sysUserOrgDept.setDeptId(sysUser.getDeptId());
		sysUserOrgDept.setHasMain(true);
		listNewUserOrgDept.add(sysUserOrgDept);
	}

	/* 
	 * 根据ID 获取用户信息
	 */
	@Override
	public SysUserVo getVoById(Long id) {
		// TODO Auto-generated method stub
		return sysUserMapper.getSysUserVoById(id);
	}

	/* 
	 * 分页查询用户信息
	 */
	@Override
	public IPage<SysUserVo> pageListVo(Page page, Long orgId, Long deptId, String name, String userName, String noNum) {
		// TODO Auto-generated method stub
		Page<SysUser> pageParam = page;
		IPage<SysUserVo> pageResult = sysUserMapper.pageListVo(pageParam, orgId, deptId, name, userName, noNum);
        return pageResult;
	}

	/* 
	 * 根据角色ID分页查询用户信息
	 */
	@Override
	public IPage<SysUserVo> pageListVoByRoleId(Page page, Long roleId) {
		// TODO Auto-generated method stub
		Page<SysUser> pageParam = page;
		IPage<SysUserVo> pageResult = sysUserMapper.pageListVoByRoleId(pageParam, roleId);
        return pageResult;
	}

	/* 
	 * 帐号重覆性检查
	 */
	@Override
	public boolean accountRepeatCheck(SysUser sysUser, Boolean organAccountAllowRepeat) {
		// TODO Auto-generated method stub
		if(null == organAccountAllowRepeat) {
			organAccountAllowRepeat = false;
		}
		
		QueryWrapper<SysUser> wrapper = Wrappers.query();
		if(organAccountAllowRepeat) {
			wrapper.eq(SysUser.ORG_ID, sysUser.getOrgId()).eq(SysUser.USERNAME, sysUser.getUsername());
		}else {
			wrapper.eq(SysUser.USERNAME, sysUser.getUsername());
		}
		List<SysUser> list = super.list(wrapper);
		if(null == sysUser.getId()) {
			if(!(list== null || list.size() == 0)) {
				return true;
			}
		}else {
			if(list != null && list.size() > 0) {
				if(list.size() == 1) {
					if(!list.get(0).getId().equals(sysUser.getId())) {
						return true;
					}
				}else {
					return true;
				}
			}
		}
		return false;
	}
	
}
