package com.stroage.cloud.model.pojo;

/**
 * @author Administrator
 * @date 创建时间 2018/4/9
 * @Description
 */

public class UpdateLockPoJo {

    public UpdateLockPoJo(String productid, int lockcmd) {
        this.productid = productid;
        this.lockcmd = lockcmd;
    }

    /**
     * productid : 201700032
     * lockcmd : 1
     */

    private String productid;
    private int lockcmd;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public int getLockcmd() {
        return lockcmd;
    }

    public void setLockcmd(int lockcmd) {
        this.lockcmd = lockcmd;
    }
}
