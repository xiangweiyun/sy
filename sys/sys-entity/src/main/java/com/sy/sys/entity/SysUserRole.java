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
 * 用户和角色关联表
 * </p>
 *
 * @author zxwen
 * @since 2021-09-22
 */
@Getter
@Setter
@TableName("SYS_USER_ROLE")
@ApiModel(value="SysUserRole对象", description="用户和角色关联表")
public class SysUserRole extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /** 用户ID. */
    public static final String USER_ID = "USER_ID";
    /** 角色ID. */
    public static final String ROLE_ID = "ROLE_ID";

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    @TableField("USER_ID")
    private Long userId;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    @TableField("ROLE_ID")
    private Long roleId;
}
