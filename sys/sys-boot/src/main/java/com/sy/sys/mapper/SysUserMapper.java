package com.sy.sys.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sy.sys.entity.SysUser;
import com.sy.sys.vo.SysUserVo;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author zxwen
 * @since 2021-09-22
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
	IPage<SysUserVo> pageListVo(Page<SysUser> page, @Param("orgId") Long orgId, @Param("deptId") Long deptId, @Param("name") String name, @Param("userName") String userName, @Param("noNum") String noNum);
	SysUserVo getSysUserVoById(Long id);
}
