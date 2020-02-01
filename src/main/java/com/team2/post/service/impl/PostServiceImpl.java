package com.team2.post.service.impl;
import com.team2.post.collection.Post;
import com.team2.post.controller.feignInterfaces.UserProxy;
import com.team2.post.dto.UserDetailDto;
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

    @Override
    public Post addPost(Post post)
    {
        return postRepository.insert(post);
    }

    @Override
    public List<Post> showPostByUserId(String userId) {
        List<Post> postListByUserId = postRepository.findByUserId(userId);
        return postListByUserId;
    }

    @Override
    public String getUserIdByPostId(String postId) {
        Post post = postRepository.findByPostId(postId);
        return post.getUserId();
    }

    @Override
    public List<Post> sortedList() {
        List<Post> PostList = postRepository.findAll();
        PostList.sort(Comparator.comparing(Post::getTimestamp).reversed());
        return PostList;
    }

    @Override
    public Post getPostByPostId(String postId)
    {
        return postRepository.findByPostId(postId);
    }

    @Override
    public List<Post> findPostByUserId(List<String> userId)
    {
        return postRepository.findAllByUserIdIn(userId);
    }

    @Override
    public BaseResponse<UserDetailDto> getUserDetails(String userId) {
        return userProxy.getUserDetailsById(userId);
    }

    @Override
    public Date getTimeStamp() {
        Date now = new Date();
        Date timeStamp = new Date(now.getTime());
        return timeStamp;
    }
}
