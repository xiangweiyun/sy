package com.sy.sys.mapper;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sy.sys.entity.SysOrg;
import com.sy.sys.vo.SysOrgVo;

/**
 * <p>
 * 系统机构表 Mapper 接口
 * </p>
 *
 * @author zxwen
 * @since 2021-09-17
 */
public interface SysOrgMapper extends BaseMapper<SysOrg> {
	List<SysOrgVo> listChildById(@Param("orgId") Long orgId);
}
