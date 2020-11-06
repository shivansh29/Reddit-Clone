package com.example.RedditClone.demo.Spring.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Vote {

	
	  @Id
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    private Long voteId;
	    private VoteType voteType;
	    @NotNull
	    @ManyToOne(fetch=FetchType.LAZY)
	    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
	    private Post post;
	    @ManyToOne(fetch=FetchType.LAZY)
	    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
	    private User user;
}
