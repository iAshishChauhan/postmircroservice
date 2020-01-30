package com.team2.post.service.impl;

import com.team2.post.collection.Comment;
import com.team2.post.controller.feignInterfaces.UserProxy;
import com.team2.post.response.BaseResponse;
import com.team2.post.dto.UserDetailDto;
import com.team2.post.repository.CommentRepository;
import com.team2.post.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserProxy userProxy;

    @Override
    public String saveComment(Comment comment) {
        commentRepository.insert(comment);
        return "OK";
    }

    @Override
    public List<Comment> findByPostId(long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public List<Comment> findByUserId(long userId) {
        return commentRepository.findByUserId(userId);
    }

    public BaseResponse<UserDetailDto> getUserDetail(long userId) {
        return userProxy.getUserDetailsById(userId);
    }

}
