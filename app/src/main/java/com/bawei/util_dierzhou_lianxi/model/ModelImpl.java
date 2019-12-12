package com.bawei.util_dierzhou_lianxi.model;

import com.bawei.util_dierzhou_lianxi.Contracts;
import com.bawei.util_dierzhou_lianxi.utils.NetUtil;

/**
 * Created by Android Studio.
 * User: 潘世松
 * Date: 2019/12/8
 * Time: 17:38
 */
public class ModelImpl implements Contracts.IModel {
    @Override
    public void onGetIofo(String url, Class cla, final Contracts.MyCallBack myCallBack) {
        NetUtil.getInstance().doGet(url, cla, new NetUtil.NetCallBack() {
            @Override
            public void onSuccess(Object o) {
                myCallBack.onSuccess(o);
            }

            @Override
            public void onError(String error) {
                myCallBack.onError(error);
            }
        });
    }
}
