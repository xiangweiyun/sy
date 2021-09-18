package com.sy.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.spring.util.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.sys.entity.SysOrg;
import com.sy.sys.mapper.SysOrgMapper;
import com.sy.sys.service.SysOrgService;
import com.sy.sys.vo.SysOrgVo;

import cn.hutool.core.bean.BeanUtil;

/**
 * <p>
 * 系统机构表 服务实现类
 * </p>
 *
 * @author zxwen
 * @since 2021-09-17
 */
@Service
public class SysOrgServiceImpl extends ServiceImpl<SysOrgMapper, SysOrg> implements SysOrgService {

	@Override
	public List<SysOrgVo> getTreeData(Long orgId) {
		// TODO Auto-generated method stub		
		List<SysOrgVo> treeData = new ArrayList<SysOrgVo>();
		if(orgId == null) {
			QueryWrapper<SysOrg> queryWrapper = Wrappers.query();
			queryWrapper.isNull("PARENT_ID");
			treeData =  toVo(this.list(queryWrapper));
        }else {
        	SysOrg sysOrg = this.getById(orgId);
        	if(sysOrg != null) {
        		SysOrgVo sysOrgVo = new SysOrgVo();
    			BeanUtil.copyProperties(this.getById(orgId), sysOrgVo);
            	treeData.add(sysOrgVo);  
        	}
        }
		
		if(treeData != null && treeData.size() > 0) {
			for(SysOrgVo sysOrgVo : treeData) {
				sysOrgVo.setChildren(setOrgChildren(sysOrgVo));
			}
		}
		
		return treeData;
	}
	
	/**
	 * 设置子记录数据
	 * @param sysOrgVo
	 * @return
	 */
	private List<SysOrgVo> setOrgChildren(SysOrgVo sysOrgVo) {
		QueryWrapper<SysOrg> queryWrapper = Wrappers.query();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PARENT_ID", sysOrgVo.getId());
		queryWrapper.allEq(map);
		List<SysOrg> chlidNode = this.list(queryWrapper);
		
		List<SysOrgVo> chlidNodeVo = new ArrayList<SysOrgVo>();
		
		if(chlidNode !=null && chlidNode.size()>0) {
			for(SysOrg sysOrg : chlidNode) {
				SysOrgVo sysOrgVoChild = new SysOrgVo();
				BeanUtil.copyProperties(sysOrg, sysOrgVoChild);
				sysOrgVoChild.setChildren(setOrgChildren(sysOrgVoChild));
				chlidNodeVo.add(sysOrgVoChild);
			}
		}
		
		return chlidNodeVo;
	}
	
	/**
	 * 数据类型转换
	 * @param listSysOrg
	 * @return
	 */
	private List<SysOrgVo> toVo(List<SysOrg> listSysOrg) {
		List<SysOrgVo> data = new ArrayList<SysOrgVo>();
		for (SysOrg sysOrg : listSysOrg) {
			SysOrgVo sysOrgVo = new SysOrgVo();
			BeanUtil.copyProperties(sysOrg, sysOrgVo);
			data.add(sysOrgVo);
		}
		return data;
	}
}
