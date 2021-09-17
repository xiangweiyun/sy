package com.sy.sys.controller;

import com.sy.ecis.service.XwyService;
import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统_用户控制器.
 * 
 * @author xiangwy
 * @date: 2020-12-01
 * @Copyright: Copyright (c) 2020
 * @Company: Xwy科技股份有限公司
 * @Version: V1.0
 */
@RestController
@RequestMapping("/xwy")
@Api(tags = "系统_用户")
public class XwyController {
	private final Logger logger = LoggerFactory.getLogger(XwyController.class);

	@DubboReference(version = "${dubbo.service.version}")
	private XwyService xwyService;

	@GetMapping("/getName")
	public String getName(){
		return xwyService.hello("张三");
	}

}
