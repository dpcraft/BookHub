package com.dpcraft.bookhub.UIWidget;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.dpcraft.bookhub.R;

/**
 * Created by DPC on 2017/3/7.
 */
public class CustomToolbar extends LinearLayout{
   private Toolbar cToolbar;
    public CustomToolbar(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_toolbar,this);
         cToolbar = (Toolbar) findViewById(R.id.c_toolbar);
        cToolbar.setNavigationOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                ((Activity)getContext()).finish();

            }

        });
    }
    public void setTitle(CharSequence title){
        cToolbar.setTitle(title);
    }
}
