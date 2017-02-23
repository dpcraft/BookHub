package com.dpcraft.bookhub.NetModule;

import android.util.Log;

import com.dpcraft.bookhub.DataClass.User;

import java.net.ResponseCache;
import java.net.URL;

/**
 * Created by DPC on 2017/2/23.
 */
public class NetUtils {

        private static String SIGNUPURL = "http://bookp2p.imwork.net:10142/BooksServer/user";

        private static String LOGINURL="http://bookp2p.imwork.net:10142/BooksServer/login";

        private static String PHOTOURL="http://bookp2p.imwork.net:10142/BooksServer/user/photo";

        public static void signup(User user)throws Exception{
             /*

         * 使用POST方法请求注册操作

         * url地址不加任何后缀

         * 如果注册成功会在result中返回JSON格式的code为201，message为注册成功

         * 如果注册不成功，会在result中返回JSON格式的code为409（某个值已存在，如用户名）或400（验证错误，数据不符合规范）

         */

            JSONUtil jsonUtil = new JSONUtil();
            String str = jsonUtil.packageJson(user);
            HttpUtil.sendHttpPostRequest(SIGNUPURL, str, new HttpCallBackListener() {
                @Override
                public void onFinish(String response, int responseCode) {
                    Log.i("signup code",responseCode+"");
                }

                @Override
                public void onError(Exception e) {
                    Log.i("signup error","");

                }
            });
        }

    public static void login(User user) throws Exception {

        /*

         * 使用GET方法请求登录操作

         * url地址后加username和password

         * 如果登录成功会在result中返回JSON格式的code为201

         * message为Token令牌，作其他操作时提交令牌即可获得权限，令牌有时限

         */

        //String loginUrl = LOGINURL + "?username=" + user.getUserName() + "&password=" + user.getPassWord();
        String loginUrl = "http://www.uestc.edu.cn";
        Log.i("urlString",loginUrl);
        HttpUtil.sendHttpGetRequest(loginUrl, new HttpCallBackListener() {
            @Override
            public void onFinish(String response, int responseCode) {
                Log.i("Login code", responseCode + "");
            }

            @Override
            public void onError(Exception e) {
                Log.i("Login error","");

            }
        });
    }
}
