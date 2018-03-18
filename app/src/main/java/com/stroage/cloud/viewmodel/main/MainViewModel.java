package com.stroage.cloud.viewmodel.main;

import com.stroage.cloud.domain.main.MainUseCaseImp;
import com.stroage.cloud.model.usefeed.DeviceInfoFeed;

import javax.inject.Inject;

import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Administrator
 * @date 创建时间 2018/3/18
 * @Description
 */

public class MainViewModel {

    DeviceInfoListener deviceInfoListener;
    MainUseCaseImp mainUseCaseImp;
    private CompositeSubscription subscription;

    @Inject
    public MainViewModel(final DeviceInfoListener deviceInfoListener, MainUseCaseImp mainUseCaseImp){
        this.deviceInfoListener = deviceInfoListener;
        this.mainUseCaseImp = mainUseCaseImp;
        selectDeviceType("");
    }


    public void selectDeviceType(String type){
        subscription.add(mainUseCaseImp.getDeviceInfo("").doOnNext(new Action1<DeviceInfoFeed>() {
            @Override
            public void call(DeviceInfoFeed deviceInfoFeed) {
                deviceInfoListener.onDeviceTypeLoaded(deviceInfoFeed);
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                deviceInfoListener.onError(throwable.getMessage());
            }
        }).subscribe());
    }
    public interface DeviceInfoListener{
        void onDeviceTypeLoaded(DeviceInfoFeed deviceInfoFeed);
        void onError(String errorMsg);
    }



}
