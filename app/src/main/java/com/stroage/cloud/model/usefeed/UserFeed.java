package com.stroage.cloud.model.usefeed;

/**
 * @author Administrator
 * @date 创建时间 2018/4/8
 * @Description
 */

public class UserFeed {

    /**
     * isNewRecord : false
     * id : 1
     * account : admin
     * password : 21232f297a57a5a743894a0e4a801fc3
     * name : 10
     * sex : 男
     * qq : 1010
     * phone : 101
     * email : 010
     * number :
     */

    private boolean isNewRecord;
    private int id;
    private String account;
    private String password;
    private String name;
    private String sex;
    private String qq;
    private String phone;
    private String email;
    private String number;

    public boolean isIsNewRecord() {
        return isNewRecord;
    }

    public void setIsNewRecord(boolean isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
