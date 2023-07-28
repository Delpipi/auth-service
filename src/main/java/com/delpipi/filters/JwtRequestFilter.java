package com.delpipi.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.delpipi.services.JwtTokenService;
import com.delpipi.services.impl.AppUserDetailService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private AppUserDetailService appUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("authorizationHeader");

        String jwtToken = null;
        String username = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            jwtToken = authorizationHeader.substring(7);
            username = jwtTokenService.extractUsername(jwtToken);
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                try {
                    UserDetails userDetails = appUserDetailService.loadUserByUsername(username);
                    if(jwtTokenService.validateToken(jwtToken, userDetails)){
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }else{
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.getWriter().write("Token expiré");
                    }
                } catch (UsernameNotFoundException e) {
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    response.getWriter().write("Utilisateur inexistant");
                }
            }
        }
        filterChain.doFilter(request, response);
    }
    
}
