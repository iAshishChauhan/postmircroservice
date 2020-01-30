package com.team2.post.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T>{
    private String errorMessage;
    private boolean success;
    private T data;
    private HttpStatus status;
}