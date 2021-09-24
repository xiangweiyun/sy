package com.sy.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sy.sys.entity.SysUserOrgDept;
import com.sy.sys.vo.SysUserOrgDeptVo;

/**
 * <p>
 * 用户部门关联表 服务类
 * </p>
 *
 * @author zxwen
 * @since 2021-09-22
 */
public interface SysUserOrgDeptService extends IService<SysUserOrgDept> {
	boolean saveBatch(List<SysUserOrgDept> listSysUserOrgDept);
	List<SysUserOrgDeptVo> listByUserId(Long userId);
}
