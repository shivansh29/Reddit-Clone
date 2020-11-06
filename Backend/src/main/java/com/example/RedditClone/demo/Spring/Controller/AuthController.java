package com.example.RedditClone.demo.Spring.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RedditClone.demo.Spring.Services.AuthService;
import com.example.RedditClone.demo.Spring.Services.RefreshTokenService;
import com.example.RedditClone.demo.Spring.dto.AuthenticationResponse;
import com.example.RedditClone.demo.Spring.dto.LoginRequest;
import com.example.RedditClone.demo.Spring.dto.RefreshTokenDTO;
import com.example.RedditClone.demo.Spring.dto.RegisterRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthService auth;
	@Autowired
	private RefreshTokenService refreshTokenService;
	

	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest) {
		auth.signUp(registerRequest);
		return new ResponseEntity<>("User Registration Successfull",HttpStatus.OK);
	}
	
	@GetMapping("/accountVerification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token) {
		auth.verifyAccount(token);
		
		return new ResponseEntity<>("Account Activated Successfully",HttpStatus.OK);
	}
	
	 @PostMapping("/login")
	 public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
	        return auth.login(loginRequest);
	    }
	
	 @PostMapping("/refresh/token")
	 public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenDTO refreshToken) {
		 return auth.refreshToken(refreshToken);
	 }
	 
	 @PostMapping("/logout")
	 public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenDTO refreshToken){
		 refreshTokenService.deleteRefreshToken(refreshToken.getRefreshToken());
		 return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!!");
	 }
	
}
