package com.dpcraft.bookhub.NetModule;

import android.util.Log;

import com.dpcraft.bookhub.DataClass.BookPreview;
import com.dpcraft.bookhub.DataClass.GetBookResponse;
import com.dpcraft.bookhub.DataClass.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
    public static SignupResponse parseJSONwithGSON(String jsonData){
        Gson gson = new Gson();
        SignupResponse response = gson.fromJson(jsonData,SignupResponse.class);
        return  response;
    }

    public static GetBookResponse parseGetBookResponse(String str) {

        //GSON直接解析成对象
        GetBookResponse getBookResponse = new Gson().fromJson(str,GetBookResponse.class);
        //对象中拿到集合
        //List<BookPreview> bookPreviewList = getBookResponse.getBookPreviewList();
        return getBookResponse;
    }

}
