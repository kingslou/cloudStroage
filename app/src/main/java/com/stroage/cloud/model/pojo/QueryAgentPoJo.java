package com.stroage.cloud.model.pojo;

/**
 * @author Administrator
 * @date 创建时间 2018/4/8
 * @Description
 */

public class QueryAgentPoJo {
    private String agentName;

    public QueryAgentPoJo(String agentName) {
        this.agentName = agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentName() {
        return agentName;
    }
}
