package com.amazing.juno.springwebapp.controller.admin.api;



import com.amazing.juno.springwebapp.dto.IntroDTO;
import com.amazing.juno.springwebapp.entity.Introduction;
import com.amazing.juno.springwebapp.exc.NotFoundException;
import com.amazing.juno.springwebapp.service.admin.IntroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
public class IntroRestController {

    public static final String INTRO_PATH = "/api/introduction";

    public static final String INTRO_ID_PATH =  "/api/introduction/{introId}";

    private final IntroService introService;

    @GetMapping(INTRO_PATH)
    public ResponseEntity<List<IntroDTO>> getAllIntroductionRecords(){
        return new ResponseEntity<>(introService.getAllIntroductionRecords(), HttpStatus.ACCEPTED);
    }

    @PostMapping(INTRO_PATH)
    public ResponseEntity<IntroDTO> saveIntroduction(@RequestBody IntroDTO introDTO){
        return new ResponseEntity<>(introService.saveIntroduction(introDTO), HttpStatus.CREATED);
    }

    @GetMapping(INTRO_ID_PATH)
    public ResponseEntity<IntroDTO> getIntroductionById(@PathVariable("introId") UUID introId){
        return new ResponseEntity<>(introService.getIntroductionById(introId).orElseThrow(NotFoundException::new), HttpStatus.ACCEPTED);
    }


    @GetMapping( INTRO_PATH + "/recent")
    public ResponseEntity<IntroDTO> getRecentIntroduction(){
        return new ResponseEntity<>(introService.getRecentIntroduction().orElseThrow(NotFoundException::new), HttpStatus.ACCEPTED);
    }




}
