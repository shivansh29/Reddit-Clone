package com.example.RedditClone.demo.Spring.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.RedditClone.demo.Spring.dto.CommentsDTO;
import com.example.RedditClone.demo.Spring.model.Comment;
import com.example.RedditClone.demo.Spring.model.Post;
import com.example.RedditClone.demo.Spring.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "text", source = "commentsDto.text")
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "post", source = "post")
	@Mapping(target = "user", source = "user")
	Comment map(CommentsDTO commentsDto, User user,Post post);
	
	@Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
	CommentsDTO mapToDto(Comment comment);
}
