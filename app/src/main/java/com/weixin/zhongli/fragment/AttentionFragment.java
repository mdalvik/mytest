package com.weixin.zhongli.fragment;

import android.content.Context;
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

import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.weixin.zhongli.R;
import com.weixin.zhongli.activity.ImagePreviewActivity;
import com.weixin.zhongli.adapter.CommonAdapter;
import com.weixin.zhongli.adapter.ViewHolder;
import com.weixin.zhongli.mView.MyListView;
import com.weixin.zhongli.mView.ShowMoreTextView;
import com.weixin.zhongli.util.ImageLoaderUtil;
import com.weixin.zhongli.vo.FindVo;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * 关注界面
 */
public class AttentionFragment extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener {

    private Context mContext;
    List<FindVo> findList;
    MyListView lv_tontant;
    List<String> labelList;
    CommonAdapter<FindVo> adapter;
    View view;
    private boolean isRefresh = false;//是否刷新中

    SwipeRefreshLayout  swipeRefreshLayout;

    private NineGridImageViewAdapter<String> mAdapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext=getActivity();
         view = LayoutInflater.from(mContext).inflate(R.layout.fragment_attention, null);
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


    private void initView() {
        lv_tontant = (MyListView) view.findViewById(R.id.attention_lv_tontant);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.pull_scrollview);


        


        /**
         * 标签假数据
         */
        labelList = new ArrayList<>();
        labelList.add("独孤九剑");
        labelList.add("九阴真经");
        labelList.add("如来神掌");

        /**
         * 图片假数据
         */
        final ArrayList<String> picList = new ArrayList<>();
        for (int i=0;i<5;i++){
            if (i%3==0){
                picList.add("http://www.wallcoo.com/nature/Magic_Landscapes_by_Michael_Breitung/wallpapers/1680x1050/quiraingview.jpg");
            }else if (i%3==1){
                picList.add("http://pic51.nipic.com/file/20141030/2531170_080422201000_2.jpg");
            }else if(i%3==2){
                picList.add("http://img.taopic.com/uploads/allimg/130807/240451-130PFI24945.jpg");
            }else {
                picList.add("http://pic1.nipic.com/2008-10-07/200810782928597_2.jpg");

            }
        }


        mAdapter = new NineGridImageViewAdapter<String>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String s) {
                ImageLoaderUtil.displayImage1(s,imageView);

            }

            @Override
            protected void onItemImageClick(Context context, int index, List<String> list) {
                super.onItemImageClick(context, index, list);
                ImagePreviewActivity.openThisActivity(mContext,picList, index);
            }
        };



        adapter = new CommonAdapter<FindVo>(mContext, findList, R.layout.main_fragment_item) {

            @Override
            public void convert(ViewHolder holder, final FindVo findVo) {
                holder.setText(R.id.tv_name, findVo.getName());
                ShowMoreTextView tv_cantont = holder.getView(R.id.tv_cantont);
                tv_cantont.setContent("\"　  东风夜放花千树，更吹落，星如雨。宝马雕车香满路。凤箫声动，玉壶光转，一夜鱼龙舞。\n" +
                        "　蛾儿雪柳黄金缕，笑语盈盈暗香去。众里寻他千百度，蓦然回首，那人却在，灯火阑珊处。\"");
//                ImageLoaderUtil.displayImage1(findVo.getImgUrl(), (ImageView) holder.getView(R.id.iv_pic));
                AutoLinearLayout al_label = holder.getView(R.id.al_label);

                initLabel(labelList,al_label);

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

    private void initData() {
         findList = new ArrayList<>();
        for (int i=0;i<3;i++){
            FindVo findVo = new FindVo();
            findVo.setName("刘德华"+i);
            findVo.setContant("　  东风夜放花千树，更吹落，星如雨。宝马雕车香满路。凤箫声动，玉壶光转，一夜鱼龙舞。\n" +
                    "　蛾儿雪柳黄金缕，笑语盈盈暗香去。众里寻他千百度，蓦然回首，那人却在，灯火阑珊处。");
            findList.add(findVo);
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
                    findList.add(findVo);
                    adapter.notifyDataSetChanged();
                    isRefresh= false;
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 2000); }


    }
}
