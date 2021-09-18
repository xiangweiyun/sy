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
 * 系统部门
 * </p>
 *
 * @author xiangwy
 * @since 2021-09-18
 */
@Getter
@Setter
@TableName("SYS_DEPT")
@ApiModel(value="SysDept对象", description="系统部门")
public class SysDept extends BaseEntity {
    private static final long serialVersionUID = 1L;
    
    public static final String CODE = "CODE";
    /** 部门名称. */
    public static final String DEPT_NAME = "DEPT_NAME";
    /** 父部门ID. */
    public static final String PARENT_ID = "PARENT_ID";
    
    public static final String LOCATION = "LOCATION";
    
    public static final String INTRODUCTION = "INTRODUCTION";
    /** 祖级列表. */
    public static final String ANCESTORS = "ANCESTORS";
    
    public static final String ORG_ID = "ORG_ID";
    /** 显示顺序. */
    public static final String ORDER_NUM = "ORDER_NUM";
    /** 负责人. */
    public static final String LEADER = "LEADER";
    /** 联系电话. */
    public static final String PHONE = "PHONE";
    /** 邮箱. */
    public static final String EMAIL = "EMAIL";
    /** 启用状态. */
    public static final String STATUS = "STATUS";
    /** 删除标志. */
    public static final String IS_DELETED = "IS_DELETED";

    @TableField("CODE")
    private String code;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    @TableField("DEPT_NAME")
    private String deptName;

    /**
     * 父部门ID
     */
    @ApiModelProperty(value = "父部门ID")
    @TableField("PARENT_ID")
    private Long parentId;

    @TableField("LOCATION")
    private String location;

    @TableField("INTRODUCTION")
    private String introduction;

    /**
     * 祖级列表
     */
    @ApiModelProperty(value = "祖级列表")
    @TableField("ANCESTORS")
    private String ancestors;

    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序")
    @TableField("ORDER_NUM")
    private Long orderNum;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    @TableField("LEADER")
    private String leader;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @TableField("PHONE")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @TableField("EMAIL")
    private String email;

    /**
     * 启用状态
     */
    @ApiModelProperty(value = "启用状态")
    @TableField("STATUS")
    private String status;

    /**
     * 删除标志
     */
    @ApiModelProperty(value = "删除标志")
    @TableField("IS_DELETED")
    private Integer isDeleted;
}
