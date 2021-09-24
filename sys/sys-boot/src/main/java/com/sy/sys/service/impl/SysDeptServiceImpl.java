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
import com.sy.sys.entity.SysDept;
import com.sy.sys.mapper.SysDeptMapper;
import com.sy.sys.service.SysDeptService;
import com.sy.sys.vo.SysDeptVo;

import cn.hutool.core.bean.BeanUtil;

/**
 * <p>
 * 系统部门 服务实现类
 * </p>
 *
 * @author xiangwy
 * @since 2021-09-18
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
	
	@Autowired
	private SysDeptMapper sysDeptMapper;
	
    @Override
    public List<SysDept> listByOrgId(Long orgId) {
        QueryWrapper<SysDept> wrapper = Wrappers.query();
        wrapper.eq(SysDept.ORG_ID, orgId);
        wrapper.orderByDesc(SysDept.ORDER_NUM);
        return super.list(wrapper);
    }

    @Override
    public List<SysDeptVo> listTreeByOrgId(Long orgId) {
        List<SysDept> deptList = listByOrgId(orgId);
        List<SysDeptVo> deptVoList = new ArrayList<>();
        for (SysDept sysDept : deptList) {
            if (null == sysDept.getParentId()) {
                SysDeptVo sysDeptVo = new SysDeptVo();
                BeanUtil.copyProperties(sysDept, sysDeptVo);
                deptVoList.add(sysDeptVo);
                loadChildNode(deptList, sysDeptVo);
            }
        }
        return deptVoList;
    }

    /**
     * 递归加载子节点
     *
     * @param list       部门列表
     * @param parentDept 上级部门信息
     */
    private void loadChildNode(List<SysDept> list, SysDeptVo parentDept) {
        List<SysDeptVo> resultList = new ArrayList<>();
        for (SysDept sysDept : list) {
            if (null != sysDept.getParentId()) {
                if (sysDept.getParentId().equals(parentDept.getId())) {
                    SysDeptVo sysDeptVo = new SysDeptVo();
                    BeanUtil.copyProperties(sysDept, sysDeptVo);
                    sysDeptVo.setParentName(parentDept.getDeptName());
                    resultList.add(sysDeptVo);
                }
            }
        }
        if (resultList.isEmpty()) {
            return;
        }
        parentDept.setChildren(resultList);
        for (SysDeptVo sysDeptVo : resultList) {
            loadChildNode(list, sysDeptVo);
        }
    }

	/**
	 * 根据ID查询部门信息
	 */
	@Override
	public SysDeptVo getVoById(Long id) {
		// TODO Auto-generated method stub
		return sysDeptMapper.getVoById(id);
	}

	/* 
	 * 用于部门新增及修改时  新增时 deptId不传值
	 */
	@Override
	public List<SysDept> listParentByOrgIdAndDeptId(Long orgId, Long deptId) {
		// TODO Auto-generated method stub
		if(null == deptId) {
			QueryWrapper<SysDept> wrapper = Wrappers.query();
	        wrapper.eq(SysDept.ORG_ID, orgId).select(SysDept.ID, SysDept.CODE, SysDept.DEPT_NAME);
	        return super.list(wrapper);
		}else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", orgId);
			map.put("deptId", deptId);
			return sysDeptMapper.listParentByOrgIdAndDeptId(map);
		}
	}

}
