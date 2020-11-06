package com.example.RedditClone.demo.Spring.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.example.RedditClone.demo.Spring.exception.SpringRedditException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtProvider {
	
	private KeyStore keyStore;
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;
    
	public String generateToken(Authentication authentication) {
		User principal = (User)authentication.getPrincipal();
		return Jwts.builder()
				.setSubject(principal.getUsername())
				.signWith(getPrivateKey())
				.setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
				.compact();
				
	}
	
	public String generateTokenWithUsername(String username) {
		return Jwts.builder()
				.setSubject(username)
				.signWith(getPrivateKey())
				.setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
				.compact();
				
	}
	
	
	 @PostConstruct
	    public void init() {
	        try {
	            keyStore = KeyStore.getInstance("JKS");
	            InputStream resourceAsStream = getClass().getResourceAsStream("/springreddit.jks");
	            keyStore.load(resourceAsStream, "Shiv@nsh29".toCharArray());
	        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
	        	e.printStackTrace();
	            throw new SpringRedditException("Exception occurred while loading keystore", e);
	        }

	    }
	 
	 private PrivateKey getPrivateKey() {
	        try {
	            return (PrivateKey) keyStore.getKey("springreddit", "Shiv@nsh29".toCharArray());
	        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
	            throw new SpringRedditException("Exception occured while retrieving public key from keystore", e);
	        }
	    }
	 
	 public boolean ValidateToken(String JwtToken) {
		 Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(JwtToken);
		 return true;
	 }
	 
	 public PublicKey getPublicKey() {
		 try {
	            return keyStore.getCertificate("springreddit").getPublicKey();
	        } catch (KeyStoreException e) {
	            throw new SpringRedditException("Exception occured while retrieving public key from keystore", e);
	        }
	 }
	 
	 public String getUserName(String token) {
		 Claims claims = Jwts.parser()
	                .setSigningKey(getPublicKey())
	                .parseClaimsJws(token)
	                .getBody();

	        return claims.getSubject();
	 }


	public Long getJwtExpirationInMillis() {
		return jwtExpirationInMillis;
	}
 

}
