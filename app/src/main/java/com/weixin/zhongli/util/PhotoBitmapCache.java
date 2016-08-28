package com.weixin.zhongli.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class PhotoBitmapCache {
    private LruCache<String, Bitmap> mCache;

    public PhotoBitmapCache() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();   
        int maxSize =maxMemory/8;
        mCache = new LruCache<String, Bitmap>(maxSize) {
            @SuppressLint("NewApi")
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }

    public Bitmap getBitmap(String url) {
        return mCache.get(url);
    }

    public void putBitmap(String url, Bitmap bitmap) {
        
        mCache.put(url, bitmap);
    }
}
