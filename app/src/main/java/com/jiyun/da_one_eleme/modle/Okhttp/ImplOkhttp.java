package com.jiyun.da_one_eleme.modle.Okhttp;

/**
 * Created by 张凯雅 on 2017/8/12.
 */

public class ImplOkhttp implements Okhttp {
    @Override
    public void doget(String url, final CallBack callBack) {
        OkHttpManager manager=new OkHttpManager();
        manager.getNet(url, new OkHttpManager.CallBacks() {
            @Override
            public void getString(String s) {
                callBack.success(s);
            }

});
    }
}