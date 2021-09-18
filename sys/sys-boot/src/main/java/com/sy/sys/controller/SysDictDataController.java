package com.sy.sys.controller;


import com.sy.center.common.enums.YesNoEnum;
import com.sy.center.framework.utils.DataformResult;
import com.sy.sys.entity.SysDictData;
import com.sy.sys.service.SysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典数据控制器.
 *
 * @author xiangwy
 * @date: 2021-09-18
 * @Copyright: Copyright (c) 2021
 * @Company:
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sysDictData")
@Api(tags = "字典数据")
public class SysDictDataController {
    private final Logger logger = LoggerFactory.getLogger(SysDictDataController.class);
    @Autowired
    private SysDictDataService sysDictDataService;

    @ApiOperation(value = "字典数据-新增", notes = "字典数据-新增")
    @PostMapping("/save")
    public DataformResult<String> save(@RequestBody SysDictData sysDictData) {
        if (null == sysDictData.getId()) {
            if (StringUtils.isBlank(sysDictData.getStatus())){
                sysDictData.setStatus(YesNoEnum.NO.getCode());
            }
            sysDictDataService.save(sysDictData);
        } else {
            sysDictDataService.updateById(sysDictData);
        }
        return DataformResult.success();
    }

    @ApiOperation(value = "字典数据-删除", notes = "字典数据-刪除")
    @PostMapping("/remove/{id}")
    public DataformResult<String> removeById(@PathVariable("id") Long id) {
        sysDictDataService.removeById(id);
        return DataformResult.success();
    }

    @ApiOperation(value = "字典数据-根据ID获取", notes = "字典数据-根据ID获取")
    @GetMapping("/{id}")
    public DataformResult<SysDictData> getById(@PathVariable("id") Long id) {
        SysDictData sysDictData = sysDictDataService.getById(id);
        return DataformResult.success(sysDictData);
    }

    @ApiOperation(value = "字典数据-根据字典类型获取字典项", notes = "字典数据-根据字典类型获取字典项")
    @ApiImplicitParams({
            @ApiImplicitParam(required = false, name = "dictType", value = "字典类型", dataType = "String"),
            @ApiImplicitParam(required = false, name = "status", value = "状态(0正常 1停用)", dataType = "String")})
    @GetMapping("/listByDictType/{dictType}")
    public DataformResult<List<SysDictData>> listByDictType(@PathVariable("dictType") String dictType, String status) {
        List<SysDictData> listData = sysDictDataService.listByDictType(dictType, status);
        return DataformResult.success(listData);
    }
}
