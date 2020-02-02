package com.team2.post.service;

import com.team2.post.collection.Post;
import com.team2.post.dto.PostActivityDTO;
import com.team2.post.dto.PostDTO;
import com.team2.post.dto.UserDetailDto;
import com.team2.post.response.BaseResponse;

import java.util.Date;
import java.util.List;

public interface PostService {
    Post addPost(Post post);
    List<Post> showPostByUserId(String userId);
    String getUserIdByPostId(String postId);
    List<Post> sortedList();
    Post getPostByPostId(String postId);
    List<Post> findPostByUserId(List<String> userId);
    BaseResponse<UserDetailDto> getUserDetails(String userId);
    Date getTimeStamp();
    void sendUserActivity(PostActivityDTO postActivityDTO);

}
