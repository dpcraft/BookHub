package com.dpcraft.bookhub.UIWidget;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import com.dpcraft.bookhub.R;

/**
 * Created by DPC on 2017/2/24.
 */
public class Dialog {
    public static void showDialog(String message, Context context) {
  /*
  这里使用了 android.support.v7.app.AlertDialog.Builder
  可以直接在头部写 import android.support.v7.app.AlertDialog
  那么下面就可以写成 AlertDialog.Builder
  */
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Material Design Dialog");
        builder.setMessage(message);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", null);
        builder.show();
    }
    public static void showSignupSuccessDialog(Context context) {

        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window =dialog.getWindow();
        window.setContentView(R.layout.dialog_signup_success);

    }
}
