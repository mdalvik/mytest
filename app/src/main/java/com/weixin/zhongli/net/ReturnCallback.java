package com.weixin.zhongli.net;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.socks.library.KLog;
import com.weixin.zhongli.mView.MyToast;
import com.weixin.zhongli.vo.ReturnVo;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;


public abstract class ReturnCallback extends Callback<ReturnVo> {
    String logTag = "response";
    boolean isShowError = true;
    Context context;
    public ReturnVo returnVo;

    /**
     * 网络访问回调函数,不显示错误提示的构造函数
     *
     * @param logTag 打印结果的tag，用于定位调用的接口或方法
     */
    public ReturnCallback(String logTag) {
        this.logTag = logTag;
        this.isShowError = false;
    }

    /**
     * 网络访问回调函数,显示错误提示的构造函数
     *
     * @param context context
     * @param logTag  打印结果的tag，用于定位调用的接口或方法
     */
    public ReturnCallback(Context context, String logTag) {
        this.context = context;
        this.logTag = logTag;
    }

    @Override
    public ReturnVo parseNetworkResponse(Response response) throws Exception {
        String str = response.body().string();
        KLog.json(logTag, str);
        returnVo = JSON.parseObject(str, ReturnVo.class);
        if (!"200".equals(returnVo.getStatus())) {
            if (!TextUtils.isEmpty(returnVo.getCode()) && Integer.parseInt(returnVo.getCode()) > 0)
                throw new StatusErrorException(returnVo.getMsg() + "(" + Integer.parseInt(returnVo.getCode()) % 1000000 + ")");
            else
                throw new StatusErrorException(returnVo.getMsg());
        } else if (null == returnVo.getData())
            throw new StatusErrorException("数据异常，请重试");
        return returnVo;
    }

    @Override
    public void onError(Call call, Exception e) {
        KLog.e(logTag, e.getMessage());
        e.printStackTrace();
        if ("Canceled".equals(e.getMessage()) || "Socket closed".equals(e.getMessage())) {
            return;
        }
        if (isShowError) {
            if (e instanceof StatusErrorException)

                MyToast.showToast(context, e.getMessage());
            else
                MyToast.showToast(context, "网络异常，请保持网络连接");
        }
    }

    /**
     * Status错误的异常类
     */
    public class StatusErrorException extends RuntimeException {
        public StatusErrorException(String detailMessage) {
            super(detailMessage);
        }
    }

}
