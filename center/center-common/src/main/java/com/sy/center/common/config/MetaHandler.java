package com.sy.center.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.sy.center.common.utils.jwt.TokenUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 设置填充值
 * 
 * @author xiangwy
 * @date: 2020-12-01 15:59:52
 * @Copyright: Copyright (c) 2020
 * @Company: Xwy科技股份有限公司
 * @Version: V1.0
 */
@Component
public class MetaHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		this.setFieldValByName("createdDate", LocalDateTime.now(), metaObject);
		this.setFieldValByName("createdBy", Long.parseLong(TokenUtil.getUserId()), metaObject);
		this.setFieldValByName("lastModifiedDate", LocalDateTime.now(), metaObject);
		this.setFieldValByName("lastModifiedBy", Long.parseLong(TokenUtil.getUserId()), metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		this.setFieldValByName("lastModifiedDate", LocalDateTime.now(), metaObject);
		this.setFieldValByName("lastModifiedBy", Long.parseLong(TokenUtil.getUserId()), metaObject);
	}

}
