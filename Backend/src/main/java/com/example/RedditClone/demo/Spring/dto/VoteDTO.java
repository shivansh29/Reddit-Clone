package com.example.RedditClone.demo.Spring.dto;

import com.example.RedditClone.demo.Spring.model.VoteType;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {

	private VoteType voteType;
    private Long postId;
}
