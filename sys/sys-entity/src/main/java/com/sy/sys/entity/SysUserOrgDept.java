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
 * 用户部门关联表
 * </p>
 *
 * @author zxwen
 * @since 2021-09-22
 */
@Getter
@Setter
@TableName("SYS_USER_ORG_DEPT")
@ApiModel(value="SysUserOrgDept对象", description="用户部门关联表")
public class SysUserOrgDept extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /** 用户ID. */
    public static final String USER_ID = "USER_ID";
    /** 机构ID. */
    public static final String ORG_ID = "ORG_ID";
    /** 部门ID. */
    public static final String DEPT_ID = "DEPT_ID";
    /** 主部门. */
    public static final String HAS_MAIN = "HAS_MAIN";

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

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID")
    @TableField("DEPT_ID")
    private Long deptId;

    /**
     * 主部门
     */
    @ApiModelProperty(value = "主部门")
    @TableField("HAS_MAIN")
    private Boolean hasMain;
}
