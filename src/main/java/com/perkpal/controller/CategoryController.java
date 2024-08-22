package com.perkpal.controller;

import com.perkpal.dto.CategoryDto;
import com.perkpal.response.ResponseHandler;
import com.perkpal.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.perkpal.constants.Message.*;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Object> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        return ResponseHandler.responseBuilder(CATEGORY_CREATION, HttpStatus.CREATED, createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategory = categoryService.updateCategory(id, categoryDto);
        return ResponseHandler.responseBuilder(CATEGORY_UPDATION , HttpStatus.OK, updatedCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable Long id) {
        CategoryDto category = categoryService.getCategoryById(id);
        return ResponseHandler.responseBuilder(CATEGORY_RETRIEVAL, HttpStatus.OK, category);
    }

    @GetMapping
    public ResponseEntity<Object> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseHandler.responseBuilder(CATEGORY_RETRIEVAL, HttpStatus.OK, categories);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseHandler.responseBuilder(CATEGORY_DELETE, HttpStatus.NO_CONTENT, null);
    }
}
