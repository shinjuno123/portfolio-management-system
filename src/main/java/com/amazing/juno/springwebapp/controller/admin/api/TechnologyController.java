package com.amazing.juno.springwebapp.controller.admin.api;


import com.amazing.juno.springwebapp.entity.TechCategory;
import com.amazing.juno.springwebapp.entity.TechCategoryItem;
import com.amazing.juno.springwebapp.service.admin.TechnologyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/technology")
public class TechnologyController {

    private final TechnologyService technologyService;

    @GetMapping
    public ResponseEntity<List<TechCategory>> listCategories(){
        List<TechCategory> categories = technologyService.findAllCategories();

        return new ResponseEntity<>(categories,HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<TechCategory> addCategory(@RequestBody TechCategory category){
        technologyService.addCategory(category);

        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<List<TechCategoryItem>> listItemsByCategoryName(@PathVariable("categoryName") String categoryName){

        return new ResponseEntity<>(technologyService.listItemsByCategoryName(categoryName),HttpStatus.ACCEPTED);
    }

    @PostMapping("/{categoryName}")
    public ResponseEntity<List<TechCategoryItem>> saveOrUpdateItemsToCategory(@PathVariable("categoryName") String categoryName,@RequestBody List<TechCategoryItem> items){

        return new ResponseEntity<>(technologyService.saveOrUpdateItemsToCategory(categoryName, items),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{categoryName}")
    public ResponseEntity<?> deleteCategoryByCategoryName(@PathVariable("categoryName") String categoryName){
        log.debug(categoryName);
        technologyService.deleteCategoryByCategoryName(categoryName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/categories/{itemId}")
    public ResponseEntity<?> deleteItemsInCategory(@PathVariable("itemId") UUID itemId){
        technologyService.deleteItemsInCategory(itemId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
