package com.dpcraft.bookhub.DataClass;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DPC on 2017/3/24.
 */
public class LoginResponseUserInfo {

    @SerializedName("nickname")
    private String nickName;
    @SerializedName("phonenum")
    private String phoneNumber;


    public String getNickName() {
        return nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
