package com.example.demo.security.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.security.jwt.util.JwtUtil;
import com.example.demo.security.service.UserService;

@Component
public class JwtFilter extends OncePerRequestFilter {
 
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService service;
 
 
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
 
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
 
        String token = null;
        String userName = null;
 
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            userName = jwtUtil.extractUsername(token);
            System.out.println("****************** Filter username : "+ userName);
        }
 
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	System.out.println("****************** Filter contenthold null ");
            UserDetails userDetails = service.loadUserByUsername(userName);
 
            if (jwtUtil.validateToken(token, userDetails)) {
 
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken); // 권한 체크하지 않고 패스  
            }else {
      
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
	
}