package com.sy.sys.vo.user;

import java.util.List;

import com.sy.sys.vo.SysMenuVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户相关信息
 * @author zxwen
 * @date 2021年9月24日
 *  
 */

@Getter
@Setter
@ApiModel(value="用户相关信息", description="用户相关信息")
public class User {
	@ApiModelProperty(value = "ID")
	private Long id;
	
	@ApiModelProperty(value = "机构ID")
	private Long orgId;
	
	@ApiModelProperty(value = "机构描述")
	private String orgName;
	
	@ApiModelProperty(value = "部门ID")
	private Long deptId;
	
	@ApiModelProperty(value = "部门描述")
	private String deptName;
	
	@ApiModelProperty(value = "姓名")
	private String name;
	
	@ApiModelProperty(value = "用户帐号")
	private String userName;
	
	@ApiModelProperty(value = "职称编码")
	private String jobCode;
	
	@ApiModelProperty(value = "职称描述")
	private String jobName;
	
	@ApiModelProperty(value = "职务编码")
	private String postCode;
	
	@ApiModelProperty(value = "职务描述")
	private String postName;
	
	@ApiModelProperty(value = "所属机构")
	private List<Org> listOrg;
	
	@ApiModelProperty(value = "所属部门")
	private List<Dept> listDept;
	
	@ApiModelProperty(value = "菜单")
	private List<SysMenuVo> listMenu;
	
}
