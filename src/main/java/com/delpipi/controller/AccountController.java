package com.delpipi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delpipi.entities.AppUser;
import com.delpipi.entities.AuthRequest;
import com.delpipi.entities.AuthResponse;
import com.delpipi.services.AccountService;
import com.delpipi.services.JwtTokenService;
import com.delpipi.services.impl.AppUserDetails;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/")
public class AccountController {

    private Logger log = LoggerFactory.getLogger(AccountController.class);
    
    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwTokenService;

    @GetMapping(value = "users")
    public List<AppUser> listAllAppUser(){
        return accountService.listUsers();
    }

    @PostMapping(value = "login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest ){
        log.info("User credentials : " + authRequest.toString());
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
            final String jwtToken = jwTokenService.generateToken(appUserDetails);
            return ResponseEntity.ok(new AuthResponse(jwtToken, appUserDetails.getAppUser()));
        } catch (DisabledException | LockedException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
