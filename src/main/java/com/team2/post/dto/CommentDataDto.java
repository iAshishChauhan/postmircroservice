package com.team2.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDataDto {
    private String imageUrl;
    private String userName;
    private long postId;
    private long commentId;
    private long parentCommentId;
    private String text;
    private ZonedDateTime timeStamp;
}
