package com.stroage.cloud.model.api;

import com.stroage.cloud.model.pojo.AgentPoJo;
import com.stroage.cloud.model.pojo.FindByProductIdPoJo;
import com.stroage.cloud.model.pojo.GetAllDevicePoJo;
import com.stroage.cloud.model.pojo.LoginPoJo;
import com.stroage.cloud.model.pojo.QueryAgentPoJo;
import com.stroage.cloud.model.pojo.QueryDevicePoJo;
import com.stroage.cloud.model.pojo.UpdateLockPoJo;
import com.stroage.cloud.model.pojo.UpdateSwitchClosePoJo;
import com.stroage.cloud.model.pojo.UpdateSwitchOpenPoJo;
import com.stroage.cloud.model.usefeed.AgentListFeed;
import com.stroage.cloud.model.usefeed.BaseFeed;
import com.stroage.cloud.model.usefeed.DeviceInfoFeed;
import com.stroage.cloud.model.usefeed.DeviceListInfoFeed;
import com.stroage.cloud.model.usefeed.LoginFeed;
import com.stroage.cloud.model.usefeed.SearchAgentFeed;
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

    //1登陆接口
    @Headers("Content-Type:application/json")
    @POST("burnermanager/mobile/doLogin")
    Observable<LoginFeed> login(@Body LoginPoJo loginPoJo);

    //2根据代理商名称获取代理商信息
    @Headers("Content-Type:application/json")
    @POST("burnermanager/agent/findByName")
    Observable<AgentListFeed> getAgentListByName(@Body QueryAgentPoJo queryAgentPoJo);

    //3得到所有代理商列表
    @Headers("Content-Type:application/json")
    @POST("burnermanager/agent/findlist")
    Observable<AgentListFeed> getAgentList(@Body AgentPoJo agentPoJo);

    //4查找代理商
    @Headers("Content-Type:application/json")
    @POST("burnermanager/customer/findbyagent")
    Observable<DeviceListInfoFeed> findbyAgent(@Body QueryDevicePoJo queryDevicePoJo);

    //5开锁 关锁 接口
    @Headers("Content-Type:application/json")
    @POST("burnermanager/customer/updatelockcmd")
    Observable<UpdateLockFeed> updateLockcmd(@Body UpdateLockPoJo updateLockPoJo);

    //6根据设备ID徐照设备信息
    @Headers("Content-Type:application/json")
    @POST("burnermanager/customer/findbyproductid")
    Observable<DeviceInfoFeed> findbyproductid(@Body FindByProductIdPoJo findByProductIdPoJo);

    //7修改设备开状态
    @Headers("Content-Type:application/json")
    @POST("burnermanager/customer/updateSwitchStatus")
    Observable<BaseFeed> updateSwitchOpenStatus(@Body UpdateSwitchOpenPoJo updateSwitchStatusPoJo);

    //修改设备关油阀状态
    @Headers("Content-Type:application/json")
    @POST("burnermanager/customer/updateSwitchStatus")
    Observable<BaseFeed> updateSwitchCloseStatus(@Body UpdateSwitchClosePoJo updateSwitchStatusPoJo);

    //8设备的无筛选列表
    @Headers("Content-Type:application/json")
    @POST("burnermanager/customer/findlist")
    Observable<DeviceListInfoFeed> findAllDeviceList(@Body GetAllDevicePoJo getAllDevicePoJo);
}
