package com.sy.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sy.center.common.enums.YesNoEnum;
import com.sy.center.framework.utils.DataformResult;
import com.sy.sys.entity.SysDictType;
import com.sy.sys.service.SysDictDataService;
import com.sy.sys.service.SysDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 字典类型控制器.
 *
 * @author xiangwy
 * @date: 2021-09-18
 * @Copyright: Copyright (c) 2021
 * @Company: 云南升玥信息技术有限公司
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sysDictType")
@Api(tags = "字典类型")
public class SysDictTypeController {
    private final Logger logger = LoggerFactory.getLogger(SysDictTypeController.class);
    @Autowired
    private SysDictTypeService sysDictTypeService;

    @Autowired
    private SysDictDataService sysDictDataService;

    @ApiOperation(value = "字典类型-保存", notes = "字典类型-保存")
    @PostMapping("/save")
    public DataformResult<String> save(@RequestBody SysDictType sysDictType) {
        SysDictType result = sysDictTypeService.getByDictType(sysDictType.getDictType());
        if (null == sysDictType.getId()) {
            if (result != null) {
                return DataformResult.failure("新增失败,字典类型已存在");
            }
            if (StringUtils.isBlank(sysDictType.getStatus())){
                sysDictType.setStatus(YesNoEnum.NO.getCode());
            }
            sysDictTypeService.save(sysDictType);
        } else {
            if (result != null) {
                if (!result.getId().equals(sysDictType.getId())) {
                    return DataformResult.failure("修改失败,字典类型已存在");
                }
            }
            sysDictTypeService.updateById(sysDictType);
        }
        return DataformResult.success();
    }

    @ApiOperation(value = "字典类型-删除", notes = "字典类型-刪除")
    @PostMapping("/remove/{id}")
    public DataformResult<String> removeById(@PathVariable("id") Long id) {
        SysDictType sysDictType = sysDictTypeService.getById(id);
        // 删除字典项
        sysDictDataService.removeByDictType(sysDictType.getDictType());
        sysDictTypeService.removeById(id);
        return DataformResult.success();
    }

    @ApiOperation(value = "字典类型-根据ID获取", notes = "字典类型-根据ID获取")
    @GetMapping("/{id}")
    public DataformResult<SysDictType> getById(@PathVariable("id") Long id) {
        SysDictType sysDictType = sysDictTypeService.getById(id);
        return DataformResult.success(sysDictType);
    }

    @ApiOperation(value = "字典类型-分页获取字典类型", notes = "字典类型-分页获取字典类型")
    @ApiImplicitParams({
            @ApiImplicitParam(required = false, name = "dictName", value = "字典名称", dataType = "String"),
            @ApiImplicitParam(required = false, name = "dictType", value = "字典类型", dataType = "String")})
    @GetMapping("/pageSysDictType")
    public DataformResult<IPage<SysDictType>> pageSysDictType(Page page, String dictName, String dictType) {
        IPage<SysDictType> pageData = sysDictTypeService.pageSysDictType(page, dictName, dictType);
        return DataformResult.success(pageData);
    }

}
