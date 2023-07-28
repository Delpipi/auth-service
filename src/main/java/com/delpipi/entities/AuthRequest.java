package com.delpipi.entities;

import org.springframework.data.repository.NoRepositoryBean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @NoRepositoryBean @AllArgsConstructor
public class AuthRequest {
    private String username;
    private String password;
}
