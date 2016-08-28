package com.weixin.zhongli.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.weixin.zhongli.R;
import com.weixin.zhongli.activity.TestActivity;
import com.weixin.zhongli.dialog.AddPopWindow;
import com.weixin.zhongli.mView.MyToast;
import com.weixin.zhongli.util.DptoPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private View view;


    /**
     * 筛选
     */
    private ImageView iv_filtrate;

    /**
     * 全部
     */
    private TextView tv_all;

    /**
     * 关注
     */
    private TextView tv_atten;

    /**
     * 相关
     */
    private TextView tv_corre;

    /**
     * 搜索
     */
    private ImageView iv_search;

    /**
     * veiwpager
     */
    private ViewPager vp_contant;


    // Fragment集合
    private List<Fragment> mFragments = new ArrayList<Fragment>();

    AllFragment allFragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        view = LayoutInflater.from(mContext).inflate(R.layout.activity_main_fragment, null);
        ButterKnife.bind(getActivity());
        initView();
        initTop();
        initEvent();
        return view;
    }


    private void initEvent() {
        vp_contant.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                initTop();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(getActivity(), PhotoMainActivity.class);
//                Intent intent = new Intent(getActivity(), MessgeTestActivity.class);
                Intent intent = new Intent(getActivity(), TestActivity.class);
                startActivity(intent);
            }
        });
    }




    private void initTop() {

        iv_filtrate = (ImageView) view.findViewById(R.id.main_iv_filtrate);
        tv_all = (TextView) view.findViewById(R.id.main_tv_all);
        tv_atten = (TextView) view.findViewById(R.id.main_tv_atten);
        tv_corre = (TextView) view.findViewById(R.id.main_tv_corre);
        iv_search= (ImageView) view.findViewById(R.id.main_iv_search);
        int twf = new DptoPUtils().px2dip(mContext, 30);
        int tw = new DptoPUtils().px2dip(mContext, 20);


        iv_filtrate.setOnClickListener(this);

        if (vp_contant.getCurrentItem()==0){
            tv_all.setTextColor(getResources().getColor(R.color.cheng));


            tv_atten.setTextColor(getResources().getColor(R.color.text_color1));
            tv_corre.setTextColor(getResources().getColor(R.color.text_color1));
        }else if (vp_contant.getCurrentItem()==1){
            tv_atten.setTextColor(getResources().getColor(R.color.cheng));
            tv_all.setTextColor(getResources().getColor(R.color.text_color1));
            tv_corre.setTextColor(getResources().getColor(R.color.text_color1));
        }else if (vp_contant.getCurrentItem()==2){
            tv_corre.setTextColor(getResources().getColor(R.color.cheng));
            tv_all.setTextColor(getResources().getColor(R.color.text_color1));
            tv_atten.setTextColor(getResources().getColor(R.color.text_color1));
        }
    }

    private void initView() {
         allFragment = new AllFragment();
        AttentionFragment attentionFragment = new AttentionFragment();
        CorrelationFragment correlationFragment = new CorrelationFragment();
        mFragments.add(allFragment);
        mFragments.add(attentionFragment);
        mFragments.add(correlationFragment);
        vp_contant = (ViewPager) view.findViewById(R.id.main_vp_contant);
        vp_contant.setAdapter(new myAdapter(getFragmentManager()));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_iv_filtrate:

                final List<String> data=new ArrayList<>();
                data.add("py");
                data.add("66");
                data.add("77");

                final AddPopWindow addPopWindow = new AddPopWindow((Activity) mContext,data);
                addPopWindow.showPopupWindow(iv_filtrate);
                addPopWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String s = data.get(position);
                        MyToast.showToast(mContext,s);
                        allFragment.initData(s);
                        addPopWindow.dismiss();
                    }
                });
                break;
        }
    }


    private class myAdapter extends FragmentPagerAdapter {
        public myAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

    }
}
