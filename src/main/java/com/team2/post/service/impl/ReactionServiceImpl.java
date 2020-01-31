package com.team2.post.service.impl;

import com.team2.post.collection.Reaction;
import com.team2.post.controller.feignInterfaces.UserProxy;
import com.team2.post.dto.UserDetailDto;
import com.team2.post.repository.ReactionRepository;
import com.team2.post.response.BaseResponse;
import com.team2.post.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReactionServiceImpl implements ReactionService {


    @Autowired
    ReactionRepository reactionRepository;

    @Autowired
    UserProxy userProxy;

    @Override
    public void addPostActivity(Reaction reaction)
    {
      reactionRepository.insert(reaction);
    }

    @Override
    public List<Reaction> readReactionByPostId(Long postId)
    {
        return reactionRepository.findAllByPostId(postId);
    }

    @Override
    public List<Reaction> getReactionByUserId(Long userId)
    {
        return reactionRepository.findAllByUserId(userId);
    }

    public BaseResponse<UserDetailDto> getUserDetails(String userId)
    {
      return userProxy.getUserDetailsById(userId);
    }
}
