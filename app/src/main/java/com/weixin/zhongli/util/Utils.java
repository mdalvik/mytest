/*
 * @Copyright:           Copyright© 2007-2015 DTDS
 * @Company:             深圳市动态电子商务有限公司
 * @Project:             Tao_mobile
 * @see                  com.dtds.tsh_mobile.utils                              
 */
package com.weixin.zhongli.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

public class Utils {
	
	/**
	 * 获取手机分辨率
	 * 
	 * @return data[0]=width,,data[1]=height
	 */
	public static int[] getPhoneWidth(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int data[] = new int[2];
		data[0] = dm.widthPixels;
		data[1] = dm.heightPixels;
		return data;
	}
	
	 /**
	  * 网络是否畅通
	  */
	 public static boolean isNetworkAvailable(Context context) {
			ConnectivityManager cm = ((ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE));
			if (cm != null) {
				NetworkInfo info = cm.getActiveNetworkInfo();
				if (info != null && info.isConnectedOrConnecting()) {
					return true;
				}
			}
			return false;
		}
	    


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
	

}
