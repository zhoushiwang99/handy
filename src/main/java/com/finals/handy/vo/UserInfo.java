package com.finals.handy.vo;

/**
 * @author zsw
 * 用户信息获取
 */
public class UserInfo {
    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 学号
     */
    private String studentId;

    /**
     * 性别
     */
    private char sex;


    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 手机号
     */
    private String phone;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", studentId='" + studentId + '\'' +
                ", sex=" + sex +
                ", idNumber='" + idNumber + '\'' +
                ", className='" + className + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
