package com.bawei.util_dierzhou_lianxi2.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bawei.util_dierzhou_lianxi2.Contracts;

/**
 * Created by Android Studio.
 * User: 潘世松
 * Date: 2019/12/8
 * Time: 23:11
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements Contracts.IView {
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutID() != 0) {
            setContentView(getLayoutID());
            initViews();
            mPresenter = iniPresenter();
            mPresenter.onAttch(this);
            startCoding();
        }
    }

    protected abstract void startCoding();

    protected abstract P iniPresenter();

    protected abstract void initViews();

    protected abstract int getLayoutID();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDeAttch();
            mPresenter = null;
        }
    }
}
