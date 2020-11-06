package com.example.RedditClone.demo.Spring.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.RedditClone.demo.Spring.Mapper.CommentMapper;
import com.example.RedditClone.demo.Spring.Repositories.CommentRepository;
import com.example.RedditClone.demo.Spring.Repositories.PostRepository;
import com.example.RedditClone.demo.Spring.Repositories.UserRepository;
import com.example.RedditClone.demo.Spring.dto.CommentsDTO;
import com.example.RedditClone.demo.Spring.exception.PostNotFoundException;
import com.example.RedditClone.demo.Spring.model.Comment;
import com.example.RedditClone.demo.Spring.model.NotificationEmail;
import com.example.RedditClone.demo.Spring.model.Post;
import com.example.RedditClone.demo.Spring.model.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentsService {


    private static final String POST_URL = "";
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final CommentMapper commentMapper;
	private final CommentRepository commentRepo;
	private final MailContentSender mailSender;
	private final MailService mailService;
	
	public void save(CommentsDTO commentDto) {
		Post post = postRepository.findById(commentDto.getPostId())
				.orElseThrow(()->  new PostNotFoundException(commentDto.getPostId().toString()));

		System.out.println("\n \n \n \n \n This is the required post   \n \n \n  "+post);
		Comment comment = commentMapper.map(commentDto, authService.getCurrentUser(), post);
		commentRepo.save(comment);
		//String message=mailSender.build(authService.getCurrentUser() + " posted a comment on your post." + POST_URL);
		String message = authService.getCurrentUser() + " posted a comment on your post.";
		//sendCommentNotification(message,post.getUser());
	}
	
	private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }
	
	public List<CommentsDTO> getAllCommentsForPost(Long postId){
		Post post = postRepository.findById(postId)
				.orElseThrow(()->  new PostNotFoundException(postId.toString()));
		
		return commentRepo.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(Collectors.toList());
	}
	
	public List<CommentsDTO> getAllCommentsForUser(String userName){
		User user= userRepository.findByUsername(userName).orElseThrow( ()-> new UsernameNotFoundException(userName) );
		
		return commentRepo.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
	}
	
}
