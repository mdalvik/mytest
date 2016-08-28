package com.weixin.zhongli.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weixin.zhongli.R;
import com.weixin.zhongli.mView.TopView;
import com.weixin.zhongli.mView.ZoomImageView;
import com.weixin.zhongli.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

public class ImagePreviewActivity extends Activity implements OnClickListener {

	/**
	 * 顶部栏
	 */
	private TopView topView;

	private ViewPager mViewPager;

	private TextView text_pages;

	/**
	 * 当前浏览的下标
	 */
	private int currentIndex;
	/**
	 * 图片集合
	 */
	private List<String> imgs;
	/**
	 * 图片控件集合
	 */
	private ImageView[] mImageViews;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_preview);
		context = this;
		currentIndex = getIntent().getIntExtra("currentIndex", 0);
		imgs = getIntent().getStringArrayListExtra("imgs");
		mImageViews = new ImageView[imgs.size()];
		initView();
	}

	private void initView() {
		topView= (TopView) findViewById(R.id.top_view);
		mViewPager= (ViewPager) findViewById(R.id.id_viewpager);
		text_pages= (TextView) findViewById(R.id.text_pages);


		topView.getLeftView().setOnClickListener(this);
		topView.getMidView().setText("查看图片");
		if (imgs.size() >= 1) {
			String str="<font color='#ff4400'>"+(currentIndex + 1)+"</font>"+"/" + imgs.size();
			text_pages.setText(Html.fromHtml(str));
		}
		mViewPager.setAdapter(new PagerAdapter() {
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				ZoomImageView imageView = new ZoomImageView(context);
				ImageLoaderUtil.displayImage1(imgs.get(position), imageView);
				container.addView(imageView);
				mImageViews[position] = imageView;
				return imageView;
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(mImageViews[position]);
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return imgs.size();
			}
		});
		mViewPager.setCurrentItem(currentIndex);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int index) {
				currentIndex = index;
				String str="<font color='#ff4400'>"+(currentIndex + 1)+"</font>"+"/" + imgs.size();
				text_pages.setText(Html.fromHtml(str));
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
	
	/**
	 * 外部打开这个Activity
	 * 
	 * @param context
	 * @param imgs
	 *            图片List<String>，图片路径可以是网络的，也可以是本地的 <br>
	 *            http://site.com/image.png //from Web 
	 *            file:///mnt/sdcard/image.png // from SD card
	 *            file:///mnt/sdcard/video.mp4 // from SD card (video thumbnail)
	 *            content://media/external/images/media/13 // from content provider 
	 *            content://media/external/video/media/13 // from content provider (video thumbnail) 
	 *            assets://image.png // from assets 
	 *            drawable:// + R.drawable.img // from drawables(non-9patch images)
	 * @param index
	 *            当前查看的图片下标
	 */
	public static void openThisActivity(final Context context,
			final ArrayList<String> imgs,
			final int index) {
		Intent intent = new Intent(context, ImagePreviewActivity.class);
		intent.putExtra("imgs", imgs);
		intent.putExtra("currentIndex", index);
		context.startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		if (v == topView.getLeftView()) {
			finish();
		}
	}
}
