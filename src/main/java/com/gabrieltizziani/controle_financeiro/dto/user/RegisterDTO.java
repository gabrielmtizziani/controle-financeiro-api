package com.gabrieltizziani.controle_financeiro.dto.user;

import com.gabrieltizziani.controle_financeiro.domain.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
