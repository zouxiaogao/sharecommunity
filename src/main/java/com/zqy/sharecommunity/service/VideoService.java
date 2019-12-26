package com.zqy.sharecommunity.service;

import com.zqy.sharecommunity.entity.Video;
import com.zqy.sharecommunity.entity.VideoAttr;
import com.zqy.sharecommunity.entity.VideoDownloadUrl;

import java.util.List;

/**
 * @Author zqy
 * @Date 2019/12/24
 */
public interface VideoService {

    /**
     * 保存电影
     * @param video
     */
    public void saveVideo(Video video);

    /**
     * 保存电影详情
     * @param videoAttr
     */
    public void saveVideoAttr(VideoAttr videoAttr);

    /**
     * 保存电影下载链接
     * @param downloadUrl
     */
    public void saveDownloadUrl(List<VideoDownloadUrl> downloadUrl);

    /**
     * 根据条件查询电影
     * @param video
     * @return
     */
    public List<Video> findAll(Video video);
}
