package com.stroage.cloud;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 闪屏页面
 * Created by Administrator on 2018/3/17.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoLogin();
            }
        },1500);
    }

    private void gotoLogin(){
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    private void gotoMain(){

    }

}
