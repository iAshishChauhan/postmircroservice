package com.team2.post.service;

import com.team2.post.collection.Comment;

import java.util.List;

public interface CommentService {
    String saveComment(Comment comment);
    List<Comment> findByPostId(long postId);
}
