package com.team2.post.service.impl;

import com.team2.post.collection.Reaction;
import com.team2.post.controller.feignInterfaces.FeedProxy;
import com.team2.post.controller.feignInterfaces.UserProxy;
import com.team2.post.dto.PostActivityDTO;
import com.team2.post.dto.UserDetailDto;
import com.team2.post.repository.ReactionRepository;
import com.team2.post.response.BaseResponse;
import com.team2.post.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReactionServiceImpl implements ReactionService {


    @Autowired
    ReactionRepository reactionRepository;

    @Autowired
    UserProxy userProxy;

    @Autowired
    FeedProxy feedProxy;

    @Override
    public void addPostActivity(Reaction reaction)
    {
      reactionRepository.insert(reaction);
    }

    @Override
    public List<Reaction> readReactionByPostId(String postId)
    {
        return reactionRepository.findAllByPostId(postId);
    }

    @Override
    public List<Reaction> getReactionByUserId(String userId)
    {
        return reactionRepository.findAllByUserId(userId);
    }

    @Override
    public BaseResponse<UserDetailDto> getUserDetails(String userId)
    {
      return userProxy.getUserDetailsById(userId);
    }

    @Override
    public void sendUserActivity(PostActivityDTO postActivityDTO)
    {
         feedProxy.addPostAfterActivity(postActivityDTO);
    }

    @Override
    public Date getTimeStamp() {
        Date now = new Date();
        Date timeStamp = new Date(now.getTime());
        return timeStamp;
    }
}
