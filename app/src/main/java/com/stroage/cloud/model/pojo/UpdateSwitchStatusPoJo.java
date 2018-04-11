package com.stroage.cloud.model.pojo;

/**
 * @author Administrator
 * @date 创建时间 2018/4/12
 * @Description
 */

public class UpdateSwitchStatusPoJo {


    public UpdateSwitchStatusPoJo(String productid, int switchcmdon) {
        this.productid = productid;
        this.switchcmdon = switchcmdon;
    }

    /**
     * productid : 201700032
     * switchcmdon : 1
     */



    private String productid;
    private int switchcmdon;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public int getSwitchcmdon() {
        return switchcmdon;
    }

    public void setSwitchcmdon(int switchcmdon) {
        this.switchcmdon = switchcmdon;
    }


}
