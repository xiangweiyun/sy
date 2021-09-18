package com.sy.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sy.sys.entity.SysDictData;

import java.util.List;

/**
 * <p>
 * 字典数据 服务类
 * </p>
 *
 * @author xiangwy
 * @since 2021-09-18
 */
public interface SysDictDataService extends IService<SysDictData> {

    /**
     * 根据字典类型查询字典项
     *
     * @param dictType 字典类型
     * @param status   状态
     * @return
     */
    List<SysDictData> listByDictType(String dictType, String status);
}
