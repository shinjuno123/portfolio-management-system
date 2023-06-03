package com.amazing.juno.pmsrest.controller.api;


import com.amazing.juno.pmsrest.dto.SecondCategoryDTO;
import com.amazing.juno.pmsrest.dto.FirstCategoryDTO;
import com.amazing.juno.pmsrest.dto.RelevantProjectDTO;
import com.amazing.juno.pmsrest.dto.SkillSetItemDTO;
import com.amazing.juno.pmsrest.entity.ResponseSuccess;
import com.amazing.juno.pmsrest.exc.NotFoundException;
import com.amazing.juno.pmsrest.exc.NotSavedException;
import com.amazing.juno.pmsrest.service.FileStorageService;
import com.amazing.juno.pmsrest.service.SkillSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SkillSetRestController {

    private final SkillSetService skillSetService;

    private final FileStorageService fileStorageService;

    // Url partial paths

    public final static String FIRST_CATEGORY_ID = "/first-categories/{firstCategoryId}";

    public final static String SECOND_CATEGORY_ID = "/second-categories/{secondCategoryId}";

    public final static String SKILL_SET_ITEM_ID = "/skill-set-items/{skillSetItemId}";

    public final static String RELEVANT_PROJECT_ID = "/relevant-projects/{relevantProjectId}";

    public final static String PUBLIC_ROOT_URL = "/api/public/skill-set";

    public final static String ADMIN_ROOT_URL = "/api/admin/skill-set";

    // Full Url Paths

    public final static String PUBLIC_SKILL_SET_PATH =
            PUBLIC_ROOT_URL;

    public final static String PUBLIC_SKILL_SET_SECOND_CATEGORY_ID_PATH =
            PUBLIC_ROOT_URL
                    + SECOND_CATEGORY_ID;

    public final static String ADMIN_SKILL_SET_FIRST_CATEGORY_PATH =
            ADMIN_ROOT_URL
                    + "/first-category";


    public final static String ADMIN_SKILL_SET_FIRST_CATEGORY_ID =
            ADMIN_ROOT_URL
                    + FIRST_CATEGORY_ID;

    public final static String ADMIN_SKILL_SET_FIRST_CATEGORY_ID_SECOND_CATEGORY_PATH =
            ADMIN_ROOT_URL
                    + FIRST_CATEGORY_ID
                    + "/second-category";

    public final static String ADMIN_SKILL_SET_FIRST_CATEGORY_ID_CATEGORY_ID =
            ADMIN_ROOT_URL
                    + FIRST_CATEGORY_ID
                    + SECOND_CATEGORY_ID;

    public final static String ADMIN_SKILL_SET_FIRST_CATEGORY_ID_SECOND_CATEGORY_ID_SKILL_ITEM_PATH =
            ADMIN_ROOT_URL
                    + FIRST_CATEGORY_ID
                    + SECOND_CATEGORY_ID
                    + "/skill-set-item";

    public final static String ADMIN_SKILL_SET_FIRST_CATEGORY_ID_SECOND_CATEGORY_ID_SKILL_ITEM_ID =
            ADMIN_ROOT_URL
                    + FIRST_CATEGORY_ID
                    + SECOND_CATEGORY_ID
                    + SKILL_SET_ITEM_ID;

    public final static String ADMIN_SKILL_SET_FIRST_CATEGORY_ID_SECOND_CATEGORY_ID_SKILL_ITEM_ID_RELEVANT_PROJECT_PATH =
            ADMIN_ROOT_URL
                    + FIRST_CATEGORY_ID
                    + SECOND_CATEGORY_ID
                    + SKILL_SET_ITEM_ID
                    + "/relevant-project";

    public final static String ADMIN_SKILL_SET_FIRST_CATEGORY_ID_SECOND_CATEGORY_ID_SKILL_ITEM_ID_RELEVANT_PROJECT_ID =
            ADMIN_ROOT_URL
                    + FIRST_CATEGORY_ID
                    + SECOND_CATEGORY_ID
                    + SKILL_SET_ITEM_ID
                    + RELEVANT_PROJECT_ID;


    @GetMapping(PUBLIC_SKILL_SET_PATH)
    public ResponseEntity<List<FirstCategoryDTO>> listAllSkillSet() {
        return new ResponseEntity<>(skillSetService.listAllSkillSet(), HttpStatus.ACCEPTED);
    }


    @GetMapping(PUBLIC_SKILL_SET_SECOND_CATEGORY_ID_PATH)
    public ResponseEntity<List<SkillSetItemDTO>> listSkillSetItemsBySecondCategoryId(@PathVariable("secondCategoryId") UUID secondCategoryId) {
        return new ResponseEntity<>(skillSetService.listSkillSetItemsByCategoryId(secondCategoryId), HttpStatus.ACCEPTED);
    }

    @PostMapping(ADMIN_SKILL_SET_FIRST_CATEGORY_PATH)
    public ResponseEntity<FirstCategoryDTO> saveOrUpdatePlatform(@Validated @RequestBody FirstCategoryDTO firstCategoryDTO) {
        return new ResponseEntity<>(skillSetService.saveOrUpdatePlatform(firstCategoryDTO).orElseThrow(()->new NotFoundException("Entered Id in entity is invalid!")), HttpStatus.ACCEPTED);
    }


    @PostMapping(ADMIN_SKILL_SET_FIRST_CATEGORY_ID_SECOND_CATEGORY_PATH)
    public ResponseEntity<SecondCategoryDTO> saveOrUpdateCategory(@PathVariable("firstCategoryId") UUID platformId, @Validated @RequestBody SecondCategoryDTO secondCategoryDTO) {
        return new ResponseEntity<>(skillSetService.saveOrUpdateCategory(platformId, secondCategoryDTO).orElseThrow(NotSavedException::new), HttpStatus.ACCEPTED);
    }

    @PostMapping(ADMIN_SKILL_SET_FIRST_CATEGORY_ID_SECOND_CATEGORY_ID_SKILL_ITEM_PATH)
    public ResponseEntity<SkillSetItemDTO> saveOrUpdateSkillItemSet(@PathVariable("firstCategoryId") UUID firstCategoryId,
                                                                    @PathVariable("secondCategoryId") UUID secondCategoryId,
                                                                    @Validated @RequestPart("skillSetItem") SkillSetItemDTO skillSetItemDTO,
                                                                    @RequestPart("skillSetImage") MultipartFile multipartFile) {

        String skillSetImagePath = fileStorageService.saveFile(multipartFile, "skill-set");

        return new ResponseEntity<>(skillSetService.saveOrUpdateSkillSetItem(firstCategoryId, secondCategoryId, skillSetItemDTO, skillSetImagePath).orElseThrow(NotSavedException::new), HttpStatus.ACCEPTED);
    }

    @PostMapping(ADMIN_SKILL_SET_FIRST_CATEGORY_ID_SECOND_CATEGORY_ID_SKILL_ITEM_ID_RELEVANT_PROJECT_PATH)
    public ResponseEntity<RelevantProjectDTO> saveOrUpdateRelevantProject(@PathVariable("firstCategoryId") UUID platformId,
                                                                          @PathVariable("secondCategoryId") UUID categoryId,
                                                                          @PathVariable("skillSetItemId") UUID skillSetItemId,
                                                                          @Validated @RequestBody RelevantProjectDTO relevantProjectDTO
    ) {

        return new ResponseEntity<>(skillSetService.saveOrUpdateRelevantProject(platformId, categoryId, skillSetItemId, relevantProjectDTO).orElseThrow(NotSavedException::new), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(ADMIN_SKILL_SET_FIRST_CATEGORY_ID)
    public ResponseEntity<ResponseSuccess> deleteFirstCategory(@PathVariable("firstCategoryId") UUID firstCategoryId) {



        return new ResponseEntity<>(skillSetService.deletePlatform(firstCategoryId).orElseThrow(() -> new NotFoundException("Entered Id is not valid")), HttpStatus.ACCEPTED);
    }


    @DeleteMapping(ADMIN_SKILL_SET_FIRST_CATEGORY_ID_CATEGORY_ID)
    public ResponseEntity<ResponseSuccess> deleteSecondCategory(@PathVariable("firstCategoryId") UUID firstCategoryId,
                                                                @PathVariable("secondCategoryId") UUID secondCategoryId){


        return new ResponseEntity<>(skillSetService.deleteCategory(firstCategoryId, secondCategoryId).orElseThrow(() -> new NotFoundException("Entered Ids are not valid")), HttpStatus.ACCEPTED);
    }


    @DeleteMapping(ADMIN_SKILL_SET_FIRST_CATEGORY_ID_SECOND_CATEGORY_ID_SKILL_ITEM_ID)
    public ResponseEntity<ResponseSuccess> deleteSkillSetItem(@PathVariable("firstCategoryId") UUID firstCategoryId,
                                                              @PathVariable("secondCategoryId") UUID secondCategoryId,
                                                              @PathVariable("skillSetItemId") UUID skillSetItemId){

        return new ResponseEntity<>(skillSetService.deleteSkillSetItem(firstCategoryId, secondCategoryId, skillSetItemId).orElseThrow(() -> new NotFoundException("Entered Ids are not valid")), HttpStatus.ACCEPTED);
}


    @DeleteMapping(ADMIN_SKILL_SET_FIRST_CATEGORY_ID_SECOND_CATEGORY_ID_SKILL_ITEM_ID_RELEVANT_PROJECT_ID)
    public ResponseEntity<ResponseSuccess> deleteRelevantProject(@PathVariable("firstCategoryId") UUID firstCategoryId,
                                                                 @PathVariable("secondCategoryId") UUID secondCategoryId,
                                                                 @PathVariable("skillSetItemId") UUID skillSetItemId,
                                                                 @PathVariable("relevantProjectId") UUID relevantProjectId) {

        return new ResponseEntity<>(skillSetService.deleteRelevantProject(firstCategoryId, secondCategoryId, skillSetItemId, relevantProjectId).orElseThrow(() -> new NotFoundException("Entered Ids are not valid")), HttpStatus.ACCEPTED);
    }


}
