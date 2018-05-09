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

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Administrator
 * @date 创建时间 2018/4/8
 * @Description
 */

public class RestDataSource {

    private static RetrofitInterface instance;

    private static RestDataSource restDataSource;

    private String BASE_URL = "http://af.jxbazong.com:8080/";

    private void init() {
       // HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        //logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient();
        //client.interceptors().add(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        instance = retrofit.create(RetrofitInterface.class);
    }

    public static RetrofitInterface getAPIService() {
        if (restDataSource == null) {
            restDataSource = new RestDataSource();
        }

        if (instance == null) {
            restDataSource.init();
        }

        return instance;
    }

    /***
     * 登录
     * @param pj
     * @param observer
     */
    public static void login(LoginPoJo pj, Observer<LoginFeed> observer) {
        setSubscribe(getAPIService().login(pj), observer);
    }

    /***
     * 根据供应商名字获取供应商列表
     * @param queryAgentPoJo
     * @param observer
     */
    public static void getAgentListByName(QueryAgentPoJo queryAgentPoJo,Observer<AgentListFeed> observer){
        setSubscribe(getAPIService().getAgentListByName(queryAgentPoJo),observer);
    }

    /***
     * 获取供应商列表
     * @param agentPoJo
     * @param observer
     */
    public static void getAgentList(AgentPoJo agentPoJo, Observer<AgentListFeed> observer){
        setSubscribe(getAPIService().getAgentList(agentPoJo),observer);
    }

    /***
     * 列出某个供应商下面的设备列表
     * @param poJo
     * @param observer
     */
    public static void findDevicebyAgent(QueryDevicePoJo poJo, Observer<DeviceListInfoFeed> observer){
        setSubscribe(getAPIService().findbyAgent(poJo),observer);
    }

    /***
     * 开锁 或者关闭
     * @param poJo
     * @param observer
     */
    public static void updateLockCMD(UpdateLockPoJo poJo, Observer<UpdateLockFeed> observer){
        setSubscribe(getAPIService().updateLockcmd(poJo),observer);
    }

    /***
     * 根据设备编号 获取设备信息
     * @param poJo
     * @param observer
     */
    public static void findbyproductid(FindByProductIdPoJo poJo, Observer<DeviceInfoFeed> observer){
        setSubscribe(getAPIService().findbyproductid(poJo),observer);
    }

    /***
     * 无筛选状态的所有设备列表
     * @param pojo
     * @param observer
     */
    public static void findAllDeviceList(GetAllDevicePoJo pojo,Observer<DeviceListInfoFeed> observer){
        setSubscribe(getAPIService().findAllDeviceList(pojo),observer);
    }

    public static void updateSwitchOpenStatus(UpdateSwitchOpenPoJo pojo, Observer<BaseFeed> observer){
        setSubscribe(getAPIService().updateSwitchOpenStatus(pojo),observer);
    }

    public static void updateSwitchCloseStatus(UpdateSwitchClosePoJo pojo,Observer<BaseFeed> observer){
        setSubscribe(getAPIService().updateSwitchCloseStatus(pojo),observer);
    }


    /**
     * 插入观察者
     *
     * @param observable
     * @param observer
     * @param <T>
     */
    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {

        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }
}
