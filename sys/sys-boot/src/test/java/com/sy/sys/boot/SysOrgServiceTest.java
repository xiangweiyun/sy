package com.sy.sys.boot;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.sy.sys.entity.SysOrg;
import com.sy.sys.service.SysOrgService;
import com.sy.sys.vo.SysOrgVo;

/**
 * {title}
 * @author zxwen
 * @date 2021年9月17日
 *  
 */

@SpringBootTest
@RefreshScope
@RunWith(SpringRunner.class)
public class SysOrgServiceTest {

	@Autowired
	private SysOrgService sysOrgService;
	
	@Test
	public void save() {
		SysOrg sysOrg = new SysOrg();
		sysOrg.setCode("111");
		// sysOrg.setParentId(0L);
		sysOrg.setName("省人医");
		sysOrg.setOrgType("0");
		
		sysOrgService.save(sysOrg);
	}
	
	public void getOrgData() {
		List<SysOrgVo> a = sysOrgService.getTreeData(1438844617070034946L);
		System.out.println(JSON.toJSONString(a));
	}
}
