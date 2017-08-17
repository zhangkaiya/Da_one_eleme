package com.jiyun.da_one_eleme.modle.Okhttp;

import android.os.Handler;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 张凯雅 on 2017/8/10.
 */

public class OkHttpManager {
    private OkHttpClient client;
    Handler handler;
    private static OkHttpManager OkHttpManager;

    public OkHttpManager() {
        handler=new Handler();
        client = new OkHttpClient();
    }


    public static OkHttpManager getInstance() {
        if (OkHttpManager == null) {
            synchronized (OkHttpManager.class) {
                if (OkHttpManager == null) {
                    OkHttpManager = new OkHttpManager();
                }
            }

        }
        return OkHttpManager;
    }
    //创建接口是为了实现异步调用
    public interface CallBacks{

        void getString(String s);
    }


    public void getNet(String url, final CallBacks callBacks) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                //为什么要使用handler.post是为了把内容发送到主线程中
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //run里面的方法都在主线中
                       callBacks.getString(string);
                    }
                });
            }
        });
    }
}
