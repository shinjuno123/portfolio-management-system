package com.amazing.juno.springwebapp.controller.api;


import com.amazing.juno.springwebapp.dao.UserRepository;
import com.amazing.juno.springwebapp.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class LoginRestController {

    private final UserRepository userRepository;

    public final static String PUBLIC_USER = "/api/public/user";

    @GetMapping(PUBLIC_USER)
    public User getUserDetailsAfterLogin(Authentication authentication){
        User foundUser = userRepository.findUserByEmail(authentication.getName());

        return Optional.ofNullable(foundUser).orElseThrow(()-> new BadCredentialsException("User doesn't exist!"));
    }
}
