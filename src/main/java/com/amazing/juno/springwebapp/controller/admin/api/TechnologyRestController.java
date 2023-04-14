package com.amazing.juno.springwebapp.controller.admin.api;


import com.amazing.juno.springwebapp.dto.TechCategoryDTO;
import com.amazing.juno.springwebapp.dto.TechCategoryItemDTO;
import com.amazing.juno.springwebapp.entity.TechCategory;
import com.amazing.juno.springwebapp.entity.TechCategoryItem;
import com.amazing.juno.springwebapp.exc.NotFoundException;
import com.amazing.juno.springwebapp.service.admin.TechnologyService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@RestController
public class TechnologyRestController {

    public final static String TECHNOLOGY_PATH = "/api/technology";

    public final static String TECHNOLOGY_CATEGORY_NAME_PATH = "/api/technology/{categoryName}";

    public final static String TECHNOLOGY_CATEGORY_NAME_ITEM_NAME_PATH = "/api/technology/{categoryName}/{itemId}";

    private final TechnologyService technologyService;

    @GetMapping(TECHNOLOGY_PATH)
    public ResponseEntity<List<TechCategoryDTO>> listCategories(){
        return new ResponseEntity<>(technologyService.findAllCategories(),HttpStatus.ACCEPTED);
    }

    @PostMapping(TECHNOLOGY_PATH)
    public ResponseEntity<TechCategoryDTO> addOrUpdateCategory(@Validated @RequestBody TechCategoryDTO categoryDTO){

        return new ResponseEntity<>(technologyService.addCategory(categoryDTO), HttpStatus.CREATED);
    }


    @PostMapping(TECHNOLOGY_CATEGORY_NAME_PATH)
    public ResponseEntity<TechCategoryDTO> saveOrUpdateItemToCategory(@PathVariable("categoryName") String categoryName,@Validated @RequestBody TechCategoryItemDTO item){

        return new ResponseEntity<>(technologyService.saveOrUpdateItemToCategory(categoryName, item).orElseThrow(NotFoundException::new),HttpStatus.CREATED);
    }

    @DeleteMapping(TECHNOLOGY_CATEGORY_NAME_PATH)
    public ResponseEntity<?> deleteCategoryByCategoryName(@PathVariable("categoryName") String categoryName){
        if(!technologyService.deleteCategoryByCategoryName(categoryName)){
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(TECHNOLOGY_CATEGORY_NAME_ITEM_NAME_PATH)
    public ResponseEntity<?> deleteItemsInCategory(@PathVariable("categoryName") String categoryName,@PathVariable("itemId") UUID itemId){
        if(!technologyService.deleteItemsInCategory(categoryName,itemId)){
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
