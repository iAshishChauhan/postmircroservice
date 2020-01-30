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

    private long reactionId;
    private long userId;
    private long postId;
    private String activity;
    private Date timeStamp;
}
