package com.sy.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.sys.entity.SysDictType;
import com.sy.sys.mapper.SysDictTypeMapper;
import com.sy.sys.service.SysDictTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典类型 服务实现类
 * </p>
 *
 * @author xiangwy
 * @since 2021-09-18
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    @Override
    public IPage<SysDictType> pageSysDictType(Page page, String dictName, String dictType) {
        Page<SysDictType> pageParam = page;
        QueryWrapper<SysDictType> wrapper = Wrappers.query();
        if (StringUtils.isNotBlank(dictName)){
            wrapper.like(SysDictType.DICT_NAME, dictName);
        }
        if (StringUtils.isNotBlank(dictType)){
            wrapper.like(SysDictType.DICT_TYPE, dictType);
        }
        wrapper.orderByDesc(SysDictType.CREATED_DATE);
        IPage<SysDictType> pageResult = super.page(pageParam, wrapper);
        return pageResult;
    }
}
