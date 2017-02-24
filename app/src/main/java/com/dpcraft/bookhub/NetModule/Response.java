package com.dpcraft.bookhub.NetModule;

/**
 * Created by DPC on 2017/2/24.
 */
public class Response {
    private int code;
    //private String data;
    private String message;
    public int getCode(){
        return code;
    }
    public void setCode(int code){
        this.code = code;
    }
    /*public String getData(){
        return data;
    }
    public void setData(String data){
        this.data = data;
    }*/
    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
}
