package com.sy.center.swagger.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.sy.center.swagger.properties.SwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置
 * 
 * @author xiangwy
 * @date: 2020-12-01 09:20:20
 * @Copyright: Copyright (c) 2020
 * @Company: XWY有限公司
 * @Version: V1.0
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

	@Autowired
	private SwaggerProperties swaggerProperties;

	@Bean
	public Docket customDocket() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());
	}

	/**
	 * name:开发者姓名 url:开发者网址 email:开发者邮箱
	 * 
	 * @author: xiangwy
	 * @date: 2020-12-01 09:21:11
	 * @return
	 */
	private ApiInfo apiInfo() {
		Contact contact = new Contact("向为运", "", "1223062573@qq.com");
		// 标题
		return new ApiInfoBuilder().title(swaggerProperties.getTitle())
				// 文档接口的描述
				.description(swaggerProperties.getDescription())
				// 版本号
				.contact(contact).version(swaggerProperties.getVersion())
				.build();
	}
}
