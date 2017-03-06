package com.dpcraft.bookhub.Application;

import android.app.Application;

/**
 * Created by DPC on 2017/3/6.
 */
public class MyApplication extends Application{
    private boolean loginStatus;
    public void setLoginStatus(boolean loginStatus){
        this.loginStatus = loginStatus;
    }
    public boolean getLoginStatus(){
        return loginStatus;
    }
    public boolean isLogin(){
       return loginStatus;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        loginStatus = false;
    }

}
