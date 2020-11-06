package com.example.RedditClone.demo.Spring.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.RedditClone.demo.Spring.Mapper.SubRedditMapper;
import com.example.RedditClone.demo.Spring.Repositories.SubredditRepository;
import com.example.RedditClone.demo.Spring.dto.SubRedditDTO;
import com.example.RedditClone.demo.Spring.exception.SpringRedditException;
import com.example.RedditClone.demo.Spring.model.SubReddit;

import static java.util.stream.Collectors.toList;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Service()
@Slf4j
public class SubRedditService {
	
	@Autowired
	private SubredditRepository subredditRepository;
	@Autowired
	private SubRedditMapper subMapper;

	@Transactional
	public SubRedditDTO save(SubRedditDTO subRed) {
		SubReddit save= subredditRepository.save(subMapper.mapDTOtoSubReddit(subRed));
		subRed.setId(save.getId());
		return subRed;
	}
	
	
	
	@Transactional
	public List<SubRedditDTO> getAll() {
		return subredditRepository.findAll().stream().map(subMapper::mapSubRedditToDto).collect(toList());
	}
	
	
	public SubRedditDTO getSubRedditDto(Long id) {
		SubReddit sub=subredditRepository.findById(id)
				.orElseThrow( ()-> new SpringRedditException("No subreddit found with ID - " + id) );
		
		return subMapper.mapSubRedditToDto(sub);
	}
}
