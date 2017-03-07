package com.dpcraft.bookhub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dpcraft.bookhub.Application.MyApplication;
import com.dpcraft.bookhub.DataClass.User;
import com.dpcraft.bookhub.UIWidget.CustomToolbar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DPC on 2017/3/7.
 */
public class UserInfoActivity extends Activity {
    private User user ;
    private MyApplication myApplication;
    private CustomToolbar customToolbar;
    private Button logoutButton;
    private CircleImageView circleUserIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myApplication = (MyApplication)getApplication();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        initWidget();
        customToolbar.setTitle("个人信息");
        circleUserIcon.setImageResource(R.drawable.yy);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myApplication.setLoginStatus(false);
                Log.i("userInfo islogin",myApplication.isLogin().toString());
                finish();
            }
        });
    }


    public void initWidget(){
        customToolbar = (CustomToolbar)findViewById(R.id.ctb_userInfo);
        logoutButton = (Button)findViewById(R.id.btn_logout);
        circleUserIcon = (CircleImageView)findViewById(R.id.civ_usericon);
    }
    public static void actionStart(Context context, String data1, String data2){
        Intent intent = new Intent(context,UserInfoActivity.class);
        intent.putExtra("para1",data1);
        intent.putExtra("para2",data2);
        context.startActivity(intent);
    }


}