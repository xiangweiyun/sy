package com.sy.center.auth.filter;

import com.sy.center.auth.manager.TokenManager;
import com.sy.center.auth.properties.JwtProperties;
import com.sy.center.auth.util.JwtTokenUtil;
import com.sy.center.common.constant.SysConstant;
import com.sy.center.common.utils.BlankUtils;
import com.sy.center.common.utils.WriterUtil;
import com.sy.center.framework.constant.CustomRspCon;
import com.sy.center.framework.utils.DataformResult;
import io.jsonwebtoken.JwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 对客户端请求的jwt token验证过滤器
 * 
 * @author xiangwy
 * @date: 2020-12-03 10:33:00
 * @Copyright: Copyright (c) 2020
 * @Company: XWY有限公司
 * @Version: V1.0
 */
@Component
public class AuthFilter extends OncePerRequestFilter {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtProperties jwtProperties;

	@Value("${authNoInterceptURL}")
	private String authNoInterceptURL;

	@Autowired
	private TokenManager tokenManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String servletPath = request.getServletPath();
		// 不拦截/swagger文档
		if (servletPath.contains("/swagger") || servletPath.contains("/webjars") || servletPath.contains("/api-docs")
				|| servletPath.contains("/images") || servletPath.contains("/doc.html")) {
			chain.doFilter(request, response);
			return;
		}
		logger.info("接口访问地址：" + servletPath);
		if (servletPath.contains("/" + jwtProperties.getAuthPath())) {
			chain.doFilter(request, response);
			return;
		}
		// 不拦截的地址
		if (BlankUtils.isNotBlank(authNoInterceptURL)) {
			String[] authNoInterceptURLArr = authNoInterceptURL.split(",");
			for (String sniua : authNoInterceptURLArr) {
				if (servletPath.contains(sniua)) {
					chain.doFilter(request, response);
					return;
				}
			}
		}

		final String requestHeader = request.getHeader(jwtProperties.getHeader());
		String authToken = null;
		if (requestHeader != null && requestHeader.startsWith(SysConstant.AUTH_PRE_B)) {
			authToken = requestHeader.substring(7);
			// 验证token是否过期
			try {
				String key = tokenManager.getKey(authToken);
				if (key == null) {
					WriterUtil.renderString(response, DataformResult.failure(CustomRspCon.TOKEN_EXPIRED.getCode(),
							CustomRspCon.TOKEN_EXPIRED.getMsg()));
					return;
				}
			} catch (JwtException e) {
				// 有异常就是token解析失败
				WriterUtil.renderString(response,
						DataformResult.failure(CustomRspCon.TOKEN_EXPIRED.getCode(), CustomRspCon.TOKEN_EXPIRED.getMsg()));
				return;
			}
		} else {
			// header没有带Bearer字段
			WriterUtil.renderString(response,
					DataformResult.failure(CustomRspCon.TOKEN_EXPIRED.getCode(), CustomRspCon.TOKEN_EXPIRED.getMsg()));
			return;
		}
		chain.doFilter(request, response);
	}
}