package com.bawei.util_dierzhou_lianxi.utils;

import android.widget.ImageView;

import com.bawei.util_dierzhou_lianxi.app.MyApplication;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by Android Studio.
 * User: 潘世松
 * Date: 2019/12/8
 * Time: 17:35
 */
public class GlideUtil {
    public static void loadImade(String url, ImageView imageView) {
        Glide.with(MyApplication.mContext)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }
}
