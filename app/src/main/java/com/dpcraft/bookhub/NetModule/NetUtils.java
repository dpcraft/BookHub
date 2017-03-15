package com.dpcraft.bookhub.NetModule;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.dpcraft.bookhub.DataClass.BookGetRequestInformation;
import com.dpcraft.bookhub.DataClass.User;

/**
 * Created by DPC on 2017/2/23.
 */
public class NetUtils {

        private static String SIGNUPURL = Server.getServerAddress() + "user";

        //private static String LOGINURL="http://bookp2p.imwork.net:10142/BooksServer/login";
        private static String LOGINURL= Server.getServerAddress() + "login";

        private static String PHOTOURL="http://bookp2p.imwork.net:10142/BooksServer/user/photo";

        public static void signup(User user, final Handler handler)throws Exception{
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
                    Log.i("response",response.toString());
                    int code = JSONUtil.parseJSONwithGSON(response).getCode();
                    String Information = JSONUtil.parseJSONwithGSON(response).getMessage();
                    Log.i("JSON code",code+"");
                    Message message = handler.obtainMessage();//创建message的方式，可以更好地被回收
                    message.what = code;
                    message.obj = Information;
                    handler.sendMessage(message);
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
       // String loginUrl = "http://www.uestc.edu.cn";
       // Log.i("urlString",loginUrl);
        JSONUtil jsonUtil = new JSONUtil();
        String str = jsonUtil.packageJson(user);
        HttpUtil.sendHttpPostRequest(LOGINURL,str,new HttpCallBackListener() {
            @Override
            public void onFinish(String response, int responseCode) {
                Log.i("Login code", responseCode + "");
                Log.i("response",response.toString());

            }

            @Override
            public void onError(Exception e) {
                Log.i("Login error","");

            }
        });
    }



    public static void getBookList(BookGetRequestInformation bookGetRequestInformation) throws Exception {

       // JSONUtil jsonUtil = new JSONUtil();
        //String str = jsonUtil.packageJson(user);
        String url =  "https://api.douban.com/v2/book/isbn/9787308083256";//"https://api.douban.com/v2/book/isbn/9787308083256";

       // HttpUtil.sendHttpGetRequest(bookGetRequestInformation.generateURL(), new HttpCallBackListener() {
        HttpUtil.sendHttpGetRequest(url, new HttpCallBackListener() {
            @Override
            public void onFinish(String response, int responseCode) {
                Log.i("booklistresponse",response.toString());
            }

            @Override
            public void onError(Exception e) {
                Log.i("Login error","");

            }
        });

    }
}
