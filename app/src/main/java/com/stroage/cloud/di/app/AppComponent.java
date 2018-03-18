package com.stroage.cloud.di.app;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.stroage.cloud.utils.RxBus;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2018/3/18.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    SharedPreferences sharedPreferences();
    Gson gson();
    RxBus bus();
}
