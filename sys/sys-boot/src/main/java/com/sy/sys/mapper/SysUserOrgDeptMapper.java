package com.sy.sys.mapper;

import com.sy.sys.entity.SysUserOrgDept;
import com.sy.sys.vo.SysUserRoleVo;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户部门关联表 Mapper 接口
 * </p>
 *
 * @author zxwen
 * @since 2021-09-22
 */
public interface SysUserOrgDeptMapper extends BaseMapper<SysUserOrgDept> {
	List<SysUserRoleVo> listByUserId(@Param("userId") Long userId);
}
