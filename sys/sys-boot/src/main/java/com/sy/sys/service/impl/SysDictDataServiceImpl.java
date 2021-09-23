package com.sy.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.sys.entity.SysDictData;
import com.sy.sys.mapper.SysDictDataMapper;
import com.sy.sys.service.SysDictDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 字典数据 服务实现类
 * </p>
 *
 * @author xiangwy
 * @since 2021-09-18
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    @Override
    public List<SysDictData> listByDictType(String dictType, String status) {
        QueryWrapper<SysDictData> wrapper = Wrappers.query();
        wrapper.eq(SysDictData.DICT_TYPE, dictType);
        if (StringUtils.isNotBlank(status)) {
            wrapper.eq(SysDictData.STATUS, status);
        }
        wrapper.orderByAsc(SysDictData.DICT_SORT);
        return super.list(wrapper);
    }

    @Override
    public void removeByDictType(String dictType) {
        QueryWrapper<SysDictData> wrapper = Wrappers.query();
        wrapper.eq(SysDictData.DICT_TYPE, dictType);
        super.remove(wrapper);
    }
}
