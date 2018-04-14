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

    public  void getDeviceListByAgentName(){

    }

    /***
     * 根据设备编号后去设备信息
     * @param code
     * @param storageCallBack
     */
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

    /***
     * 获取所有未筛选的设备列表
     * @param storageCallBack
     */
    public void getAllDeviceList(final StorageCallBack storageCallBack){
        this.storageCallBack = storageCallBack;
        RestDataSource.findAllDeviceList(new GetAllDevicePoJo(1, 100), new Observer<DeviceListInfoFeed>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                storageCallBack.onFail(e.toString());
            }

            @Override
            public void onNext(DeviceListInfoFeed deviceListInfoFeed) {
                storageCallBack.onSuccess(deviceListInfoFeed);
            }
        });
    }

    /***
     * 开锁Api
     * @param productid
     * @param lockcmd
     * @param storageCallBack
     */
    public void openLock(String productid,int lockcmd,final StorageCallBack storageCallBack){
        UpdateLockPoJo lockPoJo = new UpdateLockPoJo(productid,lockcmd);
        RestDataSource.updateLockCMD(lockPoJo, new Observer<UpdateLockFeed>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                storageCallBack.onFail(e.toString());
            }

            @Override
            public void onNext(UpdateLockFeed updateLockFeed) {
                if(updateLockFeed!=null && updateLockFeed.getStatus().equals("success")){
                    storageCallBack.onSuccess(updateLockFeed);
                }
            }
        });
    }

}
