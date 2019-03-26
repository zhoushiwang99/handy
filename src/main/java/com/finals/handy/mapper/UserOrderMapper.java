package com.finals.handy.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户订单
 * @author zsw
 * @date 2019/3/8 13:22
 */
@Mapper
public interface UserOrderMapper {


    /**
     * 获取作为接取者的已完成订单
     * @param userId
     * @return
     */
    @Select("select order_num from user_finished_order where finisher_id = #{userId}")
    Page<String> getUserFinishedReceiveOrderNum(String userId);

    /**
     * 获取作为发布者的已完成订单号
     * @param userId
     * @return
     */
    @Select("select order_num from user_finished_order where publisher_id = #{userId}")
    Page<String> getUserFinishedPublishOrderNum(String userId);


    /**
     * 获取用户接取订单的订单号
     * @param userId
     * @return
     */
    @Select("select order_num from user_received_order where user_id = #{userId}")
    Page<String> getUserReceivedOrderNum(String userId);

    /**
     * 获取用户发布订单的订单号
     * @param userId
     * @return
     */
    @Select("select order_num from user_published_order where user_id = #{userId}")
    Page<String> getUserPublishedOrderNum(String userId);

    /**
     * 将订单号存到用户发布表里
     * @param userId
     * @param orderNum
     */
    @Insert("insert into user_published_order(user_id,order_num) values(#{userId},#{orderNum})")
    void addPublishOrder(String userId,String orderNum);

    /**
     * 从用户发布表里删除订单
     * @param orderNum
     */
    @Delete("delete from user_published_order where order_num = #{orderNum}")
    void deletePublishOrder(String orderNum);


    /**
     * 将订单号存到用户接取表里
     * @param userId
     * @param orderNum
     */
    @Insert("insert into user_received_order(user_id,order_num) values(#{userId},#{orderNum})")
    void addReceiveOrder(String userId,String orderNum);

    /**
     * 从用户接取表里删除订单
     * @param orderNum
     */
    @Delete("delete from user_received_order where order_num = #{orderNum}")
    void deleteReceiveOrder(String orderNum);

    /**
     * 将订单号存到用户完成订单表里
     * @param orderNum
     * @param publisherId
     * @param finisherId
     */
    @Insert("insert into user_finished_order(order_num,publisher_id,finisher_id) values (#{orderNum},#{publisherId},#{finisherId})")
    void addFinishedOrder(String orderNum,String publisherId,String finisherId);




}
