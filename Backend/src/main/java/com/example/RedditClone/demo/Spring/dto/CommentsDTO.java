package com.example.RedditClone.demo.Spring.dto;

import java.time.Instant;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDTO {

	private Long id;
    private Long postId;
    private Instant createdDate;
    private String text;
    private String userName;
}
