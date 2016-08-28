package com.weixin.zhongli.util;

import android.content.Context;

/**
 * 
 ****************************************************** 
 * @author LBZ
 * @date 2016-1-20上午9:36:51
 * @Company 深圳市动态电子商务有限公司
 * @Description：像素转换工具类
 ****************************************************** 
 */
public class DptoPUtils {
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
