package com.weixin.zhongli.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int layoutId;
    Handler hand;

    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.layoutId = layoutId;
        hand = new Handler();
    }

    public void setDatas(List<T> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    public void addData(T data) {
        mDatas.add(data);
        notifyDataSetChanged();
    }

    public void addData(int location, T data) {
        mDatas.add(location, data);
    }

    public void addAllDatas(final List<T> datas) {
        if (datas == null || datas.size() < 1)
            return;
        hand.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < datas.size(); i++) {
                    if (!mDatas.contains(datas.get(i)))
                        mDatas.add(datas.get(i));
                }
                notifyDataSetChanged();
            }
        });
    }

    public boolean removeItem(T data) {
        boolean result = mDatas.remove(data);
        notifyDataSetChanged();
        return result;
    }

    public boolean removeItems(List<T> datas) {
        boolean result = mDatas.removeAll(datas);
        notifyDataSetChanged();
        return result;
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    @Override
    public int getCount() {
        if (mDatas!=null){
            return mDatas.size();
        }
        return 0;

    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent,
                layoutId, position);
        convert(holder, getItem(position));
        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder, T t);

}
