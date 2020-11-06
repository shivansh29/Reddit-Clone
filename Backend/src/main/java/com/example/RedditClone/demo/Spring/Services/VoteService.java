package com.example.RedditClone.demo.Spring.Services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.RedditClone.demo.Spring.Repositories.PostRepository;
import com.example.RedditClone.demo.Spring.model.Vote;
import com.example.RedditClone.demo.Spring.Repositories.VoteRepository;
import com.example.RedditClone.demo.Spring.dto.VoteDTO;
import com.example.RedditClone.demo.Spring.exception.PostNotFoundException;
import com.example.RedditClone.demo.Spring.exception.SpringRedditException;
import com.example.RedditClone.demo.Spring.model.Post;
import static com.example.RedditClone.demo.Spring.model.VoteType.UPVOTE;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteService {

	private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    
    @Transactional
    public void Vote(VoteDTO voteDto) {
    	Post post = postRepository.findById(voteDto.getPostId())
    			.orElseThrow( ()-> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()) );
    	
    	Optional<Vote> voteByPostAndUser= voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
    	
    	if(voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
    		throw new SpringRedditException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
    	}
    	
    	if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
    	
    	voteRepository.save(DtoToVote(voteDto,post));
    	postRepository.save(post);
    }
    
    
    
    private Vote DtoToVote(VoteDTO voteDto,Post post) {
    	return Vote.builder().voteType(voteDto.getVoteType())
    			.post(post).user(authService.getCurrentUser())
    			.build();
    }
}
