package com.team2.post.controller;
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

    @PostMapping("/addPost")
    public String addPost(@RequestBody PostDTO postDTO){

        try
        {
            Post post = new Post();
            postDTO.setTimestamp(postService.getTimeStamp());
            BeanUtils.copyProperties(postDTO, post);
            postService.addPost(post);
            //postNotification(postDTO);
            return post.getPostId();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
            ex.printStackTrace();
        }
        return null;
    }

    @GetMapping("/getByUserId/{userId}")
    public List<Post> postByUserId(@PathVariable("userId") String userId){
        return postService.showPostByUserId(userId);
    }

    @GetMapping("/getUserIdByPostId/{postId}")
    public String userIdByPostId(@PathVariable("postId")String postId){
        return postService.getUserIdByPostId(postId);
    }

    @GetMapping("/getSortedPosts")
    public List<Post> sortedPosts(){
        return  postService.sortedList();
    }

    @GetMapping("/user/timeline/{userId}")
    public BaseResponse<List<PostTimelineDto>> getUsersAllPost(@PathVariable("userId") String userId)
    {
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
        }catch (Exception ex)
        {
            System.out.println(ex);
        }
        return null;
    }


    @PostMapping("/getPostsByUserIds")
    public List<PostDTO> getPostsByUserIds(@RequestBody List<String> userId)
    {
        List<Post> posts = postService.findPostByUserId(userId);
        List<PostDTO> postDTOS = new ArrayList<>();
        for(Post post:posts)
        {
            PostDTO postDTO = new PostDTO();
            BeanUtils.copyProperties(post,postDTO);
            postDTOS.add(postDTO);
        }
        return postDTOS;
    }

    public void postNotification(PostDTO postDTO)
    {
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


}