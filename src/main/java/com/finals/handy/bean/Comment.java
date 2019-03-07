package com.finals.handy.bean;

/**
 * @author xiaoqiang
 * @date $(DATE)-$(TIME)
 * 任务评论
 */
public class Comment {
    //    评论的id
    private Integer id;
    //   评论用户的id
    private Integer userId;
    //  评论的内容
    private String content;
    //    是否被举报
    private Integer isReport;
    //    评论的时间
    private String time;

    //   任务，帖子的id
    private Integer taskId;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", isReport=" + isReport +
                ", time='" + time + '\'' +
                ", taskId=" + taskId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsReport() {
        return isReport;
    }

    public void setIsReport(Integer isReport) {
        this.isReport = isReport;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Comment() {
    }

    public Comment(Integer id, Integer userId, String content, Integer isReport, String time, Integer taskId) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.isReport = isReport;
        this.time = time;
        this.taskId = taskId;
    }
}
