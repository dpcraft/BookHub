package com.dpcraft.bookhub.NetModule;

import android.util.Log;

import com.dpcraft.bookhub.DataClass.User;
import com.google.gson.Gson;

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
    public static Response parseJSONwithGSON( String jsonData){
        Gson gson = new Gson();
        Response response = gson.fromJson(jsonData,Response.class);
        return  response;
    }

}
