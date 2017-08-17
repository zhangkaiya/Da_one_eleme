package com.jiyun.da_one_eleme.controller.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initView());
        App.baseActivity = this;

        initView();
        getViewId();
        initListener();
        loadData();


    }

    /**
     * 统一进行数据的初始化
     */
    protected abstract int initView();

    protected abstract void initListener();

    protected abstract void getViewId();

    /**
     * 统一加载数据
     */
    protected abstract void loadData();

    @Override
    protected void onResume() {
        super.onResume();
        App.baseActivity = this;
    }

}
