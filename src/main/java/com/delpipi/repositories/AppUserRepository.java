package com.delpipi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.delpipi.entities.AppUser;
import com.delpipi.exceptions.UsernameNoFoundException;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username) throws UsernameNoFoundException ;
}
