package com.sy.sys.service.impl;

import com.sy.sys.entity.SysUserOrg;
import com.sy.sys.mapper.SysUserOrgMapper;
import com.sy.sys.service.SysUserOrgService;
import com.sy.sys.vo.SysUserOrgVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户关联机构 服务实现类
 * </p>
 *
 * @author zxwen
 * @since 2021-09-27
 */
@Service
public class SysUserOrgServiceImpl extends ServiceImpl<SysUserOrgMapper, SysUserOrg> implements SysUserOrgService {
	
	@Autowired
	private SysUserOrgMapper sysUserOrgMapper;
	/* 
	 * 根据用户ID查询用户所属机构
	 */
	@Override
	public List<SysUserOrgVo> listByUserId(Long userId) {
		// TODO Auto-generated method stub
		return sysUserOrgMapper.listByUserId(userId);
	}
	
	/* 
	 * 用户机构保存
	 */
	@Override
	public boolean saveBatch(List<SysUserOrg> listSysUserOrg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", listSysUserOrg.get(0).getUserId());
		super.removeByMap(map);
		
		return super.saveBatch(listSysUserOrg);
	}

}
