package com.team2.post.controller;

import com.team2.post.collection.Comment;
import com.team2.post.dto.CommentDataDto;
import com.team2.post.dto.CommentDto;
import com.team2.post.dto.Response.BaseResponse;
import com.team2.post.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<Comment> getCommentByPostId(@PathVariable("postId") long postId) {
        return commentService.findByPostId(postId);
    }


}
