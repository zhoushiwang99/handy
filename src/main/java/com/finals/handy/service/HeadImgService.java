package com.finals.handy.service;

import com.auth0.jwt.interfaces.Claim;
import com.finals.handy.constant.ResponseCode;
import com.finals.handy.mapper.AccountMapper;
import com.finals.handy.mapper.UserHeadImgMapper;
import com.finals.handy.util.JwtUtil;
import com.finals.handy.vo.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author zsw
 */
@Service
public class HeadImgService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserHeadImgMapper userHeadImgMapper;

    @Autowired
    AccountMapper accountMapper;

    private static String DEFAULT_HEAD_IMG = "/root/handy/user/defaultHeadImg.png";
//    private static String DEFAULT_HEAD_IMG = "C:\\Users\\zhousw\\Desktop\\defaultHeadImg.png";

    private static final String USER_HEAD_IMG_NAME_PREFIX = "headerImg";


    /**
     * 限制图片大小
     */
    private static final long MAX_IMG_SIZE = 1024 * 1024 * 8;

    private List<String> imgSuffix = new ArrayList<>(Arrays.asList(".jpg", ".png", ".jpeg"));

    public Map<String, Object> getUserScore(String accessToken) {
        Map<String, Object> map = new HashMap<>(16);
        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
        String userId = claimMap.get("userId").asString();

        if (accountMapper.ifUserHasScoreByUserId(userId) != 1) {
            accountMapper.addUserScoreInfo(userId);
            return map;
        } else {
            int publishNum = accountMapper.getUserPublishOrderNumByUserId(userId);
            int receiveNum = accountMapper.getReceiveOrderNumByUserId(userId);
            int sumScore = accountMapper.getScoreByUserId(userId);
            double score;
            if (receiveNum != 0) {
                score = (sumScore * 1.0) / receiveNum;
                BigDecimal bg = new BigDecimal(score).setScale(2, RoundingMode.UP);
                score = bg.doubleValue();
            } else {
                score = 0;
            }

            map.put("publishNum", publishNum);
            map.put("receiveNum", receiveNum);
            map.put("score", score);

            map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
            return map;

        }


    }


    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> setHeadImg(String accessToken, MultipartFile headImg) {

        Map<String, Object> map = new HashMap<>(16);
        //获取文件名
        String fileName = headImg.getOriginalFilename();
        //后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(suffixName);
        if (headImg.isEmpty() || headImg.getSize() > MAX_IMG_SIZE || !imgSuffix.contains(suffixName)) {
            map.put("code", ResponseCode.PARAM_ILLEGAL.getValue());
            map.put("msg", "头像参数错误");
            return map;
        }
        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
        String userId = claimMap.get("userId").asString();
        String filePath = "/root/handy/user/headImg/";

        String path = filePath + userId + suffixName;
//        String filePath = "C:\\Users\\zhousw\\Desktop\\";
        try {
            //将图片保存到服务器
            headImg.transferTo(new File(path));
            if (userHeadImgMapper.ifUserHasSetHeadImg(userId) == 0) {
                userHeadImgMapper.firstHeadImgByUserId(userId, path);
            } else {
                System.out.println("更新头像");
                userHeadImgMapper.updateHeadImgByUserId(userId, path);
            }
            map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("code", ResponseCode.PARAM_ILLEGAL.getValue());
            return map;
        }
    }

    public Map<String, Object> getHeadImg(String accessToken) {
        Map<String, Object> map = new HashMap<>(16);
        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
        String userId = claimMap.get("userId").asString();
        String imgPath = userHeadImgMapper.getHeadImgPathByUserId(userId);
        byte[] bytes;
        System.out.println("path:" + imgPath);
        if (imgPath != null) {
            File file = new File(imgPath);
            FileInputStream fileInputStream;
            //在服务器寻找头像图片
            try {
                fileInputStream = new FileInputStream(file);
                bytes = new byte[fileInputStream.available()];
                fileInputStream.read(bytes);
                System.out.println("找到了图片");
                fileInputStream.close();
                map.put("headImg", bytes);
                map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
                return map;
            } catch (FileNotFoundException e) {
                //没找到图片则使用默认图片
                try {
                    fileInputStream = new FileInputStream(DEFAULT_HEAD_IMG);
                    bytes = new byte[fileInputStream.available()];
                    fileInputStream.read(bytes);
                    System.out.println("没找到图片");
                    fileInputStream.close();
                    map.put("headImg", bytes);
                    map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
                    return map;
                } catch (FileNotFoundException e1) {
                    logger.error("默认用户头像图片丢失");
                    map.put("msg","用户默认头像图片丢失");
                    map.put("code", ResponseCode.SERVER_ERROR.getValue());
                    return map;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    map.put("code", ResponseCode.SERVER_ERROR.getValue());
                    return map;
                }
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code", ResponseCode.SERVER_ERROR.getValue());
                return map;
            }
        } else {
            try {
                //没找到则使用默认图片
                FileInputStream fileInputStream = new FileInputStream(DEFAULT_HEAD_IMG);
                bytes = new byte[fileInputStream.available()];
                fileInputStream.read(bytes);
                fileInputStream.close();
                System.out.println("没找到图片");

                map.put("headImg", bytes);
                map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
                return map;
            } catch (FileNotFoundException e1) {
                logger.error("默认用户头像图片丢失");
                map.put("code", ResponseCode.SERVER_ERROR.getValue());
                return map;
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code", ResponseCode.SERVER_ERROR.getValue());
                return map;
            }
        }

    }

    public Map<String, Object> getUserInfo(String accessToken) {
        Map<String, Object> map = new HashMap<>(16);
        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
        String userId = claimMap.get("userId").asString();
        UserInfo info = accountMapper.getUserInfoByUserId(userId);
        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        map.put("userInfo", info);
        return map;
    }


}
