package com.sy.sys.mapper;

import org.springframework.data.repository.query.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sy.sys.entity.SysDept;
import com.sy.sys.vo.SysDeptVo;

/**
 * <p>
 * 系统部门 Mapper 接口
 * </p>
 *
 * @author xiangwy
 * @since 2021-09-18
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {
	SysDeptVo getVoById(@Param("id") Long id);
}
