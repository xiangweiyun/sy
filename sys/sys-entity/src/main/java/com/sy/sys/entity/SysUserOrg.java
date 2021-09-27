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
 * 用户关联机构
 * </p>
 *
 * @author zxwen
 * @since 2021-09-27
 */
@Getter
@Setter
@TableName("SYS_USER_ORG")
@ApiModel(value="SysUserOrg对象", description="用户关联机构")
public class SysUserOrg extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /** 用户ID. */
    public static final String USER_ID = "USER_ID";
    /** 机构ID. */
    public static final String ORG_ID = "ORG_ID";

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    @TableField("USER_ID")
    private Long userId;

    /**
     * 机构ID
     */
    @ApiModelProperty(value = "机构ID")
    @TableField("ORG_ID")
    private Long orgId;
}
