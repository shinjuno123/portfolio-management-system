package com.amazing.juno.springwebapp.filter;

import com.amazing.juno.springwebapp.exc.CustomBadCredentialsException;
import com.amazing.juno.springwebapp.properties.JWTConstraints;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@RequiredArgsConstructor
public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    private final JWTConstraints jwtConstraints;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        return request.getServletPath().contains("/api/public") || request.getServletPath().contains("/user");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader(jwtConstraints.getHeader());
        if(null != jwt){
            try{
                SecretKey key = Keys.hmacShaKeyFor(
                  jwtConstraints.getKey().getBytes(StandardCharsets.UTF_8)
                );

                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                String username= String.valueOf(claims.get("username"));
                String authorities = (String) claims.get("authorities");
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        username, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));

                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e){
                throw new BadCredentialsException("You entered bad credentials");
            }
        }

        filterChain.doFilter(request, response);
    }


}
