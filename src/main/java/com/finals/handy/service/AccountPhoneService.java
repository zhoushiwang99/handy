package com.finals.handy.service;

import com.finals.handy.constant.ResponseCode;
import com.finals.handy.mapper.AccountMapper;
import com.finals.handy.mapper.AddUserMapper;
import com.finals.handy.mapper.UserLoginMapper;
import com.finals.handy.util.SendShortMsgUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zsw
 */
@Service
public class AccountPhoneService {

    @Autowired
    UserLoginMapper userLoginMapper;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    AddUserMapper addUserMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    JwService jwService;

    private static final String RESET_PSD_CACHE_PREFIX = "resetpsd:code";

    private static final String RESET_PSD_PHONE_PREFIX = "resetphone:number";

    private static final String RESET_PHONE_CODE_PREFIX = "resetphone:code:";


    private static final String RESET_PHONE_USER_ID_PREFIX = "resetphone:userId:";


    /**
     * 重置手机号
     * @param phone
     * @param code
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> resetPhoneByJw(int userId,String phone,String code){

        Map<String,Object> map = new HashMap<>(16);

        if(!redisService.exist(RESET_PHONE_USER_ID_PREFIX+userId)){
           map.put("code",ResponseCode.ILLEGAL_REQUEST.getValue());
           return map;
       }

        redisService.remove(RESET_PHONE_USER_ID_PREFIX+userId);

        String correctCode = (String) redisService.get(AddUserService.Reg_Code_Prefix+phone);


        if(correctCode == null || !correctCode.equals(code)){
            map.put("code",ResponseCode.PHONE_CODE_ERROR.getValue());
            return map;
        }

        if(addUserMapper.phoneHasRegisted(phone) != 0){
            map.put("code",ResponseCode.PHONE_HAS_REGISTED.getValue());
            return map;
        }

        accountMapper.resetPhoneInfo(userId,phone);

        accountMapper.resetPhoneLogin(userId,phone);

        map.put("code",ResponseCode.REQUEST_SUCCEED.getValue());
        return map;
    }


    /**
     * 学号重置手机号时，先登录教务系统验证
     *
     * @param viewState
     * @param password
     * @param checkCode
     * @param studentId
     * @param psdLen
     * @param cookie
     * @return
     */
    public Map<String, Object> resetPhoneByJwVerify(String viewState, String password, String checkCode, String studentId, String psdLen, String cookie) {

        if (userLoginMapper.studentIdExist(studentId) != 1) {
            Map<String, Object> map = new HashMap<>(16);
            map.put("code", ResponseCode.XH_NOT_REGESIT.getValue());
            return map;
        }

        Map<String, Object> map = jwService.loginJw(viewState, password, checkCode, studentId, psdLen, cookie);
        int loginStatus = (int) map.get("code");
        if (loginStatus != ResponseCode.REQUEST_SUCCEED.getValue()) {
            return map;
        }

        int userId = userLoginMapper.getUserIdByStudentId(studentId);
        redisService.set(RESET_PHONE_USER_ID_PREFIX + userId, String.valueOf(userId), (long) 610);

        map.put("userId",userId);
        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        return map;
    }

    public Map<String, Object> sendResetPasswordMsg(String phone) {
        HashMap<String, Object> map = new HashMap<>(16);

        if (userLoginMapper.phoneExist(phone) != 1) {
            map.put("code", ResponseCode.PHONE_NOT_REGISTED.getValue());
            return map;
        }
        if (userLoginMapper.isUserBlackByPhone(phone)) {
            map.put("code", ResponseCode.USER_IS_BLACK.getValue());
            return map;
        }

        String code = SendShortMsgUtil.randomCode();
        boolean result = SendShortMsgUtil.sendResetPsdMsg(phone, code, 2);

        if (!result) {
            map.put("code", ResponseCode.SEND_MSG_ERROR.getValue());
            return map;
        }

        redisService.set(RESET_PSD_CACHE_PREFIX + phone, code, (long) 130);

        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        return map;
    }


    public Map<String, Object> verifyResetPsdCode(String phone, String code) {
        Map<String, Object> map = new HashMap<>(16);

        String correctCode = (String) redisService.get(RESET_PSD_CACHE_PREFIX + phone);

        redisService.remove(RESET_PSD_CACHE_PREFIX + phone);

        if (correctCode == null || !correctCode.equals(code)) {
            map.put("code", ResponseCode.PHONE_CODE_ERROR.getValue());
            return map;
        }

        redisService.set(RESET_PSD_PHONE_PREFIX + phone, phone, (long) 610);

        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        return map;
    }

    public Map<String, Object> resetPsd(String phone, String password) {
        Map<String, Object> map = new HashMap<>(16);
        if (!redisService.exist(RESET_PSD_PHONE_PREFIX + phone)) {
            map.put("code", ResponseCode.ILLEGAL_REQUEST.getValue());
            return map;
        }
        redisService.remove(RESET_PSD_PHONE_PREFIX + phone);

        String bcryptPassword = BCrypt.hashpw(password,BCrypt.gensalt());


        accountMapper.resetPasswordByPhone(phone, bcryptPassword);
        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        return map;
    }

    public Map<String, Object> sendResetPhoneMsg(String phone) {
        HashMap<String, Object> map = new HashMap<>(16);

        if (userLoginMapper.phoneExist(phone) != 1) {
            map.put("code", ResponseCode.PHONE_NOT_REGISTED.getValue());
            return map;
        }
        if (userLoginMapper.isUserBlackByPhone(phone)) {
            map.put("code", ResponseCode.USER_IS_BLACK.getValue());
            return map;
        }

        String code = SendShortMsgUtil.randomCode();
        boolean result = SendShortMsgUtil.sendResetPhoneMsg(phone, code, 2);

        if (!result) {
            map.put("code", ResponseCode.SEND_MSG_ERROR.getValue());
            return map;
        }

        redisService.set(RESET_PHONE_CODE_PREFIX + phone, code, (long) 130);

        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        return map;
    }


    public Map<String, Object> verifyResetPhoneCode(String phone, String code) {
        Map<String, Object> map = new HashMap<>(16);

        String correctCode = (String) redisService.get(RESET_PHONE_CODE_PREFIX + phone);

        if (correctCode == null || !correctCode.equals(code)) {
            map.put("code", ResponseCode.PHONE_CODE_ERROR.getValue());
            return map;
        }

        redisService.remove(RESET_PHONE_CODE_PREFIX + phone);

        int userId = accountMapper.getUserIdByPhone(phone);

        redisService.set(RESET_PHONE_USER_ID_PREFIX + userId, String.valueOf(userId), (long) 610);

        map.put("userId",userId);
        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> resetPhone(int userId, String phone, String code) {

        Map<String, Object> map = new HashMap<>(16);

        String correctCode = (String) redisService.get(AddUserService.Reg_Code_Prefix + phone);

        if (correctCode == null || !correctCode.equals(code)) {
            map.put("code", ResponseCode.PHONE_CODE_ERROR.getValue());
            return map;
        }

        redisService.remove(AddUserService.Reg_Code_Prefix + phone);

        if (addUserMapper.phoneHasRegisted(phone) != 0) {
            map.put("code", ResponseCode.PHONE_HAS_REGISTED.getValue());
            return map;
        }

        //redis不存在useId,可能是非法请求，也可能是缓存过期
        if (!redisService.exist(RESET_PHONE_USER_ID_PREFIX + userId)) {
            map.put("code", ResponseCode.ILLEGAL_REQUEST.getValue());
            return map;
        }

        accountMapper.resetPhoneInfo(userId, phone);
        accountMapper.resetPhoneLogin(userId, phone);
        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        return map;
    }

}
