package com.sy.sys.service.impl;

import com.sy.ecis.service.XwyService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author xiangwy
 * @description: TODO
 * @date: 2021-09-10 16:14
 * @Copyright: Copyright (c) 2021 - 2021
 * @Company: xwy
 * @Version: V1.0
 */
@DubboService(version = "${dubbo.service.version}")
public class XwyServiceImpl implements XwyService {

    @Override
    public String hello(String name) {
        return name;
    }
}
