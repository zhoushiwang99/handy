package com.finals.handy.bean;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author zsw
 */
public class SpecialtyOrder {


    public SpecialtyOrder() {
    }

    /**
     * 订单id
     */
    private Integer id;

    /**
     * 订单号
     */
    private String orderNum;

    /**
     * 订单完成报酬
     */
    @NotNull
    @Range(min = 1,max = 200)
    private double payMoney;


    /**
     * 物品名称
     */
    @NotNull
    @Size(min = 1,max = 30)
    private String tradeName;

    /**
     * 发布者id
     *
     */
    private Integer publisherId;



    /**
     * 联系人姓名
     */
    @NotNull
    @Size(min = 2,max = 15)
    private String contactName;

    /**
     * 联系人手机号
     */
    @NotNull
    @Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$")
    private String phone;


    /**
     *预计价格
     */
    @NotNull
    @Range(min = 1,max = 10000)
    private double money;

    /**
     * 备注
     */
    @Size(max = 40)
    private String remarks;

    /**
     * 发布时间
     */
    private String publishTime;

    /**
     * 是否已被接取
     */
    private boolean isReceived;

    /**
     * 接取者id
     */
    private String receiverId;
    /**
     * 购买地址
     */
    @NotNull
    @Size(min = 1,max = 40)
    private String buyAddress;

    /**
     * 用户总支付金额
     */
    private double allMoney;

    /**
     * 收货地址
     */
    @NotNull
    @Size(min = 1,max = 40)
    private String receiveAddress;


    public double getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(double allMoney) {
        this.allMoney = allMoney;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setBuyAddress(String buyAddress) {
        this.buyAddress = buyAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public String getTradeName() {
        return tradeName;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public String getContactName() {
        return contactName;
    }

    public String getPhone() {
        return phone;
    }

    public double getMoney() {
        return money;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public boolean isReceived() {
        return isReceived;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getBuyAddress() {
        return buyAddress;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    @Override
    public String toString() {
        return "SpecialtyOrder{" +
                "id=" + id +
                ", orderNum='" + orderNum + '\'' +
                ", tradeName='" + tradeName + '\'' +
                ", publisherId=" + publisherId +
                ", contactName='" + contactName + '\'' +
                ", phone='" + phone + '\'' +
                ", money=" + money +
                ", remarks='" + remarks + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", buyAddress='" + buyAddress + '\'' +
                ", receiveAddress='" + receiveAddress + '\'' +
                '}';
    }
}
