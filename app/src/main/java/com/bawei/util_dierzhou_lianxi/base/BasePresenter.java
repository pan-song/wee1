package com.bawei.util_dierzhou_lianxi.base;

import com.bawei.util_dierzhou_lianxi.Contracts;

import java.lang.ref.WeakReference;

/**
 * Created by Android Studio.
 * User: 潘世松
 * Date: 2019/12/8
 * Time: 17:39
 */
public abstract class BasePresenter<V extends Contracts.IView> implements Contracts.IPresenter {
    private WeakReference<V> mWeakR;

    public BasePresenter() {
        initModel();
    }

    protected abstract void initModel();

    protected void onAttch(V v) {
        mWeakR = new WeakReference<>(v);
    }

    protected void onDeAttch() {
        if (mWeakR != null) {
            mWeakR.clear();
            mWeakR = null;
        }
    }

    protected V getView() {
        return mWeakR.get();
    }
}
