package com.stroage.cloud.domain.main;


import com.stroage.cloud.model.usefeed.DeviceInfoFeed;

import rx.Observable;

/**
 * @author Administrator
 * @date 创建时间 2018/3/18
 * @Description
 */

public interface MainUsecase {

    Observable<DeviceInfoFeed> getDeviceInfo(String deviceType);

}
