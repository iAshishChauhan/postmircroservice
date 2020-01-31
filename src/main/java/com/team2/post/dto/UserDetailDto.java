package com.team2.post.dto;

import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDetailDto {

    private String userId;
    private String userName;
    private String imageUrl;
    private String gender;
    private String email;
    private Date DOB;
    private Long mobileNumber;
    private List<String> interests;
    private String profileType;
    private String displayType;

    private HashSet<Long> friendIds;
    private HashSet<Long> pendingFriendIds;
}
