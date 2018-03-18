package com.stroage.cloud.di.login;

import com.stroage.cloud.di.Activity;
import com.stroage.cloud.domain.login.LoginUseCaseImp;
import com.stroage.cloud.model.api.ApiSource;
import com.stroage.cloud.viewmodel.login.LoginViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * @author Administrator
 * @date 创建时间 2018/3/18
 * @Description
 */
@Module
public class LoginModule {

    LoginViewModel.MainListener mainListener;

    public LoginModule(LoginViewModel.MainListener mainListener) {
        this.mainListener = mainListener;
    }

    @Provides
    @Activity
    public LoginViewModel provideLoginViewModel(){
        return new LoginViewModel(mainListener);
    }


    @Provides
    public LoginUseCaseImp provideLoginUseCaseImp(ApiSource apiSource){
        return new LoginUseCaseImp(apiSource);
    }

}
