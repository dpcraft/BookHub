package com.dpcraft.bookhub.DataClass;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DPC on 2017/4/15.
 */
public class GetBookDetailsIncludeUserResponse {
    private int code ;
    private String msg;
    @SerializedName("data")
    private BookDetailsIncludeUser data;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BookDetailsIncludeUser getData() {
        return data;
    }

    public void setData(BookDetailsIncludeUser data) {
        this.data = data;
    }


}
