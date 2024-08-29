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


    /**
     * Creates a new category.
     *
     * @param categoryDto The data transfer object containing the category details to be created.
     * @return A ResponseEntity containing a success message, HTTP status code, and the created category details.
     */
    @PostMapping
    public ResponseEntity<Object> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        return ResponseHandler.responseBuilder(CATEGORY_CREATION, HttpStatus.CREATED, createdCategory);
    }


    /**
     * Updates an existing category by ID.
     *
     * @param id          The ID of the category to be updated.
     * @param categoryDto The data transfer object containing the updated category details.
     * @return A ResponseEntity containing a success message, HTTP status code, and the updated category details.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategory = categoryService.updateCategory(id, categoryDto);
        return ResponseHandler.responseBuilder(CATEGORY_UPDATION, HttpStatus.OK, updatedCategory);
    }


    /**
     * Retrieves a category by its ID.
     *
     * @param id The ID of the category to be retrieved.
     * @return A ResponseEntity containing a success message, HTTP status code, and the retrieved category details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable Long id) {
        CategoryDto category = categoryService.getCategoryById(id);
        return ResponseHandler.responseBuilder(CATEGORY_RETRIEVAL, HttpStatus.OK, category);
    }

    /**
     * Retrieves all categories.
     *
     * @return A ResponseEntity containing a success message, HTTP status code, and a list of all categories.
     */
    @GetMapping
    public ResponseEntity<Object> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseHandler.responseBuilder(CATEGORY_RETRIEVAL, HttpStatus.OK, categories);
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id The ID of the category to be deleted.
     * @return A ResponseEntity containing a success message and HTTP status code. No content is returned.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseHandler.responseBuilder(CATEGORY_DELETE, HttpStatus.NO_CONTENT, null);
    }

    /**
     * Retrieves only the names of all categories, typically for filtering activities.
     *
     * @return A ResponseEntity containing a success message, HTTP status code, and a list of category names.
     */
    @GetMapping("/category-name-only")
    public ResponseEntity<Object> getCategoryNameOnly() {
        return ResponseHandler.responseBuilder(CATEGORY_RETRIEVAL, HttpStatus.OK, categoryService.getAllCategoriesForActivityFilter());
    }
}
