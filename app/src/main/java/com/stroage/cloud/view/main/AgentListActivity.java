package com.stroage.cloud.view.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.stroage.cloud.BaseActivity;
import com.stroage.cloud.R;
import com.stroage.cloud.adapter.BaseViewHolder;
import com.stroage.cloud.adapter.OnItemClickListener;
import com.stroage.cloud.adapter.StorageBaseAdapter;
import com.stroage.cloud.model.api.RestDataSource;
import com.stroage.cloud.model.pojo.AgentPoJo;
import com.stroage.cloud.model.pojo.QueryAgentPoJo;
import com.stroage.cloud.model.usefeed.AgentFeed;
import com.stroage.cloud.model.usefeed.AgentListFeed;
import com.stroage.cloud.model.usefeed.SearchAgentFeed;
import com.stroage.cloud.utils.DialogBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;

/**
 * @author Administrator
 * @date 创建时间 2018/5/9
 * @Description
 */

public class AgentListActivity extends BaseActivity {

    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.image_search)
    ImageView imageSearch;
    @BindView(R.id.recycleView)
    RecyclerView mRecycleView;
    private List<AgentFeed> agentFeedList = new ArrayList<>();
    private StorageBaseAdapter baseAdapter;
    private List<AgentFeed> allAgentList = new ArrayList<>();
    private List<AgentFeed> filterList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agentlist);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initAgent();
        addSearchListener();
    }

    private void addSearchListener() {
        imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editSearch.getText().toString())) {
                    return;
                }
                searchAgentByName();
            }
        });

        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (editSearch.getText().toString() != null) {
                        searchAgentByName();
                    }
                }
                return false;
            }
        });

        //如果用户清空输入框，那么继续显示全部分销商
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(TextUtils.isEmpty(charSequence.toString())){
                    filterList.clear();
                    agentFeedList = allAgentList;
                    initAdapter();
                }else{
                    for(Iterator<AgentFeed> iterator = allAgentList.iterator(); iterator.hasNext();){
                        AgentFeed agentFeed = iterator.next();
                        if (agentFeed.getName().contains(charSequence)&&!existAgent(agentFeed)) {
                            filterList.add(agentFeed);
                        }
                    }
                    agentFeedList = filterList;
                    initAdapter();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private boolean existAgent(AgentFeed agentFeed){
        for(int i=0;i<filterList.size();i++){
            if(filterList.get(i).getNumber().equals(agentFeed.getNumber())){
                return true;
            }
        }
        return false;
    }

    private void initAdapter(){
        if(baseAdapter!=null){
            baseAdapter.notifyDataSetChanged();
            return;
        }
        baseAdapter = new StorageBaseAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, int position) {
                TextView agentName =  (TextView) holder.getView(R.id.text_agent_name);
                AgentFeed agentFeed = agentFeedList.get(position);
                agentName.setText(agentFeed.getName()+"");
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_agent_list;
            }

            @Override
            public int getItemCount() {
                return agentFeedList.size();
            }
        };

        baseAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AgentFeed agentFeed = agentFeedList.get(position);
                Intent intent = new Intent();
                intent.putExtra("agent",new Gson().toJson(agentFeed));
                setResult(MainActivity.REQUESTCODE,intent);
                finish();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecycleView.setAdapter(baseAdapter);
    }
    //分销商数据比较简单，默认初始化100个分销商，加快搜索速度
    private void initAgent() {
        RestDataSource.getAgentList(new AgentPoJo(1, 100), new Observer<AgentListFeed>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AgentListFeed agentListFeed) {
                if (agentListFeed.getStatus().equals("failed")) {
                    return;
                }
                agentFeedList = agentListFeed.getPageList().getRows();
                initAdapter();
                allAgentList =  agentListFeed.getPageList().getRows();
            }
        });
    }

    private void searchAgentByName(){
        RestDataSource.getAgentListByName(new QueryAgentPoJo(editSearch.getText().toString()), new Observer<AgentListFeed>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                Log.e("ee",e.toString());
            }
            @Override
            public void onNext(AgentListFeed agentListFeed) {
                if(agentListFeed!=null && agentListFeed.getStatus().equals("success")){
                    if(agentListFeed.getDataList()!=null && agentListFeed.getDataList().size()!=0){
                        agentFeedList = agentListFeed.getDataList();
                        initAdapter();
                    }
                }
            }
        });
    }
}
