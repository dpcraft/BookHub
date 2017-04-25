package com.dpcraft.bookhub.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;

import com.dpcraft.bookhub.Application.MyApplication;
import com.dpcraft.bookhub.R;
import com.dpcraft.bookhub.UIWidget.CustomToolbar;

/**
 * Created by DPC on 2017/4/21.
 */
public class ChangePasswordActivity  extends Activity {
    private TextInputLayout oldPassWordWrapper , passWordWrapper ,rePassWordWrapper;
    private Button submitButton;
    private CustomToolbar toolbar;
    private MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initWidget();
        toolbar.setTitle("修改密码");

    }
    private void initWidget(){
        toolbar = (CustomToolbar)findViewById(R.id.ctb_change_password);
        oldPassWordWrapper = (TextInputLayout)findViewById(R.id.oldPasswordWrapper);
        passWordWrapper = (TextInputLayout)findViewById(R.id.passwordWrapper);
        rePassWordWrapper = (TextInputLayout)findViewById(R.id.passwordWrapper);
        submitButton = (Button)findViewById(R.id.btn_submit);
    }
    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        intent.putExtra("para1", data1);
        intent.putExtra("para2", data2);
        context.startActivity(intent);
    }
}
