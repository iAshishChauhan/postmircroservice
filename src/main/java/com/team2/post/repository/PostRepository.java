package com.team2.post.repository;

import com.team2.post.collection.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post,Long> {
    List<Post> findByUserId(Long userId);
    Post findByPostId(Long postId);
}
