package com.stroage.cloud.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.stroage.cloud.R;
import com.stroage.cloud.StorageCloudApp;
import com.stroage.cloud.bean.SpinnerListBean;
import com.stroage.cloud.di.main.DaggerMainComponent;
import com.stroage.cloud.di.main.MainModule;
import com.stroage.cloud.model.usefeed.DeviceInfoFeed;
import com.stroage.cloud.viewmodel.main.MainViewModel;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @date 创建时间 2018/3/18
 * @author Administrator
 * @Description
 * @version
 */

public class MainActivity extends AppCompatActivity implements MainViewModel.DeviceInfoListener {


    NiceSpinner niceSpinner;

    @Inject
    MainViewModel mainViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInject();
        niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
        initAgent();
    }

    void initAgent(){
        final List<String> listBeans = new ArrayList<>();
        for(int i=0;i<10;i++){
            SpinnerListBean spinnerListBean = new SpinnerListBean("device"+i,i+"");
            listBeans.add(spinnerListBean.getAgentName());
        }
        niceSpinner.attachDataSource(listBeans);
        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String deviceType = listBeans.get(i);
                mainViewModel.selectDeviceType(deviceType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void initInject(){
        DaggerMainComponent.builder().appComponent(((StorageCloudApp)getApplication()).getAppComponent())
                .mainModule(new MainModule(this)).build().inject(this);
    }

    @Override
    public void onDeviceTypeLoaded(DeviceInfoFeed deviceInfoFeed) {

    }

    @Override
    public void onError(String errorMsg) {

    }
}
