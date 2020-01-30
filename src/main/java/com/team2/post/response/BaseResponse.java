package com.team2.post.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseResponse<T> {
    private Boolean success;
    private String errorMessage;
    private T data;
}
