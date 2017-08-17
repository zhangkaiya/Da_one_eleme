package com.jiyun.da_one_eleme.controller.Activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.jiyun.da_one_eleme.R;
import com.jiyun.da_one_eleme.controller.Fragment.Business_Fragment;
import com.jiyun.da_one_eleme.controller.Fragment.Evaluate_Fragment;
import com.jiyun.da_one_eleme.controller.Fragment.Shopping_Fragment;
import com.jiyun.da_one_eleme.controller.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class Order_Shopping_Activity extends BaseActivity {
    private List<String> stringList=new ArrayList<>();
    private List<Fragment> fragmentList=new ArrayList<>();
    private TabLayout tab;
    private ViewPager viewpager;
    private MyPagerAdapter pagerAdapter;
    private TextView name;

    @Override
    protected int initView() {
        return R.layout.activity_order_;
    }

    @Override
    protected void initListener() {
        name = (TextView) findViewById(R.id.name);
        tab = (TabLayout) findViewById(R.id.tab);
        viewpager = (ViewPager) findViewById(R.id.viewpager);

        Intent intent=getIntent();
        String string = intent.getStringExtra("name");
        name.setText(string);


        stringList.add("商品");
        stringList.add("评价");
        stringList.add("商家");
        tab.addTab(tab.newTab().setText(stringList.get(0)));
        tab.addTab(tab.newTab().setText(stringList.get(1)));
        tab.addTab(tab.newTab().setText(stringList.get(2)));

        fragmentList.add(new Shopping_Fragment());
        fragmentList.add(new Evaluate_Fragment());
        fragmentList.add(new Business_Fragment());

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(pagerAdapter);
        tab.setupWithViewPager(viewpager);




    }

    @Override
    protected void getViewId() {

    }

    @Override
    protected void loadData() {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return stringList.get(position);
        }
    }
}
