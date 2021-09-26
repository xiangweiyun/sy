package com.sy.sys.enums;

/**
 * 文件分类
 * @author zxwen
 * @date 2021年9月25日
 *  
 */
public enum FileNatureEnum {
	USER("USER", "用户头像"),
	ORG("ORG", "机构照片"),
	OTHER("OTHER", "其他");
	
	private String code;
	private String name;
	
	private FileNatureEnum(String code, String name) {
		this.code = code;
		this.name = name;
	};

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
