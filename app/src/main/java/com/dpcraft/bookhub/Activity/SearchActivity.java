package com.dpcraft.bookhub.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.dpcraft.bookhub.R;

/**
 * Created by DPC on 2017/4/2.
 */
public class SearchActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }
    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra("para", data1);
        intent.putExtra("para2", data2);
        context.startActivity(intent);
    }

}
