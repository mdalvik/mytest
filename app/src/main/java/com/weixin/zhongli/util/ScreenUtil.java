package com.weixin.zhongli.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

public class ScreenUtil {
    public static int screenWith;
    public static int screenHeight;
    public static float densiny;
    public static int densinyDpi;
    public static float scaledDensity;
    public static TypedValue mTmpValue = new TypedValue();



    /**
     * dp转px
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }
    /**
     * px转dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    public static int dpToPx(int dp) {
        Log.d("densiny", "densinys" + densiny + "---" + "" + (dp * densiny + 0.5f));
        return (int) (dp * densiny + 0.5f);
    }

    public static int pxToDp(int px) {
        return (int) (px / densiny + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int pxToSp(float pxValue) {
        return (int) (pxValue / scaledDensity + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int spToPx(float spValue) {
        return (int) (spValue * scaledDensity + 0.5f);
    }

    public static int dpToSp(int dp) {
        int px = dpToPx(dp);
        return pxToSp(px);

    }

    public static int getXmlDef(Context context, int id) {
        synchronized (mTmpValue) {
            TypedValue value = mTmpValue;
            context.getResources().getValue(id, value, true);
            return (int) TypedValue.complexToFloat(value.data);
        }
    }
    
    /**
     * 获取屏幕高度
     * @author: LK
     * @date: 2015-11-12 
     * @param activity
     * @return
     */
    public static int getScreenHeight(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		ScreenUtil.screenHeight = dm.heightPixels;
		return screenHeight;
	}
}
