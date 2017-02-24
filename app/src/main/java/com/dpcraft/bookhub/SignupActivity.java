package com.dpcraft.bookhub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.dpcraft.bookhub.DataClass.User;
import com.dpcraft.bookhub.NetModule.NetUtils;
import com.dpcraft.bookhub.UIWidget.Dialog;
import com.dpcraft.bookhub.algorithm.ContainsChinese;

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
    private  Button signupButton ;


    private User user;
    private Handler handler= new Handler(){
        public void handleMessage(Message msg){
            switch ( msg.what){
                case SUCCESS :
                    Dialog.showSignupSuccessDialog(SignupActivity.this);
                    //Dialog.showDialog((String)msg.obj,SignupActivity.this);
                    break;
                case REQUEST_ERROR:
                    Dialog.showDialog((String)msg.obj,SignupActivity.this);
                    break;
                case OPERATION_UNAUTHORIZED:
                    Dialog.showDialog((String)msg.obj,SignupActivity.this);
                    break;
                case OBJECT_EXIST:
                    Dialog.showDialog((String)msg.obj,SignupActivity.this);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        user = new User();
        initWidget();
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setUserName(mUsernameWrapper.getEditText().getText().toString().trim());
                user.setPassWord(mPasswordWrapper.getEditText().getText().toString().trim());
                user.setPhoneNum(mPhoneNumberWrapper.getEditText().getText().toString().trim());
                if(canSignup()) {
                    try {
                        NetUtils.signup(user, handler);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //Dialog.showSignupSuccessDialog(SignupActivity.this);

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
    private boolean canSignup(){
        if(isUserNameValid()&&isPasswordValid()&&isRePasswordValid()&&isPhoneNumberValid()&&isVerfCodeValid())
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