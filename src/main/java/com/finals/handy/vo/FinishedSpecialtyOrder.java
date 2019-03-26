package com.finals.handy.vo;

/**
 * @author zsw
 * @date 2019/3/8 18:38
 */
public class FinishedSpecialtyOrder {

    private String orderNum;

    private String tradeName;

    private String buyAddress;

    private String receiveAddress;

    private String publisherId;

    private String contactName;

    private String phone;

    private String publishTime;

    /**
     * 用来买东西的钱
     */
    private double money;

    private String remarks;

    private String finisherId;

    private String finishTime;

    private String comment;

    private String score;

    /**
     * 给接取者的报酬
     */
    private String payMoney;

    /**
     * 订单总金额
     */
    private String allMoney;

    public FinishedSpecialtyOrder() {
    }


    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getBuyAddress() {
        return buyAddress;
    }

    public void setBuyAddress(String buyAddress) {
        this.buyAddress = buyAddress;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFinisherId() {
        return finisherId;
    }

    public void setFinisherId(String finisherId) {
        this.finisherId = finisherId;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(String allMoney) {
        this.allMoney = allMoney;
    }

    @Override
    public String toString() {
        return "FinishedSpecialtyOrder{" +
                "orderNum='" + orderNum + '\'' +
                ", tradeName='" + tradeName + '\'' +
                ", buyAddress='" + buyAddress + '\'' +
                ", receiveAddress='" + receiveAddress + '\'' +
                ", publisherId='" + publisherId + '\'' +
                ", contactName='" + contactName + '\'' +
                ", phone='" + phone + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", money=" + money +
                ", remarks='" + remarks + '\'' +
                ", finisherId='" + finisherId + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", comment='" + comment + '\'' +
                ", score='" + score + '\'' +
                ", payMoney='" + payMoney + '\'' +
                ", allMoney='" + allMoney + '\'' +
                '}';
    }
}
