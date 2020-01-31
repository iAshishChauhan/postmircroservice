package com.team2.post.dto;

import lombok.*;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostTimelineDto {


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostTimelineDto that = (PostTimelineDto) o;
        return Objects.equals(postId, that.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId);
    }

    String postId;
    String userName;
    String imageUrl;
    String message;
    PostDTO postDTO;
    Date timeStamp;
}
