package com.amazing.juno.springwebapp.controller.api;


import com.amazing.juno.springwebapp.dto.TechCategoryDTO;
import com.amazing.juno.springwebapp.dto.TechCategoryItemDTO;
import com.amazing.juno.springwebapp.exc.NotFoundException;
import com.amazing.juno.springwebapp.service.TechnologyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@RestController
public class TechnologyRestController {

    public final static String ADMIN_TECHNOLOGY_PATH = "/api/admin/technology";

    public final static String PUBLIC_TECHNOLOGY_PATH = "/api/public/technology";

    public final static String ADMIN_TECHNOLOGY_CATEGORY_NAME_PATH = "/api/admin/technology/{categoryName}";

    public final static String ADMIN_TECHNOLOGY_CATEGORY_NAME_ITEM_NAME_PATH = "/api/admin/technology/{categoryName}/{itemId}";

    private final TechnologyService technologyService;

    @GetMapping(PUBLIC_TECHNOLOGY_PATH)
    public ResponseEntity<List<TechCategoryDTO>> listCategories(){
        return new ResponseEntity<>(technologyService.findAllCategories(),HttpStatus.ACCEPTED);
    }

    @PostMapping(ADMIN_TECHNOLOGY_PATH)
    public ResponseEntity<TechCategoryDTO> addOrUpdateCategory(@Validated @RequestBody TechCategoryDTO categoryDTO){

        return new ResponseEntity<>(technologyService.addCategory(categoryDTO), HttpStatus.CREATED);
    }


    @PostMapping(ADMIN_TECHNOLOGY_CATEGORY_NAME_PATH)
    public ResponseEntity<TechCategoryDTO> saveOrUpdateItemToCategory(@PathVariable("categoryName") String categoryName,@Validated @RequestBody TechCategoryItemDTO item){

        return new ResponseEntity<>(technologyService.saveOrUpdateItemToCategory(categoryName, item).orElseThrow(NotFoundException::new),HttpStatus.CREATED);
    }

    @DeleteMapping(ADMIN_TECHNOLOGY_CATEGORY_NAME_PATH)
    public ResponseEntity<?> deleteCategoryByCategoryName(@PathVariable("categoryName") String categoryName){
        if(!technologyService.deleteCategoryByCategoryName(categoryName)){
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(ADMIN_TECHNOLOGY_CATEGORY_NAME_ITEM_NAME_PATH)
    public ResponseEntity<?> deleteItemsInCategory(@PathVariable("categoryName") String categoryName,@PathVariable("itemId") UUID itemId){
        if(!technologyService.deleteItemsInCategory(categoryName,itemId)){
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
