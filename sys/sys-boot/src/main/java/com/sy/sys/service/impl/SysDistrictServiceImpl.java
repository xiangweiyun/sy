package com.sy.sys.service.impl;

import com.sy.sys.entity.SysDistrict;
import com.sy.sys.mapper.SysDistrictMapper;
import com.sy.sys.service.SysDistrictService;
import com.sy.sys.vo.SysDistrictVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 区域表 服务实现类
 * </p>
 *
 * @author zxwen
 * @since 2021-09-18
 */
@Service
public class SysDistrictServiceImpl extends ServiceImpl<SysDistrictMapper, SysDistrict> implements SysDistrictService {
	
	@Autowired
	private SysDistrictMapper sysDistrictMapper;
	@Override
	public List<SysDistrictVo> listMain() {
		// TODO Auto-generated method stub
		return sysDistrictMapper.listMain();
	}
	
	@Override
	public List<SysDistrictVo> listChildren(Long id) {
		// TODO Auto-generated method stub
		return sysDistrictMapper.listChildren(id);
	}
	
}
