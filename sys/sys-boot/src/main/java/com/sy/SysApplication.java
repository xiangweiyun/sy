package com.sy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 启动类
 *
 * @author xiangwy
 * @date: 2020-11-30 15:41:43
 * @Copyright: Copyright (c) 2020
 * @Company: 云南升玥信息技术有限公司
 * @Version: V1.0
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class SysApplication implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(SysApplication.class, args);
    }

    @Value("${filePath}")
    private String filePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("file:"+filePath);
    }
}
