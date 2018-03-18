package com.stroage.cloud.model.usefeed;

/**
 * @date 创建时间 2018/3/18
 * @author Administrator
 * @Description 请求返回的信息
 * @version
 */

public class LoginFeed {
    private String userName;
    private String gender;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
