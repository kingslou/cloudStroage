package com.stroage.cloud;

/**
 * @author Administrator
 * @date 创建时间 2018/4/15
 * @Description
 */

public interface StorageCallBack {

    void onSuccess(Object obj);

    void onFail(String error);

}
