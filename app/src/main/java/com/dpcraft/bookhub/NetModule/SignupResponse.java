package com.dpcraft.bookhub.NetModule;

/**
 * Created by DPC on 2017/2/24.
 */
public class SignupResponse {
    private int code;
    private String message;
    public int getCode(){
        return code;
    }
    public void setCode(int code){
        this.code = code;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
}
