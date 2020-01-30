package com.team2.post.service;

import com.team2.post.collection.Reaction;
import com.team2.post.dto.UserDetailDto;
import com.team2.post.response.BaseResponse;

import java.util.List;

public interface ReactionService {

    void addPostActivity(Reaction reaction);

    List<Reaction> readReactionByPostId(Long postId);

    List<Reaction> getReactionByUserId(Long userId);

    BaseResponse<UserDetailDto> getUserDetails(Long userId);
}
