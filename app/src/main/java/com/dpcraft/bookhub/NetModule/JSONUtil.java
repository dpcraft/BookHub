package com.dpcraft.bookhub.NetModule;

import android.util.Log;

import com.dpcraft.bookhub.DataClass.GetBookResponse;
import com.dpcraft.bookhub.DataClass.LoginResponse;
import com.dpcraft.bookhub.DataClass.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DPC on 2017/2/23.
 */
public class JSONUtil {

        public String packageJson(User user){

            String str=null;

            try {

                JSONObject jsonObject=new JSONObject();

                jsonObject.put("username",user.getUserName());

                jsonObject.put("password",user.getPassWord());

                jsonObject.put("nickname",user.getNickName());

                jsonObject.put("phonenum",user.getPhoneNum());

                str=jsonObject.toString();

                Log.i("packageJson",str);

            } catch (JSONException e) {

                e.printStackTrace();

            }

            return str;

        }

    public static int getResponseCode(String jsonData ){
        int code = 0;
        try{
            JSONObject jsonObject =new JSONObject(jsonData);
            String codeStr = jsonObject.getString("code");
            code = Integer.parseInt(codeStr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return code;

    }


    public static <T> T parseJsonWithGson(String jsonString,Class<T> type){
        Gson gson = new Gson();
            T result = gson.fromJson(jsonString, type);
            return result;


    }


}
