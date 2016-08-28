package com.weixin.zhongli;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weixin.zhongli.fragment.FriendsFragmen;
import com.weixin.zhongli.fragment.MainFragment;
import com.weixin.zhongli.fragment.MyselfFragment;
import com.weixin.zhongli.fragment.SecondFragment;
import com.weixin.zhongli.photo.util.ToastUtils;
import com.weixin.zhongli.view.indicator.view.indicator.Indicator;
import com.weixin.zhongli.view.indicator.view.indicator.IndicatorViewPager;
import com.weixin.zhongli.view.indicator.view.indicator.transition.OnTransitionTextListener;
import com.weixin.zhongli.view.indicator.view.viewpager.SViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndicatorActivity extends FragmentActivity {


    @BindView(R.id.viewpage_main)
    SViewPager mViewPager;
    @BindView(R.id.of_main_indicator)
    Indicator mIndicator;
    private Context mContext;

    public static String[] tabNames = {"寻ta", "沿途", "同道人", "我的"};
    public static int[] tabIcons = {R.drawable.find_default, R.drawable.yantu_default, R.drawable.tongdao_default, R.drawable.myself_default};
    public static int[] tabIcons_select = {R.drawable.find_select, R.drawable.yantu_select, R.drawable.tongdao_select, R.drawable.myself_select};
    private MyAdapter adapter;
    private IndicatorViewPager mIndicatorViewPager;

    // Fragment集合
    private List<Fragment> mFragments = new ArrayList<Fragment>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_indicator);
        mContext=this;
        ButterKnife.bind(this);
        initFragment();




        // 设置滚动监听（渐变颜色）
        int selectColorId = R.color.mian_color;
        int unSelectColorId = R.color.font_icon;
        mIndicator.setOnTransitionListener(new OnTransitionTextListener().setColorId(this, selectColorId, unSelectColorId));

        mIndicatorViewPager = new IndicatorViewPager(mIndicator, mViewPager);
        adapter = new MyAdapter(getSupportFragmentManager());
        mIndicatorViewPager.setAdapter(adapter);

        // 设置viewpager保留界面不重新加载的页面数量
        mViewPager.setOffscreenPageLimit(tabNames.length);
        // viewpage不能滑动
        mViewPager.setCanScroll(true);
        mIndicatorViewPager.setCurrentItem(0, true);

        initEvent();
    }

    private void initEvent() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void initFragment() {
        MainFragment main = new MainFragment();
        SecondFragment secondFragment = new SecondFragment();
        FriendsFragmen threeFragmen = new FriendsFragmen();
        MyselfFragment myselfFragment = new MyselfFragment();
        mFragments.add(main);
        mFragments.add(secondFragment);
        mFragments.add(threeFragmen);
        mFragments.add(myselfFragment);
    }


    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private LayoutInflater inflater;
        private int mChildCount = 0;

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            inflater = LayoutInflater.from(getApplicationContext());
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();

        }

        @Override
        public int getItemPosition(Object object) {

            if (mChildCount > 0) {
                mChildCount--;
                return PagerAdapter.POSITION_NONE;
            }
            return super.getItemPosition(object);
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = (TextView) inflater.inflate(R.layout.tab_main, container, false);
            }
            TextView textView = (TextView) convertView;

            textView.setText(tabNames[position]);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[position], 0, 0);
            return textView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            // TODO Auto-generated method stub
            return mFragments.get(position);
        }
    }


    private long exitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (System.currentTimeMillis() - exitTime > 2000){
                exitTime = System.currentTimeMillis();
                ToastUtils.showToast(IndicatorActivity.this, "再按一次退出应用");
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
