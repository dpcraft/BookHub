package com.dpcraft.bookhub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.dpcraft.bookhub.DataClass.User;
import com.dpcraft.bookhub.NetModule.NetUtils;

/**
 * Created by DPC on 2017/2/18.
 */
public class LoginActivity extends Activity {
    private Toolbar toolbar;
    private User user ;
    private TextInputLayout mUsernameWrapper;
    private TextInputLayout mPasswordWrapper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

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
       // final EditText userNameEditText = mUsernameWraper.
       // final EditText passWordEditText = (EditText)findViewById(R.id.edt_password);

       // Log.i("username",mUsernameWrapper.getEditText().getText().toString().trim());
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
            }
        });


        /*toolbar = (Toolbar) findViewById(R.id.common_toolbar);
        toolbar.setTitle("登录");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
    }
    public static void actionStart(Context context, String data1, String data2){
        Intent intent = new Intent(context,LoginActivity.class);
        intent.putExtra("para1",data1);
        intent.putExtra("para2",data2);
        context.startActivity(intent);
    }

}
