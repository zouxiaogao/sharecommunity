package com.zqy.sharecommunity.elasticsearch;

import com.zqy.sharecommunity.entity.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author zqy
 * @Date 2020/03/24
 */

/***
 *
 * 继承elasticsearch方法  DiscussPost：查询的对象   Integer：主键类型
 * **/

@Repository
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost,Integer> {

}
