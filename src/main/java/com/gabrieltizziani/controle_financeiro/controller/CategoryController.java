package com.gabrieltizziani.controle_financeiro.controller;

import com.gabrieltizziani.controle_financeiro.domain.Category;
import com.gabrieltizziani.controle_financeiro.domain.User;
import com.gabrieltizziani.controle_financeiro.dto.category.CategoryResponse;
import com.gabrieltizziani.controle_financeiro.dto.category.CreateCategoryRequest;
import com.gabrieltizziani.controle_financeiro.dto.category.UpdateCategoryRequest;
import com.gabrieltizziani.controle_financeiro.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CreateCategoryRequest createCategoryRequest,
                                                          Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Category category = categoryService.createCategory(createCategoryRequest, user);
        return ResponseEntity.ok(new CategoryResponse(category));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return  ResponseEntity.ok(categoryService.getAllCategories(user.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCategoryRequest updateCategoryRequest, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        var categoryUpdate = categoryService.updateCategory(id, updateCategoryRequest, user.getId());
        return ResponseEntity.ok(new CategoryResponse(categoryUpdate));
    }

    @PatchMapping("/{id}/inactivate")
    public ResponseEntity<Void> inactivateCategory(
            @PathVariable Long id, Authentication authentication
    ){
        var user = (User) authentication.getPrincipal();
        categoryService.inactivateCategory(id, user.getId());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateCategory(
            @PathVariable Long id, Authentication authentication
    ){
        var user = (User) authentication.getPrincipal();
        categoryService.activateCategory(id, user.getId());
        return ResponseEntity.noContent().build();
    }



}
