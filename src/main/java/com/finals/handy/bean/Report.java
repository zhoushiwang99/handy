package com.finals.handy.bean;

/**
 * @author xiaoqiang
 * @date $(DATE)-$(TIME)
 */
public class Report {
    //    id
    private Integer id;
    //    举报原因
    private String reason;
    //    举报时间
    private String time;
    //    举报人id
    private Integer reportId;

    public Report() {
    }

    public Report(Integer id, String reason, String time, Integer reportId) {
        this.id = id;
        this.reason = reason;
        this.time = time;
        this.reportId = reportId;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", reason='" + reason + '\'' +
                ", time='" + time + '\'' +
                ", reportId=" + reportId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }
}
