package com.amazing.juno.pmsrest.controller.api.v1;


import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteDTO;
import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteFindAllUnderConditionDTO;
import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteFindAllUnderConditionResponseDTO;
import com.amazing.juno.pmsrest.entity.ResponseSuccess;
import com.amazing.juno.pmsrest.exc.NotFoundException;
import com.amazing.juno.pmsrest.service.relevantsites.RelevantSiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RelevantSiteRestController {

    private final RelevantSiteService relevantSiteService;

    public static final String PUBLIC_PATH="/api/v1/public";

    public static final String ADMIN_PATH="/api/v1/admin";

    public static final String PUBLIC_RELEVANT_SITES_PATH = PUBLIC_PATH + "/relevant-sites";

    public static final String ADMIN_RELEVANT_SITES_PATH = ADMIN_PATH + "/relevant-sites";

    public static final String ADMIN_RELEVANT_SITES_ID_PATH = ADMIN_PATH + "/relevant-sites/{id}";

    @GetMapping(PUBLIC_RELEVANT_SITES_PATH)
    public ResponseEntity<RelevantSiteFindAllUnderConditionResponseDTO> listRelevantSites(RelevantSiteFindAllUnderConditionDTO relevantSiteFindAllUnderConditionDTO){
        return new ResponseEntity<>(relevantSiteService.findAllUnderCondition(relevantSiteFindAllUnderConditionDTO), HttpStatus.OK);
    }

    @PostMapping(ADMIN_RELEVANT_SITES_PATH)
    public ResponseEntity<RelevantSiteDTO> saveRelevantSite(@Validated @RequestBody RelevantSiteDTO relevantSiteDTO) {

        return new ResponseEntity<>(relevantSiteService.save(relevantSiteDTO), HttpStatus.CREATED);
    }

    @PutMapping(ADMIN_RELEVANT_SITES_ID_PATH)
    public ResponseEntity<RelevantSiteDTO> updateRelevantSite(@PathVariable("id") UUID id,
                                                              @Validated @RequestBody RelevantSiteDTO relevantSiteDTO) {

        return new ResponseEntity<>(relevantSiteService.updateById(id, relevantSiteDTO).orElseThrow(()->new NotFoundException(id.toString() + "doesn't exist.")), HttpStatus.OK);
    }

    @GetMapping(ADMIN_RELEVANT_SITES_ID_PATH)
    public ResponseEntity<RelevantSiteDTO> getRelevantSiteById(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(relevantSiteService.getRelevantSiteById(id).orElseThrow(()-> new NotFoundException(id.toString() + "doesn't exist!")), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(ADMIN_RELEVANT_SITES_ID_PATH)
    public ResponseEntity<ResponseSuccess> DeleteRelevantSite(@PathVariable("id") UUID id) {
        UUID deletedId = relevantSiteService.deleteById(id).orElseThrow(
                ()->new NotFoundException(id.toString() + "doesn't exist.")
        );

        ResponseSuccess response = new ResponseSuccess();
        response.setStatus(HttpStatus.ACCEPTED.value());
        response.setTimeStamp(LocalDateTime.now());
        response.setMessage(deletedId.toString()+" is successfully deleted");

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

}
