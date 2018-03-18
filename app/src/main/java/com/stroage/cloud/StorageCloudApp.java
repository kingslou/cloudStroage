package com.stroage.cloud;

import android.app.Application;
import com.stroage.cloud.di.app.AppComponent;
import com.stroage.cloud.di.app.AppModule;
import com.stroage.cloud.di.app.DaggerAppComponent;
import com.stroage.cloud.di.app.NetworkModule;

/**
 * @author  loujin
 * Created by Administrator on 2018/3/17.
 */

public class StorageCloudApp extends Application {

    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
