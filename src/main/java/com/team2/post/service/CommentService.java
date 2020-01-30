package com.team2.post.service;

import com.team2.post.collection.Comment;
import com.team2.post.dto.response.BaseResponse;
import com.team2.post.dto.UserDetailDto;

import java.util.List;

public interface CommentService {
    String saveComment(Comment comment);
    List<Comment> findByPostId(long postId);
    List<Comment> findByUserId(long userId);
    BaseResponse<UserDetailDto> getUserDetail(long userId);
}
