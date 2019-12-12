package com.bawei.util_dierzhou_lianxi2.model;

import com.bawei.util_dierzhou_lianxi2.Contracts;
import com.bawei.util_dierzhou_lianxi2.utils.NetUtil;

/**
 * Created by Android Studio.
 * User: 潘世松
 * Date: 2019/12/8
 * Time: 23:13
 */
public class ModelImpl implements Contracts.IModel {
    @Override
    public void onGetIofo(String url, Class cla, final Contracts.MyCallBack myCallBack) {
        NetUtil.getInstance().onGetIofo(url, cla, new NetUtil.NetCallBack() {
            @Override
            public void onSuccess(Object o,String json) {
                myCallBack.onSuccess(o,json);
            }

            @Override
            public void onError(String error) {
                myCallBack.onError(error);
            }
        });
    }
}
