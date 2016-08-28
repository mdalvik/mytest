package com.weixin.zhongli.mView;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.weixin.zhongli.R;
import com.weixin.zhongli.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class SlideShowView extends RelativeLayout {

    //自动轮播的时间间隔
    private int TIME_INTERVAL = 10;
    //自动轮播启用开关
    private final static boolean isAutoPlay = true; 
    //自定义轮播图的资源ID
    public List<String> imageList;
    //放轮播图片的ImageView 的list
    private List<ImageView> imageViewsList = new ArrayList<ImageView>();;
    //放圆点的View的list
    private List<View> dotViewsList = new ArrayList<View>();
    //ViewPager
    private ViewPager viewPager;
    //LinearLayout
    private LinearLayout op_slide_show_layout;
    //当前轮播页
    public int currentItem  = 0;
	//定时任务
    private ScheduledExecutorService scheduledExecutorService;
    //Handler
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(isAutoPlay){
                viewPager.setCurrentItem(currentItem);
            }
        }
        
    };
    
    private Context mContext;
    
	public SlideShowView(Context context) {
        this(context,null);
    }
    public SlideShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext= context;
    }
    
    /**
     * 显示播放
     */
    public void initShow(){
        initUI(mContext);
        if(isAutoPlay){
            startPlay();
        }
    }
    /**
     * 开始轮播图切换
     */
    private void startPlay(){
    	if(scheduledExecutorService==null){
	        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
	        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), TIME_INTERVAL, TIME_INTERVAL, TimeUnit.SECONDS);
    	}
    }
    /**
     * 停止轮播图切换
     */
    public void stopPlay(){
        scheduledExecutorService.shutdown();
    }
    /**
     * 初始化相关Data
     */
    public void setData(List<String> image){
    	imageList = image;
    }
    /**
     * 初始化Views等UI
     */
	private void initUI(Context context){
        View convertView = LayoutInflater.from(context).inflate(R.layout.op_slide_show, this, true);
        op_slide_show_layout = (LinearLayout) convertView.findViewById(R.id.op_slide_show_layout);
        currentItem = 0;
        op_slide_show_layout.removeAllViews();
        imageViewsList.clear();
        dotViewsList.clear();
        for (int i = 0; i < imageList.size(); i++) {
        	ImageView image =  new ImageView(context);
            ImageLoaderUtil.displayImage1(imageList.get(i),image);
            image.setScaleType(ScaleType.CENTER_CROP);
            image.setOnClickListener(onClicks);
            imageViewsList.add(image);
            
            /**添加点布局**/
            View dot_layout = LayoutInflater.from(mContext).inflate( R.layout.op_slide_show_view_dot, null);
            View dot = dot_layout.findViewById(R.id.dot);
            LayoutParams lp =  new LayoutParams(8,8);
            lp.setMargins(5, 0, 0, 0);
			dot.setLayoutParams(lp);
			dot.setPadding(8, 0, 8, 0);
            op_slide_show_layout.addView(dot_layout);
            dotViewsList.add(dot);
            ((View)dotViewsList.get(0)).setBackgroundResource(R.drawable.dot1);
		}
        
        viewPager = (ViewPager) convertView.findViewById(R.id.op_slide_show_viewPager);
        viewPager.setFocusable(true);
        
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }
    
    /**
     * 填充ViewPager的页面适配器
     */
    private class MyPagerAdapter  extends PagerAdapter{

        @Override
        public void destroyItem(View container, int position, Object object) {
        	if(position<imageViewsList.size())
        		((ViewPager)container).removeView(imageViewsList.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager)container).addView(imageViewsList.get(position));
            return imageViewsList.get(position);
        }

        @Override
        public int getCount() {
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {
            
        }
        
    }
    /**
     * ViewPager的监听器
     * 当ViewPager中页面的状态发生改变时调用
     */
    private class MyPageChangeListener implements OnPageChangeListener{

        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int pages) {
            switch (pages) {
            case 1:// 手势滑动，空闲中
                isAutoPlay = false;
                break;
            case 2:// 界面切换中
                isAutoPlay = true;
                break;
            case 0:
            	// 滑动结束，即切换完毕或者加载完毕
                // 当前为最后一张，此时从右向左滑，则切换到第一张
                if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                    viewPager.setCurrentItem(0);
                }
                // 当前为第一张，此时从左向右滑，则切换到最后一张
                else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
                    viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
                }
                break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            
        }

        @Override
        public void onPageSelected(int pos) {
            
            currentItem = pos;
            for(int i=0;i < dotViewsList.size();i++){
                if(i == pos){
                    ((View)dotViewsList.get(pos)).setBackgroundResource(R.drawable.dot1);
                }else {
                    ((View)dotViewsList.get(i)).setBackgroundResource(R.drawable.dot2);
                }
            }
        }
        
    }
    
    /**
     *执行轮播图切换任务
     */
    private class SlideShowTask implements Runnable{

        @Override
        public void run() {
            synchronized (viewPager) {
                currentItem = ( currentItem + 1 ) % imageViewsList.size();
                handler.obtainMessage().sendToTarget();
            }
        }
        
    }
    
    private OnClickListener onClicks;
    public OnClickListener getonCLick() {
        return onClicks;
    }
    public void setOnClick(OnClickListener onCLick) {
        this.onClicks = onCLick;
    }
    
}