package com.sy.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sy.center.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;

/**
 * <p>
 * 系统机构表
 * </p>
 *
 * @author zxwen
 * @since 2021-09-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_ORG")
@ApiModel(value="SysOrg对象", description="系统机构表")
public class SysOrg extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /** 上级机构. */
    public static final String PARENT_ID = "parentId";
    /** 机构编码. */
    public static final String CODE = "code";
    /** 机构名称. */
    public static final String NAME = "name";
    /** 机构级别. */
    public static final String LEVEL_CODE = "levelCode";
    /** 经度. */
    public static final String LONGITUDE = "longitude";
    /** 纬度. */
    public static final String LATITUDE = "latitude";
    /** 联系电话. */
    public static final String PHONE = "phone";
    /** 机构简介. */
    public static final String ORG_PROFILE = "orgProfile";
    /** 机构图片. */
    public static final String PICTURE = "picture";
    /** 机构类型. */
    public static final String ORG_TYPE = "orgType";

    /**
     * 上级机构
     */
    @Column(name = "PARENT_ID")
    @ApiModelProperty(value = "上级机构")
    @TableField("PARENT_ID")
    private Long parentId;

    /**
     * 机构编码
     */
    @Column(name = "CODE")
    @ApiModelProperty(value = "机构编码")
    @TableField("CODE")
    private String code;

    /**
     * 机构名称
     */
    @Column(name = "NAME")
    @ApiModelProperty(value = "机构名称")
    @TableField("NAME")
    private String name;

    /**
     * 机构级别
     */
    @Column(name = "LEVEL_CODE")
    @ApiModelProperty(value = "机构级别")
    @TableField("LEVEL_CODE")
    private Long levelCode;

    /**
     * 经度
     */
    @Column(name = "LONGITUDE")
    @ApiModelProperty(value = "经度")
    @TableField("LONGITUDE")
    private Double longitude;

    /**
     * 纬度
     */
    @Column(name = "LATITUDE")
    @ApiModelProperty(value = "纬度")
    @TableField("LATITUDE")
    private Double latitude;

    /**
     * 联系电话
     */
    @Column(name = "PHONE")
    @ApiModelProperty(value = "联系电话")
    @TableField("PHONE")
    private String phone;

    /**
     * 机构简介
     */
    @Column(name = "ORG_PROFILE")
    @ApiModelProperty(value = "机构简介")
    @TableField("ORG_PROFILE")
    private String orgProfile;

    /**
     * 机构图片
     */
    @Column(name = "PICTURE")
    @ApiModelProperty(value = "机构图片")
    @TableField("PICTURE")
    private String picture;

    /**
     * 机构类型
     */
    @Column(name = "ORG_TYPE")
    @ApiModelProperty(value = "机构类型")
    @TableField("ORG_TYPE")
    private String orgType;
}
