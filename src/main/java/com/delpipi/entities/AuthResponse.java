package com.delpipi.entities;

import org.springframework.data.repository.NoRepositoryBean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @NoRepositoryBean @AllArgsConstructor
public class AuthResponse {
    
    private String jwt;
    private AppUser appUser;
}
