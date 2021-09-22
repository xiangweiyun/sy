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
 * 角色和菜单关联表
 * </p>
 *
 * @author zxwen
 * @since 2021-09-22
 */
@Getter
@Setter
@TableName("SYS_ROLE_MENU")
@ApiModel(value="SysRoleMenu对象", description="角色和菜单关联表")
public class SysRoleMenu extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /** 角色ID. */
    public static final String ROLE_ID = "ROLE_ID";
    /** 菜单ID. */
    public static final String MENU_ID = "MENU_ID";

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    @TableField("ROLE_ID")
    private Long roleId;

    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单ID")
    @TableField("MENU_ID")
    private Long menuId;
}
