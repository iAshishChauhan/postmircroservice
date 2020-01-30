package com.team2.post.repository;

import com.team2.post.collection.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByPostId(long postId);
    List<Comment> findByParentCommentId(long parentCommentId);
    List<Comment> findByUserId(long userId);
}
