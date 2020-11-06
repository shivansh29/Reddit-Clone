package com.example.RedditClone.demo.Spring.Mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.RedditClone.demo.Spring.dto.SubRedditDTO;
import com.example.RedditClone.demo.Spring.model.Post;
import com.example.RedditClone.demo.Spring.model.SubReddit;

@Mapper(componentModel = "spring")
public interface SubRedditMapper {

	@Mapping(target ="numberOfPosts", expression = "java(mapPosts(subRed.getPosts()))")
	SubRedditDTO mapSubRedditToDto (SubReddit subRed);
	
	default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }
	
	@InheritInverseConfiguration
	@Mapping(target="posts", ignore =true)
	SubReddit mapDTOtoSubReddit(SubRedditDTO subRedDto);
	
}
