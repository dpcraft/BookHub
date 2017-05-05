package com.dpcraft.bookhub.NetModule;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.dpcraft.bookhub.DataClass.BookGetRequestInformation;
import com.dpcraft.bookhub.DataClass.LoginResponse;
import com.dpcraft.bookhub.DataClass.RequestBookInfo;
import com.dpcraft.bookhub.DataClass.RequestGetRequestInformation;
import com.dpcraft.bookhub.DataClass.ResponseFromServer;
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

            HttpUtil.sendHttpPostRequest(signupURL, user, new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {

                    String responseBody = response.body().string();
                    Log.i("signup code",response.code() + "");
                    Log.i("response.body",responseBody);
                    ResponseFromServer responseFromServer = JSONUtil.parseJsonWithGson(responseBody,ResponseFromServer.class);
                    int code = responseFromServer.getCode();
                    String Information = responseFromServer.getMessage();
                    Log.i("JSON code",code+"");
                    Message message = handler.obtainMessage();//创建message的方式，可以更好地被回收
                    message.what = code;
                    message.obj = Information;
                    handler.sendMessage(message);
                }
            });
        }
    public static void login(User user,final Handler handler) throws Exception {


        HttpUtil.sendHttpPostRequest(loginURL, user, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("login error", "error" );
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String responseBody = response.body().string();

                Log.i("response.body",responseBody);
               int code = JSONUtil.getResponseCode(responseBody);
                Log.i("login code",code + "");
                Message message = handler.obtainMessage();//创建message的方式，可以更好地被回收
                message.what = code;
                message.obj = responseBody;
                Log.i("firstmessge.obj",message.obj.toString());
                handler.sendMessage(message);

            }
        });
    }


    public static void changePassword(String address ,final Handler handler){

        HttpUtil.sendHttpGetRequest(address, new Callback() {
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
                Log.i("changePWResponse===",responseBody);
                int code;
                code = JSONUtil.getResponseCode(responseBody);
                Message message = handler.obtainMessage();//创建message的方式，可以更好地被回收
                message.what = code;
                message.obj = responseBody;
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
                int code = JSONUtil.parseJsonWithGson(responseBody,ResponseFromServer.class).getCode();
                Log.i("JSON code",code+"");
                Message message = handler.obtainMessage();//创建message的方式，可以更好地被回收
                message.what = code;
                message.obj = responseBody;
                Log.i("uploadmessage.obj",message.obj.toString());
                handler.sendMessage(message);

            }
        });
    }





    public static void getBookList(BookGetRequestInformation bookGetRequestInformation , final boolean refresh , final Handler handler)  {


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
               Log.i("RBcode===========" , response.code() + "");
               String responseBody =  response.body().string();
               Log.i("okhttp3.response",responseBody);
               int code;
                   if (refresh) {
                       code = 1;
                   } else {
                       code = 2;
                   }

               Message message = handler.obtainMessage();//创建message的方式，可以更好地被回收
               message.what = code;
               message.obj = responseBody;
               Log.i("message.obj=======" , message.obj.toString());
               handler.sendMessage(message);
           }
       });

    }
    public static void getRequestPreviewList(RequestGetRequestInformation requestGetRequestInformation , final boolean refresh , final Handler handler)  {


        HttpUtil.sendHttpGetRequest(requestGetRequestInformation.generateURL(), new Callback() {
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
                Log.i("RRBcode===========" , response.code() + "");
                String responseBody =  response.body().string();
                Log.i("okhttp3.response",responseBody);
                int code;
                if (refresh) {
                    code = 1;
                } else {
                    code = 2;
                }

                Message message = handler.obtainMessage();//创建message的方式，可以更好地被回收
                message.what = code;
                message.obj = responseBody;
                Log.i("message.obj=======" , message.obj.toString());
                handler.sendMessage(message);
            }
        });

    }

    /*
    *isMyIntention 为true时获取我的意向，为false时获取我的发布
     */
    public static void getMyBookList(String address , String token , boolean isMyIntention ,final Handler handler)  {


        if(isMyIntention) {
            HttpUtil.sendHttpGetRequest(address, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("onFailure", "onFailure");

                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);

                    }
                    String responseBody = response.body().string();
                    Log.i("okhttp3.response", responseBody);
                    int code;
                    code = 2;
                    Message message = handler.obtainMessage();//创建message的方式，可以更好地被回收
                    message.what = code;
                    message.obj = responseBody;
                    handler.sendMessage(message);
                }
            });
        }else {
            HttpUtil.sendHttpPostRequest2(address, token ,new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("onFailure", "onFailure");

                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);

                    }
                    String responseBody = response.body().string();
                    Log.i("okhttp3.response", responseBody);
                    int code;
                    code = 2;
                    Message message = handler.obtainMessage();//创建message的方式，可以更好地被回收
                    message.what = code;
                    message.obj = responseBody;
                    handler.sendMessage(message);
                }
            });
        }

    }

    public static void changePassWord(String token , String oldPassWord , String newPassWord ,final Handler handler)  {


            String address = Server.getServerAddress() + "user/update?token=" + token
                    + "&oldpassword=" + oldPassWord + "&newpassword=" + newPassWord;
            HttpUtil.sendHttpGetRequest(address, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("onFailure", "onFailure");

                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);

                    }
                    String responseBody = response.body().string();
                    Log.i("okhttp3.response", responseBody);
                    int code;
                    code = 2;
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

    public static void getRequestDetails(String address, final Handler handler)  {


        HttpUtil.sendHttpGetRequest(address, new Callback() {
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
                int code = 1;//JSONUtil.getResponseCode(responseBody);
                Message message = handler.obtainMessage();//创建message的方式，可以更好地被回收
                message.what = code;
                message.obj = responseBody;
                handler.sendMessage(message);
            }
        });

    }

    public static void deleteRequest(String token,String requestId, final Handler handler)  {


        HttpUtil.sendHttpPostRequestDelete(token, requestId, new Callback() {
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
                int code = 2;//JSONUtil.getResponseCode(responseBody);
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
    public static void modifyIntention( String address , String token , String bookid , Boolean isSeller , Boolean isIntention,final Handler handler)  {


        HttpUtil.sendHttpPostRequest( address , token , bookid ,  isSeller , isIntention , new Callback() {
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
                int code = 002;
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
    public static void uploadUserIcon(String token,final Handler handler)  {


        HttpUtil.sendHttpPostRequest(Server.getServerAddress() + "user/photo", token ,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("onFailure","onFailure");

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String responseBody = response.body().string();
                Log.i("login code",response.code() + "");
                Log.i("response.body",responseBody);
                int code = 3;
                Log.i("JSON code",code+"");
                Message message = handler.obtainMessage();//创建message的方式，可以更好地被回收
                message.what = code;
                message.obj = responseBody;
                Log.i("uploadmessage.obj",message.obj.toString());
                handler.sendMessage(message);

            }
        });

    }
    public static void deleteBook(String token ,String bookId ,final Handler handler){
        String address = Server.getServerAddress() + "book/update?token=" + token
                + "&bookid=" + bookId;

        HttpUtil.sendHttpGetRequest(address, new Callback() {
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
                Log.i("deleteResponse===",responseBody);
                int code;
                code = 301;
                Message message = handler.obtainMessage();//创建message的方式，可以更好地被回收
                message.what = code;
                message.obj = responseBody;
                handler.sendMessage(message);
            }
        });

    }


    public static void uploadRequestBook(RequestBookInfo requestBookInfo, String token, final Handler handler) throws Exception {

        HttpUtil.sendHttpPostRequest(Server.getServerAddress() + "bw" ,token , requestBookInfo, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("upload error", "error" );
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String responseBody = response.body().string();
                Log.i("login code",response.code() + "");
                Log.i("response.body",responseBody);
                //int code = JSONUtil.parseJsonWithGson(responseBody,ResponseFromServer.class).getCode();
                int code = JSONUtil.getResponseCode(responseBody);
                Log.i("JSON code",code+"");
                Message message = handler.obtainMessage();//创建message的方式，可以更好地被回收
                message.what = code;
                message.obj = responseBody;
                Log.i("uploadmessage.obj",message.obj.toString());
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
