package com.stroage.cloud.viewmodel.login;

import android.view.View;

import com.stroage.cloud.model.pojo.LoginPoJo;

/**
 * @date 创建时间 2018/3/18
 * @author Administrator
 * @Description
 * @version
 */

public class LoginViewModel {

    MainListener mainListener;
    boolean checkPwd = false;

    public LoginViewModel(MainListener mainListener){
        this.mainListener = mainListener;
    }

    public interface MainListener {
        void onLoginClick(LoginPoJo loginPoJo);
        void onCheckPwdClick(View view);
    }

    public void clickLogonBtn(LoginPoJo loginPoJo){
        if(mainListener!=null){
            mainListener.onLoginClick(loginPoJo);
        }
    }

    public void clickCheckPwd(View view){
        if(mainListener!=null){
            mainListener.onCheckPwdClick(view);
        }
    }

    public void setCheckPwd(boolean checkPwd) {
        this.checkPwd = checkPwd;
    }

    public boolean isCheckPwd() {
        return checkPwd;
    }
}
