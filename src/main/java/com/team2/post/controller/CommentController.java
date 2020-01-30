package com.team2.post.controller;

import com.team2.post.collection.Comment;
import com.team2.post.dto.CommentDataDto;
import com.team2.post.dto.CommentDto;
import com.team2.post.dto.response.BaseResponse;
import com.team2.post.dto.UserDetailDto;
import com.team2.post.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/addComment")
    public BaseResponse<String> addComment(@RequestBody CommentDto commentDto){
        Date d = new Date();
        commentDto.setTimeStamp(d);
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDto,comment);
        String response = commentService.saveComment(comment);
        return new BaseResponse<String>("null", true, response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/viewCommentByPost/{postId}")
    public List<CommentDataDto> getCommentByPostId(@PathVariable("postId") long postId) {
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
    public List<CommentDto> getCommentByUserId(@PathVariable("userId") long userId) {
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
