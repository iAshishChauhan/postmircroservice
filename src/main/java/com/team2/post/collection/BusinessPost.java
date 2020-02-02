package com.team2.post.collection;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "business_post")
public class BusinessPost {
    @Id
    private String  postId;
    private String category;
    private String adminId;
    private Content content;
    private Date timestamp;
    private int likeCount;
}