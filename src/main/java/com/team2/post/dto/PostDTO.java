package com.team2.post.dto;

import com.team2.post.collection.Content;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private String  postId;
    private String category;
    private String userId;
    private Content content;
    private Date timestamp;
}
