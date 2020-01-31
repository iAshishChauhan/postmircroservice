package com.team2.post.controller;

import com.team2.post.collection.Comment;
import com.team2.post.collection.Post;
import com.team2.post.dto.*;
import com.team2.post.response.BaseResponse;
import com.team2.post.service.CommentService;
import com.team2.post.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    PostService postService;

    @PostMapping("/addComment")
    public BaseResponse<String> addComment(@RequestBody CommentDto commentDto){
        Date now = new Date();
        Date timeStamp = new Date(now.getTime());
        commentDto.setTimeStamp(timeStamp);
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDto,comment);
        String response = commentService.saveComment(comment);
        PostActivityDTO postActivityDTO = new PostActivityDTO();
        Post post = postService.getPostByPostId(comment.getPostId());
        PostDTO postDTO = new PostDTO();
        BeanUtils.copyProperties(post,postDTO);
        postActivityDTO.setPostDTO(postDTO);
        postActivityDTO.setUserFriendId(comment.getUserId());
        postActivityDTO.setMessage("Commented");

        return new BaseResponse<String>("null", true, response, HttpStatus.CREATED);
    }

    /*@GetMapping("/viewReplyComment/{parentCommentId}")
    public List<CommentDataDto> getReplyComment(@PathVariable(value = "parentCommentId") String parentCommentId) {

        List<CommentDataDto> commentDataDtos = new ArrayList<CommentDataDto>();
        List<Comment> commentList = commentService.findByParentCommentId(parentCommentId);

        for(Comment comment: commentList) {
            CommentDataDto commentDataDto = new CommentDataDto();
            BaseResponse<UserDetailDto> user = commentService.getUserDetail(comment.getUserId());
            UserDetailDto userDetailDto = new UserDetailDto();
            userDetailDto = user.getData();

            commentDataDto.setImageUrl(userDetailDto.getImageUrl());
            commentDataDto.setUserName(userDetailDto.getUserName());
            commentDataDto.setTimeStamp(comment.getTimeStamp());
            commentDataDto.setCommentId(comment.getCommentId());
            commentDataDto.setParentCommentId(comment.getParentCommentId());
            commentDataDto.setText(comment.getText());
            commentDataDto.setPostId(comment.getPostId());

            commentDataDtos.add(commentDataDto);
        }
        return commentDataDtos;
    }*/

    @GetMapping(value = "/viewCommentByPost/{postId}")
    public List<CommentDataDto> getCommentByPostId(@PathVariable("postId") String postId) {
        List<CommentDataDto> commentDataDtos = new ArrayList<CommentDataDto>();
        List<Comment> commentList = commentService.findByPostId(postId);

        for(Comment comment: commentList) {
            CommentDataDto commentDataDto = new CommentDataDto();
            BaseResponse<UserDetailDto> user = commentService.getUserDetail(comment.getUserId());
            UserDetailDto userDetailDto = new UserDetailDto();
            userDetailDto = user.getData();

            commentDataDto.setImageUrl(userDetailDto.getImageUrl());
            commentDataDto.setUserName(userDetailDto.getUserName());
            commentDataDto.setTimeStamp(comment.getTimeStamp());
            commentDataDto.setCommentId(comment.getCommentId());
            commentDataDto.setParentCommentId(comment.getParentCommentId());
            commentDataDto.setText(comment.getText());
            commentDataDto.setPostId(comment.getPostId());

            commentDataDtos.add(commentDataDto);
        }
        return commentDataDtos;
    }

    @GetMapping(value = "/viewCommentByUser/{userId}")
    public List<CommentDto> getCommentByUserId(@PathVariable("userId") String userId) {
        List<Comment> commentList = commentService.findByUserId(userId);
        List<CommentDto> commentDtos = new ArrayList<>();
        for(Comment comment : commentList) {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment , commentDto);
            commentDtos.add(commentDto);
        }
        return commentDtos;
    }
}
