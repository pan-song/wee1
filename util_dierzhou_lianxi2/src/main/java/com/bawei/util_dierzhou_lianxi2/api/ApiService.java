package com.bawei.util_dierzhou_lianxi2.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Android Studio.
 * User: 潘世松
 * Date: 2019/12/8
 * Time: 22:53
 */
public interface ApiService {
    @GET
    Observable<ResponseBody> getIofo(@Url String url);
}
