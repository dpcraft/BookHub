package com.dpcraft.bookhub.DataClass;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DPC on 2017/3/26.
 */
public class UserInfoResponse {
    private int code;
    private String message;
    @SerializedName("data")
    private UserInfo data;
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public UserInfo getData() {
        return data;
    }
}
