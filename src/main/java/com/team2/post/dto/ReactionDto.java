package com.team2.post.dto;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReactionDto {

    private String reactionId;
    private String userId;
    @NonNull
    private String postId;
    private String activity;
    private Date timeStamp;
}
