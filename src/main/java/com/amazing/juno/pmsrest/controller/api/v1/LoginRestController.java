package com.amazing.juno.pmsrest.controller.api.v1;


import com.amazing.juno.pmsrest.dao.UserRepository;
import com.amazing.juno.pmsrest.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class LoginRestController {

    private final UserRepository userRepository;

    public final static String PUBLIC_USER = "/user";

    public final static String PUBLIC_INFORMATION = "/api/v1/public/information";

    @GetMapping(PUBLIC_USER)
    public User getUserDetailsAfterLogin(Authentication authentication){
        User foundUser = userRepository.findUserByEmail(authentication.getName());

        return Optional.ofNullable(foundUser).orElseThrow(()-> new BadCredentialsException("User doesn't exist!"));
    }

    @GetMapping(PUBLIC_INFORMATION)
    @ResponseBody
    public User getUserInformation() {

        return userRepository.findUserByRole("ROLE_ADMIN");
    }
}
