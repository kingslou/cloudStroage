package com.stroage.cloud.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.stroage.cloud.R;
import com.stroage.cloud.bean.SpinnerListBean;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 创建时间 2018/3/18
 * @author Administrator
 * @Description
 * @version
 */

public class MainActivity extends AppCompatActivity {


    NiceSpinner niceSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
        initAgent();
    }

    void initAgent(){
        List<String> listBeans = new ArrayList<>();
        for(int i=0;i<10;i++){
            SpinnerListBean spinnerListBean = new SpinnerListBean("device"+i,i+"");
            listBeans.add(spinnerListBean.getAgentName());
        }
        niceSpinner.attachDataSource(listBeans);
    }
}
