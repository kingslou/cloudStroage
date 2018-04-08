package com.stroage.cloud.model.api;

import android.util.Log;

import com.stroage.cloud.model.pojo.AgentPoJo;
import com.stroage.cloud.model.pojo.LoginPoJo;
import com.stroage.cloud.model.pojo.QueryAgentPoJo;
import com.stroage.cloud.model.pojo.QueryDevicePoJo;
import com.stroage.cloud.model.usefeed.AgentFeed;
import com.stroage.cloud.model.usefeed.AgentListFeed;
import com.stroage.cloud.model.usefeed.LoginFeed;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
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

    public static void login(LoginPoJo pj, Observer<LoginFeed> observer) {
        setSubscribe(getAPIService().login(pj), observer);
    }

    public static void getAgentListByName(QueryAgentPoJo queryAgentPoJo,Observer<AgentListFeed> observer){
        setSubscribe(getAPIService().getAgentListByName(queryAgentPoJo),observer);
    }

    public static void getAgentList(AgentPoJo agentPoJo, Observer<AgentListFeed> observer){
        setSubscribe(getAPIService().getAgentList(agentPoJo),observer);
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
