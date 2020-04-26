package com.zqy.sharecommunity.service;

import com.zqy.sharecommunity.dao.MusicMapper;
import com.zqy.sharecommunity.entity.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author zqy
 * @Date 2019/12/26
 */
@Service
public class MusicService {

    @Autowired
    private MusicMapper musicMapper;

    @Transactional
    public void save(Music music){
        musicMapper.insert(music);
    }

    public List<Music> findMusic(int offset,int limit){
        return musicMapper.selectMusic(offset,limit);
    }

    public int findMusicCount(){
        return musicMapper.selectMusicCount();
    }

    public Music findMusicById(int id){
        return musicMapper.selectMusicById(id);
    }

    public List<Music> findMusicBySelective(String title){
        return musicMapper.selectMusicBySelective(title);
    }

    public int updateMusicStatus(int status,int id){
        return musicMapper.updateMusicStatus(status,id);
    }
}
