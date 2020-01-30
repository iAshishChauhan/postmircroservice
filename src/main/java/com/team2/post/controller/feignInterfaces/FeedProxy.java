package com.team2.post.controller.feignInterfaces;


import com.team2.post.dto.UserDetailDto;
import com.team2.post.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="feed",url="http://10.177.68.178:8084")
public interface FeedProxy {

    @GetMapping("/getUserDetails/{userId}")
    BaseResponse<UserDetailDto> getUserDetailsById(@PathVariable(value = "userId") Long userId);
}
