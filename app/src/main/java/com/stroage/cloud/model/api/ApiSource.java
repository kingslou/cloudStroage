package com.stroage.cloud.model.api;

import com.stroage.cloud.model.usefeed.DeviceInfoFeed;
import com.stroage.cloud.model.usefeed.LoginFeed;

import rx.Observable;


/**
 * @author Administrator
 * @date 创建时间 2018/3/18
 * @Description
 */

public interface ApiSource {

    Observable<LoginFeed> login(String loginName, String loginPwd);

    Observable<DeviceInfoFeed> getDeviceInfo(String deviceType);
}
