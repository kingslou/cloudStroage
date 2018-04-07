package com.stroage.cloud.model.usefeed;

import java.util.List;

/**
 * @author Administrator
 * @date 创建时间 2018/4/5
 * @Description
 */

public class BaseFeed {

    private String status;

    private String msg;

    private String errorMsg;

    private List<?> errorDetails;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<?> getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(List<?> errorDetails) {
        this.errorDetails = errorDetails;
    }
}
