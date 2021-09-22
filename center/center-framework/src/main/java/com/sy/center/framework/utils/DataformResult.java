package com.sy.center.framework.utils;

import com.sy.center.framework.constant.CustomRspCon;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 統一結果列表返回MODEL
 * 
 * @author xiangwy
 * @date: 2020-12-01 12:03:27
 * @Copyright: Copyright (c) 2020
 * @Company: Xwy科技股份有限公司
 * @Version: V1.0
 */
@Setter
@Getter
@ToString
public class DataformResult<T> implements Serializable {
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 8968766969795754248L;

	/**
	 * 信息
	 */
	private String message;

	/**
	 * 状态码
	 */

	private int code;

	/**
	 * 是否成功
	 */
	private boolean success;

	private T data;

	public static <T> DataformResult<T> success() {
		DataformResult<T> result = new DataformResult<>();
		result.setSuccess(true);
		result.setCode(200);
		return result;
	}

	public static <T> DataformResult<T> success(T data) {
		DataformResult<T> result = new DataformResult<>();
		result.setSuccess(true);
		result.setCode(200);
		result.setData(data);
		return result;
	}

	public static <T> DataformResult<T> failure() {
		DataformResult<T> result = new DataformResult<>();
		result.setSuccess(false);
		return result;
	}

	public static <T> DataformResult<T> failure(String message) {
		DataformResult<T> result = new DataformResult<>();
		result.setSuccess(false);
		result.setMessage(message);
		return result;
	}

	public static <T> DataformResult<T> failure(CustomRspCon rspCon) {
		DataformResult<T> result = new DataformResult<>();
		result.setSuccess(false);
		result.setCode(rspCon.getCode());
		result.setMessage(rspCon.getMsg());
		return result;
	}

	public static <T> DataformResult<T> failure(int statusCode, String message) {
		DataformResult<T> result = new DataformResult<>();
		result.setCode(statusCode);
		result.setSuccess(false);
		result.setMessage(message);
		return result;
	}

	public static <T> DataformResult<T> failure(String message, T data) {
		DataformResult<T> result = new DataformResult<>();
		result.setSuccess(false);
		result.setMessage(message);
		result.setData(data);
		return result;
	}
}
