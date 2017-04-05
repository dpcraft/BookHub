package com.dpcraft.bookhub.DataClass;

import com.google.gson.annotations.SerializedName;



/**
 * Created by DPC on 2017/4/5.
 */
public class GetBookDetailsResponse {
    private int code ;
    private String msg;


    @SerializedName("data")
    private BookDetails data;
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
    public BookDetails getData() {
        return data;
    }

    public void setData(BookDetails data) {
        this.data = data;
    }

}
