package com.bawei.util_dierzhou_lianxi2.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bawei.util_dierzhou_lianxi2.api.ApiService;
import com.bawei.util_dierzhou_lianxi2.url.MyUrls;
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
 * Time: 22:55
 */
public class NetUtil {

    private ApiService apiService;

    private NetUtil() {
        //创建日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //创建Ok
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
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

    @SuppressLint("CheckResult")
    public void onGetIofo(String url, final Class cla, final NetCallBack netCallBack) {
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
                            netCallBack.onSuccess(o, string);
                        }
                    }
                });
    }

    public boolean hasNet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }

    private static class NetHolder {
        private static NetUtil netUtil = new NetUtil();
    }

    public static NetUtil getInstance() {
        return NetHolder.netUtil;
    }


    public interface NetCallBack<T> {
        void onSuccess(T t, String json);

        void onError(String error);
    }
}
