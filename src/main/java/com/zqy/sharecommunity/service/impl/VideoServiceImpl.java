package com.zqy.sharecommunity.service.impl;

import com.zqy.sharecommunity.dao.VideoAttrMapper;
import com.zqy.sharecommunity.dao.VideoDownloadUrlMapper;
import com.zqy.sharecommunity.dao.VideoMapper;
import com.zqy.sharecommunity.entity.Video;
import com.zqy.sharecommunity.entity.VideoAttr;
import com.zqy.sharecommunity.entity.VideoDownloadUrl;
import com.zqy.sharecommunity.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author zqy
 * @Date 2019/12/24
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private VideoAttrMapper videoAttrMapper;
    @Autowired
    private VideoDownloadUrlMapper videoDownloadUrl;

    @Override
    @Transactional
    public void saveVideo(Video video) {
        videoMapper.insert(video);
    }

    @Override
    @Transactional
    public void saveVideoAttr(VideoAttr videoAttr) {
        videoAttrMapper.insert(videoAttr);
    }

    @Override
    @Transactional
    public void saveDownloadUrl(List<VideoDownloadUrl> downloadUrl) {
        videoDownloadUrl.insert(downloadUrl);
    }

    @Override
    public List<Video> findAll(Video video) {
        return null;
    }
}
