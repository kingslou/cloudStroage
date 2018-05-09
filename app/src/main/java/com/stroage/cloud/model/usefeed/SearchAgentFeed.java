package com.stroage.cloud.model.usefeed;

import java.util.List;

/**
 * @author Administrator
 * @date 创建时间 2018/5/9
 * @Description
 */

public class SearchAgentFeed extends BaseFeed {


    /**
     * data : null
     * dataList : [{"id":"201700032","name":"福建融鸿景商贸4","content":"","time":"2018-04-04 0:11:26","choose":"","number":"上海冯涛"}]
     * pageList : null
     */

    private Object data;
    private Object pageList;
    private List<DataListBean> dataList;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getPageList() {
        return pageList;
    }

    public void setPageList(Object pageList) {
        this.pageList = pageList;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * id : 201700032
         * name : 福建融鸿景商贸4
         * content :
         * time : 2018-04-04 0:11:26
         * choose :
         * number : 上海冯涛
         */

        private String id;
        private String name;
        private String content;
        private String time;
        private String choose;
        private String number;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getChoose() {
            return choose;
        }

        public void setChoose(String choose) {
            this.choose = choose;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
