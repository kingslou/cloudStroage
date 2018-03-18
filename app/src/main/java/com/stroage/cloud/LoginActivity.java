package com.stroage.cloud;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stroage.cloud.databinding.ActivityLoginBinding;
import com.stroage.cloud.model.pojo.LoginPoJo;

/***
 * @author  loujin
 *
 */
public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        LoginPoJo loginPoJo = new LoginPoJo("111","222");
        activityLoginBinding.setUser(loginPoJo);
    }

}

