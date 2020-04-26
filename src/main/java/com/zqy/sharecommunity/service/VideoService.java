package com.zqy.sharecommunity.service;

import com.zqy.sharecommunity.dao.VideoAttrMapper;
import com.zqy.sharecommunity.dao.VideoDownloadUrlMapper;
import com.zqy.sharecommunity.dao.VideoMapper;
import com.zqy.sharecommunity.entity.DownloadUrl;
import com.zqy.sharecommunity.entity.Video;
import com.zqy.sharecommunity.entity.VideoAttr;
import com.zqy.sharecommunity.entity.VideoDTO;
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

    public List<VideoDTO> findVideos(int offset,int limit) {
        return videoMapper.selectVideos(offset,limit);
    }

    public int findVideoCount(){
        return videoMapper.selectVideoCount();
    }

    public VideoAttr findVideoAttr(int videoId){
        return videoAttrMapper.selectVideoAttr(videoId);
    }


    public VideoDTO findVideoById(int videoId){
        return videoMapper.selectVideoById(videoId);
    }
    //获取下载链接
    public List<DownloadUrl> findDownloadUrl(int videoId){
        return videoDownloadUrl.selectDownloadUrlByVideoId(videoId);
    }


    public List<VideoDTO> findVideosSelective(String title){
        return videoMapper.selectVideosBySelective(title);
    }

    public int updateStatus(int status,int id){
        return videoMapper.updateVideoStatus(status,id);
    }

}
