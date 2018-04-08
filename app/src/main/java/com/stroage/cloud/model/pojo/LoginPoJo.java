package com.stroage.cloud.model.pojo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;

import com.stroage.cloud.BR;

/**
 * @date 创建时间 2018/3/18
 * @author Administrator
 * @Description 登录请求
 * @version
 */
public class LoginPoJo extends BaseObservable {

    @Bindable
    private String account;
    @Bindable
    private String password;

    public LoginPoJo(String loginName, String loginPwd) {
        this.account = loginName;
        this.password = loginPwd;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
        notifyPropertyChanged(BR.account);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
}
