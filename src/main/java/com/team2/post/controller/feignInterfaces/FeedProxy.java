package com.team2.post.controller.feignInterfaces;


import com.team2.post.dto.PostActivityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="feed",url="http://172.16.20.113:8084")
public interface FeedProxy
{
    @GetMapping("feed/addPostAfterActivity")
    String addPostAfterActivity(@RequestBody PostActivityDTO postActivityDTO);
}
