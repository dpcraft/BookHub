package com.dpcraft.bookhub.NetModule;

/**
 * Created by DPC on 2017/2/23.
 */
public interface HttpCallBackListener {
    void onFinish(String response,int responseCode);
    void onError(Exception e);
}
