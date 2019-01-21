package com.finals.handy.shiro.realm;

import com.finals.handy.mapper.UserLoginMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zsw
 * 用户请求时验证token及给用户设置身份和权限
 */
public class UserRealm extends AuthorizingRealm {


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    public UserRealm() {
        this.setCredentialsMatcher((token, info) -> {
            UsernamePasswordToken userToken = (UsernamePasswordToken) token;
            //要验证的明文密码
            String plaintext = new String(userToken.getPassword());
            //数据库中的加密后的密文
            String hashed = info.getCredentials().toString();
            return BCrypt.checkpw(plaintext, hashed);
        });
    }

    @Autowired
    UserLoginMapper userLoginMapper;

    /**
     * 获取用户身份认证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("----身份认证方法----");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        //从 UsernamePasswordToken中来获取phone
        String phone = usernamePasswordToken.getUsername();
        System.out.println("phone" + phone);
        if(userLoginMapper.phoneExist(phone) != 1){
            System.out.println("账号未知");
            throw new UnknownAccountException("手机号未注册");
        }
        if(!userLoginMapper.isUserVerifyByPhone(phone)){
            throw new LockedAccountException("用户未实名");
        }
        if(userLoginMapper.isUserBlackByPhone(phone)) {
            throw new DisabledAccountException("黑名单");
        }
        //从数据库获取用户的信息
        Object principal = phone;
        Object credentials = userLoginMapper.getPhonePassword(phone);
        String realmName = getName();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,credentials,realmName);
        return info;
    }

    /**
     * 当需要检测用户角色或权限的时候调用此方法，例如checkRole,checkPermission之类的
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
         System.out.println("...权限认证方法...");
        //1. 从PrincipalCollection 中来获取登录用户的信息
        String phone = (String) principals.getPrimaryPrincipal();

        System.out.println("phone is..." + phone);
        //2.从数据库获取用户的角色信息，并将角色信息赋给用户
        Set<String> roles = new HashSet<>();
        String role = userLoginMapper.getRoleByPhone(phone);
        System.out.println("角色为:"+role);
        roles.add(role);

        //创建 SimpleAuthoizationInfo，并设置其 roles 属性
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        //返回
        return info;
    }



}
