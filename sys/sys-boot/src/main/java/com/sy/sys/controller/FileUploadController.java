package com.sy.sys.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import com.alibaba.nacos.common.utils.StringUtils;
import com.sy.center.common.utils.FileUitl;
import com.sy.center.framework.utils.DataformResult;
import com.sy.sys.enums.FileNatureEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

/**
 * 文件上传
 * @author zxwen
 * @date 2021年9月25日
 *  
 */

@RestController
@RequestMapping("/file")
@Api(tags = "文件上传")
public class FileUploadController {
	
	@Value("${filePath}")
    private String filePath;	
	
	@PostMapping({"/uploadFile"})
	@ApiImplicitParams({
        @ApiImplicitParam(required = false, name = "file", value = "文件", dataType = "File"),
        @ApiImplicitParam(required = false, name = "source", value = "来源 USER-->用户图像  ORG -->机构照片", dataType = "String")})
	public DataformResult<String> uploadFile(HttpServletRequest request) throws IOException
	{	
		String contentType = request.getContentType();
		String source = request.getParameter("source");
		if(StringUtils.isBlank(source)) {
			return DataformResult.failure("文件来源不允许为空");
		}
		String  fileType = FileNatureEnum.OTHER.getCode();
		switch(source.toUpperCase()) {
		case "USER":
			fileType = FileNatureEnum.USER.getCode();
			break;
		case "ORG":
			fileType = FileNatureEnum.ORG.getCode();
			break;
		default:
			fileType = FileNatureEnum.OTHER.getCode();
		}
		
		if(contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
			MultipartHttpServletRequest multipartReqest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
			MultipartFile file = multipartReqest.getFile("file");
			
			if(null == file) {
				return DataformResult.failure("未接收到文件内容");
			}
			if(!StringUtils.isNotBlank(file.getOriginalFilename())) {
				return DataformResult.success("未接收到文件内容");
			}
		    if (!filePath.endsWith("/")) {
		    	filePath += "/";
		    }
		    
		    String path = filePath + fileType + "/";
		    String fileName = FileUitl.uploadFile(file, path);
		    return DataformResult.success(fileType + "/" + fileName);
		}
		return DataformResult.failure("未接收到文件内容");
	}
}
