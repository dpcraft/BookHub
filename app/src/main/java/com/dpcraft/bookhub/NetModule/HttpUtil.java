package com.dpcraft.bookhub.NetModule;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;

/**
 * Created by DPC on 2017/2/23.
 */
public class HttpUtil {
    public static void sendHttpGetRequest(final String address,final HttpCallBackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    Log.i("dpc","get  start");
                    connection = (HttpURLConnection) url.openConnection();
                    Log.i("dpc","connection");
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(1000);
                    connection.setReadTimeout(1000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    int code = connection.getResponseCode();

                    Log.i("dpc code",code + "");
                    BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
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
    public static void sendHttpPostRequest(final String address,final String stringToWrite,final HttpCallBackListener listener){
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
                    BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

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
