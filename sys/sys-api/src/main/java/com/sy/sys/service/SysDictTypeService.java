package com.sy.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sy.sys.entity.SysDictType;

/**
 * <p>
 * 字典类型 服务类
 * </p>
 *
 * @author zxwen
 * @since 2021-09-18
 */
public interface SysDictTypeService extends IService<SysDictType> {

    /**
     * 分页获取字典类型
     *
     * @param page     分页参数
     * @param dictName 字典名称
     * @param dictType 字典类型
     * @return
     */
    IPage<SysDictType> pageSysDictType(Page page, String dictName, String dictType);
}
