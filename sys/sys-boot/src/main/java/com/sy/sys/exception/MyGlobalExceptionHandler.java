package com.sy.sys.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sy.center.framework.utils.DataformResult;

/**
 * 全局异常处理
 * @author zxwen
 * @date 2021年9月22日
 *  
 */

@RestControllerAdvice(annotations = RestController.class)
public class MyGlobalExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(MyGlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	public DataformResult<String> GlobalException(Exception e) {
		logger.info(e.getMessage());
		return DataformResult.failure("系统异常，请联系管理人员 "+e.getMessage());
	}
}
