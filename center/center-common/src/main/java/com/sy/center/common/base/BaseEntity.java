package com.sy.center.common.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
	public static final String CREATED_BY = "createdBy";
	/** 创建时间. */
	public static final String CREATED_DATE = "createdDate";
	/** 更新时间. */
	public static final String LAST_MODIFIED_DATE = "lastModifiedDate";
	/** 更新人. */
	public static final String LAST_MODIFIED_BY = "lastModifiedBy";

	@Id
	@ApiModelProperty(value = "主键")
	@TableId(value = "ID", type = IdType.ID_WORKER)
	private Long id;

	@ApiModelProperty(value = "创建人")
	@TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
	private Long createdBy;

	@ApiModelProperty(value = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@TableField(value = "CREATED_DATE", fill = FieldFill.INSERT, update = "now()")
	private LocalDateTime createdDate;

	@ApiModelProperty(value = "更新时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@TableField(value = "LAST_MODIFIED_DATE", fill = FieldFill.INSERT_UPDATE, update = "now()")
	private LocalDateTime lastModifiedDate;

	@ApiModelProperty(value = "更新人")
	@TableField(value = "LAST_MODIFIED_BY", fill = FieldFill.INSERT_UPDATE)
	private Long lastModifiedBy;

}
