package com.weixin.zhongli.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.weixin.zhongli.R;

/**
 * 
 * @author LBZ
 * @date 2015-1-24
 * @Description：是否联系dialog
 */
public class CallPhoneDialog  extends Dialog {
	
	private Context mContext;
	
	public TextView cancel_btn;
	public TextView ok_btn;
	public TextView totalJF;
	public TextView mTv_title;

	public CallPhoneDialog(Context context) {
		super(context, R.style.DialogStyleRight);
		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Full Screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// No Titlebar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.callphone_dialog);
		
		ok_btn = (TextView) findViewById(R.id.mall_sku_faildialog_other);
		cancel_btn = (TextView) findViewById(R.id.mall_sku_faildialog_cancel);
		mTv_title = (TextView) findViewById(R.id.tv_title);
	}

}
