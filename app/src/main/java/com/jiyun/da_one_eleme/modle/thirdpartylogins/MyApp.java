package com.jiyun.da_one_eleme.modle.thirdpartylogins;

import android.app.Application;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by 张凯雅 on 2017/8/14.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
        Config.DEBUG=true;
        PlatformConfig.setQQZone("100424468","c7394704798a158208a74ab60104f0ba");

    }
}
