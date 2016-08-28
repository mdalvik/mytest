package com.weixin.zhongli.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by j on 2016/7/8
 */
public class FindVo implements Serializable {

    public boolean isShowAll = false;
    //头像
    private  String imgUrl;
    //名字
    private String name;
    //内容
    private String contant;
    //标签集合
    private List<String> labelList;
    //图片集合
    private List<String> picUrlList;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContant() {
        return contant;
    }

    public void setContant(String contant) {
        this.contant = contant;
    }

    public List<String> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<String> labelList) {
        this.labelList = labelList;
    }

    public List<String> getPicUrlList() {
        return picUrlList;
    }

    public void setPicUrlList(List<String> picUrlList) {
        this.picUrlList = picUrlList;
    }

    @Override
    public String toString() {
        return "FindVo{" +
                "isShowAll=" + isShowAll +
                ", imgUrl='" + imgUrl + '\'' +
                ", name='" + name + '\'' +
                ", contant='" + contant + '\'' +
                ", labelList=" + labelList +
                ", picUrlList=" + picUrlList +
                '}';
    }
}
