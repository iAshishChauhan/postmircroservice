package com.team2.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDetailsDTO {
    private String adminId;
    private List<String> moderatorIds;
    private String category;
    private String status;
    private String businessName;
    private String businessImageUrl;
    private String email;
}
