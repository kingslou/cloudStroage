package com.stroage.cloud.model.api;

import com.stroage.cloud.model.pojo.DevicePoJo;
import com.stroage.cloud.model.pojo.LoginPoJo;
import com.stroage.cloud.model.usefeed.DeviceInfoFeed;
import com.stroage.cloud.model.usefeed.LoginFeed;
import com.stroage.cloud.view.login.LoginActivity;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Administrator
 * @date 创建时间 2018/3/18
 * @Description
 */

public interface RetrofitInterface {

    @Headers("Content-Type:application/json")
    @POST
    Observable<DeviceInfoFeed> getDeviceInfo(@Body DevicePoJo devicePoJo);

    @Headers("Content-Type:application/json")
    @POST
    Observable<LoginFeed> login(@Body LoginPoJo loginPoJo);

    @GET("tags/search")
    Observable<DeviceInfoFeed> searchTag(@Query("q") String query,
                                            @Query("access_token") String token);

}
