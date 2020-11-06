package com.example.RedditClone.demo.Spring.model;

import java.time.Instant;

import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token")
public class VerificationToken {

	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String token;
    @OneToOne(fetch=FetchType.LAZY)
    private User user;
    private Instant expiryDate;
    
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Instant getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Instant expiryDate) {
		this.expiryDate = expiryDate;
	}
    
    
    
}
