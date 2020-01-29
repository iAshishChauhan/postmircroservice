package com.team2.post.collection;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Comment {
    @Id
    long commentId;
    long postId;
    long userId;
    String text;
    long parentCommentId;
    String timeStamp;
}
