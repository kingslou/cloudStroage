package com.stroage.cloud.model.pojo;

/**
 * @author Administrator
 * @date 创建时间 2018/4/9
 * @Description
 */

public class QueryDevicePoJo {

    public QueryDevicePoJo(int pageNo, int pageSize, String number) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.number = number;
    }

    /**
     * pageNo : 1
     * pageSize : 10
     * number : 000001
     */

    private int pageNo;
    private int pageSize;
    private String number;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
