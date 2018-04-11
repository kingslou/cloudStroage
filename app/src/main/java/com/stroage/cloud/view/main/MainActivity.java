package com.stroage.cloud.view.main;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.stroage.cloud.R;
import com.stroage.cloud.adapter.BaseViewHolder;
import com.stroage.cloud.adapter.OnItemClickListener;
import com.stroage.cloud.bean.DeviceInfoBean;
import com.stroage.cloud.bean.SpinnerListBean;
import com.stroage.cloud.model.api.RestDataSource;
import com.stroage.cloud.model.pojo.AgentPoJo;
import com.stroage.cloud.model.usefeed.AgentFeed;
import com.stroage.cloud.model.usefeed.AgentListFeed;
import com.stroage.cloud.utils.DialogBuilder;
import com.stroage.cloud.utils.DisplayUtils;
import com.stroage.cloud.view.map.MapLocationActivity;
import com.stroage.cloud.viewmodel.main.LoadAgentViewModel;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

/**
 * @date 创建时间 2018/3/18
 * @author Administrator
 * @Description
 * @version
 */

public class MainActivity extends AppCompatActivity implements LoadAgentViewModel.AgentListener {


    NiceSpinner niceSpinner;
    private RecyclerView mRecycleView;
    private com.stroage.cloud.adapter.BaseAdapter baseAdapter;
    private List<DeviceInfoBean> deviceInfoBeanList = new ArrayList<>();
    LoadAgentViewModel loadAgentViewModel;
    private List<AgentFeed> agentFeedList = new ArrayList<>();
    private long mExitTime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
        mRecycleView = (RecyclerView)findViewById(R.id.recycleView);
        initAgent();
        initAdapter();
    }

    void initAdapter(){
        for(int i=0;i<20;i++){
            DeviceInfoBean deviceInfoBean = new DeviceInfoBean();
            deviceInfoBean.setActive(100);
            deviceInfoBean.setAgentname("代理商"+i);
            deviceInfoBean.setHotelname("酒店"+i);
            deviceInfoBean.setSignalstate("强");
            deviceInfoBean.setProductid("201804000"+i);
            deviceInfoBean.setAgentno("sdhashd");
            deviceInfoBean.setCapacity((i+1)+"");
            deviceInfoBeanList.add(deviceInfoBean);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        //设置布局管理器
        mRecycleView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper. VERTICAL);

        baseAdapter = new com.stroage.cloud.adapter.BaseAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, int position) {
                //设备编号
                TextView text_device_code = (TextView) holder.getView(R.id.text_device_code);
                //信号状态
                TextView text_signal_state = (TextView) holder.getView(R.id.text_signal_state);
                //剩余容量
                TextView text_capacity = (TextView)holder.getView(R.id.text_capacity);
                //设备温度
                TextView text_device_wd = (TextView)holder.getView(R.id.text_device_wd);
                // 酒店名称
                TextView text_hotel_name = (TextView)holder.getView(R.id.text_hotel_name);

                DeviceInfoBean deviceInfoBean = deviceInfoBeanList.get(position);
                text_capacity.setText(deviceInfoBean.getCapacity());
                text_device_code.setText(deviceInfoBean.getProductid());
                if(position%2==0){
                    text_device_wd.setText("正常");
                    text_device_wd.setTextColor(getResources().getColor(R.color.color_heading_black));
                }else{
                    text_device_wd.setTextColor(getResources().getColor(R.color.color_tab_tip_dot_bg));
                    text_device_wd.setText("异常");
                }

                text_signal_state.setText(deviceInfoBean.getSignalstate());
                text_hotel_name.setText(deviceInfoBean.getHotelname());

            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_devicelist;
            }

            @Override
            public int getItemCount() {
                return deviceInfoBeanList.size();
            }
        };
        mRecycleView.setAdapter(baseAdapter);
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
                intent.setClass(MainActivity.this, MapLocationActivity.class);
                startActivity(intent);
            }
        });
    }

    void initAgent(){
        final List<String> listBeans = new ArrayList<>();
        for(int i=0;i<6;i++){
            listBeans.add("代理商"+i);
        }
        niceSpinner.attachDataSource(listBeans);
        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String deviceType = listBeans.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        RestDataSource.getAgentList(new AgentPoJo(1,10),new Observer<AgentListFeed>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(AgentListFeed agentListFeed) {
                if(agentListFeed.getStatus().equals("failed")){
                    return ;
                }
                agentFeedList = agentListFeed.getPageList().getRows();
                for(AgentFeed agentFeed : agentFeedList){
                    SpinnerListBean spinnerListBean = new SpinnerListBean(agentFeed.getName(),agentFeed.getId()+"");
                    listBeans.add(spinnerListBean.getAgentName());
                }
                niceSpinner.attachDataSource(listBeans);
                niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String deviceType = listBeans.get(i);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });
    }


    @Override
    public void onLoaded(AgentFeed agentFeed) {

    }

    @Override
    public void onError(Throwable error) {

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
}
