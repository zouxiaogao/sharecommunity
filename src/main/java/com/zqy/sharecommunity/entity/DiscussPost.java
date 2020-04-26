package com.zqy.sharecommunity.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;


//帖子实体类
//创建索引，type默认_doc(7.0已废弃),分片6,副本3
@Document(indexName = "discusspost",type = "_doc",shards = 6,replicas =3 )
public class DiscussPost {
    @Id
    private int id;

    @Field(type = FieldType.Integer)
    private int userId;

    /**
     * 互联网校招（关键词 分词，拆词--->:互联网，校招） analyzer ：分词器 --->ik_max_word 最广分词 （存储用）  ik_mart:最优（搜索用）
     * **/

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_mart")
    private String title;   //标题

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_mart")
    private String content;    //内容

    @Field(type = FieldType.Integer)
    private int type;  // 0-普通  1-置顶

    @Field(type = FieldType.Integer)
    private int status;   //0-正常  1-精华  2-拉黑

    @Field(type = FieldType.Date)
    private Date createTime;

    @Field(type = FieldType.Integer)
    private int commentCount;   //评论数

    @Field(type = FieldType.Double)
    private Double score;    //排名用的


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String toString() {
        return "DiscussPost{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", createTime=" + createTime +
                ", commentCount=" + commentCount +
                ", score=" + score +
                ", content='" + content + '\'' +
                '}';
    }
}