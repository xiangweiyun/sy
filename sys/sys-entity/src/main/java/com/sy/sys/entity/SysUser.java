package com.sy.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sy.center.common.base.BaseEntity;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author zxwen
 * @since 2021-09-22
 */
@Getter
@Setter
@TableName("SYS_USER")
@ApiModel(value="SysUser对象", description="用户表")
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /** 用户姓名. */
    public static final String NAME = "NAME";
    /** 登录名. */
    public static final String USERNAME = "USERNAME";
    /** 用户密码. */
    public static final String PASSWORD = "PASSWORD";
    /** 医生编号. */
    public static final String NO_NUM = "NO_NUM";
    /** 职称代码. */
    public static final String JOB_TITLE_CODE = "JOB_TITLE_CODE";
    /** 身份证号. */
    public static final String IDCARD = "IDCARD";
    /** 性别代码. */
    public static final String GENDER_CODE = "GENDER_CODE";
    /** 民族代码. */
    public static final String NATION_CODE = "NATION_CODE";
    /** 擅长领域. */
    public static final String GOOD_AT_FIELD = "GOOD_AT_FIELD";
    /** 个人简介. */
    public static final String PERSONAL_PROFILE = "PERSONAL_PROFILE";
    /** 头像路径. */
    public static final String AVATAR = "AVATAR";
    /** 办公室电话. */
    public static final String OFFICE_PHONE = "OFFICE_PHONE";
    /** 是否启用. */
    public static final String IS_ENABLED = "IS_ENABLED";
    /** 启用日期. */
    public static final String ENABLE_DATE = "ENABLE_DATE";
    /** 停用日期. */
    public static final String DISABLED_DATE = "DISABLED_DATE";
    /** 是否删除. */
    public static final String IS_DELETED = "IS_DELETED";
    /** 直属机构. */
    public static final String ORG_ID = "ORG_ID";
    /** 直属部门. */
    public static final String DEPT_ID = "DEPT_ID";
    /** 职务编码. */
    public static final String POST_CODE = "POST_CODE";
    

    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名")
    @TableField("NAME")
    private String name;

    /**
     * 登录名
     */
    @ApiModelProperty(value = "登录名")
    @TableField("USERNAME")
    private String username;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户密码")
    @TableField("PASSWORD")
    private String password;

    /**
     * 医生编号
     */
    @ApiModelProperty(value = "医生编号")
    @TableField("NO_NUM")
    private String noNum;

    /**
     * 职称代码
     */
    @ApiModelProperty(value = "职称代码")
    @TableField("JOB_TITLE_CODE")
    private Long jobTitleCode;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    @TableField("IDCARD")
    private String idcard;

    /**
     * 性别代码
     */
    @ApiModelProperty(value = "性别代码")
    @TableField("GENDER_CODE")
    private Long genderCode;

    /**
     * 民族代码
     */
    @ApiModelProperty(value = "民族代码")
    @TableField("NATION_CODE")
    private Long nationCode;

    /**
     * 擅长领域
     */
    @ApiModelProperty(value = "擅长领域")
    @TableField("GOOD_AT_FIELD")
    private String goodAtField;

    /**
     * 个人简介
     */
    @ApiModelProperty(value = "个人简介")
    @TableField("PERSONAL_PROFILE")
    private String personalProfile;

    /**
     * 头像路径
     */
    @ApiModelProperty(value = "头像路径")
    @TableField("AVATAR")
    private String avatar;

    /**
     * 办公室电话
     */
    @ApiModelProperty(value = "办公室电话")
    @TableField("OFFICE_PHONE")
    private String officePhone;

    /**
     * 是否启用
     */
    @ApiModelProperty(value = "是否启用")
    @TableField("IS_ENABLED")
    private Integer isEnabled;

    /**
     * 启用日期
     */
    @ApiModelProperty(value = "启用日期")
    @TableField("ENABLE_DATE")
    private LocalDateTime enableDate;

    /**
     * 停用日期
     */
    @ApiModelProperty(value = "停用日期")
    @TableField("DISABLED_DATE")
    private LocalDateTime disabledDate;

    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
    @TableField("IS_DELETED")
    private Integer isDeleted;

    /**
     * 直属机构
     */
    @ApiModelProperty(value = "直属机构")
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 直属部门
     */
    @ApiModelProperty(value = "直属部门")
    @TableField("DEPT_ID")
    private Long deptId;
    
    /**
     * 职务编码
     */
    @ApiModelProperty(value = "职务编码")
    @TableField("POST_CODE")
    private Long postCode;
}
