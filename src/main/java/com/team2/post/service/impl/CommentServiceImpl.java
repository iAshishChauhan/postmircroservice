package com.team2.post.service.impl;

import com.team2.post.collection.Comment;
import com.team2.post.controller.feignInterfaces.FeedProxy;
import com.team2.post.controller.feignInterfaces.UserProxy;
import com.team2.post.dto.CommentDataDto;
import com.team2.post.dto.PostActivityDTO;
import com.team2.post.response.BaseResponse;
import com.team2.post.dto.UserDetailDto;
import com.team2.post.repository.CommentRepository;
import com.team2.post.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    FeedProxy feedProxy;

    @Autowired
    UserProxy userProxy;

    public BaseResponse<UserDetailDto> getUserDetail(String userId) {
        return userProxy.getUserDetailsById(userId);
    }

    @Override
    public String saveComment(Comment comment) {
        commentRepository.insert(comment);
        return "OK";
    }

    @Override
    public List<Comment> findByPostId(String postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public List<Comment> findByUserId(String userId) {
        return commentRepository.findByUserId(userId);
    }

//    @Override
//    public List<Comment> findByAdminId(String adminId) {
//        return commentRepository.findByAdminId(adminId);
//    }

    @Override
    public List<Comment> findByParentCommentId(String parentCommentId) {
        return commentRepository.findByParentCommentId(parentCommentId);
    }

    @Override
    public void sendUserActivity(PostActivityDTO postActivityDTO) {
        feedProxy.addPostAfterActivity(postActivityDTO);
    }

    @Override
    public Date getTimeStamp() {
        Date now = new Date();
        Date timeStamp = new Date(now.getTime());
        return timeStamp;
    }
}
