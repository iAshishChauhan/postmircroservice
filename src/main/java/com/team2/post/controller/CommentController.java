package com.team2.post.controller;

import com.team2.post.collection.Comment;
import com.team2.post.collection.Post;
import com.team2.post.dto.*;
import com.team2.post.response.BaseResponse;
import com.team2.post.service.CommentService;
import com.team2.post.service.PostService;
import com.team2.post.service.impl.Producer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    PostService postService;


    @PostMapping("/addComment")
    public BaseResponse<String> addComment(@RequestBody CommentDto commentDto) {

        try {
            commentDto.setTimeStamp(commentService.getTimeStamp());
            Comment comment = new Comment();
            BeanUtils.copyProperties(commentDto, comment);
            String response = commentService.saveComment(comment);
            //commentNotifications(commentDto);
            sendPostActivities(commentDto);

            return new BaseResponse<>("null", true, response, HttpStatus.CREATED);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    @GetMapping(value = "/viewCommentByPost/{postId}")
    public BaseResponse<List<CommentDataDto>> getCommentByPostId(@PathVariable("postId") String postId) {

        try {
            List<CommentDataDto> commentDataDtos = new ArrayList<CommentDataDto>();
            List<Comment> commentList = commentService.findByPostId(postId);
            List<Comment> sortedComment = commentList.stream()
                    .sorted(Comparator.comparing(Comment::getTimeStamp))
                    .collect(Collectors.toList());

            for (Comment comment : sortedComment) {
                CommentDataDto commentDataDto = new CommentDataDto();
                BaseResponse<UserDetailDto> user = commentService.getUserDetail(comment.getUserId());
                UserDetailDto userDetailDto = user.getData();

                commentDataDto.setPostId(comment.getPostId());
                commentDataDto.setImageUrl(userDetailDto.getImageUrl());
                commentDataDto.setUserName(userDetailDto.getUserName());
                commentDataDto.setTimeStamp(comment.getTimeStamp());
                commentDataDto.setCommentId(comment.getCommentId());
                commentDataDto.setText(comment.getText());

                if (comment.getParentCommentId().equals("null")) {
                    commentDataDto.setParentCommentId(comment.getParentCommentId());
                    commentDataDtos.add(commentDataDto);
                } else {
                    for (CommentDataDto existingComment : commentDataDtos) {
                        if (existingComment.getCommentId().equals(comment.getParentCommentId())) {
                            List<CommentDataDto> appendChildComment = new ArrayList<>();
                            if (existingComment.getChildComment() == null) {
                                appendChildComment.add(commentDataDto);
                                existingComment.setChildComment(appendChildComment);
                            } else {
                                appendChildComment = existingComment.getChildComment();
                                appendChildComment.add(commentDataDto);
                                existingComment.setChildComment(appendChildComment);
                            }
                        }
                    }
                }
            }
            return new BaseResponse<>("null", true, commentDataDtos, HttpStatus.CREATED);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    @GetMapping(value = "/viewCommentByUser/{userId}")
    public List<CommentDto> getCommentByUserId(@PathVariable("userId") String userId) {
        try {
            List<Comment> commentList = commentService.findByUserId(userId);
            List<CommentDto> commentDtos = new ArrayList<>();
            for (Comment comment : commentList) {
                CommentDto commentDto = new CommentDto();
                BeanUtils.copyProperties(comment, commentDto);
                commentDtos.add(commentDto);
            }
            return commentDtos;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    @GetMapping(value = "/viewCommentByParentId/{parentCommentId}")
    public BaseResponse<List<CommentDataDto>> getCommentByParentId(@PathVariable("parentCommentId") String parentCommentId) {

        List<CommentDataDto> commentDataDtos = new ArrayList<CommentDataDto>();
        List<Comment> commentList = commentService.findByPostId(parentCommentId);
        List<Comment> sortedComment = commentList.stream()
                .sorted(Comparator.comparing(Comment::getTimeStamp))
                .collect(Collectors.toList());

        for (Comment comment : sortedComment) {
            CommentDataDto commentDataDto = new CommentDataDto();
            BaseResponse<UserDetailDto> user = commentService.getUserDetail(comment.getUserId());
            UserDetailDto userDetailDto = user.getData();

            commentDataDto.setPostId(comment.getPostId());
            commentDataDto.setImageUrl(userDetailDto.getImageUrl());
            commentDataDto.setUserName(userDetailDto.getUserName());
            commentDataDto.setTimeStamp(comment.getTimeStamp());
            commentDataDto.setCommentId(comment.getCommentId());
            commentDataDto.setText(comment.getText());
            commentDataDto.setParentCommentId(comment.getParentCommentId());
            commentDataDto.setChildComment(null);

            commentDataDtos.add(commentDataDto);
        }

        return new BaseResponse<>("null", true, commentDataDtos, HttpStatus.CREATED);

    }
    public void commentNotifications(CommentDto commentDto) {
        try {
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setActivity("Commented");
            notificationDto.setPostId(commentDto.getPostId());
            notificationDto.setActivityUserId(commentDto.getUserId());
            Post post = postService.getPostByPostId(commentDto.getPostId());
            notificationDto.setUserIdOfPost(post.getUserId());
            BaseResponse<UserDetailDto> user = commentService.getUserDetail(commentDto.getUserId());
            UserDetailDto userDetailDto = user.getData();
            BaseResponse<UserDetailDto> activityUser = commentService.getUserDetail(notificationDto.getActivityUserId());
            UserDetailDto userDetailDto1 = activityUser.getData();
            HashSet<String> userFriend = userDetailDto.getFriendIds();
            HashSet<String> activityUserFriend = userDetailDto1.getFriendIds();
            userFriend.addAll(activityUserFriend);
            notificationDto.setListOfFriends(userFriend);
            Producer producer = new Producer();
            producer.kafkaProducer(notificationDto);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void sendPostActivities(CommentDto commentDto) {
        try {
            PostActivityDTO postActivityDTO = new PostActivityDTO();
            Post post = postService.getPostByPostId(commentDto.getPostId());
            PostDTO postDTO = new PostDTO();
            BeanUtils.copyProperties(post, postDTO);
            postActivityDTO.setPostDTO(postDTO);
            postActivityDTO.setUserFriendId(commentDto.getUserId());
            postActivityDTO.setMessage("Commented");

            commentService.sendUserActivity(postActivityDTO);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
