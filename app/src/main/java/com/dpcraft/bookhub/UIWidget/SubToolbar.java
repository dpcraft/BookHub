package com.dpcraft.bookhub.UIWidget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.dpcraft.bookhub.R;

/**
 * Created by DPC on 2017/3/6.
 */
public class SubToolbar extends Toolbar {

    public SubToolbar(Context context) {
        this(context, null);
    }



    public SubToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public SubToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.toolbarStyle);
        LayoutInflater.from(context).inflate(R.layout.subtoolbar,this);
        setNavigationOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                ((Activity)getContext()).finish();

            }

        });
    }

    @Override
    public void setNavigationOnClickListener(OnClickListener listener) {
        super.setNavigationOnClickListener(listener);
    }


}
