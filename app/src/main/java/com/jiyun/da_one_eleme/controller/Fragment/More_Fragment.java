package com.jiyun.da_one_eleme.controller.Fragment;


import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import com.jiyun.da_one_eleme.R;
import com.jiyun.da_one_eleme.controller.base.BaseFragment;
import com.jiyun.da_one_eleme.view.LockView;

import java.util.List;

public class More_Fragment extends BaseFragment {
    private Button btnCancle;
    private Button btnOk;
    private LockView lvLock;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_geng_duo;
    }

    @Override
    protected void initView(View view) {
        lvLock = (LockView) view.findViewById(R.id.lv_lock);
        btnCancle = (Button)view. findViewById(R.id.btn_cancle);
        btnOk = (Button) view.findViewById(R.id.btn_ok);
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
                } else {
                    btnOk.setText("两次绘制不一致");
                }
            }
        });
    }


    @Override
    protected void loadData() {

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
}
