package com.dpcraft.bookhub.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dpcraft.bookhub.Application.MyApplication;
import com.dpcraft.bookhub.DataClass.LoginResponse;
import com.dpcraft.bookhub.DataClass.ResponseFromServer;
import com.dpcraft.bookhub.DataClass.User;
import com.dpcraft.bookhub.NetModule.JSONUtil;
import com.dpcraft.bookhub.NetModule.NetUtils;
import com.dpcraft.bookhub.R;
import com.dpcraft.bookhub.UIWidget.CustomToolbar;
import com.dpcraft.bookhub.UIWidget.Dialog;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by DPC on 2017/2/18.
 */
public class LoginActivity extends BaseActivity {
    private User user ;
    private TextInputLayout mUsernameWrapper;
    private TextInputLayout mPasswordWrapper;
    private CustomToolbar customToolbar;
    private Button registerButton, loginButton , weChatButton;
    private MyApplication myApplication ;
    private  String nextActivity,userName,passWord;
    private Timer timer = new Timer();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final int LOGIN_SUCCESS = 201;
    public static final int LOGIN_FAIL = 400;


    private Handler handler= new Handler(){
        public void handleMessage(Message msg){
            try{
            switch (msg.what){
                case LOGIN_SUCCESS :
                    LoginResponse loginResponse = JSONUtil.parseJsonWithGson(msg.obj.toString(),LoginResponse.class);
                    Dialog.showLoginSuccessDialog(LoginActivity.this);
                    rememberPassword(msg.what);
                    myApplication.setLoginStatus(true);
                    myApplication.setToken(loginResponse.getMessage());
                    myApplication.setLoginResponseUserInfo(loginResponse.getData());
                    Log.i("token",myApplication.getToken());
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            //do something
                            goToNext();
                        }
                    },2000);//延时2s执行

                    //Dialog.showDialog((String)msg.obj,LoginActivity.this);
                    break;
                case LOGIN_FAIL:
                    ResponseFromServer responseFromServer = JSONUtil.parseJsonWithGson(msg.obj.toString(),ResponseFromServer.class);
                    Dialog.showDialog("登录失败",responseFromServer.getMessage(),LoginActivity.this);
                    rememberPassword(msg.what);
                    break;

                default:
                    break;
            }
            }catch (Exception e){
                Dialog.showDialog("获取数据错误"," 数据库错误 !" , LoginActivity.this);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);

       initWidget();
        //nextActivity参数获取
        Intent intent = getIntent();
        nextActivity = intent.getStringExtra("nextActivity");


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignupActivity.actionStart(LoginActivity.this, "data1", "data2");

            }
        });

        //检查是否记住用户名密码
        boolean isRemember = sharedPreferences.getBoolean("remember_password",false);
        if(isRemember){
            userName = sharedPreferences.getString("user_name","");
            passWord = sharedPreferences.getString("password","");
            mUsernameWrapper.getEditText().setText(userName);
            mPasswordWrapper.getEditText().setText(passWord);

        }


        Log.i("loginActivity","start");
        user = new User();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userName = mUsernameWrapper.getEditText().getText().toString().trim();
                passWord = mPasswordWrapper.getEditText().getText().toString().trim();
                Log.i("username",userName);
                user.setUserName(userName);
                user.setPassWord(passWord);
                try{
                NetUtils.login(user,handler);
                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        });



        myApplication = (MyApplication)getApplication();
        Log.i(" login activity islogin",myApplication.isLogin().toString());
        weChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this , "微信登录暂不可用" , Toast.LENGTH_SHORT).show();
            }
        });
    }




    public void initWidget(){

        customToolbar = (CustomToolbar)findViewById(R.id.ctb_login);
        customToolbar.setTitle("用户登录");

        registerButton = (Button)findViewById(R.id.btn_register);
        weChatButton = (Button) findViewById(R.id.btn_wechat_login);

        mUsernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        mPasswordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        loginButton = (Button)findViewById(R.id.btn_login) ;


    }

    //记住用户名密码
    private void rememberPassword(int code){
        editor = sharedPreferences.edit();
        if(code == LOGIN_SUCCESS) {
            editor.putBoolean("remember_password", true);
            editor.putString("user_name", userName);
            editor.putString("password", passWord);
        }
        else{
            editor.clear();
        }
        editor.apply();

    }

    //判断登录之前的Activity 是哪个，并跳转
    private void goToNext(){
        switch (nextActivity){
            case "MainActivity":
                MainActivity.actionStart(LoginActivity.this,"data1","data2");
                finish();
                break;
            case "SignupActivity":
                SignupActivity.actionStart(LoginActivity.this,"data1","data2");
                finish();
                break;
            case "UserInfoActivity":
                UserInfoActivity.actionStart(LoginActivity.this,"data1","data2");
                finish();
                break;
            case "MyUploadActivity":
                MyUploadActivity.actionStart(LoginActivity.this, "data1", "data2");
                finish();
                break;
            case "MyIntentionActivity":
                MyIntentionActivity.actionStart(LoginActivity.this, "data1", "data2");
                finish();
                break;
            case "RequestEditorActivity":
                RequestEditorActivity.actionStart(LoginActivity.this, "", "");
                finish();
                break;
            case "UploadActivity":
                UploadActivity.actionStart(LoginActivity.this,"data1","data2");
                finish();
                break;
            default:
                MainActivity.actionStart(LoginActivity.this,"data1","data2");
                finish();
                break;

        }
    }
    public static void actionStart(Context context, String data1, String data2){
        Intent intent = new Intent(context,LoginActivity.class);
        intent.putExtra("nextActivity",data1);
        intent.putExtra("para2",data2);
        context.startActivity(intent);
    }


}
