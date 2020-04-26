package com.zqy.sharecommunity.controller;

import com.zqy.sharecommunity.entity.Comment;
import com.zqy.sharecommunity.entity.DiscussPost;
import com.zqy.sharecommunity.entity.Event;
import com.zqy.sharecommunity.event.EventProducer;
import com.zqy.sharecommunity.service.CommentService;
import com.zqy.sharecommunity.service.DiscussPostService;
import com.zqy.sharecommunity.util.CommunityConstant;
import com.zqy.sharecommunity.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * @Author zqy
 * @Date 2020/03/17
 */
@Controller
@RequestMapping("/comment")
public class CommentCtrl implements CommunityConstant {

    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private DiscussPostService discussPostService;


    @PostMapping("/add/{discussPostId}")
    public String addComment(@PathVariable("discussPostId") int discussPostId, Comment comment){
        comment.setUserId(hostHolder.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentService.addComment(comment);


        //触发评论事件
        Event event = new Event()
                .setTopic(TOPIC_COMMENT)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(comment.getEntityType())
                .setEntityId(comment.getEntityId())
                .setData("postId",discussPostId);

        if(comment.getEntityType() == ENTITY_TYPE_POST){  //帖子
            DiscussPost target = discussPostService.findDiscussPostById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }else if (comment.getEntityType() == ENTITY_TYPE_COMMENT){  //评论
            Comment target = commentService.findCommentById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }

        //调动事件
        eventProducer.fireEvent(event);


        if(comment.getEntityType() == ENTITY_TYPE_POST){
            //触发发帖事件
            event=new Event()
                    .setTopic(TOPIC_PUBLISH)
                    .setUserId(comment.getId())
                    .setEntityType(ENTITY_TYPE_POST)
                    .setEntityId(discussPostId);

            eventProducer.fireEvent(event);
        }

        return "redirect:/discuss/detail/"+discussPostId;

    }

}
