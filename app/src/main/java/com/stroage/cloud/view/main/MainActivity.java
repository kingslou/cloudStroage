package com.stroage.cloud.view.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.stroage.cloud.BaseActivity;
import com.stroage.cloud.EndLessOnScrollListener;
import com.stroage.cloud.R;
import com.stroage.cloud.StorageCloudApp;
import com.stroage.cloud.adapter.AdapterWrapper;
import com.stroage.cloud.adapter.BaseViewHolder;
import com.stroage.cloud.adapter.OnItemClickListener;
import com.stroage.cloud.adapter.StorageBaseAdapter;
import com.stroage.cloud.adapter.SwipeToLoadHelper;
import com.stroage.cloud.bean.DeviceInfoBean;
import com.stroage.cloud.model.api.RestDataSource;
import com.stroage.cloud.model.pojo.AgentPoJo;
import com.stroage.cloud.model.pojo.FindByProductIdPoJo;
import com.stroage.cloud.model.pojo.GetAllDevicePoJo;
import com.stroage.cloud.model.pojo.QueryDevicePoJo;
import com.stroage.cloud.model.usefeed.AgentFeed;
import com.stroage.cloud.model.usefeed.AgentListFeed;
import com.stroage.cloud.model.usefeed.DeviceInfoFeed;
import com.stroage.cloud.model.usefeed.DeviceListInfoFeed;
import com.stroage.cloud.model.usefeed.LoginFeed;
import com.stroage.cloud.utils.DialogBuilder;
import com.stroage.cloud.utils.DisplayUtils;
import com.stroage.cloud.view.map.MapLocationActivity;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;

/**
 * @author Administrator
 * @date 创建时间 2018/3/18
 * @Description
 */

public class MainActivity extends BaseActivity implements SwipeToLoadHelper.LoadMoreListener {

    public static final int REQUESTCODE = 1001;
    @BindView(R.id.nice_spinner)
    NiceSpinner niceSpinner;
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.image_search)
    ImageView imageSearch;
    @BindView(R.id.recycleView)
    RecyclerView mRecycleView;
    @BindView(R.id.edit_search_agent)
    TextView edit_search_agent;
    @BindView(R.id.relativeSearchAgent)
    RelativeLayout relativeSearchAgent;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.image_clear)
    ImageView imageClear;

    @BindView(R.id.image_s)
    ImageView imageViewS;

    private StorageBaseAdapter baseAdapter;
    private List<DeviceInfoBean> deviceInfoBeanList = new ArrayList<>();
    private long mExitTime;
    private int currentPageNo = 1;
    private AgentFeed currentAgentFeed;
    //全部类别的默认识别码
    private String allNumberKey = "#all$$$***()(@#$##$$#";
    private int total; //总数
    private EndLessOnScrollListener endLessOnScrollListener;
    private AdapterWrapper mAdapterWrapper;
    private SwipeToLoadHelper mLoadMoreHelper;
    private LoginFeed loginFeed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loginFeed = new Gson().fromJson(getIntent().getStringExtra("userInfo"),LoginFeed.class);
        addSearchListener();
        initAdapter();
        initAgent();
    }

    private void addSearchListener() {
        imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editSearch.getText().toString())) {
                    return;
                }
                searchDeviceById();
            }
        });

        imageClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(loginFeed.getUserFeed().getNumber())) {
                    currentPageNo = 1;
                    AgentFeed agentFeedAll = new AgentFeed();
                    agentFeedAll.setName("全部");
                    agentFeedAll.setNumber(allNumberKey);
                    currentAgentFeed = agentFeedAll;
                    loadAllDeviceList(true);
                    edit_search_agent.setText("");
                    imageClear.setVisibility(View.GONE);
                    imageViewS.setVisibility(View.VISIBLE);
                }
            }
        });

        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (editSearch.getText().toString() != null) {
                        searchDeviceById();
                    }
                }
                return false;
            }
        });

        if(!TextUtils.isEmpty(loginFeed.getUserFeed().getNumber())){
            relativeSearchAgent.setEnabled(false);
            edit_search_agent.setText(loginFeed.getUserFeed().getAccount());
        }else{
            relativeSearchAgent.setEnabled(true);
        }
        relativeSearchAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AgentListActivity.class);
                startActivityForResult(intent, REQUESTCODE);
            }
        });
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue),getResources().getColor(R.color.color_work_item_announce));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPageNo = 1;
                if(TextUtils.isEmpty(loginFeed.getUserFeed().getNumber()) && currentAgentFeed.getNumber().equals(allNumberKey)) {
                    loadAllDeviceList(true);
                }else{
                    loadDeviceListByAgent(currentAgentFeed,true);
                }
            }
        });
    }

    private void initAdapter() {
        if (mAdapterWrapper != null) {
            mAdapterWrapper.notifyDataSetChanged();
            return;
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        baseAdapter = new StorageBaseAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, int position) {
                //设备编号
                TextView text_device_code = (TextView) holder.getView(R.id.text_device_code);
                //信号状态
                TextView text_signal_state = (TextView) holder.getView(R.id.text_signal_state);
                //剩余容量
                TextView text_capacity = (TextView) holder.getView(R.id.text_capacity);
                //设备温度
                TextView text_device_wd = (TextView) holder.getView(R.id.text_device_wd);
                // 酒店名称
                TextView text_hotel_name = (TextView) holder.getView(R.id.text_hotel_name);
                //设备状态
                ImageView image_device_status = holder.getView(R.id.image_device_status);

                DeviceInfoBean deviceInfoBean = deviceInfoBeanList.get(position);
                if (deviceInfoBean.getTemp().equals("0")) {
                    text_device_wd.setText("正常");
                    text_device_wd.setTextColor(getResources().getColor(R.color.color_heading_black));
                } else {
                    text_device_wd.setTextColor(getResources().getColor(R.color.color_tab_tip_dot_bg));
                    text_device_wd.setText("高温");
                }
                //暂时认为 1是在线状态
                if (deviceInfoBean.getActive() == 1) {
                    image_device_status.setImageResource(R.drawable.icon_tag_green);
                } else {
                    image_device_status.setImageResource(R.drawable.icon_tag_grey);
                }
                //剩余燃料
                if (deviceInfoBean.getCapacity().equals("0")) {
                    text_capacity.setText("低液位");
                    text_capacity.setTextColor(getResources().getColor(R.color.color_tab_tip_dot_bg));
                } else if (deviceInfoBean.getCapacity().equals("100")) {
                    text_capacity.setTextColor(getResources().getColor(R.color.color_label_black));
                    text_capacity.setText("高液位");
                } else {
                    if (!TextUtils.isEmpty(deviceInfoBean.getCapacity())) {
                        text_capacity.setTextColor(getResources().getColor(R.color.color_label_black));
                        text_capacity.setText(deviceInfoBean.getCapacity() + "%");
                    }else{
                        text_capacity.setTextColor(getResources().getColor(R.color.color_label_black));
                        text_capacity.setText("无");
                    }
                }

                //设备编号
                text_device_code.setText(deviceInfoBean.getProductid());
                // 信号状态 1显示弱，2显示强  其他显示为无
                String signalState = deviceInfoBean.getSignalstate();
                if (signalState.equals("1")) {
                    signalState = "弱";
                } else if (signalState.equals("2")) {
                    signalState = "强";
                } else {
                    signalState = "无";
                }
                text_signal_state.setText(signalState);
                text_hotel_name.setText(deviceInfoBean.getHotelname());
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_devicelist;
            }

            @Override
            public int getItemViewType(int position) {
                return super.getItemViewType(position);
            }

            @Override
            public int getItemCount() {
                return deviceInfoBeanList.size();
            }

        };

        mAdapterWrapper = new AdapterWrapper(baseAdapter);
        mLoadMoreHelper = new SwipeToLoadHelper(mRecycleView, mAdapterWrapper);
        mLoadMoreHelper.setLoadMoreListener(this);

        mRecycleView.setAdapter(mAdapterWrapper);
        mRecycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = DisplayUtils.dip2px(5);
            }
        });
        baseAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                DeviceInfoBean deviceInfoBean = deviceInfoBeanList.get(position);
                String deviceInfo = new Gson().toJson(deviceInfoBean);
                intent.putExtra("deviceInfo", deviceInfo);
                intent.setClass(MainActivity.this, MapLocationActivity.class);
                startActivity(intent);
            }
        });

        endLessOnScrollListener = new EndLessOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if (deviceInfoBeanList.size() < total) {
                    loadMoreDeviceList();
                } else {
                    Toast.makeText(StorageCloudApp.getContext(), "没有更多了", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    @Override
    public void onLoad() {
        if (total == deviceInfoBeanList.size()) {
            mAdapterWrapper.setLoadNoMore();
        } else {
            loadMoreDeviceList();
        }
    }

    private void initAgent() {
        //如果登录返回的信息为总部,默认显示全部
        if(TextUtils.isEmpty(loginFeed.getUserFeed().getNumber())){
            AgentFeed agentFeedAll = new AgentFeed();
            agentFeedAll.setName("全部");
            agentFeedAll.setNumber(allNumberKey);
            currentAgentFeed = agentFeedAll;
            loadAllDeviceList(true);
        }else{
            AgentFeed agentFeed = new AgentFeed();
            agentFeed.setName(loginFeed.getUserFeed().getName());
            agentFeed.setNumber(loginFeed.getUserFeed().getNumber());
            currentAgentFeed = agentFeed;
            loadDeviceListByAgent(currentAgentFeed,true);
        }
    }

    private void loadMoreDeviceList() {
        //分页加载每页6条
        currentPageNo++;
        if (currentAgentFeed.getNumber().equals(allNumberKey)) {
            loadAllDeviceList(false);
        } else {
            loadDeviceListByAgent(currentAgentFeed, false);
        }
    }

    /***
     * 加载所有供应商的设备列表
     * @param needClear ，每次重新选择时候需要重置
     */
    private void loadAllDeviceList(final boolean needClear) {
        RestDataSource.findAllDeviceList(new GetAllDevicePoJo(currentPageNo, 6), new Observer<DeviceListInfoFeed>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                DialogBuilder.hideDialog();
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onNext(DeviceListInfoFeed deviceListInfoFeed) {
                refreshLayout.setRefreshing(false);
                if (deviceListInfoFeed != null && deviceListInfoFeed.getStatus().equals("success")) {
                    try {
                        total = deviceListInfoFeed.getPageList().getTotal();
                        if (needClear) {
                            deviceInfoBeanList.clear();
                            deviceInfoBeanList = deviceListInfoFeed.getPageList().getRows();
                        } else {
                            deviceInfoBeanList.addAll(deviceListInfoFeed.getPageList().getRows());
                        }
                        if (mLoadMoreHelper != null) {
                            mLoadMoreHelper.setLoadMoreFinish();
                        }
                        //动态更新Adapter
                        initAdapter();
                        DialogBuilder.hideDialog();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /***
     * 根据选择的分销商来获取设备列表
     * @param agentFeed  分销商实体
     * @param needClear 是否需要清空数据，当重新选择分销商的时候，需要传递true，重置当前分页状态
     */
    private void loadDeviceListByAgent(AgentFeed agentFeed, final boolean needClear) {
        Log.e("agentNum",agentFeed.getNumber()+"dddd");
        RestDataSource.findDevicebyAgent(new QueryDevicePoJo(currentPageNo, 6, agentFeed.getNumber()), new Observer<DeviceListInfoFeed>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e("ddd", e.toString());
                DialogBuilder.hideDialog();
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onNext(DeviceListInfoFeed deviceListInfoFeed) {
                refreshLayout.setRefreshing(false);
                if (deviceListInfoFeed != null && deviceListInfoFeed.getStatus().equals("success")) {
                    DialogBuilder.hideDialog();
                    try {
                         total = deviceListInfoFeed.getPageList().getTotal();
                        if (needClear) {
                            deviceInfoBeanList.clear();
                            deviceInfoBeanList = deviceListInfoFeed.getPageList().getRows();
                        } else {
                            deviceInfoBeanList.addAll(deviceListInfoFeed.getPageList().getRows());
                        }
                        if (mLoadMoreHelper != null) {
                            mLoadMoreHelper.setLoadMoreFinish();
                        }
                        //动态更新Adapter
                        initAdapter();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void searchDeviceById() {
        String agentNumber;
        if(TextUtils.isEmpty(loginFeed.getUserFeed().getNumber())){
            agentNumber = "";
        }else{
            agentNumber = loginFeed.getUserFeed().getNumber();
        }
        RestDataSource.findbyproductid(new FindByProductIdPoJo(editSearch.getText().toString(),agentNumber), new Observer<DeviceInfoFeed>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(DeviceInfoFeed deviceInfoFeed) {
                if (deviceInfoFeed != null && deviceInfoFeed.getStatus().equals("success")) {
                    if (deviceInfoFeed.getData() != null) {
                        deviceInfoBeanList.clear();
                        deviceInfoBeanList.add(deviceInfoFeed.getData());
                        initAdapter();
                    } else {
                        Toast.makeText(MainActivity.this, "没有查询到信息", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE:
                if (data != null) {
                    String agent = data.getStringExtra("agent");
                    currentAgentFeed = new Gson().fromJson(agent, AgentFeed.class);
                    edit_search_agent.setText(currentAgentFeed.getName());
                    currentPageNo = 1;
                    loadDeviceListByAgent(currentAgentFeed, true);
                    imageClear.setVisibility(View.VISIBLE);
                    imageViewS.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        editSearch.setText("");
    }
}
