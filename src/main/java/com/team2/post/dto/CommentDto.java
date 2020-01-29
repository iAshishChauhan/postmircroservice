package com.team2.post.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDto {
    String imageUrl;
    String userName;
    long commentId;
    long parentCommentId;
    String text;
    String timeStamp;
}
