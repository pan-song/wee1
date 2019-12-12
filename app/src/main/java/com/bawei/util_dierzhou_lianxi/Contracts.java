package com.bawei.util_dierzhou_lianxi;

/**
 * Created by Android Studio.
 * User: 潘世松
 * Date: 2019/12/8
 * Time: 17:37
 */
public interface Contracts {

    interface IModel{
        void onGetIofo(String url,Class cla,MyCallBack myCallBack);
    }

    interface IView<T>{
        void onSuccess(T t);
        void onError(String error);
    }

    interface IPresenter{
        void startRequest(String url,Class cla);
    }

    interface MyCallBack<T>{
        void onSuccess(T t);
        void onError(String error);
    }
}
