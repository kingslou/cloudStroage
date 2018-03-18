package com.stroage.cloud.model.api;

import com.stroage.cloud.model.pojo.DevicePoJo;
import com.stroage.cloud.model.pojo.LoginPoJo;
import com.stroage.cloud.model.usefeed.DeviceInfoFeed;
import com.stroage.cloud.model.usefeed.LoginFeed;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * @author Administrator
 * @date 创建时间 2018/3/18
 * @Description
 */

public class ApiSourceImp implements ApiSource {

    RetrofitInterface retrofitInterface;

    public ApiSourceImp(Retrofit retrofit){
        retrofitInterface = retrofit.create(RetrofitInterface.class);
    }

    @Override
    public Observable<LoginFeed> login(String loginName, String loginPwd) {
        return retrofitInterface.login(new LoginPoJo(loginName,loginPwd));
    }

    @Override
    public Observable<DeviceInfoFeed> getDeviceInfo(String deviceType) {
        return retrofitInterface.getDeviceInfo(new DevicePoJo(deviceType));
    }
}
