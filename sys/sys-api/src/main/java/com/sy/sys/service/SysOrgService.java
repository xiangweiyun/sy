package com.sy.sys.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sy.sys.entity.SysOrg;
import com.sy.sys.vo.SysOrgVo;

/**
 * <p>
 * 系统机构表 服务类
 * </p>
 *
 * @author zxwen
 * @since 2021-09-17
 */
public interface SysOrgService extends IService<SysOrg> {
	List<SysOrgVo> getTreeData(Long orgId);
	List<SysOrgVo> listChildById(Long orgId);
}
