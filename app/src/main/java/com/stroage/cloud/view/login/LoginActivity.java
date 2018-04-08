package com.stroage.cloud.view.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.gson.Gson;
import com.stroage.cloud.AppConfig;
import com.stroage.cloud.R;
import com.stroage.cloud.StorageCloudApp;
import com.stroage.cloud.databinding.ActivityLoginBinding;
import com.stroage.cloud.model.api.RestDataSource;
import com.stroage.cloud.model.pojo.LoginPoJo;
import com.stroage.cloud.model.usefeed.LoginFeed;
import com.stroage.cloud.utils.DialogBuilder;
import com.stroage.cloud.utils.SPUtil;
import com.stroage.cloud.view.main.MainActivity;
import com.stroage.cloud.viewmodel.login.LoginViewModel;

import butterknife.BindView;
import rx.Subscriber;

/**
 * @author Administrator
 * @date 创建时间 2018/3/18
 * @Description
 */
public class LoginActivity extends AppCompatActivity implements LoginViewModel.MainListener {

    ActivityLoginBinding activityLoginBinding;

    LoginViewModel loginViewModel;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_pwd)
    EditText editPwd;
    private int status = 0;
    private boolean isCheckAccount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewModel = new LoginViewModel(this);
        activityLoginBinding.setClickLogin(loginViewModel);
        isCheckAccount = SPUtil.getBooleanPreferences(StorageCloudApp.getContext(),AppConfig.ISCHECKACCOUNT);
        loginViewModel.setCheckPwd(isCheckAccount);

    }

    @Override
    public void onLoginClick(LoginPoJo loginPoJo) {
        LoginPoJo loginPoJo1 = new LoginPoJo("admin", "bazong888");
        if (TextUtils.isEmpty(loginPoJo1.getAccount()) || TextUtils.isEmpty(loginPoJo1.getPassword())) {
            DialogBuilder.infoDialog(getApplicationContext(), R.string.str_tip_text_warn, R.string.str_login_warn_text);
            return;
        }
        RestDataSource.login(loginPoJo1, new Subscriber<LoginFeed>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(LoginFeed loginFeed) {
                //保存登录信息
                SPUtil.setStringContentPreferences(StorageCloudApp.getContext(), AppConfig.CACHEUSER,new Gson().toJson(loginFeed));
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onCheckPwdClick(View view) {
        CheckBox radioButton = (CheckBox) view;
        if (radioButton.isChecked()) {
            loginViewModel.setCheckPwd(false);
            isCheckAccount = false;
        } else {
            loginViewModel.setCheckPwd(true);
            isCheckAccount = true;
        }
        SPUtil.setBooleanPreferences(StorageCloudApp.getContext(),AppConfig.ISCHECKACCOUNT,isCheckAccount);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

