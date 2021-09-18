package com.sy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

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
public class SysApplication {
	public static void main(String[] args) {
		SpringApplication.run(SysApplication.class, args);
	}
}
