package com.weixin.zhongli.mView;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.weixin.zhongli.R;

/**
 * Created by j on 2016/7/18
 */
public class MyLoadingDialog {
    AlertDialog dialog;
    View view;
    public MyLoadingDialog(Context context) {
        super();
        view = LayoutInflater.from(context).inflate(R.layout.myloadingdialog, null);
        dialog = new AlertDialog.Builder(context, R.style.MyDialog).create();
        dialog.setCanceledOnTouchOutside(false);


    }

    public boolean isShowing()
    {
        return dialog.isShowing();
    }

    public void show(){
        dialog.show();
        dialog.setContentView(view);
    }

    public void dismiss()
    {
        dialog.dismiss();
    }


}
