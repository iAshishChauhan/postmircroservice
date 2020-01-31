package com.team2.post.controller;

import com.team2.post.collection.Reaction;
import com.team2.post.dto.ReactionRequestDto;
import com.team2.post.dto.ReactionDto;
import com.team2.post.dto.UserDetailDto;
import com.team2.post.response.BaseResponse;
import com.team2.post.service.ReactionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reaction")
public class ReactionController {

    @Autowired
    ReactionService reactionService;

    @PostMapping("/addActivity")
    public String addActivity(@RequestBody ReactionDto reactionDto)
    {

        Reaction reaction = new Reaction();
        BeanUtils.copyProperties(reactionDto,reaction);
        reactionService.addPostActivity(reaction);
        return "added";
    }
    @PostMapping("/showReactions/{postId}")
    public List<ReactionRequestDto> showReactionsByUserId(@PathVariable Long postId)
    {
        List<ReactionRequestDto> reactionRequestDtos = new ArrayList<ReactionRequestDto>();
        List<Reaction> reactions = reactionService.readReactionByPostId(postId);
        for(Reaction reaction : reactions)
        {
            ReactionRequestDto reactionRequestDto = new ReactionRequestDto();
            BaseResponse<UserDetailDto> user = reactionService.getUserDetails(reaction.getUserId());
            UserDetailDto userDetailDto = user.getData();
            reactionRequestDto.setUserName(userDetailDto.getUserName());
            reactionRequestDto.setImageUrl(userDetailDto.getImageUrl());
            reactionRequestDto.setActivity(reaction.getActivity());
            reactionRequestDto.setTimeStamp(reaction.getTimeStamp());
            reactionRequestDtos.add(reactionRequestDto);
        }
        return reactionRequestDtos;
    }

    @PostMapping("/getActivityByUsers/{userId}")
    public List<ReactionDto> getReactionByUserId(long userId)
    {
        List<Reaction> reactions = reactionService.getReactionByUserId(userId);
        List<ReactionDto> reactionDtos = new ArrayList<>();
        for(Reaction react : reactions) {
            ReactionDto reactionDto = new ReactionDto();
            BeanUtils.copyProperties(react , reactionDto);
            reactionDtos.add(reactionDto);
        }
        return reactionDtos;
    }


}
