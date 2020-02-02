package com.team2.post.repository;

import com.team2.post.collection.BusinessPost;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BusinessPostRepository extends MongoRepository<BusinessPost,String> {
    List<BusinessPost> findByAdminId(String adminId);
    BusinessPost findByPostId(String postId);
    List<BusinessPost> findAllByAdminIdIn(List<String> adminId);
}
