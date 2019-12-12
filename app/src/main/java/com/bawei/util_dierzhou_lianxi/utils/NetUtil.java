package com.bawei.util_dierzhou_lianxi.utils;

import android.annotation.SuppressLint;

import com.bawei.util_dierzhou_lianxi.api.ApiService;
import com.bawei.util_dierzhou_lianxi.url.MyUrls;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Android Studio.
 * User: 潘世松
 * Date: 2019/12/8
 * Time: 17:29
 */
public class NetUtil {

    private ApiService apiService;

    private NetUtil() {
        //创建日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //OK请求
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //添加日志拦截器
                .addInterceptor(interceptor)
                .build();

        //初始化Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyUrls.BASEURL)
                //关联OK
                .client(okHttpClient)
                //关联RxJava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //关联Gson
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //初始化ApiService
        apiService = retrofit.create(ApiService.class);
    }

    private static class NetHolder {
        private static NetUtil netUtil = new NetUtil();
    }

    public static NetUtil getInstance() {
        return NetHolder.netUtil;
    }

    @SuppressLint("CheckResult")
    public void doGet(String url, final Class cla, final NetCallBack netCallBack) {
        apiService.getIofo(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        Gson gson = new Gson();

                        if (netCallBack != null) {
                            String string = responseBody.string();
                            Object o = gson.fromJson(string, cla);
                            netCallBack.onSuccess(o);
                        }

                    }
                });
    }

    public interface NetCallBack<T> {
        void onSuccess(T t);

        void onError(String error);
    }
}
