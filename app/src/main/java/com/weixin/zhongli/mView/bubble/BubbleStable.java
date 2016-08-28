package com.weixin.zhongli.mView.bubble;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;
/**
 *
 ******************************************************
 * @author LK
 * @date 2015-12-26下午5:53:26
 * @Company  深圳市动态电子商务有限公司
 * @Description：TODO 消息气泡，拖动删除，粘性小球
 ******************************************************
 */
public class BubbleStable extends RelativeLayout {
	
	private BubbleMove bubble;
	private boolean isInit = false;

	public BubbleStable(Context context) {
		super(context);
		init();
	}

	public BubbleStable(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init(){
		// 创建浮动气泡
		bubble = new BubbleMove(getContext());
	}
	
	/**
	 * 设置文字
	 * @author: LK
	 * @date: 2015-12-30 
	 * @param s
	 */
	public void setText(String s)
	{
		bubble.setText(s);
		
	}
	
	/**
	 * 获取文字
	 * @author: LK
	 * @date: 2015-12-30 
	 * @return
	 */
	public String getText()
	{
		return bubble.getText();
	}
	
	/**
	 * 设置字体大小
	 * @author: LK
	 * @date: 2015-12-30 
	 * @param px
	 */
	public void setTextSize(int px){
		bubble.setTextSize(px);
	}
		
	/**
	 * 设置拖动最大距离
	 * @author: LK
	 * @date: 2015-12-30 
	 * @param dis
	 */
	public void setMaxDistance(int dis)
	{
		bubble.setMaxDistance(dis);
	}

	/**
	 * 设置背景色
	 * @author: LK
	 * @date: 2015-12-30 
	 * @param bubllebackgColor
	 */
	public void setBubllebackgColor(int bubllebackgColor) 
	{
		bubble.setBubllebackgColor(bubllebackgColor);
	}
	
	/**
	 * 设置拖动监听
	 * @author: LK
	 * @date: 2015-12-30 
	 * @param mOnDragCompeteListener
	 */
	public void setOnBubbleMoveListener(BubbleMove.OnBubbleMoveListener mOnDragCompeteListener) {
		bubble.setOnBubbleMoveListener(mOnDragCompeteListener);
	}

	/**
	 * 隐藏View
	 * @author: LK
	 * @date: 2015-12-30
	 */
    public void hide(){
    	bubble.setVisibility(View.INVISIBLE);
    }
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (!isInit) {
			// 画原始圆
			isInit = true;
			int[] location = new int[2];
			getLocationOnScreen(location);
			bubble.setBasePoint(location[0], location[1] - getTopBarHeight((Activity) getContext()));
			bubble.setRadius(w / 2);
//			bubble.setMaxDistance(150);
//			bubble.setText(text);
			// 全屏绑定
			attachToWindow(getContext());
		}
	}
	
	private void attachToWindow(Context context) {
		try {
			WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			WindowManager.LayoutParams params = new WindowManager.LayoutParams();
//			params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;// 返回键不可用
			params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
			params.height = WindowManager.LayoutParams.MATCH_PARENT;
			params.width = WindowManager.LayoutParams.MATCH_PARENT;
			params.format = PixelFormat.RGBA_8888;
			params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
			mWindowManager.addView(bubble, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		bubble.onTouchEvent(event);
		return true;
	}
	
	private int getTopBarHeight(Activity activity)
	{
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = activity.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return sbar;
	}
}
