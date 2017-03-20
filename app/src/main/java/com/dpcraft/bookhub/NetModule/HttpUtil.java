package com.dpcraft.bookhub.NetModule;

import android.util.Log;

import com.dpcraft.bookhub.DataClass.User;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


import okhttp3.*;

/**
 * Created by DPC on 2017/2/23.
 */
public class HttpUtil {
    private static String signupURL = Server.getServerAddress() + "user";

    private  static String loginURL= Server.getServerAddress() + "login";
    public static final MediaType JSON
            = MediaType.parse("application/json");
   public static void sendHttpGetRequest2(final String address,final HttpCallBackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    Log.i("URL",url.toString());
                    Log.i("dpc","get  start");
                    connection = (HttpURLConnection) url.openConnection();
                    Log.i("dpc","connection");
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    int code = connection.getResponseCode();
                    Log.i("dpc code",code + "");
                  BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("response",response.toString());


                    if (listener != null) {
                        listener.onFinish(response.toString(),code);
                    }
                }catch (Exception e){
                    if(listener != null){
                        listener.onError(e);
                        Log.i("error", "error");
                    }
                }finally {
                    if(connection == null){
                        connection.disconnect();
                        Log.i("disc", "disc");
                    }
                }

            }
        }).start();

    }

    public static void sendHttpGetRequest(final String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);

    }
    public static void sendHttpPostRequest(final String address, final User user, final okhttp3.Callback callback){
        Log.i("post url",address);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody;
        if(address == signupURL){
            requestBody = //RequestBody.create(JSON,stringTowrite);
                    new FormBody.Builder()
                            .add("username",user.getUserName())
                            .add("password",user.getPassWord())
                             .add("nickname",user.getNickName())
                            .add("phonenum",user.getPhoneNum())
                            .build();
        }
        else {
         requestBody = //RequestBody.create(JSON,stringTowrite);
               new FormBody.Builder()
                .add("username",user.getUserName())
                .add("password",user.getPassWord())
                .build();
        }
       // Log.i("sendhttppost",stringTowrite);
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);

    }

    public static void sendHttpPostRequest2(final String address,final String stringToWrite,final HttpCallBackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    OutputStreamWriter writer=new OutputStreamWriter(connection.getOutputStream(),"utf-8");
                    writer.write(stringToWrite);
                    writer.flush();
                    int code=connection.getResponseCode();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);

                    }
                    Log.i("Login code", code + "");


                    if (listener != null) {
                        listener.onFinish(response.toString(),code);
                    }
                }catch (Exception e){
                    if(listener != null){
                        listener.onError(e);
                    }
                }finally {
                    if(connection == null){
                        connection.disconnect();
                    }
                }

            }
        }).start();

    }


}
