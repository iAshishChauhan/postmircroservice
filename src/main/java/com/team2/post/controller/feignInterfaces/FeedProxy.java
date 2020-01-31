package com.team2.post.controller.feignInterfaces;


import com.team2.post.dto.PostActivityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="feed",url="http://10.177.69.66:8084")
public interface FeedProxy {

    @GetMapping("feed/addPostAfterActivity")
    String addPostAfterActivity(@RequestBody PostActivityDTO postActivityDTO);
}
