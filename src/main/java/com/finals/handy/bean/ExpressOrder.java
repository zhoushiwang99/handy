package com.finals.handy.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zsw
 * 带领快递
 */
public class ExpressOrder {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


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
     *
     */
    private Integer publisherId;

    /**
     * 接受者id
     */
    private Integer receiverId;

    /**
     * 联系人姓名
     */
    @NotNull
    @Size(min = 2, max = 15)
    private String contactName;

    /**
     * 联系人手机号
     */
    @NotNull
    @Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$")
    private String contactNumber;

    /**
     * 宿舍楼栋
     */
    @NotNull
    @Size(min = 2, max = 10)
    private String builderNumber;

    /**
     * 寝室号
     */
    @NotNull
    @Size(min = 2, max = 6)
    private String dormitoryNumber;

    /**
     * 快递公司名称
     */
    @Size(min = 2, max = 6)
    @NotNull
    private String expressCompanyName;

    /**
     * 哪个门
     */
    @NotNull
    @Size(min = 2, max = 6)
    private String pickupDoor;

    /**
     * 取件地址
     */
    @Size(min = 2, max = 20)
    private String pickupAddress;

    /**
     * 订单金额
     */
    private Integer payMoney = 2;

    /**
     * 备注
     */
    @Size(max = 20)
    private String remarks;

    /**
     * 是否被接取
     */
    private boolean isReceived = false;

    /**
     * 是否已完成
     */
    private boolean isFinished = false;

    /**
     * 任务发布时间
     */
    private String publishTime = sdf.format(new Date());

    /**
     * 任务接取时间
     */
    private String receiveTime;

    /**
     * 任务完成时间
     */
    private String finishTime;

    /**
     * 订单评价
     */
    private String comment;


    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getBuilderNumber() {
        return builderNumber;
    }

    public void setBuilderNumber(String builderNumber) {
        this.builderNumber = builderNumber;
    }

    public String getDormitoryNumber() {
        return dormitoryNumber;
    }

    public void setDormitoryNumber(String dormitoryNumber) {
        this.dormitoryNumber = dormitoryNumber;
    }

    public String getExpressCompanyName() {
        return expressCompanyName;
    }

    public void setExpressCompanyName(String expressCompanyName) {
        this.expressCompanyName = expressCompanyName;
    }

    public String getPickupDoor() {
        return pickupDoor;
    }

    public void setPickupDoor(String pickupDoor) {
        this.pickupDoor = pickupDoor;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public Integer getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Integer payMoney) {
        this.payMoney = payMoney;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isReceived() {
        return isReceived;
    }

    public void setReceived(boolean received) {
        isReceived = received;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public String toString() {
        return "ExpressOrder{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", publisherId=" + publisherId +
                ", receiverId=" + receiverId +
                ", contactName='" + contactName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", builderNumber='" + builderNumber + '\'' +
                ", dormitoryNumber='" + dormitoryNumber + '\'' +
                ", expressCompanyName='" + expressCompanyName + '\'' +
                ", pickupDoor='" + pickupDoor + '\'' +
                ", pickupAddress='" + pickupAddress + '\'' +
                ", payMoney=" + payMoney +
                ", remarks='" + remarks + '\'' +
                ", isReceived=" + isReceived +
                ", isFinished=" + isFinished +
                ", publishTime='" + publishTime + '\'' +
                ", receiveTime='" + receiveTime + '\'' +
                ", finishTime='" + finishTime + '\'' +
                '}';
    }
}
