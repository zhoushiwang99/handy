package com.finals.handy.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author zsw
 */
public class MyTokenErrorException extends AuthenticationException {
    public MyTokenErrorException() {
    }

    public MyTokenErrorException(String message) {
        super(message);
    }
}
