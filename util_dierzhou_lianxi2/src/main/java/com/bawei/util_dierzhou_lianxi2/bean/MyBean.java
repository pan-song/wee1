package com.bawei.util_dierzhou_lianxi2.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Android Studio.
 * User: 潘世松
 * Date: 2019/12/9
 * Time: 0:34
 */
@Entity
public class MyBean {
    String jsonStr;

    @Generated(hash = 1064746827)
    public MyBean(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    @Generated(hash = 1281580447)
    public MyBean() {
    }

    public String getJsonStr() {
        return this.jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }
    
}
