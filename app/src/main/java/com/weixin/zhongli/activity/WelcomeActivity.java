package com.weixin.zhongli.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import com.weixin.zhongli.R;
import com.weixin.zhongli.util.Constant;
import com.weixin.zhongli.util.Utils;

/**
 ******************************************************
 * @Description：欢迎界面
 ******************************************************
 */
public class WelcomeActivity extends Activity {
	
	private AlertDialog dialog1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.wecome);
        
        new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if (Utils.isNetworkAvailable(WelcomeActivity.this)) {
					Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
					startActivity(intent);
					finish();
				} else {
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
//					setAlertDialog();
				}
			}
		}, 3000);
        
        //获取屏幕分辨率
        DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		Constant.screenWith = dm.widthPixels;
		Constant.screenHeight = dm.heightPixels;
		
		System.out.println(dm.density + "," + dm.densityDpi + "," +dm.scaledDensity);
    }

    public void setAlertDialog() {

        dialog1 = new AlertDialog.Builder(WelcomeActivity.this).create();
        Window window = dialog1.getWindow();
        window.setGravity(Gravity.CENTER); // 设置dialog显示的位置
        window.setWindowAnimations(R.style.mystyle); // 添加动画
        dialog1.show();
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.getWindow().setContentView(R.layout.welcome_dialog_layout);
        dialog1.getWindow().findViewById(R.id.btn_ui_dialog_sure)
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                        Intent i = new Intent(Settings.ACTION_SETTINGS);
                        startActivity(i);
                        finish();
                    }
                });

    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// TODO Auto-generated method stub
    	if (keyCode == KeyEvent.KEYCODE_BACK){
    		return true;
    	}
    	return super.onKeyDown(keyCode, event);
    }
    
}
