/*
 * @Copyright:           Copyright© 2007-2015 DTDS
 * @Company:             深圳市动态电子商务有限公司
 * @Project:             Tao_mobile
 * @see                  com.dtds.tsh_mobile.utils                              
 */
package com.weixin.zhongli.photo.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
	
    public static void showToast(Context context, String content) {
    	show(context, content, Toast.LENGTH_SHORT);
    }
    
    public static void showToastLong(Context context, String content){
    	show(context, content, Toast.LENGTH_LONG);
    }
    
    private static void show(Context context, String content, int duration){
    	Toast.makeText(context, content, duration).show();
    }
    
}
