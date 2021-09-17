package com.sy.center.common.config;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus分页插件
 * 
 * @author xiangwy
 * @date: 2020-11-30 16:21:56
 * @Copyright: Copyright (c) 2020
 * @Company: XWY有限公司
 * @Version: V1.0
 */
@Configuration
@MapperScan("com.sy.**.mapper")
public class MybatisPlusConfig {
	/**
	 * 分页插件
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}

	/**
	 * 自动填充功能
	 *
	 * @return
	 */
	@Bean
	public GlobalConfig globalConfig() {
		GlobalConfig globalConfig = new GlobalConfig();
		globalConfig.setMetaObjectHandler(new MetaHandler());
		return globalConfig;
	}

}
