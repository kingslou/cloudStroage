package com.stroage.cloud.model.usefeed;

import com.google.gson.annotations.SerializedName;

/**
 * @date 创建时间 2018/3/18
 * @author Administrator
 * @Description 请求返回的信息
 * @version
 */

public class LoginFeed extends BaseFeed {

    @SerializedName("data")
    private UserFeed userFeed;

    public void setUserFeed(UserFeed userFeed) {
        this.userFeed = userFeed;
    }

    public UserFeed getUserFeed() {
        return userFeed;
    }

    @Override
    public String toString() {
        return "LoginFeed{" +
                "userFeed=" + userFeed +
                '}';
    }
}
