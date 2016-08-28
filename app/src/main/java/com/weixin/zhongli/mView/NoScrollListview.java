package com.weixin.zhongli.mView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by j on 2016/8/4
 */
public class NoScrollListview extends ListView {
    public NoScrollListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
