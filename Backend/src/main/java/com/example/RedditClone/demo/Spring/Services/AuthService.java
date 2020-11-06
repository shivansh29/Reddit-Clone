package com.example.RedditClone.demo.Spring.Services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.RedditClone.demo.Spring.Repositories.UserRepository;
import com.example.RedditClone.demo.Spring.Repositories.VerificationTokenRepository;
import com.example.RedditClone.demo.Spring.dto.AuthenticationResponse;
import com.example.RedditClone.demo.Spring.dto.LoginRequest;
import com.example.RedditClone.demo.Spring.dto.RefreshTokenDTO;
import com.example.RedditClone.demo.Spring.dto.RegisterRequest;
import com.example.RedditClone.demo.Spring.exception.SpringRedditException;
import com.example.RedditClone.demo.Spring.model.NotificationEmail;
import com.example.RedditClone.demo.Spring.model.User;
import com.example.RedditClone.demo.Spring.model.VerificationToken;
import com.example.RedditClone.demo.Spring.security.JwtProvider;


@Service
public class AuthService {
	
	private final PasswordEncoder pswdEncoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository vTokenRepository;
	private final MailService mailSender;
	private final AuthenticationManager authManager;
	private final JwtProvider jwtProvider;
	private final RefreshTokenService refreshTokenService;
	
	
	@Autowired
	public AuthService(PasswordEncoder pswdEncoder, UserRepository userRepository, VerificationTokenRepository vTokenRepository, MailService mailSender, AuthenticationManager authManager, JwtProvider jwtProvider, RefreshTokenService refreshTokenService) {
		this.pswdEncoder = pswdEncoder;
		this.userRepository = userRepository;
		this.vTokenRepository = vTokenRepository;
		this.mailSender = mailSender;
		this.authManager = authManager;
		this.jwtProvider = jwtProvider;
		this.refreshTokenService = refreshTokenService;
	}



	@Transactional
	public void signUp(RegisterRequest registerRequest) {
		User user=new User();
		user.setEmail(registerRequest.getEmail());
		user.setPassword(pswdEncoder.encode(registerRequest.getPassword()));
		user.setUsername(registerRequest.getUsername());
		user.setEnabled(false);
		user.setCreated(Instant.now());
		
		userRepository.save(user);
		
		String token=generateToken(user);
		
		mailSender.sendMail(new NotificationEmail("Please verify your mail",user.getEmail(),
				"Thank you for signing up to Spring Reddit, " +
		                "please click on the below url to activate your account : " +"http://localhost:8559/api/auth/accountVerification/" + token));
	}
	
	
	public String generateToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken vToken = new VerificationToken();
		vToken.setToken(token);
		vToken.setUser(user);
		
		vTokenRepository.save(vToken);
		return token;
	}
	
	
	public AuthenticationResponse login(LoginRequest loginRequest) {
		Authentication authenticate=authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		
		String token=jwtProvider.generateToken(authenticate);
		return AuthenticationResponse.builder().expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
				.token(token).refreshToken(refreshTokenService.generateRefreshToken().getToken()).username(loginRequest.getUsername()).build();
	}
	
	
	
	public void verifyAccount(String token) {
		Optional<VerificationToken> verificationToken=vTokenRepository.findByToken(token);
		verificationToken.orElseThrow( ()-> new SpringRedditException("Invalid Token") );
		fetchUserandEnable(verificationToken.get());
	}
	
	@Transactional
	public void fetchUserandEnable(VerificationToken vToken) {
		String username = vToken.getUser().getUsername();
		User user=userRepository.findByUsername(username).orElseThrow( ()-> new SpringRedditException("User not found with Username "+username) );
		user.setEnabled(true);
		userRepository.save(user);
	} 
	
	@Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new SpringRedditException("User name not found - " + principal.getUsername()));
    }
	
	public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
	
	public AuthenticationResponse refreshToken(RefreshTokenDTO refreshToken) {
		refreshTokenService.validateRefreshToken(refreshToken.getRefreshToken());
		String token = jwtProvider.generateTokenWithUsername(refreshToken.getUsername());
		
		return AuthenticationResponse.builder().token(token).refreshToken(refreshToken.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshToken.getUsername())
                .build();
	}
}
