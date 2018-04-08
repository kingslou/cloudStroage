package com.stroage.cloud.model.usefeed;

import com.stroage.cloud.bean.DeviceInfoBean;

/**
 * @author Administrator
 * @date 创建时间 2018/4/9
 * @Description
 */

public class DeviceInfoFeed extends BaseFeed {

    private DeviceInfoBean data;

    public void setData(DeviceInfoBean data) {
        this.data = data;
    }

    public DeviceInfoBean getData() {
        return data;
    }
}
