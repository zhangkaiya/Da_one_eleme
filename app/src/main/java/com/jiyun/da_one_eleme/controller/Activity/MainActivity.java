package com.jiyun.da_one_eleme.controller.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import com.jiyun.da_one_eleme.R;
import com.jiyun.da_one_eleme.controller.base.BaseActivity;
import com.jiyun.da_one_eleme.view.LockView;

import java.util.List;

public class MainActivity extends BaseActivity {
    private Button btnCancle;
    private Button btnOk;
    private LockView lvLock;

    @Override
    protected int initView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initListener() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvLock.drawTwo();
            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvLock.cancel();
                btnOk.setEnabled(false);
                btnOk.setText("继续");
                btnOk.setTextColor(Color.GRAY);
                btnCancle.setEnabled(false);
                btnOk.setTextColor(Color.GRAY);
            }
        });

    }

    @Override
    protected void getViewId() {
        lvLock = (LockView) findViewById(R.id.lv_lock);
        btnCancle = (Button) findViewById(R.id.btn_cancle);
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnOk.setEnabled(false);
        btnOk.setTextColor(Color.GRAY);
        btnCancle.setEnabled(false);
        btnOk.setTextColor(Color.GRAY);
        lvLock.setOnDrawFinish(new LockView.OnDrawFinish() {
            @Override
            public void oneDraw(List<Integer> passwords) {
                btnOk.setEnabled(true);
                btnOk.setText("继续");
                btnOk.setTextColor(Color.BLACK);
                btnCancle.setText("重绘");
                btnCancle.setEnabled(true);
                btnOk.setTextColor(Color.BLACK);
            }

            @Override
            public void twoDraw(boolean isOk) {
                if (isOk) {
                    btnOk.setEnabled(true);
                    btnOk.setText("确定");
                    btnOk.setTextColor(Color.BLACK);
                    btnCancle.setText("重绘");
                    btnCancle.setEnabled(true);
                    btnOk.setTextColor(Color.BLACK);
                    Intent intent=new Intent(MainActivity.this,Restart_Main_Activity.class);
                    startActivity(intent);
                    finish();
                } else {
                    btnOk.setText("两次绘制不一致");
                }
            }
        });
    }

    @Override
    protected void loadData() {

    }

}
