package com.team2.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {

    private String userIdOfPost;
    private String postId;
    private String activityUserId;
    private HashSet<String> listOfFriends;
    private String activity;
    private static final String provider = "Facebook";
}
