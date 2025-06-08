package com.example.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jwt.config.RequestContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class JwtTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            RequestContext.setToken(token);

            JwtTokenUtil.decodeToken(token).ifPresent(jwt -> {
                if (!JwtTokenUtil.isTokenExpired(jwt)) {
                    String role = JwtTokenUtil.getRole(jwt);
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.toUpperCase());
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(jwt.getSubject(), null, List.of(authority));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            });
        }

        try {
            chain.doFilter(request, response);
        } finally {
            RequestContext.clear(); // 🔥 Always clear after use
        }

    }
}
