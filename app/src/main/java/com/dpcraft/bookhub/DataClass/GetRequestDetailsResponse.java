package com.dpcraft.bookhub.DataClass;

import com.google.gson.annotations.SerializedName;


/**
 * Created by DPC on 2017/4/5.
 */
public class GetRequestDetailsResponse {
    @SerializedName("code")
    private int code ;
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private RequestPreview data;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public RequestPreview getData() {
        return data;
    }

    public void setData(RequestPreview data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
