package com.finals.handy.mapper;

import com.finals.handy.bean.Food;
import com.finals.handy.bean.FoodOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zsw
 */
@Mapper
public interface FoodOrderMapper {

    /**
     * 将订单信息(除了具体food)加入到数据表中
     *
     * @param foodOrder
     * @return
     */
    @Insert("insert into published_food_order(order_number,publisher_id,contact_name,receive_address" +
            ",publish_time,remarks,total_money) values " +
            "(#{orderNumber},#{publisherId},#{contactName},#{receiveAddress},#{publishTime},#{remarks},#{totalMoney})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void publishOrder(FoodOrder foodOrder);

    /**
     * 将food加入数据表
     *
     * @param foods
     */
    @Insert("<script>insert into food(food_name,money,address) values " +
            "<foreach collection='list' item='c' separator=','>(#{c.foodName},#{c.money},#{c.address})</foreach></script>")
    void addFoodToPublish(@Param("list") List<Food> foods);


}
