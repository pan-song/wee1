package com.bawei.util_dierzhou_lianxi.presenter;

import com.bawei.util_dierzhou_lianxi.Contracts;
import com.bawei.util_dierzhou_lianxi.base.BasePresenter;
import com.bawei.util_dierzhou_lianxi.model.ModelImpl;

/**
 * Created by Android Studio.
 * User: 潘世松
 * Date: 2019/12/8
 * Time: 17:43
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
            public void onSuccess(Object o) {
                getView().onSuccess(o);
            }

            @Override
            public void onError(String error) {
                getView().onError(error);
            }
        });
    }
}
