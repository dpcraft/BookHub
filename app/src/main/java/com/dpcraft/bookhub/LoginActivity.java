package com.dpcraft.bookhub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dpcraft.bookhub.Application.MyApplication;
import com.dpcraft.bookhub.DataClass.User;
import com.dpcraft.bookhub.NetModule.NetUtils;
import com.dpcraft.bookhub.UIWidget.CustomToolbar;

/**
 * Created by DPC on 2017/2/18.
 */
public class LoginActivity extends Activity {
    private User user ;
    private TextInputLayout mUsernameWrapper;
    private TextInputLayout mPasswordWrapper;
   private CustomToolbar customToolbar;
   private MyApplication myApplication ;
    private  String nextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        customToolbar = (CustomToolbar)findViewById(R.id.ctb_login);
        customToolbar.setTitle("用户登录");
        //nextActivity参数获取
        Intent intent = getIntent();
        nextActivity = intent.getStringExtra("nextActivity");

        Button RegisterButton = (Button)findViewById(R.id.btn_register);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignupActivity.actionStart(LoginActivity.this, "data1", "data2");

            }
        });
        mUsernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        mPasswordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        Button LoginButton = (Button)findViewById(R.id.btn_login) ;

        Log.i("loginActivity","start");
        user = new User();
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("username",mUsernameWrapper.getEditText().getText().toString().trim());
                user.setUserName(mUsernameWrapper.getEditText().getText().toString().trim());
                user.setPassWord(mPasswordWrapper.getEditText().getText().toString().trim());
                //user.setUserName("user");
               // user.setPassWord("password");
                try{
                NetUtils.login(user);
                }catch (Exception e){
                    e.printStackTrace();
                }
                myApplication.setLoginStatus(true);

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
                        default:
                            MainActivity.actionStart(LoginActivity.this,"data1","data2");
                            finish();
                            break;

                }
            }
        });



        myApplication = (MyApplication)getApplication();
        Log.i(" login activity islogin",myApplication.isLogin().toString());
    }
    public static void actionStart(Context context, String data1, String data2){
        Intent intent = new Intent(context,LoginActivity.class);
        intent.putExtra("nextActivity",data1);
        intent.putExtra("para2",data2);
        context.startActivity(intent);
    }

}
