package com.dpcraft.bookhub.UIWidget;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dpcraft.bookhub.Algorithm.UnitConversion;
import com.dpcraft.bookhub.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by DPC on 2017/2/24.
 */
public class Dialog {
    public static void showDialog( String title,String message,Context context) {
  /*
  这里使用了 android.support.v7.app.AlertDialog.Builder
  可以直接在头部写 import android.support.v7.app.AlertDialog
  那么下面就可以写成 AlertDialog.Builder
  */
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        //builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", null);
        builder.show();
    }
    public static void showDialog( String message,Context context) {
  /*
  这里使用了 android.support.v7.app.AlertDialog.Builder
  可以直接在头部写 import android.support.v7.app.AlertDialog
  那么下面就可以写成 AlertDialog.Builder
  */
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", null);
        builder.show();
    }

    public static void showDealerMessageDialog( String title,String message,Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("联系", null);
        builder.show();
    }
    public static void showSignupSuccessDialog(Context context,String dialogTitle) {

        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window =dialog.getWindow();
        window.setContentView(R.layout.dialog_signup_success);
        TextView textView = (TextView) window.findViewById(R.id.tv_success);
        textView.setText(dialogTitle);

    }
    public static void showProgressDialog(Context context,String dialogTitle) {

        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window =dialog.getWindow();
        window.setContentView(R.layout.dialog_progress);
        TextView textView = (TextView) window.findViewById(R.id.tv_success);
        textView.setText(dialogTitle);

    }
    public static void showLoginSuccessDialog(Context context) {

        final Timer timer = new Timer();
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window =dialog.getWindow();
        window.setContentView(R.layout.dialog_login_success);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //do something
                dialog.dismiss();

            }
        },2000);//延时2s执行

    }



    public static void showChangeIconDialog2(final Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View changeIconView = inflater.inflate(R.layout.dialog_change_icon,null);
        //builder.setView(changeIconView);
        //builder.setItems(new String[]{"相机","从相册选择"},null);
        builder.setNegativeButton("相机", null);
        builder.setPositiveButton("从相册选择", null);
        //builder.show();
        final AlertDialog dialog =builder.create();
        dialog.show();
        Window window =dialog.getWindow();
        WindowManager.LayoutParams layoutParams =window.getAttributes();
        layoutParams.width = UnitConversion.dip2px(context,270);//应该是828ppi
        layoutParams.height = UnitConversion.dip2px(context,400);//应该是1242ppi
        window.setAttributes(layoutParams);
        window.setContentView(R.layout.dialog_change_icon);
        Button openCameraButton,openAlbumButton;
        openCameraButton = (Button)changeIconView.findViewById(R.id.bt_open_camera);
        openAlbumButton = (Button)changeIconView.findViewById(R.id.bt_open_album) ;
        openCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(context,"打开相机",Toast.LENGTH_LONG).show();
            }
        });

        Log.i("150",UnitConversion.dip2px(context,150)+ "");
        Log.i("1242",UnitConversion.px2dip(context,1242)+ "");

    }

    public static void showChangeIconDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        builder.setTitle("更改头像").setItems(new String[]{"相机","从相册选择"},null);
        builder.show();


    }


}
