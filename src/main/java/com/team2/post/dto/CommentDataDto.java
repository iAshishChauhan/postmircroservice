package com.team2.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDataDto {
    private String imageUrl;
    private String userName;
    private String postId;
    private String commentId;
    private String parentCommentId;
    private String text;
    private Date timeStamp;
    private List<CommentDataDto> childComment;
}
