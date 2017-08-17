package com.jiyun.da_one_eleme.controller.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

import com.jiyun.da_one_eleme.R;
import com.jiyun.da_one_eleme.controller.Fragment.Homepage_Fragment;
import com.jiyun.da_one_eleme.controller.Fragment.More_Fragment;
import com.jiyun.da_one_eleme.controller.Fragment.Order_Fragment;
import com.jiyun.da_one_eleme.controller.Fragment.Person_Fragment;
import com.jiyun.da_one_eleme.controller.base.BaseActivity;

public class Restart_Main_Activity extends BaseActivity implements View.OnClickListener {
        private RadioButton RadioButton_1;
        private RadioButton RadioButton_2;
        private RadioButton RadioButton_3;
        private RadioButton RadioButton_4;
        private Homepage_Fragment shouyeFragment;
        private Order_Fragment dingdanFragment ;
        private More_Fragment gengduoFrahment ;
        private Person_Fragment gerenFragment ;
        private String url="http://123.206.14.104:8080/TakeoutService/home?latitude=116.30142&longitude=40.05087";
        @Override
        protected int initView() {
            return R.layout.activity_restart__main_;
        }

        @Override
        protected void initListener() {
            RadioButton_1.setOnClickListener(this);
            RadioButton_2.setOnClickListener(this);
            RadioButton_3.setOnClickListener(this);
            RadioButton_4.setOnClickListener(this);

        }

        @Override
        protected void getViewId() {
            RadioButton_1 = (RadioButton) findViewById(R.id.RadioButton_1);
            RadioButton_2 = (RadioButton) findViewById(R.id.RadioButton_2);
            RadioButton_3 = (RadioButton) findViewById(R.id.RadioButton_3);
            RadioButton_4 = (RadioButton) findViewById(R.id.RadioButton_4);
        }

        @Override
        protected void loadData() {
            shouyeFragment= new Homepage_Fragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragment,shouyeFragment);
            transaction.show(shouyeFragment);
            transaction.commit();
        }

        @Override
        public void onClick(View v) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            initHide(transaction);
            switch (v.getId()) {
                case R.id.RadioButton_1:
                    if (shouyeFragment == null) {
                        shouyeFragment = new Homepage_Fragment();
                        transaction.add(R.id.fragment, shouyeFragment);
                    } else {
                        transaction.show(shouyeFragment);
                    }
                    break;
                case R.id.RadioButton_2:
                    if (dingdanFragment == null) {
                        dingdanFragment = new Order_Fragment();
                        transaction.add(R.id.fragment, dingdanFragment);

                    } else {
                        transaction.show(dingdanFragment);
                    }
                    break;
                case R.id.RadioButton_3:
                    if (gerenFragment == null) {
                        gerenFragment = new Person_Fragment();
                        transaction.add(R.id.fragment, gerenFragment);
                    } else {
                        transaction.show(gerenFragment);
                    }
                    break;
                case R.id.RadioButton_4:
                    if (gengduoFrahment == null) {
                        gengduoFrahment = new More_Fragment();
                        transaction.add(R.id.fragment, gengduoFrahment);
                    } else {
                        transaction.show(gengduoFrahment);
                    }
                    break;
            }
            transaction.commit();
        }

        private void initHide(FragmentTransaction transaction) {
            if (shouyeFragment != null) {
                transaction.hide(shouyeFragment);
            }
            if (dingdanFragment != null) {
                transaction.hide(dingdanFragment);
            }
            if (gerenFragment != null) {
                transaction.hide(gerenFragment);
            }
            if (gengduoFrahment != null) {
                transaction.hide(gengduoFrahment);
            }
        }
}
