package com.stroage.cloud.view.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.stroage.cloud.R;
import com.stroage.cloud.StorageCloudApp;
import com.stroage.cloud.databinding.ActivityLoginBinding;
import com.stroage.cloud.di.app.DaggerAppComponent;
import com.stroage.cloud.di.login.DaggerLoginComponent;
import com.stroage.cloud.di.login.LoginModule;
import com.stroage.cloud.model.pojo.LoginPoJo;
import com.stroage.cloud.utils.DialogBuilder;
import com.stroage.cloud.view.main.MainActivity;
import com.stroage.cloud.viewmodel.LoginViewModel;

import javax.inject.Inject;

/**
 * @date 创建时间 2018/3/18
 * @author Administrator
 * @Description
 * @version
 */
public class LoginActivity extends AppCompatActivity implements LoginViewModel.MainListener {

    ActivityLoginBinding activityLoginBinding;
    @Inject
    LoginViewModel loginViewModel;
    private int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        initInject();
        LoginPoJo loginPoJo = new LoginPoJo("111","222");
        activityLoginBinding.setUser(loginPoJo);
        activityLoginBinding.setClickLogin(loginViewModel);
        loginViewModel.setCheckPwd(false);

    }

    void initInject(){
        DaggerLoginComponent.builder().appComponent(((StorageCloudApp)getApplication()).getAppComponent())
                .loginModule(new LoginModule(this)).build().inject(this);
    }

    @Override
    public void onLoginClick(LoginPoJo loginPoJo) {
        if(TextUtils.isEmpty(loginPoJo.getLoginName())||TextUtils.isEmpty(loginPoJo.getLoginPwd())){
            DialogBuilder.infoDialog(getApplicationContext(),R.string.str_tip_text_warn,R.string.str_login_warn_text);
            return;
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onCheckPwdClick(View view) {
        CheckBox radioButton = (CheckBox) view;
        if(radioButton.isChecked()){
            loginViewModel.setCheckPwd(false);
        }else{
            loginViewModel.setCheckPwd(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

