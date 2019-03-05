package com.finals.handy.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author zsw
 */
public class FoodOrder {

    /**
     * 订单id
     */
    private Integer id;

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 发布者id
     */
    private String publisherId;

    /**
     * 联系人姓名
     */
    @NotNull
    @Size(min = 2,max = 15)
    private String contactName;

    /**
     * 收货地址
     */
    @NotNull
    @Size(min = 2,max = 40)
    private String receiveAddress;

    /**
     * 发布时间
     */
    private String publishTime;

    /**
     * 备注
     */
    @Size(min = 0,max = 40)
    private String remarks;

    /**
     * 订单总金额
     */
    private double totalMoney;

    /**
     * 食物
     */
    private List<Food> foods;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    @Override
    public String toString() {
        return "FoodOrder{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", publisherId='" + publisherId + '\'' +
                ", contactName='" + contactName + '\'' +
                ", receiveAddress='" + receiveAddress + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", remarks='" + remarks + '\'' +
                ", totalMoney='" + totalMoney + '\'' +
                ", foods=" + foods +
                '}';
    }
}
