package com.weixin.zhongli.net;

import android.app.Activity;

import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by Administrator on 2016/5/17
 */
public class BaseHttp {
    Activity context;

    public BaseHttp(Activity context) {
        this.context = context;
    }

    public void post(String url, Map<String, String> params, Callback callback) {
        params.put("token", context.getIntent().getStringExtra("token")!=null?context.getIntent().getStringExtra("token"):"");
        params.put("reqSource", context.getIntent().getStringExtra("reqSource")!=null?context.getIntent().getStringExtra("reqSource"):"b2c");
        params.put("sysType", context.getIntent().getStringExtra("sysType")!=null?context.getIntent().getStringExtra("sysType"):"2");
        KLog.e("post", url + "?" + params);
        OkHttpUtils.post().url(url).params(params).tag(context).build().execute(callback);
    }

}
