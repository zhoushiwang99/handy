package com.finals.handy.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author zsw
 */
public class SendShortMsgUtil {

    static Logger logger = LoggerFactory.getLogger(SendShortMsgUtil.class);

    public static boolean sendRegisterMsg(String phone, String code) {

        int appId = 1400181857;

        //申请到的AppKey
        String appKey = "sdandksk2546asdaodsisd";
        //短信模板 id
        int templateId = 269510;
        String smsSign = "顺手APP";

        String[] params = {code, "2"};

        SmsSingleSender ssender = new SmsSingleSender(appId, appKey);
        try {
            SmsSingleSenderResult result = ssender.sendWithParam("86", phone, templateId, params, smsSign, "", "");

            JSONObject jsonObject = JSON.parseObject(String.valueOf(result));

            int status = (int) jsonObject.get("result");
            if(status != 0){
                return false;
            }
            return true;
        } catch (HTTPException | IOException e) {
            logger.warn("发送短信失败,信息:" + e.getMessage());
            return false;
        }
    }


    /**
     * 生成六位随机验证码
     *
     * @return
     */
    public static String randomCode() {
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        return String.valueOf(code);
    }


}
