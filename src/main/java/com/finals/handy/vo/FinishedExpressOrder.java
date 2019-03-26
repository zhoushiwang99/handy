package com.finals.handy.vo;

/**
 * @author zsw
 * @date 2019/3/8 18:29
 */
public class FinishedExpressOrder {

    private Integer id;

    private String orderNumber;

    private String publisherId;

    private String finisherId;

    private String contactName;

    private String contactNumber;

    private String publishTime;

    private String finishTime;

    private String payMoney;

    private String pickupDoor;

    private String pickupAddress;

    private String dormitoryBuilding;

    private String dormitoryNumber;

    private String remarks;

    private String comment;

    private int score;

    private String companyName;

    public FinishedExpressOrder() {
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

    public String getFinisherId() {
        return finisherId;
    }

    public void setFinisherId(String finisherId) {
        this.finisherId = finisherId;
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

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
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

    public String getDormitoryBuilding() {
        return dormitoryBuilding;
    }

    public void setDormitoryBuilding(String dormitoryBuilding) {
        this.dormitoryBuilding = dormitoryBuilding;
    }

    public String getDormitoryNumber() {
        return dormitoryNumber;
    }

    public void setDormitoryNumber(String dormitoryNumber) {
        this.dormitoryNumber = dormitoryNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "FinishedExpressOrder{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", publisherId='" + publisherId + '\'' +
                ", finisherId='" + finisherId + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", payMoney='" + payMoney + '\'' +
                ", pickupDoor='" + pickupDoor + '\'' +
                ", pickupAddress='" + pickupAddress + '\'' +
                ", dormitoryBuilding='" + dormitoryBuilding + '\'' +
                ", dormitoryNumber='" + dormitoryNumber + '\'' +
                ", remarks='" + remarks + '\'' +
                ", comment='" + comment + '\'' +
                ", score=" + score +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
