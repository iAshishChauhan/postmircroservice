package com.team2.post.service;

import com.team2.post.collection.Reaction;
import com.team2.post.dto.PostActivityDTO;
import com.team2.post.dto.UserDetailDto;
import com.team2.post.response.BaseResponse;

import java.util.Date;
import java.util.List;

public interface ReactionService {

    void addPostActivity(Reaction reaction);

    List<Reaction> readReactionByPostId(String postId);

    List<Reaction> getReactionByUserId(String userId);

    List<Reaction> getReactionByAdminId(String adminId);

    BaseResponse<UserDetailDto> getUserDetails(String userId);

    void sendUserActivity(PostActivityDTO postActivityDTO);

    Date getTimeStamp();


}
