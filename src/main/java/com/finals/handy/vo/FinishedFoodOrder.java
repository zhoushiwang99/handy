package com.finals.handy.vo;

import com.finals.handy.bean.Food;

import java.util.List;

/**
 * @author zsw
 * @date 2019/3/8 18:33
 */
public class FinishedFoodOrder {

    private Integer id;

    private String orderNumber;

    private String publisherId;

    private String contactName;

    private String receiveAddress;

    private String finisherId;

    private String publishTime;

    private String remarks;

    private String receiveTime;

    private String totalMoney;

    private double total_money;

    private double pay_money;

    private double all_money;

    private String comment;

    private int score;

    private String finishTime;

    private List<Food> foods;


    public FinishedFoodOrder() {
    }

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

    public String getFinisherId() {
        return finisherId;
    }

    public void setFinisherId(String finisherId) {
        this.finisherId = finisherId;
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

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public double getTotal_money() {
        return total_money;
    }

    public void setTotal_money(double total_money) {
        this.total_money = total_money;
    }

    public double getPay_money() {
        return pay_money;
    }

    public void setPay_money(double pay_money) {
        this.pay_money = pay_money;
    }

    public double getAll_money() {
        return all_money;
    }

    public void setAll_money(double all_money) {
        this.all_money = all_money;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    @Override
    public String toString() {
        return "FinishedFoodOrder{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", publisherId='" + publisherId + '\'' +
                ", contactName='" + contactName + '\'' +
                ", receiveAddress='" + receiveAddress + '\'' +
                ", finisherId='" + finisherId + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", remarks='" + remarks + '\'' +
                ", receiveTime='" + receiveTime + '\'' +
                ", totalMoney='" + totalMoney + '\'' +
                ", total_money=" + total_money +
                ", pay_money=" + pay_money +
                ", all_money=" + all_money +
                ", comment='" + comment + '\'' +
                ", score=" + score +
                ", finishTime='" + finishTime + '\'' +
                ", foods=" + foods +
                '}';
    }
}
