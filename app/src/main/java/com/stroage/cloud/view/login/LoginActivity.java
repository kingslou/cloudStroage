package com.stroage.cloud.view.login;

import android.app.Application;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.gson.Gson;
import com.stroage.cloud.AppConfig;
import com.stroage.cloud.BaseActivity;
import com.stroage.cloud.R;
import com.stroage.cloud.StorageCloudApp;
import com.stroage.cloud.databinding.ActivityLoginBinding;
import com.stroage.cloud.model.api.RestDataSource;
import com.stroage.cloud.model.pojo.LoginPoJo;
import com.stroage.cloud.model.usefeed.LoginFeed;
import com.stroage.cloud.utils.DialogBuilder;
import com.stroage.cloud.utils.DialogFactory;
import com.stroage.cloud.utils.SPUtil;
import com.stroage.cloud.view.main.MainActivity;
import com.stroage.cloud.viewmodel.login.LoginViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * @author Administrator
 * @date 创建时间 2018/3/18
 * @Description
 */
public class LoginActivity extends BaseActivity implements LoginViewModel.MainListener {

    ActivityLoginBinding activityLoginBinding;

    LoginViewModel loginViewModel;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_pwd)
    EditText editPwd;
    @BindView(R.id.check_account)
    CheckBox mChecked;
    private int status = 0;
    private boolean isCheckAccount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        ButterKnife.bind(this);
        loginViewModel = new LoginViewModel(this);
        activityLoginBinding.setClickLogin(loginViewModel);
        isCheckAccount = SPUtil.getBooleanPreferences(StorageCloudApp.getContext(),AppConfig.ISCHECKACCOUNT);
        mChecked.setChecked(isCheckAccount);
        if(isCheckAccount){
            LoginFeed loginFeed = new Gson().fromJson(SPUtil.getStringContentPreferences(this, AppConfig.CACHEUSER),LoginFeed.class);
            if(loginFeed!=null && loginFeed.getUserFeed()!=null){
                editPhone.setText(loginFeed.getUserFeed().getAccount());
                editPwd.setText(SPUtil.getStringContentPreferences(this,AppConfig.CACHEPWD));
            }
        }
        mChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheckAccount = b;
            }
        });
    }

    @Override
    public void onLoginClick(LoginPoJo loginPoJo) {
//        editPhone.setText("admin");
//        editPwd.setText("bazong888");
        if (TextUtils.isEmpty(editPhone.getText().toString()) || TextUtils.isEmpty(editPwd.getText().toString())) {
            DialogBuilder.infoDialog(LoginActivity.this, R.string.str_tip_text_warn, R.string.str_login_warn_text);
            showErrorMsg("");
            return;
        }
        loginPoJo = new LoginPoJo(editPhone.getText().toString().trim(),editPwd.getText().toString().trim());
        showLoading();
        RestDataSource.login(loginPoJo, new Subscriber<LoginFeed>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                hideLoading();
            }

            @Override
            public void onNext(LoginFeed loginFeed) {
                //保存登录信息
                hideLoading();
                if(loginFeed.getStatus().equals("failed")){
                    DialogBuilder.infoDialog(LoginActivity.this, loginFeed.getErrorMsg());
                    return ;
                }
                SPUtil.setBooleanPreferences(StorageCloudApp.getContext(),AppConfig.ISCHECKACCOUNT,isCheckAccount);
                Log.e("保存的状态",isCheckAccount+"");
                if(isCheckAccount){
                    SPUtil.setStringContentPreferences(StorageCloudApp.getContext(), AppConfig.CACHEUSER,new Gson().toJson(loginFeed));
                    SPUtil.setStringContentPreferences(StorageCloudApp.getContext(),AppConfig.CACHEPWD,editPwd.getText().toString());
                }else{
                    SPUtil.clearDataByKey(StorageCloudApp.getContext(), AppConfig.CACHEUSER);
                }
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onCheckPwdClick(View view) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

