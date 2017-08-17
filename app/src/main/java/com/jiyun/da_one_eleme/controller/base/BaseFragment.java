package com.jiyun.da_one_eleme.controller.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 张凯雅 on 2017/8/10.
 */

public abstract class BaseFragment extends Fragment {
    protected Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initListener();
        loadData();
    }


    //找布局
    protected abstract int getLayoutId();

    //布局id
    protected abstract void initView(View view);

    /**
     * 统一加载数据
     */
    protected abstract void loadData();

    /**
     * 统一进行数据的初始化
     */

    protected abstract void initListener();


//
//    /**
//     * 当Fragment显示状态发生变化时调用
//     * 当hidden为true时代表当前fragment处于隐藏状态 false显示状态
//     */
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (hidden) {
//            //隐藏状态，资源释放
//            onHidden();
//        } else {
//            //显示状态 数据刷新
//            onShow();
//        }
//    }
//
//    protected abstract void onShow();
//
//    protected abstract void onHidden();

    /**
     * fragment切换时传递参数
     */
    public void setParams(Bundle bundle) {
        this.bundle = bundle;
    }
}
