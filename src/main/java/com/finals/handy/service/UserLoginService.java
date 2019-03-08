package com.finals.handy.service;

import com.finals.handy.bean.User;
import com.finals.handy.constant.ResponseCode;
import com.finals.handy.mapper.AddUserMapper;
import com.finals.handy.mapper.UserLoginMapper;
import com.finals.handy.util.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zsw
 */
@Service
public class UserLoginService {

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * redis存放token的前缀
     */
    public static final String USER_TOKEN_PREFIX = "user:token:";

    /**
     * 设置token过期时间一周
     */
    public static final long TOKEN_EXPIRE_TIME = 60 * 60 * 24 * 7;

    @Autowired
    private UserLoginMapper userLoginMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    JwService jwService;

    @Autowired
    AddUserMapper addUserMapper;

    public Map<String, Object> userPhoneLogin(String phone, String password) {
        Map<String, Object> map = new HashMap<>(16);
        Subject user = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
        try {
            user.login(token);
            User user1 = userLoginMapper.getUserByPhone(phone);

            //将用户的id返回
            map.put("userId", user1.getId());

            String accessToken = JwtUtil.createAccessToken(user1.getId());

            String refreshToken = JwtUtil.createRefreshToken(user1.getId());

            map.put("accessToken", accessToken);
            map.put("refreshToken", refreshToken);

            redisService.set("user:token:" + user1.getId(),refreshToken, TOKEN_EXPIRE_TIME);
            map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
            return map;
        } catch (UnknownAccountException e) {
            map.put("code", ResponseCode.USER_NOT_EXIST.getValue());

        } catch (IncorrectCredentialsException e) {
            map.put("code", ResponseCode.PASSWORD_ERROR.getValue());

        } catch (LockedAccountException e) {
            int userId = addUserMapper.getUserIdByPhone(phone);
            map.put("userId",userId);
            map.put("code", ResponseCode.USER_NOT_VERIFY.getValue());

        } catch (DisabledAccountException e) {
            map.put("code", ResponseCode.USER_IS_BLACK.getValue());

        } catch (Exception e) {
            map.put("code", ResponseCode.SERVER_ERROR.getValue());
            e.printStackTrace();
        }

        return map;
    }

    public Map<String, Object> xhLogin(String viewState, String password, String checkCode,
                                       String studentId, String psdLen, String jwCookie) {
        Map<String, Object> map = new HashMap<>(16);

        if (userLoginMapper.xhExist(studentId) == 0) {
            map.put("code", ResponseCode.XH_NOT_REGESIT.getValue());
            return map;
        }

        int userId = userLoginMapper.getUserIdByStudentId(studentId);

        if (userLoginMapper.isUserBlackByStudentId(studentId)) {
            map.put("code", ResponseCode.USER_IS_BLACK.getValue());
            return map;
        }
        if (!userLoginMapper.isUserVerifyByStudentId(studentId)) {
            map.put("code", ResponseCode.USER_NOT_VERIFY.getValue());
            map.put("userId",userLoginMapper.getUserIdByStudentId(studentId));
            return map;
        }
        map = jwService.loginJw(viewState, password, checkCode, studentId, psdLen, jwCookie);
        int status = (int) map.get("code");
        if (ResponseCode.REQUEST_SUCCEED.getValue() != status) {
            return map;
        }

        String accessToken = JwtUtil.createAccessToken(userId);

        String refreshToken = JwtUtil.createRefreshToken(userId);

        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);

        redisService.set(USER_TOKEN_PREFIX + userId,refreshToken,TOKEN_EXPIRE_TIME);

        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        return map;

    }
}
