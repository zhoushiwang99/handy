package com.finals.handy.bean;

import java.util.List;

/**
 * @author xiaoqiang
 * @date $(DATE)-$(TIME)
 */
public class Task {
    //    任务的id
    private Integer id;
    //    任务的名称
    private String name;
    //    发布的内容
    private String content;

    //    任务发布的时间
    private String time;
    //    图片的路径
    private List<String> imgsPath;
    //    是否被举报
    private Integer isReport;
    //    任务发布者
    private Integer publishId;
    //任务接受者
    private Integer acceptId;
    //    是否完成
    private Integer isFinish;
    public Task() {
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", imgsPath=" + imgsPath +
                ", isReport=" + isReport +
                ", publishId=" + publishId +
                ", acceptId=" + acceptId +
                ", isFinish=" + isFinish +
                '}';
    }

    public Task(Integer id, String name, String content, String time, List<String> imgsPath, Integer isReport, Integer publishId, Integer acceptId, Integer isFinish) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.time = time;
        this.imgsPath = imgsPath;
        this.isReport = isReport;
        this.publishId = publishId;
        this.acceptId = acceptId;
        this.isFinish = isFinish;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImgsPath() {
        return imgsPath;
    }

    public void setImgsPath(List<String> imgsPath) {
        this.imgsPath = imgsPath;
    }

    public Integer getIsReport() {
        return isReport;
    }

    public void setIsReport(Integer isReport) {
        this.isReport = isReport;
    }

    public Integer getPublishId() {
        return publishId;
    }

    public void setPublishId(Integer publishId) {
        this.publishId = publishId;
    }

    public Integer getAcceptId() {
        return acceptId;
    }

    public void setAcceptId(Integer acceptId) {
        this.acceptId = acceptId;
    }

    public Integer getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }
}
