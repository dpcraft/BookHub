package com.dpcraft.bookhub.Application;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.dpcraft.bookhub.MainActivity;

/**
 * Created by DPC on 2017/3/6.
 */
public class MyApplication extends Application{
    private Boolean loginStatus;
    private String nextActivity;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;
    public void setLoginStatus(Boolean loginStatus){
        this.loginStatus = loginStatus;
    }
    public Boolean getLoginStatus(){

        return loginStatus;
    }
    public Boolean isLogin(){

        return loginStatus;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        loginStatus = false;
        nextActivity = null;
        token = "";
        Log.i("APPLICATION","application is rebuild.");
    }

}
