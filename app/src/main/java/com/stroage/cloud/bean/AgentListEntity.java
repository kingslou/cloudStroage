package com.stroage.cloud.bean;

import java.util.List;

/**
 * @author Administrator
 * @date 创建时间 2018/4/9
 * @Description
 */

public class AgentListEntity {


    /**
     * pageNo : 1
     * pageSize : 10
     * total : 6
     * rows : []
     * attributeMap : null
     */

    private int pageNo;
    private int pageSize;
    private String total;
    private Object attributeMap;
    private List<?> rows;

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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Object getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(Object attributeMap) {
        this.attributeMap = attributeMap;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
