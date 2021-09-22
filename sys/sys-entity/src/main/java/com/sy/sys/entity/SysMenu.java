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
 * 系统菜单表
 * </p>
 *
 * @author zxwen
 * @since 2021-09-18
 */
@Getter
@Setter
@TableName("SYS_MENU")
@ApiModel(value="SysMenu对象", description="系统菜单表")
public class SysMenu extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /** 菜单名称. */
    public static final String NAME = "NAME";
    /** 父菜单ID. */
    public static final String PARENT_ID = "PARENT_ID";
    /** 所属系统标识. */
    public static final String SYS_ID = "SYS_ID";
    /** 是否为vue项目. */
    public static final String VUE = "VUE";
    /** 显示顺序. */
    public static final String ORDER_NUM = "ORDER_NUM";
    /** 请求地址/路由地址. */
    public static final String URL = "URL";
    /** 打开方式（menuItem页签 menuBlank新窗口）. */
    public static final String TARGET = "TARGET";
    /** 菜单类型（M目录 C菜单 F按钮）. */
    public static final String MENU_TYPE = "MENU_TYPE";
    /** 菜单状态（0隐藏 1显示）. */
    public static final String IS_VISIBLE = "IS_VISIBLE";
    /** 是否刷新（0不刷新 1刷新）. */
    public static final String IS_REFRESH = "IS_REFRESH";
    /** 权限标识. */
    public static final String PERMS = "PERMS";
    /** 菜单图标. */
    public static final String ICON = "ICON";
    /** 备注. */
    public static final String REMARK = "REMARK";

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    @TableField("NAME")
    private String name;

    /**
     * 父菜单ID
     */
    @ApiModelProperty(value = "父菜单ID")
    @TableField("PARENT_ID")
    private Long parentId;

    /**
     * 所属系统标识
     */
    @ApiModelProperty(value = "所属系统标识")
    @TableField("SYS_ID")
    private String sysId;

    /**
     * 是否为vue项目
     */
    @ApiModelProperty(value = "是否为vue项目")
    @TableField("VUE")
    private Integer vue;

    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序")
    @TableField("ORDER_NUM")
    private Long orderNum;

    /**
     * 请求地址/路由地址
     */
    @ApiModelProperty(value = "请求地址/路由地址")
    @TableField("URL")
    private String url;

    /**
     * 打开方式（menuItem页签 menuBlank新窗口）
     */
    @ApiModelProperty(value = "打开方式（menuItem页签 menuBlank新窗口）")
    @TableField("TARGET")
    private String target;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @ApiModelProperty(value = "菜单类型（M目录 C菜单 F按钮）")
    @TableField("MENU_TYPE")
    private String menuType;

    /**
     * 菜单状态（0隐藏 1显示）
     */
    @ApiModelProperty(value = "菜单状态（0隐藏 1显示）")
    @TableField("IS_VISIBLE")
    private Integer isVisible;

    /**
     * 是否刷新（0不刷新 1刷新）
     */
    @ApiModelProperty(value = "是否刷新（0不刷新 1刷新）")
    @TableField("IS_REFRESH")
    private Integer isRefresh;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识")
    @TableField("PERMS")
    private String perms;

    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标")
    @TableField("ICON")
    private String icon;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;
}
