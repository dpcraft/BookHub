package com.dpcraft.bookhub.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dpcraft.bookhub.DataClass.User;
import com.dpcraft.bookhub.NetModule.NetUtils;
import com.dpcraft.bookhub.R;
import com.dpcraft.bookhub.UIWidget.CustomToolbar;
import com.dpcraft.bookhub.UIWidget.Dialog;
import com.dpcraft.bookhub.Algorithm.ContainsChinese;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by DPC on 2017/2/21.
 */
public class SignupActivity extends Activity {
    public static final int SUCCESS = 201;
    public static final int REQUEST_ERROR = 400;
    public static final int OPERATION_UNAUTHORIZED = 401;
    public static final int OBJECT_EXIST = 409;
    private TextInputLayout mUsernameWrapper;
    private TextInputLayout mPasswordWrapper;
    private TextInputLayout mPhoneNumberWrapper;
    private TextInputLayout mRePasswordWrapper;
    private TextInputLayout mVerfCodeWrapper;
    private  Button signupButton, getVerfCodeButton;
    private CustomToolbar customToolbar;
    private User user;
    private int  time = 60;
    private boolean verfCodeIstrue = false;

    private EventHandler eventHandler ;

    private Handler handler= new Handler(){
        public void handleMessage(Message msg){
            if(msg.what == SUCCESS || msg.what == REQUEST_ERROR || msg.what == OPERATION_UNAUTHORIZED ||
                    msg.what == OBJECT_EXIST){
            try {

                switch (msg.what) {
                    case SUCCESS:
                        Dialog.showSignupSuccessDialog(SignupActivity.this, "恭喜您注册成功");
                        //Dialog.showDialog((String)msg.obj,SignupActivity.this);
                        break;
                    case REQUEST_ERROR:
                        Dialog.showDialog("注册失败", (String) msg.obj, SignupActivity.this);
                        break;
                    case OPERATION_UNAUTHORIZED:
                        Dialog.showDialog("注册失败", (String) msg.obj, SignupActivity.this);
                        break;
                    case OBJECT_EXIST:
                        Dialog.showDialog("注册失败", (String) msg.obj, SignupActivity.this);
                        break;
                    default:
                        break;
                }

            }catch (Exception e){
                Dialog.showDialog("获取数据错误"," 数据库错误 !" , SignupActivity.this);
            }
            }else {
                Log.i("msg.what=======",msg.what + "");

                if(msg.what == -1){
                    //修改按钮上倒计时
                    getVerfCodeButton.setText( time + "s");
                }else  if(msg.what == -2 ){
                    //修改按钮文字，进行重新发送验证码
                    getVerfCodeButton.setText("重新发送");
                    getVerfCodeButton.setClickable(true);
                    time = 60;
                }else{
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.getData();
                    if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                        //短信验证成功
                        //verfCodeIstrue = true;
                        if(result == SMSSDK.RESULT_COMPLETE) {
                            try {
                                NetUtils.signup(user, handler);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        else{
                            Dialog.showDialog("注册失败", "验证码错误", SignupActivity.this);
                        }
                    }else if(event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        Toast.makeText(SignupActivity.this , "验证码已发送" ,Toast.LENGTH_SHORT ).show();
                    }else if (result == SMSSDK.RESULT_ERROR){
                        try{
                            Throwable throwable = (Throwable)data;
                            throwable.printStackTrace();
                            JSONObject object = new JSONObject(throwable.getMessage());
                            String des = object.optString("detail");//错误描述
                            int status = object.optInt("status");//错误代码
                            if(status > 0 && !TextUtils.isEmpty(des)){
                                Toast.makeText(SignupActivity.this , des , Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                }

            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        SMSSDK.initSDK(SignupActivity.this , "1d164a6add800" , "f93a74559fa0806eee0ad040cc9cb974");
        eventHandler = new EventHandler(){
            @Override
            public void afterEvent(int event , int result , Object data){
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
        customToolbar = (CustomToolbar)findViewById(R.id.ctb_signup);
        customToolbar.setTitle("用户注册");
        user = new User();
        initWidget();


        getVerfCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(FormatRight()){
                    SMSSDK.getVerificationCode("86" , mPhoneNumberWrapper.getEditText().getText().toString().trim());
                    getVerfCodeButton.setClickable(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (; time > 0; time--) {
                                handler.sendEmptyMessage(-1);
                                if (time <= 0) {
                                    break;
                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            //倒计时结束执行
                            handler.sendEmptyMessage(-2);
                        }
                    }).start();
                }

            //}
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setUserName(mUsernameWrapper.getEditText().getText().toString().trim());
                user.setNickName(mUsernameWrapper.getEditText().getText().toString().trim());
                user.setPassWord(mPasswordWrapper.getEditText().getText().toString().trim());
                user.setPhoneNum(mPhoneNumberWrapper.getEditText().getText().toString().trim());
                if(FormatRight() && isVerfCodeValid()) {

                    SMSSDK.submitVerificationCode("86" , mPhoneNumberWrapper.getEditText().getText().toString().trim() ,
                            mVerfCodeWrapper.getEditText().getText().toString().trim());


                }
            }
        });


        //Dialog.showSignupSuccessDialog(SignupActivity.this);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }










    public static void actionStart(Context context, String data1, String data2){
        Intent intent = new Intent(context,SignupActivity.class);
        intent.putExtra("para1",data1);
        intent.putExtra("para2",data2);
        context.startActivity(intent);
    }


    public void initWidget(){
        mUsernameWrapper = (TextInputLayout)findViewById(R.id.usernameWrapper);
        mPasswordWrapper = (TextInputLayout)findViewById(R.id.passwordWrapper);
        mPhoneNumberWrapper = (TextInputLayout)findViewById(R.id.phoneNumberWrapper);
        mRePasswordWrapper = (TextInputLayout)findViewById(R.id.re_passwordWrapper);
        mVerfCodeWrapper = (TextInputLayout)findViewById(R.id.verfCodeWrapper);
        signupButton =(Button)findViewById(R.id.btn_signup);
        getVerfCodeButton = (Button)findViewById(R.id.btn_getVerfCode);
        mUsernameWrapper.getEditText().addTextChangedListener(new MyTextWatcher(mUsernameWrapper.getEditText()));
        mPasswordWrapper.getEditText().addTextChangedListener(new MyTextWatcher(mPasswordWrapper.getEditText()));
        mRePasswordWrapper.getEditText().addTextChangedListener(new MyTextWatcher(mRePasswordWrapper.getEditText()));
        mPhoneNumberWrapper.getEditText().addTextChangedListener(new MyTextWatcher(mPhoneNumberWrapper.getEditText()));
        mVerfCodeWrapper.getEditText().addTextChangedListener(new MyTextWatcher(mVerfCodeWrapper.getEditText()));

    }

    private class MyTextWatcher implements TextWatcher{
        private View view;

        private MyTextWatcher(View view){
            this.view = view;
        }
        @Override
        public void beforeTextChanged(CharSequence s,int start,int count,int after){

        }
        @Override
        public void onTextChanged(CharSequence s,int start,int count,int after){

        }
        @Override
        public void afterTextChanged(Editable s){
            switch (view.getId()){
                case R.id.username:
                    isUserNameValid();
                    break;
                case R.id.password:
                    isPasswordValid();
                    break;
                case R.id.re_password:
                    isRePasswordValid();
                    break;
                case R.id.phoneNumber:
                    isPhoneNumberValid();
                    break;
                case R.id.verfCode:
                    isVerfCodeValid();
                    break;
                default:
                    break;

            }
        }


    }
    private boolean FormatRight(){
        if(isUserNameValid()&&isPasswordValid()&&isRePasswordValid()&&isPhoneNumberValid())//&&isVerfCodeValid())
            return true;
        else
        return false;
    }




    public boolean isUserNameValid(){
        if(mUsernameWrapper.getEditText().getText().toString().trim().equals("")||
                mUsernameWrapper.getEditText().getText().toString().trim().isEmpty()){
            mUsernameWrapper.setErrorEnabled(true);
            mUsernameWrapper.setError("用户名不能为空");
            mUsernameWrapper.requestFocus();
            return false;
        }else if(ContainsChinese.isContainsChinese(mUsernameWrapper.getEditText().getText().toString().trim())){
            mUsernameWrapper.setErrorEnabled(true);
            mUsernameWrapper.setError("用户名不能包含汉字");
            mUsernameWrapper.requestFocus();
            return false;
        } else if(mUsernameWrapper.getEditText().getText().toString().trim().length() > 20 ||
                mUsernameWrapper.getEditText().getText().toString().trim().length() < 6){
            mUsernameWrapper.setErrorEnabled(true);
            mUsernameWrapper.setError("用户名长度为6~20字符");
            mUsernameWrapper.requestFocus();
            return false;
        }

        mUsernameWrapper.setErrorEnabled(false);
        return true;
    }

    public boolean isPasswordValid(){
        if(mPasswordWrapper.getEditText().getText().toString().trim().equals("")||
                mPasswordWrapper.getEditText().getText().toString().trim().isEmpty()){
            mPasswordWrapper.setErrorEnabled(true);
            mPasswordWrapper.setError("密码不能为空");
            mPasswordWrapper.requestFocus();
            return false;
        }else if(ContainsChinese.isContainsChinese(mPasswordWrapper.getEditText().getText().toString().trim())){
            mPasswordWrapper.setErrorEnabled(true);
            mPasswordWrapper.setError("密码不能包含汉字");
            mPasswordWrapper.requestFocus();
            return false;
        } else if(mPasswordWrapper.getEditText().getText().toString().trim().length() > 10 ||
                mPasswordWrapper.getEditText().getText().toString().trim().length() < 6){
            mPasswordWrapper.setErrorEnabled(true);
            mPasswordWrapper.setError("密码长度为6~10字符");
            mPasswordWrapper.requestFocus();
            return false;
        }
        mPasswordWrapper.setErrorEnabled(false);
        return true;
    }
    public boolean isRePasswordValid(){
        if( !mRePasswordWrapper.getEditText().getText().toString().equals(
                mPasswordWrapper.getEditText().getText().toString().trim())){
            mRePasswordWrapper.setErrorEnabled(true);
            mRePasswordWrapper.setError("密码输入不一致");
            mRePasswordWrapper.requestFocus();
            return false;
        }
        mRePasswordWrapper.setErrorEnabled(false);
        return true;
    }

    public boolean isPhoneNumberValid(){
        if(mPhoneNumberWrapper.getEditText().getText().toString().trim().equals("") ||
                mPhoneNumberWrapper.getEditText().getText().toString().trim().isEmpty()){
            mPhoneNumberWrapper.setErrorEnabled(true);
            mPhoneNumberWrapper.setError("手机号码不能为空");
            mPhoneNumberWrapper.requestFocus();
            return false;
        }else if(mPhoneNumberWrapper.getEditText().getText().toString().trim().length() != 11 ){
            mPhoneNumberWrapper.setErrorEnabled(true);
            mPhoneNumberWrapper.setError("请输入正确的手机号码");
            mPhoneNumberWrapper.requestFocus();
            return false;
        }
        mPhoneNumberWrapper.setErrorEnabled(false);
        return true;
    }
    public boolean isVerfCodeValid(){
        if(mVerfCodeWrapper.getEditText().getText().toString().trim().equals("") ||
               mVerfCodeWrapper.getEditText().getText().toString().trim().isEmpty()){
           mVerfCodeWrapper.setErrorEnabled(true);
           mVerfCodeWrapper.setError("手机验证码不能为空");
           mVerfCodeWrapper.requestFocus();
            return false;
        }else if(mVerfCodeWrapper.getEditText().getText().toString().trim().length() != 4 ){
           mVerfCodeWrapper.setErrorEnabled(true);
           mVerfCodeWrapper.setError("请输入正确的手机验证码");
           mVerfCodeWrapper.requestFocus();
            return false;
        }
        mVerfCodeWrapper.setErrorEnabled(false);
        return true;
    }

}