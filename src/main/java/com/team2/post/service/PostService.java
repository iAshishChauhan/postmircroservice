package com.team2.post.service;

import com.team2.post.collection.Post;
import com.team2.post.dto.PostDTO;

import java.util.List;

public interface PostService {
    Post addPost(PostDTO postDTO);
    List<Post> showPostByUserId(Long userId);
    Long getUserIdByPostId(Long postId);
    List<Post> sortedList();

}
