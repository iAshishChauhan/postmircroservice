package com.team2.post.repository;

import com.team2.post.collection.Reaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends MongoRepository<Reaction,Long>
{
    List<Reaction> findAllByPostId(Long postId);

    List<Reaction> findAllByUserId(Long userId);
}
