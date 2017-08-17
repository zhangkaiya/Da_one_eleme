package com.jiyun.da_one_eleme.controller.Fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiyun.da_one_eleme.R;
import com.jiyun.da_one_eleme.controller.Activity.Order_taking_Activity;
import com.jiyun.da_one_eleme.controller.base.BaseFragment;
import com.jiyun.da_one_eleme.modle.Okhttp.OkHttpManager;
import com.jiyun.da_one_eleme.modle.adapter.MyOrderRecyclerAdapter;
import com.jiyun.da_one_eleme.modle.bean.OrderBean;
import com.jiyun.da_one_eleme.modle.bean.OrderDataBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Order_Fragment extends BaseFragment{
    private String url = "http://123.206.14.104:8080/TakeoutService/order?userId=3626";
    private List<OrderDataBean> dataBeen=new ArrayList<>();
    private RecyclerView recycler;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dingdan;
    }

    @Override
    protected void initView(View view) {
        recycler =(RecyclerView) view.findViewById(R.id.recycler);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
        OkHttpManager.getInstance().getNet(url, new OkHttpManager.CallBacks() {
            @Override
            public void getString(String s) {
                Gson gson=new Gson();
                OrderBean orderBean = gson.fromJson(s, OrderBean.class);
                Type type = new TypeToken<List<OrderDataBean>>(){}.getType();
                List<OrderDataBean> lists = gson.fromJson(orderBean.getData(), type);
                dataBeen.addAll(lists);

                recycler.setLayoutManager(new GridLayoutManager(getActivity(),1));
                MyOrderRecyclerAdapter adapter=new MyOrderRecyclerAdapter(getActivity(),dataBeen);
                RecyclerAdapterWithHF hf=new RecyclerAdapterWithHF(adapter);
                recycler.setAdapter(hf);
                hf.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
                        Intent intent=new Intent(getActivity(), Order_taking_Activity.class);
                        startActivity(intent);
                    }
                });
            }
        });


    }

}
