package com.team2.post.service.impl;

import com.team2.post.collection.Comment;
import com.team2.post.repository.CommentRepository;
import com.team2.post.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public String saveComment(Comment comment) {
        commentRepository.insert(comment);
        return "OK";
    }

    @Override
    public List<Comment> findByPostId(long postId) {
        return commentRepository.findByPostId(postId);
    }
}
