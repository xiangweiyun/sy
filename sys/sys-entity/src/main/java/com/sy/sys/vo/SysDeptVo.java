package com.sy.sys.vo;

import com.sy.sys.entity.SysDept;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 * 系统部门扩展
 * </p>
 *
 * @author xiangwy
 * @since 2021-09-18
 */
@Getter
@Setter
@ApiModel(value="SysDeptVo对象", description="系统部门扩展")
public class SysDeptVo extends SysDept {

    @ApiModelProperty(value = "子集部门")
    private List<SysDeptVo> children;
}
