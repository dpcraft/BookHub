package com.dpcraft.bookhub.NetModule;

import android.app.Application;

/**
 * Created by DPC on 2017/2/24.
 */
public class MyApplication extends Application {

    private String token;
    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
