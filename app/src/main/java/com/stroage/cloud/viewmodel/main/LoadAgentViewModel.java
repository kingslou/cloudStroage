package com.stroage.cloud.viewmodel.main;

import com.stroage.cloud.model.usefeed.AgentFeed;
import com.stroage.cloud.model.usefeed.AgentListFeed;

import rx.functions.Action1;

/**
 * @author Administrator
 * @date 创建时间 2018/4/8
 * @Description
 */

public class LoadAgentViewModel {

    private AgentListener agentListener;

    public LoadAgentViewModel(AgentListener agentListener){
        this.agentListener = agentListener;

    }
    public interface AgentListener{
        void onLoaded(AgentFeed agentFeed);

        void onError(Throwable error);
    }

}
