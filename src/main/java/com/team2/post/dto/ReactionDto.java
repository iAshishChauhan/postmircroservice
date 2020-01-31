package com.team2.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReactionDto {

    private String reactionId;
    private String userId;
    private String postId;
    private String activity;
    private Date timeStamp;
}
