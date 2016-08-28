/*
 * @Copyright:           Copyright© 2007-2016 DTDS
 * @Company:             深圳市动态电子商务有限公司
 * @Project:             Tao_mobile
 * @see                  com.dtds.tsh_mobile.utils                              
 */
package com.weixin.zhongli.photo.util;

import android.content.Context;
import android.view.View;

import com.weixin.zhongli.R;
import com.weixin.zhongli.mView.bubble.BadgeView;


public class BadgeViewUtils {

	/**
	 * 显示右上角数字
	 * @author: LK
	 * @date: 2016-1-5 
	 * @param ctx
	 * @param view
	 * @param text
	 */
	public static BadgeView show(Context ctx, View view, String text) {
		BadgeView badge = new BadgeView(ctx, view);
		// 这是数字
		badge.setText(text);
		badge.setTextSize(14);
		badge.setBadgeMargin(0);
		// 设置位置
		badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
		badge.setBackgroundResource(R.drawable.badge_bg);
		badge.show();
		return badge;
	}
	
	public static void hide(BadgeView badge){
		if (badge != null)
			badge.hide();
	}
}
