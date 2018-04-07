package com.stroage.cloud.model.usefeed;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Administrator
 * @date 创建时间 2018/4/8
 * @Description
 */

public class AgentListFeed extends BaseFeed {

    @SerializedName("pageList")
    private List<AgentFeed> agentFeedList;

    public void setAgentFeedList(List<AgentFeed> agentFeedList) {
        this.agentFeedList = agentFeedList;
    }

    public List<AgentFeed> getAgentFeedList() {
        return agentFeedList;
    }
}
