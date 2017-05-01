package com.dpcraft.bookhub.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import com.dpcraft.bookhub.NetModule.NetState;
import com.dpcraft.bookhub.NetModule.NetWorkStateReceiver;

/**
 * Created by DPC on 2017/5/1.
 */
public class BaseActivity extends Activity {

    NetWorkStateReceiver mNetWorkStateReceiver;

    @Override
    protected void onResume(){
        NetState.isConnection(this);
        if(mNetWorkStateReceiver == null){
            mNetWorkStateReceiver = new NetWorkStateReceiver();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetWorkStateReceiver, intentFilter);
        Log.i("网络连接检测", "注册事件");
        super.onResume();
    }
    @Override
    protected void onPause(){
        unregisterReceiver(mNetWorkStateReceiver);
        Log.i("网络连接检测", "注销事件");
        super.onPause();
    }



}
