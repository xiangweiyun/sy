package com.sy.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sy.sys.entity.SysUserOrg;
import com.sy.sys.vo.SysUserOrgVo;

/**
 * <p>
 * 用户关联机构 服务类
 * </p>
 *
 * @author zxwen
 * @since 2021-09-27
 */
public interface SysUserOrgService extends IService<SysUserOrg> {
	boolean saveBatch(List<SysUserOrg> listSysUserOrg);
	List<SysUserOrgVo> listByUserId(Long userId);
}
