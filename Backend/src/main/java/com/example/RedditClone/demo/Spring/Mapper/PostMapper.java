package com.example.RedditClone.demo.Spring.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.RedditClone.demo.Spring.Repositories.CommentRepository;
import com.example.RedditClone.demo.Spring.Repositories.VoteRepository;
import com.example.RedditClone.demo.Spring.Services.AuthService;
import com.example.RedditClone.demo.Spring.dto.PostRequest;
import com.example.RedditClone.demo.Spring.dto.PostResponse;
import com.example.RedditClone.demo.Spring.model.*;
import com.github.marlonlom.utilities.timeago.TimeAgo;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

	@Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;

    
	@Mapping(target="description", source="postrequest.description")
	@Mapping(target="createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target="voteCount", constant ="0")
	@Mapping(target = "subreddit", source = "subreddit")
	abstract public Post map(PostRequest postrequest,SubReddit subreddit,User user);
	
	@Mapping(target="id", source="postId")
	@Mapping(target="subredditName", source="subreddit.name")
	@Mapping(target="userName", source="user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
	abstract public PostResponse  mapToDto(Post post);
	

	 Integer commentCount(Post post) {
	        return commentRepository.findByPost(post).size();
	    }

	    String getDuration(Post post) {
	        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
	    }
	/*
	    boolean isPostUpVoted(Post post) {
	        return checkVoteType(post, VoteType.UPVOTE);
	    }

	    boolean isPostDownVoted(Post post) {
	        return checkVoteType(post, VoteType.DOWNVOTE);
	    }

	    /*private boolean checkVoteType(Post post, VoteType voteType) {
	        if (authService.isLoggedIn()) {
	            Optional<Vote> voteForPostByUser =
	                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
	                            authService.getCurrentUser());
	            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
	                    .isPresent();
	        }
	        return false;
	    }*/
}
