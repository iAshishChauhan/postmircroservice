package com.team2.post.controller;

import com.team2.post.collection.Post;
import com.team2.post.collection.Reaction;
import com.team2.post.dto.*;
import com.team2.post.response.BaseResponse;
import com.team2.post.service.PostService;
import com.team2.post.service.ReactionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
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
        try {
            Reaction reaction = new Reaction();
            PostActivityDTO postActivityDTO = new PostActivityDTO();
            Date now = new Date();
            Date timeStamp = new Date(now.getTime());
            reactionDto.setTimeStamp(timeStamp);
            BeanUtils.copyProperties(reactionDto, reaction);
            reactionService.addPostActivity(reaction);
            Post post = postService.getPostByPostId(reaction.getPostId());
            PostDTO postDTO = new PostDTO();
            BeanUtils.copyProperties(post, postDTO);
            postActivityDTO.setPostDTO(postDTO);
            postActivityDTO.setUserFriendId(reaction.getUserId());
            postActivityDTO.setMessage(reaction.getActivity());

            reactionService.sendUserActivity(postActivityDTO);

            return new BaseResponse<>("null", true, "Reaction Added", HttpStatus.CREATED);
        }catch(Exception ex)
        {
            System.out.println(ex);
        }
        return null;
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




}
