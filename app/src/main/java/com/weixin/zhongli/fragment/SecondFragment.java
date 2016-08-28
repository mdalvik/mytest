package com.weixin.zhongli.fragment;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.weixin.zhongli.R;
import com.weixin.zhongli.activity.ImagePreviewActivity;
import com.weixin.zhongli.adapter.CommonAdapter;
import com.weixin.zhongli.adapter.ViewHolder;
import com.weixin.zhongli.mView.NoScrollListview;
import com.weixin.zhongli.mView.ShowMoreTextView;
import com.weixin.zhongli.util.ImageLoaderUtil;
import com.weixin.zhongli.util.ScreenUtil;
import com.weixin.zhongli.vo.FindVo;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener{
    private Context mContext;
    ListView lv_tontant;
    View view;

    List<FindVo> findList;
    List<String> picList;

    CommonAdapter<FindVo> adapter;
    PopupWindow popupWindow;

    SwipeRefreshLayout  swipeRefreshLayout;

    private boolean isRefresh = false;//是否刷新中

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext=getActivity();
   view = LayoutInflater.from(mContext).inflate(R.layout.fragment_second, null);
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
                                            findList.add(findVo);
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


    private void initData() {
        findList = new ArrayList<>();
        for (int i=0;i<3;i++){
            FindVo findVo = new FindVo();
            findVo.setName("刘德华"+i);
            findVo.setContant("　  东风夜放花千树，更吹落，星如雨。宝马雕车香满路。凤箫声动，玉壶光转，一夜鱼龙舞。\n" +
                    "　蛾儿雪柳黄金缕，笑语盈盈暗香去。众里寻他千百度，蓦然回首，那人却在，灯火阑珊处。");
            findList.add(findVo);
        }


        /**
         * 图片假数据
         */
        picList = new ArrayList<>();
        for (int i=0;i<2;i++){
            if (i%3==0){
//                picList.add("http://www.uyooo.com/d/file/news/ba/2016-06-05/7ec4a37be370c1a1f9264bbf69c7644c.jpg");
                picList.add("http://pic1.nipic.com/2008-10-07/200810782928597_2.jpg");
            }else if (i%3==1){
//                picList.add("http://www.sznews.com/ent/images/attachement/jpg/site3/20160125/4439c4524387180fabe304.jpg");
                picList.add("http://pic12.nipic.com/20101225/3337728_164258097144_2.jpg");
            }else {
//                picList.add("http://img1.imgtn.bdimg.com/it/u=2324917859,3729993072&fm=21&gp=0.jpg");
                picList.add("http://img.taopic.com/uploads/allimg/140713/318752-140G311132474.jpg");
            }
        }

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
    lv_tontant = (ListView) view.findViewById(R.id.lv_tontant);



        adapter = new CommonAdapter<FindVo>(mContext, findList, R.layout.second_fragment_item) {

            @Override
            public void convert(ViewHolder holder, final FindVo findVo) {
                holder.setText(R.id.tv_name, findVo.getName());
                ShowMoreTextView tv_cantont = holder.getView(R.id.tv_cantont);
                tv_cantont.setContent("  东风仿佛吹开了盛开鲜花的千棵树，又如将空中的繁星吹");

//                ImageLoaderUtil.displayImage1(findVo.getImgUrl(), (ImageView) holder.getView(R.id.iv_pic));
                AutoLinearLayout al_label = holder.getView(R.id.al_label);


                NoScrollListview lv_message = holder.getView(R.id.all_lv_message);
                initMessage(lv_message);

                final ImageView iv_more = holder.getView(R.id.all_iv_more);
                iv_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        initPOPU(iv_more);
                        showPopUp(iv_more);

                    }
                });

                NineGridImageView nineGridImageView = holder.getView(R.id.layout_nine_grid);
                if (picList!=null&&picList.size()>0){
                    nineGridImageView.setVisibility(View.VISIBLE);
                }
                nineGridImageView.setAdapter(mAdapter);
                nineGridImageView.setImagesData(picList);

            }



        };
        lv_tontant.setAdapter(adapter);


    }

    private NineGridImageViewAdapter<String> mAdapter=new NineGridImageViewAdapter<String>() {
        @Override
        protected void onDisplayImage(Context context, ImageView imageView, String s) {
            ImageLoaderUtil.displayImage1(s,imageView);

        }

        @Override
        protected void onItemImageClick(Context context, int index, List<String> list) {
            super.onItemImageClick(context, index, list);
            ImagePreviewActivity.openThisActivity(mContext, (ArrayList<String>) picList, index);
        }
    };


    private void showPopUp(View v) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.all_more_item, null);
        popupWindow = new PopupWindow(view, ScreenUtil.px2dip(mContext,1400),AutoLinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        int[] location = new int[2];
        v.getLocationOnScreen(location);

        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0]-popupWindow.getWidth(), location[1]);
    }



    private void initMessage(final NoScrollListview lv_message) {
        if (findList==null&&findList.size()==0){
            lv_message.setVisibility(View.GONE);
        }
        final CommonAdapter<FindVo> messageAdapter = new CommonAdapter<FindVo>(mContext, findList, R.layout.messge_item) {

            @Override
            public void convert(ViewHolder holder, final FindVo findVo) {
                holder.setText(R.id.tv_message,findVo.getName()+":"+findVo.getContant());
            }


        };
        lv_message.setAdapter(messageAdapter);

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
                    findList.add(findVo);
                    adapter.notifyDataSetChanged();
                    isRefresh= false;
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 2000); }
    }

}
