package com.stroage.cloud.model.pojo;

/**
 * @author Administrator
 * @date 创建时间 2018/3/18
 * @Description
 */

public class DevicePoJo {
    private String deviceType;
    private String deviceCode;

    public DevicePoJo(String deviceType, String deviceCode) {
        this.deviceType = deviceType;
        this.deviceCode = deviceCode;
    }

    public DevicePoJo(String deviceType) {
        this.deviceType = deviceType;
    }
}

