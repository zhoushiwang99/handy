package com.finals.handy.bean;

public class User {

    private Integer id;
    /**
     * 用户名字
     */
    private String username;

    /**
     * 用户用手机号登录的密码
     */
    private String password;

    /**
     * 用户学号
     */
    private String studentId;

    /**
     * 班级
     */
    private String className;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 入学日期
     */
    private String rxrq;
    /**
     * 学制
     */
    private String xz;

    /**
     * 性别
     */
    private char sex;

    /**
     * 身份证号
     */
    private String idNumber;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public String getRxrq() {
        return rxrq;
    }

    public void setRxrq(String rxrq) {
        this.rxrq = rxrq;
    }

    public String getXz() {
        return xz;
    }

    public void setXz(String xz) {
        this.xz = xz;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", studentId='" + studentId + '\'' +
                ", className='" + className + '\'' +
                ", phone='" + phone + '\'' +
                ", rxrq='" + rxrq + '\'' +
                ", xz='" + xz + '\'' +
                ", sex=" + sex +
                ", idNumber='" + idNumber + '\'' +
                '}';
    }
}
