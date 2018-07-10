package com.marlon.myretrofitclient.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by KangLong on 2017/9/26.
 */

public class Userinfo implements Serializable {
    private String portrait;
    private String mobile;
    private String name;
    private Long point;
    private Long userId;
    private List<MobileVideoResource> resources;
    //年龄
    private Integer year;

    //身高
    private Integer height;

    //体重
    private Integer weight;

    //性别
    private String sex;


    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<MobileVideoResource> getResources() {
        return resources;
    }

    public void setResources(List<MobileVideoResource> resources) {
        this.resources = resources;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }


    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
