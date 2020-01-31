package com.team2.post.controller;
import com.team2.post.collection.Post;
import com.team2.post.collection.Reaction;
import com.team2.post.dto.PostDTO;
import com.team2.post.service.CommentService;
import com.team2.post.service.PostService;
import com.team2.post.service.ReactionService;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    ReactionService reactionService;

    @Autowired
    CommentService commentService;

    @PostMapping("/addPost")
    public String addPost(@RequestBody PostDTO postDTO){

        Post post = new Post();
        Date now = new Date();
        Date timeStamp = new Date(now.getTime());
        postDTO.setTimestamp(timeStamp);
        BeanUtils.copyProperties(postDTO, post);
        postService.addPost(post);
        return postDTO.getPostId();
    }

    @GetMapping("/getByUserId/{userId}")
    public List<Post> postByUserId(@PathVariable("userId") String userId){
        return postService.showPostByUserId(userId);
    }

    @GetMapping("/getUserIdByPostId/{postId}")
    public String userIdByPostId(@PathVariable("postId")String postId){
        return postService.getUserIdByPostId(postId);
    }

    @GetMapping("/getSortedPosts")
    public List<Post> sortedPosts(){
        return  postService.sortedList();
    }

    @GetMapping("/user/timeline/{userId}")
    public List<Post> getUsersAllPost(@PathVariable("userId") String userId)
    {
        Set<PostDTO> postDTOS = new HashSet<>();
        List<Post> posts = postService.showPostByUserId(userId);
        //List<Comment> comments = commentService.findByUserId(userId);
        List<Reaction> reactions = reactionService.getReactionByUserId(userId);
        /*for(Comment comment : comments)
        {
            posts.add(postService.getPostByPostId(comment.getPostId()));
        }*/
        for(Reaction reaction : reactions)
        {
            posts.add(postService.getPostByPostId(reaction.getPostId()));
        }
        List<Post> postWithoutDuplicates = Lists.newArrayList(Sets.newHashSet(posts));
        postWithoutDuplicates.sort(Comparator.comparing(Post::getTimestamp).reversed());
        return postWithoutDuplicates;
    }

    @PostMapping("/getPostsByUserIds")
    public List<PostDTO> getPostsByUserIds(@RequestBody List<String> userId)
    {
        List<Post> posts = postService.findPostByUserId(userId);
        List<PostDTO> postDTOS = new ArrayList<>();
        for(Post post:posts)
        {
            PostDTO postDTO = new PostDTO();
            BeanUtils.copyProperties(post,postDTO);
            postDTOS.add(postDTO);
        }
        return postDTOS;
    }


}