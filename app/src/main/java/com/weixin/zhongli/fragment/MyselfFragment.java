package com.weixin.zhongli.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.weixin.zhongli.R;
import com.weixin.zhongli.activity.MyUserInfoActivity;
import com.weixin.zhongli.activity.SettingActivity;
import com.weixin.zhongli.photo.util.BadgeViewUtils;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MyselfFragment extends Fragment {

    private Context mContext;
    ListView lv_tontant;
    View view;
    AutoLinearLayout setting;
    AutoLinearLayout person;
    AutoLinearLayout label;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext=getActivity();
        view = LayoutInflater.from(mContext).inflate(R.layout.fragment_myself, null);
        initView();
        initEvent();
        initLabel();
        return view;
    }

    private void initLabel() {
        /**
         * 标签假数据
         */
      List<String> labelList = new ArrayList<>();
        labelList.add("5号线");
        labelList.add("灵芝");
        labelList.add("大学城");
        labelList.add("桃源居");

        label.removeAllViews();
        for (int i=0;i<labelList.size();i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.label_iteml, label, false);
            final TextView tv_label = (TextView) view.findViewById(R.id.tv_label);
            tv_label.setText(labelList.get(i));

            label.addView(view);
        }

    }

    private void initEvent() {
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, SettingActivity.class));
            }
        });

        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, MyUserInfoActivity.class));
            }
        });
    }

    private void initView() {
        TextView tv_fans = (TextView)view.findViewById(R.id.myself_tv_fans);
        ImageView iv_fans = (ImageView) view.findViewById(R.id.myself_iv_fans);
        setting = (AutoLinearLayout) view.findViewById(R.id.ll_consumerinfo_setting);
        person = (AutoLinearLayout) view.findViewById(R.id.al_person);
         label = (AutoLinearLayout) view.findViewById(R.id.myself_ll_label);

        BadgeViewUtils.show(getActivity(),iv_fans,"");

    }
}
