package com.dpcraft.bookhub.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dpcraft.bookhub.Algorithm.ContainsChinese;
import com.dpcraft.bookhub.Application.MyApplication;
import com.dpcraft.bookhub.DataClass.ResponseFromServer;
import com.dpcraft.bookhub.NetModule.JSONUtil;
import com.dpcraft.bookhub.NetModule.NetUtils;
import com.dpcraft.bookhub.NetModule.Server;
import com.dpcraft.bookhub.R;
import com.dpcraft.bookhub.UIWidget.CustomToolbar;
import com.dpcraft.bookhub.UIWidget.Dialog;

/**
 * Created by DPC on 2017/4/21.
 */
public class ChangePasswordActivity  extends Activity {
    private TextInputLayout oldPassWordWrapper , passWordWrapper ,rePassWordWrapper;
    private Button submitButton;
    private CustomToolbar toolbar;
    private MyApplication myApplication;
    public static final int SUCCESS = 201;
    public static final int REQUEST_ERROR = 401;

    private Handler handler= new Handler(){
        public void handleMessage(Message msg){
            if(msg.what == SUCCESS ){ Toast.makeText(ChangePasswordActivity.this , "修改成功" ,Toast.LENGTH_SHORT).show();
                //ialog.showSignupSuccessDialog(UploadActivity.this , "上传成功");
                ChangePasswordActivity.this.finish();
            }else{
                Dialog.showDialog("修改失败", JSONUtil.parseJsonWithGson((String)msg.obj,
                        ResponseFromServer.class).getMessage(),ChangePasswordActivity.this);
            }
//            switch ( msg.what){
//                case SUCCESS :
//                    Toast.makeText(ChangePasswordActivity.this , "修改成功" ,Toast.LENGTH_SHORT).show();
//                    //ialog.showSignupSuccessDialog(UploadActivity.this , "上传成功");
//                    ChangePasswordActivity.this.finish();
//                    break;
//                case REQUEST_ERROR:
//                    Dialog.showDialog("修改失败", JSONUtil.parseJsonWithGson((String)msg.obj,ResponseFromServer.class).getMessage(),ChangePasswordActivity.this);
//
//                    break;
//
//                default:
//                    break;
//            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initWidget();
        toolbar.setTitle("修改密码");
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOldPasswordValid() && isPasswordValid() &&  isRePasswordValid()){
                    myApplication = (MyApplication)getApplication();
                    String address = Server.getServerAddress() + "user/update?token="
                                    + myApplication.getToken() + "&oldpassword="
                                    + oldPassWordWrapper.getEditText().getText().toString().trim()
                                    + "&newpassword=" + oldPassWordWrapper.getEditText().getText().toString().trim();
                    Log.i("changePasswordAddress==" , address);
                    NetUtils.changePassword(address , handler);



                    //网络提交强求
                }
            }
        });

    }
    private void initWidget(){
        toolbar = (CustomToolbar)findViewById(R.id.ctb_change_password);
        oldPassWordWrapper = (TextInputLayout)findViewById(R.id.oldPasswordWrapper);
        passWordWrapper = (TextInputLayout)findViewById(R.id.passwordWrapper);
        rePassWordWrapper = (TextInputLayout)findViewById(R.id.passwordWrapper);
        submitButton = (Button)findViewById(R.id.btn_submit);
    }

    public boolean isOldPasswordValid(){
        if(oldPassWordWrapper.getEditText().getText().toString().trim().equals("")||
                oldPassWordWrapper.getEditText().getText().toString().trim().isEmpty()){
            oldPassWordWrapper.setErrorEnabled(true);
            oldPassWordWrapper.setError("密码不能为空");
            oldPassWordWrapper.requestFocus();
            return false;
        }else if(ContainsChinese.isContainsChinese(oldPassWordWrapper.getEditText().getText().toString().trim())){
            oldPassWordWrapper.setErrorEnabled(true);
            oldPassWordWrapper.setError("密码不能包含汉字");
            oldPassWordWrapper.requestFocus();
            return false;
        } else if(oldPassWordWrapper.getEditText().getText().toString().trim().length() > 10 ||
                oldPassWordWrapper.getEditText().getText().toString().trim().length() < 6){
            oldPassWordWrapper.setErrorEnabled(true);
            oldPassWordWrapper.setError("密码长度为6~10字符");
            oldPassWordWrapper.requestFocus();
            return false;
        }
        oldPassWordWrapper.setErrorEnabled(false);
        return true;
    }
    public boolean isPasswordValid(){
        if(passWordWrapper.getEditText().getText().toString().trim().equals("")||
                passWordWrapper.getEditText().getText().toString().trim().isEmpty()){
            passWordWrapper.setErrorEnabled(true);
            passWordWrapper.setError("密码不能为空");
            passWordWrapper.requestFocus();
            return false;
        }else if(ContainsChinese.isContainsChinese(passWordWrapper.getEditText().getText().toString().trim())){
            passWordWrapper.setErrorEnabled(true);
            passWordWrapper.setError("密码不能包含汉字");
            passWordWrapper.requestFocus();
            return false;
        } else if(passWordWrapper.getEditText().getText().toString().trim().length() > 10 ||
                passWordWrapper.getEditText().getText().toString().trim().length() < 6){
            passWordWrapper.setErrorEnabled(true);
            passWordWrapper.setError("密码长度为6~10字符");
            passWordWrapper.requestFocus();
            return false;
        }
        passWordWrapper.setErrorEnabled(false);
        return true;
    }
    public boolean isRePasswordValid(){
        if( !rePassWordWrapper.getEditText().getText().toString().equals(
                passWordWrapper.getEditText().getText().toString().trim())){
            rePassWordWrapper.setErrorEnabled(true);
            rePassWordWrapper.setError("密码输入不一致");
            rePassWordWrapper.requestFocus();
            return false;
        }
        rePassWordWrapper.setErrorEnabled(false);
        return true;
    }


    
    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        intent.putExtra("para1", data1);
        intent.putExtra("para2", data2);
        context.startActivity(intent);
    }
}
