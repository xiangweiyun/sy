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
 * 字典数据
 * </p>
 *
 * @author xiangwy
 * @since 2021-09-18
 */
@Getter
@Setter
@TableName("SYS_DICT_DATA")
@ApiModel(value="SysDictData对象", description="字典数据")
public class SysDictData extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /** 主键. */
    public static final String DICT_CODE = "DICT_CODE";
    /** 字典排序. */
    public static final String DICT_SORT = "DICT_SORT";
    /** 字典标签. */
    public static final String DICT_LABEL = "DICT_LABEL";
    /** 字典键值. */
    public static final String DICT_VALUE = "DICT_VALUE";
    /** 字典类型. */
    public static final String DICT_TYPE = "DICT_TYPE";
    /** 样式属性（其他样式扩展）. */
    public static final String CSS_CLASS = "CSS_CLASS";
    /** 表格字典样式. */
    public static final String LIST_CLASS = "LIST_CLASS";
    /** 是否默认. */
    public static final String IS_DEFAULT = "IS_DEFAULT";
    /** 备注. */
    public static final String REMARK = "REMARK";
    /** 状态. */
    public static final String STATUS = "STATUS";

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableField("DICT_CODE")
    private Long dictCode;

    /**
     * 字典排序
     */
    @ApiModelProperty(value = "字典排序")
    @TableField("DICT_SORT")
    private Long dictSort;

    /**
     * 字典标签
     */
    @ApiModelProperty(value = "字典标签")
    @TableField("DICT_LABEL")
    private String dictLabel;

    /**
     * 字典键值
     */
    @ApiModelProperty(value = "字典键值")
    @TableField("DICT_VALUE")
    private String dictValue;

    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型")
    @TableField("DICT_TYPE")
    private String dictType;

    /**
     * 样式属性（其他样式扩展）
     */
    @ApiModelProperty(value = "样式属性（其他样式扩展）")
    @TableField("CSS_CLASS")
    private String cssClass;

    /**
     * 表格字典样式
     */
    @ApiModelProperty(value = "表格字典样式")
    @TableField("LIST_CLASS")
    private String listClass;

    /**
     * 是否默认
     */
    @ApiModelProperty(value = "是否默认")
    @TableField("IS_DEFAULT")
    private String isDefault;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("STATUS")
    private String status;
}
