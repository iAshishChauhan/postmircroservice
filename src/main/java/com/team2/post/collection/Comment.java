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
@Document(value = "comment")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    private long commentId;
    private long postId;
    private long userId;
    private String text;
    private long parentCommentId;
    private Date timeStamp;
}
