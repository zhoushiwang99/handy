package com.finals.handy.bean;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author zsw
 */
public class Food {

    /**
     * id
     */
    private Integer id;

    /**
     * 食物名称
     */
    @NotNull
    @Size(min = 1, max = 10)
    private String foodName;

    /**
     * 购买价格
     */
    @NotNull
    @Range(min = 1, max = 40)
    private double money;

    /**
     * 购买地址
     */
    @NotNull
    @Size(min = 1, max = 20)
    private String address;

    public Food(Integer id, @NotNull @Size(min = 1, max = 10) String foodName, @NotNull @Range(min = 1, max = 40) double money, @NotNull @Size(min = 1, max = 20) String address) {
        this.id = id;
        this.foodName = foodName;
        this.money = money;
        this.address = address;
    }

    public Food(@NotNull @Size(min = 1, max = 10) String foodName) {
        this.foodName = foodName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", foodName='" + foodName + '\'' +
                ", money=" + money +
                ", address='" + address + '\'' +
                '}';
    }

    public Food() {
    }
}
