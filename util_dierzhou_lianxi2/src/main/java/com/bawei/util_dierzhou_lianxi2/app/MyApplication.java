package com.bawei.util_dierzhou_lianxi2.app;

import android.app.Application;
import android.content.Context;

import com.bawei.util_dierzhou_lianxi2.dao.DaoMaster;
import com.bawei.util_dierzhou_lianxi2.dao.DaoSession;

/**
 * Created by Android Studio.
 * User: 潘世松
 * Date: 2019/12/8
 * Time: 17:36
 */
public class MyApplication extends Application {
    public static Context mContext;
    public static DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        daoSession = DaoMaster.newDevSession(this,"ps.db");
    }
    public static DaoSession getDaoSession(){
        return daoSession;
    }


}
