package com.stroage.cloud.di.main;

import com.stroage.cloud.di.Activity;
import com.stroage.cloud.di.app.AppComponent;
import com.stroage.cloud.view.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Administrator
 * @date 创建时间 2018/3/18
 * @Description
 */

@Activity
@Component(dependencies = AppComponent.class,modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity mainActivity);

}
