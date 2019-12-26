package com.zqy.sharecommunity.service;

import com.zqy.sharecommunity.dao.MusicMapper;
import com.zqy.sharecommunity.entity.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author zqy
 * @Date 2019/12/26
 */
@Service
public class MusicService {

    @Autowired
    private MusicMapper mapper;

    @Transactional
    public void save(Music music){
        mapper.insert(music);
    }
}
