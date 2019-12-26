package com.zqy.sharecommunity.entity;

import java.util.Date;

public class VideoAttr {
    private Integer id;

    private String videoName;

    private String videoActors;

    private String videoDirector;

    private String videoLanguage;

    private String videoStatus;

    private Date createTime;

    private Date updateTime;

    private String showDate;

    private String remark;

    private String intro;

    private String img;

    private Integer videoId;

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName == null ? null : videoName.trim();
    }

    public String getVideoActors() {
        return videoActors;
    }

    public void setVideoActors(String videoActors) {
        this.videoActors = videoActors == null ? null : videoActors.trim();
    }

    public String getVideoDirector() {
        return videoDirector;
    }

    public void setVideoDirector(String videoDirector) {
        this.videoDirector = videoDirector == null ? null : videoDirector.trim();
    }

    public String getVideoLanguage() {
        return videoLanguage;
    }

    public void setVideoLanguage(String videoLanguage) {
        this.videoLanguage = videoLanguage == null ? null : videoLanguage.trim();
    }

    public String getVideoStatus() {
        return videoStatus;
    }

    public void setVideoStatus(String videoStatus) {
        this.videoStatus = videoStatus == null ? null : videoStatus.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}