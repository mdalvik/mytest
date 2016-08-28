package com.weixin.zhongli.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.weixin.zhongli.R;

import java.io.File;


/**
 * *****************************************************
 *
 * @author LK
 * @date 2015-12-7 下午12:27:12
 * @Company 深圳市动态电子商务有限公司
 * @Description：TODO 加载图片工具类
 * *****************************************************
 */
public class ImageLoaderUtil {
    private static Context ctx;
    /**
     * 获取缓存目录
     *
     * @return
     * @author: LK
     * @date: 2016年5月5日
     */
    public static String getCachePath() {
        return ImageLoader.getInstance().getDiskCache().getDirectory().getAbsolutePath();
    }
    public static ImageLoader getImageLoader(Context context) {
        return ImageLoader.getInstance();
    }

    /**
     * 加载图片
     * 网络图片          ( http://site.com/image.png )
     * SD卡图片        ( file:///mnt/sdcard/image.png )
     * content ( provider  content://media/external/images/media/13 )
     * assets  ( assets://image.png )
     * drawables  ( drawable:// + R.drawable.img )
     *
     * @param uri
     * @param imageView
     * @author: LK
     * @date: 2016年5月5日
     */
    public static void displayImage(String uri, ImageView imageView) {
        ImageLoader.getInstance().displayImage(uri, imageView);
    }

    /**
     * 加载网络图片
     *
     * @param uri
     * @param imageView
     */
    public static void displayImage1(final String uri, final ImageView imageView) {
        final DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.welcome_bg)
                // 加载等待 时显示的图片.resetViewBeforeLoading()
                .showImageForEmptyUri(R.drawable.welcome_bg)
                // 加载数据为空时显示的图片
                .showImageOnFail(R.drawable.welcome_bg)
                // 加载失败时显示的图片
                .bitmapConfig(Bitmap.Config.RGB_565)
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisk(true)
                // 图片加载好后渐入的动画时间
                // .displayer(new FadeInBitmapDisplayer(100))
                .build();
        ImageLoader.getInstance().displayImage(uri, imageView, options);
    }

    public static void displayRoundedImage(String uri, ImageView imageView) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.welcome_bg)		// 加载等待 时显示的图片
                .showImageForEmptyUri(R.drawable.welcome_bg)	// 加载数据为空时显示的图片
                .showImageOnFail(R.drawable.welcome_bg)		// 加载失败时显示的图片
                .bitmapConfig(Bitmap.Config.RGB_565)			// 设置图片以如何的编码方式显示
                .cacheInMemory(true)							// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)								// 设置下载的图片是否缓存在SD卡中
                .resetViewBeforeLoading(true)					// 设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(ctx.getResources().getDimensionPixelOffset(R.dimen.px_4)))		// 图片加载好后渐入的动画时间 RoundedBitmapDisplayer圆角
                .build();
        ImageLoader.getInstance().displayImage(uri, imageView, options);
    }

    /**
     * 清除ImageLoad图片缓存
     *
     * @author: LK
     * @date: 2016年5月5日
     */
    public static void clearImageCache() {
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiskCache();
    }

    /**
     * 初始化ImageLoad
     *
     * @param context
     * @author: LK
     * @date: 2016年5月5日
     */
    public static void initImageLoader(Context context) {
        ctx=context;
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.welcome_bg)        // 加载等待 时显示的图片
                .showImageForEmptyUri(R.drawable.welcome_bg)    // 加载数据为空时显示的图片
                .showImageOnFail(R.drawable.default_load)        // 加载失败时显示的图片
                .bitmapConfig(Bitmap.Config.RGB_565)            // 设置图片以如何的编码方式显示
                .cacheInMemory(true)                            // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)                                // 设置下载的图片是否缓存在SD卡中
                .resetViewBeforeLoading(true)                    // 设置图片在下载前是否重置，复位
                .displayer(new FadeInBitmapDisplayer(200))        // 图片加载好后渐入的动画时间 c圆角
                .build();

        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "com.dtds.tao_affordable/cache");// 缓存文件的目录
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPoolSize(3)                           // 线程池内加载的数量
                .denyCacheImageMultipleSizesInMemory()       // default 设置当前线程的优先级
                .diskCache(new UnlimitedDiskCache(cacheDir)) // 缓存文件的目录
                .diskCacheSize(200 * 1024 * 1024)            // 200 Mb sd卡(本地)缓存的最大值
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())// 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000))// connectTimeout (5 s), readTimeout (30 s)超时时间
                .defaultDisplayImageOptions(options).build();
        ImageLoader.getInstance().init(config);
    }

    public static DisplayImageOptions getPhotoImageOption() {
        Integer extra = 1;
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .showImageForEmptyUri(R.drawable.welcome_bg).showImageOnFail(R.drawable.welcome_bg)
                .showImageOnLoading(R.drawable.welcome_bg)
                .extraForDownloader(extra)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        return options;
    }

    public static void displayImage(Context context, ImageView imageView, String url, DisplayImageOptions options) {
        getImageLoader(context).displayImage(url, imageView, options);
    }

    public static void displayImage(Context context, ImageView imageView, String url, DisplayImageOptions options, ImageLoadingListener listener) {
        getImageLoader(context).displayImage(url, imageView, options, listener);
    }

}
