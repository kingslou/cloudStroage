package com.stroage.cloud.domain.main;

import com.stroage.cloud.model.api.ApiSource;
import com.stroage.cloud.model.usefeed.DeviceInfoFeed;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Administrator
 * @date 创建时间 2018/3/18
 * @Description
 */

public class MainUseCaseImp implements MainUsecase {

    ApiSource apiSource;

    @Inject
    public MainUseCaseImp(ApiSource apiSource){
        this.apiSource = apiSource;
    }

    @Override
    public Observable<DeviceInfoFeed> getDeviceInfo(String deviceType) {
        return apiSource.getDeviceInfo(deviceType).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
