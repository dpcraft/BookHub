package com.dpcraft.bookhub.DataClass;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DPC on 2017/3/23.
 */
public class GetBookResponse {
    private int code ;
    @SerializedName("message")
    private String message;


    @SerializedName("data")
    private List<BookPreview> data;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public List<BookPreview> getData() {
        return data;
    }

    public void setData(List<BookPreview> data) {
        this.data = data;
    }





}
