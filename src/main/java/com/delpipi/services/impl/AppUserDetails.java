package com.delpipi.services.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.delpipi.entities.AppUser;

public class AppUserDetails implements UserDetails{

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> autorities;
    private AppUser appUser;

    public AppUserDetails(AppUser appUser) {
        this.username = appUser.getUsername();
        this.password = appUser.getPassword();
        this.autorities = appUser.getAppRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
        this.appUser = appUser;
    }

    public AppUser getAppUser(){
        return appUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return autorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
