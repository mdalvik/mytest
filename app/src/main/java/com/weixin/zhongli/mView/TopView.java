package com.weixin.zhongli.mView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weixin.zhongli.R;

public class TopView extends RelativeLayout {

	private Context mContext;
	private RelativeLayout root;
	private TextView leftView;
	private TextView midView;
	private TextView rightView;

	public TopView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		root = (RelativeLayout) View.inflate(context, R.layout.top_view_layout, this);
		leftView = (TextView) root.findViewById(R.id.top_leftbutton);
		midView = (TextView) root.findViewById(R.id.top_titletext);
		rightView = (TextView) root.findViewById(R.id.top_rightbutton);
	}

	public TextView getLeftView() {
		return leftView;
	}

	public TextView getMidView() {
		return midView;
	}

	public TextView getRightView() {
		return rightView;
	}
	public RelativeLayout getRoot() {
		return root;
	}

}
