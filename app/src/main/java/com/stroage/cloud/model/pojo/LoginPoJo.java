package com.stroage.cloud.model.pojo;

/**
 * @date 创建时间 2018/3/18
 * @author Administrator
 * @Description 登录请求
 * @version
 */
public class LoginPoJo {

    private String loginName;
    private String loginPwd;

    public LoginPoJo(String loginName, String loginPwd) {
        this.loginName = loginName;
        this.loginPwd = loginPwd;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }
}
