package com.gabrieltizziani.controle_financeiro.service;

import com.gabrieltizziani.controle_financeiro.domain.Category;
import com.gabrieltizziani.controle_financeiro.domain.User;
import com.gabrieltizziani.controle_financeiro.dto.account.AccountResponse;
import com.gabrieltizziani.controle_financeiro.dto.category.CategoryResponse;
import com.gabrieltizziani.controle_financeiro.dto.category.CreateCategoryRequest;
import com.gabrieltizziani.controle_financeiro.dto.category.UpdateCategoryRequest;
import com.gabrieltizziani.controle_financeiro.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public Category createCategory(CreateCategoryRequest createCategoryRequest, User user) {
        String categoryName = createCategoryRequest.nameCategory().trim();
        if (categoryRepository.existsByUserIdAndNameCategory(user.getId(), categoryName)) {
            throw new RuntimeException("You already have an category with this name");
        }

        Category category = new Category(createCategoryRequest, user);
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Long id, UpdateCategoryRequest updateCategoryRequest, Long userId) {
        var category = categoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (updateCategoryRequest.nameCategory() != null) {
            String nameCategory = updateCategoryRequest.nameCategory().trim();

            if (categoryRepository.existsByUserIdAndNameCategoryAndIdNot(userId, nameCategory, id)) {
                throw new RuntimeException("You already have an account with this name");
            }
        }

        category.updateCategory(updateCategoryRequest);
        return categoryRepository.save(category);
    }

    public List<CategoryResponse> getAllCategories(Long userId) {
        return categoryRepository.findAllByUserId(userId)
                .stream()
                .map(CategoryResponse::new)
                .toList();
    }



}
