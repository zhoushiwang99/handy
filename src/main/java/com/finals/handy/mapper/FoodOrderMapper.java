package com.finals.handy.mapper;

import com.finals.handy.bean.Food;
import com.finals.handy.bean.FoodOrder;
import com.finals.handy.vo.FinishedFoodOrder;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zsw
 */
@Mapper
public interface FoodOrderMapper {


    /**
     * 根据订单号获取已完成的订单
     * @param orderNum
     * @return
     */
    @Select("select * from finished_food_order where order_number = #{orderNum}")
    FinishedFoodOrder getFinishedOrderByNum(String orderNum);


    /**
     * 完成订单时设置评分、评论、完成时间
     * @param score
     * @param comment
     * @param time
     * @param orderNum
     */
    @Update("update finished_food_order set score = #{score},comment = #{comment},finish_time = #{time} " +
            "where order_number = #{orderNum}")
    void addScoreAndCommentAndTimeToFinishByOrderNum(int score,String comment,String time,String orderNum);

    /**
     * 将订单从发布表移到已完成表
     * @param orderNum
     */
    @Insert("insert into finished_food_order(order_number,publisher_id,contact_name,receive_address,finisher_id," +
            "publish_time,remarks,receive_time,total_money,pay_money,all_money,phone) select order_number,publisher_id,contact_name," +
            "receive_address,receiver_id,publish_time,remarks," +
            "receive_time,total_money,pay_money,all_money,phone from published_food_order where order_number = #{orderNum}")
    void addOrderToFinishedOrder(String orderNum);

    /**
     * 完成订单时操作
     * 从已发布的food表里面删除
     * @param orderNum
     */
    @Delete("delete from published_food_order where order_number = #{orderNum}")
    void deleteOrderAfterFinished(String orderNum);

    /**
     * 订单是否已完成
     * @param orderNum
     * @return
     */
    @Select("select count(*) from finished_food_order where order_number = #{orderNum}")
    int isOrderHasFinishedByOrderNum(String orderNum);

    /**
     * 传入订单号
     * 获取订单发布者是否想删除订单
     * @param orderNum
     * @return
     */
    @Select("select want_delete from published_food_order where order_number = #{orderNum}")
    boolean isPublisherWantDeleteOrderByNum(String orderNum);

    /**
     * 根据订单号获取订单接取者
     * @param orderNum
     * @return
     */
    @Select("select receiver_id from published_food_order where order_number = #{orderNum}")
    String getReceiverIdByOrderNum(String orderNum);

    /**
     * 传入订单号
     * 发布者想要删除订单
     * @param orderNum
     */
    @Update("update published_food_order set want_delete = 1 where order_number = #{orderNum}")
    void wantDeleteByOrderNum(String orderNum);

    /**
     * 根据订单号删除订单
     * @param orderNum
     */
    @Update("update published_food_order set is_delete = 1 where order_number = #{orderNum}")
    void deleteOrderByOrderNum(String orderNum);


    /**
     * 获取当前可被接取的订单
     * @return
     */
    @Select("select * from published_food_order where is_delete = 0 and is_received = 0")
    Page<FoodOrder> getPublishFoodOrder();


    /**
     * 根据订单号获取订单信息
     * @param orderNum
     * @return
     */
    @Select("select * from published_food_order where order_number = #{orderNum}")
    FoodOrder getOrderByOrderNum(String orderNum);





    /**
     * 根据传入的订单号获取对应的food
     * @param orderNum
     * @return
     */
    @Select("select id,address,food_name,money from food where order_num = #{orderNum}")
    List<Food> getFoodsByOrderNum(String orderNum);

    /**
     * 传入用户id,获取该用户发布但还未完成的食物订单
     * @param userId
     * @return
     */
    @Select("select * from published_food_order where publisher_id = #{userId} and is_delete = 0")
    Page<FoodOrder> getMyPublishFoodOrder(String userId);


    /**
     * 接取订单
     * @param userId 接取者id
     * @param orderNum 订单号
     * @param time 接取时间
     */
    @Update("update published_food_order set is_received = 1, receiver_id = #{userId},receive_time = #{time}" +
            " where order_number = #{orderNum}")
    void receiveOrder(String userId,String orderNum,String time);

    /**
     * 订单是否已被接取
     * @param orderNum 订单号
     * @return
     */
    @Select("select is_received from published_food_order where order_number = #{orderNum}")
    boolean isOrderHasReceivedByOrderNum(String orderNum);

    /**
     * 根据订单号获取订单发布者id
     * @param orderNum
     * @return
     */
    @Select("select publisher_id from published_food_order where order_number = #{orderNum}")
    String getPublisherIdByOrderNum(String orderNum);

    /**
     * 传入订单号判断订单是否存在
     * 返回1代表存在
     * @param orderNum
     * @return
     */
    @Select("select count(*) from published_food_order where order_number = #{orderNum} and is_delete = 0")
    int isOrderExistByOrderNum(String orderNum);

    /**
     * 将订单信息(除了具体food)加入到数据表中
     *
     * @param foodOrder
     * @return
     */
    @Insert("insert into published_food_order(order_number,publisher_id,contact_name,receive_address" +
            ",publish_time,remarks,total_money,pay_money,all_money,phone) values " +
            "(#{orderNumber},#{publisherId},#{contactName},#{receiveAddress},#{publishTime},#{remarks},#{totalMoney}," +
            "#{payMoney},#{allMoney},#{phone})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void publishOrder(FoodOrder foodOrder);


    /**
     * 传入订单号和food对象
     * 将food加入数据表
     * @param orderNum 订单号
     * @param foods
     */
    @Insert("<script>insert into food(order_num,food_name,money,address) values " +
            "<foreach collection='list' item='c' separator=','>(#{orderNum},#{c.foodName},#{c.money},#{c.address})</foreach></script>")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void addFoodToPublish(String orderNum,@Param("list") List<Food> foods);


}
