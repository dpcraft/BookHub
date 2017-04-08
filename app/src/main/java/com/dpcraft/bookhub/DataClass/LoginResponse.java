package com.dpcraft.bookhub.DataClass;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DPC on 2017/3/24.
 */
public class LoginResponse {
    private int code;
    private String message;
    @SerializedName("data")
    private LoginResponseUserInfo data;
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public LoginResponseUserInfo getData() {
        return data;
    }
}
