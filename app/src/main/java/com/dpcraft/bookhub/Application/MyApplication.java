package com.dpcraft.bookhub.Application;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.dpcraft.bookhub.DataClass.LoginResponseUserInfo;

/**
 * Created by DPC on 2017/3/6.
 */
public class MyApplication extends Application{


    private static MyApplication mMyApplication;
    private boolean isNetWorkConnected;
    private Boolean loginStatus;


    private LoginResponseUserInfo loginResponseUserInfo;

    private String token;

    public boolean isNetWorkConnected() {
        return isNetWorkConnected;
    }

    public void setNetWorkConnected(boolean netWorkConnected) {
        isNetWorkConnected = netWorkConnected;
    }
    public void setLoginStatus(Boolean loginStatus){

        this.loginStatus = loginStatus;
    }

    public Boolean isLogin(){

        return loginStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {

        this.token = token;
    }

    public LoginResponseUserInfo getLoginResponseUserInfo() {
        return loginResponseUserInfo;
    }

    public void setLoginResponseUserInfo(LoginResponseUserInfo loginResponseUserInfo) {
        this.loginResponseUserInfo = loginResponseUserInfo;
    }
    public static MyApplication getInstance(){
        return mMyApplication;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        mMyApplication = this;
        isNetWorkConnected = false;
        loginStatus = false;
        token = "";
        loginResponseUserInfo = new LoginResponseUserInfo();
        Log.i("APPLICATION","application is rebuild.");
    }

}
