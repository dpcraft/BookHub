package com.dpcraft.bookhub.Algorithm;

import android.content.Context;

/**
 * Created by DPC on 2017/3/27.
 */
public class UnitConversion {
    public static int dip2px(Context context, float dpValue) {

        //scale 是屏幕像素密度
        final float scale = context.getResources().getDisplayMetrics().density;
        //scale 是屏幕像素密度
        return (int) (dpValue * scale + 0.5f);

    }
    public static int px2dip(Context context,float pxValue) {

        //scale 是屏幕像素密度
        final float scale = context.getResources().getDisplayMetrics().density;
        // + 0.5f 是为了四舍五入来校正
        return (int) (pxValue / scale + 0.5f);


    }
}
