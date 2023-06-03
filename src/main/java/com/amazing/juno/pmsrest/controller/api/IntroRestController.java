package com.amazing.juno.pmsrest.controller.api;



import com.amazing.juno.pmsrest.dto.IntroDTO;
import com.amazing.juno.pmsrest.exc.NotFoundException;
import com.amazing.juno.pmsrest.service.IntroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
public class IntroRestController {

    public static final String ADMIN_INTRO_PATH = "/api/admin/introduction";

    public static final String ADMIN_INTRO_ID_PATH =  "/api/admin/introduction/{introId}";
    public static final String PUBLIC_INTRO_PATH = "/api/public/introduction";

    private final IntroService introService;

    @GetMapping(ADMIN_INTRO_PATH)
    public ResponseEntity<List<IntroDTO>> getAllIntroductionRecords(){
        return new ResponseEntity<>(introService.getAllIntroductionRecords(), HttpStatus.ACCEPTED);
    }

    @PostMapping(ADMIN_INTRO_PATH)
    public ResponseEntity<IntroDTO> saveIntroduction(@Validated @RequestBody IntroDTO introDTO){
        return new ResponseEntity<>(introService.saveIntroduction(introDTO), HttpStatus.CREATED);
    }

    @GetMapping(ADMIN_INTRO_ID_PATH)
    public ResponseEntity<IntroDTO> getIntroductionById(@PathVariable("introId") UUID introId){
        return new ResponseEntity<>(introService.getIntroductionById(introId).orElseThrow(NotFoundException::new), HttpStatus.ACCEPTED);
    }


    @GetMapping(PUBLIC_INTRO_PATH)
    public ResponseEntity<IntroDTO> getRecentIntroduction(){
        return new ResponseEntity<>(introService.getRecentIntroduction().orElseThrow(NotFoundException::new), HttpStatus.ACCEPTED);
    }




}
