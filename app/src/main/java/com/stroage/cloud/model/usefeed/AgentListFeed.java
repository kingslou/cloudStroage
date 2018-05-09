package com.stroage.cloud.model.usefeed;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Administrator
 * @date 创建时间 2018/4/8
 * @Description
 */

public class AgentListFeed extends BaseFeed {

    /**
     * msg : null
     * data : null
     * dataList : null
     * pageList : {"pageNo":1,"pageSize":10,"total":"6","rows":[],"attributeMap":null}
     * errorDetails : []
     * errorMsg : null
     */

    private List<AgentFeed> dataList;
    private Object data;
    private PageListBean pageList;

    public List<AgentFeed> getDataList() {
        return dataList;
    }

    public void setDataList(List<AgentFeed> dataList) {
        this.dataList = dataList;
    }

    public PageListBean getPageList() {
        return pageList;
    }

    public void setPageList(PageListBean pageList) {
        this.pageList = pageList;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public static class PageListBean {
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
        private List<AgentFeed> rows;

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

        public List<AgentFeed> getRows() {
            return rows;
        }

        public void setRows(List<AgentFeed> rows) {
            this.rows = rows;
        }
    }

}
