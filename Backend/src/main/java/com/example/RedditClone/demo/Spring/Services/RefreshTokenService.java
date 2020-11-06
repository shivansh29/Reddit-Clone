package com.example.RedditClone.demo.Spring.Services;

import java.time.Instant;
import java.util.UUID;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.RedditClone.demo.Spring.Repositories.RefreshTokenRepository;
import com.example.RedditClone.demo.Spring.exception.SpringRedditException;
import com.example.RedditClone.demo.Spring.model.RefreshToken;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;
	
	public RefreshToken generateRefreshToken() {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setCreatedDate(Instant.now());
		refreshToken.setToken(UUID.randomUUID().toString());
		
		return refreshTokenRepository.save(refreshToken);
	}
	
	
	 void validateRefreshToken(String token) {
	        refreshTokenRepository.findByToken(token)
	                .orElseThrow(() -> new SpringRedditException("Invalid refresh Token"));
	    }
	 
	 public void deleteRefreshToken(String token) {
	        refreshTokenRepository.deleteByToken(token);
	    }
}
