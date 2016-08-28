package com.weixin.zhongli.mView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weixin.zhongli.R;

/**
 * Created by j on 2016/8/3
 */
public class ShowMoreTextView extends LinearLayout {
    //    用来标记是否为展开状态
    private int hideOrShow = 0;
    private TextView textView;
    private TextView button;

    public ShowMoreTextView(Context context) {
        super(context);
    }

    public ShowMoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        实例化layoutInflater对象，获取到布局填充服务
        LayoutInflater layoutInflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        填充自定义的布局xml文件
        layoutInflater.inflate(R.layout.my_text_view,this);
        textView = (TextView)findViewById(R.id.content);
        button=(TextView) findViewById(R.id.hide_show);
        button.setText("显示更多");
//        隐藏或显示
        hideOrShow();
    }
    //    创建setContent方法为TextView填充内容
    public void setContent(String content) {
        textView.setText(content);
        if (content.length()>20){
            button.setVisibility(VISIBLE);
        }
    }

    public void hideOrShow() {
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //由hideOrShow的值确定按钮和textview的状态
                if (hideOrShow == 0) {
                    button.setText("收起");
                    textView.setMaxLines(100);
                    hideOrShow = 2;
                }else if(hideOrShow==2){
                    button.setText("显示更多");
                    textView.setMaxLines(3);
                    hideOrShow = 1;
                }else if(hideOrShow==1){
                    button.setText("收起");
                    textView.setMaxLines(100);
                    hideOrShow=2;
                }
            }
        });
    }
}
