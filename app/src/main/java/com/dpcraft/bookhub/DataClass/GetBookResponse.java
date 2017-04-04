package com.dpcraft.bookhub.DataClass;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DPC on 2017/3/23.
 */
public class GetBookResponse {
    private int code ;
    private String msg;


    @SerializedName("data")
    private List<BookPreview> data;
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
    public List<BookPreview> getData() {
        return data;
    }

    public void setData(List<BookPreview> data) {
        this.data = data;
    }





}
