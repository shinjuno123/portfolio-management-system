package com.amazing.juno.springwebapp.filter;

import com.amazing.juno.springwebapp.properties.JWTConstraints;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

    JWTConstraints jwtConstraints;

    public JWTTokenGeneratorFilter(JWTConstraints jwtConstraints){
        this.jwtConstraints = jwtConstraints;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().contains("/api/public");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            SecretKey key = Keys.hmacShaKeyFor(jwtConstraints.getKey().getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder().setIssuer(jwtConstraints.getIssuer()).setSubject(jwtConstraints.getSubject())
                    .claim("username", authentication.getAuthorities())
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + 1000 * 60 * 60 * 24))
                    .signWith(key).compact();
            response.setHeader(jwtConstraints.getHeader(), jwt);
        }

        filterChain.doFilter(request, response);
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authoritiesSet = new HashSet<>();
        for(GrantedAuthority authority : authorities){
            authoritiesSet.add(authority.getAuthority());
        }

        return String.join(",", authoritiesSet);
    }
}
