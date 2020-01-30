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
@Document(value = "reactions")
public class Reaction
{
    @Id
    private long reactionId;
    private long userId;
    private long postId;
    private String activity;
    private Date timeStamp;
}