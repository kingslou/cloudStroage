package com.stroage.cloud.model.usefeed;

import com.stroage.cloud.bean.DeviceInfoBean;

import java.util.List;

/**
 * @author Administrator
 * @date 创建时间 2018/3/18
 * @Description
 */

public class DeviceListInfoFeed extends BaseFeed {

    /**
     * msg : null
     * data : null
     * dataList : null
     * pageList : {"pageNo":1,"pageSize":10,"total":"6","rows":[],"attributeMap":null}
     * errorDetails : []
     * errorMsg : null
     */

    private Object dataList;
    private Object data;
    private PageListBean pageList;

    public Object getDataList() {
        return dataList;
    }

    public void setDataList(Object dataList) {
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
        private int total;
        private Object attributeMap;
        private List<DeviceInfoBean> rows;

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

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public Object getAttributeMap() {
            return attributeMap;
        }

        public void setAttributeMap(Object attributeMap) {
            this.attributeMap = attributeMap;
        }

        public List<DeviceInfoBean> getRows() {
            return rows;
        }

        public void setRows(List<DeviceInfoBean> rows) {
            this.rows = rows;
        }
    }
}
