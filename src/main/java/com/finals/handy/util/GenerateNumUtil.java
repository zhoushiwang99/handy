package com.finals.handy.util;

import com.finals.handy.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author zsw
 * 生成订单号类
 * 单例模式
 */
@Component
public class GenerateNumUtil {

    @Autowired
    RedisService redisService;

    /**
     * 代购食物订单前缀
     */
    public static String FOOD_Order_PREFIX = "1";

    /**
     * 带领快递订单前缀
     */
    public static String EXPRESS_ORDER_PREFIX = "2";

    /**
     * 代购特产订单前缀
     */
    public static String SPECIALTY_ORDER_PREFIX = "3";

    /**
     * 闲置资源订单前缀
     */
    public static String IDLE_RESOURCES_ORDER_PREFIX = "4";

/*
    private GenerateNumUtil() {
    }

    private static volatile GenerateNumUtil generateNumUtil = null;

    public static GenerateNumUtil getGenerateNumInstance() {
        if (generateNumUtil == null) {
            synchronized (GenerateNumUtil.class) {
                if (generateNumUtil == null) {
                    generateNumUtil = new GenerateNumUtil();
                }
            }
        }
        return generateNumUtil;
    }
*/

    /**
     * 每日代购食物订单数量
     */
    private static final String FOOD_ORDER_NUM_PREFIX = "food:order:num";

    /**
     * 每日带领快递订单数量
     */
    private static final String EXPRESS_ORDER_NUM_PREFIX = "express:order:num";

    /**
     * 每日代购特产订单数量
     */
    private static final String SPECIALTY_ORDER_NUM_PREFIX = "specialty:order:num";

    /**
     * 每日闲置资源订单数量
     */
    private static final String IDLE_ORDER_NUM_PREFIX = "idle:order:num";


    /**
     * 格式化时间字符串
     */
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    /**
     * 记录上一次的时间，用来判断是否需要递增每日订单数量
     */
    private static String now = null;

    /**
     * 获取当前日期
     *
     * @return
     */
    private static String getNowDate() {
        return sdf.format(new Date());
    }


    private static long secondsToTomorrow() {
        Calendar curDate = Calendar.getInstance();
        Calendar tommorowDate = new GregorianCalendar(curDate
                .get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate
                .get(Calendar.DATE) + 1, 0, 0, 0);
        return (tommorowDate.getTimeInMillis() - curDate.getTimeInMillis()) / 1000;
    }

    /**
     * 生成带饭订单号
     *
     * @return
     */
    public synchronized String getFoodOrderNumber() {
        String nowDate = getNowDate();

        long foodCount;

        if (!redisService.exist(FOOD_ORDER_NUM_PREFIX)) {
            long secondsToTomorrow = secondsToTomorrow();
            redisService.set(FOOD_ORDER_NUM_PREFIX, "1", secondsToTomorrow);
            foodCount = 1;
        } else {
            foodCount = redisService.incr(FOOD_ORDER_NUM_PREFIX, 1);
        }
        String foodOrderNumber = FOOD_Order_PREFIX + nowDate + foodCount;
        return foodOrderNumber;
    }

    /**
     * 生成代领快递订单号
     * @return
     */
    public synchronized String getExpressOrderNumber() {
        String nowDate = getNowDate();

        long expressCount;

        if (!redisService.exist(EXPRESS_ORDER_NUM_PREFIX)) {
            long secondsToTomorrow = secondsToTomorrow();
            redisService.set(EXPRESS_ORDER_NUM_PREFIX, "1", secondsToTomorrow);
            expressCount = 1;
        } else {
            expressCount = redisService.incr(EXPRESS_ORDER_NUM_PREFIX, 1);
        }
        String expressOrderNumber = EXPRESS_ORDER_PREFIX + nowDate + expressCount;
        return expressOrderNumber;
    }

    /**
     * 获取代购特产订单号
     * @return
     */
    public synchronized String getSpecialtyOrderNumber() {
        String nowDate = getNowDate();

        long specialtyCount;

        if (!redisService.exist(SPECIALTY_ORDER_NUM_PREFIX)) {
            long secondsToTomorrow = secondsToTomorrow();
            redisService.set(SPECIALTY_ORDER_NUM_PREFIX, "1", secondsToTomorrow);
            specialtyCount = 1;
        } else {
            specialtyCount = redisService.incr(EXPRESS_ORDER_NUM_PREFIX, 1);
        }
        String specialtyOrderNumber = SPECIALTY_ORDER_PREFIX + nowDate + specialtyCount;
        return specialtyOrderNumber;
    }

    /**
     * 生成闲置资源订单号
     * @return
     */
    public synchronized String getIdleOrderNumber() {
        String nowDate = getNowDate();

        long idleCount;

        if (!redisService.exist(IDLE_ORDER_NUM_PREFIX)) {
            long secondsToTomorrow = secondsToTomorrow();
            redisService.set(IDLE_ORDER_NUM_PREFIX, "1", secondsToTomorrow);
            idleCount = 1;
        } else {
            idleCount = redisService.incr(EXPRESS_ORDER_NUM_PREFIX, 1);
        }
        String idleOrderNumber = IDLE_RESOURCES_ORDER_PREFIX + nowDate + idleCount;
        return idleOrderNumber;
    }



}
