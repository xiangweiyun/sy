package com.sy.center.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.sy.center.framework.constant.CustomRspCon;
import com.sy.center.framework.utils.DataformResult;
import com.sy.center.gateway.fegin.AuthService;
import com.sy.center.gateway.properties.IgnoreUrlsProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * token校验过滤器
 * 
 * @author xiangwy
 * @date: 2020-12-03 11:42:02
 * @Copyright: Copyright (c) 2020
 * @Company: XWY有限公司
 * @Version: V1.0
 */
@Component
public class AuthorizeGatewayFilter implements GlobalFilter, Ordered {
	private final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 认证的头部
	 */
	private static final String AUTHORIZE_TOKEN = "Authorization";
	private static final String BEARER = "Bearer ";
	/**
	 * UTF-8 字符集
	 */
	private static final String UTF8 = "UTF-8";

	@Autowired
	private AuthService authService;

	@Autowired
	private IgnoreUrlsProperties ignoreUrlsProperties;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		HttpHeaders headers = request.getHeaders();
		String path = request.getPath().value();
		logger.info("请求网关地址：" + path);
		try {
			if (isIgnoreHttpUrls(path)) {
				return chain.filter(exchange);
			}
			final String authorization = headers.getFirst(AUTHORIZE_TOKEN);
			if (authorization != null && authorization.startsWith(BEARER)) {
				String authToken = authorization.substring(7);
				DataformResult<String> checkToken = authService.checkToken(authToken);
				int code = checkToken.getCode();
				// 认证（校验）成功的
				if (code == CustomRspCon.SUCCESS.getCode()) {
					ServerHttpRequest.Builder builder = request.mutate();
					// 转发的请求都加上服务间认证token
					builder.header(AUTHORIZE_TOKEN, authorization);
					// 将jwt token中的用户信息传给服务
					builder.header("userInfoStr", checkToken.getData());
					ServerWebExchange mutableExchange = exchange.mutate().request(builder.build()).build();
					return chain.filter(mutableExchange);
				} else if (code == CustomRspCon.TOKEN_EXPIRED.getCode()) {
					logger.info("token已过期！");
					return unauthorizedResponse(exchange, DataformResult.failure(code, "token已过期！"));
				}
			} else {
				logger.info("认证信息不为空！");
				return unauthorizedResponse(exchange, DataformResult.failure(702, "请确保认证信息不为空！"));
			}
		} catch (Exception e) {
			logger.info("网关异常：" + e);
			return unauthorizedResponse(exchange, DataformResult.failure(703, "网关异常！"));
		}
		return chain.filter(exchange);
	}

	/**
	 * 请求地址是否为需要忽略token验证的地址
	 * 
	 * @author: xiangwy
	 * @date: 2020-12-03 11:50:21
	 * @param servletPath
	 * @return
	 */
	private boolean isIgnoreHttpUrls(String servletPath) {
		boolean rt = false;
		List<String> ignoreHttpUrls = ignoreUrlsProperties.getHttpUrls();
		for (String ihu : ignoreHttpUrls) {
			if (servletPath.contains(ihu)) {
				rt = true;
				break;
			}
		}
		return rt;
	}

	/**
	 * 认证失败时，返回json数据
	 * 
	 * @author: xiangwy
	 * @date: 2020-12-03 11:49:28
	 * @param exchange
	 * @param dataformResult
	 * @return
	 */
	private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, DataformResult<String> dataformResult) {
		ServerHttpResponse originalResponse = exchange.getResponse();
		originalResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
		originalResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
		byte[] response = null;
		try {
			response = JSON.toJSONString(dataformResult).getBytes(UTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		DataBuffer buffer = originalResponse.bufferFactory().wrap(response);
		return originalResponse.writeWith(Flux.just(buffer));
	}

	@Override
	public int getOrder() {
		return -100;
	}

}