package com.bawei.util_dierzhou_lianxi2.presenter;

import com.bawei.util_dierzhou_lianxi2.Contracts;
import com.bawei.util_dierzhou_lianxi2.base.BasePresenter;
import com.bawei.util_dierzhou_lianxi2.model.ModelImpl;

/**
 * Created by Android Studio.
 * User: 潘世松
 * Date: 2019/12/8
 * Time: 23:14
 */
public class PresenterImpl extends BasePresenter {

    private ModelImpl model;

    @Override
    protected void initModel() {
        model = new ModelImpl();
    }

    @Override
    public void startRequest(String url, Class cla) {
        model.onGetIofo(url, cla, new Contracts.MyCallBack() {
            @Override
            public void onSuccess(Object o,String json) {
                getView().onSuccess(o,json);
            }

            @Override
            public void onError(String error) {
                getView().onError(error);
            }
        });
    }
}
