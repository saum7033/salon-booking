package com.category.controller;

import com.category.modal.Category;
import com.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/salon/{id}")
    public ResponseEntity<Set<Category>> getCategoriesBySalon(
            @PathVariable Long id
    )throws Exception{
        Set<Category> categories = categoryService.getAllCategoriesBySalon(id);
        return ResponseEntity.ok(categories);
    }

    @GetMapping
    public ResponseEntity<Category> getCategoryById(
            @PathVariable Long id
    )throws Exception{
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }
}
