package com.finals.handy.shiro.realm;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.finals.handy.mapper.UserLoginMapper;
import com.finals.handy.shiro.JwtToken;
import com.finals.handy.shiro.exception.MyTokenErrorException;
import com.finals.handy.shiro.exception.MyTokenExpireException;
import com.finals.handy.util.JwtUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author zsw
 */
public class JwtRealm extends AuthorizingRealm {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserLoginMapper userLoginMapper;


    /**
     * 限定这个Realm只支持我们自定义的JWT Token
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限认证。。。。");
        String accessToken = (String) principals.getPrimaryPrincipal();
        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
        String phone = claimMap.get("phone").asString();

        System.out.println("phone is..." + phone);
        //2.从数据库获取用户的角色信息，并将角色信息赋给用户
        Set<String> roles = new HashSet<>();
        String role = userLoginMapper.getRoleByPhone(phone);
        System.out.println("角色为:" + role);
        roles.add(role);

        //创建 SimpleAuthoizationInfo，并设置其 roles 属性
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        //返回
        return info;

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("身份认证。。。。");
        JwtToken jwtToken = (JwtToken) token;
        String accessToken = jwtToken.getToken();
        try {
            JwtUtil.verifyAccessToken(accessToken);
        } catch (TokenExpiredException e) {
            throw new MyTokenExpireException("token过期");
        } catch (Exception e) {
            throw new MyTokenErrorException("token错误");
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(accessToken, accessToken, "JwtRealm");
        return authenticationInfo;
    }
}
