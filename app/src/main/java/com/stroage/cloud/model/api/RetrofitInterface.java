package com.stroage.cloud.model.api;

import com.stroage.cloud.model.pojo.AgentPoJo;
import com.stroage.cloud.model.pojo.FindByProductIdPoJo;
import com.stroage.cloud.model.pojo.LoginPoJo;
import com.stroage.cloud.model.pojo.QueryAgentPoJo;
import com.stroage.cloud.model.pojo.QueryDevicePoJo;
import com.stroage.cloud.model.pojo.UpdateLockPoJo;
import com.stroage.cloud.model.usefeed.AgentListFeed;
import com.stroage.cloud.model.usefeed.DeviceInfoFeed;
import com.stroage.cloud.model.usefeed.DeviceListInfoFeed;
import com.stroage.cloud.model.usefeed.LoginFeed;
import com.stroage.cloud.model.usefeed.UpdateLockFeed;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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
    @POST("burnermanager/customer/findbyAgent")
    Observable<DeviceListInfoFeed> findbyAgent(@Body QueryDevicePoJo queryDevicePoJo);

    @Headers("Content-Type:application/json")
    @POST("burnermanager/customer/updatelockcmd")
    Observable<UpdateLockFeed> updateLockcmd(@Body UpdateLockPoJo updateLockPoJo);

    @Headers("Content-Type:application/json")
    @POST("burnermanager/customer/findbyproductid")
    Observable<DeviceInfoFeed> findbyproductid(@Body FindByProductIdPoJo findByProductIdPoJo);



}
