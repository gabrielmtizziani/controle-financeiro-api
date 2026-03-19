package com.gabrieltizziani.controle_financeiro.dto.category;

import com.gabrieltizziani.controle_financeiro.domain.Category;
import com.gabrieltizziani.controle_financeiro.domain.enums.StatusCategory;
import com.gabrieltizziani.controle_financeiro.domain.enums.TypeCategory;

public record CategoryResponse(Long id,
                              String nameCategory,
                              TypeCategory typeCategory,
                               StatusCategory statusCategory

) {
    public CategoryResponse(Category category){
        this(
                category.getId(),
                category.getNameCategory(),
                category.getTypeCategory(),
                category.getStatusCategory()
        );
    }}

