package com.jiyun.da_one_eleme.controller.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jiyun.da_one_eleme.R;
import com.jiyun.da_one_eleme.controller.base.BaseActivity;
import com.mob.MobSDK;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class Lodding_Activity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SmsYanzheng";
    private Button mButtonGetCode;
    private Button mButtonLogin;
    private EditText mEditTextPhoneNumber;
    private EditText mEditTextCode;
    private EventHandler eventHandler;
    private String strPhoneNumber;
    private ImageView image_qq;
    //////////////////第三方登录//////////////////
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            String name = null;
            String uel = null;

            Intent intent = new Intent();

            Set<String> set = data.keySet();
            for (String s : set) {
                name = data.get("screen_name");
                uel = data.get("profile_image_url");
            }
            intent.putExtra("name", name);
            intent.putExtra("url", uel);
            intent.setAction("cccccc");
            sendBroadcast(intent);
            finish();

            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };


    //////////////////第三方登录//////////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected int initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST);
        return R.layout.activity_deng_lu;
    }

    @Override
    protected void getViewId() {
        mEditTextPhoneNumber = (EditText) findViewById(R.id.phone_number);
        mEditTextCode = (EditText) findViewById(R.id.verification_code);
        mButtonGetCode = (Button) findViewById(R.id.button_send_verification_code);
        mButtonLogin = (Button) findViewById(R.id.button_login);
        image_qq = (ImageView) findViewById(R.id.image_qq);


    }
    @Override
    protected void initListener() {
        mButtonGetCode.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);
        image_qq.setOnClickListener(this);
        ///////////第三方登录////////////////
        image_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UMShareAPI.get(Lodding_Activity.this).getPlatformInfo(Lodding_Activity.this, SHARE_MEDIA.QQ, umAuthListener);
            }
        });


////////////////////////短信验证///////////////////////
        MobSDK.init(this, "2024773d34c53", "d501a093e70333bec22d3979bf831f8b"); //使用你申请的key 和 secret

        eventHandler = new EventHandler() {

            /**
             * 在操作之后被触发
             *
             * @param event  参数1
             * @param result 参数2 SMSSDK.RESULT_COMPLETE表示操作成功，为SMSSDK.RESULT_ERROR表示操作失败
             * @param data   事件操作的结果
             */

            @Override
            public void afterEvent(int event, int result, Object data) {
                Message message = myHandler.obtainMessage(0x00);
                message.arg1 = event;
                message.arg2 = result;
                message.obj = data;
                myHandler.sendMessage(message);
            }
        };

        SMSSDK.registerEventHandler(eventHandler);
    }





    @Override
    protected void loadData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_login) {
                                String strCode = mEditTextCode.getText().toString();
                                if (null != strCode && strCode.length() == 4) {
                                    Log.d(TAG, mEditTextCode.getText().toString());
                                    SMSSDK.submitVerificationCode("86", strPhoneNumber, mEditTextCode.getText().toString());
                                } else {
                                    Toast.makeText(this, "密码长度不正确", Toast.LENGTH_SHORT).show();
                                }
                            } else if (v.getId() == R.id.button_send_verification_code) {
                                strPhoneNumber = mEditTextPhoneNumber.getText().toString();
                                if (null == strPhoneNumber || "".equals(strPhoneNumber) || strPhoneNumber.length() != 11) {
                                    Toast.makeText(this, "电话号码输入有误", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                SMSSDK.getVerificationCode("86", strPhoneNumber);
                                mButtonGetCode.setClickable(false);
                                //开启线程去更新button的text
                                new Thread() {
                                    @Override
                                    public void run() {
                                        int totalTime = 60;
                                        for (int i = 0; i < totalTime; i++) {
                                            Message message = myHandler.obtainMessage(0x01);
                                            message.arg1 = totalTime - i;
                                            myHandler.sendMessage(message);
                                            try {
                                                sleep(1000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                        }
                    }
                    myHandler.sendEmptyMessage(0x02);
                }
            }.start();
        }
    }

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x00:
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    Log.e(TAG, "result : " + result + ", event: " + event + ", data : " + data);
                    if (result == SMSSDK.RESULT_COMPLETE) { //回调  当返回的结果是complete
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) { //获取验证码
                            Toast.makeText(Lodding_Activity.this, "发送验证码成功", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "get verification code successful.");
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) { //提交验证码
                            Log.d(TAG, "submit code successful");
                            Toast.makeText(Lodding_Activity.this, "提交验证码成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Lodding_Activity.this, SecondActivity.class);
                            startActivity(intent);
                        } else {
                            Log.d(TAG, data.toString());
                        }
                    } else { //进行操作出错，通过下面的信息区分析错误原因
                        try {
                            Throwable throwable = (Throwable) data;
                            throwable.printStackTrace();
                            JSONObject object = new JSONObject(throwable.getMessage());
                            String des = object.optString("detail");//错误描述
                            int status = object.optInt("status");//错误代码
                            //错误代码：  http://wiki.mob.com/android-api-%E9%94%99%E8%AF%AF%E7%A0%81%E5%8F%82%E8%80%83/
                            Log.e(TAG, "status: " + status + ", detail: " + des);
                            if (status > 0 && !TextUtils.isEmpty(des)) {
                                Toast.makeText(Lodding_Activity.this, des, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 0x01:
                    mButtonGetCode.setText("重新发送(" + msg.arg1 + ")");
                    break;
                case 0x02:
                    mButtonGetCode.setText("获取验证码");
                    mButtonGetCode.setClickable(true);
                    break;
            }
        }
    };

}
