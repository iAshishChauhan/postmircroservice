package com.team2.post.controller;

import com.team2.post.collection.Post;
import com.team2.post.dto.PostDTO;
import com.team2.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping("/addPost")
    public Long addPost(@RequestBody PostDTO postDTO){
        postService.addPost(postDTO);
        return postDTO.getPostId();
    }

    @GetMapping("/getByUserId/{userId}")
    public List<Post> postByUserId(@PathVariable("userId") Long userId){
        return postService.showPostByUserId(userId);
    }

    @GetMapping("/getUserIdByPostId/{postId}")
    public Long userIdByPostId(@PathVariable("postId")Long postId){
        return postService.getUserIdByPostId(postId);
    }

    @GetMapping("/getSortedPosts")
    public List<Post> sortedPosts(){
        return  postService.sortedList();
    }
}
