package com.finals.handy.service;

import com.finals.handy.constant.ResponseCode;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zsw
 * 与教务系统之间的service
 */

@Service
public class JwService {


    private final String SUCCESS_LOGIN = "正在加载权限数据...";

    private final String CODE_ERROR = "验证码错误！ 登录失败！";

    private final String PASSWORD_ERROR = "帐号或密码不正确！请重新输入。";


    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 从教务系统获取验证码
     *
     * @return
     */
    public Map<String, Object> getJwCode() {
        Map<String, Object> map = new HashMap<>(16);
        OkHttpClient client = new OkHttpClient();

        Request validateRequest = new Request.Builder()
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0")
                .header("Accept", "*/*")
                .header("Accept-Language", "zh-CN,en-US;q=0.8,zh;q=0.7,zh-TW;q=0.5,zh-HK;q=0.3,en;q=0.2")
                .header("Referer", "http://xk.csust.edu.cn/_data/index_LOGIN.aspx")
                .header("Connection", "keep-alive")
                .url("http://xk.csust.edu.cn/sys/ValidateCode.aspx").build();
        Call validateCall = client.newCall(validateRequest);
        try {
            Response validateResponse = validateCall.execute();

            Headers responseHeaders = validateResponse.headers();
            List<String> cookies = responseHeaders.values("Set-Cookie");
            String session = cookies.get(0);
            String jwCookie = session.substring(0, session.indexOf(";"));
            byte[] validateImg = validateResponse.body().bytes();
            map.put("jwCookie", jwCookie);
            map.put("validateImg", validateImg);
            ByteToFile(validateImg);

            Request getLoginRequest = new Request.Builder()
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:64.0) Gecko/20100101 Firefox/64.0")
                    .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .addHeader("Accept-Language", "zh-CN,en-US;q=0.8,zh;q=0.7,zh-TW;q=0.5,zh-HK;q=0.3,en;q=0.2")
                    .addHeader("Referer", "http://xk.csust.edu.cn/_data/index_LOGIN.aspx")
                    .addHeader("Upgrade-Insecure-Requests", "1")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Host", "xk.csust.edu.cn")
                    .addHeader("Cookie", jwCookie)
                    .url("http://xk.csust.edu.cn/_data/index_LOGIN.aspx").build();

            Call getCall = client.newCall(getLoginRequest);

            Response getLoginResponse = getCall.execute();
            String loginBody = getLoginResponse.body().string();
            Document doc = Jsoup.parse(loginBody);
            Elements elements = doc.getElementsByTag("input");
            for (Element element : elements) {
                if ("__VIEWSTATE".equals(element.attr("name"))) {
                    map.put("viewState", element.attr("value"));
                    break;
                }
            }
            map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        } catch (Exception e) {
            logger.warn("从教务系统获取验证码异常，异常信息:" + e.getMessage() + "异常类型" + e.getClass());
            map.put("code", ResponseCode.JW_SERVER_ERROR.getValue());
        }
        return map;
    }

    /**
     * 教务系统登录
     *
     * @return code 0 登录成功
     */
    public Map<String, Object> loginJw(String viewState, String password, String checkCode, String studentId, String psdLen, String cookie) {

        System.out.println(password);
        System.out.println(studentId);
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType type = MediaType.parse("application/x-www-form-urlencoded");
        Map<String, Object> map = new HashMap<>(16);


        String encodeViewState = null;
        try {
            encodeViewState = URLEncoder.encode(viewState, "GBK");
        } catch (UnsupportedEncodingException e) {
            map.put("code", ResponseCode.SERVER_ERROR.getValue());
            return map;
        }


        String sb = ("__VIEWSTATE" + "=" + encodeViewState + "&") +
                "__VIEWSTATEGENERATOR" + "=" + "CAA0A5A7" + "&" +
                "pcInfo" + "=" + "Mozilla%2F5.0+%28Windows+NT+10.0%3B+Win64%3B+x64%3B+rv%3A64.0%29+Gecko%2F20100101+Firefox%2F64.0Windows+NT+10.0%3B+Win64%3B+x645.0+%28Windows%29+SN%3ANULL" + "&" +
                "typeName" + "=" + "%D1%A7%C9%FA" + "&" +
                "dsdsdsdsdxcxdfgfg" + "=" + password + "&" +
                "fgfggfdgtyuuyyuuckjg" + "=" + checkCode + "&" +
                "Sel_Type" + "=" + "STU" + "&" +
                "txt_asmcdefsddsd" + "=" + studentId + "&" +
                "txt_pewerwedsdfsdff" + "=" + "" + "&" +
                "txt_sdertfgsadscxcadsads" + "=" + "" + "&" +
                "sbtState" + "=" + "" + "&" +
                "txt_mm_expression" + "=" + "12" + "&" +
                "txt_mm_length" + "=" + psdLen + "&" +
                "txt_mm_userzh" + "=" + "0" + "&" +
                "txt_mm_lxpd" + "=" + "";
        RequestBody body = RequestBody.create(type, sb);

        Request loginRequest = new Request.Builder()
                .url("http://xk.csust.edu.cn/_data/index_LOGIN.aspx")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Host", "xk.csust.edu.cn")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:64.0) Gecko/20100101 Firefox/64.0")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .addHeader("Accept-Language", "zh-CN,en-US;q=0.8,zh;q=0.7,zh-TW;q=0.5,zh-HK;q=0.3,en;q=0.2")
                .addHeader("Referer", "http://xk.csust.edu.cn/_data/index_LOGIN.aspx")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Content-Length", "551")
                .addHeader("Connection", "keep-alive")
                .addHeader("Cookie", cookie)
                .addHeader("Upgrade-Insecure-Requests", "1")
                .post(body)
                .build();

        Response jwResponse = null;
        try {
            jwResponse = okHttpClient.newCall(loginRequest).execute();
        } catch (Exception e) {
            logger.error("教务系统登录异常，异常类型" + e.getClass() + "异常信息" + e.getMessage());
            map.put("code", ResponseCode.JW_SERVER_ERROR.getValue());
            return map;
        }

        String loginStatus = null;
        try {
            loginStatus = jwResponse.body().string();
        } catch (IOException e) {
            logger.error("解析教务系统返回数据异常，异常类型" + e.getClass() + e.getMessage());
            map.put("code", ResponseCode.JW_SERVER_ERROR.getValue());
            return map;
        }
        Document statusDoc = Jsoup.parse(loginStatus);
        String status = statusDoc.select("#divLogNote").text();
        System.out.println(status);
        if (SUCCESS_LOGIN.equals(status)) {
            map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        } else if (CODE_ERROR.equals(loginStatus)) {
            map.put("code", ResponseCode.JW_CODE_ERROR.getValue());
        } else if (PASSWORD_ERROR.equals(status)) {
            map.put("code", ResponseCode.JW_PASSWORD_ERROR.getValue());
        } else {
            map.put("code", ResponseCode.JW_SERVER_ERROR.getValue());
            logger.error("教务系统登录返回异常，状态为:" + status);
        }

        return map;
    }

    public Map<String, Object> getStuInfoFromJw(String cookie) {
        Map<String, Object> map = new HashMap<>(16);

        OkHttpClient okHttpClient = new OkHttpClient();

        Request infoRequest = new Request.Builder()
                .header("Cookie", cookie)
                .url("http://xk.csust.edu.cn/xsxj/Stu_MyInfo_RPT.aspx").build();

        Call infoCall = okHttpClient.newCall(infoRequest);


        Response infoResponse = null;
        try {
            infoResponse = infoCall.execute();
        } catch (Exception e) {
            map.put("code", ResponseCode.JW_SERVER_ERROR.getValue());
            logger.error("获取学生学籍信息异常，异常类型:" + e.getClass() + "异常信息:" + e.getMessage());
            return map;
        }

        byte[] b;
        String str;

        try {
            b = infoResponse.body().bytes();
            str = new String(b, "GB2312");
        } catch (Exception e) {
            logger.error("获取学生学籍信息异常，异常类型:" + e.getClass() + "异常信息:" + e.getMessage());
            map.put("code", ResponseCode.JW_SERVER_ERROR.getValue());
            return map;
        }

        Document doc = Jsoup.parse(str);
        Elements tds = doc.select("table").select("td");
        String info = "学号,姓名,身份证号,入学时间,性别,行政班级,学习年限";
        for (int i = 0; i < tds.size() - 1; i++) {
            String text = tds.get(i).text();

            text = text.replaceAll("[\u2002\\s]", "");

            if (!"".equals(text) && info.contains(text)) {
                map.put(text, tds.get(i + 1).text().replaceAll("[\u2002\\s]", ""));
            }
        }
        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        return map;
    }

    private static void ByteToFile(byte[] bytes) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        BufferedImage bi1 = ImageIO.read(bais);
        try {
            File w2 = new File("C:\\Users\\zhousw\\Desktop\\121.jpg");
            //可以是jpg,png,gif格式
            ImageIO.write(bi1, "jpg", w2);
            //不管输出什么格式图片，此处不需改动
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bais.close();
        }
    }
}
