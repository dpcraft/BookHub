package com.dpcraft.bookhub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dpcraft.bookhub.Application.MyApplication;
import com.dpcraft.bookhub.DataClass.User;
import com.dpcraft.bookhub.DataClass.UserInfo;
import com.dpcraft.bookhub.DataClass.UserInfoResponse;
import com.dpcraft.bookhub.NetModule.JSONUtil;
import com.dpcraft.bookhub.NetModule.NetUtils;
import com.dpcraft.bookhub.UIWidget.CustomToolbar;
import com.dpcraft.bookhub.UIWidget.Dialog;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DPC on 2017/3/7.
 */
public class UserInfoActivity extends Activity {
    private UserInfo userInfo ;
    private MyApplication myApplication;
    private CustomToolbar customToolbar;
    private Button logoutButton;
    private CircleImageView circleUserIcon;
    private TextView userNameTextView,phoneNumberTextView;
    public static final int REQUEST_SUCCESS = 201;
    public static final int REQUEST_FAIL = 400;


    private Handler handler= new Handler(){
        public void handleMessage(Message msg){
            UserInfoResponse userInfoResponse =  JSONUtil.parseJsonWithGson(msg.obj.toString(), UserInfoResponse.class);
            if(msg.what == 200)
            switch ( userInfoResponse.getCode()){
                case REQUEST_SUCCESS :
                    userInfo =userInfoResponse.getData();
                    userNameTextView.setText(userInfo.getNickName());
                    phoneNumberTextView.setText(userInfo.getPhoneNumber());
                    break;
                case REQUEST_FAIL:
                    Dialog.showDialog("获取失败",userInfoResponse.getMessage(),UserInfoActivity.this);
                    break;
                default:
                    break;
            }
            else{
                Dialog.showDialog("获取失败","检查网络失败",UserInfoActivity.this);
            }
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myApplication = (MyApplication)getApplication();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        initWidget();
        customToolbar.setTitle("个人信息");
        circleUserIcon.setImageResource(R.drawable.yy);
        myApplication = (MyApplication)getApplication();
        NetUtils.getUserInfo(myApplication.getToken(),handler);
        circleUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog.showChangeIconDialog(UserInfoActivity.this);

            }
        });
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
        userNameTextView = (TextView)findViewById(R.id.tv_username);
        phoneNumberTextView = (TextView)findViewById(R.id.tv_phonenumber);
    }
    public static void actionStart(Context context, String data1, String data2){
        Intent intent = new Intent(context,UserInfoActivity.class);
        intent.putExtra("para1",data1);
        intent.putExtra("para2",data2);
        context.startActivity(intent);
    }


}