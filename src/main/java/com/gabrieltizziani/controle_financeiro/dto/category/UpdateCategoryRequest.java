package com.gabrieltizziani.controle_financeiro.dto.category;

import com.gabrieltizziani.controle_financeiro.domain.enums.TypeCategory;
import jakarta.validation.constraints.NotNull;

public record UpdateCategoryRequest(
        String nameCategory
) {
}
