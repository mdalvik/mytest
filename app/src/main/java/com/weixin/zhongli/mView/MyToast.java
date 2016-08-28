package com.weixin.zhongli.mView;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.weixin.zhongli.R;


/**
 * Created by Administrator on 2016/6/20
 */
public class MyToast {

    public static void showToast(Context context, String content) {
        if(TextUtils.isEmpty(content))
            return;
        TextView view = (TextView) LayoutInflater.from(context).inflate(R.layout.my_toast, null);
        view.setText(content);
        Toast mToast = new Toast(context);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setView(view);
        mToast.show();
    }

    public static void showLongToast(Context context, String content) {
        if(TextUtils.isEmpty(content))
            return;
        TextView view = (TextView) LayoutInflater.from(context).inflate(R.layout.my_toast, null);
        view.setText(content);
        Toast mToast = new Toast(context);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setView(view);
        mToast.show();
    }
}
