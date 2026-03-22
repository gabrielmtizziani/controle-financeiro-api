package com.gabrieltizziani.controle_financeiro.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
        @NotBlank(message = "The name category is required")
        String nameCategory
) {
}
