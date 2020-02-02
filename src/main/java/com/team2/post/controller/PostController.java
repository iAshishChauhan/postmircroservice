package com.team2.post.controller;

import com.team2.post.collection.BusinessPost;
import com.team2.post.collection.Comment;
import com.team2.post.collection.Post;
import com.team2.post.collection.Reaction;
import com.team2.post.dto.*;
import com.team2.post.response.BaseResponse;
import com.team2.post.service.CommentService;
import com.team2.post.service.PostService;
import com.team2.post.service.ReactionService;
import com.team2.post.service.impl.Producer;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    ReactionService reactionService;

    @Autowired
    CommentService commentService;

    @PostMapping("/addBusinessPost")
    public String addPost(@RequestBody BusinessPostDTO businessPostDTO) {
        try {
            BusinessPost businessPost = new BusinessPost();
            businessPostDTO.setTimestamp(postService.getTimeStamp());
            BeanUtils.copyProperties(businessPostDTO, businessPost);
            postService.addBusinessPost(businessPost);
            return businessPost.getPostId();
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        return null;
    }

    @PostMapping("/addPost")
    public String addPost(@RequestBody PostDTO postDTO) {

        try {
            Post post = new Post();
            postDTO.setTimestamp(postService.getTimeStamp());
            BeanUtils.copyProperties(postDTO, post);
            postService.addPost(post);
            //postNotification(postDTO);
            return post.getPostId();
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        return null;
    }

    @GetMapping("/getByUserId/{userId}")
    public List<Post> postByUserId(@PathVariable("userId") String userId) {
        return postService.showPostByUserId(userId);
    }

    @GetMapping("/getByAdminId/{adminId}")
    public List<BusinessPost> postByAdminId(@PathVariable("adminId") String adminId) {
        return postService.showBusinessPostByAdminId(adminId);
    }

    @GetMapping("/getUserIdByPostId/{postId}")
    public String userIdByPostId(@PathVariable("postId") String postId) {
        return postService.getUserIdByPostId(postId);
    }

    @GetMapping("/getAdminIdByPostId/{postId}")
    public String adminIdByPostId(@PathVariable("postId") String postId) {
        return postService.getAdminIdByPostId(postId);
    }

    @GetMapping("/getSortedPosts")
    public List<Post> sortedPosts() {
        return postService.sortedPost();
    }

    @GetMapping("/getSortedBusinessPosts")
    public List<BusinessPost> sortedBusinessPosts() {
        return postService.sortedBusinessPost();
    }

    @GetMapping("/user/timeline/{userId}")
    public BaseResponse<List<PostTimelineDto>> getUsersAllPost(@PathVariable("userId") String userId) {
        try {

            Set<PostDTO> postDTOS = new HashSet<>();
            List<PostTimelineDto> postTimelineDtos = new ArrayList<>();
            List<Post> posts = postService.showPostByUserId(userId);
            List<Comment> comments = commentService.findByUserId(userId);
            List<Reaction> reactions = reactionService.getReactionByUserId(userId);


            for (Post post : posts) {
                PostDTO postDTO = new PostDTO();
                BeanUtils.copyProperties(post, postDTO);
                BaseResponse<UserDetailDto> user = postService.getUserDetails(post.getUserId());
                UserDetailDto userDetailDto = user.getData();
                PostTimelineDto postTimelineDto = new PostTimelineDto();
                postTimelineDto.setPostId(post.getPostId());
                postTimelineDto.setUserName(userDetailDto.getUserName());
                postTimelineDto.setImageUrl(userDetailDto.getImageUrl());
                postTimelineDto.setMessage("Posted");
                postTimelineDto.setPostDTO(postDTO);
                postTimelineDto.setTimeStamp(postDTO.getTimestamp());
                postTimelineDtos.add(postTimelineDto);
            }

            for (Comment comment : comments) {
                PostDTO postDTO = new PostDTO();
                BeanUtils.copyProperties(postService.getPostByPostId(comment.getPostId()), postDTO);
                BaseResponse<UserDetailDto> user = postService.getUserDetails(comment.getUserId());
                UserDetailDto userDetailDto = user.getData();
                PostTimelineDto postTimelineDto = new PostTimelineDto();
                postTimelineDto.setPostId(postDTO.getPostId());
                postTimelineDto.setUserName(userDetailDto.getUserName());
                postTimelineDto.setImageUrl(userDetailDto.getImageUrl());
                postTimelineDto.setMessage("Commented");
                postTimelineDto.setPostDTO(postDTO);
                postTimelineDto.setTimeStamp(comment.getTimeStamp());
                postTimelineDtos.add(postTimelineDto);

            }
            for (Reaction reaction : reactions) {
                PostDTO postDTO = new PostDTO();
                BeanUtils.copyProperties(postService.getPostByPostId(reaction.getPostId()), postDTO);
                BaseResponse<UserDetailDto> user = postService.getUserDetails(reaction.getUserId());
                UserDetailDto userDetailDto = user.getData();
                PostTimelineDto postTimelineDto = new PostTimelineDto();
                postTimelineDto.setPostId(postDTO.getPostId());
                postTimelineDto.setUserName(userDetailDto.getUserName());
                postTimelineDto.setImageUrl(userDetailDto.getImageUrl());
                postTimelineDto.setMessage(reaction.getActivity());
                postTimelineDto.setPostDTO(postDTO);
                postTimelineDto.setTimeStamp(reaction.getTimeStamp());
                postTimelineDtos.add(postTimelineDto);
            }
            List<PostTimelineDto> postWithoutDuplicates = Lists.newArrayList(Sets.newHashSet(postTimelineDtos));
            postWithoutDuplicates.sort(Comparator.comparing(PostTimelineDto::getTimeStamp).reversed());

            return new BaseResponse<>("null", true, postWithoutDuplicates, HttpStatus.CREATED);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    @GetMapping("/admin/timeline/{adminId}")
    public BaseResponse<List<BusinessPostTimelineDTO>> getAdminsAllPost(@PathVariable("adminId") String adminId) {
        try {

            Set<BusinessPostDTO> businessPostDTOs = new HashSet<>();
            List<BusinessPostTimelineDTO> businessPostTimelineDTOs = new ArrayList<>();
            List<BusinessPost> businessPosts = postService.showBusinessPostByAdminId(adminId);
            List<Comment> comments = commentService.findByAdminId(adminId);
            List<Reaction> reactions = reactionService.getReactionByAdminId(adminId);


            for (BusinessPost businessPost : businessPosts) {
                BusinessPostDTO businessPostDTO = new BusinessPostDTO();
                BeanUtils.copyProperties(businessPost, businessPostDTO);
                BaseResponse<BusinessDetailsDTO> business = postService.getBusinessUserDetails(businessPost.getAdminId());
                BusinessDetailsDTO businessDetailsDTO = business.getData();
                BusinessPostTimelineDTO businessPostTimelineDTO = new BusinessPostTimelineDTO();
                businessPostTimelineDTO.setPostId(businessPost.getPostId());
                businessPostTimelineDTO.setBusinessName(businessDetailsDTO.getBusinessName());
                businessPostTimelineDTO.setBusinessImageUrl(businessDetailsDTO.getBusinessImageUrl());
                businessPostTimelineDTO.setMessage("Posted");
                businessPostTimelineDTO.setBusinessPostDTO(businessPostDTO);
                businessPostTimelineDTO.setTimeStamp(businessPost.getTimestamp());
                businessPostTimelineDTOs.add(businessPostTimelineDTO);
            }

            for (Comment comment : comments) {
                BusinessPostDTO businessPostDTO = new BusinessPostDTO();
                BeanUtils.copyProperties(postService.getBusinessPostByPostId(comment.getPostId()), businessPostDTO);
                BaseResponse<BusinessDetailsDTO> business = postService.getBusinessUserDetails(comment.getUserId());
                BusinessDetailsDTO businessDetailsDTO = business.getData();
                BusinessPostTimelineDTO businessPostTimelineDTO = new BusinessPostTimelineDTO();
                businessPostTimelineDTO.setPostId(businessPostDTO.getPostId());
                businessPostTimelineDTO.setBusinessName(businessDetailsDTO.getBusinessName());
                businessPostTimelineDTO.setBusinessImageUrl(businessDetailsDTO.getBusinessImageUrl());
                businessPostTimelineDTO.setMessage("Commented");
                businessPostTimelineDTO.setBusinessPostDTO(businessPostDTO);
                businessPostTimelineDTO.setTimeStamp(comment.getTimeStamp());
                businessPostTimelineDTOs.add(businessPostTimelineDTO);

            }
            for (Reaction reaction : reactions) {
                BusinessPostDTO businessPostDTO = new BusinessPostDTO();
                BeanUtils.copyProperties(postService.getBusinessPostByPostId(reaction.getPostId()), businessPostDTO);
                BaseResponse<BusinessDetailsDTO> business = postService.getBusinessUserDetails(reaction.getUserId());
                BusinessDetailsDTO businessDetailsDTO = business.getData();
                BusinessPostTimelineDTO businessPostTimelineDTO = new BusinessPostTimelineDTO();
                businessPostTimelineDTO.setPostId(businessPostDTO.getPostId());
                businessPostTimelineDTO.setBusinessName(businessDetailsDTO.getBusinessName());
                businessPostTimelineDTO.setBusinessImageUrl(businessDetailsDTO.getBusinessImageUrl());
                businessPostTimelineDTO.setMessage(reaction.getActivity());
                businessPostTimelineDTO.setBusinessPostDTO(businessPostDTO);
                businessPostTimelineDTO.setTimeStamp(reaction.getTimeStamp());
                businessPostTimelineDTOs.add(businessPostTimelineDTO);
            }
            List<BusinessPostTimelineDTO> businessPostWithoutDuplicates = Lists.newArrayList(Sets.newHashSet(businessPostTimelineDTOs));
            businessPostWithoutDuplicates.sort(Comparator.comparing(BusinessPostTimelineDTO::getTimeStamp).reversed());

            return new BaseResponse<>("null", true, businessPostWithoutDuplicates, HttpStatus.CREATED);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }


    @PostMapping("/getPostsByUserIds")
    public List<PostDTO> getPostsByUserIds(@RequestBody List<String> userId) {
        List<Post> posts = postService.findPostByUserId(userId);
        List<PostDTO> postDTOS = new ArrayList<>();
        for (Post post : posts) {
            PostDTO postDTO = new PostDTO();
            BeanUtils.copyProperties(post, postDTO);
            postDTOS.add(postDTO);
        }
        return postDTOS;
    }

    @PostMapping("/getPostsByAdminIds")
    public List<BusinessPostDTO> getPostsByAdminIds(@RequestBody List<String> adminId) {
        List<BusinessPost> businessPosts = postService.findBusinessPostByAdminId(adminId);
        List<BusinessPostDTO> businessPostDTOS = new ArrayList<>();
        for (BusinessPost businessPost : businessPosts) {
            BusinessPostDTO businessPostDTO = new BusinessPostDTO();
            BeanUtils.copyProperties(businessPost, businessPostDTO);
            businessPostDTOS.add(businessPostDTO);
        }
        return businessPostDTOS;
    }

    public void postNotification(PostDTO postDTO) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setActivity("Posted");
        notificationDto.setPostId(postDTO.getPostId());
        notificationDto.setUserIdOfPost(postDTO.getUserId());
        BaseResponse<UserDetailDto> user = postService.getUserDetails(postDTO.getUserId());
        UserDetailDto userDetailDto = user.getData();
        HashSet<String> userFriend = userDetailDto.getFriendIds();
        notificationDto.setListOfFriends(userFriend);
        Producer producer = new Producer();
        producer.kafkaProducer(notificationDto);
    }

    public void postNotification(BusinessPostDTO businessPostDTO) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setActivity("Posted");
        notificationDto.setPostId(businessPostDTO.getPostId());
        notificationDto.setUserIdOfPost(businessPostDTO.getAdminId());
        Producer producer = new Producer();
        producer.kafkaProducer(notificationDto);
    }


}