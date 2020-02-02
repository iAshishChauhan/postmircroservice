package com.team2.post.service;

import com.team2.post.collection.BusinessPost;
import com.team2.post.collection.Post;
import com.team2.post.dto.BusinessDetailsDTO;
import com.team2.post.dto.PostDTO;
import com.team2.post.dto.UserDetailDto;
import com.team2.post.response.BaseResponse;

import java.util.Date;
import java.util.List;

public interface PostService {
    Post addPost(Post post);
    BusinessPost addBusinessPost(BusinessPost businessPost);
    List<Post> showPostByUserId(String userId);
    List<BusinessPost> showBusinessPostByAdminId(String adminId);
    String getUserIdByPostId(String postId);
    String getAdminIdByPostId(String postId);
    List<Post> sortedPost();
    List<BusinessPost> sortedBusinessPost();
    Post getPostByPostId(String postId);
    BusinessPost getBusinessPostByPostId(String postId);
    List<Post> findPostByUserId(List<String> userId);
    List<BusinessPost> findBusinessPostByAdminId(List<String> adminId);
    BaseResponse<UserDetailDto> getUserDetails(String userId);
    BaseResponse<BusinessDetailsDTO> getBusinessUserDetails(String adminId);
    Date getTimeStamp();

}
