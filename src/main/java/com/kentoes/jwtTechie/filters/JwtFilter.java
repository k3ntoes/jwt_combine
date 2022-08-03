package com.kentoes.jwtTechie.filters;

import com.kentoes.jwtTechie.services.UserDetailServiceImpl;
import com.kentoes.jwtTechie.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private String TOKEN_PREFIX = "Bearer ";
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailServiceImpl service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authorizationHeader = request.getHeader("Authorization");
//        String token = null;
//        String username = null;
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            token = authorizationHeader.substring(7);
//            username = jwtUtil.extractUsername(token);
//        }
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = service.loadUserByUsername(username);
//            if (jwtUtil.validateToken(token, userDetails)) {
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                usernamePasswordAuthenticationToken.setDetails(
//                        new WebAuthenticationDetailsSource().buildDetails(request)
//                );
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            }
//        }
//
//        filterChain.doFilter(request, response);
        UsernamePasswordAuthenticationToken auth = getAuthentication(request);
        if (auth == null) {
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null || !token.startsWith(TOKEN_PREFIX)) return null;
        String username = jwtUtil.extractUsername(token.replace(TOKEN_PREFIX, ""));
        if (username == null) return null;
        UserDetails userDetails = service.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
    }
}
