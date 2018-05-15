package com.stroage.cloud.model.pojo;

/**
 * @author Administrator
 * @date 创建时间 2018/4/9
 * @Description
 */

public class FindByProductIdPoJo {


    public FindByProductIdPoJo(String productid,String number) {
        this.productid = productid;
        this.number = number;
    }

    /**
     * productid : 201700032
     */

    private String productid;
    private String number;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}
