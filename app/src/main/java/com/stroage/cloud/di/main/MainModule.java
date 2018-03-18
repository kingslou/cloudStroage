package com.stroage.cloud.di.main;

import com.stroage.cloud.di.Activity;
import com.stroage.cloud.domain.main.MainUseCaseImp;
import com.stroage.cloud.model.api.ApiSource;
import com.stroage.cloud.viewmodel.main.MainViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * @author Administrator
 * @date 创建时间 2018/3/18
 * @Description
 */

@Module
public class MainModule {

    MainViewModel.DeviceInfoListener deviceInfoListener;
    MainUseCaseImp mainUseCaseImp;

    public MainModule(MainViewModel.DeviceInfoListener listener){
        this.deviceInfoListener = listener;
    }

    @Provides
    @Activity
    public MainViewModel provideMainViewModel(){
        return new MainViewModel(deviceInfoListener,mainUseCaseImp);
    }

    @Provides
    @Activity
    public MainUseCaseImp provideMainUseCaseImp(ApiSource apiSource){
        return new MainUseCaseImp(apiSource);
    }

}
