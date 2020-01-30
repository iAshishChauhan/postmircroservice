package com.team2.post.service.impl;

import com.team2.post.collection.Post;
import com.team2.post.dto.PostDTO;
import com.team2.post.repository.PostRepository;
import com.team2.post.service.PostService;
import javafx.geometry.Pos;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;

    @Override
    public Post addPost(PostDTO postDTO) {
        Post post = new Post();
        BeanUtils.copyProperties(postDTO, post);
        return postRepository.insert(post);
    }

    @Override
    public List<Post> showPostByUserId(Long userId) {
        List<Post> postListByUserId = postRepository.findByUserId(userId);
        return postListByUserId;
    }

    @Override
    public Long getUserIdByPostId(Long postId) {
        Post post = postRepository.findByPostId(postId);
        return post.getUserId();
    }

    @Override
    public List<Post> sortedList() {
        List<Post> PostList = postRepository.findAll();
        PostList.sort(Comparator.comparing(Post::getTimestamp).reversed());
        return PostList;
    }
}
