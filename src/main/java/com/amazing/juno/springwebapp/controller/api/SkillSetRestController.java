package com.amazing.juno.springwebapp.controller.api;


import com.amazing.juno.springwebapp.dto.CategoryDTO;
import com.amazing.juno.springwebapp.dto.PlatformDTO;
import com.amazing.juno.springwebapp.dto.RelevantProjectDTO;
import com.amazing.juno.springwebapp.dto.SkillSetItemDTO;
import com.amazing.juno.springwebapp.entity.ResponseSuccess;
import com.amazing.juno.springwebapp.exc.NotSavedException;
import com.amazing.juno.springwebapp.service.FileStorageService;
import com.amazing.juno.springwebapp.service.SkillSetService;
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

    public final static String PLATFORM_ID = "/platforms/{platformId}";

    public final static String CATEGORY_ID = "/categories/{categoryId}";

    public final static String SKILL_SET_ITEM_ID = "/skill-set-items/{skillSetItemId}";

    public final static String PUBLIC_ROOT_URL = "/api/public/skill-set";

    public final static String ADMIN_ROOT_URL = "/api/public/skill-set";

    // Full Url Paths

    public final static String PUBLIC_SKILL_SET_PATH =
            PUBLIC_ROOT_URL;

    public final static String PUBLIC_SKILL_SET_CATEGORY_ID_PATH =
            PUBLIC_ROOT_URL
                    + CATEGORY_ID;

    public final static String ADMIN_SKILL_SET_PLATFORM_PATH =
            PUBLIC_ROOT_URL
                    + "/platform";


    public final static String ADMIN_SKILL_SET_PLATFORM_ID =
            ADMIN_ROOT_URL
                    + PLATFORM_ID;

    public final static String ADMIN_SKILL_SET_PLATFORM_ID_CATEGORY_PATH =
            ADMIN_ROOT_URL
                    + PLATFORM_ID
                    + "/category";

    public final static String ADMIN_SKILL_SET_PLATFORM_ID_CATEGORY_ID_SKILL_ITEM_PATH =
            ADMIN_ROOT_URL
                    + PLATFORM_ID
                    + CATEGORY_ID
                    + "/skill-set-item";

    public final static String ADMIN_SKILL_SET_PLATFORM_ID_CATEGORY_ID_SKILL_ITEM_ID_RELEVANT_PROJECT_PATH =
            ADMIN_ROOT_URL
                    + PLATFORM_ID
                    + CATEGORY_ID
                    + SKILL_SET_ITEM_ID
                    + "/relevant-project";



    @GetMapping(PUBLIC_SKILL_SET_PATH)
    public ResponseEntity<List<PlatformDTO>> listAllSkillSet() {
        return new ResponseEntity<>(skillSetService.listAllSkillSet(), HttpStatus.ACCEPTED);
    }


    @GetMapping(PUBLIC_SKILL_SET_CATEGORY_ID_PATH)
    public ResponseEntity<List<SkillSetItemDTO>> listSkillSetItemsByCategoryId(@PathVariable("categoryId") UUID categoryId) {
        return new ResponseEntity<>(skillSetService.listSkillSetItemsByCategoryId(categoryId), HttpStatus.ACCEPTED);
    }

    @PostMapping(ADMIN_SKILL_SET_PLATFORM_PATH)
    public ResponseEntity<PlatformDTO> saveOrUpdatePlatform(@Validated @RequestBody PlatformDTO platformDTO) {
        return new ResponseEntity<>(skillSetService.saveOrUpdatePlatform(platformDTO), HttpStatus.CREATED);
    }


    @PostMapping(ADMIN_SKILL_SET_PLATFORM_ID_CATEGORY_PATH)
    public ResponseEntity<CategoryDTO> saveOrUpdateCategory(@PathVariable("platformId") UUID platformId, @Validated @RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(skillSetService.saveOrUpdateCategory(platformId, categoryDTO).orElseThrow(NotSavedException::new), HttpStatus.OK);
    }

    @PostMapping(ADMIN_SKILL_SET_PLATFORM_ID_CATEGORY_ID_SKILL_ITEM_PATH)
    public ResponseEntity<SkillSetItemDTO> saveOrUpdateSkillItemSet(@PathVariable("platformId") UUID platformId,
                                                                    @PathVariable("categoryId") UUID categoryId,
                                                                    @Validated @RequestPart("skillSetItem") SkillSetItemDTO skillSetItemDTO,
                                                                    @RequestPart("skillSetImage") MultipartFile multipartFile) {

        String skillSetImagePath = fileStorageService.saveFile(multipartFile, "skill-set");

        return new ResponseEntity<>(skillSetService.saveOrUpdateSkillSetItem(platformId, categoryId, skillSetItemDTO, skillSetImagePath).orElseThrow(NotSavedException::new), HttpStatus.OK);
    }

    @PostMapping(ADMIN_SKILL_SET_PLATFORM_ID_CATEGORY_ID_SKILL_ITEM_ID_RELEVANT_PROJECT_PATH)
    public ResponseEntity<RelevantProjectDTO> saveOrUpdateRelevantProject(@PathVariable("platformId") UUID platformId,
                                                                          @PathVariable("categoryId") UUID categoryId,
                                                                          @PathVariable("skillSetItemId") UUID skillSetItemId,
                                                                          @Validated @RequestBody RelevantProjectDTO relevantProjectDTO
                                                                          ) {

        return new ResponseEntity<>(skillSetService.saveOrUpdateRelevantProject(platformId,categoryId,skillSetItemId,relevantProjectDTO).orElseThrow(NotSavedException::new), HttpStatus.OK);
    }

    @DeleteMapping(ADMIN_SKILL_SET_PLATFORM_ID)
    public ResponseEntity<ResponseSuccess> deletePlatform(@PathVariable("platformId") UUID platformId){

        return null;
    }





}
