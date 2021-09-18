package com.sy.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sy.sys.entity.SysRole;

import java.util.List;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author xiangwy
 * @since 2021-09-18
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 分页获取角色列表
     *
     * @param page  分页参数
     * @param orgId 机构ID
     * @param name  角色名称
     * @param code  角色编码
     * @return
     */
    IPage<SysRole> pageSysRole(Page page, Long orgId, String name, String code);

    /**
     * 获取角色列表
     *
     * @param orgId 机构ID
     * @param name  角色名称
     * @param code  角色编码
     * @return
     */
    List<SysRole> listSysRole(Long orgId, String name, String code);
}
