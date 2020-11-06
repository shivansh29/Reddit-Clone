package com.example.RedditClone.demo.Spring.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostResponse {

	private Long id;
    private String postName;
    private String url;
    private String description;
    private String userName;
    private String subredditName;
    private Integer voteCount;
    private Integer commentCount;
    private String duration;
    private boolean upVote;
    private boolean downVote;
}
