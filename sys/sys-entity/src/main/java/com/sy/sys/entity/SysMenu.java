package com.sy.sys.entity;

import com.sy.center.common.base.BaseEntity;
import javax.persistence.Column;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统_资源
 * </p>
 *
 * @author xiangwy
 * @since 2021-09-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysMenu对象", description="系统_资源")
public class SysMenu extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /** 标题. */
    public static final String NAME = "name";
    /** 资源标识符. */
    public static final String IDENTITY = "identity";
    /** 菜单url. */
    public static final String URL = "url";
    /** 父资源. */
    public static final String PARENT_ID = "parentId";
    /** 父路径. */
    public static final String PARENT_IDS = "parentIds";
    /** 菜单权重. */
    public static final String WEIGHT = "weight";
    /** 菜单图标. */
    public static final String ICON = "icon";
    /** 资源类型（1=显示2禁止0=隐藏）. */
    public static final String STATUS = "status";
    /** 资源类型（1=菜单2=权限）. */
    public static final String MENU_TYPE = "menuType";
    /** 外部链接（0，否，1是）. */
    public static final String OUT_LINK = "outLink";
    /** 显示方式（0，普通内嵌，1，内嵌iframe，2，外窗口）. */
    public static final String DISPLAY_MODE = "displayMode";

    /**
     * 标题
     */
    @Column(name = "NAME")
    @ApiModelProperty(value = "标题")
    private String name;

    /**
     * 资源标识符
     */
    @Column(name = "IDENTITY")
    @ApiModelProperty(value = "资源标识符")
    private String identity;

    /**
     * 菜单url
     */
    @Column(name = "URL")
    @ApiModelProperty(value = "菜单url")
    private String url;

    /**
     * 父资源
     */
    @Column(name = "PARENT_ID")
    @ApiModelProperty(value = "父资源")
    private String parentId;

    /**
     * 父路径
     */
    @Column(name = "PARENT_IDS")
    @ApiModelProperty(value = "父路径")
    private String parentIds;

    /**
     * 菜单权重
     */
    @Column(name = "WEIGHT")
    @ApiModelProperty(value = "菜单权重")
    private Integer weight;

    /**
     * 菜单图标
     */
    @Column(name = "ICON")
    @ApiModelProperty(value = "菜单图标")
    private String icon;

    /**
     * 资源类型（1=显示2禁止0=隐藏）
     */
    @Column(name = "STATUS")
    @ApiModelProperty(value = "资源类型（1=显示2禁止0=隐藏）")
    private String status;

    /**
     * 资源类型（1=菜单2=权限）
     */
    @Column(name = "MENU_TYPE")
    @ApiModelProperty(value = "资源类型（1=菜单2=权限）")
    private String menuType;

    /**
     * 外部链接（0，否，1是）
     */
    @Column(name = "OUT_LINK")
    @ApiModelProperty(value = "外部链接（0，否，1是）")
    private String outLink;

    /**
     * 显示方式（0，普通内嵌，1，内嵌iframe，2，外窗口）
     */
    @Column(name = "DISPLAY_MODE")
    @ApiModelProperty(value = "显示方式（0，普通内嵌，1，内嵌iframe，2，外窗口）")
    private String displayMode;
}
