package com.zqy.sharecommunity.entity;

import java.util.Date;

/**
 * @Author zqy
 * @Date 2020/03/30
 */
public class VideoDTO {
    private Integer id;

    private String videoNewName;

    private String areaName;

    private String typeName;

    private int status;

    private Date updateTime;

    private Date createTime;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVideoNewName() {
        return videoNewName;
    }

    public void setVideoNewName(String videoNewName) {
        this.videoNewName = videoNewName;
    }


    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "VideoDTO{" +
                "id=" + id +
                ", videoNewName='" + videoNewName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", typeName='" + typeName + '\'' +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                '}';
    }
}
