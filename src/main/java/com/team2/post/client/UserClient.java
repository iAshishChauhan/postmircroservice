package com.team2.post.client;

import com.team2.post.dto.response.BaseResponse;
import com.team2.post.dto.UserDetailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(url = "http://10.177.68.178:8082/user", name = "UserClient")
public interface UserClient {

    @RequestMapping("/getUserDetails/{userId}")
    BaseResponse<UserDetailDto> getUserDetailsById(@PathVariable(value = "userId") long userId);
}
