package com.sy.sys.mapper;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sy.sys.entity.SysUserOrg;
import com.sy.sys.vo.SysUserOrgVo;

/**
 * <p>
 * 用户关联机构 Mapper 接口
 * </p>
 *
 * @author zxwen
 * @since 2021-09-27
 */
public interface SysUserOrgMapper extends BaseMapper<SysUserOrg> {
	List<SysUserOrgVo> listByUserId(@Param("userId") Long userId);
}
