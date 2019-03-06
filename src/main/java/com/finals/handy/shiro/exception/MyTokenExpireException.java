package com.finals.handy.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author zsw
 */
public class MyTokenExpireException extends AuthenticationException {

    public MyTokenExpireException() {
    }

    public MyTokenExpireException(String message) {
        super(message);
    }
}
