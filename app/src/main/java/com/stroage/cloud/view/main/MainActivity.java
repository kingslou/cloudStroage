package com.stroage.cloud.view.main;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
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

    @BindView(R.id.nice_spinner)
    NiceSpinner niceSpinner;
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.image_search)
    ImageView imageSearch;
    @BindView(R.id.recycleView)
    RecyclerView mRecycleView;
    private StorageBaseAdapter baseAdapter;
    private List<DeviceInfoBean> deviceInfoBeanList = new ArrayList<>();
    private List<AgentFeed> agentFeedList = new ArrayList<>();
    private long mExitTime;
    private HashMap<String, AgentFeed> agentMap = new HashMap<>();
    private int currentPageNo = 1;
    private AgentFeed currentAgentFeed;
    //全部类别的默认识别码
    private String allNumberKey = "#all$$$***()(@#$##$$#";
    private int total; //总数
    private EndLessOnScrollListener endLessOnScrollListener;

    private AdapterWrapper mAdapterWrapper;
    private SwipeToLoadHelper mLoadMoreHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
    }

    private void initAdapter() {
        if (mAdapterWrapper != null) {
            mAdapterWrapper.notifyDataSetChanged();
            return;
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        mRecycleView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
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
                if(deviceInfoBean.getActive()==1){
                    image_device_status.setImageResource(R.drawable.icon_tag_green);
                }else{
                    image_device_status.setImageResource(R.drawable.icon_tag_grey);
                }
                //剩余燃料
                if(deviceInfoBean.getCapacity().equals("0")){
                    text_capacity.setText("低液位");
                    text_capacity.setTextColor(getResources().getColor(R.color.color_tab_tip_dot_bg));
                }else if(deviceInfoBean.getCapacity().equals("100")){
                    text_capacity.setTextColor(getResources().getColor(R.color.color_label_black));
                    text_capacity.setText("高液位");
                }else{
                    if(!TextUtils.isEmpty(deviceInfoBean.getCapacity())){
                        text_capacity.setTextColor(getResources().getColor(R.color.color_label_black));
                        text_capacity.setText(deviceInfoBean.getCapacity()+"%");
                    }
                }

                //设备编号
                text_device_code.setText(deviceInfoBean.getProductid());
                // 信号状态 1显示弱，2显示强  其他显示为无
                String signalState = deviceInfoBean.getSignalstate();
                if(signalState.equals("1")){
                  signalState = "弱";
                }else if(signalState.equals("2")){
                    signalState = "强";
                }else{
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
                if(deviceInfoBeanList.size()<total){
                    loadMoreDeviceList();
                }else{
                    Toast.makeText(StorageCloudApp.getContext(),"没有更多了",Toast.LENGTH_SHORT).show();
                }
            }
        };
        //mRecycleView.addOnScrollListener(endLessOnScrollListener);
    }

    @Override
    public void onLoad() {
        if(total==deviceInfoBeanList.size()){
            mAdapterWrapper.setLoadNoMore();
        }else{
            loadMoreDeviceList();
        }
    }

    private void initAgent() {
        final List<String> listBeans = new ArrayList<>();
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
                //第一个为全部
                AgentFeed agentFeedAll = new AgentFeed();
                agentFeedAll.setName("全部");
                agentFeedAll.setNumber(allNumberKey);
                agentMap.put("-1",agentFeedAll);
                listBeans.add("全部");
                for (int i = 0; i < agentFeedList.size(); i++) {
                    AgentFeed agentFeed = agentFeedList.get(i);
                    listBeans.add(agentFeed.getName());
                    agentMap.put(i + "", agentFeed);
                }
                niceSpinner.attachDataSource(listBeans);
                niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        DialogBuilder.showLoading(MainActivity.this);
                        currentPageNo = 1;
                        AgentFeed agentFeed = agentMap.get((i-1) + "");
                        currentAgentFeed = agentFeed;
                        if(currentAgentFeed.getNumber().equals(allNumberKey)){
                            loadAllDeviceList(true);
                        }else{
                            loadDeviceList(agentFeed, true);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                //默认请求全部的供应商列表分页
                AgentFeed agentFeed = agentMap.get("-1");
                currentAgentFeed = agentFeed;
                loadAllDeviceList(true);
            }
        });
    }

    private void loadMoreDeviceList() {
        //分页加载每页6条
        currentPageNo++;
        if(currentAgentFeed.getNumber().equals(allNumberKey)){
            loadAllDeviceList(false);
        }else{
            loadDeviceList(currentAgentFeed, false);
        }
    }

    /***
     * 加载所有供应商的设备列表
     * @param needClear ，每次重新选择时候需要重置
     */
    private void loadAllDeviceList(final boolean needClear){
        RestDataSource.findAllDeviceList(new GetAllDevicePoJo(currentPageNo, 6), new Observer<DeviceListInfoFeed>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                DialogBuilder.hideDialog();
            }

            @Override
            public void onNext(DeviceListInfoFeed deviceListInfoFeed) {
                if (deviceListInfoFeed != null && deviceListInfoFeed.getStatus().equals("success")) {
                    try {
                        total = deviceListInfoFeed.getPageList().getTotal();
                        if (needClear) {
                            deviceInfoBeanList.clear();
                            deviceInfoBeanList = deviceListInfoFeed.getPageList().getRows();
                        } else {
                            deviceInfoBeanList.addAll(deviceListInfoFeed.getPageList().getRows());
                        }
                        if(mLoadMoreHelper!=null){
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

    private void loadDeviceList(AgentFeed agentFeed, final boolean needClear) {
        RestDataSource.findDevicebyAgent(new QueryDevicePoJo(currentPageNo, 100, agentFeed.getNumber()), new Observer<DeviceListInfoFeed>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e("ddd", e.toString());
                DialogBuilder.hideDialog();
            }

            @Override
            public void onNext(DeviceListInfoFeed deviceListInfoFeed) {
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
                        if(mLoadMoreHelper!=null){
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
        RestDataSource.findbyproductid(new FindByProductIdPoJo(editSearch.getText().toString()), new Observer<DeviceInfoFeed>() {
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
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        editSearch.setText("");
        //todo 重新定位到第一个,怎么想的，每次回来都让用户重新选择一次？
//        initAgent();
//        currentPageNo = 1;
    }
}
