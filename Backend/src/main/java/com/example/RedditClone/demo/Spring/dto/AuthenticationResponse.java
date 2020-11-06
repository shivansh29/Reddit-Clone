package com.example.RedditClone.demo.Spring.dto;


import java.time.Instant;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {

	private String username;
	private String token;
	private String refreshToken;
    private Instant expiresAt;
}
