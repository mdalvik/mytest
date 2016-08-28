package com.weixin.zhongli.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.weixin.zhongli.R;
import com.weixin.zhongli.activity.ImagePreviewActivity;
import com.weixin.zhongli.adapter.CommonAdapter;
import com.weixin.zhongli.adapter.ViewHolder;
import com.weixin.zhongli.dialog.MorePopWindow;
import com.weixin.zhongli.inter.JsonCallBack;
import com.weixin.zhongli.mView.MyListView;
import com.weixin.zhongli.mView.MyToast;
import com.weixin.zhongli.mView.NoScrollListview;
import com.weixin.zhongli.mView.ShowMoreTextView;
import com.weixin.zhongli.net.ZdOkHttpUtils;
import com.weixin.zhongli.util.ImageLoaderUtil;
import com.weixin.zhongli.vo.FindVo;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

/**
 * 全部界面
 */
public class AllFragment extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener {

    private Context mContext;
    MyListView lv_tontant;
    AutoLinearLayout al_filtrate;
    private ArrayList<View> tvList = new ArrayList<View>();

    List<FindVo> findList;
    List<FindVo> messageList;
    List<String> labelList;
    List<String> filtrateList;
    CommonAdapter<FindVo> adapter;
    View view;
    private boolean isRefresh = false;//是否刷新中

    List<String> picList;

    SwipeRefreshLayout swipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        view = LayoutInflater.from(mContext).inflate(R.layout.fragment_all, null);
        ImageLoaderUtil.initImageLoader(mContext);
        initData("");

        return view;
    }

    private void initPUll() {

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.pull_scrollview);

        swipeRefreshLayout.setColorSchemeResources(R.color.green,
                R.color.sandybrown,
                R.color.red,
                R.color.blue);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        ;
        swipeRefreshLayout.setProgressBackgroundColor(R.color.main_bg);
        swipeRefreshLayout.setOnRefreshListener(this);

    }


    private void initEvent() {

        lv_tontant.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (lv_tontant.getLastVisiblePosition() == (lv_tontant.getCount() - 1)) {

                            if (!isRefresh) {
                                isRefresh = true;
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        for (int i = 0; i < 3; i++) {
                                            FindVo findVo = new FindVo();
                                            findVo.setName("andyLau");
                                            findVo.setContant("万水千山总是情");
                                            findList.add(findVo);
                                        }
                                        adapter.notifyDataSetChanged();
                                        isRefresh = false;
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                }, 1000);
                            }
                        }
                        // 判断滚动到顶部

                        if (lv_tontant.getFirstVisiblePosition() == 0) {
                        }

                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });


    }


    private void initView() {
        lv_tontant = (MyListView) view.findViewById(R.id.all_lv_tontant);
        al_filtrate = (AutoLinearLayout) view.findViewById(R.id.all_al_filtrate);

//        mPullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pull_scrollview);
//        mall_go_top = (ImageView) view.findViewById(R.id.mall_main_go_top_iv);


        adapter = new CommonAdapter<FindVo>(mContext, findList, R.layout.main_fragment_item) {

            @Override
            public void convert(ViewHolder holder, final FindVo findVo) {
                holder.setText(R.id.tv_name, findVo.getName());
//                ImageLoaderUtil.displayImage1(findVo.getImgUrl(), (ImageView) holder.getView(R.id.iv_pic));
                AutoLinearLayout al_label = holder.getView(R.id.al_label);

                initLabel(labelList, al_label);

                NoScrollListview lv_message = holder.getView(R.id.all_lv_message);
                initMessage(lv_message);


                ShowMoreTextView tv_cantont = holder.getView(R.id.tv_cantont);
                tv_cantont.setContent("东风仿佛吹开了盛开鲜花的千棵树，又如将空中的繁星吹落，" +
                        "像阵阵星雨。华丽的香车宝马在路上来来往往，各式各样的醉人香气弥漫着大街。" +
                        "悦耳的音乐之声四处回荡，亦如凤萧和玉壶在空中流光飞舞，热闹的夜晚鱼龙形的彩灯在翻腾。美人的头上都戴着亮丽的饰物，" +
                        "晶莹多彩的装扮在人群中晃动。她们面容微笑，带着淡淡的香气从人面前经过。我寻找那人千百次，都没看见她，不经意间一回头，却看见了她立在灯火零落之处。");

                final ImageView iv_more = holder.getView(R.id.all_iv_more);
                iv_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        initPOPU(iv_more);
                        showPopUp(iv_more);

                    }
                });

                NineGridImageView nineGridImageView = holder.getView(R.id.layout_nine_grid);
                if (picList != null && picList.size() > 0) {
                    nineGridImageView.setVisibility(View.VISIBLE);
                }
                nineGridImageView.setAdapter(mAdapter);
                nineGridImageView.setImagesData(picList);


            }


        };
        lv_tontant.setAdapter(adapter);
    }

    private NineGridImageViewAdapter<String> mAdapter = new NineGridImageViewAdapter<String>() {
        @Override
        protected void onDisplayImage(Context context, ImageView imageView, String s) {
            ImageLoaderUtil.displayImage1(s, imageView);

        }

        @Override
        protected void onItemImageClick(Context context, int index, List<String> list) {
            super.onItemImageClick(context, index, list);
            ImagePreviewActivity.openThisActivity(mContext, (ArrayList<String>) picList, index);
        }
    };

    private void initMessage(final NoScrollListview lv_message) {
        final CommonAdapter<FindVo> messageAdapter = new CommonAdapter<FindVo>(mContext, messageList, R.layout.messge_item) {

            @Override
            public void convert(ViewHolder holder, final FindVo findVo) {
                holder.setText(R.id.tv_message, findVo.getName() + ":" + findVo.getContant());
            }


        };
        lv_message.setAdapter(messageAdapter);

    }

    private void showPopUp(View v) {
        final MorePopWindow morePopWindow = new MorePopWindow((Activity) mContext);
        morePopWindow.showPopupWindow(v);
        morePopWindow.more_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + 665348));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                morePopWindow.dismiss();
            }
        });
    }


    /**
     * 初始化筛选
     */
    private void initFiltrate() {
        al_filtrate.removeAllViews();
        for (int i = 0; i < filtrateList.size(); i++) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.all_filtrate_item, al_filtrate, false);
            final TextView tv_filtrate = (TextView) view.findViewById(R.id.tv_filtrate);
            final View line = view.findViewById(R.id.item_view);
            tv_filtrate.setText(filtrateList.get(i));
            line.setBackgroundColor(Color.TRANSPARENT);
            tv_filtrate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (View view : tvList) {
                        TextView tv = (TextView) view.findViewById(R.id.tv_filtrate);
                        View line = view.findViewById(R.id.item_view);

                        tv.setTextColor(mContext.getResources().getColor(R.color.c_666666));
                        line.setBackgroundColor(Color.TRANSPARENT);
                    }
                    tv_filtrate.setTextColor(getResources().getColor(R.color.mian_color));
                    line.setBackgroundColor(getResources().getColor(R.color.mian_color));
                }
            });
            if (i == 0) {
                tv_filtrate.setTextColor(getResources().getColor(R.color.mian_color));
                line.setBackgroundColor(getResources().getColor(R.color.mian_color));

            }

            tvList.add(view);
            al_filtrate.addView(view);

        }

    }

    /**
     * 初始化标签
     *
     * @param al_label
     */
    private void initLabel(List<String> labelList, AutoLinearLayout al_label) {

        al_label.removeAllViews();
        for (int i = 0; i < labelList.size(); i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.all_label_iteml, al_label, false);
            final TextView tv_label = (TextView) view.findViewById(R.id.tv_label);
            tv_label.setText(labelList.get(i));
            al_label.addView(view);

        }

    }

    public void initData(String s) {
        MyToast.showToast(mContext, s + "没");
        findList = new ArrayList<FindVo>();
//        for (int i = 0; i < 3; i++) {
//            FindVo findVo = new FindVo();
//            findVo.setName("刘德华" + i);
//            findVo.setContant("我在5号线灵芝捡到了钱包，失者请联系我！");
//            findList.add(findVo);
//        }
//        Log.e("接口测试",""+findList.toString());
//
//        Gson gson = new Gson();
//        String str = gson.toJson(findList);
//        List arrayList = gson.fromJson(str, new TypeToken<ArrayList<FindVo>>(){}.getType());
//
//        Log.e("接口测试2",""+arrayList.toString());


        messageList = new ArrayList<FindVo>();
        for (int i=0;i<9;i++){
            FindVo findVo = new FindVo();
            if (i%2==0){
                findVo.setName("黎明:");
                findVo.setContant("感激不尽，正在着急寻找呢");
            }else {
                findVo.setName("刘德华回复黎明:");
                findVo.setContant("要证明你是不是失主先");
            }
            messageList.add(findVo);
        }

        /**
         * 标签假数据
         */
        labelList = new ArrayList<>();
        labelList.add("5号线");
        labelList.add("灵芝（上车）");

        /**
         * 筛选假数据
         */
        filtrateList = new ArrayList<>();
        filtrateList.add("公交");
        filtrateList.add("地铁");
        filtrateList.add("商场");
        filtrateList.add("电影院");
        filtrateList.add("学校");
        filtrateList.add("海边");
        filtrateList.add("其他");

        /**
         * 图片假数据
         */
        picList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            if (i % 3 == 0) {
//                picList.add("http://www.uyooo.com/d/file/news/ba/2016-06-05/7ec4a37be370c1a1f9264bbf69c7644c.jpg");
                picList.add("http://d.hiphotos.baidu.com/image/h%3D200/sign=201258cbcd80653864eaa313a7dca115/ca1349540923dd54e54f7aedd609b3de9c824873.jpg");
            } else if (i % 3 == 1) {
//                picList.add("http://www.sznews.com/ent/images/attachement/jpg/site3/20160125/4439c4524387180fabe304.jpg");
                picList.add("http://img1.imgtn.bdimg.com/it/u=1089582262,166446285&fm=206&gp=0.jpg");
            } else {
//                picList.add("http://img1.imgtn.bdimg.com/it/u=2324917859,3729993072&fm=21&gp=0.jpg");
                picList.add("http://img5.imgtn.bdimg.com/it/u=173521105,1887026872&fm=21&gp=0.jpg");
            }
        }


        HashMap<String, String> map = new HashMap<String, String>();

        map.put("pageSize", 20 + "");
        map.put("page", 0 + "");
        map.put("gameType", 0 + "");
//        http://localhost:8080/ZdStruts01/hello.do
//        http://a8.tvesou.com/v3/olympic/game_list_one
        ZdOkHttpUtils.getInstance().normalGet("http://192.168.11.190:8080/ssi/hello.do", map, getActivity(), new JsonCallBack() {
            @Override
            public void onJSONResponse(boolean success, Response response, Throwable throwable) {
                if (success) {
                    String str = null;
                    try {
                        int code = response.code();
                        Log.e("success", "请求成功,code=" + code);
                        str = response.body().string();
                        Message msg = new Message();
                        msg.what=1;
                        msg.obj = str;
                        nextHander.sendMessage(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.e("success", str);
                } else {
                    Log.e("success", "请求失败," + throwable.getMessage());
                }
            }
        });


    }


    @Override
    public void onRefresh() {
        if (!isRefresh) {
            isRefresh = true;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    FindVo findVo = new FindVo();
                    findVo.setName("andyLau");
                    findVo.setContant("万水千山总是情");
                    findList.add(findVo);
                    adapter.notifyDataSetChanged();
                    isRefresh = false;
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }

    }

    Handler nextHander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String str = (String) msg.obj;
                    Gson gson = new Gson();
                    findList = gson.fromJson(str, new TypeToken<ArrayList<FindVo>>(){}.getType());
                    Log.e("接口测试2",""+findList.toString());
                    initView();
                    initFiltrate();
                    initEvent();
                    initPUll();
                    break;
            }
        }
    };
}
