package com.bawei.util_dierzhou_lianxi.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bawei.util_dierzhou_lianxi.Contracts;

/**
 * Created by Android Studio.
 * User: 潘世松
 * Date: 2019/12/8
 * Time: 17:41
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements Contracts.IView {
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getLayoutID() != 0) {
            setContentView(getLayoutID());
            initViews();
            mPresenter = initPresenter();
            mPresenter.onAttch(this);
            startCoding();
        }
    }

    protected abstract void startCoding();

    protected abstract P initPresenter();

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
