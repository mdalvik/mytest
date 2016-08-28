package com.weixin.zhongli.dialog;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.PopupWindow;

import com.weixin.zhongli.R;
import com.weixin.zhongli.adapter.CommonAdapter;
import com.weixin.zhongli.adapter.ViewHolder;
import com.weixin.zhongli.mView.MyListView;

import java.util.ArrayList;
import java.util.List;


public class AddPopWindow extends PopupWindow {
    private View conentView;
    private Context mContext;
    private List datas=new ArrayList();
    MyListView lv_more;

   
	@SuppressLint("InflateParams")
	public AddPopWindow(final Activity context,List data) {
        mContext=context;
        datas.addAll(data);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popupwindow_add, null);
 
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);
        
        
         lv_more = (MyListView) conentView.findViewById(R.id.lv_more);
        initList(lv_more);


    }

    private void initList(MyListView lv_more) {


        CommonAdapter  adapter= new CommonAdapter<String>(mContext, datas, R.layout.popupwindow_add_item) {
            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.tv_contant,s);
            }
        };
        lv_more.setAdapter(adapter);
    }

    // 设置listViewitem点击事件
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        lv_more.setOnItemClickListener(listener);
    }


    /**
     * 显示popupWindow
     * 
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, 0, 0);
        } else {
            this.dismiss();
        }
    }
}
