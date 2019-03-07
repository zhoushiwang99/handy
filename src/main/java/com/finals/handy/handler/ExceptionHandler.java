package com.finals.handy.handler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.finals.handy.constant.ResponseCode;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zsw
 * 异常处理类
 */
@RestControllerAdvice
public class ExceptionHandler {

    /**
     * 捕获shiro的无权限异常
     * @param
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(AuthorizationException.class)
    public Map<String,Object> noAuthorizationHandle(){
        HashMap<String, Object> map = new HashMap<>(16);
        map.put("code", ResponseCode.NOT_PERMIT.getValue());
        return map;
    }

    /**
     * 捕获token过期异常
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(TokenExpiredException.class)
    public Map<String,Object> tokenExpireHandle(){
        HashMap<String, Object> map = new HashMap<>(16);
        map.put("code", ResponseCode.TOKEN_EXPIRE.getValue());
        return map;
    }

    /**
     * token错误的异常
     * @param
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({JWTDecodeException.class, SignatureVerificationException.class})
    public Map<String,Object> tokenErrorHandle(){
        HashMap<String, Object> map = new HashMap<>(16);
        map.put("code", ResponseCode.TOKEN_ERROR.getValue());
        return map;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ConstraintViolationException.class, BindException.class})
    public Map<String,Object> validationExceptionHandler(){
        HashMap<String, Object> map = new HashMap<>(16);

        System.out.println("参数不合法");
        map.put("code",ResponseCode.PARAM_ILLEGAL.getValue());
        return map;
    }


}
