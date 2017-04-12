package com.dpcraft.bookhub.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dpcraft.bookhub.Application.MyApplication;
import com.dpcraft.bookhub.DataClass.UserInfo;
import com.dpcraft.bookhub.DataClass.UserInfoResponse;
import com.dpcraft.bookhub.NetModule.JSONUtil;
import com.dpcraft.bookhub.NetModule.NetUtils;
import com.dpcraft.bookhub.PhotoUtil.ImagePicker;
import com.dpcraft.bookhub.R;
import com.dpcraft.bookhub.UIWidget.CustomToolbar;
import com.dpcraft.bookhub.UIWidget.Dialog;

import java.io.FileNotFoundException;

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
    private ImagePicker imagePicker = new ImagePicker();


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
       // if(myApplication.getUserIcon() == null){
         //   circleUserIcon.setImageResource(R.drawable.default_user_icon);
       // }else {
        //circleUserIcon.setImageURI(myApplication.getUserIcon());
         // }
        if(myApplication.getUserIconBitmap() == null){
            circleUserIcon.setImageResource(R.drawable.default_user_icon);
        }
        else{
            circleUserIcon.setImageBitmap(myApplication.getUserIconBitmap());
        }

        myApplication = (MyApplication)getApplication();
        NetUtils.getUserInfo(myApplication.getToken(),handler);
        circleUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Dialog.showChangeIconDialog(UserInfoActivity.this);
                startCameraOrGallery();

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
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    private void startCameraOrGallery() {
        new AlertDialog.Builder(this).setTitle("设置头像")
                .setItems(new String[] { "从相册中选取图片", "拍照" }, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        // 回调
                        ImagePicker.Callback callback = new ImagePicker.Callback() {
                            @Override public void onPickImage(Uri imageUri) {
                            }

                            @Override public void onCropImage(Uri imageUri) {
                                circleUserIcon.setImageURI(imageUri);
                                myApplication.setUserIcon(imageUri);
                                try {
                                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                                    myApplication.setUserIconBitmap(bitmap);
                                }catch (FileNotFoundException e){
                                    e.printStackTrace();
                                }

                            }
                        };
                        if (which == 0) {
                            // 从相册中选取图片
                            imagePicker.startGallery(UserInfoActivity.this, callback);
                        } else {
                            // 拍照
                            imagePicker.startCamera(UserInfoActivity.this, callback);
                        }
                    }
                })
                .show()
                .getWindow()
                .setGravity(Gravity.BOTTOM);
    }
    public static void actionStart(Context context, String data1, String data2){
        Intent intent = new Intent(context,UserInfoActivity.class);
        intent.putExtra("para1",data1);
        intent.putExtra("para2",data2);
        context.startActivity(intent);
    }


}