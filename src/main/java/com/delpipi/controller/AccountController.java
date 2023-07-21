package com.delpipi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delpipi.entities.AppUser;
import com.delpipi.services.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
    
    @Autowired
    private AccountService accountService;

    @GetMapping("/users")
    public List<AppUser> listAllAppUser(){
        return accountService.listUsers();
    }
}
