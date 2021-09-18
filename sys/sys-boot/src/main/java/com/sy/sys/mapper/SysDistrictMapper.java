package com.sy.sys.mapper;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sy.sys.entity.SysDistrict;
import com.sy.sys.vo.SysDistrictVo;

/**
 * <p>
 * 区域表 Mapper 接口
 * </p>
 *
 * @author zxwen
 * @since 2021-09-18
 */
public interface SysDistrictMapper extends BaseMapper<SysDistrict> {
	List<SysDistrictVo> listMain();
	List<SysDistrictVo> listChildren(@Param("id") Long id);
}
