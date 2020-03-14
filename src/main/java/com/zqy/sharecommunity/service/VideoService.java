package com.zqy.sharecommunity.service;

import com.zqy.sharecommunity.dao.VideoAttrMapper;
import com.zqy.sharecommunity.dao.VideoDownloadUrlMapper;
import com.zqy.sharecommunity.dao.VideoMapper;
import com.zqy.sharecommunity.entity.DownloadUrl;
import com.zqy.sharecommunity.entity.Video;
import com.zqy.sharecommunity.entity.VideoAttr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author zqy
 * @Date 2019/12/24
 */
@Service
public class VideoService {

    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private VideoAttrMapper videoAttrMapper;
    @Autowired
    private VideoDownloadUrlMapper videoDownloadUrl;

    @Transactional
    public void saveVideo(Video video) {
        videoMapper.insert(video);
    }

    @Transactional
    public void saveVideoAttr(VideoAttr videoAttr) {
        videoAttrMapper.insert(videoAttr);
    }

    @Transactional
    public void saveDownloadUrl(List<DownloadUrl> downloadUrl) {
        videoDownloadUrl.insert(downloadUrl);
    }

    public List<Video> findAll(Video video) {
        return null;
    }
}
