package com.sy.center.common.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体基类
 * 
 * @author xiangwy
 * @date: 2020-12-01 15:42:29
 * @Copyright: Copyright (c) 2020
 * @Company: Xwy科技股份有限公司
 * @Version: V1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseEntity implements Serializable {
	/**
	 *
	 */
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	/** 主键ID. */
	public static final String ID = "id";
	/** 创建人. */
	public static final String CREATE_BY = "createBy";
	/** 创建时间. */
	public static final String CREATE_TIME = "createDate";
	/** 更新时间. */
	public static final String UPDATE_TIME = "updateDate";
	/** 更新人. */
	public static final String UPDATE_BY = "updateBy";

	@Id
	@Column(name = "ID")
	@ApiModelProperty(value = "主键")
	@TableId(value = "ID", type = IdType.ID_WORKER)
	private Long id;

	@Column(name = "CREATE_BY", updatable = false)
	@ApiModelProperty(value = "创建人")
	@TableField(value = "CREATE_BY", fill = FieldFill.INSERT)
	private Long createBy;

	@Column(name = "CREATE_TIME", updatable = false)
	@ApiModelProperty(value = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
	private LocalDateTime createDate;

	@Column(name = "UPDATE_TIME")
	@ApiModelProperty(value = "更新时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@TableField(value = "UPDATE_DATE", fill = FieldFill.INSERT_UPDATE, update = "now()")
	private LocalDateTime updateDate;

	@Column(name = "UPDATE_BY")
	@ApiModelProperty(value = "更新人")
	@TableField(value = "UPDATE_BY", fill = FieldFill.INSERT_UPDATE)
	private Long updateBy;

}
