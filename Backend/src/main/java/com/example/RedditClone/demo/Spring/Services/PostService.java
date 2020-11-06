package com.example.RedditClone.demo.Spring.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.RedditClone.demo.Spring.Mapper.PostMapper;
import com.example.RedditClone.demo.Spring.Repositories.PostRepository;
import com.example.RedditClone.demo.Spring.Repositories.SubredditRepository;
import com.example.RedditClone.demo.Spring.Repositories.UserRepository;
import com.example.RedditClone.demo.Spring.dto.PostRequest;
import com.example.RedditClone.demo.Spring.dto.PostResponse;
import com.example.RedditClone.demo.Spring.exception.PostNotFoundException;
import com.example.RedditClone.demo.Spring.exception.SpringRedditException;
import com.example.RedditClone.demo.Spring.exception.SubRedditNotFoundException;
import com.example.RedditClone.demo.Spring.model.Post;
import com.example.RedditClone.demo.Spring.model.SubReddit;
import com.example.RedditClone.demo.Spring.model.User;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
	
	private final SubredditRepository subredditRepository;
	private final AuthService authService;
	private final PostMapper postMapper; 
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	
	public void save(PostRequest postRequest) {
		SubReddit subRed= subredditRepository.findByName(postRequest.getSubredditName())
				.orElseThrow( ()-> new SpringRedditException(postRequest.getSubredditName()) );
		User currentUser=authService.getCurrentUser();
		postRepository.save(postMapper.map(postRequest, subRed, currentUser));
		//return postMapper.map(postRequest, subRed, currentUser);
	}
	
	@Transactional(readOnly = true)
	public PostResponse getPost(Long id) {
		Post post=postRepository.findById(id)
				.orElseThrow( () -> new PostNotFoundException(id.toString()) );
		return postMapper.mapToDto(post);
	}
	

	@Transactional(readOnly = true)
	public List<PostResponse> getAll(){
		return postRepository.findAll().stream().map(postMapper::mapToDto)
				.collect(Collectors.toList());
	}
	

	@Transactional(readOnly = true)
	public List<PostResponse> getPostBySubReddit(Long subRedid) {
		SubReddit subRed= subredditRepository.findById(subRedid)
							.orElseThrow( ()-> new SubRedditNotFoundException(subRedid.toString()));
		List<Post> post=postRepository.findAllBySubreddit(subRed);
		return post.stream().map(postMapper::mapToDto).collect(Collectors.toList());
	}
	
	
	public List<PostResponse> getPostByUsername(String username){
		User user=userRepository.findByUsername(username)
				.orElseThrow( ()-> new SpringRedditException("Not Found "+ username));
		return postRepository.findAllByUser(user).stream().map(postMapper::mapToDto).collect(Collectors.toList());
	}
	
	
	
	
	
}
