package com.stroage.cloud.bean;

/**
 * @author Administrator
 * @date 创建时间 2018/3/18
 * @Description
 */

public class DeviceInfoBean {


    /**
     * productid : 000001
     * hotelname : 测试
     * capacity :
     * active : 1
     * signalstate :
     * agentname : 上海冯涛
     * agentno : 000001
     */

    private String productid;
    private String hotelname;
    private String capacity;
    private int active;
    private String signalstate;
    private String agentname;
    private String agentno;
    private String temp;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getHotelname() {
        return hotelname;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getSignalstate() {
        return signalstate;
    }

    public void setSignalstate(String signalstate) {
        this.signalstate = signalstate;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public String getAgentno() {
        return agentno;
    }

    public void setAgentno(String agentno) {
        this.agentno = agentno;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTemp() {
        return temp;
    }
}
