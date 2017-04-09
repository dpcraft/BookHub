package com.dpcraft.bookhub.NetModule;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.dpcraft.bookhub.DataClass.BookGetRequestInformation;
import com.dpcraft.bookhub.DataClass.LoginResponse;
import com.dpcraft.bookhub.DataClass.SignupResponse;
import com.dpcraft.bookhub.DataClass.UploadBookInfo;
import com.dpcraft.bookhub.DataClass.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.*;

/**
 * Created by DPC on 2017/2/23.
 */
public class NetUtils {

        private static String signupURL = Server.getServerAddress() + "user";

        private  static String loginURL= Server.getServerAddress() + "login";

        private static String PHOTOURL="http://bookp2p.imwork.net:10142/BooksServer/user/photo";




        public static void signup(User user, final Handler handler)throws Exception{
             /*

         * 使用POST方法请求注册操作

         * url地址不加任何后缀

         * 如果注册成功会在result中返回JSON格式的code为201，message为注册成功

         * 如果注册不成功，会在result中返回JSON格式的code为409（某个值已存在，如用户名）或400（验证错误，数据不符合规范）

         */

           /*JSONUtil jsonUtil = new JSONUtil();
            String str = jsonUtil.packageJson(user);
            HttpUtil.sendHttpPostRequest2(signupURL, str, new HttpCallBackListener() {
                @Override
                public void onFinish(String response, int responseCode) {
                    Log.i("signup code",responseCode+"");
                    Log.i("response",response.toString());
                    int code = JSONUtil.parseSignupResponse(response).getCode();
                    String Information = JSONUtil.parseSignupResponse(response).getMessage();
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
            });*/
            HttpUtil.sendHttpPostRequest(signupURL, user, new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {

                    String responseBody = response.body().string();
                    Log.i("signup code",response.code() + "");
                    Log.i("response.body",responseBody);
                    SignupResponse signupResponse = JSONUtil.parseJsonWithGson(responseBody,SignupResponse.class);
                    int code = signupResponse.getCode();
                    String Information = signupResponse.getMessage();
                    Log.i("JSON code",code+"");
                    Message message = handler.obtainMessage();//创建message的方式，可以更好地被回收
                    message.what = code;
                    message.obj = Information;
                    handler.sendMessage(message);
                }
            });
        }
    public static void login(User user,final Handler handler) throws Exception {

        /*

         * 如果登录成功会在result中返回JSON格式的code为201

         * message为Token令牌，作其他操作时提交令牌即可获得权限，令牌有时限

         */

//        JSONUtil jsonUtil = new JSONUtil();
//        String str = jsonUtil.packageJson(user);
        HttpUtil.sendHttpPostRequest(loginURL, user, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("login error", "error" );
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String responseBody = response.body().string();
                Log.i("login code",response.code() + "");
                Log.i("response.body",responseBody);
                int code = JSONUtil.parseJsonWithGson(responseBody,LoginResponse.class).getCode();
                Log.i("JSON code",code+"");
                Message message = handler.obtainMessage();//创建message的方式，可以更好地被回收
                message.what = code;
                message.obj = responseBody;
                Log.i("firstmessge.obj",message.obj.toString());
                handler.sendMessage(message);

            }
        });
    }
    public static void uploadBook(UploadBookInfo uploadBookInfo,String token, final Handler handler) throws Exception {

        HttpUtil.sendHttpPostRequest(Server.getServerAddress() + "book" ,token , uploadBookInfo, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("upload error", "error" );
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String responseBody = response.body().string();
                Log.i("login code",response.code() + "");
                Log.i("response.body",responseBody);
               // int code = JSONUtil.parseJsonWithGson(responseBody,LoginResponse.class).getCode();
               // Log.i("JSON code",code+"");
                //Message message = handler.obtainMessage();//创建message的方式，可以更好地被回收
                //message.what = code;
               // message.obj = responseBody;
                //Log.i("firstmessge.obj",message.obj.toString());
                //handler.sendMessage(message);

            }
        });
    }





    public static void getBookList(BookGetRequestInformation bookGetRequestInformation,final Handler handler)  {


       HttpUtil.sendHttpGetRequest(bookGetRequestInformation.generateURL(), new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
               Log.i("onFailure","onFailure");

           }

           @Override
           public void onResponse(Call call, okhttp3.Response response) throws IOException {
               if (!response.isSuccessful())
               {
                   throw new IOException("Unexpected code " + response);

               }
               String responseBody =  response.body().string();
               Log.i("okhttp3.response",responseBody);
               int code = 201;
               Message message = handler.obtainMessage();//创建message的方式，可以更好地被回收
               message.what = code;
               message.obj = responseBody;
               handler.sendMessage(message);
           }
       });

    }

    /*
     * The method used to get details of the book
     *
     */
    public static void getBookDetails(BookGetRequestInformation bookGetRequestInformation,final Handler handler)  {


        HttpUtil.sendHttpGetRequest(bookGetRequestInformation.generateBookDetailURL(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("onFailure","onFailure");

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (!response.isSuccessful())
                {
                    throw new IOException("Unexpected code " + response);

                }
                String responseBody =  response.body().string();
                Log.i("okhttp3.response",responseBody);
                int code = 201;
                Message message = handler.obtainMessage();//创建message的方式，可以更好地被回收
                message.what = code;
                message.obj = responseBody;
                handler.sendMessage(message);
            }
        });

    }


    public static void getUserInfo(String token,final Handler handler)  {


        HttpUtil.sendHttpGetRequest(Server.getServerAddress() + "user?token=" + token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("onFailure","onFailure");

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (!response.isSuccessful())
                {
                    throw new IOException("Unexpected code " + response);

                }
                String responseBody =  response.body().string();
                Log.i("okhttp3.response",responseBody);

                int code = response.code();
                Message message = handler.obtainMessage();//创建message的方式，可以更好地被回收
                message.what = code;
                message.obj = responseBody;
                handler.sendMessage(message);
            }
        });

    }
    public static void downloadImage(){
        String url = "http://112.74.19.3:80/BooksServer/book/image?bookid=6";
        HttpUtil.sendHttpGetRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("onFailure","onFailure");

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (!response.isSuccessful())
                {
                    throw new IOException("Unexpected code " + response);

                }
                Log.i("responseCode=========",response.code() + "");
                Log.i("responseMessage======",response.message());
                InputStream inputStream = response.body().byteStream();

                FileOutputStream fileOutputStream ;
                try {
                    fileOutputStream = new FileOutputStream(new File("/sdcard/bookhub.jpg"));
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    fileOutputStream.flush();
                } catch (IOException e) {
                    Log.i("bookhub==========", "IOException");
                    e.printStackTrace();
                }

                Log.d("bookhub============", "文件下载成功");
            }
        });

    }

}
