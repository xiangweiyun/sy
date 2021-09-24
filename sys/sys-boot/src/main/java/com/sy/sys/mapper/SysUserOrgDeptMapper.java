package com.sy.sys.mapper;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sy.sys.entity.SysUserOrgDept;
import com.sy.sys.vo.SysUserOrgDeptVo;

/**
 * <p>
 * 用户部门关联表 Mapper 接口
 * </p>
 *
 * @author zxwen
 * @since 2021-09-22
 */
public interface SysUserOrgDeptMapper extends BaseMapper<SysUserOrgDept> {
	List<SysUserOrgDeptVo> listByUserId(@Param("userId") Long userId);
}
