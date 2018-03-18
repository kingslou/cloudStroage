package com.stroage.cloud.di.login;

import com.stroage.cloud.di.Activity;
import com.stroage.cloud.di.app.AppComponent;
import com.stroage.cloud.view.login.LoginActivity;

import javax.inject.Inject;

import dagger.Component;

/**
 * @author Administrator
 * @date 创建时间 2018/3/18
 * @Description
 */
@Activity
@Component(dependencies = AppComponent.class,modules = LoginModule.class)
public interface LoginComponent {

    void inject(LoginActivity loginActivity);
}
