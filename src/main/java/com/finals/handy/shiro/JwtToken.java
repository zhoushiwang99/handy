package com.finals.handy.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author zsw
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    public String getToken(){
        return this.token;
    }
}
