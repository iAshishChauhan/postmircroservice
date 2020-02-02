package com.team2.post.service;

import com.team2.post.collection.Comment;
import com.team2.post.dto.PostActivityDTO;
import com.team2.post.response.BaseResponse;
import com.team2.post.dto.UserDetailDto;

import java.util.Date;
import java.util.List;

public interface CommentService {
    String saveComment(Comment comment);
    List<Comment> findByPostId(String postId);
    List<Comment> findByUserId(String userId);
    List<Comment> findByAdminId(String adminId);
    List<Comment> findByParentCommentId(String parentCommentId);
    BaseResponse<UserDetailDto> getUserDetail(String userId);
    void sendUserActivity(PostActivityDTO postActivityDTO);
    Date getTimeStamp();

}
