package com.example.RedditClone.demo.Spring.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RedditClone.demo.Spring.Services.SubRedditService;
import com.example.RedditClone.demo.Spring.dto.SubRedditDTO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/subreddit")
@Slf4j
public class SubRedditController {
	
	@Autowired
	private SubRedditService subRedService;

	@PostMapping()
	public ResponseEntity<SubRedditDTO> createSubreddit(@RequestBody SubRedditDTO subReddit) {
		return ResponseEntity.status(HttpStatus.CREATED).body(subRedService.save(subReddit));
		
	}
	
	@GetMapping()
	public ResponseEntity<List<SubRedditDTO>> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(subRedService.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SubRedditDTO> getSubReddit(@PathVariable Long id ) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(subRedService.getSubRedditDto(id));
	}
}
