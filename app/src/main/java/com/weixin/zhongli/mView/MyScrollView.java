package com.weixin.zhongli.mView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

	View mTopView;
	View mFlowView;
	View mGoTopView;

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {

		super.onScrollChanged(l, t, oldl, oldt);
		if (mTopView != null && mFlowView != null) {
			if (t >= mTopView.getHeight()) {
				mFlowView.setVisibility(View.VISIBLE);
			} else {
				mFlowView.setVisibility(View.GONE);
			}
		}
		if (mGoTopView != null) {
			if (t > 400)
				mGoTopView.setVisibility(View.VISIBLE);
			else
				mGoTopView.setVisibility(View.GONE);
		}
	}

	/**
	 * 监听浮动view的滚动状态
	 * 
	 * @param topView
	 *            顶部区域view，即当ScrollView滑动的高度要大于等于哪个view的时候隐藏floatview
	 * @param flowView
	 *            浮动view，即要哪个view停留在顶部
	 */
	public void listenerFlowViewScrollState(View topView, View flowView,
			View goTopView) {
		mTopView = topView;
		mFlowView = flowView;
		mGoTopView = goTopView;
		if(mGoTopView!=null){
			mGoTopView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mGoTopView.post(new Runnable() {
						public void run() {
							// 滚动至顶部
							MyScrollView.this.fullScroll(ScrollView.FOCUS_UP);
						}
					});
				}
			});
		}
	}

}
