package com.team2.post.controller.feignInterfaces;

import com.team2.post.dto.BusinessDetailsDTO;
import com.team2.post.dto.UserDetailDto;
import com.team2.post.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user",url="http://172.16.20.180:8082")
public interface UserProxy
{
    @GetMapping("user/getUserDetails/{userId}")
    BaseResponse<UserDetailDto> getUserDetailsById(@PathVariable(value = "userId") String userId);

    @GetMapping("/getBusinessUserDetails/{businessUserId}")
    BaseResponse<BusinessDetailsDTO> getBusinessUserDetails(@PathVariable String businessUserId);

}
