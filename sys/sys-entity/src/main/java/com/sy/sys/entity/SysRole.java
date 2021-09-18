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
 * 系统角色表
 * </p>
 *
 * @author xiangwy
 * @since 2021-09-18
 */
@Getter
@Setter
@TableName("SYS_ROLE")
@ApiModel(value="SysRole对象", description="系统角色表")
public class SysRole extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /** 角色名称. */
    public static final String NAME = "NAME";
    /** 角色权限字符串. */
    public static final String CODE = "CODE";
    /** 机构ID. */
    public static final String ORG_ID = "ORG_ID";
    /** 显示顺序. */
    public static final String ROLE_SORT = "ROLE_SORT";
    /** 是否删除. */
    public static final String IS_DELETED = "IS_DELETED";

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    @TableField("NAME")
    private String name;

    /**
     * 角色权限字符串
     */
    @ApiModelProperty(value = "角色权限字符串")
    @TableField("CODE")
    private String code;

    /**
     * 机构ID
     */
    @ApiModelProperty(value = "机构ID")
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序")
    @TableField("ROLE_SORT")
    private Long roleSort;

    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
    @TableField("IS_DELETED")
    private Integer isDeleted;
}
