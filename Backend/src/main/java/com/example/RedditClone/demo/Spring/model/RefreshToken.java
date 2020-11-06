package com.example.RedditClone.demo.Spring.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

	 	@Id
	    @GeneratedValue(strategy =GenerationType.IDENTITY)
	    private Long id;
	    private String token;
	    private Instant createdDate;
}
