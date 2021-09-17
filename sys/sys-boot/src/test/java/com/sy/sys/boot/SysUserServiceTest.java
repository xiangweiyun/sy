package com.sy.sys.boot;

import com.sy.sys.service.SysUserService;
import com.sy.sys.entity.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 急救患者信息服务测试.
 * 
 * @author xiangweiyun
 * @date: 2019-09-24 15:44:41
 * @Copyright: Copyright (c) 2020
 * @Company: XWY有限公司
 * @Version: V1.0
 */
@SpringBootTest
@RefreshScope
@RunWith(SpringRunner.class)
public class SysUserServiceTest {
	@Autowired
	private SysUserService sysUserService;

	@Value("${xwy.name}")
	private String name;

	@Test
	public void save() {
		SysUser sysUser = new SysUser();
		sysUser.setUsername("xdd4");
		sysUser.setName("向大大4");
		sysUserService.add(sysUser);
	}

	@Test
	public void getName(){

		System.out.println(name);
	}
}