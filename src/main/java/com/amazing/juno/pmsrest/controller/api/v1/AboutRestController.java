package com.amazing.juno.pmsrest.controller.api.v1;
import com.amazing.juno.pmsrest.dto.AboutDTO;
import com.amazing.juno.pmsrest.entity.ResponseSuccess;
import com.amazing.juno.pmsrest.exc.NotFoundException;
import com.amazing.juno.pmsrest.service.filestorage.FileStorageService;
import com.amazing.juno.pmsrest.service.about.AboutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class AboutRestController {

    private final AboutService aboutService;

    private final FileStorageService fileStorageService;

    public final static String PUBLIC_ABOUT_PATH = "/api/public/about";

    public final static String ADMIN_ABOUT_PATH = "/api/admin/about";

    public final static String ADMIN_ABOUT_ID_PATH = "/api/admin/about/{aboutId}";


    @GetMapping(ADMIN_ABOUT_PATH)
    public ResponseEntity<List<AboutDTO>> getAllAbout(){

        return new ResponseEntity<>(aboutService.getAllAbout(), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = ADMIN_ABOUT_PATH)
    public ResponseEntity<AboutDTO> saveAbout(@Validated @RequestPart("about") AboutDTO aboutDTO,
                                              @RequestPart(value = "faceImage", required = false) MultipartFile faceImage,
                                              @RequestPart(value = "diploma", required = false) MultipartFile diploma,
                                              @RequestPart(value = "transcript", required = false) MultipartFile transcript){


        String filePath = "";
        String diplomaPath = "";
        String transcriptPath = "";

        if(faceImage != null) {
            filePath = fileStorageService.saveFile(faceImage, "about");
        }
        if(diploma != null) {
            diplomaPath = fileStorageService.saveFile(diploma, "diploma");
        }
        if(transcript != null) {
            transcriptPath = fileStorageService.saveFile(transcript, "transcript");
        }

        return new ResponseEntity<>(aboutService.saveAbout(aboutDTO, filePath, diplomaPath, transcriptPath), HttpStatus.CREATED);
    }

    @GetMapping(ADMIN_ABOUT_ID_PATH)
    public ResponseEntity<AboutDTO> getAboutById(@PathVariable("aboutId") UUID aboutId){

        return new ResponseEntity<>(aboutService.getAboutById(aboutId).orElseThrow(()-> new NotFoundException("Entered id is not Found")), HttpStatus.ACCEPTED);
    }

    @GetMapping(PUBLIC_ABOUT_PATH)
    public ResponseEntity<AboutDTO> getRecentAbout(@RequestParam(required = false) Boolean active){
        if(active != null && active) {
            return new ResponseEntity<>(aboutService.getActiveAbout().orElseThrow(NotFoundException::new), HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>(aboutService.getRecentAbout().orElseThrow(()-> new NotFoundException("No recent data")), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(ADMIN_ABOUT_ID_PATH)
    public ResponseEntity<ResponseSuccess> deleteAboutById(@PathVariable("aboutId") UUID aboutId) {
        return new ResponseEntity<>(aboutService.deleteAboutById(aboutId).orElseThrow(()-> new NotFoundException("Entered id is not Found")), HttpStatus.ACCEPTED);
    }

}
