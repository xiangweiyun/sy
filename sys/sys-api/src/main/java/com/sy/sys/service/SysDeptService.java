package com.sy.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sy.sys.entity.SysDept;
import com.sy.sys.vo.SysDeptVo;

import java.util.List;

/**
 * <p>
 * 系统部门 服务类
 * </p>
 *
 * @author xiangwy
 * @since 2021-09-18
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 根据机构获取部门列表
     *
     * @param orgId 机构ID
     * @return
     */
    List<SysDept> listByOrgId(Long orgId);

    /**
     * 部门列表(树型)
     *
     * @param orgId 机构ID
     * @return
     */
    List<SysDeptVo> listTreeByOrgId(Long orgId);
}
