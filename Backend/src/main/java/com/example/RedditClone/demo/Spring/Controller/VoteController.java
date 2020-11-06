package com.example.RedditClone.demo.Spring.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RedditClone.demo.Spring.Services.VoteService;
import com.example.RedditClone.demo.Spring.dto.VoteDTO;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/votes/")
@AllArgsConstructor
public class VoteController {
	
	private final VoteService voteService;

	@PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteDTO voteDto){
		
		voteService.Vote(voteDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
