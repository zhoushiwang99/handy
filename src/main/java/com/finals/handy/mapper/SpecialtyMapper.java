package com.finals.handy.mapper;

import com.finals.handy.bean.SpecialtyOrder;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

/**
 * 代购特产
 *
 * @author zsw
 */
@Mapper
public interface SpecialtyMapper {

    /**
     * 订单完成时添加评价和时间
     * @param orderNum
     * @param comment
     * @param time
     */
    @Update("update finished_specialty_order set comment = #{comment},finish_time = #{time}")
    void addCommentAndTimeByOrderNum(String orderNum,String comment,String time);


    /**
     * 根据订单号查询订单是否完成
     * @param orderNum
     * @return
     */
    @Select("select count(*) from finished_specialty_order where order_num = #{orderNum}")
    int isOrderHasFinishedByOrderNum(String orderNum);

    /**
     * 订单完成后从可接取的订单中删除此订单
     * @param orderNum
     */
    @Delete("delete from published_specialty_order where order_num = #{orderNum}")
    void deleteOrderFromPublishedAfterFinishByOrderNum(String orderNum);


    /**
     * 将订单信息移到已完成的表
     * @param orderNum 订单号
     */
    @Insert("insert into finished_specialty_order(order_num,trade_name,buy_address,receive_address,publisher_id," +
            "contact_name,phone,publish_time,money,remarks,finisher_id) select order_num, trade_name," +
            "buy_address,receive_address,publisher_id,contact_name,phone,publish_time,money,remarks,receiver_id " +
            "from published_specialty_order where order_num = #{orderNum}")
    void finishOrderByOrderNum(String orderNum);


    /**
     * 订单发布者想要删除订单，待接取者同意，需传入订单号
     * @param orderNum
     */
    @Update("update published_specialty_order set want_delete = 1 where order_num = #{orderNum}")
    void publisherWantDeleteByOrderNum(String orderNum);

    /**
     * 订单发布者是否希望删除订单
     * @param orderNum
     * @return
     */
    @Select("select want_delete from published_specialty_order where order_num = #{orderNum}")
    boolean isPublisherWantDeleteByOrderNum(String orderNum);


    /**
     * 根据订单号获取接取者id
     *
     * @param orderNum
     * @return
     */
    @Select("select receiver_id from published_specialty_order where order_num = #{num}")
    String getReceivedIdByOrderNum(String orderNum);

    /**
     * 根据订单号删除订单
     *
     * @param orderNum
     */
    @Update("update published_specialty_order set is_delete = 1 where order_num = #{num}")
    void deleteOrderByOrderNum(String orderNum);

    /**
     * 发布订单
     *
     * @param specialtyOrder
     */
    @Insert("insert into published_specialty_order(order_num,trade_name,buy_address,receive_address,publisher_id,contact_name,phone,publish_time,money,remarks) " +
            "values(#{orderNum},#{tradeName},#{buyAddress},#{receiveAddress},#{publisherId},#{contactName},#{phone},#{publishTime},#{money},#{remarks})")
    void publishOrder(SpecialtyOrder specialtyOrder);

    /**
     * 根据订单号返回订单数量，用于判断订单是否存在
     *
     * @param num
     * @return
     */
    @Select("select count(*) from published_specialty_order where order_num = #{num}")
    int isOrderExistByOrderNum(String num);

    /**
     * 根据订单号获取订单发布者
     *
     * @param num
     * @return
     */
    @Select("select publisher_id from published_specialty_order where order_num = #{num}")
    String getPublisherIdByOrderNum(String num);

    /**
     * 订单是否已被接取
     *
     * @param num
     * @return
     */
    @Select("select is_received from published_specialty_order where order_num = #{num}")
    boolean isOrderReceivedByOrderNum(String num);

    /**
     * 设置订单接取者和接取时间(根据订单号)
     *
     * @param orderNum
     * @param receiverId
     * @param time
     */
    @Update("update published_specialty_order set is_received = 1,receiver_id = #{receiverId},receive_time = #{time} where order_num = #{orderNum}")
    void receiveOrderByOrderNum(String orderNum, String receiverId, String time);

    /**
     * 根据用户id获取该用户发布代购特产订单
     *
     * @param id
     * @return
     */
    @Select("select * from published_specialty_order where publisher_id = #{id} and is_delete = 0")
    Page<SpecialtyOrder> getMySpecialtyOrder(String id);

    /**
     * 获取当前可被接取的代购特产订单
     *
     * @return
     */
    @Select("select * from published_specialty_order where is_delete = 0 and is_received = 0")
    Page<SpecialtyOrder> getPublishSpecialtyOrder();


}
