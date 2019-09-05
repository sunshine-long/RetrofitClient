package com.marlon.retrofitclentdemo.bean;

import java.io.Serializable;

/**
 * Created by KangLong on 2017/9/26.
 */

public class MobileVideoResource implements Serializable {
    private String type;
    //视频文件URL
    private String url;
    //封面
    private String cover;
    //如果是付费的，需要填写价格
    private int cost;
    //标题
    private String title;

    private String content;

    private long resourceId;

    //是否是自己计划
    private Boolean isPlan;

    //课程难度星级
    private Integer star;

    //课程时长
    private Integer duration;

    private String trainType;

    public Boolean getPlan() {
        return isPlan;
    }

    public void setPlan(Boolean plan) {
        isPlan = plan;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }
}
