package com.stroage.cloud.bean;

/**
 * @author Administrator
 * @date 创建时间 2018/3/18
 * @Description
 */

public class SpinnerListBean {
    private String agentName;
    private String agentId;

    public SpinnerListBean(String agentName, String agentId) {
        this.agentName = agentName;
        this.agentId = agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentName() {
        return agentName;
    }
}
