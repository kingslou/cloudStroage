package com.stroage.cloud.model.pojo;

/**
 * @author Administrator
 * @date 创建时间 2018/4/15
 * @Description
 */

public class UpdateSwitchClosePoJo {

    public UpdateSwitchClosePoJo(String productid, int switchcmdon) {
        this.productid = productid;
        this.switchcmdoff = switchcmdon;
    }

    /**
     * productid : 201700032
     * switchcmdon : 1
     */



    private String productid;
    private int switchcmdoff;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public int getSwitchcmdon() {
        return switchcmdoff;
    }

    public void setSwitchcmdon(int switchcmdon) {
        this.switchcmdoff = switchcmdon;
    }
}
