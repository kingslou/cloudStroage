package com.stroage.cloud.model.api;

import com.stroage.cloud.StorageCallBack;
import com.stroage.cloud.model.pojo.FindByProductIdPoJo;
import com.stroage.cloud.model.usefeed.DeviceInfoFeed;

import rx.Observer;

/**
 * @author Administrator
 * @date 创建时间 2018/4/15
 * @Description
 */

public class StorageTask {

    private static StorageTask instance;
    private StorageCallBack storageCallBack;
    private StorageTask(){
    }
    public static StorageTask getInstance(){
        if(instance==null){
            synchronized (StorageTask.class){
                if(instance==null){
                    instance = new StorageTask();
                }
            }
        }
        return instance;
    }

    public  void getDeviceListByAgentName(){

    }

    public void searchDeviceByCode(String code, final StorageCallBack storageCallBack){
        this.storageCallBack = storageCallBack;
        RestDataSource.findbyproductid(new FindByProductIdPoJo(code), new Observer<DeviceInfoFeed>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                storageCallBack.onFail(e.toString());
            }

            @Override
            public void onNext(DeviceInfoFeed deviceInfoFeed) {
                if(deviceInfoFeed!=null && deviceInfoFeed.getStatus().equals("success")){
                    storageCallBack.onSuccess(deviceInfoFeed);
                }
            }
        });
    }

    public void openLock(){
        
    }

}
