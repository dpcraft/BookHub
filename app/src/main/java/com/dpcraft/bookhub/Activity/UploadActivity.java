package com.dpcraft.bookhub.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dpcraft.bookhub.R;
import com.dpcraft.bookhub.UIWidget.CustomToolbar;

/**
 * Created by DPC on 2017/4/4.
 */
public class UploadActivity extends Activity {
    private CustomToolbar mCustomToolbar;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_upload);
        initWidget();
        mCustomToolbar.setTitle("发布书籍");
    }
    private void initWidget(){
        mCustomToolbar = (CustomToolbar)findViewById(R.id.ctb_upload);
    }
    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, UploadActivity.class);
        intent.putExtra("para", data1);
        intent.putExtra("para2", data2);
        context.startActivity(intent);
    }
}
