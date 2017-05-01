package com.dpcraft.bookhub.NetModule;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.dpcraft.bookhub.Application.MyApplication;

/**
 * Created by DPC on 2017/5/1.
 */
public class NetState {

    //检测当前的网络状态
    public static boolean isConnection(Context context){
        //API版本23以下时调用此方法进行检测
        //因为API23后getNetworkInfo(int networkType)方法被弃用
        //public void checkState_23()
        boolean conneceted = false;
        if(Build.VERSION.SDK_INT < 23)
        {
            //步骤1：通过Context.getSystemService(Context.CONNECTIVITY_SERVICE)获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //步骤2：获取ConnectivityManager对象对应的NetworkInfo对象
            //NetworkInfo对象包含网络连接的所有信息
            //步骤3：根据需要取出网络连接信息
            //获取WIFI连接的信息
            NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            Boolean isWifiConn = networkInfo.isConnected();

            //获取移动数据连接的信息
            networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            Boolean isMobileConn = networkInfo.isConnected();

            Log.i("WIFI STATE======","Wifi是否连接:" + isWifiConn);
            Log.i("MOBI STATE======","移动数据是否连接:" + isMobileConn);
            if(!isMobileConn && !isWifiConn){
                conneceted = false;
            }
            else {
               conneceted = true;
            }


        }

        // API 23及以上时调用此方法进行网络的检测
// getAllNetworks() 在API 21后开始使用
//步骤非常类似
        //public void checkState_23orNew()
        if(Build.VERSION.SDK_INT >= 23)
        {
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取所有网络连接的信息
            Network[] networks = connMgr.getAllNetworks();
            //用于存放网络连接信息
            StringBuilder sb = new StringBuilder();
            //通过循环将网络信息逐个取出来
            for (int i=0; i < networks.length; i++){
                //获取ConnectivityManager对象对应的NetworkInfo对象
                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected());
                Log.i(networkInfo.getTypeName()+ " connect is ", networkInfo.isConnected() + "");
                if(networkInfo.isConnected()){
                   conneceted = true;
                }
            }
        }
        if(!conneceted){
            Toast.makeText(context ,"网络连接不可用",Toast.LENGTH_SHORT).show();
        }
        MyApplication.getInstance().setNetWorkConnected(conneceted);
        return conneceted;
    }
}
