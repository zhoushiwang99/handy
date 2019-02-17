package com.finals.handy.mapper;

import com.finals.handy.bean.ExpressOrder;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zsw
 */
@Mapper
public interface ExpressOrderMapper {


    @Select("select publisher_id from published_express_order where id = #{id}")
    String getPublisherIdByExpressId(String id);


    @Update("update published_express_order set is_delete = 1")
    String deleteExpressOrder(String id);


    /**
     * 获取用户发布但未完成的快递订单
     * @param id 用户id
     * @return
     */
    @Select("select id,order_number orderNumber,publisher_id publisherId,contact_name contactName,contact_number contactNumber" +
            ",dormitory_building builderNumber,dormitory_number dormitoryNumber,company_name expressCompanyName,pickup_door pickupDoor," +
            "pickup_address pickupAddress,pay_money payMoney," +
            "remarks,publish_time publishTime,is_received isReceived from published_express_order where publisher_id = #{id} and is_delete = 0")
    List<ExpressOrder> getMyExpressOrders(String id);


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
    int isOrderExistByOrderId(int id);


    /**
     * 根据订单号获取订单发布者id
     * @param id 订单id
     * @return
     */
    @Select("select publisher_id from published_express_order where id = #{id}")
    int getPublisherIdByOrderId(int id);


    /**
     * 订单是否已被接取
     * @param id
     * @return
     */
    @Select("select is_received from published_express_order where id = #{id}")
    boolean isOrderReceived(int id);


    /**
     * 接取订单
     * @param orderId
     * @param receiverId
     * @param receiveTime
     */
    @Update("update published_express_order set is_received = 1, receiver_id = #{receiverId},receive_time = #{receiveTime} where id = #{orderId}")
    void receiveExpressOrder(int orderId,int receiverId,String receiveTime);


}
