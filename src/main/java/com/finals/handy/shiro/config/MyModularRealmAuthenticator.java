package com.finals.handy.shiro.config;

import com.finals.handy.shiro.JwtToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @author zsw
 */
public class MyModularRealmAuthenticator extends ModularRealmAuthenticator {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("4135416546");
        assertRealmsConfigured();
        Collection<Realm> realms = getRealms();
        Realm realm = null;
        if(authenticationToken instanceof UsernamePasswordToken){
            System.out.println("userpa");
            for (Realm realm1 : realms){
                System.out.println(realm1.getName());
                if(realm1.getName().contains("User")){
                    realm = realm1;
                }
            }
        }
        else if(authenticationToken instanceof JwtToken){
            System.out.println("jwt");
            for (Realm realm1 : realms){
                System.out.println(realm1.getName());
                if(realm1.getName().contains("Jwt")){
                    realm = realm1;
                }
            }
        }
        return doSingleRealmAuthentication(realm,authenticationToken);
    }

    public MyModularRealmAuthenticator() {
        this.setAuthenticationStrategy(new FirstSuccessfulStrategy());
    }
}
