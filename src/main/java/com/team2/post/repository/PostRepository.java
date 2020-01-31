package com.team2.post.repository;

import com.team2.post.collection.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PostRepository extends MongoRepository<Post,String> {

    List<Post> findByUserId(String userId);
    Post findByPostId(String postId);
    List<Post> findAllByUserIdIn(List<String> userId);
}
