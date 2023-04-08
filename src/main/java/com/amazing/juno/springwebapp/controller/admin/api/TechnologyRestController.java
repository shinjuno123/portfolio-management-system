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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/technology")
public class TechnologyRestController {

    private final TechnologyService technologyService;

    @GetMapping
    public ResponseEntity<List<TechCategoryDTO>> listCategories(){
        return new ResponseEntity<>(technologyService.findAllCategories(),HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<TechCategoryDTO> addCategory(@RequestBody TechCategoryDTO categoryDTO){

        return new ResponseEntity<>(technologyService.addCategory(categoryDTO), HttpStatus.CREATED);
    }


    @PostMapping("/{categoryName}")
    public ResponseEntity<TechCategoryDTO> saveOrUpdateItemToCategory(@PathVariable("categoryName") String categoryName, @RequestBody TechCategoryItemDTO items){

        return new ResponseEntity<>(technologyService.saveOrUpdateItemToCategory(categoryName, items).orElseThrow(NotFoundException::new),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{categoryName}")
    public ResponseEntity<?> deleteCategoryByCategoryName(@PathVariable("categoryName") String categoryName){
        log.debug(categoryName);
        technologyService.deleteCategoryByCategoryName(categoryName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{categoryName}/{itemId}")
    public ResponseEntity<?> deleteItemsInCategory(@PathVariable("categoryName") String categoryName,@PathVariable("itemId") UUID itemId){
        if(!technologyService.deleteItemsInCategory(categoryName,itemId)){
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
