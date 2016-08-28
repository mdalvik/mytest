package com.weixin.zhongli.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.weixin.zhongli.R;
import com.weixin.zhongli.adapter.CommonAdapter;
import com.weixin.zhongli.adapter.ViewHolder;
import com.weixin.zhongli.mView.MyListView;
import com.weixin.zhongli.vo.FindVo;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragmen extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener{
    private Context mContext;
    View view;
    MyListView lv_tontant;
    List<FindVo> friendList;
    List<String> labelList;
    CommonAdapter<FindVo> adapter;
    private boolean isRefresh = false;//是否刷新中
    int type=0;

    SwipeRefreshLayout  swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext=getActivity();
        view = LayoutInflater.from(mContext).inflate(R.layout.fragment_friends, null);
        initData();
        initView();
        initPUll();
        initEvent();
        return view;
    }

    private void initEvent() {
        lv_tontant.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (lv_tontant.getLastVisiblePosition() == (lv_tontant.getCount() - 1)) {

                            if(!isRefresh){
                                isRefresh = true;
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        for (int i=0;i<3;i++){
                                            FindVo findVo = new FindVo();
                                            findVo.setName("andyLau");
                                            findVo.setContant("万水千山总是情");
                                            friendList.add(findVo);
                                        }
                                        adapter.notifyDataSetChanged();
                                        isRefresh= false;
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                }, 1000); }
                        }
                        // 判断滚动到顶部

                        if(lv_tontant.getFirstVisiblePosition() == 0){
                        }

                        break;
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) { }});
    }

    private void initPUll() {

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.pull_scrollview);

        swipeRefreshLayout.setColorSchemeResources(R.color.green,
                R.color.sandybrown,
                R.color.red,
                R.color.blue);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);;
        swipeRefreshLayout.setProgressBackgroundColor(R.color.main_bg);
        swipeRefreshLayout.setOnRefreshListener(this);

    }

    private void initView() {
        lv_tontant = (MyListView) view.findViewById(R.id.lv_tontant);


        adapter = new CommonAdapter<FindVo>(mContext, friendList, R.layout.friends_fragment_item) {

            @Override
            public void convert(ViewHolder holder, final FindVo findVo) {
                holder.setText(R.id.tv_name, findVo.getName());
                holder.setText(R.id.tv_cantont, findVo.getContant());
//                ImageLoaderUtil.displayImage1(findVo.getImgUrl(), (ImageView) holder.getView(R.id.iv_pic));

                final ImageView iv_attention = holder.getView(R.id.iv_attention);
                iv_attention.setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {

                        if (type==0){
                            iv_attention.setBackground(getResources().getDrawable(R.drawable.type_select_btn_presse));
                            type=1;
                        }else if (type==1){
                            iv_attention.setBackground(getResources().getDrawable(R.drawable.type_select_btn_pressed));
                            type=0;
                        }

                    }
                });


                AutoLinearLayout al_label = holder.getView(R.id.al_label);

                initLabel(labelList,al_label);

            }

        };
        lv_tontant.setAdapter(adapter);
    }


    private void initData() {
        friendList = new ArrayList<FindVo>();
        for (int i = 0; i < 9; i++) {
            FindVo findVo = new FindVo();
            findVo.setName("刘德华" + i);
            findVo.setContant("我在5号线灵芝捡到了钱包，失者请联系我！");
            friendList.add(findVo);
        }

        /**
         * 标签假数据
         */
        labelList = new ArrayList<>();
        labelList.add("5号线");
        labelList.add("灵芝（上车）");


    }


    /**
     * 初始化标签
     * @param al_label
     */
    private void initLabel(List<String> labelList,AutoLinearLayout al_label) {

        al_label.removeAllViews();
        for (int i=0;i<labelList.size();i++){
            View view = LayoutInflater.from(mContext).inflate(R.layout.all_label_iteml, al_label, false);
            final TextView tv_label = (TextView) view.findViewById(R.id.tv_label);

            tv_label.setText(labelList.get(i));
            al_label.addView(view);

        }

    }



    @Override
    public void onRefresh() {
        if(!isRefresh){
            isRefresh = true;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    FindVo findVo = new FindVo();
                    findVo.setName("andyLau");
                    findVo.setContant("万水千山总是情");
                    friendList.add(findVo);
                    adapter.notifyDataSetChanged();
                    isRefresh= false;
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 2000); }

    }
}
