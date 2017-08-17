package com.jiyun.da_one_eleme.controller.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.jiyun.da_one_eleme.R;
import com.jiyun.da_one_eleme.controller.base.BaseActivity;

public class Shopping_car_Activity extends BaseActivity implements View.OnClickListener {

    private Button btn_acounts;

    @Override
    protected int initView() {
        return R.layout.activity_shopping_car_;
    }

    @Override
    protected void initListener() {
        btn_acounts.setOnClickListener(this);

    }

    @Override
    protected void getViewId() {
        btn_acounts=(Button) findViewById(R.id.btn_acounts);

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(Shopping_car_Activity.this,Center_Activity.class);
        startActivity(intent);
    }
}
