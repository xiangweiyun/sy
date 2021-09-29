package com.sy.center.auth.controller;

import com.sy.center.auth.feign.SysLoginLogService;
import com.sy.center.auth.manager.TokenManager;
import com.sy.center.auth.properties.JwtProperties;
import com.sy.center.auth.util.JwtTokenUtil;
import com.sy.center.auth.validator.IReqValidator;
import com.sy.center.auth.vo.AuthRequestVo;
import com.sy.center.common.constant.SysConstant;
import com.sy.center.common.utils.BlankUtils;
import com.sy.center.common.utils.jwt.TokenUtil;
import com.sy.center.framework.constant.CustomRspCon;
import com.sy.center.framework.utils.DataformResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求验证
 *
 * @author xiangwy
 * @date: 2020-12-03 11:39:33
 * @Copyright: Copyright (c) 2020
 * @Company: XWY有限公司
 * @Version: V1.0
 */
@RestController
@RequestMapping("/auth")
@Api(value = "登录验证接口", tags = "登录验证接口")
public class AuthController {
    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 注解切换验证方式 （1，simpleValidator为写死的验证；2，dbValidator为数据库中进行验证）
     */
    @Resource(name = "dbValidator")
    private IReqValidator reqValidator;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 系统用户登录日志接口
     */
    @Autowired
    private SysLoginLogService sysLoginLogService;

    @PostMapping(value = "auth")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authRequestVo", dataType = "authRequestVo", value = "authRequestVo实体的json类型( fans后台系统用户认证方式)<br>{\"userName\":\"admin\",\"password\":\"123456\",\"logintype\":\"1\",\"captcha\":\"9aqd\",\"captchaId\":\"4a9cd3a2-ab45-46c6-9870-b91659c88fd51579329664992\"}")})
    public DataformResult<Map<String, Object>> auth(@RequestBody AuthRequestVo authRequestVo) {
        try {
            DataformResult<Map<String, Object>> auth = reqValidator.validate(authRequestVo);
            final String randomKey = jwtTokenUtil.getRandomKey();
            // 验证成功
            if (CustomRspCon.SUCCESS.getCode() == auth.getCode()) {
                String userName = authRequestVo.getUserName();
                Map<String, Object> userInfo = auth.getData();
                // 系统用户登录
                String id = String.valueOf(userInfo.get("id"));
                String userInfoStr = id + ";;" + userName;
                final String token = jwtTokenUtil.generateToken(userInfoStr, randomKey);
                // 存储到redis或者db中
                tokenManager.createRelationship(authRequestVo.getUserName(), token);
                // 设置认证信息
                Map<String, Object> authData = new HashMap<>(3);
                // 设置过期时间
                authData.put("expiration", jwtProperties.getExpiration());
                authData.put("token", token);
                authData.put("randomKey", randomKey);
                userInfo.put("authData", authData);
                auth.setData(userInfo);
            }
            return auth;
        } catch (Exception e) {
            logger.error("系统异常(检查 redis 或 mysql 是否正常 )！" + e);
            return DataformResult.failure();
        }
    }

    /**
     * 注销
     *
     * @return
     */
    @PostMapping(value = "logout")
    @ApiOperation(value = "用户注销", notes = "用户注销")
    public DataformResult<String> logout() {
        // TODO 操蛋的JWT不能从服务端destroy token， logout目前只能在客户端的cookie 或
        // localStorage/sessionStorage remove token
        // TODO
        // 准备用jwt生成永久的token，再结合redis来实现Logout。具体是把token的生命周期交给redis来管理，jwt只负责生成token
        int code = 1;
        String msg = "用户退出失败！";
        try {
            String authToken = TokenUtil.getToken();
            if (BlankUtils.isNotBlank(authToken)) {
                String userName = TokenUtil.getUsernameFromToken(authToken);
                // 注销成功
                tokenManager.delRelationshipByToken(authToken);
                logger.info("用户退出登录成功！");
                sysLoginLogService.saveLoginLog(userName, "2", "1", "用户退出登录成功！");
                return DataformResult.success();
            } else {
                logger.info("用户退出登录失败！");
                return DataformResult.failure(code, msg);
            }
        } catch (Exception e) {
            msg = "服务内部异常！";
            code = 2;
            logger.error("服务内部异常！" + e);
            return DataformResult.failure(code, msg);
        }
    }

    /**
     * token 校验
     *
     * @return
     * @author: xiangwy
     * @date: 2020-12-03 11:36:31
     */
    @PostMapping(value = "checkToken")
    @ApiOperation(value = "token 校验", notes = "token 校验")
    public DataformResult<String> checkToken(String authToken) {
        CustomRspCon result = CustomRspCon.TOKEN_EXPIRED;
        if (BlankUtils.isNotBlank(authToken)) {
            try {
                String key = tokenManager.getKey(authToken);
                // 根据token获取redis的key
                if (BlankUtils.isNotBlank(key)) {
                    String userInfoStrFromToken = jwtTokenUtil.getUserInfoStrFromToken(authToken);
                    return DataformResult.success(userInfoStrFromToken);
                }
            } catch (Exception e) {
                logger.error("token失败认证！", e);
            }
        }
        return DataformResult.failure(result.getCode(), result.getMsg());

    }

    /**
     * 验证是否登录过
     *
     * @return
     * @author: xiangwy
     * @date: 2020-12-03 11:38:39
     */
    @PostMapping(value = "isLogined")
    @ApiOperation(value = "是否登录", notes = "是否登录")
    public DataformResult<String> isLogined(HttpServletRequest request) {
        int code = 705;
        String msg = "未登录！";
        // 从头部获取token信息
        String requestHeader = request.getHeader(SysConstant.AUTH_KEY);
        if (requestHeader != null && requestHeader.startsWith(SysConstant.AUTH_PRE_B)) {
            String authToken = requestHeader.substring(7);
            if (BlankUtils.isNotBlank(authToken)) {
                try {
                    String key = tokenManager.getKey(authToken);
                    // 是否进行了认证操作
                    // 不为空的时候则进行了验证操作
                    if (BlankUtils.isNotBlank(key)) {
                        String userInfoStrFromToken = jwtTokenUtil.getUserInfoStrFromToken(authToken);
                        return DataformResult.success(userInfoStrFromToken);
                    }
                } catch (Exception e) {
                    logger.error("token失败认证！", e);
                }
            }
        }
        return DataformResult.failure(code, msg);
    }

}
