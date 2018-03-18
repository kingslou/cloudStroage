package com.stroage.cloud.bean;

/**
 * @author Administrator
 * @date 创建时间 2018/3/18
 * @Description
 */

public class DeviceInfoBean {

    private String deviceName;
    private String deviceCode;
    /***
     * 信号
     */
    private String signal;
    /***
     * 剩余燃料
     */
    private String residualFuel;
    /***
     * 设备温度
     */
    private String deviceTemperature;


    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public String getResidualFuel() {
        return residualFuel;
    }

    public void setResidualFuel(String residualFuel) {
        this.residualFuel = residualFuel;
    }

    public String getDeviceTemperature() {
        return deviceTemperature;
    }

    public void setDeviceTemperature(String deviceTemperature) {
        this.deviceTemperature = deviceTemperature;
    }


}
