package com.sy.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.sys.entity.SysRole;
import com.sy.sys.mapper.SysRoleMapper;
import com.sy.sys.service.SysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author xiangwy
 * @since 2021-09-18
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public IPage<SysRole> pageSysRole(Page page, Long orgId, String name, String code) {
        Page<SysRole> pageParam = page;
        QueryWrapper<SysRole> wrapper = Wrappers.query();
        if (null != orgId) {
            wrapper.eq(SysRole.ORG_ID, orgId);
        }
        if (StringUtils.isNotBlank(name)) {
            wrapper.like(SysRole.NAME, name);
        }
        if (StringUtils.isNotBlank(code)) {
            wrapper.like(SysRole.CODE, code);
        }
        wrapper.orderByDesc(SysRole.ROLE_SORT);
        IPage<SysRole> pageResult = super.page(pageParam, wrapper);
        return pageResult;
    }

    @Override
    public List<SysRole> listSysRole(Long orgId, String name, String code) {
        QueryWrapper<SysRole> wrapper = Wrappers.query();
        if (null != orgId) {
            wrapper.eq(SysRole.ORG_ID, orgId);
        }
        if (StringUtils.isNotBlank(name)) {
            wrapper.like(SysRole.NAME, name);
        }
        if (StringUtils.isNotBlank(code)) {
            wrapper.like(SysRole.CODE, code);
        }
        wrapper.orderByDesc(SysRole.ROLE_SORT);
        return super.list(wrapper);
    }
}
