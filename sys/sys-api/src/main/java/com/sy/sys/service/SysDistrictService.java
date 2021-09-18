package com.sy.sys.service;

import com.sy.sys.entity.SysDistrict;
import com.sy.sys.vo.SysDistrictVo;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 区域表 服务类
 * </p>
 *
 * @author zxwen
 * @since 2021-09-18
 */
public interface SysDistrictService extends IService<SysDistrict> {
	List<SysDistrictVo> listMain();
	List<SysDistrictVo> listChildren(@Param("id") Long id);
}
