package com.sy.sys.mapper;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sy.sys.entity.SysUserRole;
import com.sy.sys.vo.SysUserRoleVo;

/**
 * <p>
 * 用户和角色关联表 Mapper 接口
 * </p>
 *
 * @author zxwen
 * @since 2021-09-22
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
	List<SysUserRoleVo> listByUserId(@Param("userId") Long userId);
}
