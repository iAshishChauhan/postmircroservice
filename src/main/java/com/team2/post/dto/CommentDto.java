package com.team2.post.dto;

import com.sun.tracing.dtrace.ArgsAttributes;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private long commentId;
    private long postId;
    private long userId;
    private String text;
    private long parentCommentId;
    private Date timeStamp;
}
