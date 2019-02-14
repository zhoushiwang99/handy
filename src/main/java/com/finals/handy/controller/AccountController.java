package com.finals.handy.controller;

import com.finals.handy.service.AccountPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zsw
 */
@RestController
public class AccountController {

    @Autowired
    AccountPhoneService accountPhoneService;

    @GetMapping("/guest/sendResetPsdMsg")
    public Map<String, Object> sendResetPasswordMsg(String phone) {
        Map<String, Object> map = accountPhoneService.sendResetPasswordMsg(phone);
        return map;
    }

    @PostMapping("/guest/resetPhoneByJwVerify")
    public Map<String,Object> resetPhoneByJwVerify(String viewState, String password, String checkCode, String studentId, String psdLen, String jwCookie){
        Map<String, Object> map = accountPhoneService.resetPhoneByJwVerify(viewState, password, checkCode, studentId, psdLen, jwCookie);
        return map;
    }

    /**
     * 验证重置密码时的手机验证码
     * @param phone
     * @param code
     * @return
     */
    @PostMapping("/guest/verifyResetPsdCode")
    public Map<String, Object> verifyResetPsdCode(String phone, String code) {
        Map<String, Object> map = accountPhoneService.verifyResetPsdCode(phone,code);
        return map;
    }


    @PostMapping("/guest/resetPsd")
    public Map<String,Object> resetPsd(String phone,String password){
        Map<String, Object> map = accountPhoneService.resetPsd(phone, password);
        return map;
    }

    @GetMapping("/guest/sendResetPhoneMsg")
    public Map<String,Object> sendResetPhoneMsg(String phone){
        Map<String, Object> map = accountPhoneService.sendResetPhoneMsg(phone);
        return map;
    }

    @PostMapping("/guest/verifyResetPhoneCode")
    public Map<String,Object> verifyResetPhoneCode(String phone,String code){
        Map<String, Object> map = accountPhoneService.verifyResetPhoneCode(phone, code);
        return map;
    }

    @PostMapping("/guest/resetPhone")
    public Map<String,Object> resetPhone(int userId,String phone,String code){
        Map<String, Object> map = accountPhoneService.resetPhone(userId, phone, code);
        return map;
    }

}
