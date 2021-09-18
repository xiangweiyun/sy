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
 * 字典类型
 * </p>
 *
 * @author zxwen
 * @since 2021-09-18
 */
@Getter
@Setter
@TableName("SYS_DICT_TYPE")
@ApiModel(value="SysDictType对象", description="字典类型")
public class SysDictType extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /** 字典名称. */
    public static final String DICT_NAME = "DICT_NAME";
    /** 字典类型. */
    public static final String DICT_TYPE = "DICT_TYPE";
    /** 备注. */
    public static final String REMARK = "REMARK";
    /** 状态（0正常 1停用. */
    public static final String STATUS = "STATUS";

    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称")
    @TableField("DICT_NAME")
    private String dictName;

    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型")
    @TableField("DICT_TYPE")
    private String dictType;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    /**
     * 状态（0正常 1停用
     */
    @ApiModelProperty(value = "状态（0正常 1停用")
    @TableField("STATUS")
    private String status;
}
