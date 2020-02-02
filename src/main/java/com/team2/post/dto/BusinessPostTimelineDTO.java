package com.team2.post.dto;

import com.team2.post.collection.BusinessPost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessPostTimelineDTO {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessPostTimelineDTO that = (BusinessPostTimelineDTO) o;
        return Objects.equals(postId, that.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId);
    }

    String postId;
    String businessName;
    String businessImageUrl;
    String message;
    BusinessPostDTO businessPostDTO;
    Date timeStamp;
}
