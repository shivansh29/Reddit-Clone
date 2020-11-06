package com.example.RedditClone.demo.Spring.dto;

import javax.validation.constraints.NotBlank;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenDTO {

	@NotBlank
    private String refreshToken;
    private String username;
}
