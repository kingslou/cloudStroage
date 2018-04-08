package com.stroage.cloud.model.api;

import com.stroage.cloud.model.pojo.AgentPoJo;
import com.stroage.cloud.model.pojo.DevicePoJo;
import com.stroage.cloud.model.pojo.LoginPoJo;
import com.stroage.cloud.model.pojo.QueryAgentPoJo;
import com.stroage.cloud.model.pojo.QueryDevicePoJo;
import com.stroage.cloud.model.usefeed.AgentListFeed;
import com.stroage.cloud.model.usefeed.DeviceInfoFeed;
import com.stroage.cloud.model.usefeed.LoginFeed;
import com.stroage.cloud.view.login.LoginActivity;

import java.util.List;

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
    @POST("burnermanager/mobile/doLogin")
    Observable<LoginFeed> login(@Body LoginPoJo loginPoJo);

    @Headers("Content-Type:application/json")
    @POST("agent/findByName")
    Observable<AgentListFeed> getAgentListByName(@Body QueryAgentPoJo queryAgentPoJo);

    @Headers("Content-Type:application/json")
    @POST("burnermanager/agent/findlist")
    Observable<AgentListFeed> getAgentList(@Body AgentPoJo agentPoJo);

    @Headers("Content-Type:application/json")
    @POST("burnermanager/customer/findbyagent")
    Observable<AgentListFeed> findbyagent(@Body QueryDevicePoJo queryDevicePoJo);




    @GET("tags/search")
    Observable<DeviceInfoFeed> searchTag(@Query("q") String query,
                                            @Query("access_token") String token);

}
