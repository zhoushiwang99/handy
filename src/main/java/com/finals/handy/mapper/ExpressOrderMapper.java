package com.finals.handy.mapper;

import com.finals.handy.bean.ExpressOrder;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

/**
 * @author zsw
 */
@Mapper
public interface ExpressOrderMapper {


    /**
     * 发布者想删除此订单，等待接取者同意
     * @param orderNum
     */
    @Update("update published_express_order set want_delete = 1 where order_number = #{orderNum}")
    void wantToDeleteByOrderNum(String orderNum);

    @Select("select want_delete from published_express_order where order_number = #{orderNum}")
    boolean isWantDeleteByOrderNum(String orderNum);


    /**
     * 从可接取订单中删除已完成的该订单
     * @param orderNum
     */
    @Delete("delete from published_express_order where order_number = #{orderNum}")
    void deleteOrderForPublicAfterFinish(String orderNum);


    /**
     * 根据订单号设置评价。完成时间、评分
     * @param orderNum
     * @param comment
     * @param time
     * @param score
     */
    @Update("update finished_express_order set comment = #{comment}," +
            "finish_time = #{time},score = #{score} where order_number = #{orderNum}")
    void addCommentAndTimeAndScore(String orderNum,String comment,String time,int score);


    /**
     * 订单是否已完成
     * @param orderNum
     * @return
     */
    @Select("select count(*) from finished_express_order where order_number = #{orderNum}")
    int isOrderHasFinished(String orderNum);


    /**
     * 根据订单号获取订单发布者id
     * @param id 订单id
     * @return
     */
    @Select("select publisher_id from published_express_order where id = #{id}")
    int getPublisherIdByOrderId(String id);

    /**
     * 把订单信息移到已完成的订单表中
     * @param orderNum
     */
    @Insert("insert into finished_express_order(order_number,publisher_id,finisher_id,contact_name," +
            "contact_number,publish_time,pay_money,pickup_door,pickup_address,dormitory_building,dormitory_number,remarks)" +
            "select order_number,publisher_id,receiver_id,contact_name,contact_number,publish_time,pay_money,pickup_door,pickup_address,dormitory_building," +
            "dormitory_number,remarks from published_express_order where order_number = #{orderNum}")
    void finishOrder(String orderNum);

    /**
     * 根据订单号获取接取者的id
     * @param num
     * @return
     */
    @Select("select receiver_id from published_express_order where order_number = #{num}")
    String getReceiverIdByOrderNum(String num);



    @Select("select publisher_id from published_express_order where id = #{id}")
    String getPublisherIdByExpressId(String id);

    /**
     * 删除订单
     * @param id
     */
    @Update("update published_express_order set is_delete = 1 where id = #{id}")
    void deleteExpressOrder(String id);

    /**
     * 根据订单号删除
     * @param id
     */
    @Update("update published_express_order set is_delete = 1 where order_number = #{num}")
    void deleteExpressOrderByOrderNum(String id);
    /**
     * 获取用户发布但未完成的快递订单
     * @param id 用户id
     * @return
     */
    @Select("select id,order_number orderNumber,publisher_id publisherId,contact_name contactName,contact_number contactNumber" +
            ",dormitory_building builderNumber,dormitory_number dormitoryNumber,company_name expressCompanyName,pickup_door pickupDoor," +
            "pickup_address pickupAddress,pay_money payMoney," +
            "remarks,publish_time publishTime,is_received isReceived from published_express_order where publisher_id = #{id} and is_delete = 0")
    Page<ExpressOrder> getMyExpressOrders(String id);


    /**
     * 获取尚未接取的订单
     * @return
     */

    @Select("select id,order_number orderNumber,publisher_id publisherId,contact_name contactName,contact_number contactNumber" +
            ",dormitory_building builderNumber,dormitory_number dormitoryNumber,company_name expressCompanyName,pickup_door pickupDoor," +
            "pickup_address pickupAddress,pay_money payMoney," +
            "remarks,publish_time publishTime from published_express_order where is_received = 0 and is_delete = 0")
    Page<ExpressOrder> getPublishedExpressOrder();


    /**
     * 发布代领快递订单
     * @param expressOrder
     */
    @Insert("insert into published_express_order(order_number,publisher_id,contact_name,contact_number," +
            "company_name,pickup_door,pickup_address,dormitory_building,dormitory_number,pay_money,remarks,publish_time) " +
            "values(#{orderNumber},#{publisherId},#{contactName},#{contactNumber},#{expressCompanyName},#{pickupDoor},#{pickupAddress}" +
            ",#{builderNumber},#{dormitoryNumber},#{payMoney},#{remarks},#{publishTime})")
    @Options(useGeneratedKeys = true,keyColumn = "id")
    void realseExpressOrder(ExpressOrder expressOrder);


    /**
     * 订单id是否存在
     * @param id
     * @return
     */
    @Select("select count(*) from published_express_order where id = #{id} and is_delete = 0")
    int isOrderExistByOrderId(String id);

    @Select("select count(*) from published_express_order where order_number = #{orderNum} and is_delete = 0")
    int isOrderExistByOrderNum(String orderNum);



    /**
     * 根据订单号获取订单发布者id
     * @param num 订单id
     * @return
     */
    @Select("select publisher_id from published_express_order where order_number = #{num}")
    int getPublisherIdByOrderNum(String num);


    /**
     * 订单是否已被接取
     * @param orderNum
     * @return
     */
    @Select("select is_received from published_express_order where order_number = #{orderNum}")
    boolean isOrderReceived(String orderNum);


    /**
     * 接取订单
     * @param orderNum
     * @param receiverId
     * @param receiveTime
     */
    @Update("update published_express_order set is_received = 1, receiver_id = #{receiverId},receive_time = #{receiveTime} where order_number = #{orderNum}")
    void receiveExpressOrder(String orderNum,int receiverId,String receiveTime);


    /**
     * 根据订单号获取订单id
     * @param orderNum
     * @return
     */
    @Select("select id from published_express_order where order_number = #{orderNum}")
    String getOrderIdByOrderNum(String orderNum);

    @Select("select order_number from published_express_order where id = #{id}")
    String getOrderNumByOrderId(String id);


    /**
     * 订单是否已被接取
     * @param id
     * @return
     */
    @Select("select is_received from published_express_order where id = #{id}")
    boolean isReceived(String id);

    @Select("select receiver_id from published_express_order where id = #{id}")
    String getReceiverIdByOrderId(String id);


}
