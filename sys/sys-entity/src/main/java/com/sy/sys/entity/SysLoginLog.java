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
 * 登录日志
 * </p>
 *
 * @author xiangwy
 * @since 2021-09-22
 */
@Getter
@Setter
@TableName("SYS_LOGIN_LOG")
@ApiModel(value="SysLoginLog对象", description="登录日志")
public class SysLoginLog extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /** 操作类型（1,登录;2,登出）. */
    public static final String OPER_TYPE = "OPER_TYPE";
    /** 登录结果类型(1,成功；2失败). */
    public static final String RESULT_TYPE = "RESULT_TYPE";
    /** 操作IP地址. */
    public static final String REMOTE_ADDR = "REMOTE_ADDR";
    /** 用户代理. */
    public static final String USER_AGENT = "USER_AGENT";
    /** 操作时间（登录/退出的时间）. */
    public static final String OPER_TIME = "OPER_TIME";
    /** 地点. */
    public static final String ADDRESS = "ADDRESS";
    /** 浏览器. */
    public static final String BROWSER_TYPE = "BROWSER_TYPE";
    /** 登录类型. */
    public static final String LOGIN_TYPE = "LOGIN_TYPE";
    /** 设备. */
    public static final String EQUIPMENT = "EQUIPMENT";
    /** 登录帐号. */
    public static final String LOGIN_ACCOUNT = "LOGIN_ACCOUNT";
    /** 备注信息. */
    public static final String REMARKS = "REMARKS";

    /**
     * 操作类型（1,登录;2,登出）
     */
    @ApiModelProperty(value = "操作类型（1,登录;2,登出）")
    @TableField("OPER_TYPE")
    private String operType;

    /**
     * 登录结果类型(1,成功；2失败)
     */
    @ApiModelProperty(value = "登录结果类型(1,成功；2失败)")
    @TableField("RESULT_TYPE")
    private String resultType;

    /**
     * 操作IP地址
     */
    @ApiModelProperty(value = "操作IP地址")
    @TableField("REMOTE_ADDR")
    private String remoteAddr;

    /**
     * 用户代理
     */
    @ApiModelProperty(value = "用户代理")
    @TableField("USER_AGENT")
    private String userAgent;

    /**
     * 操作时间（登录/退出的时间）
     */
    @ApiModelProperty(value = "操作时间（登录/退出的时间）")
    @TableField("OPER_TIME")
    private LocalDateTime operTime;

    /**
     * 地点
     */
    @ApiModelProperty(value = "地点")
    @TableField("ADDRESS")
    private String address;

    /**
     * 浏览器
     */
    @ApiModelProperty(value = "浏览器")
    @TableField("BROWSER_TYPE")
    private String browserType;

    /**
     * 登录类型
     */
    @ApiModelProperty(value = "登录类型")
    @TableField("LOGIN_TYPE")
    private String loginType;

    /**
     * 设备
     */
    @ApiModelProperty(value = "设备")
    @TableField("EQUIPMENT")
    private String equipment;

    /**
     * 登录帐号
     */
    @ApiModelProperty(value = "登录帐号")
    @TableField("LOGIN_ACCOUNT")
    private String loginAccount;

    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    @TableField("REMARKS")
    private String remarks;
}
