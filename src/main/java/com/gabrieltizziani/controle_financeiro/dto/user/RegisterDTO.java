package com.gabrieltizziani.controle_financeiro.dto.user;

import com.gabrieltizziani.controle_financeiro.domain.enums.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
