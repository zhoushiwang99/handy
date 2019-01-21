package com.finals.handy.service;


import com.finals.handy.bean.User;
import com.finals.handy.constant.ResponseCode;
import com.finals.handy.mapper.AccountMapper;
import com.finals.handy.mapper.AddUserMapper;
import com.finals.handy.util.JwtUtil;
import com.finals.handy.util.SendShortMsgUtil;
import org.mindrot.jbcrypt.BCrypt;
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
public class AddUserService {

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    AddUserMapper addUserMapper;

    @Autowired
    AccountService accountService;

    @Autowired
    JwService jwService;

    @Autowired
    RedisService redisService;

    Logger logger = LoggerFactory.getLogger(getClass());


    public static final String Reg_Code_Prefix = "Reg:";

    /**
     * 发送注册时的短信验证码
     *
     * @param
     */
    public Map<String, Object> sendMessage(String phone) {

        Map<String, Object> map = new HashMap<>(16);

        if (addUserMapper.phoneHasRegisted(phone) != 0) {
            map.put("code", ResponseCode.PHONE_HAS_REGISTED.getValue());
            return map;
        }

        String code = SendShortMsgUtil.randomCode();
        boolean result = SendShortMsgUtil.sendRegisterMsg(phone, code);
        if (!result) {
            map.put("code", ResponseCode.SEND_MSG_ERROR.getValue());
            return map;
        } else {
            //发送成功则将验证码存入Redis,有效时间130秒
            redisService.set(Reg_Code_Prefix + phone, code, (long) 130);
            map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
            return map;
        }
    }

    public Map<String, Object> verifyAccount(int userId, String viewState, String password, String checkCode,
                                             String studentId, String psdLen, String jwCookie) {

        HashMap<String, Object> map = new HashMap<>(16);

        /**
         * 用户是否已经注册
         */
        if (addUserMapper.userHasRegisted(userId) == 0) {
            map.put("code", ResponseCode.PHONE_NOT_REGISTED.getValue());
            return map;
        }

        /**
         * 学号是否已被注册
         */
        if (addUserMapper.studentIdHasRegisted(studentId) != 0) {
            map.put("code", ResponseCode.STUDENT_ID_REGISTED.getValue());
            return map;
        }

        Map<String, Object> loginMap = jwService.loginJw(viewState, password, checkCode, studentId, psdLen, jwCookie);
        int loginStatus = (int) loginMap.get("code");
        if (ResponseCode.REQUEST_SUCCEED.getValue() != loginStatus) {
            //如果登录失败
            return loginMap;
        }
        Map<String, Object> infoFromJw = jwService.getStuInfoFromJw(jwCookie);

        int getInfoStatus = (int) infoFromJw.get("code");

        if (getInfoStatus != ResponseCode.REQUEST_SUCCEED.getValue()) {
            //获取学生信息失败
            map.put("code", getInfoStatus);
            return map;
        }

        try {
            accountService.verifyAccount(userId, studentId, infoFromJw);
        } catch (Exception e) {
            logger.error("用户账号解封失败,信息" + e.getMessage()  +"cause:"+e.getCause());
            map.put("code", ResponseCode.SERVER_ERROR.getValue());
            return map;
        }

        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        map.put("username", infoFromJw.get("姓名"));

        String accessToken = JwtUtil.createAccessToken(userId);
        String refreshToken = JwtUtil.createRefreshToken(userId);

        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
        return map;
    }

    public void verifyAccount() {

    }


    /**
     * 从教务系统获取验证码
     *
     * @return
     */
    public Map<String, Object> getJwCode() {
        Map<String, Object> map = jwService.getJwCode();
        return map;
    }

    /**
     * 手机号注册用户
     *
     * @param phone
     * @param password
     * @param code
     * @return
     */
    public Map<String, Object> addUserByPhone(String phone, String password, String code) {

        Map<String, Object> map = new HashMap<>(16);


        /*
        查看验证码是否正确
         */
        if (!redisService.exist(Reg_Code_Prefix + phone)) {
            map.put("code", ResponseCode.PHONE_CODE_ERROR.getValue());
            return map;
        }

        String correctCode = (String) redisService.get(Reg_Code_Prefix + phone);

        if (!correctCode.equals(code)) {
            map.put("code", ResponseCode.PHONE_CODE_ERROR.getValue());
            return map;
        } else {
            String changePsd = BCrypt.hashpw(password, BCrypt.gensalt());

            User user = new User();
            user.setPhone(phone);
            user.setPassword(changePsd);

            addUserMapper.addUserPhone(user);
            addUserMapper.addPhoneLogin(user);
            map.put("userId", user.getId());
            map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
            return map;
        }

    }

}
