package com.dpcraft.bookhub.UIWidget;

import android.content.Context;

/**
 * Created by DPC on 2017/3/27.
 */
public class UnitConversion {
    public static int dip2px(Context context, float dpValue) {

        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dpValue * scale +0.5f);

    }
    public static int px2dip(Context context,float pxValue) {

        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (pxValue / scale +0.5f);

    }
}
