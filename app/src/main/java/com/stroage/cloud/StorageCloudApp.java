package com.stroage.cloud;
import android.app.Application;
import android.content.Context;


/**
 * @author  loujin
 * Created by Administrator on 2018/3/17.
 */

public class StorageCloudApp extends Application {


    static StorageCloudApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


    public static Context getContext() {
        return instance;
    }

}
