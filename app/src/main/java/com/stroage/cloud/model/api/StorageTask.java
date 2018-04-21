package com.stroage.cloud.model.api;

import com.stroage.cloud.StorageCallBack;
import com.stroage.cloud.model.pojo.FindByProductIdPoJo;
import com.stroage.cloud.model.pojo.GetAllDevicePoJo;
import com.stroage.cloud.model.pojo.UpdateLockPoJo;
import com.stroage.cloud.model.usefeed.DeviceInfoFeed;
import com.stroage.cloud.model.usefeed.DeviceListInfoFeed;
import com.stroage.cloud.model.usefeed.UpdateLockFeed;

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
}
