package com.amazing.juno.springwebapp.controller.admin.api;


import com.amazing.juno.springwebapp.entity.Introduction;
import com.amazing.juno.springwebapp.service.admin.IntroService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@RestController
@RequestMapping("/api/introduction")
public class IntroRestController {


    private final IntroService introService;

    @GetMapping
    public ResponseEntity<List<Introduction>> getAllIntroductionRecords(){

        return new ResponseEntity<>(introService.getAllIntroductionRecords(), HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<Introduction> saveIntroduction(@RequestBody Introduction introduction){
        introService.saveIntroduction(introduction);

        return new ResponseEntity<>(introduction, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{introId}")
    public ResponseEntity<Introduction> getIntroductionById(@PathVariable("introId") UUID introId){

        return new ResponseEntity<>(introService.getIntroductionById(introId), HttpStatus.ACCEPTED);
    }


    @GetMapping("/recent")
    public ResponseEntity<Introduction> getRecentIntroduction(){

        return new ResponseEntity<>(introService.getRecentIntroduction(), HttpStatus.ACCEPTED);
    }


    @RequestMapping(value="/csrf-token", method=RequestMethod.GET)
    public @ResponseBody String getCsrfToken(HttpServletRequest request) {
        CsrfToken token = (CsrfToken)request.getAttribute(CsrfToken.class.getName());
        return token.getToken();
    }


}
