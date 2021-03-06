package com.team2.post.controller.feignInterfaces;

import com.team2.post.dto.response.BaseResponse;
import com.team2.post.dto.UserDetailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user",url="http://10.177.68.178:8082")
public interface UserProxy
{
    @GetMapping("user/getUserDetails/{userId}")
    BaseResponse<UserDetailDto> getUserDetailsById(@PathVariable(value = "userId") Long userId);
}
