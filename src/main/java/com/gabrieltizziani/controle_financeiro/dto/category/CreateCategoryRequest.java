package com.gabrieltizziani.controle_financeiro.dto.category;

import com.gabrieltizziani.controle_financeiro.domain.enums.TypeCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCategoryRequest(
        @NotBlank(message = "The name category is required")
        String nameCategory
) {
}
