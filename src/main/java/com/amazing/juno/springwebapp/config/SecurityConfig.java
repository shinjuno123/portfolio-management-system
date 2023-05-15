package com.amazing.juno.springwebapp.config;

import com.amazing.juno.springwebapp.filter.CsrfCookieFilter;
import com.amazing.juno.springwebapp.filter.JWTTokenGeneratorFilter;
import com.amazing.juno.springwebapp.filter.JWTTokenValidatorFilter;
import com.amazing.juno.springwebapp.properties.JWTConstraints;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTConstraints jwtConstraints;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        csrfTokenRequestAttributeHandler.setCsrfRequestAttributeName("_csrf");

        return http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().cors().configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:4200"));
                    config.setAllowedMethods(List.of("*"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(List.of("*"));
                    config.setExposedHeaders(List.of("Authorization"));
                    config.setMaxAge(3600L * 24);
                    return config;
                }).and().csrf(
                        httpSecurityCsrfConfigurer ->
                            httpSecurityCsrfConfigurer.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                                    .ignoringRequestMatchers("/api/public/**").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

                )
                .addFilterBefore(new JWTTokenValidatorFilter(jwtConstraints), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(jwtConstraints), BasicAuthenticationFilter.class)
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers("/user").authenticated()
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and().httpBasic()
                .and().build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
}










