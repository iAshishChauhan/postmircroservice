package com.team2.post.service.impl;
import com.team2.post.collection.BusinessPost;
import com.team2.post.collection.Post;
import com.team2.post.controller.feignInterfaces.UserProxy;
import com.team2.post.dto.BusinessDetailsDTO;
import com.team2.post.dto.UserDetailDto;
import com.team2.post.repository.BusinessPostRepository;
import com.team2.post.repository.PostRepository;
import com.team2.post.response.BaseResponse;
import com.team2.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserProxy userProxy;

    @Autowired
    BusinessPostRepository businessPostRepository;

    @Override
    public Post addPost(Post post)
    {
        return postRepository.save(post);
    }

    @Override
    public BusinessPost addBusinessPost(BusinessPost businessPost) {
        return businessPostRepository.save(businessPost);
    }

    @Override
    public List<Post> showPostByUserId(String userId) {
        List<Post> postListByUserId = postRepository.findByUserId(userId);
        return postListByUserId;
    }

    @Override
    public List<BusinessPost> showBusinessPostByAdminId(String adminId) {
        List<BusinessPost> businessPostListByAdminId = businessPostRepository.findByAdminId(adminId);
        return businessPostListByAdminId;
    }

    @Override
    public String getUserIdByPostId(String postId) {
        Post post = postRepository.findByPostId(postId);
        return post.getUserId();
    }

    @Override
    public String getAdminIdByPostId(String postId) {
        BusinessPost businessPost  = businessPostRepository.findByPostId(postId);
        return businessPost.getAdminId();
    }

    @Override
    public List<Post> sortedPost() {
        List<Post> PostList = postRepository.findAll();
        PostList.sort(Comparator.comparing(Post::getTimestamp).reversed());
        return PostList;
    }


    @Override
    public List<BusinessPost> sortedBusinessPost() {
        List<BusinessPost> businessPostList = businessPostRepository.findAll();
        businessPostList.sort(Comparator.comparing(BusinessPost::getTimestamp).reversed());
        return businessPostList;
    }

    @Override
    public Post getPostByPostId(String postId)
    {
        return postRepository.findByPostId(postId);
    }

    @Override
    public BusinessPost getBusinessPostByPostId(String postId)
    {
        return businessPostRepository.findByPostId(postId);
    }

    @Override
    public List<Post> findPostByUserId(List<String> userId)
    {
        return postRepository.findAllByUserIdIn(userId);
    }

    @Override
    public List<BusinessPost> findBusinessPostByAdminId(List<String> adminId)
    {
        return businessPostRepository.findAllByAdminIdIn(adminId);
    }

    @Override
    public BaseResponse<UserDetailDto> getUserDetails(String userId) {
        return userProxy.getUserDetailsById(userId);
    }

    @Override
    public BaseResponse<BusinessDetailsDTO> getBusinessUserDetails(String adminId) {
        return userProxy.getBusinessUserDetails(adminId);
    }

    @Override
    public Date getTimeStamp() {
        Date now = new Date();
        Date timeStamp = new Date(now.getTime());
        return timeStamp;
    }
}
