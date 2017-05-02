package com.dpcraft.bookhub.DataClass;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DPC on 2017/5/2.
 */
public class RequestPreview {
    @SerializedName("bw")
    private RequestBookInfo mRequestBookInfo;
    @SerializedName("user")
    private UserInfo mUserInfo;

    public RequestBookInfo getRequestBookInfo() {
        return mRequestBookInfo;
    }

    public void setRequestBookInfo(RequestBookInfo requestBookInfo) {
        mRequestBookInfo = requestBookInfo;
    }

    public UserInfo getUserInfo() {
        return mUserInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        mUserInfo = userInfo;
    }



}
