package com.delpipi.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.delpipi.entities.AppRole;
import com.delpipi.entities.AppUser;
import com.delpipi.exceptions.UsernameNoFoundException;
import com.delpipi.repositories.AppRoleRepository;
import com.delpipi.repositories.AppUserRepository;
import com.delpipi.services.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AppUserRepository  appUserRepository;

    @Autowired
    private AppRoleRepository appRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser addNewUser(AppUser appUser) {
        String password = appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(password));
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(AppRole appRole) {
       return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) throws UsernameNoFoundException {
        AppUser appUser = appUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNoFoundException("Nom d'utilisateur introuvable : " + username));
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        appUser.getAppRoles().add(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) throws UsernameNoFoundException {
        return appUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNoFoundException("Nom d'utilisateur introuvable : " + username));
    }

    @Override
    public List<AppUser> listUsers() {
        return appUserRepository.findAll();
    }
    
}
