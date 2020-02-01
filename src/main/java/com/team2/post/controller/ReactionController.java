package com.team2.post.controller;

import com.team2.post.collection.Post;
import com.team2.post.collection.Reaction;
import com.team2.post.dto.*;
import com.team2.post.response.BaseResponse;
import com.team2.post.service.PostService;
import com.team2.post.service.ReactionService;
import com.team2.post.service.impl.Producer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/reaction")
public class ReactionController {

    @Autowired
    ReactionService reactionService;

    @Autowired
    PostService postService;

    @PostMapping("/addActivity")
    public BaseResponse<String> addActivity(@RequestBody ReactionDto reactionDto)
    {
            Reaction reaction = new Reaction();
            reactionDto.setTimeStamp(reactionService.getTimeStamp());
            BeanUtils.copyProperties(reactionDto, reaction);
            reactionService.addPostActivity(reaction);
            sendPostActivities(reactionDto);
            reactionNotifications(reactionDto);


            return new BaseResponse<>("null", true, "Reaction Added", HttpStatus.CREATED);
    }
    @PostMapping("/showReactions/{postId}")
    public BaseResponse<List<ReactionRequestDto>> showReactionsByUserId(@PathVariable String postId)
    {
        try {
            List<ReactionRequestDto> reactionRequestDtos = new ArrayList<ReactionRequestDto>();
            List<Reaction> reactions = reactionService.readReactionByPostId(postId);
            for (Reaction reaction : reactions) {
                ReactionRequestDto reactionRequestDto = new ReactionRequestDto();
                BaseResponse<UserDetailDto> user = reactionService.getUserDetails(reaction.getUserId());
                UserDetailDto userDetailDto = user.getData();
                reactionRequestDto.setUserName(userDetailDto.getUserName());
                reactionRequestDto.setImageUrl(userDetailDto.getImageUrl());
                reactionRequestDto.setActivity(reaction.getActivity());
                reactionRequestDto.setTimeStamp(reaction.getTimeStamp());
                reactionRequestDtos.add(reactionRequestDto);
            }
            return new BaseResponse<>("null", true, reactionRequestDtos, HttpStatus.CREATED);
        }catch(Exception ex)
        {
            System.out.println(ex);
        }
        return null;
    }

    @PostMapping("/getActivityByUsers/{userId}")
    public List<ReactionDto> getReactionByUserId(String userId)
    {
        List<Reaction> reactions = reactionService.getReactionByUserId(userId);
        List<ReactionDto> reactionDtos = new ArrayList<>();
        for(Reaction react : reactions) {
            ReactionDto reactionDto = new ReactionDto();
            BeanUtils.copyProperties(react,reactionDto);
            reactionDtos.add(reactionDto);
        }
        return reactionDtos;
    }

    public void reactionNotifications(ReactionDto reactionDto)
    {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setActivity(reactionDto.getActivity());
        notificationDto.setPostId(reactionDto.getPostId());
        notificationDto.setActivityUserId(reactionDto.getUserId());
        Post post = postService.getPostByPostId(reactionDto.getPostId());
        notificationDto.setUserIdOfPost(post.getUserId());
        BaseResponse<UserDetailDto> user = reactionService.getUserDetails(reactionDto.getUserId());
        UserDetailDto userDetailDto = user.getData();
        BaseResponse<UserDetailDto> activityUser = reactionService.getUserDetails(notificationDto.getActivityUserId());
        UserDetailDto userDetailDto1 = activityUser.getData();
        HashSet<String> userFriend = userDetailDto.getFriendIds();
        HashSet<String> activityUserFriend = userDetailDto1.getFriendIds();
        userFriend.addAll(activityUserFriend);
        notificationDto.setListOfFriends(userFriend);
        Producer producer = new Producer();
        producer.kafkaProducer(notificationDto);
    }

    public void sendPostActivities(ReactionDto reactionDto)
    {
        PostActivityDTO postActivityDTO = new PostActivityDTO();
        Post post = postService.getPostByPostId(reactionDto.getPostId());
        PostDTO postDTO = new PostDTO();
        BeanUtils.copyProperties(post, postDTO);
        postActivityDTO.setPostDTO(postDTO);
        postActivityDTO.setUserFriendId(reactionDto.getUserId());
        postActivityDTO.setMessage(reactionDto.getActivity());
        reactionService.sendUserActivity(postActivityDTO);
    }


}
