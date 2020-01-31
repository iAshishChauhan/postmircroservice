package com.team2.post.dto;

import com.sun.tracing.dtrace.ArgsAttributes;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private String commentId;
    private String postId;
    private String userId;
    private String text;
    private String parentCommentId;
    private Date timeStamp;
}
