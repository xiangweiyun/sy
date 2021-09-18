package com.sy.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sy.center.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 区域表
 * </p>
 *
 * @author zxwen
 * @since 2021-09-18
 */
@Getter
@Setter
@TableName("SYS_DISTRICT")
@ApiModel(value="SysDistrict对象", description="区域表")
public class SysDistrict extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /** 区域编码. */
    public static final String DISTRICT_CODE = "DISTRICT_CODE";
    /** 区域描述. */
    public static final String DISTRICT_NAME = "DISTRICT_NAME";
    /** 上级区域. */
    public static final String PARENT_ID = "PARENT_ID";

    /**
     * 区域编码
     */
    @ApiModelProperty(value = "区域编码")
    @TableField("DISTRICT_CODE")
    private String districtCode;

    /**
     * 区域描述
     */
    @ApiModelProperty(value = "区域描述")
    @TableField("DISTRICT_NAME")
    private String districtName;

    /**
     * 上级区域
     */
    @ApiModelProperty(value = "上级区域")
    @TableField("PARENT_ID")
    private Long parentId;
}
