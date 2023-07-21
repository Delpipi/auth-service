package com.delpipi.services;

import java.util.List;

import com.delpipi.entities.AppRole;
import com.delpipi.entities.AppUser;
import com.delpipi.exceptions.UsernameNoFoundException;

public interface AccountService {
    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String username, String roleName) throws UsernameNoFoundException;
    AppUser loadUserByUsername(String username) throws UsernameNoFoundException;
    List<AppUser> listUsers();
}
