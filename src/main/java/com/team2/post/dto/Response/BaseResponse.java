package com.team2.post.dto.Response;

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
    private T content;
    private HttpStatus status;
}

