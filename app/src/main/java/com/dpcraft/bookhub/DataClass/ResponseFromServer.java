package com.dpcraft.bookhub.DataClass;

/**
 * Created by DPC on 2017/2/24.
 */
public class ResponseFromServer {
    private int code;
    private String data;
    private String message;
    public int getCode(){
        return code;
    }
    public String getMessage(){
        return message;
    }

}
