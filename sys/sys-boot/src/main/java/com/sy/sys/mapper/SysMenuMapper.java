package com.sy.sys.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sy.sys.entity.SysMenu;
import com.sy.sys.vo.SysMenuVo;

/**
 * <p>
 * 系统菜单表 Mapper 接口
 * </p>
 *
 * @author zxwen
 * @since 2021-09-18
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
	List<SysMenuVo> listSysMenuByRoleId(Map<String, Object> map);
}
