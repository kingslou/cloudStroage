package com.stroage.cloud.model.pojo;

/**
 * @author Administrator
 * @date 创建时间 2018/4/9
 * @Description
 */

public class FindByProductIdPoJo {


    public FindByProductIdPoJo(String productid) {
        this.productid = productid;
    }

    /**
     * productid : 201700032
     */

    private String productid;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }
}
