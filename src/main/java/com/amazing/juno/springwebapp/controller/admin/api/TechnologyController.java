package com.amazing.juno.springwebapp.controller.admin.api;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@AllArgsConstructor
@RestController
@RequestMapping("/admin/api/technology")
public class TechnologyController {

    @GetMapping
    public ResponseEntity listCategories(){

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity addCategory(@RequestBody Object category){

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{categoryName}")
    public ResponseEntity listItemsByCategoryName(@PathVariable("categoryName") String categoryName){

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{categoryName}")
    public ResponseEntity saveOrUpdateItemsToCategoryName(@PathVariable("categoryName") String categoryName,@RequestBody Object items){

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{categoryName}")
    public ResponseEntity deleteCategoryByCategoryName(@PathVariable("categoryName") String categoryName){

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{categoryName}/{itemId}")
    public ResponseEntity deleteItemsInCategoryName(@PathVariable("categoryName") String categoryName,@PathVariable("itemId") UUID itemId){

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }




}
