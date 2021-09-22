package com.sy.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.sys.entity.SysMenu;
import com.sy.sys.mapper.SysMenuMapper;
import com.sy.sys.service.SysMenuService;
import com.sy.sys.vo.SysMenuVo;

import cn.hutool.core.bean.BeanUtil;

/**
 * <p>
 * 系统菜单表 服务实现类
 * </p>
 *
 * @author zxwen
 * @since 2021-09-18
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
	
	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	/**
	 * 获取全部应用菜单数据 TREE结构【用于菜单管理】
	 */
	@Override
	public List<SysMenuVo> listTreeData() {
		// TODO Auto-generated method stub		
		List<SysMenuVo> treeData = new ArrayList<SysMenuVo>();
		
		QueryWrapper<SysMenu> queryWrapper = Wrappers.query();
		queryWrapper.isNull("PARENT_ID");
		treeData =  toVo(this.list(queryWrapper));
		
		if(treeData != null && treeData.size() > 0) {
			for(SysMenuVo sysMenuVo : treeData) {
				sysMenuVo.setChildren(setMenuChildren(sysMenuVo));
			}
		}
		
		return treeData;
	}
	
	/**
	 * 设置子记录数据
	 * @param sysOrgVo
	 * @return
	 */
	private List<SysMenuVo> setMenuChildren(SysMenuVo sysMenuVo) {
		QueryWrapper<SysMenu> queryWrapper = Wrappers.query();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PARENT_ID", sysMenuVo.getId());
		queryWrapper.allEq(map);
		List<SysMenu> chlidNode = this.list(queryWrapper);
		
		List<SysMenuVo> chlidNodeVo = new ArrayList<SysMenuVo>();
		
		if(chlidNode !=null && chlidNode.size()>0) {
			for(SysMenu sysMenu : chlidNode) {
				SysMenuVo sysMenuVoChild = new SysMenuVo();
				BeanUtil.copyProperties(sysMenu, sysMenuVoChild);
				sysMenuVoChild.setChildren(setMenuChildren(sysMenuVoChild));
				chlidNodeVo.add(sysMenuVoChild);
			}
		}
		
		return chlidNodeVo;
	}
	
	/**
	 * 数据类型转换
	 * @param listSysOrg
	 * @return
	 */
	private List<SysMenuVo> toVo(List<SysMenu> listSysMenu) {
		List<SysMenuVo> data = new ArrayList<SysMenuVo>();
		for (SysMenu sysMenu : listSysMenu) {
			SysMenuVo sysMenuVo = new SysMenuVo();
			BeanUtil.copyProperties(sysMenu, sysMenuVo);
			data.add(sysMenuVo);
		}
		return data;
	}

	/**
	 * 根据应用ID及用户ID， 获取当前用户在该应用中的权限
	 * @param appId 应用ID
	 * @param userId 用户ID
	 */
	@Override
	public List<SysMenuVo> listTreeDataByAppIdAndUserId(Long appId, Long userId) {
		// TODO Auto-generated method stub
		List<SysMenuVo> treeData = new ArrayList<SysMenuVo>();
		QueryWrapper<SysMenu> queryWrapper = Wrappers.query();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PARENT_ID", appId);
		queryWrapper.allEq(map);
		treeData =  toVo(this.list(queryWrapper));
		
		if(treeData != null && treeData.size() > 0) {
			for(SysMenuVo sysMenuVo : treeData) {
				sysMenuVo.setChildren(setMenuChildren(sysMenuVo));
			}
		}
		
		return treeData;
	}

	/**
	 * 根据角色ID获取所有应用树型数据及选中的节点
	 * @param roleId 角色ID
	 */
	@Override
	public List<SysMenuVo> listTreeDataByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("parentId", null);
		map.put("roleId", roleId);
		
		List<SysMenuVo> listSysMenuVo = sysMenuMapper.listSysMenuByRoleId(map);
		
		if(listSysMenuVo !=null && listSysMenuVo.size()>0) {
			for(SysMenuVo sysMenuVo : listSysMenuVo) {
				sysMenuVo.setChildren(setRootMenuChildren(sysMenuVo, roleId));
			}
		}
		
		return listSysMenuVo;
	}
	
	private List<SysMenuVo> setRootMenuChildren(SysMenuVo sysMenuVo, Long roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", sysMenuVo.getId());
		map.put("roleId", roleId);
		List<SysMenuVo> chlidNode = sysMenuMapper.listSysMenuByRoleId(map);
		
		if(chlidNode !=null && chlidNode.size()>0) {
			for(SysMenuVo sysMenuVoChild : chlidNode) {
				sysMenuVoChild.setChildren(setRootMenuChildren(sysMenuVoChild, roleId));
			}
		}
		
		return chlidNode;
	}
}
